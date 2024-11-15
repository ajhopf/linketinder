package linketinder.model.dtos

import java.time.LocalDate

class CandidatoDTO extends UsuarioDTO {
    String cpf
    String sobrenome
    LocalDate dataNascimento
    String telefone
}
