package com.ecommerce.msvpedidos.repositories;

import com.ecommerce.msvpedidos.entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidosRepository extends JpaRepository<Pedido, Long> {

}
