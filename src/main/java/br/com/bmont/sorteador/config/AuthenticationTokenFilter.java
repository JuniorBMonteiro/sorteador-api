package br.com.bmont.sorteador.config;

import br.com.bmont.sorteador.exception.BadRequestException;
import br.com.bmont.sorteador.model.User;
import br.com.bmont.sorteador.service.TokenService;
import br.com.bmont.sorteador.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class AuthenticationTokenFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserDetailsServiceImpl userDetailsService;

    public AuthenticationTokenFilter(TokenService tokenService, UserDetailsServiceImpl userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = getToken(request);
        if (tokenService.isValidToken(token)){
            authenticateUser(token);
        }
        filterChain.doFilter(request, response);
    }

    private void authenticateUser(String token) {
        String username = tokenService.getUsernameByToken(token);
        User user = (User) userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


    private String getToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if (Objects.isNull(token) || !token.startsWith("Bearer ")){
            return null;
        }
        return token.substring(7);
    }
}
