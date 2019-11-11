package com.coffeetime.model;

public class Riwayat {

    private String namakopi;
    private String status;
    private String waktu;

    public Riwayat (String namakopi, String status, String waktu) {
        this.namakopi = namakopi;
        this.status = status;
        this.waktu = waktu;
    }

    public String getNamakopi() {
        return namakopi;
    }

    public void setNamakopi(String namakopi) {
        this.namakopi = namakopi;
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
}
