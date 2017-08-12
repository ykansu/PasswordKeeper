package com.yasin.zegaste.passwordbox.passwordentities;

import android.util.Base64;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Date;

/**
 * This class stands for the model of user's information
 * Created by Yasin on 12.11.2016.
 */

public class IdentitiyCard implements Serializable {



    private byte[] password;
    private String name;
    private String surName;
    private String tckn;
    private String ssn;
    private String seriNo;
    private String aileSıraNo;
    private String sıraNo;
    private String dogumYeri;
    private String nufusaKayıtlıOlduğuIl;
    private String nufusaKayıtlıolduğuIlce;
    private String verilisNedeni;
    private String medeniDurumu;
    private String dini;
    private String uyrugu;
    private String kanGrubu;
    private String doğumYeri;
    private Date dogumTarihi;
    private Date veriliştarihi;
    private String anaAdı;
    private String babaAdı;
    private String anaKızlıkSoyadı;

    public IdentitiyCard(String password) {
        setPassword(password);
    }

    public boolean checkPasswordIsCorrect(String pass) {
        return Arrays.equals(password, Base64.encode(pass.getBytes(), Base64.DEFAULT));
    }

    public void setPassword(String password) {
        this.password = Base64.encode(password.getBytes(),Base64.DEFAULT);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getTckn() {
        return tckn;
    }

    public void setTckn(String tckn) {
        this.tckn = tckn;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getSeriNo() {
        return seriNo;
    }

    public void setSeriNo(String seriNo) {
        this.seriNo = seriNo;
    }

    public String getAileSıraNo() {
        return aileSıraNo;
    }

    public void setAileSıraNo(String aileSıraNo) {
        this.aileSıraNo = aileSıraNo;
    }

    public String getSıraNo() {
        return sıraNo;
    }

    public void setSıraNo(String sıraNo) {
        this.sıraNo = sıraNo;
    }

    public String getDogumYeri() {
        return dogumYeri;
    }

    public void setDogumYeri(String dogumYeri) {
        this.dogumYeri = dogumYeri;
    }

    public String getNufusaKayıtlıOlduğuIl() {
        return nufusaKayıtlıOlduğuIl;
    }

    public void setNufusaKayıtlıOlduğuIl(String nufusaKayıtlıOlduğuIl) {
        this.nufusaKayıtlıOlduğuIl = nufusaKayıtlıOlduğuIl;
    }

    public String getNufusaKayıtlıolduğuIlce() {
        return nufusaKayıtlıolduğuIlce;
    }

    public void setNufusaKayıtlıolduğuIlce(String nufusaKayıtlıolduğuIlce) {
        this.nufusaKayıtlıolduğuIlce = nufusaKayıtlıolduğuIlce;
    }

    public String getVerilisNedeni() {
        return verilisNedeni;
    }

    public void setVerilisNedeni(String verilisNedeni) {
        this.verilisNedeni = verilisNedeni;
    }

    public String getMedeniDurumu() {
        return medeniDurumu;
    }

    public void setMedeniDurumu(String medeniDurumu) {
        this.medeniDurumu = medeniDurumu;
    }

    public String getDini() {
        return dini;
    }

    public void setDini(String dini) {
        this.dini = dini;
    }

    public String getUyrugu() {
        return uyrugu;
    }

    public void setUyrugu(String uyrugu) {
        this.uyrugu = uyrugu;
    }

    public String getKanGrubu() {
        return kanGrubu;
    }

    public void setKanGrubu(String kanGrubu) {
        this.kanGrubu = kanGrubu;
    }

    public String getDoğumYeri() {
        return doğumYeri;
    }

    public void setDoğumYeri(String doğumYeri) {
        this.doğumYeri = doğumYeri;
    }

    public Date getDogumTarihi() {
        return dogumTarihi;
    }

    public void setDogumTarihi(Date dogumTarihi) {
        this.dogumTarihi = dogumTarihi;
    }

    public Date getVeriliştarihi() {
        return veriliştarihi;
    }

    public void setVeriliştarihi(Date veriliştarihi) {
        this.veriliştarihi = veriliştarihi;
    }

    public String getAnaAdı() {
        return anaAdı;
    }

    public void setAnaAdı(String anaAdı) {
        this.anaAdı = anaAdı;
    }

    public String getBabaAdı() {
        return babaAdı;
    }

    public void setBabaAdı(String babaAdı) {
        this.babaAdı = babaAdı;
    }

    public String getAnaKızlıkSoyadı() {
        return anaKızlıkSoyadı;
    }

    public void setAnaKızlıkSoyadı(String anaKızlıkSoyadı) {
        this.anaKızlıkSoyadı = anaKızlıkSoyadı;
    }
}
