package com.spring.www.sistema_usuarios.services.implementaciones;

import com.spring.www.sistema_usuarios.entities.clienteEntity.Cliente;
import com.spring.www.sistema_usuarios.repositories.clienteRepository.ClienteRepository;
import com.spring.www.sistema_usuarios.services.servicios.ClienteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;

    ClienteServiceImpl(ClienteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cliente> listado() {
        return repository.findAll();
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Override
    public Cliente crearCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public Cliente editarCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public void eliminarCliente(Long id) {
         repository.deleteById(id);
    }
}
