package tech.jhipster.beer.account.infrastructure.primary.rest;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.jhipster.beer.account.infrastructure.primary.rest.OAuth2TestUtil.*;

import java.security.Principal;
import java.util.Collection;
import javax.security.auth.Subject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.beer.IntegrationTest;
import tech.jhipster.beer.security.oauth2.domain.AuthoritiesConstants;
import tech.jhipster.beer.security.oauth2.infrastructure.WithUnauthenticatedMockUser;

@AutoConfigureMockMvc
@IntegrationTest
@WithMockUser(value = "test")
class AccountResourceIT {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  OAuth2AuthorizedClientService authorizedClientService;

  @Autowired
  ClientRegistration clientRegistration;

  @Test
  void shouldGetExistingAccount() throws Exception {
    TestSecurityContextHolder
      .getContext()
      .setAuthentication(registerAuthenticationToken(authorizedClientService, clientRegistration, testAuthenticationToken()));

    mockMvc
      .perform(get("/api/account").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.login").value("test"))
      .andExpect(jsonPath("$.email").value("john.doe@jhipster.com"))
      .andExpect(jsonPath("$.authorities").value(AuthoritiesConstants.ADMIN));
  }

  @Test
  void shouldGetUnknownAccount() throws Exception {
    mockMvc.perform(get("/api/account").accept(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());
  }

  @Test
  @WithUnauthenticatedMockUser
  void testNonAuthenticatedUser() throws Exception {
    mockMvc.perform(get("/api/authenticate").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().string(""));
  }

  @Test
  void testAuthenticatedUser() throws Exception {
    mockMvc
      .perform(
        get("/api/authenticate")
          .with(request -> {
            request.setRemoteUser("test");
            return request;
          })
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isOk())
      .andExpect(content().string("test"));
  }
}
