package linketinder.model.dtos

import linketinder.model.Competencia

import java.time.LocalDate

class CandidatoControllerResponseDTO extends UsuarioControllerResponseDTO {
    String cpf
    String sobrenome
    LocalDate dataNascimento
    String telefone
    List<Competencia> competencias
}
