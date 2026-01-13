package com.venn.lfs.impl.repository;

import com.venn.lfs.dto.impl.LoadFundRequestDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;

/** Repository for persisting load-fund ledger entries with enforced limits. */
public class LoadFundRepository {
  private static final String LOAD_FUND_SQL =
      """
      WITH incoming AS (
          SELECT
              ?::text AS customer_id,
              ?::text AS id,
              ?::numeric AS amount,
              ?::timestamptz AT TIME ZONE 'UTC' AS event_time,
              (?::timestamptz AT TIME ZONE 'UTC')::date AS day_bucket,
              date_trunc('week', ?::timestamptz AT TIME ZONE 'UTC')::date AS week_bucket
      ),
      stats AS MATERIALIZED (
          SELECT
              COUNT(*) FILTER (WHERE l.day_bucket = i.day_bucket) AS daily_count,
              COALESCE(SUM(
                  CASE WHEN l.day_bucket = i.day_bucket THEN l.amount ELSE 0 END
              ), 0) AS daily_amount,
              COALESCE(SUM(
                  CASE WHEN l.week_bucket = i.week_bucket THEN l.amount ELSE 0 END
              ), 0) AS weekly_amount
          FROM venn.fund_ledger l
          CROSS JOIN incoming i
          WHERE l.customer_id = i.customer_id
            AND l.week_bucket = i.week_bucket
      )
      INSERT INTO venn.fund_ledger (
          customer_id,
          id,
          amount,
          event_time,
          day_bucket,
          week_bucket
      )
      SELECT
          i.customer_id,
          i.id,
          i.amount,
          i.event_time,
          i.day_bucket,
          i.week_bucket
      FROM incoming i
      JOIN stats s ON true
      WHERE
          s.daily_count < 3
      AND s.daily_amount + i.amount <= 5000
      AND s.weekly_amount + i.amount <= 20000
      ON CONFLICT (customer_id, id) DO NOTHING;
      """;

  private final DataSource dataSource;

  /**
   * Creates a repository using the given data source.
   *
   * @param dataSource the JDBC data source
   */
  public LoadFundRepository(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  /**
   * Applies Flyway migrations for the given schema.
   *
   * @param schema the target database schema
   */
  public void migrate(String schema) {
    Flyway.configure()
        .dataSource(dataSource)
        .schemas(schema)
        .locations("classpath:db/migration/" + schema)
        .load()
        .migrate();
  }

  /**
   * Inserts a fund-load entry if limit constraints are satisfied.
   *
   * @param dto the load request
   * @return {@code true} if the row was inserted; {@code false} if rejected or duplicated
   * @throws LoadFundException if a database error occurs
   */
  public boolean loadFund(LoadFundRequestDto dto) {
    try (final Connection connection = dataSource.getConnection()) {
      connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
      try (final PreparedStatement ps = connection.prepareStatement(LOAD_FUND_SQL)) {
        ps.setString(1, dto.getCustomerId());
        ps.setString(2, dto.getId());
        ps.setDouble(3, dto.getLoadAmount());
        ps.setObject(4, dto.getTime());
        ps.setObject(5, dto.getTime());
        ps.setObject(6, dto.getTime());

        return ps.executeUpdate() == 1;
      }
    } catch (SQLException e) {
      throw new LoadFundException(e);
    }
  }
}
