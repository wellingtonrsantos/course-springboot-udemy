package udemy.curso.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import udemy.curso.domain.entity.Cliente;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

//  List<Cliente> findByNomeLike(String nome);

  //@Query(value = "select c from Cliente c where c.nome like :nome")
  @Query(value = "select * from cliente c where c.nome like '%:nome%'", nativeQuery = true)
  List<Cliente> encontrarPorNome(@Param("nome") String nome);

//  List<Cliente> findByNomeOrIdOrderById(String nome, Integer id);
//
//  Cliente findOneByNome(String nome);

  @Query("delete from Cliente c where c.nome = :nome")
  @Modifying
  void deleteByNome(@Param("nome") String nome);

//  void deleteByNome(String nome);
  boolean existsByNome(String nome);

  @Query("select c from Cliente c left join fetch c.pedidos where c.id = :id")
  Cliente findClienteFetchPedidos(@Param("id") Integer id);

}