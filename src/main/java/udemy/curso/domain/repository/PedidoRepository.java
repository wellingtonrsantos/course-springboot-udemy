package udemy.curso.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udemy.curso.domain.entity.Cliente;
import udemy.curso.domain.entity.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
  List<Pedido> findByCliente(Cliente cliente);
}
