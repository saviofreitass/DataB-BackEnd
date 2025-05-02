package com.example.data_cheque.adapters.http.ecrypt.exceptions

import com.example.data_cheque.adapters.http.error.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        val format = Json { ignoreUnknownKeys = true }

        response.status = HttpStatus.UNAUTHORIZED.value()
        response.addHeader("Content-Type", "application/json")

        response.writer.write(format.encodeToString(ErrorResponse(message = "Falha na autenticação")))
    }
}