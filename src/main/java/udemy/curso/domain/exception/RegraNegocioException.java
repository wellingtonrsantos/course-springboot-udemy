package udemy.curso.domain.exception;

public class RegraNegocioException extends RuntimeException {
  public RegraNegocioException(String message) {
    super(message);
  }
}
