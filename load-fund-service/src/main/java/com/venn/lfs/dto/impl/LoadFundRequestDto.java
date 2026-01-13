package com.venn.lfs.dto.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.venn.common.utils.Check;
import java.time.OffsetDateTime;
import java.util.regex.Pattern;
import lombok.Getter;

/** Request DTO for a load-fund operation. */
@Getter
public class LoadFundRequestDto {

  private static final Pattern AMOUNT_PATTERN = Pattern.compile("^\\$[0-9]{0,4}\\.[0-9]{0,2}$");

  private final String id;
  private final String customerId;
  private final Double loadAmount;
  private final OffsetDateTime time;

  /**
   * Creates a request from JSON input.
   *
   * @param id the request identifier
   * @param customerId the customer identifier
   * @param loadAmount the dollar amount string
   * @param time the ISO-8601 timestamp
   * @throws IllegalArgumentException if any field is invalid
   */
  @JsonCreator
  public LoadFundRequestDto(
      @JsonProperty("id") String id,
      @JsonProperty("customer_id") String customerId,
      @JsonProperty("load_amount") String loadAmount,
      @JsonProperty("time") String time) {
    this.id = Check.requireNotBlank(id, "id must not be blank.");
    this.customerId = Check.requireNotBlank(customerId, "customer_id must not be blank.");

    Check.requireTrue(AMOUNT_PATTERN.matcher(loadAmount).matches(), "invalid load amount.");
    this.loadAmount = Double.valueOf(loadAmount.substring(1));
    this.time = OffsetDateTime.parse(time);
  }
}
