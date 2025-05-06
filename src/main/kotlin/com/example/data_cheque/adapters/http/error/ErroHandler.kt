package com.example.data_cheque.adapters.http.error

import com.example.data_cheque.adapters.http.security.exceptions.CredenciaisException
import com.example.data_cheque.application.funcionario.exception.FuncionarioNaoEncontradoException
import com.example.data_cheque.application.usuario.exception.*
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*

private val LOGGER = KotlinLogging.logger{ }

@ControllerAdvice
class ErrorHandler() {
    @ExceptionHandler(Exception::class)
    fun handlerException(ex: Exception): ResponseEntity<ErrorResponse> {
        return ex.toServerResponse()
    }
}

private fun Throwable.toResponse(): Pair<HttpStatus, ErrorResponse> =
    when (this) {
        is FuncionarioNaoEncontradoException -> toResponse(
            id = this.funcionarioId,
            statusCode = HttpStatus.NOT_FOUND
        )
        is EmailException -> toResponse(
            statusCode = HttpStatus.BAD_REQUEST
        )
        is EmailDuplicadoException -> toResponse(
            statusCode = HttpStatus.BAD_REQUEST
        )
        is EmailInvalidoException -> toResponse(
            statusCode = HttpStatus.BAD_REQUEST
        )
        is SenhaInvalidaException -> toResponse(
            statusCode = HttpStatus.BAD_REQUEST
        )
        is UsuarioNaoEncontradoException -> toResponse(
            id = this.usuarioId,
            statusCode = HttpStatus.NOT_FOUND
        )
        is CredenciaisException -> toResponse(
            statusCode = HttpStatus.BAD_REQUEST
        )
//        is ContadorNaoEncontradoException -> toResponse(
//            id = this.contadorId,
//            statusCode = HttpStatus.NOT_FOUND
//        )
        else ->  {
            toResponse(
                statusCode = HttpStatus.BAD_REQUEST
            )
        }
    }

private fun Throwable.toResponse(
    id: UUID? = null,
    statusCode: HttpStatus,
    message: String = this.message ?: ""
): Pair<HttpStatus, ErrorResponse> {
    val response = ErrorResponse(
        id = id,
        message = message
    )

    val fullMessage = "[${statusCode.value()}] [${this.javaClass.simpleName}] $message"
    if (statusCode.is5xxServerError) {
        LOGGER.error (this) {fullMessage}
    } else {
        LOGGER.warn { fullMessage }
    }
    return statusCode to response
}

fun Throwable.toServerResponse(): ResponseEntity<ErrorResponse> {
    val (statusCode, response) = toResponse()
    return ResponseEntity.status(statusCode).body(response)
}