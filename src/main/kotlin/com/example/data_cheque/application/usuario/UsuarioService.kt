package com.example.data_cheque.application.usuario

import com.example.data_cheque.application.usuario.exception.UsuarioNaoEncontradoException
import com.example.data_cheque.domain.usuario.Usuario
import com.example.data_cheque.domain.usuario.UsuarioRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository
) {
    fun findAll(): List<Usuario> {
        return usuarioRepository.findAll()
    }
    fun findById(usuarioId: UUID): Usuario {
        return usuarioRepository.findById(usuarioId) ?: throw UsuarioNaoEncontradoException(usuarioId)
    }

    fun findByEmail(email: String): Usuario? {
        return usuarioRepository.findByEmail(email)
    }

    fun insert(usuarioCreateCommand: UsuarioCreateCommand): Usuario {
        val usuarioDomain = usuarioCreateCommand.toUsuario()
        usuarioRepository.insert(usuarioDomain)
        return findById(usuarioDomain.id)
    }

    fun update(usuarioUpdateCommand: UsuarioUpdateCommand): Usuario {
        usuarioRepository.findById(usuarioUpdateCommand.id) ?: throw UsuarioNaoEncontradoException(usuarioUpdateCommand.id)
        usuarioRepository.update(usuarioUpdateCommand.toUsuario(usuarioUpdateCommand.id))
        return findById(usuarioUpdateCommand.id)
    }

    fun delete(usuarioId: UUID){
        usuarioRepository.findById(usuarioId = usuarioId) ?: throw UsuarioNaoEncontradoException(usuarioId)
        usuarioRepository.delete(usuarioId)
    }


}