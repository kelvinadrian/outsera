package com.outsera.filmes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private String producer;
    private int interval;
    private int previousWin;
    private int followingWin;
}
