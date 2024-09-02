package com.linketinder.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class CandidatoTest {
    Candidato createCandidato() {
        Candidato candidato = new Candidato();

        candidato.setNome("André");
        candidato.setIdade(32);
        candidato.setEmail("andre.hopf@hotmail.com");
        candidato.setCpf("12345");
        candidato.setDescricao("Descrição");
        List<Competencia> competencias = new ArrayList<>();
        competencias.add(new Competencia("Java"));
        Endereco endereco = new Endereco();
        candidato.setCompetencias(competencias);
        candidato.setEndereco(endereco);

        return candidato;
    }

    @Test
    @DisplayName("Quando invocado toString deve retornar uma representação correta em texto do Candidato")
    void toStringTest() {
        Candidato candidato = createCandidato();

        assertEquals("Candidato: André, andre.hopf@hotmail.com, idade: 32 anos, cpf: 12345, Competencias: [Java]", candidato.toString()) ;
    }

    @Test
    @DisplayName("Quando getCpf é invocado deve retornar o cpf do candidato")
    void getCpf() {
        Candidato candidato = createCandidato();

        assertEquals("12345", candidato.getCpf());
    }

    @Test
    @DisplayName("Quando setCpf é invocado deve alterar o cpf do candidato pelo novo valor")
    void setCpf() {
        Candidato candidato = createCandidato();
        candidato.setCpf("1234578");

        assertEquals("1234578", candidato.getCpf());
    }

    @Test
    @DisplayName("Quando getIdade é invocado deve retornar a idade do candidato")
    void getIdade() {
        Candidato candidato = createCandidato();

        assertEquals(32, candidato.getIdade());
    }

    @Test
    @DisplayName("Quando setIdade é invocado deve alterar o cpf do candidato pelo novo valor")
    void setIdade() {
        Candidato candidato = createCandidato();
        candidato.setIdade(10);

        assertEquals(10, candidato.getIdade());
    }
}