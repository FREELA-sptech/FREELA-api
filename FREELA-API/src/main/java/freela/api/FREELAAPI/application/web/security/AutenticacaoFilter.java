package freela.api.FREELAAPI.application.web.security;

import freela.api.FREELAAPI.application.web.security.jwt.GerenciadorTokenJwt;
import freela.api.FREELAAPI.domain.services.authentication.AutenticacaoService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static freela.api.FREELAAPI.application.web.security.SecurityConfiguracao.URLS_PERMITIDAS;

public class AutenticacaoFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AutenticacaoFilter.class);

    private final AutenticacaoService autenticacaoService;

    private final GerenciadorTokenJwt jwtTokenManager;

    public AutenticacaoFilter(AutenticacaoService autenticacaoService, GerenciadorTokenJwt jwtTokenManager) {
        this.autenticacaoService = autenticacaoService;
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username = null;
        String jwtToken = null;

        if (isUrlPermitida(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String requestTokenHeader = request.getHeader("Authorization");

        // Verificar se não foi fornecido nenhum token
        if (Objects.isNull(requestTokenHeader) || !requestTokenHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            String errorMessage = "Nenhum token fornecido";
            response.getWriter().write(errorMessage);
            return;
        }

        jwtToken = requestTokenHeader.substring(7);

        // Verificar se o token é inválido
        if (!jwtTokenManager.validateToken(jwtToken)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            String errorMessage = "Token inválido";
            response.getWriter().write(errorMessage);
            return;
        }

        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);

            try {
                username = jwtTokenManager.getUsernameFromToken(jwtToken);
            } catch (ExpiredJwtException exception) {
                LOGGER.info("[FALHA AUTENTICACAO] - Token expirado, usuario: {} - {}",
                        exception.getClaims().getSubject(), exception.getMessage());

                LOGGER.trace("[FALHA AUTENTICACAO] - stack trace: %s", exception);

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                addUsernameInContext(request, username, jwtToken);
            } catch (UsernameNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");

                String errorMessage = "Usuário não cadastrado!";
                response.getWriter().write(errorMessage);
                return;
            }

        }

        filterChain.doFilter(request, response);
    }

    private boolean isUrlPermitida(HttpServletRequest request) {
        String url = request.getRequestURI();

        for (AntPathRequestMatcher urlPermitida : URLS_PERMITIDAS) {
            if (urlPermitida.matches(request)) {
                return true;
            }
        }

        return false;
    }

    private void addUsernameInContext(HttpServletRequest request, String username, String jwtToken) {
        UserDetails userDetails = autenticacaoService.loadUserByUsername(username);

        if (jwtTokenManager.validateToken(jwtToken, userDetails)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
    }
}