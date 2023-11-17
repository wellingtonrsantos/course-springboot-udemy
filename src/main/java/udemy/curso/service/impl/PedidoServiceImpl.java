package udemy.curso.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import udemy.curso.domain.entity.Cliente;
import udemy.curso.domain.entity.ItemPedido;
import udemy.curso.domain.entity.Pedido;
import udemy.curso.domain.entity.Produto;
import udemy.curso.domain.enums.StatusPedido;
import udemy.curso.domain.exception.PedidoNaoEncontradoException;
import udemy.curso.domain.exception.RegraNegocioException;
import udemy.curso.domain.repository.ClienteRepository;
import udemy.curso.domain.repository.ItemPedidoRepository;
import udemy.curso.domain.repository.PedidoRepository;
import udemy.curso.domain.repository.ProdutoRepository;
import udemy.curso.rest.dto.ItemPedidoDTO;
import udemy.curso.rest.dto.PedidoDTO;
import udemy.curso.service.PedidoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

  private final PedidoRepository repository;
  private final ClienteRepository clienteRepository;
  private final ProdutoRepository produtoRepository;
  private final ItemPedidoRepository itemPedidoRepository;

  @Override
  @Transactional
  public Pedido salvar(PedidoDTO pedidoDTO) {

    Cliente cliente = clienteRepository
        .findById(pedidoDTO.getCliente())
        .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

    Pedido pedido = new Pedido();
    pedido.setCliente(cliente);
    pedido.setDataPedido(LocalDate.now());
    pedido.setTotal(pedidoDTO.getTotal());
    pedido.setStatus(StatusPedido.REALIZADO);

    List<ItemPedido> itemsPedido = converterItems(pedido, pedidoDTO.getItems());
    repository.save(pedido);
    itemPedidoRepository.saveAll(itemsPedido);
    pedido.setItens(itemsPedido);
    return pedido;
  }

  private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
    if (items.isEmpty()) {
      throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
    }

    return items
        .stream()
        .map(dto -> {
          Produto produto = produtoRepository
              .findById(dto.getProduto())
              .orElseThrow(
                  () -> new RegraNegocioException("Código de produto inválido: " + dto.getProduto())
              );

          ItemPedido itemPedido = new ItemPedido();
          itemPedido.setQuantidade(dto.getQuantidade());
          itemPedido.setPedido(pedido);
          itemPedido.setProduto(produto);

          return itemPedido;
        })
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Pedido> obterPedidoCompleto(Integer id) {

    return repository.findByIdFetchItens(id);
  }

  @Override
  @Transactional
  public void atualizaStatus(Integer id, StatusPedido statusPedido) {
    repository
        .findById(id)
        .map(pedido -> {
          pedido.setStatus(statusPedido);
          return repository.save(pedido);
        }).orElseThrow(() -> new PedidoNaoEncontradoException());
  }
}
