package udemy.curso.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import udemy.curso.domain.entity.Cliente;
import udemy.curso.domain.entity.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
  List<Pedido> findByCliente(Cliente cliente);

  @Query("select p from Pedido p left join fetch p.itens where p.id = :id")
  Optional<Pedido> findByIdFetchItens(@Param("id") Integer id);
}
