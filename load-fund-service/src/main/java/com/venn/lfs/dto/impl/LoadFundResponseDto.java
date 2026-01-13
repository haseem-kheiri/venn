package com.venn.lfs.dto.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Response DTO for a load-fund request. */
public class LoadFundResponseDto {

  private final String id;
  private final String customerId;
  private final boolean accepted;

  /**
   * Creates a response.
   *
   * @param id the request identifier
   * @param customerId the customer identifier
   * @param accepted whether the load was accepted
   */
  @JsonCreator
  public LoadFundResponseDto(
      @JsonProperty("id") String id,
      @JsonProperty("customer_id") String customerId,
      @JsonProperty("accepted") boolean accepted) {
    this.id = id;
    this.customerId = customerId;
    this.accepted = accepted;
  }

  /** return the request identifier. */
  public String getId() {
    return id;
  }

  /** return the customer identifier. */
  public String getCustomerId() {
    return customerId;
  }

  /** return whether the load was accepted. */
  public boolean isAccepted() {
    return accepted;
  }
}
