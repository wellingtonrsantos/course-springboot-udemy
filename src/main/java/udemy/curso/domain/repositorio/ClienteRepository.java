package udemy.curso.domain.repositorio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import udemy.curso.domain.entity.Cliente;

import java.util.List;

@Repository
public class ClienteRepository {

  private static final String INSERT = "insert into CLIENTE (nome) values (?);";
  private static final String SELECT_ALL = "SELECT * FROM CLIENTE;";
  private static final String SELECT_POR_NOME = "SELECT * FROM CLIENTE WHERE nome LIKE ?";
  private static final String UPDATE = "UPDATE CLIENTE SET nome = ? WHERE id = ?;";
  private static final String DELETE = "DELETE FROM CLIENTE WHERE id = ?;";

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public Cliente salvar(Cliente cliente) {
    jdbcTemplate.update(INSERT, cliente.getNome());
    return cliente;
  }

  public Cliente atualizar(Cliente cliente) {
    jdbcTemplate.update(UPDATE, cliente.getNome(), cliente.getId());
    return cliente;
  }

  public void deletar(Cliente cliente) {
    deletar(cliente.getId());
  }

  public void deletar(Integer id) {
    jdbcTemplate.update(DELETE, id);
  }

  public List<Cliente> buscarPorNome(String nome) {
    return jdbcTemplate.query(
        SELECT_POR_NOME,
        new Object[]{"%" + nome + "%"},
        (resultSet, i) ->
        new Cliente(
            resultSet.getInt("id"),
            resultSet.getString("nome")
        )
    );
  }

  public List<Cliente> obterTodos() {
    return jdbcTemplate.query(
        SELECT_ALL,
        (resultSet, i) ->
        new Cliente(
            resultSet.getInt("id"),
            resultSet.getString("nome")
        )
    );
  }
}
