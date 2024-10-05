package com.linketinder.model;

import com.linketinder.model.enums.Afinidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CompetenciaTest {
    @Test
    @DisplayName("Quando invocado toString deve retornar uma representação correta em texto da Competencia")
    void toStringTest() {
        Competencia competencia = new Competencia("Java", 5.0, Afinidade.MUITO_ALTA);

        assertEquals("Java", competencia.toString()) ;
    }

    @Test
    @DisplayName("Quando getCompetencia é invocado deve retornar a competencia")
    void getCompetenciaTest() {
        Competencia competencia = new Competencia("Java", 5.0, Afinidade.MUITO_ALTA);

        assertEquals("Java", competencia.getCompetencia()) ;
    }

    @Test
    @DisplayName("Quando setCompetencia é invocado deve alterar a competencia pelo novo valor")
    void setCompetenciaTest() {
        Competencia competencia = new Competencia("Java", 5.0, Afinidade.MUITO_ALTA);
        competencia.setCompetencia("Groovy");

        assertEquals("Groovy", competencia.getCompetencia());
    }
}