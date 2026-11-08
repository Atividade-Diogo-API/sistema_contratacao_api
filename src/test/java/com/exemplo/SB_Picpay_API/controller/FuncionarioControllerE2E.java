package com.exemplo.SB_Picpay_API.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FuncionarioControllerE2E {

    @Value("${local.server.port}")
    int port;

    private ResponseEntity<String> chamar(HttpMethod metodo, String caminho, String corpo) {
        RestClient.RequestBodySpec req = RestClient.create("http://localhost:" + port)
            .method(metodo).uri(caminho).contentType(MediaType.APPLICATION_JSON);
        if (corpo != null) {
            req.body(corpo);
        }
        // 4xx must come back as a response so the test can assert the status
        return req.retrieve().onStatus(s -> true, (rq, rs) -> { }).toEntity(String.class);
    }

    @Test
    void cicloDeVidaCompletoDeUmFuncionarioPeloHttp() {
        String corpo = """
            {"nome":"Ana","cpf":"20000000001","cargo":"Dev","departamento":"TI","salario":5000,"cidade":"SP","status":"EM_ANALISE"}
            """;

        assertEquals(HttpStatus.CREATED, chamar(HttpMethod.POST, "/funcionarios", corpo).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, chamar(HttpMethod.POST, "/funcionarios", corpo).getStatusCode());

        ResponseEntity<String> lista = chamar(HttpMethod.GET, "/funcionarios", null);
        assertEquals(HttpStatus.OK, lista.getStatusCode());
        assertTrue(lista.getBody().contains("20000000001"));

        assertEquals(HttpStatus.OK, chamar(HttpMethod.GET, "/funcionarios/1", null).getStatusCode());
        assertEquals(HttpStatus.OK,
            chamar(HttpMethod.PATCH, "/funcionarios/1", "{\"status\":\"CONTRATADO\"}").getStatusCode());
        assertTrue(chamar(HttpMethod.GET, "/funcionarios/1", null).getBody().contains("CONTRATADO"));
        assertEquals(HttpStatus.OK, chamar(HttpMethod.PUT, "/funcionarios/1", corpo).getStatusCode());

        assertEquals(HttpStatus.OK, chamar(HttpMethod.DELETE, "/funcionarios/1", null).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, chamar(HttpMethod.GET, "/funcionarios/1", null).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, chamar(HttpMethod.DELETE, "/funcionarios/1", null).getStatusCode());
    }
}
