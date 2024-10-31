package linketinder

import linketinder.controller.CandidatoController
import linketinder.controller.CompetenciaController
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
import linketinder.util.PostgreSqlConnection
import linketinder.util.SqlFactory
import linketinder.view.MenuInicial
import groovy.sql.Sql

class Main {
    static void main(String[] args) {
        try {
            Map services = getServices()

            CompetenciaController competenciaController = new CompetenciaController(services.competenciaService)

            CandidatoController candidatoController = new CandidatoController(services.candidatoService, competenciaController)

            MenuInicial.iniciar(candidatoController, services.empresaService, services.candidatoService, services.competenciaService, services.vagaService)
        } catch (Exception e) {
            println e.getStackTrace()
            println e
        }
    }

    static Map getServices() {
        PostgreSqlConnection postgreSqlConnection = new PostgreSqlConnection()

        Sql sql = SqlFactory.newInstance(postgreSqlConnection)

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

}


