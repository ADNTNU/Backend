package no.ntnu.idata2306.y2024.g2.backend;

public class Views {
  public interface IdOnly {
  }   // Only ID fields are visible

  public interface Search {
  }  // Search view includes only fields relevant for search

  public interface Full extends IdOnly, Search {
  }  // Full view includes all fields

  public interface NoId {
  }

  public interface HidePassword {
  }
}