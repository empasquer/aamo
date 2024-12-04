package com.example.aamo.models;

import jakarta.persistence.*;

@Entity
public class DELKommune {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 8)
    private String kode;
    private String navn;
    private String href;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    private DELRegion DELRegion;

    public DELKommune(String kode, String navn, String href, DELRegion DELRegion) {
        this.kode = kode;
        this.navn = navn;
        this.href = href;
        this.DELRegion = DELRegion;
    }

    public DELKommune() {

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

    public DELRegion getRegion() {
        return DELRegion;
    }

    public void setRegion() {
        this.DELRegion = DELRegion;
    }

    @Override
    public String toString() {
        return "Kommune{" +
                "id=" + id +
                ", kode='" + kode + '\'' +
                ", navn='" + navn + '\'' +
                ", href='" + href + '\'' +
                ", region=" + DELRegion +
                '}';
    }
}
