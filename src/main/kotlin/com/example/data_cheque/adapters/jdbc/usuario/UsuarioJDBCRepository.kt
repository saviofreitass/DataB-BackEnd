package com.example.data_cheque.adapters.jdbc.usuario

import com.example.data_cheque.adapters.jdbc.usuario.UsuarioSQLExpressions.sqlDeleteFuncionarioById
import com.example.data_cheque.adapters.jdbc.usuario.UsuarioSQLExpressions.sqlInsertFuncionario
import com.example.data_cheque.adapters.jdbc.usuario.UsuarioSQLExpressions.sqlSelectAll
import com.example.data_cheque.adapters.jdbc.usuario.UsuarioSQLExpressions.sqlSelectById
import com.example.data_cheque.adapters.jdbc.usuario.UsuarioSQLExpressions.sqlUpdateFuncionario
import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import com.example.data_cheque.domain.usuario.UsuarioRepository
import kotlinx.datetime.toKotlinInstant
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.RowMapper
import mu.KotlinLogging
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import java.util.*


@Repository
class UsuarioJDBCRepository (
    private val db: NamedParameterJdbcOperations
): UsuarioRepository {
    private companion object {
        val LOGGER = KotlinLogging.logger { }
    }

    override fun findAll(): List<Usuario> {
        val usuario = try {
            db.query(sqlSelectAll(), rowMapper())
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao consultar o funcionário: ${ex.message}" }
            throw ex
        }
        return usuario
    }

    override fun findById(usuarioId: UUID): Usuario? {

        val usuario = try {
            val params = MapSqlParameterSource("id", usuarioId)
            db.query(sqlSelectById(), params, rowMapper()).firstOrNull()
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao consultar o funcionário: ${ex.message}" }
            throw ex
        }
        return usuario
    }


    override fun insert(usuario: Usuario): Boolean {
        try {
            val params = parametros(usuario)
            val linhasAfetadas = db.update(sqlInsertFuncionario(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao inserir o usuario: ${ex.message}" }
            throw ex
        }
    }


    override fun update(usuario: Usuario): Boolean {
        try {
            val params = parametros(usuario)
            val linhasAfetadas = db.update(sqlUpdateFuncionario(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao atualizar o usuario: ${ex.message}" }
            throw ex
        }
    }

    override fun delete(usuarioId: UUID): Boolean {
        try {
            val params = MapSqlParameterSource("id", usuarioId)
            val linhasExcluidas = db.update(sqlDeleteFuncionarioById(), params)
            return linhasExcluidas == 1
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao excluir o usuario: ${ex.message}" }
            throw ex
        }
    }

    private fun rowMapper() = RowMapper<Usuario> { rs, _ ->
        val usuarioId = UUID.fromString(rs.getString("usuario_id"))

        Usuario(
            id = usuarioId,
            email = rs.getString("email"),
            senha = rs.getString("senha"),
            tipoUsuario = Role.valueOf(rs.getString("tipo_usuario")),
            nome = rs.getString("nome"),
            cpfcnpj = rs.getString("cpfcnpj"),
            telefone = rs.getString("telefone"),
            ativo = rs.getBoolean("ativo"),
            criadoEm = rs.getTimestamp("criado_em").toInstant().toKotlinInstant(),
            usuarioAtualizacao = rs.getString("usuario_atualizacao"),
            atualizadoEm = if(rs.getTimestamp("atualizado_em") == null){
                null
            }else rs.getTimestamp("atualizado_em").toInstant().toKotlinInstant(),
            usuarioCriacao = rs.getString("usuario_update")
        )
    }

    private fun parametros(usuario: Usuario): MapSqlParameterSource {
        val params = MapSqlParameterSource()
        params.addValue("id", usuario.id)
        params.addValue("email", usuario.email)
        params.addValue("senha", usuario.senha)
        params.addValue("tipoUsuario", usuario.tipoUsuario)
        params.addValue("nome", usuario.nome)
        params.addValue("cpfcnpj", usuario.cpfcnpj)
        params.addValue("ativo", usuario.ativo)
        params.addValue("criado_em", usuario.criadoEm)
        params.addValue("atualizadoEm", usuario.atualizadoEm)
        params.addValue("usuario_atualizacao", usuario.usuarioAtualizacao)
        params.addValue("usuario_criacao", usuario.usuarioCriacao)
        return params
    }
}
