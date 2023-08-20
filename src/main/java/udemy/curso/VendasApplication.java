package udemy.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import udemy.curso.domain.entity.Cliente;
import udemy.curso.domain.repositorio.ClienteRepository;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

  @Bean
  public CommandLineRunner init(@Autowired ClienteRepository clienteRepository) {
    return args -> {
      System.out.println("Salvando Clientes");
      clienteRepository.salvar(new Cliente("Wellington"));
      clienteRepository.salvar(new Cliente("Outro Cliente"));
      List<Cliente> todosClientes = clienteRepository.obterTodos();
      todosClientes.forEach(System.out::println);

      System.out.println("Atualizando Clientes");
      todosClientes.forEach(c -> {
        c.setNome(c.getNome() + " atualizado");
        clienteRepository.atualizar(c);
      });

      todosClientes = clienteRepository.obterTodos();
      todosClientes.forEach(System.out::println);

      System.out.println("Buscando Clientes");
      clienteRepository.buscarPorNome("Cli").forEach(System.out::println);

      System.out.println("Deletando Clientes");
      clienteRepository.obterTodos().forEach(clienteRepository::deletar);
      todosClientes = clienteRepository.obterTodos();

      if (todosClientes.isEmpty()) {
        System.out.println("Nenhum Cliente Encontrado");
      } else {
        todosClientes.forEach(System.out::println);
      }
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(VendasApplication.class, args);
  }
}
