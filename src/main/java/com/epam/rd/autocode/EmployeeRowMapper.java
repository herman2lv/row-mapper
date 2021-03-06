package com.epam.rd.autocode;

import com.epam.rd.autocode.domain.Employee;
import com.epam.rd.autocode.domain.FullName;
import com.epam.rd.autocode.domain.Position;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class EmployeeRowMapper implements RowMapper<Employee> {
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SALARY = "salary";
    private static final String COLUMN_HIREDATE = "hiredate";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_MIDDLENAME = "middlename";
    private static final String COLUMN_POSITION = "position";

    @Override
    public Employee mapRow(ResultSet resultSet) {
        try {
            Position position = getPosition(resultSet);
            FullName fullName = getFullName(resultSet);
            BigInteger id = BigInteger.valueOf(resultSet.getInt(COLUMN_ID));
            LocalDate hired = getHired(resultSet);
            BigDecimal salary = resultSet.getBigDecimal(COLUMN_SALARY);
            return new Employee(id, fullName, position, hired, salary);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private LocalDate getHired(ResultSet resultSet) throws SQLException {
        Date rawDate = resultSet.getDate(COLUMN_HIREDATE);
        return LocalDate.parse(rawDate.toString());
    }

    private FullName getFullName(ResultSet resultSet) throws SQLException {
        String firstName = resultSet.getString(COLUMN_FIRSTNAME);
        String lastName = resultSet.getString(COLUMN_LASTNAME);
        String middleName = resultSet.getString(COLUMN_MIDDLENAME);
        return new FullName(firstName, lastName, middleName);
    }

    private Position getPosition(ResultSet resultSet) throws SQLException {
        String rawPosition = resultSet.getString(COLUMN_POSITION);
        return Position.valueOf(rawPosition.toUpperCase());
    }
}
