package com.exemplo.SB_Picpay_API.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class FuncionarioTest {

    @Test
    void construtorCompletoEGettersExpoemOsValores() {
        Funcionario f = new Funcionario(1L, "Ana", "12345678901", "Dev", "TI", 5000, "SP", "APROVADO");

        assertEquals(1L, f.getId());
        assertEquals("Ana", f.getNome());
        assertEquals("12345678901", f.getCpf());
        assertEquals("Dev", f.getCargo());
        assertEquals("TI", f.getDepartamento());
        assertEquals(5000, f.getSalario());
        assertEquals("SP", f.getCidade());
        assertEquals("APROVADO", f.getStatus());
    }

    @Test
    void settersAlteramOsValores() {
        Funcionario f = new Funcionario();
        assertNull(f.getId());

        f.setId(2L);
        f.setNome("Bia");
        f.setCpf("10987654321");
        f.setCargo("QA");
        f.setDepartamento("Qualidade");
        f.setSalario(4000);
        f.setCidade("RJ");
        f.setStatus("EM_ANALISE");

        assertEquals(2L, f.getId());
        assertEquals("Bia", f.getNome());
        assertEquals("10987654321", f.getCpf());
        assertEquals("QA", f.getCargo());
        assertEquals("Qualidade", f.getDepartamento());
        assertEquals(4000, f.getSalario());
        assertEquals("RJ", f.getCidade());
        assertEquals("EM_ANALISE", f.getStatus());
    }
}
