package com.coffeetime.model;

import com.google.gson.annotations.SerializedName;

public class Orderan {
    @SerializedName("nama_kopi")
    private String nama_kopi;
    @SerializedName("jenis_kopi")
    private String jenis_kopi;
    @SerializedName("qty")
    private String qty;
    @SerializedName("total_harga")
    private String total_harga;
    @SerializedName("status_orderan")
    private String status_orderan;
    @SerializedName("waktu")
    private String waktu;

    public Orderan(String nama_kopi, String jenis_kopi, String qty, String total_harga, String status_orderan) {
        this.nama_kopi = nama_kopi;
        this.jenis_kopi = jenis_kopi;
        this.qty = qty;
        this.total_harga = total_harga;
        this.status_orderan = status_orderan;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public String getStatus_orderan() {
        return status_orderan;
    }

    public void setStatus_orderan(String status_orderan) {
        this.status_orderan = status_orderan;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
