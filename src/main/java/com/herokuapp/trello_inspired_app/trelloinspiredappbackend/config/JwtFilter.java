package com.herokuapp.trello_inspired_app.trelloinspiredappbackend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.exception.UserNotFoundException;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.model.User;
import com.herokuapp.trello_inspired_app.trelloinspiredappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.herokuapp.trello_inspired_app.trelloinspiredappbackend.util.JwtUtil.USERNAME;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Value("${authentication.jwt.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authorization == null || authorization.isEmpty() || authorization.equals("Bearer null")) {
            filterChain.doFilter(request, response);
            return;
        }

        var username = getUsername(authorization);
        var user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        var token = getToken(user);
        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);

        filterChain.doFilter(request, response);
    }

    private String getUsername(String authorization) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(secret)).build();
        DecodedJWT verify = jwtVerifier.verify(authorization.substring(7));

        return verify.getClaim(USERNAME).asString();
    }

    private UsernamePasswordAuthenticationToken getToken(User user) {
        return new UsernamePasswordAuthenticationToken(
                user,
                user.getUsername(),
                user.getAuthorities()
        );
    }
}
