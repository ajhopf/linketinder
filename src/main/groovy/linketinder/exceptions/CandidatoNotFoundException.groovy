package linketinder.exceptions

class CandidatoNotFoundException extends RuntimeException{
    CandidatoNotFoundException(String mensagem) {
        super(mensagem)
    }
}
