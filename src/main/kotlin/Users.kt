object Users {

    //val users = hashSetOf<String>()

    val userScore = linkedMapOf<String,Int>() //stores name of user and his/her points (amount of messages sent)
    var oldTopList = ""
    /**
     * name is added to list and default amount of points is set to 0
     */
    fun addUser(name: String): Boolean{

        if (!userScore.containsKey(name)&& checkUsername(name)){

            userScore.put(name, 0)
            println("user called $name added")
            return true
        }
        else{
            return false
        }
    }

    /**
     * searches for empty spaces in front and in the end of name -> removes them
     * checks that name is 1 to 20 long
     */
    fun checkUsername(name: String): Boolean{
        var tempName = name.trim()

        return !(tempName.length < 1 || tempName.length > 20)
    }

    fun  removeUser(name: String){
        userScore.remove(name)
    }

    /**
     * returns only the name of users
     * iterates and ads names to string
     */
    override fun toString(): String {

        var userString = ""
        for (i in userScore.keys){
            userString += "${i} ${System.lineSeparator()}"
        }
        return userString
    }

    /**
     * inserts user into userScore list with updated amount of points
     */

    fun  addPoints(name: String){
        var score: Int? = 0
        score = userScore.get(name) //gets the old score from userScore map
        if (score != null){
            score++
            userScore.put(name, score) //inputs updated user into userScore map

        }

    }

    /**
     * sorts chatters by their points and then returns String that has 3 chatters with the highest point count
     */
    fun getUserPoints(): String{
        var userPoints = ""
        val userList = userScore.toList().sortedBy { (_, value) -> value }.reversed().toMap() //sorts map by value and then stores it to userList

        var limiter = 0

        for (i in userList){

            if (limiter<3) {
                userPoints += "${i.key} has ${i.value} point(s). "
                limiter++
            }
            if (limiter == 3){
                break    //after 3 times names and values are inserted to String and for loop breaks
            }

        }
        oldTopList = userPoints

        return userPoints //returns 3 names and scores in String
    }
    fun oldTopList():String{
        return oldTopList
    }


}