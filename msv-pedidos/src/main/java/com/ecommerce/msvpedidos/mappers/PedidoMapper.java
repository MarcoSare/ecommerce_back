package com.ecommerce.msvpedidos.mappers;

import com.ecommerce.commons.dto.ClientesResponse;
import com.ecommerce.commons.dto.PedidosRequest;
import com.ecommerce.commons.dto.PedidosResponse;
import com.ecommerce.commons.dto.ProductoResponse;
import com.ecommerce.commons.mappers.CommonMapper;
import com.ecommerce.msvpedidos.clients.ClientesClients;
import com.ecommerce.msvpedidos.clients.ProductoClient;
import com.ecommerce.msvpedidos.entities.Pedido;
import com.ecommerce.msvpedidos.entities.Producto;
import com.ecommerce.msvpedidos.entities.ProductoPedido;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class PedidoMapper extends CommonMapper<PedidosRequest, PedidosResponse, Pedido> {

    private ProductoClient productoClient;
    private ClientesClients clientesClients;

    public PedidoMapper(ProductoClient productoClient,  ClientesClients clientesClients) {
        this.productoClient = productoClient;
        this.clientesClients = clientesClients;
    }

    @Override
    public Pedido requestToEntity(PedidosRequest request) {
        if (request == null) {
            return null;
        }

        Pedido pedido = new Pedido();
        pedido.setIdCliente(request.idCliente());
        pedido.setEstado(request.estado());
        //pedido.setFechaCreacion(request.fechaCreacion());
        Set<ProductoPedido> productosPedido = new HashSet<>();

        request.productos().forEach(item -> {
            ProductoResponse productoResponse = productoClient.getProductoById(item.idProducto());
            ProductoPedido pp = new ProductoPedido();
            pp.setPedido(pedido);
            pp.setIdProducto(productoResponse.id());
            pp.setCantidad(item.cantidad());
            pp.setPrecio(item.precio());
            productosPedido.add(pp);
        });
        pedido.setProductos(productosPedido);
        return pedido;
    }


    @Override
    public PedidosResponse entityToResponse(Pedido entity) {
        if (entity == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = formatter.format(entity.getFechaCreacion());
        ClientesResponse clientesResponse = clientesClients.getClienteById(entity.getIdCliente());
        String cliente = clientesResponse.nombre() + " " +  clientesResponse.apellido();


        double total = entity.getProductos()
                .stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidad())
                .sum();

        List<Map<String, Object>> productos = entity.getProductos()
                .stream()
                .map(p -> {
                    ProductoResponse productoResponse = productoClient.getProductoById(p.getIdProducto());
                    return Map.<String, Object>of(
                            "id", p.getIdProducto(),
                            "nombre", productoResponse.nombre(),
                            "descripcion", productoResponse.descripcion(),
                            "precio", p.getPrecio(),
                            "cantidad", p.getCantidad()
                    );
                })
                .toList();



        PedidosResponse response = new PedidosResponse(
                entity.getId(),
                entity.getIdCliente(),
                cliente,
                total,
                fechaFormateada,
                entity.getEstado(),
                productos
        );
        return response;
    }


    private Producto productoResponseToProducto(ProductoResponse response) {
        if(response == null) {
            return null;
        }

        Producto producto = new Producto();
        producto.setId(response.id());
        producto.setNombre(response.nombre());
        producto.setDescripcion(response.descripcion());
        producto.setPrecio(response.precio());
        producto.setStock(response.stock());
        return producto;
    }
}
