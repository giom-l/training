package fr.gocho.training.microcommerce.dao;

import fr.gocho.training.microcommerce.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoImpl_obsolete { //}} implements ProductDao {

    private static List<Product> products = new ArrayList<>();

    static {
        products.add(new Product(1, "Ordinateur portable", 350, 120));
        products.add(new Product(2, "Aspirateur Robot", 500, 200));
        products.add(new Product(3, "Table de Ping Pong", 750, 400));
    }

 //   @Override
    public List<Product> findAll() {
        return products;
    }

  //  @Override
    public Product findById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

  //  @Override
    public Product save(Product product) {
        products.add(product);
        return product;
    }
}
