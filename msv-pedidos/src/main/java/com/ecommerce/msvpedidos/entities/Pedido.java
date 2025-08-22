package com.ecommerce.msvpedidos.entities;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PEDIDOS")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEDIDOS_SEQ")
    @SequenceGenerator(name = "PEDIDOS_SEQ", sequenceName = "PEDIDOS_SEQ", allocationSize = 1)
    @Column(name = "ID_PEDIDOS")
    private Long id;

    @Column(name = "ID_CLIENTE", nullable = false)
    private Long idCliente;

    @Column(name = "TOTAL", insertable = false, updatable = false)
    private Double total;

    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "ESTADO", nullable = false, length = 20)
    private String estado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProductoPedido> productos = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        if (this.fechaCreacion == null) {
            this.fechaCreacion = new Date();
        }
    }
    public Pedido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Set<ProductoPedido> getProductos() {
        return productos;
    }

    public void setProductos(Set<ProductoPedido> productos) {
        this.productos = productos;
    }
}
