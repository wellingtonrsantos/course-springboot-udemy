package udemy.curso.rest.controller;

import static org.springframework.http.HttpStatus.*;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import udemy.curso.domain.entity.ItemPedido;
import udemy.curso.domain.entity.Pedido;
import udemy.curso.domain.enums.StatusPedido;
import udemy.curso.rest.dto.AtualizacaoStatusPedidoDTO;
import udemy.curso.rest.dto.InformacaoItemPedidoDTO;
import udemy.curso.rest.dto.InformacoesPedidoDTO;
import udemy.curso.rest.dto.PedidoDTO;
import udemy.curso.service.PedidoService;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/pedidos")
public class PedidoController {

  private final PedidoService service;

  public PedidoController(PedidoService service) {
    this.service = service;
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public Integer save(@RequestBody @Valid PedidoDTO dto) {
    Pedido pedido = service.salvar(dto);
    return pedido.getId();
  }

  @GetMapping("{id}")
  public InformacoesPedidoDTO getById(@PathVariable Integer id) {
    return service
        .obterPedidoCompleto(id)
        .map(p -> converter(p))
        .orElseThrow(() ->
            new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));
  }

  @PatchMapping("{id}")
  @ResponseStatus(NO_CONTENT)
  public void updateStatus(@RequestBody AtualizacaoStatusPedidoDTO dto, @PathVariable Integer id) {
    service.atualizaStatus(id, StatusPedido.valueOf(dto.getNovoStatus()));
  }

  private InformacoesPedidoDTO converter(Pedido pedido) {
    return InformacoesPedidoDTO
        .builder()
        .codigo(pedido.getId())
        .dataPediddo(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
        .cpf(pedido.getCliente().getCpf())
        .nomeCliente(pedido.getCliente().getNome())
        .total(pedido.getTotal())
        .status(pedido.getStatus().name())
        .items(converter(pedido.getItens()))
        .build();
  }

  private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens) {
    if (CollectionUtils.isEmpty(itens)) {
      return Collections.emptyList();
    }

    return itens.stream().map(
        item -> InformacaoItemPedidoDTO.builder()
          .quantidade(item.getQuantidade())
          .precoUnitario(item.getProduto().getPreco())
          .descricaoProduto(item.getProduto().getDescricao())
          .build()
    ).collect(Collectors.toList());
  }
}
