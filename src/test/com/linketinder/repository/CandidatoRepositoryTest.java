package com.linketinder.repository;

import com.linketinder.model.Candidato;
import com.linketinder.model.Empresa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class CandidatoRepositoryTest {
    @Nested
    @DisplayName("Método: adicionarCandidato")
    class AdicionarCandidatoTests {
        @Test
        @DisplayName("Quando método é invocado, adiciona o candidato à lista de candidatos. ")
        void adicionarCandidato() {
            //arrange
            CandidatoRepository candidatoRepository = new CandidatoRepository();
            Candidato candidato = new Candidato();

            //act
            candidatoRepository.adicionarCandidato(candidato);

            //assert
            assertEquals(1, candidatoRepository.getCandidatos().size());
        }
    }

    @Nested
    @DisplayName("Método: listaCandidatos")
    class ListarCandidatosTests {
        @Test
        @DisplayName("Quando método é invocado, retorna lista com todos os candidatos existentes ")
        void listarCandidatos() {
            //arrange
            CandidatoRepository candidatoRepository = new CandidatoRepository();

            List<Candidato> candidatos = new ArrayList<>();
            Candidato candidato1 = new Candidato();
            Candidato candidato2 = new Candidato();
            Candidato candidato3 = new Candidato();
            candidatos.add(candidato1);
            candidatos.add(candidato2);
            candidatos.add(candidato3);

            candidatoRepository.setCandidatos(candidatos);
            //act
            List<Candidato> resultado =  candidatoRepository.listarCandidatos();

            //assert
            assertEquals(3, resultado.size());
            assertEquals(resultado.get(0), candidato1);
            assertEquals(resultado.get(1), candidato2);
            assertEquals(resultado.get(2), candidato3);
        }
    }

}