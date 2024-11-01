package com.xsis.master.crud.xsis_master_crud.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xsis.master.crud.xsis_master_crud.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
  @Modifying
  @Query(
    value = """
      insert into master.products (name, slug, category_id, created_at) 
      values (?1, ?2, (
        select id from master.categories as c 
        where c.slug = ?3
        and c.deleted_at is null
        ),
        now()
      )
    """,
    nativeQuery = true
  )
  void insertProduct(String name, String slug, String categorySlug);

  @Query(
    value = """
      select
        p.name,
        p.slug,
        c.name as category
      from master.products as p
      join master.categories as c
      on p.category_id = c.id
      where p.deleted_at is null
    """,
    nativeQuery = true
  )
  Page<Object[]> findAllProducts(Pageable paging);

  @Query(
    value = """
      select
        p.name,
        p.slug,
        c.name as category
      from master.products as p
      join master.categories as c
      on p.category_id = c.id
      where p.deleted_at is null
      and p.slug = ?1
    """,
    nativeQuery = true
  )
  Object[] findBySlug(String slug);

  @Query(
    value = """
      select
        p.name,
        p.slug,
        c.name as category
      from master.products as p
      join master.categories as c
      on p.category_id = c.id
      where p.deleted_at is null
      and c.slug = ?1
    """,
    nativeQuery = true
  )
  Page<Object[]> findProductsByCategory(String category, Pageable paging);

  @Modifying
  @Query(
    value = """
      update master.products as p
      set
        name = ?1,
        slug = ?2,
        category_id = (
          select id from master.categories as c 
          where c.slug = ?3
          and c.deleted_at is null
        ),
        updated_at = now()
      where p.slug = ?4
    """,
    nativeQuery = true
  )
  void updateProductBySlug(String name, String slug, String category, String slugForQuery);

  @Modifying
  @Query(
    value = """
      update master.products as p
      set deleted_at = now()
      where p.slug = ?1
    """,
    nativeQuery = true
  )
  void deleteProductBySlug(String slug);
}
