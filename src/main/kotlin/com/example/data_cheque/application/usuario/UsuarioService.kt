package com.example.data_cheque.application.usuario

import com.example.data_cheque.application.usuario.exception.EmailDuplicadoException
import com.example.data_cheque.application.usuario.exception.EmailException
import com.example.data_cheque.application.usuario.exception.EmailInvalidoException
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

    fun insert(usuarioCreatCommand: UsuarioCreatCommand): Usuario {
        val usuarioDomain = usuarioCreatCommand.toUsuario(usuarioCreatCommand.id)
        usuarioRepository.insert(usuarioDomain)
        return findById(usuarioDomain.id)
    }

    fun update(usuario: UsuarioCreatCommand): Usuario {
        usuarioRepository.findById(usuario.id) ?: throw UsuarioNaoEncontradoException(usuario.id)
        usuarioRepository.update(usuario.toUsuario(usuario.id))
        return findById(usuario.id)
    }

    fun delete(usuarioId: UUID){
        usuarioRepository.findById(usuarioId = usuarioId) ?: throw UsuarioNaoEncontradoException(usuarioId)
        usuarioRepository.delete(usuarioId)
    }


}