package com.devsuperior.dscommerce;

import com.devsuperior.dscommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository <Product,Long> {
}
