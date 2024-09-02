package com.linketinder.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmpresaTest {
    Empresa createEmpresa() {
        Empresa empresa = new Empresa();

        empresa.setNome("Empresa");
        empresa.setEmail("empresa@hotmail.com");
        empresa.setCnpj("12345");
        empresa.setDescricao("Descrição");
        List<Competencia> competencias = new ArrayList<>();
        competencias.add(new Competencia("Java"));
        Endereco endereco = new Endereco();
        empresa.setCompetencias(competencias);
        empresa.setEndereco(endereco);

        return empresa;
    }

    @Test
    @DisplayName("Quando invocado toString deve retornar uma representação correta em texto da Empresa")
    void toStringTest() {
        Empresa empresa = createEmpresa();

        assertEquals("Empresa: Empresa, empresa@hotmail.com, cpnj: 12345, Competencias: [Java]", empresa.toString()) ;
    }

    @Test
    @DisplayName("Quando getCnpj é invocado deve retornar o cnpj da empresa")
    void getCnpjTest() {
        Empresa empresa = createEmpresa();

        assertEquals("12345", empresa.getCnpj());
    }

    @Test
    @DisplayName("Quando setCnpj é invocado deve alterar o cnpj da empresa pelo novo valor")
    void setCpf() {
        Empresa empresa = createEmpresa();
        empresa.setCnpj("1234578");

        assertEquals("1234578", empresa.getCnpj());
    }

}