package uz.etc.etcfitness.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException {
        response.setContentType("application/json");
        AntPathMatcher pathMatcher = new AntPathMatcher();
        List<String> whiteListedUrlList = Arrays.asList(
                "/api/v1/auth/authenticate",
                "/api/v1/auth/refresh-token",
                "/actuator/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/webjars/**",
                "/swagger-ui.html",
                "/swagger-ui/**"
                );

        try {

            String path = request.getServletPath();

            if (whiteListedUrlList.stream().anyMatch(whiteUrl -> pathMatcher.match(whiteUrl, path))) {
                filterChain.doFilter(request, response);
                return;
            }
            if (request.getServletPath().contains("/api/v1/auth")) {
                response.setStatus(HttpServletResponse.SC_OK);
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userPhone;
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\": \"No token provided\"}");
                return;
            } else {
                jwt = authHeader.substring(7);
                userPhone = jwtService.extractUsername(jwt);
                if (userPhone != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userPhone);
                    if (jwtService.isTokenValid(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            // Code when the JWT token is expired
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"JWT token expired\"}");
        } catch (MalformedJwtException e) {
            // Code when the token is invalid (either malformed or the signature didn't match)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Invalid JWT token\"}");
        } catch (Exception e) {
            // Code when a general error occurred during JWT validation
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Unable to validate JWT token\"}");
        }
    }
}
