package pl.coderslab.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.DbUtil;
import pl.coderslab.entity.Product;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ProductDao {

private static final String CREATE_PRODUCT_QUERY = "INSERT INTO product (name, price, description, quantity) VALUES (?, ?, ?, ?);";
private static final String DELETE_PRODUCT_QUERY = "DELETE FROM product WHERE id = ?;";
private static final String FIND_ALL_PRODUCTS_QUERY = "SELECT * FROM product;";
private static final String READ_PRODUCT_QUERY = "SELECT * FROM product WHERE id = ?;";
private static final String UPDATE_PRODUCT_QUERY = "UPDATE product SET name = ?, price = ?, description = ?, quantity = ? WHERE id = ?;";
private static final String FIND_PRODUCT_BY_NAME_QUERY = "SELECT * FROM product WHERE name = ?;";


public Product read(Integer productId) {
    Product product = new Product();
    try(Connection connection = DbUtil.getConnection();
        PreparedStatement statement = connection.prepareStatement(READ_PRODUCT_QUERY)) {
        statement.setInt(1, productId);
        try(ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return product;
}

public Product addProduct(Product product) {
    try (Connection connection = DbUtil.getConnection();
    PreparedStatement insertStm = connection.prepareStatement(CREATE_PRODUCT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
        insertStm.setString(1, product.getName());
        insertStm.setDouble(2, product.getPrice());
        insertStm.setString(3, product.getDescription());
        insertStm.setInt(4, product.getQuantity());

        int result = insertStm.executeUpdate();

        if(result != 1) {
            throw new RuntimeException("Execute update returned " + result);
        }

        try (ResultSet generateKeys = insertStm.getGeneratedKeys()) {
            if (generateKeys.first()) {
                product.setId(generateKeys.getInt(1));
                return product;
            } else {
                throw new RuntimeException("Generated key was not found");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

public List<Product> findAll() {
    List<Product> productList = new ArrayList<>();
    try (Connection connection = DbUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(FIND_ALL_PRODUCTS_QUERY);
         ResultSet resultSet = statement.executeQuery()) {

        while( resultSet.next()) {
            Product product = new Product();
            product.setId(resultSet.getInt("id"));
            product.setName(resultSet.getString("name"));
            product.setPrice(resultSet.getDouble("price"));
            product.setDescription(resultSet.getString("description"));
            product.setQuantity(resultSet.getInt("quantity"));
            productList.add(product);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return productList;
}

public void updateProduct (Product product) {
    try (Connection connection = DbUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_QUERY)) {
        statement.setInt(1, product.getId());
        statement.setString(2, product.getName());
        statement.setDouble(3, product.getPrice());
        statement.setString(4, product.getDescription());
        statement.setInt(5, product.getQuantity());

        statement.executeUpdate();
    }catch (Exception e) {
        e.printStackTrace();
    }
}

public void deleteProduct (Integer productId) {
    try (Connection connection = DbUtil.getConnection();
    PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_QUERY)){
    statement.setInt(1, productId);
    statement.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

public Product findProductByName (String name) {
    Product product = null;
    try (Connection connection = DbUtil.getConnection();
         PreparedStatement statement = connection.prepareStatement(FIND_PRODUCT_BY_NAME_QUERY)) {
        statement.setString(1, name);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                product = new Product();
                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                product.setDescription(resultSet.getString("description"));
                product.setQuantity(resultSet.getInt("quantity"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return product;
}
}
