package com.ecommerce.msvpedidos.repositories;

import com.ecommerce.msvpedidos.entities.Pedido;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidosRepository extends JpaRepository<Pedido, Long> {
	@Query(nativeQuery = true, value = "SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END FROM PEDIDOS WHERE ID_CLIENTE = :idCliente")
	int existsByIdCliente(@Param("idCliente") Long idCliente);


	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM PRODUCTOS_PEDIDOS WHERE ID_PRODUCTOS = :idProducto")
int existsByIdProducto(Long idProducto);




}
