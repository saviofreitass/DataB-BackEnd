package com.example.data_cheque.application.funcionario

import com.example.data_cheque.application.funcionario.exception.FuncionarioNaoEncontradoException
import com.example.data_cheque.application.pessoa.exception.PessoaNaoEncontradaException
import com.example.data_cheque.application.usuario.exception.*
import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.funcionario.FuncionarioRepository
import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.pessoa.PessoaRepository
import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import com.example.data_cheque.domain.usuario.UsuarioRepository
import kotlinx.datetime.Clock
import org.springframework.stereotype.Service
import java.util.*

@Service
class FuncionarioService (
    private val funcionarioRepository: FuncionarioRepository,
    private val usuarioRepository: UsuarioRepository,
    private val pessoaRepository: PessoaRepository
) {
    fun findAll(): List<FuncionarioCommandResponse> {
        return funcionarioRepository.findAll()
    }
    fun findById(funcionarioId: UUID): FuncionarioCommandResponse {
        return funcionarioRepository.findById(funcionarioId) ?: throw FuncionarioNaoEncontradoException(funcionarioId)
    }

    fun insert(funcionario: FuncionarioCreateCommand): Funcionario {

        try {
            val usuarioId = UUID.randomUUID()
            val novoUsuario = Usuario(
                id = usuarioId,
                email = funcionario.email,
                senha = funcionario.senha,
                tipoUsuario = Role.ROLE_FUNCIONARIO,
                criadoEm = funcionario.criadoEm,
                usuarioCriacao = funcionario.usuarioCriacao,
                atualizadoEm = funcionario.atualizadoEm,
                usuarioAtualizacao = funcionario.usuarioAtualizacao
            )

            val pessoaId = UUID.randomUUID()
            val novaPessoa = Pessoa(
                id = pessoaId,
                usuarioId = novoUsuario.id,
                nome = funcionario.nome,
                cpfcnpj = funcionario.cpfcnpj,
                telefone = funcionario.telefone,
                ativo = true
            )

            validandoEmail(novoUsuario.email, novoUsuario.id)
            validarSenha(novoUsuario.senha)

            usuarioRepository.insert(novoUsuario)
            pessoaRepository.insert(novaPessoa)
            val novoFuncionario = funcionario.toFuncionario(novaPessoa.id, novoUsuario.id)
            funcionarioRepository.insert(novoFuncionario)

            return novoFuncionario
        }catch(e: Exception){
            throw e
        }
    }

    fun update(update: FuncionarioUpdateCommand, funcionarioId: UUID): FuncionarioCommandResponse {
        val funcionarioExistente = funcionarioRepository.findById(funcionarioId)
            ?: throw FuncionarioNaoEncontradoException(funcionarioId)

        val usuarioExistente = usuarioRepository.findById(funcionarioExistente.usuarioId)
            ?: throw UsuarioNaoEncontradoException(funcionarioExistente.usuarioId)

        val pessoaExistente = pessoaRepository.findById(funcionarioExistente.pessoaId)
            ?: throw PessoaNaoEncontradaException(funcionarioExistente.pessoaId)

        try {
            val usuarioAtualizado = usuarioExistente.copy(
                id = usuarioExistente.id,
                email = update.email ?: usuarioExistente.email,
                senha = update.senha ?: usuarioExistente.senha,
                tipoUsuario = usuarioExistente.tipoUsuario,
                criadoEm = usuarioExistente.criadoEm,
                usuarioCriacao = usuarioExistente.usuarioCriacao,
                atualizadoEm = Clock.System.now(),
                usuarioAtualizacao = update.usuarioAtualizacao ?: usuarioExistente.usuarioAtualizacao
            )

            val funcionarioAtualizado = funcionarioExistente.copy(
                cargo = update.cargo ?: funcionarioExistente.cargo,
                setor = update.setor ?: funcionarioExistente.setor,
                salario = update.salario ?: funcionarioExistente.salario,
                dataAdmissao = update.dataAdmissao ?: funcionarioExistente.dataAdmissao,
            )

            val pessoaAtualizada = pessoaExistente.copy(
                id = pessoaExistente.id,
                usuarioId = pessoaExistente.usuarioId,
                nome = update.nome ?: pessoaExistente.nome,
                cpfcnpj = update.cpfcnpj ?: pessoaExistente.cpfcnpj,
                telefone = update.telefone ?: pessoaExistente.telefone,
                ativo = update.ativo ?: pessoaExistente.ativo
            )

            validandoEmail(usuarioAtualizado.email, usuarioAtualizado.id)
            validarSenha(usuarioAtualizado.senha)

            usuarioRepository.update(update.toUsuario(usuarioAtualizado))
            pessoaRepository.update(update.toPessoa(pessoaAtualizada))
            funcionarioRepository.update(update.toFuncionario(funcionarioAtualizado))
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
        val regex = Regex("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$")

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