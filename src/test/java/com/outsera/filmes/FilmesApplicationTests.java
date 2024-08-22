package com.outsera.filmes;

import com.outsera.filmes.dto.ResponseListDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class FilmesApplicationTests {

	private static final String HOST = "http://localhost:";
	private static final String RESOURCE = "/api/filmes";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void givenValidRequest_whenGetApiFilmes_thenReturnsSuccessResponse() {
		ResponseEntity<ResponseListDTO> responseEntity = restTemplate.getForEntity(HOST + port + RESOURCE, ResponseListDTO.class);
		Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		Assertions.assertNotNull(responseEntity.getBody());
		Assertions.assertNotNull(responseEntity.getBody().getMax());
		Assertions.assertNotNull(responseEntity.getBody().getMin());
	}

}
