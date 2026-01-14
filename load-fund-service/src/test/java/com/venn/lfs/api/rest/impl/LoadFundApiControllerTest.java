package com.venn.lfs.api.rest.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(
    classes = LoadFundApiControllerTest.TestConfig.class,
    webEnvironment = WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
class LoadFundApiControllerTest {

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

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder) {
      return builder.build();
    }
  }

  @LocalServerPort private int port;
  @Autowired private RestTemplate restTemplate;

  @Test
  void test() throws Exception {
    Assertions.assertTrue(
        invokeEndpoint(String.format("http://localhost:%d/rest/funds", port), HttpMethod.POST, "")
            .contains("400 Bad Request"));

    Assertions.assertTrue(
        invokeEndpoint(
                String.format("http://localhost:%d/rest/funds", port),
                HttpMethod.POST,
                "{\"id\":\"15887\",\"customer_id\":\"528\",\"load_amount\":\"3318.47\",\"time\":\"2000-01-01T00:00:00Z\"}")
            .contains("400 Bad Request"));
    Assertions.assertEquals(
        "{\"id\":\"15887\",\"customer_id\":\"528\",\"accepted\":true}",
        invokeEndpoint(
            String.format("http://localhost:%d/rest/funds", port),
            HttpMethod.POST,
            "{\"id\":\"15887\",\"customer_id\":\"528\",\"load_amount\":\"$3318.47\",\"time\":\"2000-01-01T00:00:00Z\"}"));
  }

  private String invokeEndpoint(String uriPath, HttpMethod method, String bodyStr) {
    try {
      return restTemplate.execute(
          new URI(uriPath),
          method,
          req -> {
            req.getHeaders().add("Content-Type", "application/json");
            req.getBody().write(bodyStr.getBytes());
          },
          resp -> {
            return new String(resp.getBody().readAllBytes(), StandardCharsets.UTF_8);
          });

    } catch (Exception e) {
      return e.getLocalizedMessage();
    }
  }
}
