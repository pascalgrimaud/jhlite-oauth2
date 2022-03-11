package tech.jhipster.beer.account.infrastructure.primary.rest;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.beer.UnitTest;

@UnitTest
class AccountResourceExceptionTest {

  @Test
  void shouldGetAccountResourceException() {
    AccountResourceException exception = new AccountResourceException();
    assertThat(exception.getMessage()).isNull();
  }

  @Test
  void shouldAccountResourceExceptionWithMessage() {
    AccountResourceException exception = new AccountResourceException("Hello JHipster");
    assertThat(exception.getMessage()).isEqualTo("Hello JHipster");
  }

  @Test
  void shouldAccountResourceExceptionWithCause() {
    NullPointerException nullPointerException = new NullPointerException();
    AccountResourceException exception = new AccountResourceException("Hello JHipster", nullPointerException);

    assertThat(exception.getMessage()).isEqualTo("Hello JHipster");
    assertThat(exception.getCause()).isInstanceOf(NullPointerException.class);
  }
}
