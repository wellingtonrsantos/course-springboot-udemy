package udemy.curso.repository;

import org.springframework.stereotype.Repository;
import udemy.curso.model.Cliente;

@Repository
public class ClientesRepository {
  public void persistir(Cliente cliente) {
    //salva no bd
  }
}
