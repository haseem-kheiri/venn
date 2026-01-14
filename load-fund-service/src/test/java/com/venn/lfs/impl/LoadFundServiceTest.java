package com.venn.lfs.impl;

import com.venn.lfs.dto.impl.Dtos;
import com.venn.lfs.dto.impl.LoadFundRequestDto;
import com.venn.lfs.dto.impl.LoadFundResponseDto;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(classes = LoadFundServiceTest.TestConfig.class)
@EnableAutoConfiguration
class LoadFundServiceTest {

  static class TestConfig {
    final String userName = "testuser";
    final String password = "testpassword";

    @SuppressWarnings("resource")
    @Bean(destroyMethod = "stop")
    PostgreSQLContainer<?> postgreSQLContainer() {
      PostgreSQLContainer<?> ods =
          new PostgreSQLContainer<>("postgres:16")
              .withDatabaseName("venn-ods")
              .withUsername(userName)
              .withPassword(password);
      ods.start();
      return ods;
    }

    @Bean(destroyMethod = "close")
    DataSource dataSource(PostgreSQLContainer<?> postgreSql) {
      final HikariConfig config = new HikariConfig();
      final String jdbcUrl = postgreSql.getJdbcUrl();
      config.setJdbcUrl(jdbcUrl);
      config.setUsername(userName);
      config.setPassword(password);
      config.setMaximumPoolSize(25);
      return new HikariDataSource(config);
    }
  }

  @Autowired private LoadFundService service;

  @Test
  void test() throws IOException {

    final List<LoadFundRequestDto> requestDtos =
        Dtos.toDtos("classpath:files/Venn - Back-End - Input.txt", LoadFundRequestDto.class);

    final List<LoadFundResponseDto> responseDtos =
        Dtos.toDtos("classpath:files/Venn - Back-End - Output .txt", LoadFundResponseDto.class);

    for (int index = 0; index < requestDtos.size(); index++) {
      final LoadFundResponseDto expected = responseDtos.get(index);
      final LoadFundRequestDto request = requestDtos.get(index);
      final LoadFundResponseDto actual = service.loadFund(request);
      Assertions.assertEquals(expected, actual);
    }
  }
}
