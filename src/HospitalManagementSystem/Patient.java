package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner sc;

    public Patient(Connection connection, Scanner sc) {
        this.connection = connection;
        this.sc = sc;
    }
    public void addPatient() {
        System.out.print("Enter Patient Name: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.print("Enter Patient Age: ");
        int age = sc.nextInt();
        System.out.print("Enter Patient Gender: ");
        String gender = sc.next();
        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
                System.out.println("Patient added successfully!!");
            }
            else {
                System.out.println("Failed to add Patient");
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void viewPatients() {
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println("Patients ");
            System.out.println("+------------+-----------------------+-------------+-----------------+");
            System.out.println("| Patient_Id | Name                  | Age         | Gender          |");
            System.out.println("+------------+-----------------------+-------------+-----------------+");
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                System.out.printf("| %-10s | %-21s | %-11s | %-15s |\n", id, name, age, gender);
                System.out.println("+------------+-----------------------+-------------+-----------------+");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public boolean getPatientById(int id) {
        String query = "SELECT * FROM patients WHERE id = ?";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }
            else {
                return false;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
