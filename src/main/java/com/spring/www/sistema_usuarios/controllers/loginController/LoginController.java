package com.spring.www.sistema_usuarios.controllers.loginController;

import com.spring.www.sistema_usuarios.repositories.loginRepository.UsuarioLoginRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;


@Controller
@RequestMapping("/api")
public class LoginController {

    private final UsuarioLoginRepository usuarioLoginRepository;

    public LoginController(UsuarioLoginRepository usuarioLoginRepository) {
        this.usuarioLoginRepository = usuarioLoginRepository;
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody Map<String, String> body, HttpSession session) {
        try {

            String username = body.get("username").trim();
            String password = body.get("password").trim();


            String usernameEncoded = Base64.getEncoder().encodeToString(username.getBytes());
            String passwordEncoded = Base64.getEncoder().encodeToString(password.getBytes());

            var usuarioLogin = usuarioLoginRepository.findByUsernameAndPassword(usernameEncoded, passwordEncoded);

            if (usuarioLogin.isPresent()) {
                // 4️⃣ Guardamos la sesión y redirigimos al formulario de clientes
                session.setAttribute("usuario", usuarioLogin.get().getUsername());
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Inicio de sesión exitoso",
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
