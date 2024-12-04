package com.example.aamo.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class DELRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String kode;
    private String navn;
    private String href;

    @OneToMany(mappedBy = "region")
    @JsonBackReference
    private Set<DELKommune> kommuner = new HashSet<>();

    public DELRegion(String kode, String navn, String href) {
        this.kode = kode;
        this.navn = navn;
        this.href = href;
        this.kommuner = kommuner;
    }

    public DELRegion() {
    }

    public int getId() {
        return id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Set<DELKommune> getKommuner() {
        return kommuner;
    }

    public void setKommuner(Set<DELKommune> kommuner) {
        this.kommuner = kommuner;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", kode='" + kode + '\'' +
                ", navn='" + navn + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
