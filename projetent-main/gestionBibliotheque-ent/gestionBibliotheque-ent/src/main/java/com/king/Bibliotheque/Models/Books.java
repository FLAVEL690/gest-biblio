package com.king.Bibliotheque.Models;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String ISBN;
    private String title;
    private String authorName;
    private String edition;
    private LocalDate publicationDate;
    private int nbExemplaire;
    private Boolean disponibility;
    @Lob
    @Column(name = "picture", length = Integer.MAX_VALUE, nullable = true)
    private String image;


    //private MultipartFile file;
    @ManyToOne
    @JoinColumn(name = "category_Id", referencedColumnName = "id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "place_Id", referencedColumnName = "id")
    private Localisation place;


}
