package com.coffeetime.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pembayaran {

    @SerializedName("id_pembayaran")
    @Expose
    private String idPembayaran;
    @SerializedName("id_pemesanan")
    @Expose
    private String idPemesanan;
    @SerializedName("kode_unik")
    @Expose
    private String kodeUnik;
    @SerializedName("total_harga")
    @Expose
    private String totalHarga;
    @SerializedName("bayar")
    @Expose
    private String bayar;
    @SerializedName("kembalian")
    @Expose
    private String kembalian;
    @SerializedName("waktu")
    @Expose
    private String waktu;

    public String getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(String idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public String getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(String idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public String getKodeUnik() {
        return kodeUnik;
    }

    public void setKodeUnik(String kodeUnik) {
        this.kodeUnik = kodeUnik;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getBayar() {
        return bayar;
    }

    public void setBayar(String bayar) {
        this.bayar = bayar;
    }

    public String getKembalian() {
        return kembalian;
    }

    public void setKembalian(String kembalian) {
        this.kembalian = kembalian;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

}