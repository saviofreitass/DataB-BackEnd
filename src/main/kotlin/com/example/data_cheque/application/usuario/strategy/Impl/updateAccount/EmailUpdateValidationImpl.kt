package com.example.data_cheque.application.usuario.strategy.Impl.updateAccount

import com.example.data_cheque.application.usuario.UsuarioUpdateCommand
import com.example.data_cheque.application.usuario.exception.EmailExistenteException
import com.example.data_cheque.application.usuario.exception.EmailInvalidoException
import com.example.data_cheque.application.usuario.strategy.AccountValidationEstrategy
import com.example.data_cheque.domain.usuario.UsuarioRepository

class EmailUpdateValidationImpl(
    val usuarioRepository: UsuarioRepository
)
    : AccountValidationEstrategy<UsuarioUpdateCommand>{

    override fun execute(command: UsuarioUpdateCommand){

        val  usuarioEncontrado = command.email?.let { usuarioRepository.findByEmail(it) }

        if(usuarioEncontrado != null && command.id != usuarioEncontrado.id){
            throw EmailExistenteException(usuarioEncontrado.email)
        }

        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}(\\.br)?$")
        command.email?.let {
            if (!emailRegex.matches(it)) {
                throw EmailInvalidoException(it)
            }
        } ?: throw EmailInvalidoException(null)

    }
}