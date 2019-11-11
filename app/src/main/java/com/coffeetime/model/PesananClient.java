package com.coffeetime.model;

public class PesananClient {
    private String id_pemesanan;
    private String id_kopi;
    private String nama_user;
    private String nama_kopi;
    private String jenis_kopi;
    private String jumlah_kopi;
    private String total_harga;

    public PesananClient(String nama_user, String nama_kopi, String jenis_kopi, String jumlah_kopi, String total_harga) {
        //this.id_kopi = id_kopi;
        this.nama_user = nama_user;
        this.nama_kopi = nama_kopi;
        this.jenis_kopi = jenis_kopi;
        this.jumlah_kopi = jumlah_kopi;
        this.total_harga = total_harga;
    }

    public String getId_pemesanan() {
        return id_pemesanan;
    }

    public void setId_pemesanan(String id_pemesanan) {
        this.id_pemesanan = id_pemesanan;
    }

    public String getId_kopi() {
        return id_kopi;
    }

    public void setId_kopi(String id_kopi) {
        this.id_kopi = id_kopi;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getNama_kopi() {
        return nama_kopi;
    }

    public void setNama_kopi(String nama_kopi) {
        this.nama_kopi = nama_kopi;
    }

    public String getJenis_kopi() {
        return jenis_kopi;
    }

    public void setJenis_kopi(String jenis_kopi) {
        this.jenis_kopi = jenis_kopi;
    }

    public String getJumlah_kopi() {
        return jumlah_kopi;
    }

    public void setJumlah_kopi(String jumlah_kopi) {
        this.jumlah_kopi = jumlah_kopi;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }
}
