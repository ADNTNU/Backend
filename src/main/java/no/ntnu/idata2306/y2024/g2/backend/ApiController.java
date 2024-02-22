package no.ntnu.idata2306.y2024.g2.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("flights")
public class ApiController {
  @GetMapping()
  public String test(){
    return "Hei";
  }
}
