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
        Map<String, List<Integer>> grupoProducer = new HashMap<>();
        List<Filme> listaFilme = filmeRepository.findAllWinner();

        for (Filme filme : listaFilme) {
            var splittedProducers = filme.getProducers().split(",\\s*|\\band\\bs*");
            for (String producer : splittedProducers) {
                var trimmedProducer = producer.trim();
                grupoProducer.computeIfAbsent(trimmedProducer, k -> new ArrayList<>()).add(filme.getYear());
            }
        }

        var respondeList = new ResponseListDTO();
        respondeList.setMin(montarDados(grupoProducer, false));
        respondeList.setMax(montarDados(grupoProducer, true));

        return respondeList;
    }

    private List<ResponseDTO> montarDados(Map<String, List<Integer>> grupoProducer, boolean isMax) {
        return grupoProducer.entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .flatMap(entry -> {
                    var producer = entry.getKey();
                    var years = entry.getValue();
                    List<ResponseDTO> dtos = new ArrayList<>();
                    var iterator = years.iterator();
                    int prevYear = iterator.next();
                    while (iterator.hasNext()) {
                        int currentYear = iterator.next();
                        var interval = currentYear - prevYear;
                        var dto = new ResponseDTO();
                        dto.setProducer(producer);
                        dto.setInterval(interval);
                        dto.setPreviousWin(prevYear);
                        dto.setFollowingWin(currentYear);
                        dtos.add(dto);
                        prevYear = currentYear;
                    }
                    return dtos.stream();
                })
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        dtos -> {
                            if (isMax) {
                                dtos.sort(Comparator.comparingInt(ResponseDTO::getInterval).reversed());
                            } else {
                                dtos.sort(Comparator.comparingInt(ResponseDTO::getInterval));
                            }
                            int firstInterval = dtos.isEmpty() ? 0 : dtos.get(0).getInterval();
                            return dtos.stream()
                                    .filter(dto -> dto.getInterval() == firstInterval)
                                    .sorted(Comparator.comparingInt(ResponseDTO::getPreviousWin))
                                    .collect(Collectors.toList());
                        }
                ));
    }

}

