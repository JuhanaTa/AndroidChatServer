object ChatHistory: ChatHistoryObservable {

    var chatHistoryList = mutableListOf<ChatMessage>()  //List of every messages
    var observers:MutableList<ChatConnectorObserver> = mutableListOf()  //List of every observers

    /**
     * Stores message that is type of ChatMessage into a chatHistorylist
     * Also calls notifyObservers function and passes the message with it
     */
    fun insert(message: ChatMessage) {

        chatHistoryList.add(message)
        notifyObservers(message)
    }

    /**
     * Adding observers to list
     */
    override fun registerObserver(observer: ChatConnectorObserver) {
        observers.add(observer)

    }
    /**
     * Removing observers from list
     */
    override fun deregisterObserver(observer: ChatConnectorObserver) {
        observers.remove(observer)

    }

    /**
     * iterates every observers in observers list and calls newMessage function for each of them
     */
    override fun notifyObservers(message: ChatMessage) {
        for (i in observers){
            i.newMessage(message)

        }
    }

    /**
     * stores content of history list to String and returns the String
     */
    override fun toString(): String {
        var history: String = ""

        for (i in chatHistoryList){
            history += "${i.time.toString()} ${i.username.toString()}: ${i.message.toString()} ${System.lineSeparator()}" //System.lineSeparator adds line break inside String

        }
        return history
    }

}