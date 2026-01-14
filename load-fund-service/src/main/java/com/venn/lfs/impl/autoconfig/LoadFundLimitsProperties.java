package com.venn.lfs.impl.autoconfig;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/** Configuration properties defining load-fund limits. */
@Getter
@Setter
public class LoadFundLimitsProperties {
  private int dailyCount = 3;
  private BigDecimal dailyAmount = new BigDecimal(5000);
  private BigDecimal weeklyAmount = new BigDecimal(20000);
}
