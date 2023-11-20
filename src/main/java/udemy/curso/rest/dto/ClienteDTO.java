package udemy.curso.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@Builder
public class ClienteDTO {

  @NotEmpty(message = "{campo.nome.obrigatorio}")
  private String nome;

  @NotEmpty(message = "{campo.cpf.obrigatorio}")
  @CPF(message = "{campo.cpf.invalido}")
  private String cpf;
}
