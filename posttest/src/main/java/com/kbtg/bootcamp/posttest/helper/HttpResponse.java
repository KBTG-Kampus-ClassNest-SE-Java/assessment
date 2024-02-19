package com.kbtg.bootcamp.posttest.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class HttpResponse {

  private Boolean status;
  private String message;
  private Object data = null;

  public HttpResponse() {

  }
}