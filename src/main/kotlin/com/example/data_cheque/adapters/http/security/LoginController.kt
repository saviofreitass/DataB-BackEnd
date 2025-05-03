package com.example.data_cheque.adapters.http.security

import com.example.data_cheque.adapters.http.security.exceptions.CredenciaisInvalidasException
import com.example.data_cheque.adapters.http.security.request.Credenciais
import com.example.data_cheque.adapters.http.security.response.Token
import com.example.data_cheque.application.usuario.EncoderPassword
import com.example.data_cheque.application.usuario.UsuarioService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity

@RestController
@CrossOrigin(origins = arrayOf("*"))
class LoginController(
    private val usuarioService: UsuarioService,
    private val encoderPassword: EncoderPassword,
    private val jwtUtil: JWTUtil
) {

    @PostMapping("/login")
    fun auth(@RequestBody credenciais: Credenciais): ResponseEntity<Token>{
        val usuario = usuarioService.findByEmail(credenciais.email) ?: throw CredenciaisInvalidasException()

        if (!encoderPassword.matches(credenciais.senha, usuario.senha)){
            throw CredenciaisInvalidasException()
        }

        val acessToken = jwtUtil.generateToken(usuario) ?: throw CredenciaisInvalidasException()

        return ResponseEntity.ok(Token(acessToken))
    }
}