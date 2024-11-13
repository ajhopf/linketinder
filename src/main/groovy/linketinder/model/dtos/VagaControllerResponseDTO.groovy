package linketinder.model.dtos

import linketinder.model.Competencia
import linketinder.model.Endereco

class VagaControllerResponseDTO {
    Integer id
    String nome
    String descricao
    int empresaId
    String empresa
    Endereco endereco
    List<Competencia> competencias
}
