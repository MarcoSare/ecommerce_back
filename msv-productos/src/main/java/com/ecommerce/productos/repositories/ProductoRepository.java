package com.ecommerce.productos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.productos.entities.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
