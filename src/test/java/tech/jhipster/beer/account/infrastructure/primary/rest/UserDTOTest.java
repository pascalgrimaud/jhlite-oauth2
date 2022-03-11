package tech.jhipster.beer.account.infrastructure.primary.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames.ID_TOKEN;

import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import tech.jhipster.beer.UnitTest;
import tech.jhipster.beer.security.oauth2.domain.AuthoritiesConstants;

@UnitTest
class UserDTOTest {

  @Test
  @DisplayName("should build UserDTO with fluent setters")
  void shouldBuildUserDTOWithFluentSetters() {
    Instant now = Instant.now();
    UserDTO userDTO = new UserDTO()
      .id("7cf87705-286e-448c-9b67-43d2c87a3de9")
      .login("beer")
      .firstName("Beer")
      .lastName("CHIPS")
      .email("beer.chips@jhipster.tech")
      .imageUrl("https://raw.githubusercontent.com/jhipster/jhipster-artwork/main/logos/lite/JHipster-Lite-neon-blue.png")
      .activated(true)
      .langKey("en")
      .createdDate(now)
      .lastModifiedDate(now)
      .authorities(Set.of("ROLE_USER", "ROLE_ADMIN"));

    assertThat(userDTO.getId()).isEqualTo("7cf87705-286e-448c-9b67-43d2c87a3de9");
    assertThat(userDTO.getLogin()).isEqualTo("beer");
    assertThat(userDTO.getFirstName()).isEqualTo("Beer");
    assertThat(userDTO.getLastName()).isEqualTo("CHIPS");
    assertThat(userDTO.getEmail()).isEqualTo("beer.chips@jhipster.tech");
    assertThat(userDTO.getImageUrl())
      .isEqualTo("https://raw.githubusercontent.com/jhipster/jhipster-artwork/main/logos/lite/JHipster-Lite-neon-blue.png");
    assertThat(userDTO.isActivated()).isTrue();
    assertThat(userDTO.getLangKey()).isEqualTo("en");
    assertThat(userDTO.getCreatedDate()).isEqualTo(now);
    assertThat(userDTO.getLastModifiedDate()).isEqualTo(now);
    assertThat(userDTO.getAuthorities()).contains("ROLE_USER", "ROLE_ADMIN");
  }

  @Test
  @DisplayName("should build UserDTO with setters")
  void shouldBuildUserDTOWithSetters() {
    Instant now = Instant.now();
    UserDTO userDTO = new UserDTO();
    userDTO.setId("7cf87705-286e-448c-9b67-43d2c87a3de9");
    userDTO.setLogin("beer");
    userDTO.setFirstName("Beer");
    userDTO.setLastName("CHIPS");
    userDTO.setEmail("beer.chips@jhipster.tech");
    userDTO.setImageUrl("https://raw.githubusercontent.com/jhipster/jhipster-artwork/main/logos/lite/JHipster-Lite-neon-blue.png");
    userDTO.setActivated(true);
    userDTO.setLangKey("en");
    userDTO.setCreatedDate(now);
    userDTO.setLastModifiedDate(now);
    userDTO.setAuthorities(Set.of("ROLE_USER", "ROLE_ADMIN"));

    assertThat(userDTO.getId()).isEqualTo("7cf87705-286e-448c-9b67-43d2c87a3de9");
    assertThat(userDTO.getLogin()).isEqualTo("beer");
    assertThat(userDTO.getFirstName()).isEqualTo("Beer");
    assertThat(userDTO.getLastName()).isEqualTo("CHIPS");
    assertThat(userDTO.getEmail()).isEqualTo("beer.chips@jhipster.tech");
    assertThat(userDTO.getImageUrl())
      .isEqualTo("https://raw.githubusercontent.com/jhipster/jhipster-artwork/main/logos/lite/JHipster-Lite-neon-blue.png");
    assertThat(userDTO.isActivated()).isTrue();
    assertThat(userDTO.getLangKey()).isEqualTo("en");
    assertThat(userDTO.getCreatedDate()).isEqualTo(now);
    assertThat(userDTO.getLastModifiedDate()).isEqualTo(now);
    assertThat(userDTO.getAuthorities()).contains("ROLE_USER", "ROLE_ADMIN");
  }

