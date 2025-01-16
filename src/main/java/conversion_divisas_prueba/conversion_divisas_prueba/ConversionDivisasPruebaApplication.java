/*==============================================================================================
Nombre del archivo: ConversionDivisasPruebaApplication.java
Descripción: Funciones para controlar el inicio del aplicativo.
--------------------------------------------------------------------------------------------
Autor: Santiago Bellaizan Chaparro
Fecha: 2025-01-16
===============================================================================================*/

package conversion_divisas_prueba.conversion_divisas_prueba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConversionDivisasPruebaApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConversionDivisasPruebaApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}