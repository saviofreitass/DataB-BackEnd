package com.example.data_cheque.adapters.http.usuario

import com.example.data_cheque.application.usuario.UsuarioService
import com.example.data_cheque.domain.usuario.Usuario
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioHandler(
    private val usuarioService: UsuarioService
) {
    fun findAll(): ResponseEntity<List<Usuario>>{
        val usuario = usuarioService.findAll()
        return ResponseEntity.ok(usuario)
    }

    fun findById(usuarioId: String): ResponseEntity<Usuario>{
        val usuario = usuarioService.findById(UUID.fromString(usuarioId))
        return ResponseEntity.ok(usuario)
    }
}