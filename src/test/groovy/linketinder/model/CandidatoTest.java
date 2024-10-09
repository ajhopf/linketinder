package linketinder.model;

import linketinder.model.enums.Afinidade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class CandidatoTest {
    Candidato createCandidato() {
        Candidato candidato = new Candidato();

        candidato.setNome("André");
        candidato.setSobrenome("Hopf");
        candidato.setEmail("andre.hopf@hotmail.com");
        candidato.setCpf("12345");
        candidato.setDescricao("Descrição");
        List<Competencia> competencias = new ArrayList<>();
        competencias.add(new Competencia("Java", 3.0, Afinidade.MUITO_ALTA));
        Endereco endereco = new Endereco();
        candidato.setCompetencias(competencias);
        candidato.setEndereco(endereco);

        return candidato;
    }

    @Test
    @DisplayName("Quando invocado toString deve retornar uma representação correta em texto do Candidato")
    void toStringTest() {
        Candidato candidato = createCandidato();

        assertEquals("Candidato: André, Hopf, andre.hopf@hotmail.com, data de nascimento: null, telefone: nullcpf: 12345, Competencias: [Nome: Java; Anos Experiência: 3.0; Afinidade: MUITO_ALTA]Endereço: Endereco(null, null, null, null)", candidato.toString()) ;
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

}