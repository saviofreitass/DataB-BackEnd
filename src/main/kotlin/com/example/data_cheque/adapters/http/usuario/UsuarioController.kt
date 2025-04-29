package com.example.data_cheque.adapters.http.usuario

import com.example.data_cheque.adapters.http.funcionario.FuncionarioHandler
import com.example.data_cheque.application.usuario.UsuarioCommand
import com.example.data_cheque.domain.usuario.Usuario
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

private const val UUID_REGEX = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}"

@RestController
@CrossOrigin(origins = arrayOf("*"))
class UsuarioController (
    private val usuarioHandler: UsuarioHandler,
    private val funcionarioHandler: FuncionarioHandler
    ){
    @GetMapping("/usuario")
    fun findAll(): ResponseEntity<List<Usuario>>{
        return usuarioHandler.findAll()
    }

    @GetMapping("/usuario/{usuarioId:$UUID_REGEX}")
    fun findById(@PathVariable usuarioId: String): ResponseEntity<Usuario>{
        return usuarioHandler.findById(usuarioId)
    }

    @PostMapping("/usuario/cadastro")
    fun insert(@RequestBody usuario: UsuarioCommand): ResponseEntity<Usuario> {
        return usuarioHandler.insert(usuario)
    }

    @PutMapping("/usuario/{usuarioId:$UUID_REGEX}")
    fun update(@RequestBody usuario: UsuarioCommand,
               @PathVariable usuarioId: String): ResponseEntity<Usuario>{
        return usuarioHandler.update(usuario, usuarioId)
    }

    @DeleteMapping("/usuario/{usuarioId:${UUID_REGEX}}")
    fun delete(@PathVariable usuarioId: String): ResponseEntity<String> {
        return funcionarioHandler.delete(usuarioId)
    }
}