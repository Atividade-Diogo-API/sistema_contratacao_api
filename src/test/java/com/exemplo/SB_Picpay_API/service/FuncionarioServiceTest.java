package com.exemplo.SB_Picpay_API.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.exemplo.SB_Picpay_API.entities.Funcionario;
import com.exemplo.SB_Picpay_API.repository.FuncionarioRepository;

@ExtendWith(MockitoExtension.class)
class FuncionarioServiceTest {

    @Mock
    FuncionarioRepository repository;

    @InjectMocks
    FuncionarioService service;

    private Funcionario valido() {
        return new Funcionario(null, "Ana", "12345678901", "Dev", "TI", 5000, "SP", "APROVADO");
    }

    private void cpfLivre() {
        when(repository.existeCpf(any(), any())).thenReturn(false);
    }

    @Test
    void salvarDelegaAoRepositorioQuandoValido() {
        Funcionario f = valido();
        cpfLivre();
        when(repository.salvar(f)).thenReturn(f);

        assertSame(f, service.salvarFuncionario(f));
    }

    @Test
    void salvarRejeitaCpfNulo() {
        Funcionario f = valido();
        f.setCpf(null);

        assertNull(service.salvarFuncionario(f));
        verify(repository, never()).salvar(any());
    }

    @Test
    void salvarRejeitaCpfComTamanhoErrado() {
        Funcionario f = valido();
        f.setCpf("123");

        assertNull(service.salvarFuncionario(f));
    }

    @Test
    void salvarRejeitaCpfComLetras() {
        Funcionario f = valido();
        f.setCpf("1234567890a");

        assertNull(service.salvarFuncionario(f));
    }

    @Test
    void salvarRejeitaCpfDuplicado() {
        when(repository.existeCpf("12345678901", null)).thenReturn(true);

        assertNull(service.salvarFuncionario(valido()));
    }

    @Test
    void salvarRejeitaSalarioNuloOuNegativo() {
        cpfLivre();
        Funcionario nulo = valido();
        nulo.setSalario(null);
        Funcionario negativo = valido();
        negativo.setSalario(-1);

        assertNull(service.salvarFuncionario(nulo));
        assertNull(service.salvarFuncionario(negativo));
    }

    @Test
    void salvarRejeitaStatusNuloOuInvalido() {
        cpfLivre();
        Funcionario nulo = valido();
        nulo.setStatus(null);
        Funcionario invalido = valido();
        invalido.setStatus("QUALQUER");

        assertNull(service.salvarFuncionario(nulo));
        assertNull(service.salvarFuncionario(invalido));
    }

    @Test
    void atualizarTudoDelegaQuandoValidoEIgnoraOProprioCpf() {
        Funcionario f = valido();
        when(repository.existeCpf("12345678901", 1L)).thenReturn(false);
        when(repository.atualizarTudo(1L, f)).thenReturn(f);

        assertSame(f, service.atualizarTudo(1L, f));
    }

    @Test
    void atualizarTudoRejeitaDadosInvalidos() {
        Funcionario f = valido();
        f.setCpf(null);

        assertNull(service.atualizarTudo(1L, f));
        verify(repository, never()).atualizarTudo(any(), any());
    }

    @Test
    void atualizarParcialAceitaObjetoSemCamposValidaveis() {
        Funcionario vazio = new Funcionario();
        when(repository.atualizarParcial(1L, vazio)).thenReturn(vazio);

        assertSame(vazio, service.atualizarParcial(1L, vazio));
    }

    @Test
    void atualizarParcialAceitaCpfValidoELivreSalarioEStatusValidos() {
        Funcionario f = new Funcionario(null, null, "12345678901", null, null, 0, null, "CONTRATADO");
        when(repository.existeCpf("12345678901", 1L)).thenReturn(false);
        when(repository.atualizarParcial(1L, f)).thenReturn(f);

        assertSame(f, service.atualizarParcial(1L, f));
    }

    @Test
    void atualizarParcialRejeitaCpfInvalido() {
        Funcionario f = new Funcionario();
        f.setCpf("123");

        assertNull(service.atualizarParcial(1L, f));
        verify(repository, never()).atualizarParcial(any(), any());
    }

    @Test
    void atualizarParcialRejeitaCpfDuplicado() {
        Funcionario f = new Funcionario();
        f.setCpf("12345678901");
        when(repository.existeCpf("12345678901", 1L)).thenReturn(true);

        assertNull(service.atualizarParcial(1L, f));
    }

    @Test
    void atualizarParcialRejeitaSalarioNegativo() {
        Funcionario f = new Funcionario();
        f.setSalario(-5);

        assertNull(service.atualizarParcial(1L, f));
    }

    @Test
    void atualizarParcialRejeitaStatusInvalido() {
        Funcionario f = new Funcionario();
        f.setStatus("QUALQUER");

        assertNull(service.atualizarParcial(1L, f));
    }

    @Test
    void atualizarParcialIgnoraStatusEmBranco() {
        Funcionario f = new Funcionario();
        f.setStatus(" ");
        when(repository.atualizarParcial(1L, f)).thenReturn(f);

        assertSame(f, service.atualizarParcial(1L, f));
    }

    @Test
    void listarBuscarEDeletarDelegamAoRepositorio() {
        Funcionario f = valido();
        when(repository.buscarTodos()).thenReturn(List.of(f));
        when(repository.buscarPorId(1L)).thenReturn(f);
        when(repository.deletar(1L)).thenReturn(true);
        when(repository.deletar(2L)).thenReturn(false);

        assertEquals(List.of(f), service.listarFuncionarios());
        assertSame(f, service.buscarFuncionarioPorId(1L));
        assertTrue(service.deletarFuncionario(1L));
        assertFalse(service.deletarFuncionario(2L));
    }
}
