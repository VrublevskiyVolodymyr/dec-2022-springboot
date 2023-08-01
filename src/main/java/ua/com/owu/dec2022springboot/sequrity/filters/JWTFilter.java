package ua.com.owu.dec2022springboot.sequrity.filters;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.com.owu.dec2022springboot.dao.UserDAO;
import ua.com.owu.dec2022springboot.dao.models.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private UserDAO userDAO;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String token = authorization.replace("Bearer ", "");
            System.out.println("token-" + token);

            String decodedData = Jwts.parser()
                    .setSigningKey("okten".getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            System.out.println("decodedData-" + decodedData);
            User user = userDAO.findByEmail(decodedData);
            System.out.println("client-" + user);
            if (user != null) {
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(
                                user.getEmail(),
                                user.getPassword(),
                                user.getAuthorities()
                        ));
            }
        }
        filterChain.doFilter(request, response);
    }
}
