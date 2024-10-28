package com.xsis.master.crud.xsis_master_crud.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Pagination {
  private Integer page;
  private Integer limit;
  private Integer total;
}
