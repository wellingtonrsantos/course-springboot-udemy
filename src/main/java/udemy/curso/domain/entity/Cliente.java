package udemy.curso.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "CLIENTE")
public class Cliente {

  public Cliente(String nome, String cpf) {
    this.nome = nome;
    this.cpf = cpf;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "nome", length = 100)
  private String nome;

  @Column(name = "cpf", length = 11)
  private String cpf;

  @JsonIgnore
  @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
  private Set<Pedido> pedidos;

}