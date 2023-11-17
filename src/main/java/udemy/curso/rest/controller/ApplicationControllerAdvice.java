package udemy.curso.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import udemy.curso.domain.exception.PedidoNaoEncontradoException;
import udemy.curso.domain.exception.RegraNegocioException;
import udemy.curso.rest.ApiErrors;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

  @ExceptionHandler(RegraNegocioException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrors handleRegraNegocioException(RegraNegocioException ex) {
    String mensagemErro = ex.getMessage();
    return new ApiErrors(mensagemErro);
  }

  @ExceptionHandler(PedidoNaoEncontradoException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiErrors handlePedidoNotFoundException(PedidoNaoEncontradoException ex) {
    String mensagemErro = ex.getMessage();
    return new ApiErrors(mensagemErro);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult().getAllErrors()
        .stream()
        .map(error -> error.getDefaultMessage())
        .collect(Collectors.toList());

    return new ApiErrors(errors);
  }
}
