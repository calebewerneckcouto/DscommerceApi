package com.devsuperior.dscommerce.services;

import com.devsuperior.dscommerce.ProductsRepository;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.services.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
	@Autowired
	private ProductsRepository productsRepository;

	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {

		Product product = productsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Recurso nao encontrado!"));
		return new ProductDTO(product);

	}

	@Transactional(readOnly = true)
	public Page<ProductDTO> findAll(Pageable pageable) {

		Page<Product> result = productsRepository.findAll(pageable);
		return result.map(x -> new ProductDTO(x));

	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {

		Product entity = new Product();
		copyDtoToEntity(dto, entity);

		entity = productsRepository.save(entity);
		return new ProductDTO(entity);

	}

	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {

		Product entity = productsRepository.getReferenceById(id);
		copyDtoToEntity(dto, entity);

		entity = productsRepository.save(entity);
		return new ProductDTO(entity);

	}
	
	
	@Transactional
	public void delete(Long id) {

		productsRepository.deleteById(id);
		

	}

	private void copyDtoToEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setImgUrl(dto.getImgUrl());

	}

}