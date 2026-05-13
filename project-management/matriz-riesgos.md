# Matriz de riesgos

| Riesgo | Mitigacion |
| --- | --- |
| Credenciales Supabase incorrectas | Usar `.env.example` y probar JDBC antes de Render. |
| CORS en despliegue | Configurar `CORS_ALLOWED_ORIGINS` con dominio Vercel. |
| JWT debil | Usar `JWT_SECRET` largo y privado. |
| Demo sin datos | DataSeeder crea usuarios, productos, movimientos, dispositivos y alertas. |
