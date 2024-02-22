package no.ntnu.idata2306.y2024.g2.backend;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("flights")
public class ApiController {

  @GetMapping()
  @Operation(summary = "Returns an string of Hei, used for initial testing.")
  public String test(){
    return "Hei";
  }
}
