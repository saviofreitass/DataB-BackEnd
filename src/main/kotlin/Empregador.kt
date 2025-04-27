import  java.time.LocalDateTime
import java.time.Instant
import java.util.UUID

class Empregador (
    val uuid: UUID,
    val nome: String,
    val cnpj: String,
    val cpf: String,
    val contadorUuid: UUID,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val criadoEm: LocalDateTime = LocalDateTime.now(),
    val atualizadoEm: Instant? = null
)
