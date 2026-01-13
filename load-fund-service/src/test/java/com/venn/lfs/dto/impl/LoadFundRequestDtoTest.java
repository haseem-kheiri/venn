package com.venn.lfs.dto.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

class LoadFundRequestDtoTest {

  @Test
  void test() throws IOException {
    final File file = ResourceUtils.getFile("classpath:files/Venn - Back-End - Input.txt");
    final ObjectMapper om = new ObjectMapper();

    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
      String request;
      while ((request = reader.readLine()) != null) {
        om.readValue(request, LoadFundRequestDto.class);
      }
    }
  }
}
