package com.xsis.master.crud.xsis_master_crud.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xsis.master.crud.xsis_master_crud.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
  @Modifying
  @Query(
    value = "insert into master.categories (name, slug, created_at) values (?1, ?2, now())",
    nativeQuery = true
    )
  void insertCategory(String name, String slug);

  @Query(
    value = """
      select
        c.name,
        c.slug
      from master.categories as c
      where c.deleted_at is null
    """, 
    nativeQuery = true
    )
  Page<Object[]> findAllCategories(Pageable paging);

  @Query(
    value = """
      select
        c.name,
        c.slug
      from master.categories as c
      where c.deleted_at is null
      and slug = ?1
    """, 
    nativeQuery = true
    )
  Object[] findBySlug(String slug);
}
