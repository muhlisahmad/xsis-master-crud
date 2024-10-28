CREATE TABLE products (
  "id" BIGSERIAL NOT NULL,
  "category_id" BIGINT NOT NULL,
  "name" VARCHAR(50) UNIQUE NOT NULL,
  "slug" VARCHAR(50) UNIQUE NOT NULL,
  "created_at" TIMESTAMP(6) NOT NULL,
  "updated_at" TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "deleted_at" TIMESTAMP(6),

  CONSTRAINT "pk_products" PRIMARY KEY ("id"),
  CONSTRAINT "fk_category" FOREIGN KEY ("category_id") REFERENCES master.categories("id") 
    ON DELETE CASCADE
)