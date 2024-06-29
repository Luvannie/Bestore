package com.luvannie.springbootbookecommerce.filters;

import com.luvannie.springbootbookecommerce.component.JwtTokenComponent;
import com.luvannie.springbootbookecommerce.entity.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor

public class JwtTokenFilter extends OncePerRequestFilter{
    @Value("${api.prefix}")
    private String apiPrefix;
    private final UserDetailsService userDetailsService;
    private final JwtTokenComponent jwtTokenUtil;
    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if(isBypassToken(request)) {
                filterChain.doFilter(request, response); //enable bypass cho đi qua hết
                return;
            }

            final String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "authHeader null or not started with Bearer");
                return;
            }
            final String token = authHeader.substring(7);
            final String account = jwtTokenUtil.getSubject(token);
            if (account != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                User userDetails = (User) userDetailsService.loadUserByUsername(account);
                if(jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response); //enable bypass



        }catch (Exception e) {
            //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage());
        }

    }
    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                // Healthcheck request, no JWT token required
//                Pair.of(String.format("%s/healthcheck/health", apiPrefix), "GET"),
                Pair.of(String.format("%s/actuator/**", apiPrefix), "GET"),

                Pair.of(String.format("%s/roles**", apiPrefix), "GET"),
                Pair.of(String.format("%s/policies**", apiPrefix), "GET"),
                Pair.of(String.format("%s/comments**", apiPrefix), "GET"),
                Pair.of(String.format("%s/coupons/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/countries**", apiPrefix), "GET"),
                Pair.of(String.format("%s/cities**", apiPrefix), "GET"),
                Pair.of(String.format("%s/cities/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/checkout/**", apiPrefix), "POST"),
                Pair.of(String.format("%s/checkout/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/books**", apiPrefix), "GET"),
                Pair.of(String.format("%s/book-category**", apiPrefix), "GET"),
                Pair.of(String.format("%s/orders/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/book_categories/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/refreshToken", apiPrefix), "POST"),
                Pair.of(String.format("%s/complain/**", apiPrefix), "POST"),
                Pair.of(String.format("%s/cart_item/**", apiPrefix), "PUT"),
                Pair.of(String.format("%s/cart_item/**", apiPrefix), "DELETE"),
                Pair.of(String.format("%s/cart_item/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/cart_item/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/books_admin/**", apiPrefix), "GET"),
                Pair.of(String.format("%s/books_admin/**", apiPrefix), "POST"),
                Pair.of(String.format("%s/books_admin/**", apiPrefix), "PUT"),
                Pair.of(String.format("%s/books_admin/**", apiPrefix), "DELETE"),
//                Pair.of(String.format("%s/book_categories_admin/**", apiPrefix), "GET"),
//                Pair.of(String.format("%s/book_categories_admin/**", apiPrefix), "POST"),
//                Pair.of(String.format("%s/book_categories_admin/**", apiPrefix), "PUT"),
//                Pair.of(String.format("%s/book_categories_admin/**", apiPrefix), "DELETE"),
                Pair.of(String.format("%s/payment/**", apiPrefix), "GET")

        );

        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();

        for (Pair<String, String> token : bypassTokens) {
            String path = token.getFirst();
            String method = token.getSecond();
            // Check if the request path and method match any pair in the bypassTokens list
            if (requestPath.matches(path.replace("**", ".*"))
                    && requestMethod.equalsIgnoreCase(method)) {
                return true;
            }
        }
        return false;
    }
}