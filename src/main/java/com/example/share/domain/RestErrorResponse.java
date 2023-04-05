package com.example.share.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RestErrorResponse {
  private int code;

  private String message;

  public RestErrorResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
