class TopChatter: ChatConnectorObserver {
    /**
     * registers top chatter observer once called
     */
    fun registerTopChatter(){
        ChatHistory.registerObserver(this)
    }

    /**
     * Prints user name and the amount of messages he/she has sent everytime the top 3 changes.
     */
    override fun newMessage(message: ChatMessage) {

        val oldTopChat = Users.oldTopList()  //gets the old string
        val textToPrint = Users.getUserPoints() //gets the new string

        if (textToPrint != oldTopChat){
            println("Hall of Fame: $textToPrint") //prints new string if it has changed
        }


    }

}