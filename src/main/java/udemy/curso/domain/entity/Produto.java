package udemy.curso.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "PRODUTO")
public class Produto {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "descricao")
  @NotEmpty(message = "{campo.descricao.obrigatorio}")
  private String descricao;

  @Column(name = "preco_unitario")
  @NotNull(message = "{campo.preco.obrigatorio}")
  private BigDecimal preco;

}
