package com.venn.lfs.api.rest.impl;

import com.venn.lfs.dto.impl.LoadFundRequestDto;
import com.venn.lfs.dto.impl.LoadFundResponseDto;
import com.venn.lfs.impl.LoadFundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/** REST controller for load-fund operations. */
@RestController
@Slf4j
public class LoadFundApiController {

  @Autowired private LoadFundService service;

  /**
   * Processes a load-fund request.
   *
   * <p>Returns {@code 400 Bad Request} for invalid input or business rule violations. Returns
   * {@code 500 Internal Server Error} for unexpected failures.
   *
   * @param dto the request body
   * @return the load result
   * @throws ResponseStatusException if request processing fails
   */
  @PostMapping(path = "rest/funds", consumes = "application/json", produces = "application/json")
  public LoadFundResponseDto loadFund(@RequestBody LoadFundRequestDto dto) {
    try {
      return service.loadFund(dto);
    } catch (RuntimeException e) {
      log.debug("Rejecting load-fund request: {}", e.getMessage());
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    } catch (Exception e) {
      log.error("Failed to process load-fund request.", e);
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
    }
  }
}
