package com.example.data_cheque.application.funcionario

import com.example.data_cheque.application.pessoa.PessoaUpdateCommand
import com.example.data_cheque.application.pessoa.toPessoaAtualizada
import com.example.data_cheque.application.usuario.EncoderPassword
import com.example.data_cheque.application.usuario.UsuarioUpdateCommand
import com.example.data_cheque.application.usuario.toUsuarioAtualizado
import com.example.data_cheque.domain.funcionario.Funcionario
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toKotlinInstant
import java.util.*


data class FuncionarioUpdateCommand(
    val contador: String?,
    val usuario: UsuarioUpdateCommand?,
    val pessoa: PessoaUpdateCommand?,
    val cargo: String?,
    val setor: String?,
    val salario: Double?,
    val dataAdmissao: java.time.Instant?,
    val atualizadoEm: Instant = Clock.System.now(),
    val usuarioAtualizacao: String?
)


fun FuncionarioUpdateCommand.toFuncionarioAtualizado(
    funcionario: Funcionario,
    encoderPassword: EncoderPassword
): Funcionario {
    return funcionario.copy(
        contador = UUID.fromString(this.contador) ?: funcionario.contador,
        cargo = this.cargo ?: funcionario.cargo,
        setor = this.setor ?: funcionario.setor,
        salario = this.salario ?: funcionario.salario,
        dataAdmissao = this.dataAdmissao?.toKotlinInstant() ?: funcionario.dataAdmissao,
        usuario = this.usuario?.toUsuarioAtualizado(funcionario.usuario, encoderPassword) ?: funcionario.usuario
    )
}
