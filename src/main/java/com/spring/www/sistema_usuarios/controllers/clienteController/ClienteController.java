package com.spring.www.sistema_usuarios.controllers.clienteController;

import com.spring.www.sistema_usuarios.entities.clienteEntity.Cliente;
import com.spring.www.sistema_usuarios.services.implementaciones.ClienteServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteServiceImpl service;

    ClienteController(ClienteServiceImpl service) {
        this.service = service;
    }


    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", service.listado());
        return "clientes/lista";
    }


    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute Cliente cliente) {
        service.crearCliente(cliente);
        return "redirect:/clientes";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        service.eliminarCliente(id);
        return "redirect:/clientes";
    }
}
