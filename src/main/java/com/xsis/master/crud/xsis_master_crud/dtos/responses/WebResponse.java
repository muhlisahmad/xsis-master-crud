package com.xsis.master.crud.xsis_master_crud.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class WebResponse<T> {
  public WebResponse(String status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }
  private String status;
  private String message;
  private T data;
  private Pagination pagination;
}
