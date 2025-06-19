package com.yearup.dealership.db;

import com.yearup.dealership.models.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicle(Vehicle vehicle) {

        String query = """
                INSERT INTO vehicles (VIN, Make, Model, Year, Sold, color, vehicleType, odometer, price
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        // Try-Catch Connection
        try (Connection connection = dataSource.getConnection();

             // Connecting SQL String to Prepared Statement
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Settings Values to  New Vehicle Attributes
            preparedStatement.setString( 1, vehicle.getVin());
            preparedStatement.setString( 2, vehicle.getMake());
            preparedStatement.setString( 3, vehicle.getModel());
            preparedStatement.setInt(    4, vehicle.getYear());
            preparedStatement.setBoolean(5, vehicle.isSold());
            preparedStatement.setString( 6, vehicle.getColor());
            preparedStatement.setString( 7, vehicle.getVehicleType());
            preparedStatement.setInt(    8, vehicle.getOdometer());
            preparedStatement.setDouble( 9,vehicle.getPrice());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("error");
        }
    }

    public void removeVehicle(String VIN) {

        String query = """
                DELETE FROM vehicles
                WHERE vin = ? 
                """;
        // Try-Catch Connection
        try (Connection connection = dataSource.getConnection();

             // Connecting SQL String to Prepared Statement
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Settings Values to  New Vehicle Attributes
            preparedStatement.setString( 1, VIN);

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error");
        }

    public List<Vehicle> searchByPriceRange(double minPrice, double maxPrice) {
        // TODO: Implement the logic to search vehicles by price range
        return new ArrayList<>();
    }

    public List<Vehicle> searchByMakeModel(String make, String model) {
        // TODO: Implement the logic to search vehicles by make and model
        return new ArrayList<>();
    }

    public List<Vehicle> searchByYearRange(int minYear, int maxYear) {
        // TODO: Implement the logic to search vehicles by year range
        return new ArrayList<>();
    }

    public List<Vehicle> searchByColor(String color) {
        // TODO: Implement the logic to search vehicles by color
        return new ArrayList<>();
    }

    public List<Vehicle> searchByMileageRange(int minMileage, int maxMileage) {
        // TODO: Implement the logic to search vehicles by mileage range
        return new ArrayList<>();
    }

    public List<Vehicle> searchByType(String type) {
        // TODO: Implement the logic to search vehicles by type
        return new ArrayList<>();
    }

    private Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(resultSet.getString("VIN"));
        vehicle.setMake(resultSet.getString("make"));
        vehicle.setModel(resultSet.getString("model"));
        vehicle.setYear(resultSet.getInt("year"));
        vehicle.setSold(resultSet.getBoolean("SOLD"));
        vehicle.setColor(resultSet.getString("color"));
        vehicle.setVehicleType(resultSet.getString("vehicleType"));
        vehicle.setOdometer(resultSet.getInt("odometer"));
        vehicle.setPrice(resultSet.getDouble("price"));
        return vehicle;
    }
}
