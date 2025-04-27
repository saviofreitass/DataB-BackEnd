import java.time.Instant
import java.time.LocalDateTime
import java.util.UUID

class Contador (
    val id: UUID,
    val usuarioId: UUID,
    val crc: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val criadoEm: LocalDateTime = LocalDateTime.now(),
    val atualizadoEm: Instant? = null,
    val status: Boolean? = null
    )