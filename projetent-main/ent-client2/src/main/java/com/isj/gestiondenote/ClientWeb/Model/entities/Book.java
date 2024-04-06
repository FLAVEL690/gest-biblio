package com.isj.gestiondenote.ClientWeb.Model.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Book extends Securite implements Serializable {
    private Integer id;
    private String title;
    private String ISBN;
    private String authorName;
    private String edition;
    private Date publicationDate;
    private String image;
    private int nbExemplaire;
    private Localisation place;
    private Category category;
}
