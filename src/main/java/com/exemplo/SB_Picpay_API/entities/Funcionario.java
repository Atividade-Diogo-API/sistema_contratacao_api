package com.exemplo.SB_Picpay_API.entities;

public class Funcionario {
    
    private Long id;
    private String nome;
    private String cpf;
    private String cargo;
    private String departamento;
    private Integer salario;
    private String cidade;
    private String status;
    
    public Funcionario (
        Long id, 
        String nome,
        String cpf,
        String cargo,
        String departamento,
        Integer salario,
        String cidade,
        String status
    ) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.departamento = departamento;
        this.salario = salario;
        this.cidade = cidade;
        this.status = status;
    }

    public Funcionario() {

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCargo() {
        return cargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public Integer getSalario() {
        return salario;
    }

    public String getCidade() {
        return cidade;
    }

    public String getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void sgetSalario(Integer salario) {
        this.salario = salario;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
