package no.ntnu.idata2306.y2024.g2.backend.db.dto;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Role;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.User;

public class RoleUserDTO {

  private final User user;
  private final Role role;

  public RoleUserDTO(User user, Role role){
    this.user = user;
    this.role = role;
  }

  public User getUser() {
    return user;
  }

  public Role getRole() {
    return role;
  }
}
