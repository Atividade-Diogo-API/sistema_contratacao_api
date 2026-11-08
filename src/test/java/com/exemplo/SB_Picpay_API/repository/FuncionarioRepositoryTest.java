package com.exemplo.SB_Picpay_API.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.exemplo.SB_Picpay_API.entities.Funcionario;

class FuncionarioRepositoryTest {

    private FuncionarioRepository repository;

    @BeforeEach
    void setUp() {
        repository = new FuncionarioRepository();
    }

    private Funcionario novo(String cpf) {
        return new Funcionario(null, "Ana", cpf, "Dev", "TI", 5000, "SP", "APROVADO");
    }

    @Test
    void salvarAtribuiIdsSequenciais() {
        assertEquals(1L, repository.salvar(novo("11111111111")).getId());
        assertEquals(2L, repository.salvar(novo("22222222222")).getId());
        assertEquals(2, repository.buscarTodos().size());
    }

    @Test
    void buscarPorIdEncontraOuRetornaNull() {
        Funcionario salvo = repository.salvar(novo("11111111111"));

        assertSame(salvo, repository.buscarPorId(salvo.getId()));
        assertNull(repository.buscarPorId(99L));
    }

    @Test
    void buscarPorIdIgnoraRegistroSemId() {
        Funcionario salvo = repository.salvar(novo("11111111111"));
        salvo.setId(null);

        assertNull(repository.buscarPorId(1L));
    }

    @Test
    void existeCpfConsideraOutrosRegistrosEIgnoraOProprio() {
        Funcionario salvo = repository.salvar(novo("11111111111"));
        repository.salvar(novo(null));

        assertTrue(repository.existeCpf("11111111111", null));
        assertTrue(repository.existeCpf("11111111111", 99L));
        assertFalse(repository.existeCpf("11111111111", salvo.getId()));
        assertFalse(repository.existeCpf("33333333333", null));
    }

    @Test
    void atualizarTudoSubstituiTodosOsCampos() {
        Funcionario salvo = repository.salvar(novo("11111111111"));
        Funcionario dados = new Funcionario(null, "Bia", "22222222222", "QA", "Qualidade", 4000, "RJ", "CONTRATADO");

        Funcionario atualizado = repository.atualizarTudo(salvo.getId(), dados);

        assertEquals("Bia", atualizado.getNome());
        assertEquals("22222222222", atualizado.getCpf());
        assertEquals("QA", atualizado.getCargo());
        assertEquals("Qualidade", atualizado.getDepartamento());
        assertEquals(4000, atualizado.getSalario());
        assertEquals("RJ", atualizado.getCidade());
        assertEquals("CONTRATADO", atualizado.getStatus());
        assertNull(repository.atualizarTudo(99L, dados));
    }

    @Test
    void atualizarParcialAlteraApenasCamposPreenchidos() {
        Funcionario salvo = repository.salvar(novo("11111111111"));
        Funcionario dados = new Funcionario(null, "Bia", "22222222222", "QA", "Qualidade", 4000, "RJ", "CONTRATADO");

        Funcionario atualizado = repository.atualizarParcial(salvo.getId(), dados);

        assertEquals("Bia", atualizado.getNome());
        assertEquals("22222222222", atualizado.getCpf());
        assertEquals("QA", atualizado.getCargo());
        assertEquals("Qualidade", atualizado.getDepartamento());
        assertEquals(4000, atualizado.getSalario());
        assertEquals("RJ", atualizado.getCidade());
        assertEquals("CONTRATADO", atualizado.getStatus());
    }

    @Test
    void atualizarParcialIgnoraNulosEEmBranco() {
        Funcionario salvo = repository.salvar(novo("11111111111"));
        Funcionario vazio = new Funcionario(null, " ", null, "", null, null, "  ", null);

        Funcionario atualizado = repository.atualizarParcial(salvo.getId(), vazio);

        assertEquals("Ana", atualizado.getNome());
        assertEquals("11111111111", atualizado.getCpf());
        assertEquals("Dev", atualizado.getCargo());
        assertEquals("TI", atualizado.getDepartamento());
        assertEquals(5000, atualizado.getSalario());
        assertEquals("SP", atualizado.getCidade());
        assertEquals("APROVADO", atualizado.getStatus());
    }

    @Test
    void atualizarParcialRetornaNullQuandoIdNaoExiste() {
        assertNull(repository.atualizarParcial(99L, novo("11111111111")));
    }

    @Test
    void deletarRemoveOuInformaQueNaoExiste() {
        Funcionario salvo = repository.salvar(novo("11111111111"));

        assertTrue(repository.deletar(salvo.getId()));
        assertTrue(repository.buscarTodos().isEmpty());
        assertFalse(repository.deletar(salvo.getId()));
    }
}
