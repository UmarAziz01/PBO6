

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ASUS
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CRUDKu {

    Connection cn;
    PreparedStatement ps;
    Statement st;

    String driver = "org.postgresql.Driver";
    String koneksi = "jdbc:postgresql://localhost:5432/PBO_P6_T5_A";
    String user = "postgres";
    String password = " "; // Ganti dengan password PostgreSQL Anda

    public CRUDKu() {
        try {
            Class.forName(driver);
            cn = DriverManager.getConnection(koneksi, user, password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean tambah(long ISBN, String judulBuku, String penerbit, int tahunTerbit) { 
        String sql = "INSERT INTO Buku (ISBN, JudulBuku, Penerbit, TahunTerbit) VALUES (?, ?, ?, ?)";
        try {
            ps = cn.prepareStatement(sql);
            ps.setLong(1, ISBN);
            ps.setString(2, judulBuku);
            ps.setString(3, penerbit);
            ps.setInt(4, tahunTerbit);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Buku> tampil() {
        List<Buku> list = new ArrayList<>();
        String sql = "SELECT * FROM Buku";
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Buku m = new Buku(
                        rs.getLong("ISBN"),
                        rs.getString("judulBuku"),
                        rs.getString("penerbit"),
                        rs.getInt("tahunTerbit")
                );
                list.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean hapus(long ISBN) {
        String sql = "DELETE FROM Buku WHERE ISBN = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setLong(1, ISBN);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean update(long ISBN, String judulBuku, String penerbit, int tahunTerbit) {
        String sql = "UPDATE Buku SET JudulBuku = ?, Penerbit = ?, TahunTerbit = ? WHERE ISBN = ?";
        try {
            ps = cn.prepareStatement(sql);
            ps.setString(1, judulBuku);
            ps.setString(2, penerbit);
            ps.setInt(3, tahunTerbit);
            ps.setLong(4, ISBN);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public void tutupKoneksi() {
        try {
            if (ps != null) {
                ps.close();
            }
            if (st != null) {
                st.close();
            }
            if (cn != null) {
                cn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CRUDKu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
