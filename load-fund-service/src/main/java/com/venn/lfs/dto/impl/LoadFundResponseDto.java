package com.venn.lfs.dto.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/** Response DTO for a load-fund request. */
@EqualsAndHashCode
@Getter
public class LoadFundResponseDto {

  @JsonProperty("id")
  private final String id;

  @JsonProperty("customer_id")
  private final String customerId;

  @JsonProperty("accepted")
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
}
