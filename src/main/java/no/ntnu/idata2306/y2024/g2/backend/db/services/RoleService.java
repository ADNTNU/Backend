package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.Role;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
  private final RoleRepository roleRepository;

  /**
   * Constructs an instance of RoleService with necessary dependency.
   *
   * @param roleRepository The repository handling role data operations.
   */
  @Autowired
  public RoleService(RoleRepository roleRepository){
    this.roleRepository = roleRepository;
  }

  /**
   * Retrieves a Role by their ID.
   *
   * @param id The unique identifier of the role to retrieve.
   * @return Return an {@link Optional} containing the found role or an empty Optional if no user is found.
   */
  public Optional<Role> getRoleById(int id){
    return roleRepository.findById(id);
  }

}
