package com.linketinder.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoTest {
    Endereco criarEndereco() {
        Endereco endereco = new Endereco();
        endereco.setPais("Brasil");
        endereco.setEstado("Santa Catarina");
        endereco.setCep("1234");

        return endereco;
    }

    @Test
    @DisplayName("Quando getPais é invocado deve retornar o pais")
    void getPaisTest() {
        Endereco endereco = criarEndereco();

        assertEquals("Brasil", endereco.getPais());
    }

    @Test
    @DisplayName("Quando setPais é invocado deve setar o novo pais")
    void setPaisTest() {
        Endereco endereco = criarEndereco();
        endereco.setPais("Uruguai");

        assertEquals("Uruguai", endereco.getPais());
    }

    @Test
    @DisplayName("Quando getEstado é invocado deve retornar o estado")
    void getEstadoTest() {
        Endereco endereco = criarEndereco();

        assertEquals("Santa Catarina", endereco.getEstado());
    }

    @Test
    @DisplayName("Quando setEstado é invocado deve setar o novo estado")
    void setEstadoTest() {
        Endereco endereco = criarEndereco();
        endereco.setEstado("Goiás");

        assertEquals("Goiás", endereco.getEstado());
    }

    @Test
    @DisplayName("Quando getCep é invocado deve retornar o cep")
    void getCepTest() {
        Endereco endereco = criarEndereco();

        assertEquals("1234", endereco.getCep());
    }

    @Test
    @DisplayName("Quando setCep é invocado deve setar o novo cep")
    void setCepTest() {
        Endereco endereco = criarEndereco();
        endereco.setCep("123456");

        assertEquals("123456", endereco.getCep());
    }
}