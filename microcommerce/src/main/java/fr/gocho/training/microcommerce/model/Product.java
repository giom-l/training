package fr.gocho.training.microcommerce.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;

import static lombok.AccessLevel.PUBLIC;

@NoArgsConstructor
@AllArgsConstructor
//@JsonFilter("noDisplayFields")
@Entity
public class Product {
    @Getter @Setter
    @Id
    @GeneratedValue
    @Column(insertable=false)
    private int id;
    @Getter @Setter
    @Length(min = 3, max = 20, message = "La taille du paramètre n'est pas comprise entre 3 et 20 caractères")
    private String name;
    @Getter @Setter
    @Min(value = 1)
    private int price;
    @Getter @Setter
    private int priceBought;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nom='" + name + '\'' +
                ", prix=" + price + '}'
                ;
    }
}

