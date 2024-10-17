package linketinder.model.enums

enum TabelaCompetencia {
    COMPETENCIAS_CANDIDATO("competencias_candidato"),
    COMPETENCIAS_VAGA("competencias_vaga")

    TabelaCompetencia(String nomeTabela) {
        this.nomeTabela = nomeTabela
    }

    private final String nomeTabela

    String getNomeTabela() {
        return nomeTabela
    }
}