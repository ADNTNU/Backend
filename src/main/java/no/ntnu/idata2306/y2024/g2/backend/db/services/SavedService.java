package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.ClassType;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.Saved;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.ClassTypeRepository;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.SavedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SavedService {
  @Autowired
  private SavedRepository savedRepository;

  public List<Saved> getAllSaves(){
    List<Saved> saves = new ArrayList<>();
    savedRepository.findAll().forEach(saves::add);
    return saves;
  }

  public Optional<Saved> getSaved(int id){
    return savedRepository.findById(id);
  }

  public void addSaved(Saved saved){
    savedRepository.save(saved);
  }

  public void updateSaved(Saved saved){
    savedRepository.save(saved);
  }

  public void deleteSaved(Saved saved){
    savedRepository.delete(saved);
  }

  public void deleteSavesById(int id){
    savedRepository.deleteById(id);
  }
}
