package com.xsis.master.crud.xsis_master_crud.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xsis.master.crud.xsis_master_crud.entities.Variant;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Long> {
  @Modifying
  @Query(
    value = """
      insert into master.variants (
        name, slug, product_id, description, price, stock, created_at
      ) 
      values (?1, ?2, (
        select id from master.products as p 
        where p.slug = ?3
        and p.deleted_at is null
        ), ?4, ?5, ?6, now(), now()
      )
    """,
    nativeQuery = true
  )
  void insertVariant(String name, String slug, String productSlug, String description, Long price, Long stock);

  @Query(
    value = """
      select
        v.name,
        v.slug,
        p.name as product,
        c.name as category,
        v.description,
        v.stock,
        v.price
      from master.variants as v
      join master.products as p
      on v.product_id = p.id
      join master.categories as c
      on p.category_id = c.id
      where v.deleted_at is null
    """,
    nativeQuery = true
  )
  Page<Object[]> findAllVariants(Pageable paging);

  @Query(
    value = """
      select
        v.name,
        v.slug,
        p.name as product,
        c.name as category,
        v.description,
        v.stock,
        v.price
      from master.variants as v
      join master.products as p
      on v.product_id = p.id
      join master.categories as c
      on p.category_id = c.id
      where v.deleted_at is null
      and v.slug = ?1
    """,
    nativeQuery = true
  )
  Object[] findBySlug(String slug);
}
