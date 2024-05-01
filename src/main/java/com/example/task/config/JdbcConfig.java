package com.example.task.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConfig {
  private static final String PROPERTY_FILE = "application.properties";

  public static Connection getConnection() throws SQLException {
    try (InputStream input = JdbcConfig.class.getClassLoader().getResourceAsStream(PROPERTY_FILE)) {
      Properties properties = new Properties();
      properties.load(input);

      String url = properties.getProperty("db.url");
      String username = properties.getProperty("db.username");
      String password = properties.getProperty("db.password");

      return DriverManager.getConnection(url, username, password);
    } catch (IOException e) {
      throw new SQLException("Error loading database properties", e);
    }
  }
}
