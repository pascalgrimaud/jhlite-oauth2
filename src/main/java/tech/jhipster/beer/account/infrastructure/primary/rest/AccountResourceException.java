package tech.jhipster.beer.account.infrastructure.primary.rest;

public class AccountResourceException extends RuntimeException {

  public AccountResourceException() {
    super();
  }

  public AccountResourceException(String message) {
    super(message);
  }

  public AccountResourceException(String message, Throwable cause) {
    super(message, cause);
  }
}
