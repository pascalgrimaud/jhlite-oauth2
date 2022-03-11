package tech.jhipster.beer.account.infrastructure.primary.rest;

import static tech.jhipster.beer.account.infrastructure.domain.AccountConstant.DEFAULT_LANGUAGE;

import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import tech.jhipster.beer.security.oauth2.application.SecurityUtils;

public class UserDTO {

  private String id;
  private String login;
  private String firstName;
  private String lastName;
  private String email;
  private String imageUrl;
  private boolean activated = false;
  private String langKey;
  private Instant createdDate;
  private Instant lastModifiedDate;
  private Set<String> authorities;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public boolean isActivated() {
    return activated;
  }

  public void setActivated(boolean activated) {
    this.activated = activated;
  }

  public String getLangKey() {
    return langKey;
  }

  public void setLangKey(String langKey) {
    this.langKey = langKey;
  }

  public Instant getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  public Instant getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Instant lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public Set<String> getAuthorities() {
    return authorities;
  }

  public void setAuthorities(Set<String> authorities) {
    this.authorities = authorities;
  }

  public UserDTO id(String id) {
    this.id = id;
    return this;
  }

  public UserDTO login(String login) {
    this.login = login;
    return this;
  }

  public UserDTO firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

  public UserDTO lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

  public UserDTO email(String email) {
    this.email = email;
    return this;
  }

  public UserDTO imageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
    return this;
  }

  public UserDTO activated(boolean activated) {
    this.activated = activated;
    return this;
  }

  public UserDTO langKey(String langKey) {
    this.langKey = langKey;
    return this;
  }

  public UserDTO createdDate(Instant createdDate) {
    this.createdDate = createdDate;
    return this;
  }

  public UserDTO lastModifiedDate(Instant lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
    return this;
  }

  public UserDTO authorities(Set<String> authorities) {
    this.authorities = authorities;
    return this;
  }

  public static UserDTO getUserDTOFromToken(AbstractAuthenticationToken authToken) {
    UserDTO userDTO = getUserDTOFromAttributes(SecurityUtils.getAttributes(authToken));
    userDTO.setAuthorities(authToken.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
    return userDTO;
  }

  private static UserDTO getUserDTOFromAttributes(Map<String, Object> details) {
    UserDTO user = new UserDTO();
    Boolean activated = Boolean.TRUE;
    String sub = String.valueOf(details.get("sub"));

    // handle resource server JWT, where sub claim is email and uid is ID
    if (details.get("uid") != null) {
      user.setId((String) details.get("uid"));
      user.setLogin(sub);
    } else {
      user.setId(sub);
    }

    String username = null;
    if (details.get("preferred_username") != null) {
      username = ((String) details.get("preferred_username")).toLowerCase();
    }
    if (username != null) {
      user.setLogin(username);
    } else if (user.getLogin() == null) {
      user.setLogin(user.getId());
    }
    if (details.get("given_name") != null) {
      user.setFirstName((String) details.get("given_name"));
    } else if (details.get("name") != null) {
      user.setFirstName((String) details.get("name"));
    }
    if (details.get("family_name") != null) {
      user.setLastName((String) details.get("family_name"));
    }
    if (details.get("email_verified") != null) {
      activated = (Boolean) details.get("email_verified");
    }
    if (details.get("email") != null) {
      user.setEmail(((String) details.get("email")).toLowerCase());
    } else if (sub.contains("|") && (username != null && username.contains("@"))) {
      // special handling for Auth0
      user.setEmail(username);
    } else {
      user.setEmail(sub);
    }
    if (details.get("langKey") != null) {
      user.setLangKey((String) details.get("langKey"));
    } else if (details.get("locale") != null) {
      // trim off country code if it exists
      String locale = (String) details.get("locale");
      if (locale.contains("_")) {
        locale = locale.substring(0, locale.indexOf('_'));
      } else if (locale.contains("-")) {
        locale = locale.substring(0, locale.indexOf('-'));
      }
      user.setLangKey(locale.toLowerCase());
    } else {
      // set langKey to default if not specified by IdP
      user.setLangKey(DEFAULT_LANGUAGE);
    }
    if (details.get("picture") != null) {
      user.setImageUrl((String) details.get("picture"));
    }
    user.setActivated(activated);
    return user;
  }
}
