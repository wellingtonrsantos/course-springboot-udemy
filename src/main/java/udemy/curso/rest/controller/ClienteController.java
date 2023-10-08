package udemy.curso.rest.controller;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import udemy.curso.domain.entity.Cliente;
import udemy.curso.domain.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/clientes")
public class ClienteController {

  private final ClienteRepository clienteRepository;

  public ClienteController(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  //  @RequestMapping(
//      value = "/hello/{nome}",
//      method = RequestMethod.GET,
//      consumes = {"application/json", "application/xml"},
//      produces = {"application/json", "application/xml"}
//  )
//  @ResponseBody
//  public String helloCliente(@PathVariable("nome") String nomeCliente) {
//    return String.format("Hello %s ", nomeCliente);
//  }


//  @GetMapping("{id}")
//  @ResponseBody
//  public ResponseEntity<Cliente> getClienteByID(@PathVariable Integer id) {
//    Optional<Cliente> cliente = clienteRepository.findById(id);
//
//    return cliente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//
//  }

  @GetMapping("{id}")
  @ResponseBody
  public ResponseEntity getClienteByID(@PathVariable Integer id) {
    Optional<Cliente> cliente = clienteRepository.findById(id);

    if (cliente.isPresent()) {
      return ResponseEntity.ok(cliente.get());
    }

    return ResponseEntity.notFound().build();
  }

  @PostMapping
  @ResponseBody
  public ResponseEntity save(@RequestBody Cliente cliente) {
    Cliente clienteSalvo = clienteRepository.save(cliente);
    return ResponseEntity.ok(clienteSalvo);
  }

  @DeleteMapping("{id}")
  @ResponseBody
  public ResponseEntity delete(@PathVariable Integer id) {
    Optional<Cliente> cliente = clienteRepository.findById(id);

    if (cliente.isPresent()) {
      clienteRepository.delete(cliente.get());
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.notFound().build();
  }

  @PutMapping("{id}")
  @ResponseBody
  public ResponseEntity update(@PathVariable Integer id,
                               @RequestBody Cliente cliente) {
    return clienteRepository.
        findById(id).
        map(clienteExistente -> {
          cliente.setId(clienteExistente.getId());
          clienteRepository.save(cliente);
          return ResponseEntity.noContent().build();
        }).orElseGet(
            () -> ResponseEntity.notFound().build()
        );
  }

  @GetMapping
  public ResponseEntity find(Cliente filtro) {
    ExampleMatcher matcher = ExampleMatcher.matching().
        withIgnoreCase().
        withStringMatcher(
            ExampleMatcher.StringMatcher.CONTAINING
        );

    Example example = Example.of(filtro, matcher);
    List<Cliente> clienteList = clienteRepository.findAll(example);
    return ResponseEntity.ok(clienteList);

  }

}
