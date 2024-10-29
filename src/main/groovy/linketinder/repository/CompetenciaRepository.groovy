package linketinder.repository

import groovy.sql.GroovyRowResult
import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.model.dtos.CompetenciaDTO
import linketinder.model.enums.Afinidade
import linketinder.repository.interfaces.CompetenciaDAO
import groovy.sql.Sql

class CompetenciaRepository implements CompetenciaDAO {
    private static CompetenciaRepository instance = null
    private Sql sql = null

    private CompetenciaRepository(Sql sql) {
        this.sql = sql
    }

    static synchronized CompetenciaRepository getInstance(Sql sql) {
        if (instance == null) {
            return new CompetenciaRepository(sql)
        }

        return instance
    }

    private List<CompetenciaDTO> listarCompetenciasDeEntidade(String statement) {
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
    List<CompetenciaDTO> listarCompetenciasDeCandidato(Integer candidatoId) {
        GString statement = """
                select c.id, c.competencia, t.afinidade, t.anos_experiencia 
                from competencias_candidato t
                INNER JOIN competencias c
                ON t.competencia_id = c.id
                WHERE t.usuario_id = $candidatoId;
            """

        return listarCompetenciasDeEntidade(statement)
    }

    @Override
    List<CompetenciaDTO> listarCompetenciasDeVaga(Integer vagaId) {
        GString statement = """
            select c.id, c.competencia, t.afinidade, t.anos_experiencia 
            from competencias_vaga t
            INNER JOIN competencias c
            ON t.competencia_id = c.id
            WHERE t.vaga_id = $vagaId;
        """

        return listarCompetenciasDeEntidade(statement)
    }


    @Override
    List<CompetenciaDTO> listarCompetencias() {
        String statement = """
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
        String statement = """
                SELECT *
                FROM competencias c
                WHERE c.id = ?
            """

        GroovyRowResult row = this.sql.firstRow(statement, [id])

        if (row == null) {
            throw new CompetenciaNotFoundException("Não foi possível localizar a competência com o id = $id")
        }

        CompetenciaDTO competenciaDTO = new CompetenciaDTO()
        competenciaDTO.competencia = row.get('competencia')
        competenciaDTO.id = row.get('id') as Integer

        return competenciaDTO
    }

    @Override
    Integer adicionarCompetencia(String competencia) {
        GString inserirCompetencia = """
            INSERT INTO competencias (competencia)
            VALUES ($competencia)
        """

        def keys = sql.executeInsert(inserirCompetencia)

        return keys[0][0] as Integer
    }

    @Override
    void adicionarCompetenciaCandidato(CompetenciaDTO competenciaDTO, Integer candidatoId)  {
        String inserirCompetencia = """
            INSERT INTO competencias_candidato (usuario_id, competencia_id, anos_experiencia, afinidade)
            VALUES (?, ?, ?, ?)
        """

        List<Number> competenciaParams = [candidatoId, competenciaDTO.id, competenciaDTO.anosExperiencia, competenciaDTO.afinidade.getAfinidade()]

        sql.executeInsert(inserirCompetencia, competenciaParams)
    }

    @Override
    void adicionarCompetenciaVaga(CompetenciaDTO competenciaDTO, Integer vagaId)  {
        String inserirCompetencia = """
            INSERT INTO competencias_vaga (vaga_id, competencia_id, anos_experiencia, afinidade)
            VALUES (?, ?, ?, ?)
        """

        List<Number> competenciaParams = [vagaId, competenciaDTO.id, competenciaDTO.anosExperiencia, competenciaDTO.afinidade.getAfinidade()]

        sql.executeInsert(inserirCompetencia, competenciaParams)
    }

    @Override
    Integer obterIdDeCompetencia(String competenciaString) {
        GString statement = """
            SELECT id FROM competencias c WHERE c.competencia LIKE $competenciaString
        """

        GroovyRowResult row = sql.firstRow(statement)

        if (row == null) {
            throw new CompetenciaNotFoundException("Não foi possível encontrar a competência ${competenciaString}")
        }

        return row.id as Integer
    }

    @Override
    void updateCompetencia(Integer competenciaId, CompetenciaDTO competencia) {
        GString statement = """
            UPDATE competencias c
            SET competencia = $competencia.competencia
            WHERE c.id = $competenciaId
        """

        int result = sql.executeUpdate(statement)

        if (result == 0 ) {
            throw new CompetenciaNotFoundException('Não foi possível atualizar a competencia')
        }
    }

    @Override
    void deletarCompetencia(Integer id) {
        GString statement = """
            DELETE FROM competencias c
            WHERE c.id = $id
        """

        int rowsAffected = sql.executeUpdate(statement)

        if (rowsAffected == 0) {
            throw new CompetenciaNotFoundException("Não foi possível encontrar uma competencia com id $id")
        }
    }

    void deletarCompetenciasCandidato(Integer candidatoId) {
        GString stmt = """
            DELETE FROM competencias_candidato
            WHERE usuario_id = $candidatoId
        """

        sql.executeUpdate(stmt)
    }

    void deletarCompetenciasVaga(Integer vagaId) {
        GString stmt = """
                DELETE FROM competencias_vaga
                WHERE vaga_id = $vagaId
            """

        sql.executeUpdate(stmt)
    }


}
