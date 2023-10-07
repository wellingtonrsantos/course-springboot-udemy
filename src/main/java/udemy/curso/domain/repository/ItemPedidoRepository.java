package udemy.curso.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udemy.curso.domain.entity.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
