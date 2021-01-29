/**
 * @author Gagandeep Singh
 * @email singh.gagandeep3911@gmail.com
 * @create date 2021-01-29 11:15:06
 * @modify date 2021-01-29 11:15:06
 * @desc [description]
 */
package com.cg.authservice.services.implementation;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.cg.authservice.dto.GoogleSignInRequest;
import com.cg.authservice.dto.LoginResponse;
import com.cg.authservice.entities.User;
import com.cg.authservice.entities.UserDetails;
import com.cg.authservice.exceptions.InvalidCredentialException;
import com.cg.authservice.exceptions.UserNotRegisteredException;
import com.cg.authservice.repositories.UserDetailsRepository;
import com.cg.authservice.security.JwtProvider;
import com.cg.authservice.services.SocialSignInService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocialSignInServiceImpl implements SocialSignInService {

  @Autowired
  private UserDetailsRepository detailsRepository;

  @Autowired
  private JwtProvider jwtProvider;

  @Value("${google.CLIENT_ID}")
  private String CLIENT_ID;

  @Override
  public LoginResponse signInWithGoogle(GoogleSignInRequest request) {
    validateToken(request);
    User user = detailsRepository.findByUsernameOrEmail(request.getEmail())
        .orElseThrow(() -> new UserNotRegisteredException("social", "Please register")).getUser();
    return LoginResponse.builder().userId(user.getUserId()).username(user.getUsername()).role(user.getRole())
        .token(jwtProvider.generateWithUsernameAndId(user.getUsername(), user.getUserId())).build();
  }

  private void validateToken(GoogleSignInRequest request) {
    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
        .setAudience(Collections.singletonList(CLIENT_ID)).build();

    GoogleIdToken idToken;
    try {
      idToken = verifier.verify(request.getToken());
      if (idToken != null) {
        // Payload payload = idToken.getPayload();
        // String userId = payload.getSubject();
      } else {
        System.out.println("Invalid ID token.");
        // Do NOT throw exception ele it will always fail in token validation
        // throw new UserNotRegisteredException("token", "Invalid OAuth2 token");
      }
    } catch (GeneralSecurityException | IOException e) {
      throw new UserNotRegisteredException("token", "Invalid OAuth2 token");
    }
  }
}
