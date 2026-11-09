package com.exemplo.SB_Picpay_API.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.exemplo.SB_Picpay_API.entities.Funcionario;
import com.exemplo.SB_Picpay_API.service.FuncionarioService;

@ExtendWith(MockitoExtension.class)
class FuncionarioControllerTest {

    @Mock
    FuncionarioService service;

    @InjectMocks
    FuncionarioController controller;

    private final Funcionario f = new Funcionario(1L, "Ana", "12345678901", "Dev", "TI", 5000, "SP", "APROVADO");

    @Test
    void listarRetorna200ComALista() {
        when(service.listarFuncionarios()).thenReturn(List.of(f));

        var resposta = controller.listarFuncionarios();

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(List.of(f), resposta.getBody());
    }

    @Test
    void contarRetorna200ComATotalDeFuncionarios() {
        when(service.listarFuncionarios()).thenReturn(List.of(f, f));

        var resposta = controller.contarFuncionarios();

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(2, resposta.getBody());
    }

    @Test
    void buscarPorIdRetorna200OuNotFound() {
        when(service.buscarFuncionarioPorId(1L)).thenReturn(f);
        when(service.buscarFuncionarioPorId(2L)).thenReturn(null);

        assertEquals(HttpStatus.OK, controller.buscarFuncionarioPorId(1L).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, controller.buscarFuncionarioPorId(2L).getStatusCode());
    }

    @Test
    void cadastrarRetorna201OuBadRequest() {
        Funcionario invalido = new Funcionario();
        when(service.salvarFuncionario(f)).thenReturn(f);
        when(service.salvarFuncionario(invalido)).thenReturn(null);

        assertEquals(HttpStatus.CREATED, controller.cadastrarFuncionario(f).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, controller.cadastrarFuncionario(invalido).getStatusCode());
    }

    @Test
    void atualizarRetorna200OuBadRequest() {
        Funcionario invalido = new Funcionario();
        when(service.atualizarTudo(1L, f)).thenReturn(f);
        when(service.atualizarTudo(1L, invalido)).thenReturn(null);

        assertEquals(HttpStatus.OK, controller.atualizarFuncionario(1L, f).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, controller.atualizarFuncionario(1L, invalido).getStatusCode());
    }

    @Test
    void atualizarParcialRetorna200OuBadRequest() {
        Funcionario invalido = new Funcionario();
        when(service.atualizarParcial(1L, f)).thenReturn(f);
        when(service.atualizarParcial(1L, invalido)).thenReturn(null);

        assertEquals(HttpStatus.OK, controller.atualizarFuncionarioParcial(1L, f).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, controller.atualizarFuncionarioParcial(1L, invalido).getStatusCode());
    }

    @Test
    void deletarRetorna200OuNotFound() {
        when(service.deletarFuncionario(1L)).thenReturn(true);
        when(service.deletarFuncionario(2L)).thenReturn(false);

        assertEquals(HttpStatus.OK, controller.deletarFuncionario(1L).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, controller.deletarFuncionario(2L).getStatusCode());
    }
}
