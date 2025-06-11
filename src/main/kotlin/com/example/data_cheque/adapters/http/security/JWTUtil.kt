package com.example.data_cheque.adapters.http.security

import com.example.data_cheque.application.contador.ContadorService
import com.example.data_cheque.application.funcionario.FuncionarioService
import com.example.data_cheque.application.usuario.UsuarioService
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
        val claims = mutableMapOf<String, Any?>(
            "email" to usuario.email,
            "tipo" to usuario.tipoUsuario.toString()
        )

        val pessoa = when (usuario.tipoUsuario) {
            Role.ROLE_FUNCIONARIO -> {
                funcionarioService.findByUserId(usuario.id)?.also { f ->
                    claims["contador_id"] = f.contadorId
                    claims["id"] = f.id
                }?.pessoa
            }
            else -> contadorService.findByUserId(usuario.id)?.also { c ->
                claims["id"] = c.id
            }?.pessoa
        }

        claims["nome"] = pessoa?.nome

        return Jwts.builder()
            .subject(usuario.id.toString())
            .also { builder -> claims.forEach { (k, v) -> builder.claim(k, v) } }
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

    fun decodificarJwtPayload(jwt: String): String {
        return try {
            val payload = jwt.split(".")[1]
            val decodedBytes = Base64.getUrlDecoder().decode(payload)
            String(decodedBytes)
        } catch (e: Exception) {
            "Erro ao decodificar JWT: ${e.message}"
        }
    }
}