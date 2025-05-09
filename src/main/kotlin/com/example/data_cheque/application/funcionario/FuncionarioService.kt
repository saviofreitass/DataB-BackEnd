package com.example.data_cheque.application.funcionario

import com.example.data_cheque.application.funcionario.exception.FuncionarioNaoEncontradoException
import com.example.data_cheque.application.pessoa.PessoaCreateCommand
import com.example.data_cheque.application.pessoa.PessoaService
import com.example.data_cheque.application.usuario.EncoderPassword
import com.example.data_cheque.application.usuario.UsuarioCreateCommand
import com.example.data_cheque.application.usuario.UsuarioService
import com.example.data_cheque.application.usuario.exception.*
import com.example.data_cheque.application.usuario.toUsuario
import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.funcionario.FuncionarioRepository
import com.example.data_cheque.domain.pessoa.PessoaRepository
import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.UsuarioRepository
import kotlinx.datetime.Clock
import org.springframework.stereotype.Service
import java.util.*

@Service
class FuncionarioService (
    private val funcionarioRepository: FuncionarioRepository,
    private val usuarioRepository: UsuarioRepository,
    private val pessoaRepository: PessoaRepository,
    private val encoderPassword: EncoderPassword
) {
    fun findAll(): List<FuncionarioCommandResponse> {
        return funcionarioRepository.findAll()
    }
    fun findById(funcionarioId: UUID): FuncionarioCommandResponse {
        return funcionarioRepository.findById(funcionarioId) ?: throw FuncionarioNaoEncontradoException(funcionarioId)
    }

    fun insert(funcionario: FuncionarioCreateCommand): Funcionario {

        try {
            val usuarioCreateCommand = UsuarioCreateCommand(
                email = funcionario.email,
                senha = funcionario.senha,
                tipoUsuario = Role.ROLE_FUNCIONARIO,
                criadoEm = funcionario.criadoEm,
                usuarioCriacao = funcionario.usuarioCriacao,
                atualizadoEm = funcionario.atualizadoEm,
                usuarioAtualizacao = funcionario.usuarioAtualizacao
            )

            val novoUsuario = usuarioRepository.insert(usuarioCreateCommand.toUsuario(encoderPassword))

            val pessoaCreateCommand = PessoaCreateCommand(
                usuarioId = novoUsuario.id,
                nome = funcionario.nome,
                cpfcnpj = funcionario.cpfcnpj,
                telefone = funcionario.telefone,
                ativo = true
            )

            val novaPessoa = pessoaService.insert(pessoaCreateCommand)

            val novoFuncionario = funcionario.toFuncionario(novaPessoa.id, novoUsuario.id)
            funcionarioRepository.insert(novoFuncionario)

            return novoFuncionario
        }catch(e: Exception){
            throw e
        }
    }

    fun update(funcionarioUpdateCommand: FuncionarioUpdateCommand, funcionarioId: UUID): FuncionarioCommandResponse {
        val funcionarioExistente = funcionarioRepository.findById(funcionarioId)
            ?: throw FuncionarioNaoEncontradoException(funcionarioId)

        val usuarioExistente = usuarioService.findById(funcionarioExistente.usuarioId)

        val pessoaExistente = pessoaService.findById(funcionarioExistente.pessoaId)

        try {
            val usuarioAtualizado = usuarioExistente.copy(
                id = usuarioExistente.id,
                email = funcionarioUpdateCommand.email ?: usuarioExistente.email,
                senha = funcionarioUpdateCommand.senha ?: usuarioExistente.senha,
                tipoUsuario = usuarioExistente.tipoUsuario,
                criadoEm = usuarioExistente.criadoEm,
                usuarioCriacao = usuarioExistente.usuarioCriacao,
                atualizadoEm = Clock.System.now(),
                usuarioAtualizacao = funcionarioUpdateCommand.usuarioAtualizacao ?: usuarioExistente.usuarioAtualizacao
            )

            val pessoaAtualizada = pessoaExistente.copy(
                id = pessoaExistente.id,
                usuarioId = pessoaExistente.usuarioId,
                nome = funcionarioUpdateCommand.nome ?: pessoaExistente.nome,
                cpfcnpj = funcionarioUpdateCommand.cpfcnpj ?: pessoaExistente.cpfcnpj,
                telefone = funcionarioUpdateCommand.telefone ?: pessoaExistente.telefone,
                ativo = funcionarioUpdateCommand.ativo ?: pessoaExistente.ativo
            )

            val funcionarioAtualizado = funcionarioExistente.copy(
                cargo = funcionarioUpdateCommand.cargo ?: funcionarioExistente.cargo,
                setor = funcionarioUpdateCommand.setor ?: funcionarioExistente.setor,
                salario = funcionarioUpdateCommand.salario ?: funcionarioExistente.salario,
                dataAdmissao = funcionarioUpdateCommand.dataAdmissao ?: funcionarioExistente.dataAdmissao,
            )

            usuarioService.update(usuarioAtualizado)
            pessoaService.funcionarioUpdateCommand(funcionarioUpdateCommand.toPessoa(pessoaAtualizada))
            funcionarioRepository.funcionarioUpdateCommand(funcionarioUpdateCommand.toFuncionario(funcionarioAtualizado))
            return findById(funcionarioId)
        }catch (e: Exception){
            throw e
        }

    }

    fun delete(funcionarioId: UUID){
        funcionarioRepository.findById(funcionarioId = funcionarioId) ?: throw FuncionarioNaoEncontradoException(funcionarioId)
        funcionarioRepository.delete(funcionarioId)
    }

    fun validandoEmail(email: String, id: UUID){
        val regex = Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$")

        val usuario = usuarioRepository.findByEmail(email)

        if(usuario != null && id != usuario.id){
            throw EmailDuplicadoException(email)
        }

        if (!regex.matches(email)) {
            throw EmailInvalidoException(email)
        }
    }

    private fun validarSenha(senha: String) {
        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")

        if (!regex.matches(senha)) {
            throw SenhaInvalidaException("Senha inválida")
        }
    }

}