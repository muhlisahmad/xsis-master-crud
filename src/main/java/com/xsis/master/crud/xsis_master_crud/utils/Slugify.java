package com.xsis.master.crud.xsis_master_crud.utils;

import java.text.Normalizer;

public class Slugify {
  public Slugify() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static String toSlug(String input) {
    String slug = input.toLowerCase();

    slug = slug.replace("&", "and").replace("@", "at");

    slug = Normalizer.normalize(slug, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

    slug = slug.replaceAll("[^a-z0-9\\s-]", "-");

    slug = slug.replaceAll("\\s+", "-");

    slug = slug.replaceAll("-{2,}", "-");

    slug = slug.replaceAll("^-|-$", "");

    return slug;
  }
}
