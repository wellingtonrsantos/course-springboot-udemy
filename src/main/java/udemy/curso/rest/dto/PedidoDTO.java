package udemy.curso.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import udemy.curso.validation.NotEmptyList;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDTO {
  @NotNull(message = "{campo.codigo-cliente-obrigatorio}")
  private Integer cliente;

  @NotNull(message = "{campo.total-pedido-obrigatorio}")
  private BigDecimal total;

  @NotEmptyList(message = "{campo.items-pedido.obrigatorio}")
  private List<ItemPedidoDTO> items;
}