  @Test
  void shouldGetUser() {
    Map<String, Object> claims = new HashMap<>();

    claims.put("sub", "4c973896-5761-41fc-8217-07c5d13a004b");
    claims.put("email_verified", true);
    claims.put("given_name", "Admin");
    claims.put("name", "Admin Administrator");
    claims.put("session_state", "3035eb99-814d-4e04-9f2d-123b3cc23748");
    claims.put("family_name", "Administrator");
    claims.put("email", "admin@localhost");
    claims.put("picture", "https://raw.githubusercontent.com/jhipster/jhipster-artwork/main/logos/lite/JHipster-Lite-neon-blue.png");
    claims.put("langKey", "en");

    OidcIdToken idToken = new OidcIdToken(ID_TOKEN, Instant.now(), Instant.now().plusSeconds(60), claims);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
    OidcUser user = new DefaultOidcUser(authorities, idToken);
    OAuth2AuthenticationToken oauth2AuthenticationToken = new OAuth2AuthenticationToken(user, authorities, "oidc");

    UserDTO result = UserDTO.getUserDTOFromToken(oauth2AuthenticationToken);

    assertThat(result.getId()).isEqualTo("4c973896-5761-41fc-8217-07c5d13a004b");
    assertThat(result.getLogin()).isEqualTo("4c973896-5761-41fc-8217-07c5d13a004b");
    assertThat(result.getFirstName()).isEqualTo("Admin");
    assertThat(result.getLastName()).isEqualTo("Administrator");
    assertThat(result.getEmail()).isEqualTo("admin@localhost");
    assertThat(result.getImageUrl())
      .isEqualTo("https://raw.githubusercontent.com/jhipster/jhipster-artwork/main/logos/lite/JHipster-Lite-neon-blue.png");
    assertThat(result.isActivated()).isTrue();
    assertThat(result.getLangKey()).isEqualTo("en");
    assertThat(result.getAuthorities()).contains("ROLE_ADMIN");
  }

  @Test
  void shouldGetUserWithUuid() {
    Map<String, Object> claims = new HashMap<>();

    claims.put("uid", "4c973896-5761-41fc-8217-07c5d13a004b");
    claims.put("sub", "admin");
    claims.put("preferred_username", "admin");
    claims.put("email_verified", true);
    claims.put("name", "Admin Administrator");
    claims.put("session_state", "3035eb99-814d-4e04-9f2d-123b3cc23748");
    claims.put("family_name", "Administrator");
    claims.put("email", "admin@localhost");
    claims.put("picture", "https://raw.githubusercontent.com/jhipster/jhipster-artwork/main/logos/lite/JHipster-Lite-neon-blue.png");
    claims.put("locale", "fr_FR");

    OidcIdToken idToken = new OidcIdToken(ID_TOKEN, Instant.now(), Instant.now().plusSeconds(60), claims);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
    OidcUser user = new DefaultOidcUser(authorities, idToken);
    OAuth2AuthenticationToken oauth2AuthenticationToken = new OAuth2AuthenticationToken(user, authorities, "oidc");

    UserDTO result = UserDTO.getUserDTOFromToken(oauth2AuthenticationToken);

    assertThat(result.getId()).isEqualTo("4c973896-5761-41fc-8217-07c5d13a004b");
    assertThat(result.getLogin()).isEqualTo("admin");
    assertThat(result.getFirstName()).isEqualTo("Admin Administrator");
    assertThat(result.getLastName()).isEqualTo("Administrator");
    assertThat(result.getEmail()).isEqualTo("admin@localhost");
    assertThat(result.getImageUrl())
      .isEqualTo("https://raw.githubusercontent.com/jhipster/jhipster-artwork/main/logos/lite/JHipster-Lite-neon-blue.png");
    assertThat(result.isActivated()).isTrue();
    assertThat(result.getLangKey()).isEqualTo("fr");
    assertThat(result.getAuthorities()).contains("ROLE_ADMIN");
  }

