package com.example.data_cheque.adapters.jdbc.funcionario

import com.example.data_cheque.adapters.jdbc.funcionario.FuncionarioSQLExpressions.sqlDeleteFuncionarioById
import com.example.data_cheque.adapters.jdbc.funcionario.FuncionarioSQLExpressions.sqlInsertFuncionario
import com.example.data_cheque.adapters.jdbc.funcionario.FuncionarioSQLExpressions.sqlUpdateFuncionario
import com.example.data_cheque.adapters.jdbc.funcionario.FuncionarioSQLExpressions.sqlSelectAll
import com.example.data_cheque.adapters.jdbc.funcionario.FuncionarioSQLExpressions.sqlSelectById
import com.example.data_cheque.domain.funcionario.Funcionario
import com.example.data_cheque.domain.funcionario.FuncionarioRepository
import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.pessoa.PessoaRepository
import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import com.example.data_cheque.domain.usuario.UsuarioRepository
import kotlinx.datetime.toInstant
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDate
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import mu.KotlinLogging
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import java.util.*


@Repository
class FuncionarioJDBCRepository(
    private val db: NamedParameterJdbcOperations,
    private val usuarioRepository: UsuarioRepository,
    private val pessoaRepository: PessoaRepository
): FuncionarioRepository {
    private companion object{
        val LOGGER = KotlinLogging.logger { }
    }

    override fun findAll(): List<Funcionario> {
        val funcionario = try {
            db.query(sqlSelectAll(), rowMapper())
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao consultar os funcionarios: ${ex.message}" }
            throw ex
        }
        return funcionario
    }

    override fun findById(funcionarioId: UUID): Funcionario? {
        val funcionario = try {
            val params = MapSqlParameterSource("id", funcionarioId)
            db.query(sqlSelectById(), params, rowMapper()).firstOrNull()
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao consultar o funcionario: ${ex.message}" }
            throw ex
        }
        return funcionario
    }

    override fun insert(funcionario: Funcionario): Boolean {
        try {
            val params = parametros(funcionario)
            val linhasAfetadas = db.update(sqlInsertFuncionario(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao inserir a funcionario: ${ex.message}" }
            throw ex
        }
    }

    override fun update(funcionario: Funcionario): Boolean {
        try {
            val params = parametros(funcionario)
            val linhasAfetadas = db.update(sqlUpdateFuncionario(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao atualizar o funcionário: ${ex.message}" }
            throw ex
        }
    }

    override fun delete(funcionarioId: UUID): Boolean {
        try {
            val params = MapSqlParameterSource("id", funcionarioId)
            val linhasExcluidas = db.update(sqlDeleteFuncionarioById(), params)
            return linhasExcluidas == 1
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao excluir o funcionário: ${ex.message}" }
            throw ex
        }
    }

    private fun rowMapper() = RowMapper<Funcionario> { rs, _ ->
        val funcionarioId = UUID.fromString(rs.getString("id"))

        val usuario = Usuario(
            id = UUID.fromString(rs.getString("usuario_id")),
            email = rs.getString("email"),
            senha = "Sem senha para voce!",
            tipoUsuario = Role.valueOf(rs.getString("tipo_usuario")),
            usuarioCriacao = rs.getString("usuario_criacao"),
            criadoEm = rs.getTimestamp("criado_em").toInstant().toKotlinInstant(),
            atualizadoEm = rs.getTimestamp("atualizado_em")?.toInstant()?.toKotlinInstant(),
            usuarioAtualizacao = rs.getString("usuario_atualizacao")
        )

        val pessoa = Pessoa(
            id = UUID.fromString(rs.getString("pessoa_id")),
            nome = rs.getString("nome"),
            cpfcnpj = rs.getString("cpfcnpj"),
            telefone = rs.getString("telefone"),
            ativo = rs.getBoolean("ativo")
        )

        Funcionario(
            id = funcionarioId,
            usuario = usuario,
            pessoa = pessoa,
            cargo = rs.getString("cargo"),
            setor = rs.getString("setor"),
            salario = rs.getDouble("salario"),
            dataAdmissao = rs.getTimestamp("data_admissao")
        )
    }

//    private fun RowMapperResponse() = RowMapper<FuncionarioCommandResponse> { rs, _ ->
//        FuncionarioCommandResponse(
//            id = UUID.fromString(rs.getString("id")),
//            usuarioId = UUID.fromString(rs.getString("usuario_id")),
//            pessoaId = UUID.fromString(rs.getString("pessoa_id")),
//            dataAdmissao = rs.getTimestamp("data_admissao"),
//            salario = rs.getDouble("salario"),
//            cargo = rs.getString("cargo"),
//            setor = rs.getString("setor"),
//            nome = rs.getString("nome"),
//            cpfcnpj = rs.getString("cpfcnpj"),
//            telefone = rs.getString("telefone"),
//            ativo = rs.getBoolean("ativo"),
//            email = rs.getString("email"),
//            criadoEm = rs.getTimestamp("criado_em").toInstant().toKotlinInstant(),
//            usuarioCriacao = rs.getString("usuario_criacao"),
//            atualizadoEm = rs.getTimestamp("atualizado_em").toInstant().toKotlinInstant(),
//            usuarioAtualizacao = rs.getString("usuario_atualizacao")
//        )
//    }

    private fun parametros(funcionario: Funcionario): MapSqlParameterSource {
        val params = MapSqlParameterSource()
        params.addValue("id", funcionario.id)
        params.addValue("usuario_id", funcionario.usuario.id)
        params.addValue("pessoa_id", funcionario.pessoa.id)
        params.addValue("cargo", funcionario.cargo)
        params.addValue("setor", funcionario.setor)
        params.addValue("salario", funcionario.salario)
        params.addValue("data_admissao", funcionario.dataAdmissao)

        return params
    }


}