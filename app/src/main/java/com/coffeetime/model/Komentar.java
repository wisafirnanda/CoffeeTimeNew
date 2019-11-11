package com.coffeetime.model;

public class Komentar {

    public String id;
    public String id_warkop;
    public String id_user;
    public String nama_user;
    public String isi_komentar;
    public String tanggal;

    public Komentar() {
    }

    public Komentar(String id, String id_warkop, String id_user, String nama_user, String isi_komentar, String tanggal) {
        this.id = id;
        this.id_warkop = id_warkop;
        this.id_user = id_user;
        this.nama_user = nama_user;
        this.isi_komentar = isi_komentar;
        this.tanggal = tanggal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_warkop() {
        return id_warkop;
    }

    public void setId_warkop(String id_warkop) {
        this.id_warkop = id_warkop;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getIsi_komentar() {
        return isi_komentar;
    }

    public void setIsi_komentar(String isi_komentar) {
        this.isi_komentar = isi_komentar;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
