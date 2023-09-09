package pl.coderslab.dao;

import org.springframework.stereotype.Repository;
import pl.coderslab.DbUtil;
import pl.coderslab.entity.CartItem;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class CartItemDao {

    private static final String CREATE_CART_ITEM_QUERY = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?);";
    private static final String DELETE_CART_ITEM_QUERY = "DELETE FROM cart WHERE id = ?;";
    private static final String FIND_ALL_CART_ITEMS_QUERY = "SELECT * FROM cart;";
    private static final String READ_CART_ITEM_QUERY = "SELECT * FROM cart WHERE id = ?;";
    private static final String UPDATE_CART_ITEM_QUERY = "UPDATE cart SET user_id = ?, product_id = ?, quantity = ? WHERE id = ?;";

    public CartItem read(Integer cartItemId) {
        CartItem cartItem = new CartItem();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(READ_CART_ITEM_QUERY)) {
            statement.setInt(1, cartItemId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cartItem.setId(resultSet.getInt("id"));
                    cartItem.setUserId(resultSet.getInt("userId"));
                    cartItem.setProductId(resultSet.getInt("productId"));
                    cartItem.setQuantity(resultSet.getInt("quantity"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cartItem;
    }

    public CartItem addCartItem(CartItem cartItem) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(CREATE_CART_ITEM_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertStm.setInt(1, cartItem.getUserId());
            insertStm.setInt(2, cartItem.getProductId());
            insertStm.setInt(3, cartItem.getQuantity());

            int result = insertStm.executeUpdate();

            if (result != 1) {
                throw new RuntimeException("Execute update returned " + result);
            }

            try (ResultSet generateKeys = insertStm.getGeneratedKeys()) {
                if (generateKeys.first()) {
                    cartItem.setId(generateKeys.getInt(1));
                    return cartItem;
                } else {
                    throw new RuntimeException("Generated key was not found");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<CartItem> findAll() {
        List<CartItem> cartItemList = new ArrayList<>();
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_CART_ITEMS_QUERY);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setId(resultSet.getInt("id"));
                cartItem.setUserId(resultSet.getInt("userId"));
                cartItem.setProductId(resultSet.getInt("productId"));
                cartItem.setQuantity(resultSet.getInt("quantity"));
                cartItemList.add(cartItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItemList;
    }

    public void updateCartItem(CartItem cartItem) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CART_ITEM_QUERY)) {
            statement.setInt(1, cartItem.getUserId());
            statement.setInt(2, cartItem.getProductId());
            statement.setInt(3, cartItem.getQuantity());
            statement.setInt(4, cartItem.getId());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCartItem(Integer cartItemId) {
        try (Connection connection = DbUtil.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CART_ITEM_QUERY)) {
            statement.setInt(1, cartItemId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}