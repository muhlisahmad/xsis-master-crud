package com.xsis.master.crud.xsis_master_crud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;


@Component
@Getter
public class ApplicationProperties {
  @Value("${spring.datasource.url}")
  private String url;
}
