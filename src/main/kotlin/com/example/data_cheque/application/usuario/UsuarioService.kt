package com.example.data_cheque.application.usuario

import com.example.data_cheque.application.usuario.exception.UsuarioNaoEncontradoException
import com.example.data_cheque.application.usuario.strategy.AccountValidationEstrategy
import com.example.data_cheque.application.usuario.strategy.Impl.createAccount.EmailValidationImpl
import com.example.data_cheque.application.usuario.strategy.Impl.createAccount.PasswordValidationImpl
import com.example.data_cheque.application.usuario.strategy.Impl.updateAccount.EmailUpdateValidationImpl
import com.example.data_cheque.application.usuario.strategy.Impl.updateAccount.PasswordUpdateValidationImpl
import com.example.data_cheque.domain.usuario.Usuario
import com.example.data_cheque.domain.usuario.UsuarioRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val encoderPassword: EncoderPassword
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

        val usuarioDomain = usuarioCreateCommand.toUsuario(encoderPassword)

        usuarioRepository.insert(usuarioDomain)
        
        return findById(usuarioDomain.id)
    }

    fun update(usuarioUpdateCommand: UsuarioUpdateCommand, id: UUID): Usuario {
        val usuarioEncontrado = usuarioRepository.findById(id) ?: throw UsuarioNaoEncontradoException(id)

        usuarioRepository.update(usuarioUpdateCommand.toUsuarioAtualizado(usuarioEncontrado, encoderPassword))
        return findById(id)
    }

    fun delete(usuarioId: UUID){
        usuarioRepository.findById(usuarioId = usuarioId) ?: throw UsuarioNaoEncontradoException(usuarioId)
        usuarioRepository.delete(usuarioId)
    }

    fun createValidations(usuarioCreateCommand: UsuarioCreateCommand){
        val validationsStrategies: List<AccountValidationEstrategy<UsuarioCreateCommand>> = listOf(
            EmailValidationImpl(usuarioRepository),
            PasswordValidationImpl()
        )

        validationsStrategies.forEach { it.execute(usuarioCreateCommand) }
    }

    fun updateValidation(usuarioUpdateCommand: UsuarioUpdateCommand){
        val validationsStrategies: List<AccountValidationEstrategy<UsuarioUpdateCommand>> = listOf(
            EmailUpdateValidationImpl(usuarioRepository),
            PasswordUpdateValidationImpl()
        )

        validationsStrategies.forEach { it.execute(usuarioUpdateCommand) }

    }
}