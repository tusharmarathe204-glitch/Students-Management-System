
package com.studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Display Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");

            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                // 1. ADD STUDENT
                case 1:
                    addStudent();
                    break;

                // 2. DISPLAY STUDENTS
                case 2:
                    displayStudents();
                    break;

                // 3. SEARCH STUDENT
                case 3:
                    searchStudent();
                    break;

                // 4. UPDATE STUDENT
                case 4:
                    updateStudent();
                    break;

                // 5. DELETE STUDENT
                case 5:
                    deleteStudent();
                    break;

                // 6. EXIT
                case 6:
                    System.out.println("Application Terminated.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    // ADD STUDENT
    public static void addStudent() {

        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Course: ");
            String course = sc.nextLine();

            System.out.print("Enter Marks: ");
            double marks = sc.nextDouble();

            String sql = "INSERT INTO students VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, course);
            ps.setDouble(4, marks);

            ps.executeUpdate();

            System.out.println("Student Added Successfully!");

        } catch (SQLException e) {
            System.out.println("Error Adding Student!");
            e.printStackTrace();
        }
    }

    // DISPLAY STUDENTS
    public static void displayStudents() {

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM students";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {

                found = true;

                System.out.println("----------------------");
                System.out.println("Student ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Course: " + rs.getString("course"));
                System.out.println("Marks: " + rs.getDouble("marks"));
            }

            if (!found) {
                System.out.println("No Students Found!");
            }

        } catch (SQLException e) {
            System.out.println("Error Displaying Students!");
            e.printStackTrace();
        }
    }

    // SEARCH STUDENT
    public static void searchStudent() {

        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter Student ID to Search: ");
            int id = sc.nextInt();

            String sql = "SELECT * FROM students WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("Student Found!");
                System.out.println("Student ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Course: " + rs.getString("course"));
                System.out.println("Marks: " + rs.getDouble("marks"));

            } else {
                System.out.println("Student Not Found!");
            }

        } catch (SQLException e) {
            System.out.println("Error Searching Student!");
            e.printStackTrace();
        }
    }

    // UPDATE STUDENT
    public static void updateStudent() {

        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter Student ID to Update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter New Name: ");
            String name = sc.nextLine();

            System.out.print("Enter New Course: ");
            String course = sc.nextLine();

            System.out.print("Enter New Marks: ");
            double marks = sc.nextDouble();

            String sql = "UPDATE students SET name=?, course=?, marks=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, course);
            ps.setDouble(3, marks);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student Updated Successfully!");
            } else {
                System.out.println("Student Not Found!");
            }

        } catch (SQLException e) {
            System.out.println("Error Updating Student!");
            e.printStackTrace();
        }
    }

    // DELETE STUDENT
    public static void deleteStudent() {

        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter Student ID to Delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM students WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student Deleted Successfully!");
            } else {
                System.out.println("Student Not Found!");
            }

        } catch (SQLException e) {
            System.out.println("Error Deleting Student!");
            e.printStackTrace();
        }
    }
}
