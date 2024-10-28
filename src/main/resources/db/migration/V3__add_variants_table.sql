CREATE TABLE variants (
  "id" BIGSERIAL NOT NULL,
  "product_id" BIGINT NOT NULL,
  "name" VARCHAR(150) UNIQUE NOT NULL,
  "slug" VARCHAR(150) UNIQUE NOT NULL,
  "stock" BIGINT NOT NULL,
  "price" BIGINT NOT NULL,
  "description" TEXT NOT NULL,
  "created_at" TIMESTAMP(6) NOT NULL,
  "updated_at" TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "deleted_at" TIMESTAMP(6),

  CONSTRAINT "pk_variants" PRIMARY KEY ("id"),
  CONSTRAINT "fk_product" FOREIGN KEY ("product_id") REFERENCES master.products("id") 
    ON DELETE CASCADE
)