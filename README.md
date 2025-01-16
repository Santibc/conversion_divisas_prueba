# API de Conversión de Divisas - Documentación Técnica

## Descripción General
Aplicación API REST que proporciona servicios de conversión de divisas utilizando datos de tipos de cambio en tiempo real. La API está protegida mediante un sistema de autenticación por API key.

## Arquitectura
La aplicación sigue una arquitectura por capas basada en Spring Boot:

```
src/main/java/conversion_divisas_prueba/
├── ConversionDivisasPruebaApplication.java  # Clase principal de la aplicación
├── config/                                  # Configuraciones
│   └── SecurityConfig.java                  # Configuración de seguridad
├── controller/                              # Controladores REST
│   └── CurrencyConversionController.java    # Endpoints de conversión
├── model/                                   # Modelos de datos
│   ├── ConversionRequest.java               # Modelo para solicitud
│   └── ConversionResponse.java              # Modelo para respuesta
├── security/                                # Componentes de seguridad
│   └── ApiKeyFilter.java                    # Filtro de autenticación
└── service/                                 # Servicios
    └── CurrencyConversionService.java       # Lógica de negocio
```
## Diagrama de flujo
![image](https://github.com/user-attachments/assets/4f4866e4-c202-4cf6-960e-b68eb9bad1d7)


### Componentes Principales

#### 1. Aplicación Principal
**Clase**: `ConversionDivisasPruebaApplication`
- Punto de entrada de la aplicación Spring Boot
- Configuración del `RestTemplate` para llamadas HTTP

```java
@SpringBootApplication
public class ConversionDivisasPruebaApplication {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

#### 2. Configuración de Seguridad
**Clase**: `SecurityConfig`
- Configuración de seguridad de Spring
- Gestión de filtros de API key
- Configuración CORS y CSRF

Configuración clave:
```java
private static final String API_KEY = "mi-clave-api-segura-123";
```

#### 3. Controlador de Conversión
**Clase**: `CurrencyConversionController`
- Endpoint: `/api/conversion`
- Método: `POST`
- Maneja las solicitudes de conversión de divisas

#### 4. Servicio de Conversión
**Clase**: `CurrencyConversionService`
- Lógica de negocio para la conversión de divisas
- Integración con API externa de tipos de cambio
- Manejo de errores y validaciones

#### 5. Modelos de Datos

##### Request Model
**Clase**: `ConversionRequest`
```java
{
    double amount;            // Cantidad a convertir
    String sourceCurrency;    // Moneda origen
    String targetCurrency;    // Moneda destino
}
```

##### Response Model
**Clase**: `ConversionResponse`
```java
{
    String date;             // Fecha de la conversión
    double conversionRate;   // Tasa de cambio
    double originalAmount;   // Cantidad original
    double convertedAmount;  // Cantidad convertida
}
```

## Seguridad

### Autenticación por API Key
- Header requerido: `X-API-KEY`
- Valor: `mi-clave-api-segura-123`
- Implementado en `ApiKeyFilter`

Códigos de respuesta:
- 401: API key faltante
- 403: API key inválida
- 200: API key válida

## API Endpoints

### Conversión de Divisas
**Endpoint**: `/api/conversion`
**Método**: POST

#### Request
```json
{
    "amount": 100.0,
    "sourceCurrency": "EUR",
    "targetCurrency": "USD"
}
```

#### Response Exitosa (200 OK)
```json
{
    "date": "2024-01-16",
    "conversionRate": 1.1,
    "originalAmount": 100.0,
    "convertedAmount": 110.0
}
```

#### Errores Posibles
- 400 Bad Request: Código de moneda inválido
- 503 Service Unavailable: Error al obtener tipos de cambio
- 500 Internal Server Error: Error general del servidor

## Integración Externa

### API de Tipos de Cambio
- URL: `http://api.exchangeratesapi.io/v1/latest`
- API Key requerida en la URL
- Proporciona tasas de cambio actualizadas

## Validaciones
1. **Cantidad**
   - Debe ser mayor o igual a 0
   - Validación mediante `@Min(0)`

2. **Códigos de Moneda**
   - No pueden estar vacíos
   - Validación mediante `@NotBlank`

## Pruebas

### Pruebas Unitarias
Ubicadas en `src/test/java`:
1. `CurrencyConversionServiceTest`
   - Pruebas de conversión exitosa
   - Pruebas de manejo de errores
   - Pruebas de validación de monedas

2. `ApiKeyFilterTest`
   - Pruebas de validación de API key
   - Pruebas de seguridad

### Ejecución de Pruebas
```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar prueba específica
mvn test -Dtest=CurrencyConversionServiceTest
```

## Dependencias Principales
```xml
<dependencies>
    <!-- Spring Boot -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- Spring Security -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    
    <!-- Validación -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    
    <!-- Pruebas -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Configuración y Despliegue

### Requisitos
- Java 17 o superior
- Maven 3.6.3 o superior
- API key válida para exchangeratesapi.io

### Pasos de Instalación
1. Clonar el repositorio
2. Configurar la API key en `CurrencyConversionService`
3. Ejecutar:
   ```bash
   mvn clean install
   ```
4. Iniciar la aplicación:
   ```bash
   mvn spring-boot:run
   ```

### Variables de Entorno Recomendadas
- `API_KEY`: Clave para la autenticación
- `EXCHANGE_API_KEY`: Clave para exchangeratesapi.io

## Manejo de Errores
La aplicación implementa un manejo de errores global que incluye:
1. Validación de entrada
2. Errores de API externa
3. Errores de conversión
4. Errores de autenticación

Cada error retorna un ResponseEntity con:
- Código de estado HTTP apropiado
- Mensaje de error descriptivo
- Detalles adicionales cuando es relevante

## Mejoras Futuras Sugeridas
1. Implementar caché de tipos de cambio
2. Agregar más endpoints para:
   - Listar monedas disponibles
   - Obtener histórico de tipos de cambio
3. Implementar rate limiting
4. Agregar métricas y monitoreo
5. Mejorar la documentación con Swagger/OpenAPI
