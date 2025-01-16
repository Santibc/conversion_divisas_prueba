/*==============================================================================================
Nombre del archivo: ApiKeyFilterTest.java
Descripción: Test para API key.
--------------------------------------------------------------------------------------------
Autor: Santiago Bellaizan Chaparro
Fecha: 2025-01-16
===============================================================================================*/

package conversion_divisas_prueba.conversion_divisas_prueba.security;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiKeyFilterTest {

    private ApiKeyFilter filter;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain filterChain;
    private static final String VALID_API_KEY = "mi-clave-api-segura-123";

    @BeforeEach
    void setUp() {
        filter = new ApiKeyFilter(VALID_API_KEY);
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        filterChain = new MockFilterChain();
    }

    @Test
    void testValidApiKey() throws Exception {
        request.addHeader("X-API-KEY", VALID_API_KEY);
        filter.doFilterInternal(request, response, filterChain);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testMissingApiKey() throws Exception {
        filter.doFilterInternal(request, response, filterChain);
        assertEquals(401, response.getStatus());
        assertEquals("API key is missing", response.getErrorMessage());
    }

    @Test
    void testInvalidApiKey() throws Exception {
        request.addHeader("X-API-KEY", "invalid-key");
        filter.doFilterInternal(request, response, filterChain);
        assertEquals(403, response.getStatus());
        assertEquals("Invalid API key", response.getErrorMessage());
    }
}