package com.venn.lfs.impl.autoconfig;

import com.venn.lfs.impl.LoadFundService;
import com.venn.lfs.impl.repository.LoadFundRepository;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/** Spring configuration for load-fund components. */
@Configuration
@ComponentScan(basePackages = {"com.venn.lfs.api.rest.impl"})
public class LoadFundServiceConfiguration {

  @Bean
  @ConditionalOnMissingBean
  @ConfigurationProperties(prefix = "venn.load-fund.limits")
  LoadFundLimitsProperties loadFundLimitsProperties() {
    return new LoadFundLimitsProperties();
  }

  /**
   * Provides the {@link LoadFundRepository}.
   *
   * @param dataSource the JDBC data source
   * @param loadFundLimitsProperties properties defining load-fund limits
   * @return the repository instance
   */
  @Bean
  @ConditionalOnMissingBean
  LoadFundRepository loadFundRepository(
      DataSource dataSource, LoadFundLimitsProperties loadFundLimitsProperties) {
    final LoadFundRepository repository =
        new LoadFundRepository(dataSource, loadFundLimitsProperties);
    repository.migrate("venn");
    return repository;
  }

  /**
   * Provides the {@link LoadFundService}.
   *
   * @param repository the backing repository
   * @return the service instance
   */
  @Bean
  @ConditionalOnMissingBean
  LoadFundService loadFundService(LoadFundRepository repository) {
    final LoadFundService service = new LoadFundService(repository);
    return service;
  }
}
