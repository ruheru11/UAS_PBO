package UAS_PBO;

public class Transaksi {
    private int idTransaksi;
    private String namaKonsumen;
    private String namaBarang;
    private int quantity;
    private int totalBiaya;

    public Transaksi(int idTransaksi, String namaKonsumen, String namaBarang, int quantity, int totalBiaya) {
        this.idTransaksi = idTransaksi;
        this.namaKonsumen = namaKonsumen;
        this.namaBarang = namaBarang;
        this.quantity = quantity;
        this.totalBiaya = totalBiaya;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public String getNamaKonsumen() {
        return namaKonsumen;
    }

    public void setNamaKonsumen(String namaKonsumen) {
        this.namaKonsumen = namaKonsumen;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(int totalBiaya) {
        this.totalBiaya = totalBiaya;
    }
}

