package no.ntnu.idata2306.y2024.g2.backend.security;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Role;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom {@link UserDetails} implementation that adapts {@link User} entity for Spring Security.
 * This class encapsulates user information which is later encapsulated into Authentication objects.
 * It converts the application-specific user entity into a format that
 * Spring Security can use for authentication and authorization.
 *
 * @author Daniel Neset
 * @version 17.04.2024
 */
public class AccessUserDetails implements UserDetails {

  private final String email;
  private final String password;
  private final boolean isActive;
  private final List<GrantedAuthority> authorityList = new LinkedList<>();

  /**
   * Constructs a new UserDetails instance from a given User entity.
   *
   * @param user The User entity from which user details are derived.
   */
  public AccessUserDetails(User user) {
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.isActive = user.isActive();
    this.convertRoles(user.getRoles());
  }

  /**
   * Converts roles associated with the user to {@link GrantedAuthority}
   * objects used by Spring Security.
   *
   * @param permissions A set of Role entities associated with the user.
   */
  private void convertRoles(Set<Role> permissions) {
    authorityList.clear();
    for (Role role : permissions) {
      authorityList.add(new SimpleGrantedAuthority(role.getName()));
    }
  }

  /**
   * Returns the authorities granted to the user.
   *
   * @return A collection of {@link GrantedAuthority} objects.
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorityList;
  }

  /**
   * Returns the password used to authenticate the user.
   *
   * @return Return the user's password.
   */
  @Override
  public String getPassword() {
    return password;
  }

  /**
   * Returns the email used to authenticate the user.
   * In this case, it is an email.
   *
   * @return Return the username (email) of the user.
   */
  @Override
  public String getUsername() {
    return email;
  }

  /**
   * Indicates whether the user's account is expired.
   *
   * @return Return true if the account is valid (not expired), false otherwise.
   */
  @Override
  public boolean isAccountNonExpired() {
    return isActive;
  }

  /**
   * Indicates whether the user is locked or unlocked.
   *
   * @return Return true if the account is not locked, false otherwise.
   */
  @Override
  public boolean isAccountNonLocked() {
    return isActive;
  }

  /**
   * Indicates whether the user's credentials (password) are expired.
   *
   * @return Return true if the credentials are valid (not expired), false otherwise.
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return isActive;
  }

  /**
   * Indicates whether the user is enabled or disabled.
   *
   * @return Return true indicating the user is enabled.
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
}
