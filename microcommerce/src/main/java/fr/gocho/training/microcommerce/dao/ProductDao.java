package fr.gocho.training.microcommerce.dao;

import fr.gocho.training.microcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Integer>{

//    public List<Product> findAll();
    Product findById(int id);
    List<Product> findByPriceGreaterThan(int price);
    List<Product> findByNameLike(String request);


//    public Product save(Product product);
}
