package com.coffeetime.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pemesanan {

    @SerializedName("id_pemesanan")
    @Expose
    private String idPemesanan;
    @SerializedName("nama_warkop")
    @Expose
    private String nama_warkop;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("id_warkop")
    @Expose
    private String idWarkop;
    @SerializedName("id_kopi")
    @Expose
    private String idKopi;
    @SerializedName("total_harga")
    @Expose
    private String totalHarga;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("alamat_antar_longitude")
    @Expose
    private String alamatAntarLongitude;
    @SerializedName("alamat_antar_latitude")
    @Expose
    private String alamatAntarLatitude;
    @SerializedName("tipe_bayar")
    @Expose
    private String tipe_bayar;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("waktu")
    @Expose
    private String waktu;
    @SerializedName("token")
    @Expose
    private String token;


    public String getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(String idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public String getNama_warkop() {
        return nama_warkop;
    }

    public void setNama_warkop(String nama_warkop) {
        this.nama_warkop = nama_warkop;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdKopi() {
        return idKopi;
    }

    public void setIdKopi(String idKopi) {
        this.idKopi = idKopi;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAlamatAntarLongitude() {
        return alamatAntarLongitude;
    }

    public void setAlamatAntarLongitude(String alamatAntarLongitude) {
        this.alamatAntarLongitude = alamatAntarLongitude;
    }

    public String getAlamatAntarLatitude() {
        return alamatAntarLatitude;
    }

    public void setAlamatAntarLatitude(String alamatAntarLatitude) {
        this.alamatAntarLatitude = alamatAntarLatitude;
    }

    public String getTipe_bayar() {
        return tipe_bayar;
    }

    public void setTipe_bayar(String tipe_bayar) {
        this.tipe_bayar = tipe_bayar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdWarkop() {
        return idWarkop;
    }

    public void setIdWarkop(String idWarkop) {
        this.idWarkop = idWarkop;
    }
}