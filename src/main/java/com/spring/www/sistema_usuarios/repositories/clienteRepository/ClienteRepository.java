package com.spring.www.sistema_usuarios.repositories.clienteRepository;

import com.spring.www.sistema_usuarios.entities.clienteEntity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
