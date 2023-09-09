package pl.coderslab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.coderslab.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductById(Integer id);
    Product findProductByName(String name);
    Product findProductByPrice(Double price);


    @Query("select p from Product p where p.id = :id")
    List<Product> getProductById(@Param("id") Integer id);

    @Query("select p from Product p where p.name = :name")
    List<Product> getProductByName(@Param("name") String name);

    @Query("select p from Product p where  p.price = :price")
    List<Product> getProductByPrice(@Param("price") double price);

}
