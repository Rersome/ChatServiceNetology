package Services

import Exceptions.ChatNotFoundException
import Exceptions.MessageNotFoundException
import Exceptions.UserNotFoundException

data class User(
    val id: Int
)

data class Message(
    val id: Int,
    var text: String = "test",
    var isMessageRead: Boolean = false,
    val ownerOfMessage: User
    )

data class Chat(
    val id: Int,
    var isChatRead: Boolean = false,
    var users: MutableList<User> = mutableListOf(),
    var messages: MutableList<Message> = mutableListOf()
)

object ChatService {
    private var chats = mutableListOf<Chat>()
    private var users = mutableListOf<User>()
    //private var messages = mutableListOf<Message>()
    private var nextIdUser = 1
    private var nextIdChat = 1
    private var nextIdMessage = 1

    /*-------------------------------------------------------------*/

    fun createUser(): User {
        val user = User(nextIdUser++)
        users.add(user)
        return user
    }

    fun getUserById(id: Int): User? {
        return users.find { it.id == id }
    }

    /*-------------------------------------------------------------*/

//    private fun createChat(): Chat {
//        val chat = Chat(nextIdChat++)
//        chats.add(chat)
//        //chat.users.add(createUser(user1))
//        //chat.users.add(createUser(user2))
//        //chat.messages.add(message)
//        return chat
//    }

    fun getChatById(id: Int): Chat? {
        return chats.find { it.id == id }
    }

    fun addUserToChat(idOfChat: Int, idOfUser: Int): Boolean {
        val chat = getChatById(idOfChat) ?: throw ChatNotFoundException("Чат небыл найден!")
        val user = getUserById(idOfUser) ?: throw UserNotFoundException("Пользователь небыл найден!")
        chat.users.add(user)
        return true
    }

    fun deleteChat(id: Int): Boolean {
        val chat = getChatById(id) ?: throw ChatNotFoundException("Чат небыл найден!")
        chats.remove(chat)
        return true
    }

    fun getChats(): List<Chat> {
        return chats
    }

    /*-------------------------------------------------------------*/


    fun createMessage(/*chatId: Int,*/ user1: User, user2: User, messageText: String): Chat {

//        val chat = getChatById(chatId) ?: throw ChatNotFoundException("Чат небыл найден!")
//        if (chat.users.size < 2) {
//            throw NotEnoughUsersException("В чате недостаточно юзеров")
//        }
//        val chat = createChat()
//        chat.users.add(user1)
//        chat.users.add(user2)
//        val message = Message(nextIdMessage++, ownerOfMessage = user1)
//        chat.messages.add(message)
//        return chat
        val existingChat = chats.find { chat -> chat.users.contains(user1)
                && chat.users.contains(user2) }
        return if (existingChat != null) {
            val message = Message(nextIdMessage++, messageText, ownerOfMessage = user1)
            existingChat.messages.add(message)
            existingChat
        } else {
            val chat = Chat(nextIdChat++)
            chat.users.add(user1)
            chat.users.add(user2)
            val message = Message(nextIdMessage++, messageText, ownerOfMessage = user1)
            chat.messages.add(message)
            chats.add(chat)
            chat
        }
    }

    fun editMessage(chatId: Int, messageId: Int, editedText: String): Message {
        val chat = getChatById(chatId) ?: throw ChatNotFoundException("Чат небыл найден!")
        val message = chat.messages.find { it.id == messageId } ?: throw MessageNotFoundException("Сообщение небыло найдено!")
        message.text = editedText
        return message
    }

    fun deleteMessage(chatId: Int, messageId: Int): Boolean {
        val chat = getChatById(chatId) ?: throw ChatNotFoundException("Чат небыл найден!")
        val message = chat.messages.find { it.id == messageId } ?: throw MessageNotFoundException("Сообщение небыло найдено!")
        chat.messages.remove(message)
        return true
    }

    fun getMessagesByIdOfUser(chatId: Int, numberOfMessages: Int, idOfUser: Int): List<Message> {
        val chat = getChatById(chatId) ?: throw ChatNotFoundException("Чат небыл найден!")
        chat.messages.forEach { it.isMessageRead = true }
        return chat.messages.filter { it.ownerOfMessage.id == idOfUser } .take(numberOfMessages)
    }

    fun clear() {
        users = mutableListOf<User>()
        //messages = mutableListOf<Message>()
        chats = mutableListOf<Chat>()
        nextIdUser = 1
        nextIdChat = 1
        nextIdMessage = 1
    }
}