package udemy.curso.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import udemy.curso.domain.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
