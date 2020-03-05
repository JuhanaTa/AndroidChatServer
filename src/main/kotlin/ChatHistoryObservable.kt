interface ChatHistoryObservable {



    fun registerObserver(observer:ChatConnectorObserver)



    fun deregisterObserver(observer:ChatConnectorObserver)



    fun notifyObservers (message:ChatMessage)

}