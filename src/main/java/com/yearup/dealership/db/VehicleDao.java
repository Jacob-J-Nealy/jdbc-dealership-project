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
                INSERT INTO vehicles (VIN, Make, Model, Year, Sold, color, vehicleType, odometer, price)
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
            preparedStatement.setString(1, VIN);

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.err.println("Error");
        }
    }

    public List<Vehicle> searchByPriceRange(double minPrice, double maxPrice) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT *
                FROM vehicles
                WHERE price
                BETWEEN ? AND ?
                """;

        // Try-Catch Connection
        try (Connection connection = dataSource.getConnection();

             // Connecting SQL String to Prepared Statement
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Settings Values to  New Vehicle Attributes
            preparedStatement.setDouble(1, minPrice);
            preparedStatement.setDouble(2, maxPrice);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }

            }

        } catch (Exception e) {
            System.err.println("Error");
        }

        return vehicles;
    }

    public List<Vehicle> searchByMakeModel(String make, String model) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT *
                FROM vehicles
                WHERE make = ? AND model = ?
                """;

        // Try-Catch Connection
        try (Connection connection = dataSource.getConnection();

             // Connecting SQL String to Prepared Statement
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Settings Values to  New Vehicle Attributes
            preparedStatement.setString(1, make);
            preparedStatement.setString(2, model);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Vehicle vehicle = createVehicleFromResultSet(resultSet);
                    vehicles.add(vehicle);
                }

            }

        } catch (Exception e) {
            System.err.println("Error");
        }

        return vehicles;
    }

    public List<Vehicle> searchByYearRange(int minYear, int maxYear) {

        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT *
                FROM  vehicles
                WHERE year
                BETWEEN ? AND ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, minYear);
            preparedStatement.setInt(2, maxYear);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(createVehicleFromResultSet(resultSet));
                }
            }

        } catch (Exception e) {
            System.err.println();
        }

        return vehicles;
    }

    public List<Vehicle> searchByColor(String color) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT *
                FROM  vehicles
                WHERE color = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, color);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(createVehicleFromResultSet(resultSet));
                }
            }

        } catch (Exception e) {
            System.err.println();
        }

        return vehicles;
    }

    public List<Vehicle> searchByMileageRange(int minMileage, int maxMileage) {
        List<Vehicle> vehicles = new ArrayList<>();

        String query = """
                SELECT *
                FROM  vehicles
                WHERE odometer
                BETWEEN ? AND ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, minMileage);
            preparedStatement.setInt(2, maxMileage);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(createVehicleFromResultSet(resultSet));
                }
            }

        } catch (Exception e) {
            System.err.println("Error");
        }
        return vehicles;
    }

    public List<Vehicle> searchByType(String type) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = """
        SELECT * 
        FROM vehicles 
        WHERE vehicleType = ?
        """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, type);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    vehicles.add(createVehicleFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
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