  @Test
  void shouldGetUserWithSubEmailAndLocaleWithDash() {
    Map<String, Object> claims = new HashMap<>();

    claims.put("uid", "4c973896-5761-41fc-8217-07c5d13a004b");
    claims.put("sub", "admin@localhost");
    claims.put("locale", "fr-FR");

    OidcIdToken idToken = new OidcIdToken(ID_TOKEN, Instant.now(), Instant.now().plusSeconds(60), claims);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
    OidcUser user = new DefaultOidcUser(authorities, idToken);
    OAuth2AuthenticationToken oauth2AuthenticationToken = new OAuth2AuthenticationToken(user, authorities, "oidc");

    UserDTO result = UserDTO.getUserDTOFromToken(oauth2AuthenticationToken);

    assertThat(result.getEmail()).isEqualTo("admin@localhost");
    assertThat(result.getLangKey()).isEqualTo("fr");
  }

  @Test
  void shouldGetUserWithSubEmailAndLocale() {
    Map<String, Object> claims = new HashMap<>();

    claims.put("uid", "4c973896-5761-41fc-8217-07c5d13a004b");
    claims.put("preferred_username", "admin@localhost");
    claims.put("sub", "admin@localhost|information");
    claims.put("locale", "fr");

    OidcIdToken idToken = new OidcIdToken(ID_TOKEN, Instant.now(), Instant.now().plusSeconds(60), claims);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
    OidcUser user = new DefaultOidcUser(authorities, idToken);
    OAuth2AuthenticationToken oauth2AuthenticationToken = new OAuth2AuthenticationToken(user, authorities, "oidc");

    UserDTO result = UserDTO.getUserDTOFromToken(oauth2AuthenticationToken);

    assertThat(result.getEmail()).isEqualTo("admin@localhost");
    assertThat(result.getLangKey()).isEqualTo("fr");
  }

  @Test
  void shouldGetUserWithSubUsernameNotContainArobase() {
    Map<String, Object> claims = new HashMap<>();

    claims.put("uid", "4c973896-5761-41fc-8217-07c5d13a004b");
    claims.put("preferred_username", "admin");
    claims.put("sub", "admin@localhost|information");
    claims.put("locale", "fr");

    OidcIdToken idToken = new OidcIdToken(ID_TOKEN, Instant.now(), Instant.now().plusSeconds(60), claims);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
    OidcUser user = new DefaultOidcUser(authorities, idToken);
    OAuth2AuthenticationToken oauth2AuthenticationToken = new OAuth2AuthenticationToken(user, authorities, "oidc");

    UserDTO result = UserDTO.getUserDTOFromToken(oauth2AuthenticationToken);

    assertThat(result.getLangKey()).isEqualTo("fr");
  }

  @Test
  void shouldGetUserWithSubNoUsername() {
    Map<String, Object> claims = new HashMap<>();

    claims.put("uid", "4c973896-5761-41fc-8217-07c5d13a004b");
    claims.put("sub", "admin@localhost|information");
    claims.put("locale", "fr");

    OidcIdToken idToken = new OidcIdToken(ID_TOKEN, Instant.now(), Instant.now().plusSeconds(60), claims);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
    OidcUser user = new DefaultOidcUser(authorities, idToken);
    OAuth2AuthenticationToken oauth2AuthenticationToken = new OAuth2AuthenticationToken(user, authorities, "oidc");

    UserDTO result = UserDTO.getUserDTOFromToken(oauth2AuthenticationToken);

    assertThat(result.getLangKey()).isEqualTo("fr");
  }

  @Test
  void shouldGetUserWithSubEmailAndNoLocale() {
    Map<String, Object> claims = new HashMap<>();

    claims.put("uid", "4c973896-5761-41fc-8217-07c5d13a004b");
    claims.put("preferred_username", "admin@localhost");
    claims.put("sub", "admin@localhost|information");

    OidcIdToken idToken = new OidcIdToken(ID_TOKEN, Instant.now(), Instant.now().plusSeconds(60), claims);
    Collection<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
    OidcUser user = new DefaultOidcUser(authorities, idToken);
    OAuth2AuthenticationToken oauth2AuthenticationToken = new OAuth2AuthenticationToken(user, authorities, "oidc");

    UserDTO result = UserDTO.getUserDTOFromToken(oauth2AuthenticationToken);

    assertThat(result.getEmail()).isEqualTo("admin@localhost");
    assertThat(result.getLangKey()).isEqualTo("en");
  }
}
