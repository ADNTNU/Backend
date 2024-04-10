package no.ntnu.idata2306.y2024.g2.backend.db.services;

import no.ntnu.idata2306.y2024.g2.backend.db.entities.ClassType;
import no.ntnu.idata2306.y2024.g2.backend.db.entities.ExtraFeature;
import no.ntnu.idata2306.y2024.g2.backend.db.repository.ExtraFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExtraFeatureService {

  @Autowired
  private ExtraFeatureRepository extraFeatureRepository;

  public List<ExtraFeature> getAllExtraFeatures(){
    List<ExtraFeature> extraFeatures = new ArrayList<>();
    extraFeatureRepository.findAll().forEach(extraFeatures::add);
    return extraFeatures;
  }

  public Optional<ExtraFeature> getExtraFeature(int id){
    return extraFeatureRepository.findById(id);
  }

  public void addExtraFeature(ExtraFeature extraFeature){
    extraFeatureRepository.save(extraFeature);
  }

  public void updateExtraFeature(ExtraFeature extraFeature){
    extraFeatureRepository.save(extraFeature);
  }

  public void deleteExtraFeature(ExtraFeature extraFeature){
    extraFeatureRepository.delete(extraFeature);
  }

  public void deleteExtraFeatureById(int id){
    extraFeatureRepository.deleteById(id);
  }


}
