package com.ecommerce.commons.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record PedidosRequest (
        @NotNull(message = "El id del cliente es obligatorio")
        Long idCliente,
        @NotBlank(message = "El estado no puede ir en blanclo o vacio")
        String estado,
        @NotNull(message = "La lista de productos es requerida")
        @Size(min = 1, message = "Debe haber al menos un producto")
        List<ProductoData> productos
){

        public record ProductoData(
                @NotNull(message = "El id del producto es obligatorio")
                Long idProducto,

                @NotNull(message = "La cantidad es obligatoria")
                @Min(value = 1, message = "La cantidad debe ser al menos 1")
                Integer cantidad,

                @NotNull(message = "El precio es obligatorio")
                @Positive(message = "El precio debe ser positivo")
                Double precio


        ) {}


}
