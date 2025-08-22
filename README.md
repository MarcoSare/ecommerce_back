# Proyecto de Microservicios con Spring Boot

Este proyecto implementa una arquitectura de microservicios utilizando **Spring Boot**, con integración de servicios independientes que se comunican entre sí.  

## Estructura del Proyecto

La solución está organizada en varios microservicios:

- `msv-clientes` → Gestión de clientes  
- `msv-productos` → Gestión de productos  
- `msv-pedidos` → Gestión de pedidos  
- `msv-gateway` → API Gateway (enrutamiento y seguridad)
- `authorization-server` → Servicio encargado de emitir tokens
- `msv-eureka` → Servidor de descubrimiento de servicios
- `commons` → Recursos comunes entre los diferentes microservicios  

*(Ajustar según tus módulos reales)*

## Requisitos Previos

Antes de levantar el proyecto, asegúrate de tener instalado:

- [Java 17+](https://adoptium.net/)  
- [Maven 3.9+](https://maven.apache.org/)  
- [Docker](https://www.docker.com/) y **Docker Compose** (opcional, para bases de datos y servicios externos)  
- [Git](https://git-scm.com/)  

## Configuración

1. Clona el repositorio:
   ```bash
   https://github.com/MarcoSare/ecommerce_back.git
   cd ecommerce_back
2. Configura las variables de entorno (por ejemplo, credenciales de DB, JWT secret, etc.) en cada application.yml o usando variables de entorno.
3. Levantar los Microservicios
    Cada microservicio es independiente. Puedes iniciarlos de dos formas:
    * Opción 1: Desde la terminal
        Ejemplo con el servicio de clientes:
          cd msv-clientes
          mvn spring-boot:run
   * Opción 2: Desde el IDEA
        Ejemplo: 
          Importa el proyecto en IntelliJ IDEA o Eclipse y levanta cada servicio desde la clase main.
4. Endpoints Principales
    Una vez levantado el proyecto:
      * Clientes Service: http://localhost:8090/api/clientes
      * Productos Service: http://localhost:8090/api/productos
      * Pedidos Service: http://localhost:8090/api/pedidos
      * Usuarios Service: http://localhost:9000/admin/usuarios
      * Authorization Service: http://localhost:9000/api/login



