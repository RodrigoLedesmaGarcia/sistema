package com.spring.www.sistema_usuarios.controllers.loginController;

import com.spring.www.sistema_usuarios.repositories.loginRepository.UsuarioLoginRepository;

import com.spring.www.sistema_usuarios.services.implementaciones.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class LoginController {

    private final UsuarioLoginRepository usuarioLoginRepository;
    private final JwtService jwtService;

    public LoginController(UsuarioLoginRepository usuarioLoginRepository, JwtService jwtService) {
        this.usuarioLoginRepository = usuarioLoginRepository;
        this.jwtService = jwtService;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username").trim();
            String password = body.get("password").trim();

            // codificados en base64 (manteniendo tu lógica)
            String usernameEncoded = Base64.getEncoder().encodeToString(username.getBytes());
            String passwordEncoded = Base64.getEncoder().encodeToString(password.getBytes());

            var usuarioLogin = usuarioLoginRepository.findByUsernameAndPassword(usernameEncoded, passwordEncoded);

            if (usuarioLogin.isPresent()) {
                // Genera el token JWT
                String token = jwtService.generarToken(usuarioLogin.get().getUsername());

                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Inicio de sesión exitoso",
                        "token", token,
                        "redirect", "/clientes/nuevo"
                ));
            } else {
                return ResponseEntity.status(401).body(Map.of(
                        "success", false,
                        "message", "Credenciales inválidas"
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "Error interno del servidor"
            ));
        }
    }
}
