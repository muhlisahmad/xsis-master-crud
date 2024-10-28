CREATE TABLE categories (
  "id" BIGSERIAL NOT NULL,
  "name" VARCHAR(50) UNIQUE NOT NULL,
  "slug" VARCHAR(50) UNIQUE NOT NULL,
  "created_at" TIMESTAMP(6) NOT NULL,
  "updated_at" TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "deleted_at" TIMESTAMP(6),

  CONSTRAINT "pk_categories" PRIMARY KEY ("id")
)