package com.isj.gestiondenote.ClientWeb.Model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor

public class CategoryDto{
    private Integer id;

    private String title;

}
