package com.venn.lfs.impl.autoconfig;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Spring configuration overriding Flyway migration behavior. */
@Configuration
public class FlywayConfiguration {

  /**
   * Disables automatic Flyway migrations.
   *
   * @return the migration strategy
   */
  @Bean
  FlywayMigrationStrategy flywayMigrationStrategy() {
    return flyway -> {
      // skip migration
    };
  }
}
