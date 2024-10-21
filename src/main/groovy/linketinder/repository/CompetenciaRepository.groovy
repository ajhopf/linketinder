package linketinder.repository

import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.model.dtos.CompetenciaDTO
import linketinder.model.enums.Afinidade
import linketinder.repository.interfaces.CompetenciaDAO
import groovy.sql.Sql


class CompetenciaRepository implements CompetenciaDAO {
    private Sql sql = null

    CompetenciaRepository(Sql sql) {
        this.sql = sql
    }

    @Override
    List<CompetenciaDTO> listarCompetenciasDeCandidatoOuVaga(Integer entidadeId, String nomeTabela) {
        def statement = """
                select c.id, c.competencia, t.afinidade, t.anos_experiencia 
                from competencias_candidato t
                INNER JOIN competencias c
                ON t.competencia_id = c.id
                WHERE t.usuario_id = $entidadeId;
            """

        if (nomeTabela == 'competencias_vaga') {
            statement = """
                select c.id, c.competencia, t.afinidade, t.anos_experiencia 
                from competencias_vaga t
                INNER JOIN competencias c
                ON t.competencia_id = c.id
                WHERE t.vaga_id = $entidadeId;
            """
        }

        List<CompetenciaDTO> competencias = []

        sql.eachRow(statement) {row ->
            CompetenciaDTO competencia = new CompetenciaDTO()
            competencia.id = row.getInt('id')
            competencia.competencia = row.getString('competencia')
            competencia.anosExperiencia = row.getInt('anos_experiencia')
            competencia.afinidade = Afinidade.fromValor(row.getInt('afinidade'))

            competencias << competencia
        }

        return competencias
    }

    @Override
    List<CompetenciaDTO> listarCompetencias() {
        def statement = """
                SELECT *
                FROM competencias c
            """

        List<CompetenciaDTO> competencias = []

        sql.eachRow(statement) {row ->
            CompetenciaDTO competencia = new CompetenciaDTO()
            competencia.competencia = row.getString('competencia')
            competencia.id = row.getInt('id')

            competencias << competencia
        }

        return competencias
    }

    @Override
    CompetenciaDTO obterCompetenciaPeloId(Integer id) {
        def statement = """
                SELECT *
                FROM competencias c
                WHERE c.id = $id 
            """

        CompetenciaDTO competenciaDTO = new CompetenciaDTO()

        this.sql.eachRow(statement) {row ->
            competenciaDTO.competencia = row.getString('competencia')
            competenciaDTO.id = row.getInt('id')
        }

        if (competenciaDTO.competencia == null) {
            throw new CompetenciaNotFoundException("Não foi possível localizar a competencia com o id = $id")
        }

        return competenciaDTO
    }

    @Override
    Integer adicionarCompetencia(String competencia) {
        def inserirCompetencia = """
            INSERT INTO competencias (competencia)
            VALUES ($competencia)
        """

        def keys = sql.executeInsert(inserirCompetencia)

        return keys[0][0] as Integer
    }

    @Override
    void adicionarCompetenciaUsuario(CompetenciaDTO competenciaDTO, Integer usuarioId)  {
        def inserirCompetencia = """
            INSERT INTO competencias_candidato (usuario_id, competencia_id, anos_experiencia, afinidade)
            VALUES (?, ?, ?, ?)
        """

        def competenciaParams = [usuarioId, competenciaDTO.id, competenciaDTO.anosExperiencia, competenciaDTO.afinidade.getAfinidade()]

        sql.executeInsert(inserirCompetencia, competenciaParams)
    }

    @Override
    void adicionarCompetenciasVaga(CompetenciaDTO competenciaDTO, Integer vagaId)  {
        def inserirCompetencia = """
            INSERT INTO competencias_vaga (vaga_id, competencia_id, anos_experiencia, afinidade)
            VALUES (?, ?, ?, ?)
        """

        def competenciaParams = [vagaId, competenciaDTO.id, competenciaDTO.anosExperiencia, competenciaDTO.afinidade.getAfinidade()]

        sql.executeInsert(inserirCompetencia, competenciaParams)
    }

    @Override
    Integer obterIdDeCompetencia(String competenciaString) {
        def statement = """
            SELECT id FROM competencias c WHERE c.competencia LIKE $competenciaString
        """

        def row = sql.firstRow(statement)

        if (row == null) {
            throw new CompetenciaNotFoundException("Não foi possível encontrar a competência ${competenciaString}")
        }

        return row.id as Integer
    }

    @Override
    void updateCompetencia(Integer competenciaId, CompetenciaDTO competencia) {
        def statement = """
            UPDATE competencias c
            SET competencia = $competencia.competencia
            WHERE c.id = $competenciaId
        """

        def result = sql.executeUpdate(statement)

        if (result == 0 ) {
            throw new CompetenciaNotFoundException('Não foi possível atualizar a competencia')
        }
    }

    @Override
    void deleteCompetencia(Integer id) {
        def statement = """
            DELETE FROM competencias c
            WHERE c.id = $id
        """

        int rowsAffected = sql.executeUpdate(statement)

        if (rowsAffected == 0) {
            throw new CompetenciaNotFoundException("Não foi possível encontrar uma competencia com id $id")
        }
    }

    @Override
    void deleteCompetenciasEntidade(Integer entidadeId, String tabela) {
        def stmt = """
            DELETE FROM competencias_candidato
            WHERE usuario_id = $entidadeId
        """

        if (tabela == 'competencias_vaga') {
            stmt = """
                DELETE FROM competencias_vaga
                WHERE vaga_id = $entidadeId
            """
        }

        sql.executeUpdate(stmt)
    }
}
