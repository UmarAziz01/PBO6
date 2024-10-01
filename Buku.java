

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ASUS
 */
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
