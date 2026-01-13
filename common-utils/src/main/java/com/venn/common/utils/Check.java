package com.venn.common.utils;

/** Static validation utilities. */
public final class Check {

  private Check() {}

  /**
   * Ensures the given string is non-null and not blank.
   *
   * @param s the value to validate
   * @param errorMessage the exception message if the check fails
   * @return the validated string
   * @throws IllegalArgumentException if {@code s} is {@code null} or blank
   */
  public static String requireNotBlank(String s, String errorMessage) {
    requireTrue(s != null && !s.isBlank(), errorMessage);
    return s;
  }

  /**
   * Ensures the given condition is {@code true}.
   *
   * @param condition the condition to evaluate
   * @param errorMessage the exception message if the check fails
   * @throws IllegalArgumentException if {@code condition} is {@code false}
   */
  public static void requireTrue(boolean condition, String errorMessage) {
    if (!condition) {
      throw new IllegalArgumentException(errorMessage);
    }
  }
}
