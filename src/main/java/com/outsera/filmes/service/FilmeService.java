package com.outsera.filmes.service;

import com.outsera.filmes.dto.ResponseDTO;
import com.outsera.filmes.dto.ResponseListDTO;
import com.outsera.filmes.entity.Filme;
import com.outsera.filmes.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmeService {


    private final FilmeRepository filmeRepository;

    @Autowired
    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public List<Filme> buscarTodos() {
        return filmeRepository.findAll();
    }

    public ResponseListDTO consultarVencedoresComIntervaloMinMax() {
        // Map para armazenar o produtor e os anos em que ganharam
        Map<String, List<Integer>> grupoProducer = new HashMap<>();

        // Obter a lista de filmes vencedores
        List<Filme> listaFilme = filmeRepository.findAllWinner();

        // Primeiro loop: preencher o mapa com os produtores e anos
        for (Filme filme : listaFilme) {
            var splittedProducers = filme.getProducers().split(",\\s*|\\band\\bs*");
            for (String producer : splittedProducers) {
                var trimmedProducer = producer.trim();
                grupoProducer.computeIfAbsent(trimmedProducer, k -> new ArrayList<>()).add(filme.getYear());
            }
        }

        // Criar um DTO de resposta
        var respondeList = new ResponseListDTO();

        // Processar os dados e definir os intervalos mínimo e máximo
        respondeList.setMin(montarDados(grupoProducer, false));
        respondeList.setMax(montarDados(grupoProducer, true));

        return respondeList;
    }

    private List<ResponseDTO> montarDados(Map<String, List<Integer>> grupoProducer, boolean isMax) {
        // List para armazenar os DTOs resultantes
        List<ResponseDTO> result = new ArrayList<>();

        // Processar cada entrada no mapa
        for (var entry : grupoProducer.entrySet()) {
            var producer = entry.getKey();
            var years = entry.getValue();

            if (years.size() > 1) {
                // Ordenar os anos e calcular intervalos
                years.sort(Integer::compareTo);
                for (int i = 1; i < years.size(); i++) {
                    int prevYear = years.get(i - 1);
                    int currentYear = years.get(i);
                    int interval = currentYear - prevYear;

                    // Adicionar DTO para o intervalo
                    var dto = new ResponseDTO();
                    dto.setProducer(producer);
                    dto.setInterval(interval);
                    dto.setPreviousWin(prevYear);
                    dto.setFollowingWin(currentYear);
                    result.add(dto);
                }
            }
        }

        // Ordenar e filtrar os resultados
        result.sort(Comparator.comparingInt(ResponseDTO::getInterval));
        if (isMax) {
            result.sort(Comparator.comparingInt(ResponseDTO::getInterval).reversed());
        }

        int firstInterval = result.isEmpty() ? 0 : result.get(0).getInterval();
        return result.stream()
                .filter(dto -> dto.getInterval() == firstInterval)
                .sorted(Comparator.comparingInt(ResponseDTO::getPreviousWin))
                .collect(Collectors.toList());
    }


}

