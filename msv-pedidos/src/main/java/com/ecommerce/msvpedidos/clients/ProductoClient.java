package com.ecommerce.msvpedidos.clients;
import com.ecommerce.commons.configuration.FeignClientConfig;
import com.ecommerce.commons.dto.ProductoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msv-productos", configuration = FeignClientConfig.class)
    public interface ProductoClient {
        @GetMapping("/{id}")
        ProductoResponse getProductoById(@PathVariable Long id);
        //CategoriaResponse getCategoriaById(@PathVariable Long id);
    }

