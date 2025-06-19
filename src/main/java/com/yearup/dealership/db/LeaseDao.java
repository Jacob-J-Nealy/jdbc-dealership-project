package com.yearup.dealership.db;

import com.yearup.dealership.models.LeaseContract;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LeaseDao {
    private DataSource dataSource;

    public LeaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addLeaseContract(LeaseContract leaseContract) {

        String query = """
    INSERT INTO lease_contract
    (VIN, lease_start_date, lease_end_date, monthly_payment)
    VALUES (?, ?, ?, ?)
    """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, leaseContract.getVin());
            preparedStatement.setDate(2, java.sql.Date.valueOf(leaseContract.getLeaseStart()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(leaseContract.getLeaseEnd()));
            preparedStatement.setDouble(4, leaseContract.getMonthlyPayment());

        } catch (Exception e) {
            System.err.println("Error");
        }
    }
}
