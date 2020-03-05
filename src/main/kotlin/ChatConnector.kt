import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class  ChatConnector(val stream: InputStream, val out: OutputStream) : ChatConnectorObserver, Runnable {

    private val printer = PrintStream(out, true)

    private val scanner = Scanner(stream)

    private val commands = mutableListOf<String>("/close", "/userlist", "/messagehistory", "/score")


    override fun run(){


        newMessage(ChatMessage("Hello, Insert your username. (Min length 1 and max length 20)", "", "", ""))
        //printer.println("Hello, Insert your username. (Min length 1 and max length 20)")
        var input = scanner.nextLine()

        var clientChatMessage = Json.parse(ChatMessage.serializer(), input)
        println("testpoint 1")
        /**
         *  checks if given username already exists.
         *  function returns false if name exists
         */
        while (!Users.addUser(clientChatMessage.message) || clientChatMessage.message.isEmpty()){
            newMessage(ChatMessage("Name already in use or invalid name", "", "", ""))
            input = scanner.nextLine()
            clientChatMessage = Json.parse(ChatMessage.serializer(), input)
            println("testpoint 2")

        }

        val username = clientChatMessage.message
        println("testpoint 3")
        ChatHistory.registerObserver(this)

        newMessage(ChatMessage("Go ahead and start chatting!!!", "", "", ""))
        println("testpoint 4")

        loop@ while (true){
            input = scanner.nextLine()
            clientChatMessage = Json.parse(ChatMessage.serializer(), input)
            println("testpoint 5")
            if (commands.contains(clientChatMessage.command)){
                when(clientChatMessage.command) {
                    "/close" ->{
                        close(username)
                        break@loop
                    }
                    "/userlist" -> newMessage(ChatMessage(Users.toString(), "", "", ""))
                    "/messagehistory" -> newMessage(ChatMessage(ChatHistory.toString(),"", "", ""))
                    "/score" -> newMessage(ChatMessage("Hall of Fame: ${Users.getUserPoints()}", "", "", ""))


                }
                println("testpoint 6")
            }
            else if (clientChatMessage.message == ""){
                newMessage(ChatMessage("","","",""))
                println("testpoint 7")
            }
            else{
                val currentDateTime = LocalDateTime.now()
                Users.addPoints(username)
                ChatHistory.insert(ChatMessage(clientChatMessage.message, username, currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm")).toString(), ""))
                println("testpoint 8")
            }

        }

    }

    /**
     * Function which prints message.
     * Same function is called at the same time in different threads
     */
    override fun newMessage(chatMessage: ChatMessage) {
        //printer.println("${chatMessage.time} ${chatMessage.username}: ${chatMessage.message}  ") //prints time username and the message itself

        Json.stringify(ChatMessage.serializer(), chatMessage)
        printer.println(Json.stringify(ChatMessage.serializer(), chatMessage))
    }

    /**
     * Checks first letter of input and returns true if first item is "/"
     */
    private fun checkCommand(input: String): Boolean{

        var firstItem = input.substring(0,1)

        return firstItem == "/"
    }

    /**
     * function for closing connection
     *
     */
    private fun close(username: String){

        ChatHistory.deregisterObserver(this)  //removes client from observers
        Users.removeUser(username) //removes user from userlist
        println("$username ended chatting")
        scanner.close() //closes the scanner e.g Putty
    }

}