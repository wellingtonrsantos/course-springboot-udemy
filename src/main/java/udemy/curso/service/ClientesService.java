package udemy.curso.service;

import org.springframework.stereotype.Service;
import udemy.curso.model.Cliente;
import udemy.curso.repository.ClientesRepository;

@Service
public class ClientesService {

  private ClientesRepository repository;

  public ClientesService(ClientesRepository repository) {
    this.repository = repository;
  }

  public void salvarCliente(Cliente cliente) {
    validarCliente(cliente);
    this.repository.persistir(cliente);
  }

  public void validarCliente(Cliente cliente) {
    //aplica validacoes
  }
}
