package com.exemplo.SB_Picpay_API.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class FuncionarioControllerIT {

    @Autowired
    MockMvc mvc;

    private static String corpo(String cpf, String status) {
        return """
            {"nome":"Ana","cpf":"%s","cargo":"Dev","departamento":"TI","salario":5000,"cidade":"SP","status":"%s"}
            """.formatted(cpf, status);
    }

    private static MockHttpServletRequestBuilder comJson(MockHttpServletRequestBuilder req, String body) {
        return req.contentType(MediaType.APPLICATION_JSON).content(body);
    }

    // ids come from a counter shared by the whole context, so look the id up by cpf
    private long cadastrar(String cpf) throws Exception {
        mvc.perform(comJson(post("/funcionarios"), corpo(cpf, "APROVADO"))).andExpect(status().isCreated());
        String lista = mvc.perform(get("/funcionarios")).andReturn().getResponse().getContentAsString();
        List<Integer> ids = JsonPath.read(lista, "$[?(@.cpf=='" + cpf + "')].id");
        return ids.get(0);
    }

    @Test
    void cadastrarELerFuncionario() throws Exception {
        long id = cadastrar("10000000001");

        mvc.perform(get("/funcionarios/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Ana"));
        mvc.perform(get("/funcionarios/999999")).andExpect(status().isNotFound());
    }

    @Test
    void cadastrarComDadosInvalidosOuCpfDuplicadoRetorna400() throws Exception {
        cadastrar("10000000002");

        mvc.perform(comJson(post("/funcionarios"), corpo("10000000002", "APROVADO"))).andExpect(status().isBadRequest());
        mvc.perform(comJson(post("/funcionarios"), corpo("curto", "APROVADO"))).andExpect(status().isBadRequest());
        mvc.perform(comJson(post("/funcionarios"), corpo("10000000003", "INVALIDO"))).andExpect(status().isBadRequest());
    }

    @Test
    void atualizarTudoEParcial() throws Exception {
        long id = cadastrar("10000000004");

        mvc.perform(comJson(put("/funcionarios/" + id), corpo("10000000004", "CONTRATADO"))).andExpect(status().isOk());
        mvc.perform(comJson(put("/funcionarios/" + id), corpo("x", "CONTRATADO"))).andExpect(status().isBadRequest());
        mvc.perform(comJson(patch("/funcionarios/" + id), "{\"cargo\":\"Tech Lead\"}")).andExpect(status().isOk());
        mvc.perform(comJson(patch("/funcionarios/" + id), "{\"salario\":-1}")).andExpect(status().isBadRequest());

        mvc.perform(get("/funcionarios/" + id))
            .andExpect(jsonPath("$.status").value("CONTRATADO"))
            .andExpect(jsonPath("$.cargo").value("Tech Lead"));
    }

    @Test
    void deletarFuncionario() throws Exception {
        long id = cadastrar("10000000005");

        mvc.perform(delete("/funcionarios/" + id)).andExpect(status().isOk());
        mvc.perform(delete("/funcionarios/" + id)).andExpect(status().isNotFound());
    }
}
