package com.spring.www.sistema_usuarios.services.implementaciones;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JwtFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String path = request.getRequestURI();
        String method = request.getMethod();
        String authHeader = request.getHeader("Authorization");

        log.info("🧠 [JWT-FILTER] Petición entrante: {} {}", method, path);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            log.info("🔐 Token recibido: {}", token);

            if (jwtService.validarToken(token)) {
                String usuario = jwtService.extraerUsername(token);
                log.info("✅ Token válido → Usuario: {}", usuario);
            } else {
                log.warn("⛔ Token inválido o expirado → Rechazando acceso a {}", path);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token inválido o expirado");
                return;
            }
        } else {
            log.debug("🚫 No se encontró token JWT para la ruta {}", path);
        }

        chain.doFilter(req, res);
    }
}
