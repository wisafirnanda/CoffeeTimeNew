package com.coffeetime.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("id_warkop")
    @Expose
    private String idWarkop;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("no_hp")
    @Expose
    private String noHp;
    @SerializedName("tipe_user")
    @Expose
    private String tipeUser;
    @SerializedName("verifikasi")
    @Expose
    private String verifikasi;
    @SerializedName("token")
    @Expose
    private String token;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdWarkop() {
        return idWarkop;
    }

    public void setIdWarkop(String idWarkop) {
        this.idWarkop = idWarkop;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getTipeUser() {
        return tipeUser;
    }

    public void setTipeUser(String tipeUser) {
        this.tipeUser = tipeUser;
    }

    public String getVerifikasi() {
        return verifikasi;
    }

    public void setVerifikasi(String verifikasi) {
        this.verifikasi = verifikasi;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}