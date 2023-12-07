package com.mycompany.service;

import com.mycompany.dto.JwtAuthenticationResponse;
import com.mycompany.dto.RefreshTokeRequest;
import com.mycompany.dto.SigninRequest;
import com.mycompany.dto.SignupRequest;
import com.mycompany.entities.User;

public interface AuthenticationService {

    User signup(SignupRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);

    JwtAuthenticationResponse refreshToken(RefreshTokeRequest request);
}
