package com.yearup.dealership.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryDao {
    private DataSource dataSource;

    public InventoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicleToInventory(String vin, int dealershipId) {

        // SQL String
        String query = """
                INSERT INTO inventory (vin, dealership_id)
                VALUES (?,?)
                """;

        // Try-Catch Connection
        try (Connection connection = dataSource.getConnection();

             // Connecting SQL String to Prepared Statement
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Settings Values to
            preparedStatement.setString(1, vin);
            preparedStatement.setInt(2, dealershipId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("error");
        }
    }

    public void removeVehicleFromInventory(String vin) {

        // SQL String
        String query = """
                DELETE FROM inventory
                WHERE vin = ?
                """;

        // Try-Catch Connection
        try (Connection connection = dataSource.getConnection();

             // Connecting SQL String to Prepared Statement
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Settings Values to
            preparedStatement.setString(1, vin);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("error");
        }




    }
}
