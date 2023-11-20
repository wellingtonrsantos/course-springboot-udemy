package udemy.curso.service;

import udemy.curso.domain.entity.Cliente;
import udemy.curso.rest.dto.ClienteDTO;

import java.util.List;

public interface ClienteService {
  ClienteDTO findClienteById(Integer id);
  Cliente salvarCliente(ClienteDTO clienteDTO);
  void deletarClienteById(Integer id);
  void atualizarCliente(Integer id, ClienteDTO clienteDTO);
  List<Cliente> buscaPorFiltro(ClienteDTO filtro);
}
