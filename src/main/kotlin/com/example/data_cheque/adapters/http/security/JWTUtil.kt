package com.example.data_cheque.adapters.http.security

import com.example.data_cheque.application.contador.ContadorService
import com.example.data_cheque.application.funcionario.FuncionarioService
import com.example.data_cheque.application.usuario.UsuarioService
import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Jwts.SIG
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import javax.crypto.SecretKey
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*

@Component
class JWTUtil (
    private val usuarioService: UsuarioService,
    private val funcionarioService: FuncionarioService,
    private val contadorService: ContadorService
) {
    private val expiration: Long = 24 * 60 * 60 * 1000

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    fun generateToken(usuario: Usuario): String?{
        val pessoa: Pessoa?;
        if(usuario.tipoUsuario == Role.ROLE_FUNCIONARIO){
            val funcionario = funcionarioService.findByIdUser(usuario.id)
            pessoa = funcionario?.pessoa
        }else{
            val contador = contadorService.findByUserId(usuario.id)
            pessoa = contador?.pessoa
        }

        return Jwts.builder()
            .subject(usuario.id.toString())
            .claim("email", usuario.email)
            .claim("tipo", usuario.tipoUsuario.toString())
            .claim("nome", pessoa?.nome)
            .expiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSecretKey(), SIG.HS512)
            .compact()
    }

    private fun getSecretKey(): SecretKey {
        val keyByte = Decoders.BASE64.decode(secret)
        return Keys.hmacShaKeyFor(keyByte)
    }

    fun isValid(jwt: String?): Boolean {
        return try {
            Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(jwt)
            true
        }catch (e: JwtException) {
            throw e
        }
    }

    fun getAuthotetication(jwt: String?): Authentication {
        val username = Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(jwt).payload.subject
        return UsernamePasswordAuthenticationToken(username, null, null)
    }
}