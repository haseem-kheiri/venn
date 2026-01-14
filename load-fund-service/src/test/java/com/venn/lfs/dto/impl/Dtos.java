package com.venn.lfs.dto.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.ResourceUtils;

/** DTO loading utilities. */
public final class Dtos {
  private static final ObjectMapper om = new ObjectMapper();

  private Dtos() {}

  /**
   * Loads line-delimited JSON objects into DTOs.
   *
   * @param path the resource path
   * @param type the DTO type
   * @param <T> the DTO type
   * @return the parsed DTOs
   * @throws IOException if reading or parsing fails
   */
  public static <T> List<T> toDtos(String path, Class<T> type) throws IOException {
    final File file = ResourceUtils.getFile(path);
    final List<T> dtos = new ArrayList<>();
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
      String request;
      while ((request = reader.readLine()) != null) {
        dtos.add(om.readValue(request, type));
      }
    }
    return dtos;
  }
}
