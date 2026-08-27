package com.exemplo.SB_Picpay_API.service;
 
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.exemplo.SB_Picpay_API.entities.Funcionario;
import com.exemplo.SB_Picpay_API.repository.FuncionarioRepository;
 
@Service
public class FuncionarioService {
 
    private static final List<String> STATUS_VALIDOS = Arrays.asList(
        "EM_ANALISE", "APROVADO", "REPROVADO", "CONTRATADO"
    );
 
    private static final int TAMANHO_CPF = 11;
    private static final Pattern REGEX_CPF = Pattern.compile("\\d{11}");
 
    @Autowired
    FuncionarioRepository repository;
 
    public Funcionario salvarFuncionario(Funcionario funcionario) {
        if (!dadosValidos(funcionario, null)) {
            return null;
        }
 
        return repository.salvar(funcionario);
    }
 
    public List<Funcionario> listarFuncionarios() {
        return repository.buscarTodos();
    }
 
    public Funcionario buscarFuncionarioPorId(Long id) {
        return repository.buscarPorId(id);
    }
 
    public Funcionario atualizarTudo(Long id, Funcionario funcionario) {
        if (!dadosValidos(funcionario, id)) {
            return null;
        }
 
        return repository.atualizarTudo(id, funcionario);
    }
 
    public Funcionario atualizarParcial(Long id, Funcionario funcionario) {
        if (!dadosValidosParaAtualizacaoParcial(funcionario, id)) {
            return null;
        }
 
        return repository.atualizarParcial(id, funcionario);
    }
 
    public boolean deletarFuncionario(Long id) {
        return repository.deletar(id);
    }
 
    private boolean dadosValidos(Funcionario funcionario, Long idAtual) {
        if (!cpfValido(funcionario.getCpf())) {
            return false;
        }
        if (repository.existeCpf(funcionario.getCpf(), idAtual)) {
            return false;
        }
        if (funcionario.getSalario() == null || funcionario.getSalario() < 0) {
            return false;
        }
        if (funcionario.getStatus() == null || !STATUS_VALIDOS.contains(funcionario.getStatus())) {
            return false;
        }
        return true;
    }
 
    private boolean dadosValidosParaAtualizacaoParcial(Funcionario funcionario, Long idAtual) {
        if (preenchido(funcionario.getCpf())) {
            if (!cpfValido(funcionario.getCpf())) {
                return false;
            }
            if (repository.existeCpf(funcionario.getCpf(), idAtual)) {
                return false;
            }
        }
        if (funcionario.getSalario() != null && funcionario.getSalario() < 0) {
            return false;
        }
        if (preenchido(funcionario.getStatus()) && !STATUS_VALIDOS.contains(funcionario.getStatus())) {
            return false;
        }
        return true;
    }

    private boolean preenchido(String valor) {
        return valor != null && !valor.isBlank();
    }
 
    private boolean cpfValido(String cpf) {
        if (cpf == null) {
            return false;
        }
        if (cpf.length() != TAMANHO_CPF) {
            return false;
        }
        return REGEX_CPF.matcher(cpf).matches();
    }
}