package com.example.data_cheque.adapters.http.error

import com.example.data_cheque.application.contador.exception.ContadorNaoEncontradoException
import com.example.data_cheque.application.funcionario.exception.FuncionarioNaoEncontradoException
import com.example.data_cheque.application.pessoa.exception.PessoaNaoEncontradaException
import com.example.data_cheque.application.usuario.exception.EmailExistenteException
import com.example.data_cheque.application.usuario.exception.EmailInvalidoException
import com.example.data_cheque.application.usuario.exception.SenhaInvalida
import com.example.data_cheque.application.usuario.exception.UsuarioNaoEncontradoException
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
        is ContadorNaoEncontradoException -> toResponse(
         id = this.contadorId,
            statusCode = HttpStatus.NOT_FOUND
       )
        is PessoaNaoEncontradaException -> toResponse(
            id = this.pessoaId,
            statusCode = HttpStatus.NOT_FOUND
        )
        is UsuarioNaoEncontradoException -> toResponse(
            id = this.usuarioId,
            statusCode = HttpStatus.NOT_FOUND
        )
        is SenhaInvalida -> toResponse(
            statusCode = HttpStatus.BAD_REQUEST
        )
        is EmailInvalidoException -> toResponse(
            statusCode = HttpStatus.BAD_REQUEST
        )
        is EmailExistenteException -> toResponse(
            statusCode = HttpStatus.BAD_REQUEST
        )
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
        LOGGER.error(fullMessage, this)
    } else {
        LOGGER.warn(fullMessage)
    }
    return statusCode to response
}

fun Throwable.toServerResponse(): ResponseEntity<ErrorResponse> {
    val (statusCode, response) = toResponse()
    return ResponseEntity.status(statusCode).body(response)
}