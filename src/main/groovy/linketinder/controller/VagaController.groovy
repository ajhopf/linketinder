package linketinder.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import linketinder.exceptions.CandidatoNotFoundException
import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.EmpresaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.exceptions.VagaNotFoundException
import linketinder.model.Competencia
import linketinder.model.Vaga
import linketinder.model.dtos.VagaControllerResponseDTO
import linketinder.model.mappers.VagaMapper
import linketinder.service.EmpresaService
import linketinder.service.VagaService
import linketinder.util.HttpHelper

import javax.servlet.ServletContext
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/vagas/*")
class VagaController extends HttpServlet  {
    private VagaService vagaService
    private EmpresaService empresaService
    private CompetenciaController competenciaController

    VagaController() {}

    VagaController(VagaService vagaService, CompetenciaController competenciaController) {
        this.vagaService = vagaService
        this.competenciaController = competenciaController
    }

    @Override
    void init() {
        ServletContext context = getServletContext()
        vagaService = (VagaService) context.getAttribute("vagaService")
        empresaService = (EmpresaService) context.getAttribute("empresaService")
        competenciaController = (CompetenciaController) context.getAttribute("competenciaController")
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getPathInfo()

        if (path == null || path == "/") {
            this.listarVagas(response)
        } else {
            this.obterVagaPeloId(path, response)
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule())
        try {
            Vaga vaga = mapper.readValue(request.getReader(), Vaga.class)

            empresaService.obterEmpresaPeloId(vaga.empresa.id)

            Integer id = this.adicionarVaga(vaga)
            response.setStatus(HttpServletResponse.SC_CREATED)
            response.getWriter().write("""
            |{
            |  "message": "Vaga criada com sucesso",
            |  "uri": "localhost:8080/linketinder/vagas/$id
            |}
            """.stripMargin())
        } catch (EmpresaNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND)
            response.getWriter().write("""
                |{        
                |   "message": "Empresa nao encontrada, nao foi possivel cadastrar a vaga",
                |   "empresasHref": "localhost:8080/linketinder-1.0-SNAPSHOT/empresas" 
                |}
            """.stripMargin())
        } catch (CompetenciaNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |   "message": "Competencia nao encontrada",
                |   "competenciasHref": "localhost:8080/linketinder-1.0-SNAPSHOT/competencias" 
                |}
            """.stripMargin())
        } catch (RepositoryAccessException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |    "message": "Erro ao cadastrar a vaga",
                |    "cause": ${e.getMessage()}
                |}
            """.stripMargin())
        } catch (IOException e) {
            println e.getMessage()
            println e.getStackTrace()
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("{\"message\": \"Formato JSON inválido\"}")
        }
    }


    void listarVagas(HttpServletResponse response) {
        List<Vaga> vagas = this.vagaService.listarVagas()

        List<VagaControllerResponseDTO> vagaControllerResponseDTOS = VagaMapper.toListControllerResponseDto(vagas)

        HttpHelper.writeResponse(
                response,
                HttpServletResponse.SC_OK,
                vagaControllerResponseDTOS
        )
    }

    void obterVagaPeloId(String pathInfo, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(pathInfo.split("/")[1])
            Vaga vaga = this.vagaService.obterVagaPeloId(id)

            VagaControllerResponseDTO vagaControllerResponseDTO = VagaMapper.toControllerResponseDTO(vaga)

            HttpHelper.writeResponse(
                    response,
                    HttpServletResponse.SC_OK,
                    vagaControllerResponseDTO
            )
        } catch (NumberFormatException e) {
            HttpHelper.writeResponse(
                    response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    null,
                    "Id inválido"
            )
        } catch (CandidatoNotFoundException e) {
            HttpHelper.writeResponse(
                    response,
                    HttpServletResponse.SC_NOT_FOUND,
                    null,
                    "Vaga não encontrada com o id informado"
            )
        }
    }


    void verificarSeCompetenciasExistem(List<Competencia> competencias) throws CompetenciaNotFoundException {
        competencias.each { competencia ->
            competenciaController.obterIdDeCompetencia(competencia.competencia)
        }
    }

    List<Vaga> listarVagas() throws RepositoryAccessException {
        return vagaService.listarVagas()
    }

    Vaga obterVagaPeloId(Integer id) throws RepositoryAccessException, VagaNotFoundException {
        return vagaService.obterVagaPeloId(id)
    }

    Integer adicionarVaga(Vaga vaga) throws RepositoryAccessException, CompetenciaNotFoundException {
        this.verificarSeCompetenciasExistem(vaga.competencias)

        vagaService.adicionarVaga(vaga)
    }

    void editarVaga(Vaga vagaAtualizada) throws VagaNotFoundException, RepositoryAccessException, CompetenciaNotFoundException {
        this.verificarSeCompetenciasExistem(vagaAtualizada.competencias)
        this.obterVagaPeloId(vagaAtualizada.id)

        vagaService.updateVaga(vagaAtualizada)
    }

    void deletarVaga(Integer id) throws RepositoryAccessException, VagaNotFoundException {
        vagaService.deletarVaga(id)
    }
}
