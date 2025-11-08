package com.spring.www.sistema_usuarios.services.servicios;

import com.spring.www.sistema_usuarios.entities.clienteEntity.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    List<Cliente> listado();

    Optional<Cliente> buscarPorId(Long id);

    Cliente crearCliente (Cliente cliente);

    Cliente editarCliente(Cliente cliente);

    void eliminarCliente (Long id);
}
