package linketinder.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import linketinder.exceptions.CandidatoNotFoundException
import linketinder.exceptions.CompetenciaNotFoundException
import linketinder.exceptions.EmpresaNotFoundException
import linketinder.exceptions.RepositoryAccessException
import linketinder.model.Candidato
import linketinder.model.Empresa
import linketinder.model.dtos.EmpresaControllerResponseDTO
import linketinder.model.mappers.EmpresaMapper
import linketinder.service.EmpresaService
import linketinder.util.HttpHelper

import javax.servlet.ServletContext
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet("/empresas/*")
class EmpresaController extends HttpServlet {
    private EmpresaService empresaService

    EmpresaController() {}

    EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService
    }

    @Override
    void init() {
        ServletContext context = getServletContext()
        empresaService = (EmpresaService) context.getAttribute("empresaService")
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getPathInfo()

        if (path == null || path == "/") {
            this.listarEmpresas(response)
        } else {
            this.obterEmpresaPeloId(path, response)
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule())
        try {
            Empresa empresa = mapper.readValue(request.getReader(), Empresa.class)

            Integer id = this.adicionarEmpresa(empresa)
            response.setStatus(HttpServletResponse.SC_CREATED)
            response.getWriter().write("""
            |{
            |  "message": "empresa criada com sucesso",
            |  "uri": "localhost:8080/linketinder/empresas/$id
            |}
            """.stripMargin())
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("{\"message\": \"Formato JSON inválido\"}")
        } catch (RepositoryAccessException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |    "message": "Erro ao cadastrar a empresa",
                |    "cause": ${e.getMessage()}
                |}
            """.stripMargin())
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        ObjectMapper mapper = new ObjectMapper()
        mapper.registerModule(new JavaTimeModule())
        try {
            Empresa empresaAtualizada = mapper.readValue(request.getReader(), Empresa.class)

            this.editarEmpresa(empresaAtualizada)
            response.setStatus(HttpServletResponse.SC_CREATED)
            response.getWriter().write("""
            |{
            |  "message": "Empresa atualizada com sucesso",
            |  "uri": "localhost:8080/linketinder/empresas/$empresaAtualizada.id
            |}
            """.stripMargin())
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("{\"message\": \"Formato JSON inválido\"}")
        }  catch (EmpresaNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |   "message": "Empresa nao encontrada para realizar a atualizacao",
                |   "competenciasHref": "localhost:8080/linketinder-1.0-SNAPSHOT/empresas" 
                |}
            """.stripMargin())
        } catch (RepositoryAccessException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |    "message": "Erro ao editar a empresa"
                |    "cause": ${e.getMessage()}
                |}
            """.stripMargin())
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getPathInfo()

        if (path == null || path == "/") {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST)
            response.getWriter().write("""
                |{        
                |    "message": "Erro ao deletar a empresa, informe o id na requisicao"
                |}
            """.stripMargin())
        } else {
            try {
                int id = Integer.parseInt(path.split("/")[1])

                this.obterEmpresaPeloId(id)
                this.deletarEmpresa(id)

                response.setStatus(HttpServletResponse.SC_NO_CONTENT)
            } catch (EmpresaNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND)
                response.getWriter().write("""
                |{        
                |    "message": "Nao foi possivel encontrar a empresa com o id informado"
                |}
                """.stripMargin())
            }


        }
    }

    void listarEmpresas(HttpServletResponse response) {
        List<Empresa> empresas = this.empresaService.listarEmpresas()

        List<EmpresaControllerResponseDTO> empresaControllerResponseDTOS = EmpresaMapper.toControllerResponseDTOList(empresas)

        HttpHelper.writeResponse(
                response,
                HttpServletResponse.SC_OK,
                empresaControllerResponseDTOS
        )
    }

    void obterEmpresaPeloId(String pathInfo, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(pathInfo.split("/")[1])
            Empresa empresa = this.empresaService.obterEmpresaPeloId(id)

            EmpresaControllerResponseDTO empresaControllerResponseDTO = EmpresaMapper.toControllerResponseDTO(empresa)

            HttpHelper.writeResponse(
                    response,
                    HttpServletResponse.SC_OK,
                    empresaControllerResponseDTO
            )
        } catch (NumberFormatException e) {
            HttpHelper.writeResponse(
                    response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    null,
                    "Id inválido"
            )
        } catch (EmpresaNotFoundException e) {
            HttpHelper.writeResponse(
                    response,
                    HttpServletResponse.SC_NOT_FOUND,
                    null,
                    "Empresa não encontrada com o id informado"
            )
        }
    }


    List<Empresa> listarEmpresas() throws RepositoryAccessException {
        return empresaService.listarEmpresas()
    }

    Empresa obterEmpresaPeloId(Integer id) throws RepositoryAccessException, EmpresaNotFoundException {
        return empresaService.obterEmpresaPeloId(id)
    }

    Integer adicionarEmpresa(Empresa empresa) throws RepositoryAccessException {
        Integer id = empresaService.adicionarEmpresa(empresa)

        return id
    }

    void editarEmpresa(Empresa empresaAtualizada) throws RepositoryAccessException, EmpresaNotFoundException {
        empresaService.editarEmpresa(empresaAtualizada)
    }

    void deletarEmpresa(Integer id) throws RepositoryAccessException, EmpresaNotFoundException {
        empresaService.deleteEmpresa(id)
    }

}
