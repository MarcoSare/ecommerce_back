package com.ecommerce.commons.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public record PedidosResponse (
        Long idPedidos,
        Long idCliente,
        Double total,
        Date fechaCreacion,
        String estado,
        List<Map<String, Object>> productos
) {
}
