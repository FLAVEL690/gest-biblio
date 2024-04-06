package com.king.Bibliotheque.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "localisation")
@NoArgsConstructor
public class Localisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String linex;
    private String columnx;

    @OneToMany
    List<Books> bookList;
}
