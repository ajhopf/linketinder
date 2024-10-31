package linketinder.config

import groovy.sql.Sql
import linketinder.controller.CandidatoController
import linketinder.controller.CompetenciaController
import linketinder.controller.EmpresaController
import linketinder.controller.VagaController
import linketinder.repository.CandidatoRepository
import linketinder.repository.CompetenciaRepository
import linketinder.repository.EmpresaRepository
import linketinder.repository.EnderecoRepository
import linketinder.repository.VagaRepository
import linketinder.service.CandidatoService
import linketinder.service.CompetenciaService
import linketinder.service.EmpresaService
import linketinder.service.EnderecoService
import linketinder.service.VagaService
import linketinder.util.sql.PostgreSqlConnection
import linketinder.util.sql.SqlFactory

class LinketinderConfig {
    static Map getBeans() {
        Sql postgreSql = getPostgreConnection()
        Map controllers = getControllers(postgreSql)

        return controllers
    }

    static Sql getPostgreConnection() {
        PostgreSqlConnection postgreSqlConnection = new PostgreSqlConnection()

        Sql sql = SqlFactory.newInstance(postgreSqlConnection)

        return sql
    }

    static Map getServices(Sql sql) {
        EmpresaRepository empresaRepository = EmpresaRepository.getInstance(sql)
        CandidatoRepository candidatoRepository = CandidatoRepository.getInstance(sql)
        EnderecoRepository enderecoRepository = EnderecoRepository.getInstance(sql)
        CompetenciaRepository competenciaRepository = CompetenciaRepository.getInstance(sql)
        VagaRepository vagaRepository = VagaRepository.getInstance(sql)

        EnderecoService enderecoService = new EnderecoService(enderecoRepository)
        CompetenciaService competenciaService = new CompetenciaService(competenciaRepository)
        EmpresaService empresaService = new EmpresaService(empresaRepository, enderecoService)
        CandidatoService candidatoService = new CandidatoService(candidatoRepository, enderecoService, competenciaService)
        VagaService vagaService = new VagaService(vagaRepository, competenciaService, enderecoService)

        return [
                enderecoService: enderecoService,
                competenciaService: competenciaService,
                empresaService: empresaService,
                candidatoService: candidatoService,
                vagaService: vagaService
        ]
    }

    static Map getControllers(Sql sql) {
        Map services = getServices(sql)

        CompetenciaController competenciaController = new CompetenciaController(services.competenciaService)
        EmpresaController empresaController = new EmpresaController(services.empresaService)
        VagaController vagaController = new VagaController(services.vagaService, competenciaController)
        CandidatoController candidatoController = new CandidatoController(services.candidatoService, competenciaController)

        return [
                competenciaController: competenciaController,
                empresaController: empresaController,
                vagaController: vagaController,
                candidatoController: candidatoController
        ]
    }
}
