package linketinder.exceptions

class RepositoryAccessException extends RuntimeException {
    RepositoryAccessException(String message, Throwable cause) {
        super(message, cause)
    }
}
