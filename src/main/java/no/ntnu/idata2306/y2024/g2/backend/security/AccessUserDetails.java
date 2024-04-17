package no.ntnu.idata2306.y2024.g2.backend.security;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Role;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AccessUserDetails implements UserDetails {

  private final String username;
  private final String password;
  private boolean isActive;
  private final List<GrantedAuthority> authorityList = new LinkedList<>();

  public AccessUserDetails(User user){
    this.username = user.getEmail();
    this.password = user.getPassword();
    this.isActive = user.isActive();
    this.convertRoles(user.getRoles());
  }

  private void convertRoles(Set<Role> permissions){
    authorityList.clear();
    for (Role role : permissions){
      authorityList.add(new SimpleGrantedAuthority(role.getName()));
    }
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorityList;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return isActive;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isActive;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isActive;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
