package com.exemplo.SB_Picpay_API.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.exemplo.SB_Picpay_API.entities.Funcionario;
import com.exemplo.SB_Picpay_API.service.FuncionarioService;
 
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
 
    @Autowired
    FuncionarioService service;
 
    @GetMapping
    public ResponseEntity<Object> listarFuncionarios() {
        List<Funcionario> listaFuncionarios = service.listarFuncionarios();

        return ResponseEntity.ok(listaFuncionarios);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarFuncionarioPorId(@PathVariable Long id) {
        Funcionario funcionario = service.buscarFuncionarioPorId(id);
 
        if (funcionario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(HttpStatus.NOT_FOUND.toString());
        } else {
            return ResponseEntity.ok(funcionario);
        }
    }
 
    @PostMapping
    public ResponseEntity<String> cadastrarFuncionario(@RequestBody Funcionario funcionario) {
        Funcionario funcionarioSalvo = service.salvarFuncionario(funcionario);
 
        if (funcionarioSalvo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar o funcionário. Verifique os dados fornecidos.");
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED.toString());
        }
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionario) {
        Funcionario funcionarioAtualizado = service.atualizarTudo(id, funcionario);
 
        if (funcionarioAtualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o funcionário. Verifique os dados fornecidos.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK.toString());
        }
    }
 
    @PatchMapping("/{id}")
    public ResponseEntity<String> atualizarFuncionarioParcial(@PathVariable Long id, @RequestBody Funcionario funcionario) {
        Funcionario funcionarioAtualizado = service.atualizarParcial(id, funcionario);
 
        if (funcionarioAtualizado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar o funcionário. Verifique os dados fornecidos.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK.toString());
        }
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarFuncionario(@PathVariable Long id) {
        boolean deletado = service.deletarFuncionario(id);
 
        if (!deletado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado.");
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK.toString());
        }
    }
}