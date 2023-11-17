package udemy.curso.service;

import udemy.curso.domain.entity.Pedido;
import udemy.curso.domain.enums.StatusPedido;
import udemy.curso.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {
  Pedido salvar(PedidoDTO pedidoDTO);
  Optional<Pedido> obterPedidoCompleto(Integer id);
  void atualizaStatus(Integer id, StatusPedido statusPedido);
}
