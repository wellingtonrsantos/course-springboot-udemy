package udemy.curso.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import udemy.curso.domain.entity.Cliente;
import udemy.curso.domain.repository.ClienteRepository;
import udemy.curso.rest.dto.ClienteDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements udemy.curso.service.ClienteService {

  private final ClienteRepository clienteRepository;

  @Override
  public ClienteDTO findClienteById(Integer id) {
    return clienteRepository
        .findById(id)
        .map(cliente ->
            ClienteDTO
                .builder()
                .nome(cliente.getNome())
                .cpf(cliente.getCpf())
                .build())
        .orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cliente não encontrado"
            )
        );
  }

  @Override
  public Cliente salvarCliente(ClienteDTO clienteDTO) {
    return clienteRepository.save(
        Cliente.builder()
            .nome(clienteDTO.getNome())
            .cpf(clienteDTO.getCpf())
            .build()
    );
  }

  @Override
  public void deletarClienteById(Integer id) {
    clienteRepository
        .findById(id)
        .map(cliente -> {
          clienteRepository.delete(cliente);
          return cliente;
        })
        .orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cliente não encontrado"
            )
        );
  }

  @Override
  public void atualizarCliente(Integer id, ClienteDTO clienteDTO) {
    clienteRepository.
        findById(id).
        map(clienteExistente -> {
          Cliente clienteAtualizado = new Cliente();
          clienteAtualizado.setId(clienteExistente.getId());
          clienteAtualizado.setNome(clienteDTO.getNome());
          clienteAtualizado.setCpf(clienteDTO.getCpf());

          clienteRepository.save(clienteAtualizado);
          return clienteAtualizado;
        }).orElseThrow(() ->
            new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cliente não encontrado"
            )
        );
  }

  @Override
  public List<Cliente> buscaPorFiltro(ClienteDTO filtro) {
    ExampleMatcher matcher = ExampleMatcher
        .matching()
        .withIgnoreCase()
        .withStringMatcher(
            ExampleMatcher.StringMatcher.CONTAINING
        );

    Cliente clienteComFiltro = new Cliente(filtro.getNome(), filtro.getCpf());

    Example example = Example.of(clienteComFiltro, matcher);
    return clienteRepository.findAll(example);
  }
}
