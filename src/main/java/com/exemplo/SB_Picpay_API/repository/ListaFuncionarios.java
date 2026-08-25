package com.exemplo.SB_Picpay_API.repository;

import com.exemplo.SB_Picpay_API.entities.Funcionario;
import java.util.ArrayList;

public class ListaFuncionarios {
    
    private final ArrayList<Funcionario> listaFuncionarios;

    public ListaFuncionarios(ArrayList<Funcionario> listaFuncionarios) {
        this.listaFuncionarios = listaFuncionarios;
    }

    public void salvar(Funcionario funcionario) {
        listaFuncionarios.add(funcionario);
    }

    public ArrayList<Funcionario> buscarTodos() {
        return listaFuncionarios;
    }

    public Funcionario buscarPorId(Long id) {
        for (Funcionario f : listaFuncionarios) {
            if (f.getId() != null && f.getId() == id) {
                return f;
            }
        }
    }

    public Funcionario atualizarTudo(Funcionario novoFuncionario) {
        for (Funcionario f : listaFuncionarios) {
            if (f.getId() != null && f.getId() = novoFuncionario.getId()) {
                listaFuncionarios.remove(f);

                f.setNome(novoFuncionario.getNome());
                f.setCpf(novoFuncionario.getCpf());
                f.setCargo(novoFuncionario.getCargo());
                f.setDepartamento(novoFuncionario.getDepartamento());
                f.setSalario(novoFuncionario.getSalario());
                f.setCidade(novoFuncionario.getCidade());
                f.setStatus(novoFuncionario.getStatus());

                listaFuncionarios.add(f);
                return f;
            }
        }
    }

    public Funcionario atualizarFuncionario(Funcionario dados) {
        for (Funcionario f : listaFuncionarios) {
            if (f.getId() != null && f.getId() = dados.getId()) {
                listaFuncionarios.remove(f);

                if (dados.getNome() != null) {
                    f.setNome(dados.getNome());
                }

                if (dados.getCpf() != null) {
                    f.setCpf(dados.getCpf());
                }

                if (dados.getCargo() != null) {
                    f.setCargo(dados.getCargo());
                }

                if (dados.getDepartamento() != null) {
                    f.setDepartamento(dados.getDepartamento());
                }

                if (dados.getSalario() != null) {
                    f.setSalario(dados.getSalario());
                }

                if (dados.getCidade() != null) {
                    f.setCidade(dados.getCidade());
                }

                if (dados.getStatus() != null) {
                    f.setStatus(dados.getStatus());
                }

                listaFuncionarios.add(f);
                return f;
            }
        }
    }

    public void deletar(Funcionario funcionario) {
        listaFuncionarios.remove(funcionario);
    }
}
