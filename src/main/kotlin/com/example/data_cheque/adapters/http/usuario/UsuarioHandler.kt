package com.example.data_cheque.adapters.http.usuario

import com.example.data_cheque.application.usuario.UsuarioCommand
import com.example.data_cheque.application.usuario.UsuarioService
import com.example.data_cheque.domain.usuario.Usuario
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import java.util.*

@Component
class UsuarioHandler(
    private val usuarioService: UsuarioService
) {
    fun findAll(): ResponseEntity<List<Usuario>> {
        val usuario = usuarioService.findAll()
        return ResponseEntity.ok(usuario)
    }

    fun findById(usuarioId: String): ResponseEntity<Usuario> {
        val usuario = usuarioService.findById(UUID.fromString(usuarioId))
        return ResponseEntity.ok(usuario)
    }

    fun insert(usuarioCommand: UsuarioCommand): ResponseEntity<Usuario>{
        val usuario = usuarioService.insert(usuarioCommand)
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario)
    }

    fun update(usuarioUpdateCommand: UsuarioCommand, usuarioId: String): ResponseEntity<Usuario>{
        val usuario = usuarioService.update(usuarioUpdateCommand, UUID.fromString(usuarioId))
        return ResponseEntity.ok(usuario)
    }

    fun delete(usuarioId: String): ResponseEntity<Usuario>{
        val usuario = usuarioService.delete(UUID.fromString(usuarioId))
        return ResponseEntity.noContent().build()
    }
}