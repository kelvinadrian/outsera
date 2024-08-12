package com.outsera.filmes.dto;

import lombok.Data;

@Data
public class FilmeDTO {

    private int year;
    private String title;
    private String studios;
    private String producers;
    private boolean winner;
}
