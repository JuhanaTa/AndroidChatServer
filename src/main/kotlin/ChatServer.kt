import java.lang.Exception
import java.net.ServerSocket

class ChatServer {
    fun serve(){

        try {
            val serverSocket = ServerSocket(30001, 3)

            var alreadyExecuted = false
            while (true){
                try {

                    println("We have port " + serverSocket.localPort)
                    val s = serverSocket.accept()  //

                    println("new connection " + s.inetAddress.hostAddress + " " + s.port)
                    val t = Thread(ChatConnector(s.getInputStream(), s.getOutputStream()))

                    if (alreadyExecuted == false){
                        ChatConsole().registerChatConsole()
                        TopChatter().registerTopChatter()
                        alreadyExecuted = true
                    }

                    t.start()

                }catch (e: Exception){
                    println("Exception $e")
                }
            }

        }
        catch (e: Exception){
            println(e)
        }

    }
}