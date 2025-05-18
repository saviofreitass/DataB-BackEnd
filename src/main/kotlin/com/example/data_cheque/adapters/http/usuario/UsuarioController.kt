package com.example.data_cheque.adapters.http.usuario

import com.example.data_cheque.domain.usuario.Usuario
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

@RestController
@CrossOrigin(origins = arrayOf("*"))
class UsuarioController(
    private val usuarioHandler: UsuarioHandler
) {
    @GetMapping("/usuario")
    fun findAll(): ResponseEntity<List<Usuario>>{
        return usuarioHandler.findAll()
    }

    @GetMapping("/usuario/{usuarioId:$UUID_REGEX}")
    fun findById(@PathVariable usuarioId: String): ResponseEntity<Usuario>{
        return usuarioHandler.findById(usuarioId)
    }
}