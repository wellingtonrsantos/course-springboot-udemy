package udemy.curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import udemy.curso.domain.entity.Cliente;
import udemy.curso.domain.entity.Pedido;
import udemy.curso.domain.repository.ClienteRepository;
import udemy.curso.domain.repository.PedidoRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class VendasApplication {

  @Bean
  public CommandLineRunner init(@Autowired ClienteRepository clienteRepository,
                                @Autowired PedidoRepository pedidoRepository
  ) {
    return args -> {
      System.out.println("Salvando Clientes");

      Cliente fulano = new Cliente("Fulano");
      clienteRepository.save(fulano);

      Pedido p = new Pedido();
      p.setCliente(fulano);
      p.setDataPedido(LocalDate.now());
      p.setTotal(BigDecimal.valueOf(100));

      pedidoRepository.save(p);

//      Cliente cliente = clienteRepository.findClienteFetchPedidos(fulano.getId());
//      System.out.println(cliente);
//      System.out.println(cliente.getPedidos());
      pedidoRepository.findByCliente(fulano).forEach(System.out::println);
    };
  }

  public static void main(String[] args) {
    SpringApplication.run(VendasApplication.class, args);
  }
}
