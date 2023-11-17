package udemy.curso.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ITEM_PEDIDO")
public class ItemPedido {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "pedido_id")
  private Pedido pedido;

  @ManyToOne
  @JoinColumn(name = "produto_id")
  private Produto produto;

  @Column(name = "quantidade")
  private Integer quantidade;

}
