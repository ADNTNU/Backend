package no.ntnu.idata2306.y2024.g2.backend;

public class Views {
  public interface IdOnly {}   // Only ID fields are visible
  public interface Full extends IdOnly {}  // Full view includes all fields

  public class NoId {}
}