package com.GreenThumb.Auth;


import lombok.RequiredArgsConstructor;
import com.GreenThumb.Config.JwtService;
import com.GreenThumb.Models.heritage.User;
import com.GreenThumb.Models.Enums.Role;
import com.GreenThumb.Repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .fullName(request.getFullname())
                .phone(request.getPhone())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole()) // Use the role from the request
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken).role(user.getRole().name())  // Include the user's role
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()
                )
        );

        // Fetch the user from the repository
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Generate the JWT token with the role
        var jwtToken = jwtService.generateToken(user);

        // Return both token and role in the response
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .role(user.getRole().name())  // Include the user's role
                .id(String.valueOf(user.getId())) // Convert Long ID to String
                .build();
    }
}
