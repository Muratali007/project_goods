package com.example.task.service;

import com.example.task.config.JdbcConfig;
import com.example.task.data.entity.Goods;
import jakarta.ejb.Stateless;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class GoodsService {

  public void saveGoods(Goods goods) {
    try (Connection conn = JdbcConfig.getConnection()) {
      String sql = "INSERT INTO goods (name, price, quantity, description) VALUES (?,?,?,?)";
      try (PreparedStatement statement =  conn.prepareStatement(sql)) {
        statement.setString(1, goods.getName());
        statement.setDouble(2, goods.getPrice());
        statement.setInt(3, goods.getQuantity());
        statement.setString(4, goods.getDescription());
        statement.executeUpdate();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }


  public List<Goods> getAllGoods() throws SQLException {
    List<Goods> goodsList = new ArrayList<>();
    try (Connection connection = JdbcConfig.getConnection()) {
      String sql = "SELECT * FROM goods";
      try (PreparedStatement statement = connection.prepareStatement(sql)) {
        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {
            Goods goods = new Goods();
            goods.setId(resultSet.getLong("id"));
            goods.setName(resultSet.getString("name"));
            goods.setDescription(resultSet.getString("description"));
            goods.setCreatedDate(resultSet.getDate("created_date"));
            goods.setPrice(resultSet.getDouble("price"));
            goods.setQuantity(resultSet.getInt("quantity"));
            goodsList.add(goods);
          }
        }
      }
    }
    return goodsList;
  }

  public Goods getGoodsById(Long id) throws SQLException {
    String sql = "SELECT * FROM goods WHERE id=?";
    try (Connection connection = JdbcConfig.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (ResultSet resultSet = statement.executeQuery()) {
        if (resultSet.next()) {
          Goods goods = new Goods();
          goods.setId(resultSet.getLong("id"));
          goods.setName(resultSet.getString("name"));
          goods.setDescription(resultSet.getString("description"));
          goods.setCreatedDate(resultSet.getDate("created_date"));
          goods.setPrice(resultSet.getDouble("price"));
          goods.setQuantity(resultSet.getInt("quantity"));
          return goods;
        } else {
          throw new SQLException("Goods not found with ID: " + id);
        }
      }
    }
  }

  public void updateGoods(Goods goods) throws SQLException {
    StringBuilder sqlBuilder = new StringBuilder("UPDATE goods SET");

    if (goods.getName() != null) {
      sqlBuilder.append(" name=?,");
    }

    if (goods.getPrice() != 0) {
      sqlBuilder.append(" price=?,");
    }

    if (goods.getQuantity() != 0) {
      sqlBuilder.append(" quantity=?,");
    }

    sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);

    sqlBuilder.append(", update_date=? WHERE id=?");

    try (Connection connection = JdbcConfig.getConnection();
         PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString())) {

      int parameterIndex = 1;

      if (goods.getName() != null) {
        statement.setString(parameterIndex++, goods.getName());
      }

      if (goods.getPrice() != 0) {
        statement.setDouble(parameterIndex++, goods.getPrice());
      }

      if (goods.getQuantity() != 0) {
        statement.setInt(parameterIndex++, goods.getQuantity());
      }


      statement.setTimestamp(parameterIndex++, new java.sql.Timestamp(new Date().getTime()));


      statement.setLong(parameterIndex, goods.getId());

      statement.executeUpdate();
    }
  }
}
