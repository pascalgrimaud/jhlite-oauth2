package tech.jhipster.beer.account.infrastructure.primary.rest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.security.Principal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.jhipster.beer.UnitTest;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AccountResourceTest {

  @InjectMocks
  AccountResource accountResource;

  @Mock
  Principal principal;

  @Test
  void shouldNotGetAccount() {
    assertThatThrownBy(() -> accountResource.getAccount(principal)).isExactlyInstanceOf(AccountResourceException.class);
  }
}
