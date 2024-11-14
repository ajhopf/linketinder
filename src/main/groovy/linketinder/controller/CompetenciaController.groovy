package linketinder.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import linketinder.exceptions.CandidatoNotFoundException
import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Candidato
import linketinder.model.Competencia
import linketinder.model.dtos.CandidatoControllerResponseDTO
import linketinder.model.dtos.CompetenciaControllerResponseDTO
import linketinder.model.mappers.CandidatoMapper
import linketinder.model.mappers.CompetenciaMapper
import linketinder.service.CandidatoService
import linketinder.service.CompetenciaService
import linketinder.util.HttpHelper

import javax.servlet.ServletContext
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/competencias/*")
class CompetenciaController extends HttpServlet {
    private CompetenciaService competenciaService

    CompetenciaController() {}

    CompetenciaController(CompetenciaService competenciaService) {
        this.competenciaService = competenciaService
    }

    @Override
    void init() {
        ServletContext context = getServletContext()
        competenciaService = (CompetenciaService) context.getAttribute("competenciaService")
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getPathInfo()

        if (path == null || path == "/") {
            this.listarCompetencias(response)
        } else {
            this.obterCompetenciaPeloId(path, response)
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule())
        try {
            Competencia competencia = mapper.readValue(request.getReader(), Competencia.class)

            Integer id = this.adicionarCompetencia(competencia.competencia)

            response.setStatus(HttpServletResponse.SC_CREATED)
            response.getWriter().write("""
            |{
            |  "message": "Competencia criada com sucesso",
            |  "uri": "localhost:8080/linketinder/competencias/$id
            |}
            """.stripMargin())
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("{\"message\": \"Formato JSON inválido\"}")
        } catch (RepositoryAccessException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |    "message": "Erro ao cadastrar a competencia",
                |    "cause": ${e.getMessage()}
                |}
            """.stripMargin())
        }
    }

    void listarCompetencias(HttpServletResponse response) {
        List<Competencia> competencias = this.competenciaService.listarCompetencias()

        List<CompetenciaControllerResponseDTO> controllerResponseDTOList =
                CompetenciaMapper.toControllerResponseDTOList(competencias)


        HttpHelper.writeResponse(
                response,
                HttpServletResponse.SC_OK,
                controllerResponseDTOList
        )
    }

    void obterCompetenciaPeloId(String pathInfo, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(pathInfo.split("/")[1])
            Competencia competencia = this.competenciaService.obterCompetenciaPeloId(id)
            CompetenciaControllerResponseDTO competenciaControllerResponseDTO =
                    CompetenciaMapper.toCompetenciaControllerResponseDTO(competencia)

            HttpHelper.writeResponse(
                    response,
                    HttpServletResponse.SC_OK,
                    competenciaControllerResponseDTO
            )
        } catch (NumberFormatException e) {
            HttpHelper.writeResponse(
                    response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    null,
                    "Id inválido"
            )
        } catch (CompetenciaNotFoundException e) {
            HttpHelper.writeResponse(
                    response,
                    HttpServletResponse.SC_NOT_FOUND,
                    null,
                    "Competencia não encontrada com o id informado"
            )
        }
    }

    Integer obterIdDeCompetencia(String competencia) throws CompetenciaNotFoundException, RepositoryAccessException {
        return competenciaService.obterIdDeCompetencia(competencia)
    }

    Competencia obterCompetenciaPeloId(Integer id) throws CompetenciaNotFoundException, RepositoryAccessException {
        return competenciaService.obterCompetenciaPeloId(id)
    }

    Integer adicionarCompetencia(String competencia) throws RepositoryAccessException {
        return competenciaService.adicionarCompetencia(competencia)
    }

    List<Competencia> listarCompetencias() throws RepositoryAccessException {
        List<Competencia> competencias = competenciaService.listarCompetencias()

        return competencias
    }

    void editarCompetencia(Competencia competencia) throws CompetenciaNotFoundException, RepositoryAccessException {
        competenciaService.editarCompetencia(competencia)
    }

    void deletarCompetencia(Integer id) throws CompetenciaNotFoundException, RepositoryAccessException {
        competenciaService.deletarCompetencia(id)
    }
}
