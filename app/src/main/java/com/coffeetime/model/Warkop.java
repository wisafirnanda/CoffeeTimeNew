package com.coffeetime.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Warkop implements Serializable {

    @SerializedName("id_warkop")
    @Expose
    private String idWarkop;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("nama_warkop")
    @Expose
    private String namaWarkop;
    @SerializedName("nama_pemilik")
    @Expose
    private String namaPemilik;
    @SerializedName("cp_warkop")
    @Expose
    private String cpWarkop;
    @SerializedName("waktu_buka")
    @Expose
    private String waktuBuka;
    @SerializedName("alamat_warkop")
    @Expose
    private String alamatWarkop;
    @SerializedName("alamat_warkop_longitude")
    @Expose
    private String alamatWarkopLongitude;
    @SerializedName("alamat_warkop_latitude")
    @Expose
    private String alamatWarkopLatitude;
    @SerializedName("id_kopi")
    @Expose
    private String idKopi;
    @SerializedName("token")
    @Expose
    private String token;

    public String getIdWarkop() {
        return idWarkop;
    }

    public void setIdWarkop(String idWarkop) {
        this.idWarkop = idWarkop;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNamaWarkop() {
        return namaWarkop;
    }

    public void setNamaWarkop(String namaWarkop) {
        this.namaWarkop = namaWarkop;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public String getCpWarkop() {
        return cpWarkop;
    }

    public void setCpWarkop(String cpWarkop) {
        this.cpWarkop = cpWarkop;
    }

    public String getWaktuBuka() {
        return waktuBuka;
    }

    public void setWaktuBuka(String waktuBuka) {
        this.waktuBuka = waktuBuka;
    }

    public String getAlamatWarkop() {
        return alamatWarkop;
    }

    public void setAlamatWarkop(String alamatWarkop) {
        this.alamatWarkop = alamatWarkop;
    }

    public String getAlamatWarkopLongitude() {
        return alamatWarkopLongitude;
    }

    public void setAlamatWarkopLongitude(String alamatWarkopLongitude) {
        this.alamatWarkopLongitude = alamatWarkopLongitude;
    }

    public String getAlamatWarkopLatitude() {
        return alamatWarkopLatitude;
    }

    public void setAlamatWarkopLatitude(String alamatWarkopLatitude) {
        this.alamatWarkopLatitude = alamatWarkopLatitude;
    }

    public String getIdKopi() {
        return idKopi;
    }

    public void setIdKopi(String idKopi) {
        this.idKopi = idKopi;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}