package com.example.data_cheque.adapters.jdbc.contador

import com.example.data_cheque.adapters.jdbc.contador.ContadorSQLExpressions.sqlDeleteContador
import com.example.data_cheque.adapters.jdbc.contador.ContadorSQLExpressions.sqlInsertContador
import com.example.data_cheque.adapters.jdbc.contador.ContadorSQLExpressions.sqlSelectAll
import com.example.data_cheque.adapters.jdbc.contador.ContadorSQLExpressions.sqlSelectById
import com.example.data_cheque.adapters.jdbc.contador.ContadorSQLExpressions.sqlSelectByUserId
import com.example.data_cheque.adapters.jdbc.contador.ContadorSQLExpressions.sqlUpdateContador
import com.example.data_cheque.domain.contador.Contador
import com.example.data_cheque.domain.contador.ContadorRepository
import com.example.data_cheque.domain.pessoa.Pessoa
import com.example.data_cheque.domain.usuario.Role
import com.example.data_cheque.domain.usuario.Usuario
import kotlinx.datetime.toKotlinInstant
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import org.springframework.stereotype.Repository
import mu.KotlinLogging
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import java.util.*


@Repository
class ContadorJDBCRepository(
    private val db: NamedParameterJdbcOperations,
): ContadorRepository {
    private companion object{
        val LOGGER = KotlinLogging.logger { }
    }

    override fun findAll(): List<Contador> {
        val contador = try {
            db.query(sqlSelectAll(), rowMapper())
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao consultar os contadores: ${ex.message}" }
            throw ex
        }

        return contador
    }

    override fun findById(contadorId: UUID): Contador? {
        val contador = try {
            val params = MapSqlParameterSource("id", contadorId)
            db.query(sqlSelectById(), params, rowMapper()).firstOrNull()
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao consultar os contadores: ${ex.message}" }
            throw ex
        }

        return contador
    }

    override fun findByUserId(usuarioId: UUID): Contador? {
        val contador = try {
            val params = MapSqlParameterSource("usuario_id", usuarioId)
            db.query(sqlSelectByUserId(), params, rowMapper()).firstOrNull()
        } catch (ex: Exception) {
            LOGGER.error { "Houve um erro ao consultar os contadores: ${ex.message}" }
            throw ex
        }

        return contador
    }

    override fun insert(contador: Contador): Boolean {
        try {
            val params = parametros(contador)
            val linhasAfetadas = db.update(sqlInsertContador(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao inserir o contador: ${ex.message}" }
            throw ex
        }
    }

    override fun update(contador: Contador): Boolean {
        try {
            val params = parametros(contador)
            val linhasAfetadas = db.update(sqlUpdateContador(), params)
            return linhasAfetadas > 0
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao atualizar o contador: ${ex.message}" }
            throw ex
        }
    }

    override fun delete(contadorId: UUID): Boolean {
        try {
            val params = MapSqlParameterSource("id", contadorId)
            val linhasAfetadas = db.update(sqlDeleteContador(), params)
            return linhasAfetadas == 1
        }catch (ex: Exception){
            LOGGER.error { "Houve um erro ao deletar o contador: ${ex.message}" }
            throw ex
        }
    }

    private fun rowMapper() = RowMapper<Contador> { rs, _ ->
        val contadorId = UUID.fromString(rs.getString("id"))

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

        Contador(
            id = contadorId,
            usuario = usuario,
            pessoa = pessoa,
            crc = rs.getString("crc")
        )
    }

    private fun parametros(contador: Contador): MapSqlParameterSource {
        val params = MapSqlParameterSource()
        params.addValue("id", contador.id)
        params.addValue("usuario_id", contador.usuario.id)
        params.addValue("pessoa_id", contador.pessoa.id)
        params.addValue("crc", contador.crc)

        return params
    }

}