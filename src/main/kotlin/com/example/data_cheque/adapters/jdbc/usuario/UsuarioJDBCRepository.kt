package com.example.data_cheque.adapters.jdbc.usuario

import com.example.data_cheque.adapters.http.ecrypt.PasswordBcryptEncoder
import com.example.data_cheque.adapters.jdbc.usuario.UsuarioSQLExpressions.sqlDeleteUsuarioById
import com.example.data_cheque.adapters.jdbc.usuario.UsuarioSQLExpressions.sqlInsertUsuario
import com.example.data_cheque.adapters.jdbc.usuario.UsuarioSQLExpressions.sqlSelectAll
import com.example.data_cheque.adapters.jdbc.usuario.UsuarioSQLExpressions.sqlSelectByEmail
import com.example.data_cheque.adapters.jdbc.usuario.UsuarioSQLExpressions.sqlSelectById
import com.example.data_cheque.adapters.jdbc.usuario.UsuarioSQLExpressions.sqlUpdateUsuario
import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import com.example.data_cheque.domain.usuario.UsuarioRepository
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import org.springframework.jdbc.core.RowMapper
import mu.KotlinLogging
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import java.sql.Timestamp
import java.util.*


@Repository
class UsuarioJDBCRepository (
    private val db: NamedParameterJdbcOperations,
    private val passwordBcryptEncoder: PasswordBcryptEncoder
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

    override fun findByEmail(email: String): Usuario? {

        val user = try {
            val params = MapSqlParameterSource("email", email)
            db.query(sqlSelectByEmail(), params, rowMapper()).firstOrNull()
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao consultar o usuario: ${ex.message}" }
            throw ex
        }
        return user
    }


    override fun insert(usuario: Usuario): Boolean {
        try {
            val params = parametros(usuario)
            val linhasAfetadas = db.update(sqlInsertUsuario(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao inserir o usuario: ${ex.message}" }
            throw ex
        }
    }


    override fun update(usuario: Usuario): Boolean {
        try {
            val params = parametros(usuario)
            val linhasAfetadas = db.update(sqlUpdateUsuario(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao atualizar o usuario: ${ex.message}" }
            throw ex
        }
    }

    override fun delete(usuarioId: UUID): Boolean {
        try {
            val params = MapSqlParameterSource("id", usuarioId)
            val linhasExcluidas = db.update(sqlDeleteUsuarioById(), params)
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
            criadoEm = rs.getTimestamp("criado_em").toInstant().toKotlinInstant(),
            usuarioAtualizacao = rs.getString("usuario_atualizacao"),
            atualizadoEm = rs.getTimestamp("atualizado_em").toInstant().toKotlinInstant(),
            usuarioCriacao = rs.getString("usuario_update")
        )
    }

    private fun parametros(usuario: Usuario): MapSqlParameterSource {
        val params = MapSqlParameterSource()

        params.addValue("id", usuario.id)

        params.addValue("email", usuario.email)

        val senhaHasheada = passwordBcryptEncoder.encode(usuario.senha)
        params.addValue("senha_hash", senhaHasheada)

        params.addValue("tipo_usuario", usuario.tipoUsuario.toString())

        params.addValue("criado_em", Timestamp.from(usuario.criadoEm.toJavaInstant()))

        params.addValue("atualizado_em", Timestamp.from(usuario.atualizadoEm?.toJavaInstant()))

        params.addValue("usuario_atualizacao", usuario.usuarioAtualizacao)

        params.addValue("usuario_criacao", usuario.usuarioCriacao)

        return params
    }
}
