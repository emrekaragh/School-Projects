package com.mycompany.mavenproject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class conDB {

    Connection conn = null;

    public conDB() {
    }

    public void connect() {
        //useUnicode=yes&characterEncoding=UTF-8
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/tesseractDB?useUnicode=yes&characterEncoding=UTF-8&" + "user=root&password=");
            System.out.println("Connected...");
            // Do something with the Connection

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void disconnect() throws SQLException {
        System.out.println("Disonnected...");
        conn.close();
    }

    public void insert(ArrayList<String> dbValues) throws SQLException {

        String sql = "INSERT INTO liste (isletmeAdi, tarih, fisNo, toplamFiyat)"
                + "VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, dbValues.get(0));
        System.out.println("ad: " + dbValues.get(0));
        preparedStatement.setString(2, dbValues.get(1));
        preparedStatement.setString(3, dbValues.get(2));
        preparedStatement.setString(4, dbValues.get(3));
        preparedStatement.executeUpdate();
        for (int i = 4; i < dbValues.size(); i++) {
            String sql2 = "INSERT INTO products (isletmeAdi, fisNo, urun)"
                    + "VALUES (?, ?, ?)";
            PreparedStatement preparedStatement2 = conn.prepareStatement(sql2);
            preparedStatement2.setString(1, dbValues.get(0));
            preparedStatement2.setString(2, dbValues.get(2));
            preparedStatement2.setString(3, dbValues.get(i));
            preparedStatement2.executeUpdate();
        }

    }

    public ResultSet selectİsletmeAdi(String isletmeAdi) throws SQLException {
        String sql = "SELECT * FROM tesseractdb.liste WHERE isletmeAdi LIKE ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, "%" + isletmeAdi + "%");
        preparedStatement.executeQuery();
        System.out.println("\nQuery: " + preparedStatement.executeQuery());

        ResultSet rs = preparedStatement.executeQuery();

        return rs;
    }

    public ResultSet selectTarih(String tarih) throws SQLException {
        String sql = "SELECT * FROM tesseractdb.liste WHERE tarih = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, tarih);
        preparedStatement.executeQuery();
        System.out.println("\nQuery: " + preparedStatement.executeQuery());
        ResultSet rs = preparedStatement.executeQuery();

        return rs;
    }

    public ResultSet selectBoth(String isletmeAdi, String tarih) throws SQLException {
        String sql = "SELECT * FROM tesseractdb.liste WHERE isletmeAdi = ? AND tarih = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, isletmeAdi);
        preparedStatement.setString(2, tarih);
        preparedStatement.executeQuery();
        System.out.println("\nQuery: " + preparedStatement.executeQuery());
        ResultSet rs = preparedStatement.executeQuery();

        return rs;
    }

    public ResultSet selectAll() throws SQLException {
        String sql = "SELECT * FROM tesseractdb.liste ";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        return rs;
    }
    
    public ResultSet selectUrunler(String isletmeAdi, String fisNo) throws SQLException {
        String sql = "SELECT * FROM tesseractdb.products WHERE isletmeAdi LIKE ? AND fisNo LIKE ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, "%" + isletmeAdi + "%");
        preparedStatement.setString(2, "%" + fisNo + "%");
        preparedStatement.executeQuery();
        System.out.println("\nQuery: " + preparedStatement.executeQuery());

        ResultSet rs = preparedStatement.executeQuery();

        return rs;
    }
}
