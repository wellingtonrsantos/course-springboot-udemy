package udemy.curso.rest;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class ApiErrors {

  private final List<String> errors;

  public ApiErrors(String mensagemErro) {
    this.errors = Collections.singletonList(mensagemErro);
  }

  public ApiErrors(List<String> errors) {
    this.errors = errors;
  }
}
