package com.example.data_cheque.application.funcionario

import com.example.data_cheque.application.pessoa.PessoaCreateCommand
import com.example.data_cheque.application.pessoa.PessoaUpdateCommand
import com.example.data_cheque.application.pessoa.toPessoaAtualizada
import com.example.data_cheque.application.usuario.EncoderPassword
import com.example.data_cheque.application.usuario.UsuarioUpdateCommand
import com.example.data_cheque.application.usuario.toUsuarioAtualizado
import com.example.data_cheque.domain.funcionario.Funcionario
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.sql.Timestamp
import java.util.*

data class FuncionarioUpdateCommand(
    val usuario: UsuarioUpdateCommand?,
    val pessoa: PessoaUpdateCommand?,
    val cargo: String?,
    val setor: String?,
    val salario: Double?,
    val dataAdmissao: Timestamp?,
    val atualizadoEm: Instant = Clock.System.now(),
    val usuarioAtualizacao: String?
)


fun FuncionarioUpdateCommand.toFuncionarioAtualizado(
    funcionario: Funcionario,
    encoderPassword: EncoderPassword
): Funcionario {
    return funcionario.copy(
        cargo = this.cargo ?: funcionario.cargo,
        setor = this.setor ?: funcionario.setor,
        salario = this.salario ?: funcionario.salario,
        dataAdmissao = this.dataAdmissao ?: funcionario.dataAdmissao,
        // opcional: atualiza auditoria se desejar
        pessoa = this.pessoa?.toPessoaAtualizada(funcionario.pessoa) ?: funcionario.pessoa,
        usuario = this.usuario?.toUsuarioAtualizado(funcionario.usuario, encoderPassword) ?: funcionario.usuario
    )
}
