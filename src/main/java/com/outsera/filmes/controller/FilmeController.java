package com.outsera.filmes.controller;

import com.outsera.filmes.dto.ResponseListDTO;
import com.outsera.filmes.service.FilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {

    private final FilmeService filmeService;

    @Autowired
    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @GetMapping
    public ResponseEntity<ResponseListDTO> consultarVencedores() {
        return ResponseEntity.ok().body(filmeService.consultarVencedoresComIntervaloMinMax());
    }
}
