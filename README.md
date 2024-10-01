# PBO6
Penugasan matakuliah pemrograman berorientasi obyek pertemuan keenam membuat CRUD sebagai Simulasi UTS

## Berikut Langkah-langkah membuat CRUD dengan Java Swing untuk entitas Buku dengan atribut: ISBN, Judul Buku, Tahun Terbit.

1. **Buat project baru** dengan nama TugasPertemuanKeenam yang dimulai dengan klik new project atau Ctrl+Shift+N, pilih Java with Ant, lalu masukkan nama project setelah itu unchecklist pada Create Main Class kemudian klik Finish.
   ![image](https://github.com/user-attachments/assets/8e71273a-9c50-4eac-a3db-c046293764c8)

2. **Buat exception** dengan cara klik kanan pada default package kemudian pilih New, klik Java Class dengan nama DataTidakDitemukanException, jadikan Exception sebagai superclass. supaya error bisa ditangani ketika melakukan pencarian data
   ````Java
   public class DataTidakDitemukanException extends Exception {

     public DataTidakDitemukanException(String message) {
        super(message);
      }
    }
    ````
3. **Buat database baru** dengan nama PBO_P6_T5_A, pada PostgreSQL dengan query dibawah ini
   ````sql
   CREATE TABLE Buku (
    ISBN int PRIMARY KEY,
    JudulBuku VARCHAR(30) NOT NULL,
    Penerbit VARCHAR(30) NOT NULL,
	  TahunTerbit CHAR(4) NOT NULL
    );
   ````
4. **Buat class baru** dengan cara klik kanan pada default package kemudian pilih New, klik Java Class dengan nama Buku. Class ini digunakan untuk merepresentasikan entitas buku yang memiliki 4 atribut yaitu ISBN, JudulBuku, Penerbit, TahunTerbit
   ````java
    public class Buku {

    private long ISBN;
    private String judulBuku;
    private String penerbit;
    private int tahunTerbit;

    public Buku(long ISBN, String judulBuku, String penerbit, int tahunTerbit) {
        this.ISBN = ISBN;
        this.judulBuku = judulBuku;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
    }

    // Getter dan Setter
    /**
     * @return the ISBN
     */
    public long getISBN() {
        return ISBN;
    }

    /**
     * @param ISBN the ISBN to set
     */
    public void setISBN(long ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * @return the judulBuku
     */
    public String getJudulBuku() {
        return judulBuku;
    }

    /**
     * @param judulBuku the judulBuku to set
     */
    public void setJudulBuku(String judulBuku) {
        this.judulBuku = judulBuku;
    }

    /**
     * @return the penerbit
     */
    public String getPenerbit() {
        return penerbit;
    }

    /**
     * @param penerbit the penerbit to set
     */
    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    /**
     * @return the tahunTerbit
     */
    public int getTahunTerbit() {
        return tahunTerbit;
    }

    /**
     * @param tahunTerbit the tahunTerbit to set
     */
    public void setTahunTerbit(int tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    }
   ````
5. **Buat class baru** dengan cara klik kanan pada default package kemudian pilih New, klik Java Class dengan nama CRUDKu. Class ini digunakan sebagai backend dari program yang akan dibuat, berisi kode untuk menghubungkan ke database
   ![image](https://github.com/user-attachments/assets/8c32ab31-5f46-46b1-bf19-c3bbd694f1c2)

   ````java
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
   ````

6. **Buat Jframe Form** dengan cara klik kanan pada default package kemudian pilih New, klik JFrame Form... dengan nama CRUDForm. ini digunakan untuk GUI pemrograman, maka setelah klik finish desainlah sesuka anda atau membuat serupa dengan gambar dibawah ini dengan beberapa variabel untuk JTextField(tfISBN, tfJudulBuku, tfPenerbit, tfTahunTerbit) dan Button(btnTambah, btnUpdate, btnHapus, btnReset, btnKeluar) juga Table(tblBuku)
   ![image](https://github.com/user-attachments/assets/a2131bcf-1746-40f3-9b2c-ece5d1a8506c)

   ![image](https://github.com/user-attachments/assets/f6533350-4dac-4a17-b421-664675c7c737)

7. **Setelah membuat tampilan** langkah selajutnya adalah melengkapi GUI tersebut dengan menambahkan kode supaya program bisa dijalankan dengan sempurna. lakukan dengan mengikuti langkah 8-selesai
8. **Buat variabel** pada public class dengan kode seperti dibawah
   ```java
       private CRUDKu crud;
       private DefaultTableModel tableModel;
   ```
9. **Buat constructor** seperti dibawah ini, dengan menambahkan dengan menginisiasi crud, mengambil model dari tabel buku, dan menambahkan method loadData yang berfungsi supaya perubahan data selalu ditampilkan secara default program dijalankan
    ```java
    public CRUDForm() {
        initComponents();
        crud = new CRUDKu(); // yang datambahkan
        tableModel = (DefaultTableModel) tblBuku.getModel(); // Ambil model dari tabel
        loadData();
    }
    ```
10. **Tambahkan method** loaddata sebagai berikut
    ```java
    private void loadData() {
        tableModel.setRowCount(0); // Bersihkan tabel
        List<Buku> bukuList = crud.tampil();
        tableModel.setColumnIdentifiers(new Object[]{"ISBN", "Judul Buku", "Penerbit", "Tahun Terbit"});

        for (Buku m : bukuList) {
            tableModel.addRow(new Object[]{m.getISBN(), m.getJudulBuku(), m.getPenerbit(), m.getTahunTerbit()});
        }

    }
    ```
11. Masukkan kode di btnTambah dengan mengklik 2x pada btnTambah sehingga serupa dengan gambar dibawah ini
    ````java
    try {
            long ISBN = Long.parseLong(tfISBN.getText());
            String judulBuku = tfJudulBuku.getText();
            String penerbit = tfPenerbit.getText();
            int tahunTerbit = Integer.parseInt(tfTahunTerbit.getText());

            if (crud.tambah(ISBN, judulBuku, penerbit, tahunTerbit)) { // dari class buku
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan data.");
            }
        } catch (NumberFormatException ex) {
        }
    ````
    ![image](https://github.com/user-attachments/assets/90209050-9460-4a9a-9f24-ccd6a64c6848)

12. Masukkan kode di btnUpdate dengan mengklik 2x pada btnUpdate sehingga serupa dengan gambar dibawah ini
    ````java
    try {
            long ISBN = Long.parseLong(tfISBN.getText());
            String judulBuku = tfJudulBuku.getText();
            String penerbit = tfPenerbit.getText();
            int tahunTerbit = Integer.parseInt(tfTahunTerbit.getText());

            if (crud.update(ISBN, judulBuku, penerbit, tahunTerbit)) {
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate.");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input harus benar.");
        }
    ````
    ![image](https://github.com/user-attachments/assets/3ea860d5-5599-4aa6-a9e8-8a6434133647)

13.  Masukkan kode di btnHapus dengan mengklik 2x pada btnHapus sehingga serupa dengan gambar dibawah ini
     ````java
      try {
            long ISBN = Long.parseLong(tfISBN.getText());
            if (crud.hapus(ISBN)) {
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Data tidak ditemukan.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input ISBN dengan benar.");
        }
      ````
  ![image](https://github.com/user-attachments/assets/554deceb-c6e0-4b50-b7bd-2b56b37935ec)


14.  Masukkan kode di btnReset dengan mengklik 2x pada btnReset sehingga serupa dengan gambar dibawah ini
     ````java
        clearForm();
         loadData();
      `````
     ![image](https://github.com/user-attachments/assets/2380ecf2-072e-453c-9e77-10ad54f1345c)

15.  Masukkan kode di btnReset dengan mengklik 2x pada btnReset sehingga serupa dengan gambar dibawah ini
     ````java
      System.exit(0);
      `````
     ![image](https://github.com/user-attachments/assets/5537ef17-3aae-4e41-9591-6f1a646b9274)

16. Masukkan kode di tblBuku dengan mengklik 2x pada tblBuku sehingga serupa dengan gambar dibawah ini
    ````java
        int row = tblBuku.getSelectedRow();
        tfISBN.setText(tblBuku.getValueAt(row, 0).toString());
        tfJudulBuku.setText(tblBuku.getValueAt(row, 1).toString());
        tfPenerbit.setText(tblBuku.getValueAt(row, 2).toString());
        tfTahunTerbit.setText(tblBuku.getValueAt(row, 3).toString());
    ````
    ![image](https://github.com/user-attachments/assets/5f58fdad-b260-49c2-894d-5bf60e32fba8)


17.  Tambahkan method clearForm sebagai penyempurna kode diatas tepat dibawah main class
      ```java
      private void clearForm() {
        tfISBN.setText("");
        tfJudulBuku.setText("");
        tfPenerbit.setText("");
        tfTahunTerbit.setText("");
      }
      ````
      ![image](https://github.com/user-attachments/assets/85a740c6-0a5e-4003-92c8-a2c25493d982)

     ## Pembuatan program telah selesai kini saatnya untuk menjalankan program yang telah dibuat
     Maka jalankan program dengan Shift+F6 atau klik kanan lalu pilih run file. jika berhasil maka tampilan program akan seperti ini, jika sudah seperti itu anda siap melakukan proses CRUD buku
     ![image](https://github.com/user-attachments/assets/4dd532e3-01ae-427f-be02-c97cd3919889)


1. Proses melakukan input data, masukkan data pada text field yang telah dibuat, jika sudah klik tambah sehingga muncullah data tersebut pada tabel disamping
   ![image](https://github.com/user-attachments/assets/34c51416-3e97-4074-8b24-0df963562373)
  Setelah berhasil menekan tombol tambah
  ![image](https://github.com/user-attachments/assets/ced4a608-4e70-4639-8d96-8220765ff894)

2. Proses melakukan update data, pilih dahulu data mana yang akan diupdate dengan mengklik data pada satu baris di tabel buku disamping
   Seharusnya Tahun Terbit pada ISBN 1235 adalah 2006, maka untuk mengupdatenya klik baris pada tabel sehingga muncul data yang akan diupdate pada text field
   ![image](https://github.com/user-attachments/assets/18afb0af-3ac1-426b-a43e-d8f87e43fd3c)

   lalu masukkan input yang sesuai dan klik update untuk menyimpan
   ![image](https://github.com/user-attachments/assets/6290441c-9dd8-411c-adb4-c047e191e6b5)


3. proses melakukan hapus data
   
   ![image](https://github.com/user-attachments/assets/0d208240-0b82-4c55-b2c6-0f618ee8e72b)

   ![image](https://github.com/user-attachments/assets/580a0f57-a543-4e04-8cf7-88274f2d1159)

7. proses melakukan resetField

   ![image](https://github.com/user-attachments/assets/211fcfa5-4e23-4430-a3d0-98a4a45919c7)

   ![image](https://github.com/user-attachments/assets/58074de6-2d1d-4c13-82cb-53e6d1732c00)
   
9. klik selesai untuk menakhiri program

   ![image](https://github.com/user-attachments/assets/e4a8e782-e0ac-472b-8979-e681f0d13309)

   ![image](https://github.com/user-attachments/assets/6c6f9d86-7e5f-4d78-bfaf-239abff1a786)


     
