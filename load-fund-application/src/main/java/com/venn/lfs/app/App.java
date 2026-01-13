package com.venn.lfs.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Spring Boot application entry point. */
@SpringBootApplication
public class App {

  /**
   * Launches the application.
   *
   * @param args command-line arguments
   */
  public static void main(String args[]) {
    SpringApplication.run(App.class, args);
  }
}
