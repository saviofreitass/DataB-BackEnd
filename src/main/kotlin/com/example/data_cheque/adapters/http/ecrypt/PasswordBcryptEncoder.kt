package com.example.data_cheque.adapters.http.ecrypt

import com.example.data_cheque.application.usuario.EncoderPassword
import org.springframework.stereotype.Component
import org.springframework.security.crypto.password.PasswordEncoder

@Component
class PasswordBcryptEncoder(
    private val passwordBcryptEncoder: PasswordEncoder
): EncoderPassword {
    override fun encode(senha: String): String{
        return passwordBcryptEncoder.encode(senha)
    }

    override fun matches(rawPassword: String,
                         encoderPassword: String): Boolean{
        return passwordBcryptEncoder.matches(rawPassword, encoderPassword);
    }
}