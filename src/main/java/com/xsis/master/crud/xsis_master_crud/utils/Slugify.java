package com.xsis.master.crud.xsis_master_crud.utils;

import java.text.Normalizer;
import java.util.Set;

import com.xsis.master.crud.xsis_master_crud.validations.ValidSlug;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.Getter;

public class Slugify {
  public Slugify() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  @Getter
  private static class SlugWrapper {
    @ValidSlug
    private final String slug;

    public SlugWrapper(String slug) {
      this.slug = slug;
    }
  }

  public static String validateSlug(String slug, Validator validator) {
    SlugWrapper slugWrapper = new SlugWrapper(slug);
    Set<ConstraintViolation<SlugWrapper>> violations = validator.validate(slugWrapper);
    if (violations.size() != 0) {
      throw new ConstraintViolationException(violations);
    } else {
      return slug;
    }
  }

  public static String toSlug(String input) {
    String slug = input.toLowerCase();

    slug = slug.replace("&", "and").replace("@", "at");

    slug = Normalizer.normalize(slug, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

    slug = slug.replaceAll("[^a-z0-9\\s-]", "");

    slug = slug.replaceAll("\\s+", "-");

    slug = slug.replaceAll("-{2,}", "-");

    slug = slug.replaceAll("^-|-$", "");

    return slug;
  }
}
