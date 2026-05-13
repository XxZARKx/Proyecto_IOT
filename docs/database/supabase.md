# Supabase

1. Crea un proyecto en Supabase.
2. Copia la cadena JDBC del pooler o conexion directa.
3. En Render, define `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`.
4. Usa `?sslmode=require` en la URL JDBC.
5. Deja `JPA_DDL_AUTO=update` para demo o ejecuta `database/schema.sql` manualmente.
