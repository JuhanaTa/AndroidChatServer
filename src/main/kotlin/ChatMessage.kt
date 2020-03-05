import kotlinx.serialization.Serializable

@Serializable
class ChatMessage(
    val message: String,
    val username: String,
    val time: String,
    val command: String
) {}