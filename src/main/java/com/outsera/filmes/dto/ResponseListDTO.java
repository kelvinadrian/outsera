package com.outsera.filmes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseListDTO {

    private List<ResponseDTO> min;
    private List<ResponseDTO> max;
}
