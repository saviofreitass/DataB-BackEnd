package com.example.data_cheque.application.usuario.strategy.Impl.updateAccount

import com.example.data_cheque.application.usuario.UsuarioUpdateCommand
import com.example.data_cheque.application.usuario.exception.SenhaInvalida
import com.example.data_cheque.application.usuario.strategy.AccountValidationEstrategy

class PasswordUpdateValidationImpl: AccountValidationEstrategy<UsuarioUpdateCommand> {
    override fun execute(command: UsuarioUpdateCommand) {
        val senhaRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$\n")

        command.senha?.let {
            if (!senhaRegex.matches(it)) {
                throw SenhaInvalida()
            }
        }
    }
}