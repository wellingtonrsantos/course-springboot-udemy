package udemy.curso.rest.controller;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import udemy.curso.domain.entity.Cliente;
import udemy.curso.rest.dto.ClienteDTO;
import udemy.curso.service.ClienteService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Api("Api Clientes")
public class ClienteController {

  private final ClienteService clienteService;

  public ClienteController(ClienteService clienteService) {
    this.clienteService = clienteService;
  }

  @GetMapping("{id}")
  @ApiOperation("Obter detalhes de um cliente")
  @ApiResponses({
      @ApiResponse(code = 200, message = "Cliente encontrado"),
      @ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado")
  })
  public ClienteDTO getClienteByID(
      @PathVariable
      @ApiParam("Id do cliente") Integer id) {

    return clienteService.findClienteById(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation("Salva um novo cliente")
  @ApiResponses({
      @ApiResponse(code = 201, message = "Cliente salvo com sucesso"),
      @ApiResponse(code = 400, message = "Erro de validacao")
  })
  public Cliente save(@RequestBody @Valid ClienteDTO clienteDTO) {
    return clienteService.salvarCliente(clienteDTO);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable Integer id) {
    clienteService.deletarClienteById(id);
  }

  @PutMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void update(@PathVariable Integer id,
                     @RequestBody @Valid ClienteDTO clienteDTO
  ) {
    clienteService.atualizarCliente(id, clienteDTO);
  }

  @GetMapping
  public List<Cliente> buscaPorFiltro(@RequestBody ClienteDTO filtro) {
    return clienteService.buscaPorFiltro(filtro);
  }
}
