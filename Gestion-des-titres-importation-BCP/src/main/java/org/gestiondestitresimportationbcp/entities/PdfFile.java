package org.gestiondestitresimportationbcp.entities;

import jakarta.persistence.*;

@Entity
public class PdfFile{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    private Long id ;
    @Column
    private String filePath;
   @Column
   private Long numeroEnregistrement ;
   @Column
    private String etat = "non traiter";
   @Column
   private String nom ;

public PdfFile() {
    }
    public Long getId() {
        return id;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public Long getNumeroEnregistrement() {
        return numeroEnregistrement;
    }
    public void setNumeroEnregistrement(Long numeroEnregistrement) {
        this.numeroEnregistrement = numeroEnregistrement;
    }
    public String getEtat() {
        return etat;
    }
    public String getNom() {
        return nom;
    }
    public PdfFile(Long id, String filePath, Long numeroEnregistrement, String etat, String nom) {
        this.id = id;
        this.filePath = filePath;
        this.numeroEnregistrement = numeroEnregistrement;
        this.etat = etat;
        this.nom = nom;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
