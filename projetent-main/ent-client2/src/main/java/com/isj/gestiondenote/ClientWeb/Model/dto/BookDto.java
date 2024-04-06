package com.isj.gestiondenote.ClientWeb.Model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.isj.gestiondenote.ClientWeb.Model.entities.Category;
import com.isj.gestiondenote.ClientWeb.Model.entities.Localisation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor

public class BookDto {
    private Integer id;
    private String ISBN;
    private String title;
    private String authorName;
    private String edition;
    private String publicationDate;
//    private MultipartFile image;
    private String nbExemplaire;
    private String place;
    private String category;
    private String disponibility;
}
