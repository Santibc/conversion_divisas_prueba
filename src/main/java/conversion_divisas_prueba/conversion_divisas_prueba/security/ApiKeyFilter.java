/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package conversion_divisas_prueba.conversion_divisas_prueba.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class ApiKeyFilter extends OncePerRequestFilter {
    private final String apiKey;

    public ApiKeyFilter(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
            FilterChain filterChain) throws ServletException, IOException {
        String headerKey = request.getHeader("X-API-KEY");
        
        if (headerKey == null || headerKey.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API key is missing");
            return;
        }
        
        if (!headerKey.equals(apiKey)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid API key");
            return;
        }
        
        filterChain.doFilter(request, response);
    }
}
