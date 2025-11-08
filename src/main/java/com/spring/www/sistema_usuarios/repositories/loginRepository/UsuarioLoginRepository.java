package com.spring.www.sistema_usuarios.repositories.loginRepository;

import com.spring.www.sistema_usuarios.entities.loginEntity.UsuarioLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioLoginRepository extends JpaRepository<UsuarioLogin, Long> {

    @Query("SELECT u FROM UsuarioLogin u WHERE TRIM(u.username) = :username AND TRIM(u.password) = :password")
    Optional<UsuarioLogin> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
