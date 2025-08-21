package com.ecommerce.msvpedidos.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCTOS_PEDIDOS")
public class ProductoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTOS_PEDIDOS_SEQ")
    @SequenceGenerator(name = "PRODUCTOS_PEDIDOS_SEQ", sequenceName = "PRODUCTOS_PEDIDOS_SEQ", allocationSize = 1)
    @Column(name = "ID_PRODUCTOS_PEDIDOS")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PEDIDOS", nullable = false)
    private Pedido pedido;

    @Column(name = "ID_PRODUCTOS", nullable = false)
    private Long idProducto; // FK al producto (puede ser relación @ManyToOne si compartes la entidad Producto)

    @Column(name = "CANTIDAD", nullable = false)
    private Integer cantidad;

    @Column(name = "PRECIO", nullable = false)
    private Double precio;

    public ProductoPedido() {}

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }

    public Long getIdProducto() { return idProducto; }
    public void setIdProducto(Long idProducto) { this.idProducto = idProducto; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
}
