package com.exemplo.SB_Picpay_API.repository;
 
import com.exemplo.SB_Picpay_API.entities.Funcionario;
 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
 
import org.springframework.stereotype.Repository;
 
@Repository
public class FuncionarioRepository {
 
    private final List<Funcionario> listaFuncionarios = new ArrayList<>();
    private final AtomicLong contadorId = new AtomicLong(1);
 
    public Funcionario salvar(Funcionario funcionario) {
        funcionario.setId(contadorId.getAndIncrement());
        listaFuncionarios.add(funcionario);
        return funcionario;
    }
 
    public List<Funcionario> buscarTodos() {
        return listaFuncionarios;
    }
 
    public Funcionario buscarPorId(Long id) {
        for (Funcionario f : listaFuncionarios) {
            if (f.getId() != null && f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }
 
    public boolean existeCpf(String cpf, Long idParaIgnorar) {
        for (Funcionario f : listaFuncionarios) {
            boolean mesmoCpf = f.getCpf() != null && f.getCpf().equals(cpf);
            boolean ehOutroRegistro = idParaIgnorar == null || !idParaIgnorar.equals(f.getId());
 
            if (mesmoCpf && ehOutroRegistro) {
                return true;
            }
        }
        return false;
    }
 
    public Funcionario atualizarTudo(Long id, Funcionario novoFuncionario) {
        Funcionario f = buscarPorId(id);
 
        if (f == null) {
            return null;
        }
 
        f.setNome(novoFuncionario.getNome());
        f.setCpf(novoFuncionario.getCpf());
        f.setCargo(novoFuncionario.getCargo());
        f.setDepartamento(novoFuncionario.getDepartamento());
        f.setSalario(novoFuncionario.getSalario());
        f.setCidade(novoFuncionario.getCidade());
        f.setStatus(novoFuncionario.getStatus());
 
        return f;
    }
 
    public Funcionario atualizarParcial(Long id, Funcionario dados) {
        Funcionario f = buscarPorId(id);
 
        if (f == null) {
            return null;
        }
 
        if (preenchido(dados.getNome())) {
            f.setNome(dados.getNome());
        }
        if (preenchido(dados.getCpf())) {
            f.setCpf(dados.getCpf());
        }
        if (preenchido(dados.getCargo())) {
            f.setCargo(dados.getCargo());
        }
        if (preenchido(dados.getDepartamento())) {
            f.setDepartamento(dados.getDepartamento());
        }
        if (dados.getSalario() != null) {
            f.setSalario(dados.getSalario());
        }
        if (preenchido(dados.getCidade())) {
            f.setCidade(dados.getCidade());
        }
        if (preenchido(dados.getStatus())) {
            f.setStatus(dados.getStatus());
        }
 
        return f;
    }
 
    public boolean deletar(Long id) {
        Funcionario f = buscarPorId(id);
 
        if (f == null) {
            return false;
        }
 
        listaFuncionarios.remove(f);
        return true;
    }

    private boolean preenchido(String valor) {
        return valor != null && !valor.isBlank();
    }
}