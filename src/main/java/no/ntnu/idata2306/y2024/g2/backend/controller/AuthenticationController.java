package no.ntnu.idata2306.y2024.g2.backend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import no.ntnu.idata2306.y2024.g2.backend.db.dto.SignupDto;
import no.ntnu.idata2306.y2024.g2.backend.db.services.AccessUserService;
import no.ntnu.idata2306.y2024.g2.backend.db.dto.AuthenticationRequest;
import no.ntnu.idata2306.y2024.g2.backend.db.dto.AuthenticationResponse;
import no.ntnu.idata2306.y2024.g2.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("auth")
@Tag(name = "Authentication API")
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AccessUserService accessUserService;

  @Autowired
  private JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authenticationRequest){
    try{
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
              authenticationRequest.getEmail(),
              authenticationRequest.getPassword()));
    }catch (BadCredentialsException badCredentialsException){
      return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }
    final UserDetails userDetails = accessUserService.loadUserByUsername(
            authenticationRequest.getEmail());
    final String jwt = jwtUtil.generateToken(userDetails);
    return ResponseEntity.ok(new AuthenticationResponse(jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signupProcess(@RequestBody SignupDto signupDto) {
    ResponseEntity<String> response;
    try{
      accessUserService.tryCreateNewUser(signupDto.getEmail(), signupDto.getPassword());
      response = new ResponseEntity<>(HttpStatus.OK);
    }catch (IOException ioException){
      response = new ResponseEntity<>(ioException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    return response;
  }

}
