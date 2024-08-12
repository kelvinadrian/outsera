package com.outsera.filmes.runner;

import com.outsera.filmes.entity.Filme;
import com.outsera.filmes.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DBLoaderRunner implements CommandLineRunner {

    private static final String CSV_FILE = "movielist.csv";

    private final FilmeRepository filmeRepository;

    @Autowired
    public DBLoaderRunner(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Filme> filmes = readCsvFile();
        filmeRepository.saveAll(filmes);
    }

    public List<Filme> readCsvFile() throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE));
             CSVReader csvReader = createCsvReader(reader)) {
            return csvReader.readAll().stream()
                    .map(this::mapToFilme)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            String errorMessage = "Erro ao ler o arquivo CSV: " + CSV_FILE;
            System.err.println(errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
    }

    private CSVReader createCsvReader(Reader reader) {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();
        return new CSVReaderBuilder(reader)
                .withCSVParser(parser)
                .withSkipLines(1)
                .build();
    }

    private Filme mapToFilme(String[] data) {
        Filme filme = new Filme(
                Integer.parseInt(data[0]),
                data[1],
                data[2],
                data[3],
                "yes".equalsIgnoreCase(data[4]));
        return filme;
    }
}

