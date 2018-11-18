package fr.gocho.training.microcommerce.web.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import fr.gocho.training.microcommerce.dao.ProductDao;
import fr.gocho.training.microcommerce.model.Product;
import fr.gocho.training.microcommerce.web.exceptions.ProductNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(description = "API pour la manipulation des produits")
@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    @RequestMapping(value="/Produits", method = RequestMethod.GET)
    public MappingJacksonValue listeProduits(){
        Iterable<Product> lstProduct =  productDao.findAll();
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.serializeAllExcept("priceBought");
        FilterProvider filterList = new SimpleFilterProvider().addFilter("noDisplayFields",filter);
        MappingJacksonValue filteredProducts = new MappingJacksonValue(lstProduct);
        filteredProducts.setFilters(filterList);
        return filteredProducts;
    }

    @ApiOperation(value= "Affiche le produit ayant l'ID demandé, à condition que celui ci existe")
    @GetMapping(value="/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id){
        Product p =  productDao.findById(id);
        if(p == null){
            throw new ProductNotFoundException(String.format("The product with id %d has not been found. I would BSOD if I could.",id));
        }
        return p;
    }

    @GetMapping(value="newFeature/Produits/byPrice/{prixLimite}")
    public List<Product> listProduitsPrixInferieurA(@PathVariable int prixLimite){
        return productDao.findByPriceGreaterThan(prixLimite);
    }

    @GetMapping(value="newFeature/Produits/byName/{nameSearched}")
    public List<Product> listProduitsAvecNomComme(@PathVariable String nameSearched){
        return productDao.findByNameLike("%"+nameSearched+"%");
    }

    @DeleteMapping(value="Produits/{id}")
    public void supprProduit(@PathVariable int id){
        productDao.deleteById(id);
    }

    @PutMapping(value = "/Produits")
    public void updateProduit(@RequestBody Product product){
        productDao.save(product);
    }

    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product){
        Product productAdded = productDao.save(product);
        if (productAdded == null){
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
