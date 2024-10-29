package com.xsis.master.crud.xsis_master_crud.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.text.Normalizer;

public class SlugValidator implements ConstraintValidator<ValidSlug, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isEmpty()) {
      return false;
    }

    String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
      .replaceAll("\\p{InCombiningDiacriticalMarks}", "");
    String slug = normalized.toLowerCase()
      .replace("&", "and")
      .replace("@", "at")
      .replaceAll("[^a-z0-9\\s-]", "")
      .replaceAll("\\s+", "-")
      .replaceAll("-{2,}", "-")
      .replaceAll("^-|-$", "");

    return slug.equals(value);
  }
  
}
