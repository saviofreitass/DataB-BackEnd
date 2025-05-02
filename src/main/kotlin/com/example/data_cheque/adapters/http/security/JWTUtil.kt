package com.example.data_cheque.adapters.http.security

import com.example.data_cheque.application.usuario.UsuarioService
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
    private val usuarioService: UsuarioService
) {
    private val expiration: Long = 24 * 60 * 60 * 1000

    @Value("\${jwt.secret}")
    private lateinit var secret: String

    fun generateToken(usuario: Usuario): String?{
        return Jwts.builder()
            .id(usuario.id.toString())
            .subject((usuario.email))
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