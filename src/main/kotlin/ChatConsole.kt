class ChatConsole: ChatConnectorObserver {
    /**
     * registers ChatConsole observer once called
     */
    fun registerChatConsole(){
        ChatHistory.registerObserver(this)
    }

    /**
     * newMessage called everytime when someone sends message
     * newMessage here just prints the message
     */
    override fun newMessage(chatMessage: ChatMessage) {
        println("${chatMessage.time} ${chatMessage.username}: ${chatMessage.message}")
    }


}