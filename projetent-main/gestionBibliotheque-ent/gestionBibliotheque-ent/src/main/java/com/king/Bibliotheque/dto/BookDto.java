package com.king.Bibliotheque.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor

public class BookDto {
    private Integer id;
    private String ISBN;
    private String title;
    private String authorName;
    private String edition;
    private String publicationDate;
    private MultipartFile image;
    private String nbExemplaire;
    private String place;
    private String category;
    private String disponibility;
}
