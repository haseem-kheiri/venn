package com.venn.lfs.impl.repository;

/** Runtime exception for load-fund persistence failures. */
@SuppressWarnings("serial")
public class LoadFundException extends RuntimeException {

  /**
   * Creates an exception with the given cause.
   *
   * @param cause the underlying failure
   */
  public LoadFundException(Throwable cause) {
    super(cause);
  }
}
