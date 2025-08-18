package com.ecommerce.productos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.productos.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
