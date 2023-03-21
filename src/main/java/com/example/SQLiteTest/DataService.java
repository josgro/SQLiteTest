package com.example.SQLiteTest;


import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {


    SQLiteDataSource dataSource;

    public DataService() {
        this.dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:test.db");
        initialiseDB();
    }

    public void createNewDatabase() {

        String url = "jdbc:sqlite:test.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void initialiseDB() {
        String sql = """
                CREATE TABLE IF NOT EXISTS MailCourse (
                 id INTEGER PRIMARY KEY AUTOINCREMENT,
                 email TEXT,
                 course TEXT,
                 status TEXT
                );""";
        try (Connection connection = this.dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Database creation failed...");
            System.out.println(e.getMessage());
        }
    }

    public List<MailCourseStatus> read() {
        List<MailCourseStatus> list = new ArrayList<>();

        String sql = "SELECT * FROM MailCourse";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                String email = result.getString("email");
                String course = result.getString("course");
                MailCourseStatus mailCourseStatus = new MailCourseStatus();
                mailCourseStatus.setEmail(email);
                mailCourseStatus.setCourse(course);
                list.add(mailCourseStatus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void write(List<MailCourseStatus> list) {
        String sql = "INSERT INTO MailCourse (email, course, status) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            for (MailCourseStatus mailCourseStatus : list) {
                ps.setString(1, mailCourseStatus.getEmail());
                ps.setString(2, mailCourseStatus.getCourse());
                ps.setString(3, mailCourseStatus.getStatus());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void createMailCourse(MailCourseStatus mailCourseStatus) {
        String sql = "INSERT INTO MailCourse (email, course) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, mailCourseStatus.getEmail());
            preparedStatement.setString(2, mailCourseStatus.getCourse());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}

