package com.ecommerce.commons.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public record PedidosResponse (
        Long idPedidos,
        Long idCliente,
        String cliente,
        Double total,
        String fechaCreacion,
        String estado,
        List<Map<String, Object>> productos
) {
}
