package com.venn.lfs.api.rest.impl;

import com.venn.lfs.dto.impl.LoadFundRequestDto;
import com.venn.lfs.dto.impl.LoadFundResponseDto;
import com.venn.lfs.impl.LoadFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** REST controller for load-fund operations. */
@RestController
public class LoadFundApiController {

  @Autowired private LoadFundService service;

  /**
   * Handles load-fund requests.
   *
   * @param dto the request body
   * @return the load result
   */
  @PostMapping(path = "rest/funds")
  public LoadFundResponseDto loadFund(@RequestBody LoadFundRequestDto dto) {
    return service.loadFund(dto);
  }
}
