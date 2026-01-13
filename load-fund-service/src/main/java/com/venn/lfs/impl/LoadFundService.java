package com.venn.lfs.impl;

import com.venn.lfs.dto.impl.LoadFundRequestDto;
import com.venn.lfs.dto.impl.LoadFundResponseDto;
import com.venn.lfs.impl.repository.LoadFundRepository;

/** Service for processing load-fund requests. */
public class LoadFundService {

  private LoadFundRepository repository;

  /**
   * Creates the service with the given repository.
   *
   * @param repository the backing repository
   */
  public LoadFundService(LoadFundRepository repository) {
    this.repository = repository;
  }

  /**
   * Executes a load-fund request.
   *
   * @param dto the request
   * @return the load result
   */
  public LoadFundResponseDto loadFund(LoadFundRequestDto dto) {
    return new LoadFundResponseDto(dto.getId(), dto.getCustomerId(), repository.loadFund(dto));
  }
}
