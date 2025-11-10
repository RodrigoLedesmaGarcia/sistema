package com.spring.www.sistema_usuarios.services.implementaciones;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private static final String SECRET_KEY =
            "clave-super-secreta-para-mi-sistema-de-usuarios-2025-123456789";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hora

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // 🔹 Genera un JWT firmado y muestra sus claims
    public String generarToken(String username) {
        Date issuedAt = new Date();
        Date expiration = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        log.info("🧩 Generando token JWT...");
        log.info("   Usuario: {}", username);
        log.info("   Emitido: {}", issuedAt);
        log.info("   Expira: {}", expiration);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        log.debug("🔐 Token JWT generado: {}", token);
        return token;
    }

    // 🔹 Valida la firma y expiración
    public boolean validarToken(String token) {
        try {
            Jws<Claims> parsedToken = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            Claims body = parsedToken.getBody();
            log.info("✅ Token válido para usuario: {}", body.getSubject());
            log.info("   Emitido en: {}", body.getIssuedAt());
            log.info("   Expira en: {}", body.getExpiration());

            return true;
        } catch (ExpiredJwtException e) {
            log.warn("⚠️ Token expirado: {}", e.getMessage());
        } catch (JwtException e) {
            log.error("❌ Token inválido: {}", e.getMessage());
        }
        return false;
    }

    // 🔹 Extrae el usuario (subject)
    public String extraerUsername(String token) {
        try {
            String username = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            log.debug("🧠 Usuario extraído del token: {}", username);
            return username;
        } catch (JwtException e) {
            log.error("❌ No se pudo extraer el usuario: {}", e.getMessage());
            return null;
        }
    }

    // 🔹 (Opcional) Método para ver el contenido completo del token en JSON
    public Map<String, Object> decodificarToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            log.info("📜 Decodificación del token:");
            claims.forEach((k, v) -> log.info("   {} = {}", k, v));
            return claims;
        } catch (JwtException e) {
            log.error("❌ Error al decodificar token: {}", e.getMessage());
            return Map.of();
        }
    }
}
