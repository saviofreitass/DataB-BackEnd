package com.example.data_cheque.application.usuario.strategy.Impl.createAccount

import com.example.data_cheque.application.usuario.UsuarioCreateCommand
import com.example.data_cheque.application.usuario.exception.EmailExistenteException
import com.example.data_cheque.application.usuario.exception.EmailInvalidoException
import com.example.data_cheque.application.usuario.strategy.AccountValidationEstrategy
import com.example.data_cheque.domain.usuario.UsuarioRepository

class EmailValidationImpl(
    val usuarioRepository: UsuarioRepository
)
    : AccountValidationEstrategy<UsuarioCreateCommand>{

    override fun execute(command: UsuarioCreateCommand){

        val  usuarioEncontrado = usuarioRepository.findByEmail(command.email)

        if(usuarioEncontrado!= null){
            throw EmailExistenteException(usuarioEncontrado.email)
        }

        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}(\\.br)?$")
        if(!emailRegex.matches(command.email)){
            throw EmailInvalidoException(command.email)
        }
    }
}