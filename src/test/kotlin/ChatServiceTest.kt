import Exceptions.ChatNotFoundException
import Exceptions.MessageNotFoundException
import Exceptions.NotEnoughUsersException
import Exceptions.UserNotFoundException
import Services.Chat
import Services.ChatService
import Services.ChatService.addUserToChat
import Services.ChatService.createUser
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class NoteWallServiceTest {
    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test
    fun testAddUserPositive(){
        val user = ChatService.createUser()

        assertEquals(1, user.id)
    }

    @Test
    fun testAddUserNegative(){
        val user = ChatService.createUser()

        assertNotEquals(2, user.id)
    }

    @Test
    fun testGetUserByIdPositive(){
        val user = ChatService.createUser()

        assertEquals(user, ChatService.getUserById(user.id))
    }

    @Test
    fun testGetUserByIdNegative(){
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()

        assertNotEquals(user1, ChatService.getUserById(user2.id))
    }

    @Test
    fun testAddChatPositive(){
        val chat = ChatService.createChat()

        assertEquals(1, chat.id)
    }

    @Test
    fun testAddChatNegative(){
        val chat = ChatService.createChat()

        assertNotEquals(2, chat.id)
    }

    @Test
    fun testGetChatByIdPositive(){
        val chat = ChatService.createChat()

        assertEquals(chat, ChatService.getChatById(chat.id))
    }

    @Test
    fun testGetChatByIdNegative(){
        val chat1 = ChatService.createChat()
        val chat2 = ChatService.createChat()

        assertNotEquals(chat1, ChatService.getChatById(chat2.id))
    }

    @Test(expected = ChatNotFoundException::class)
    fun testAddUserToChatByExceptionChat(){
        val user = ChatService.createUser()

        ChatService.addUserToChat(2, user.id)
    }

    @Test(expected = UserNotFoundException::class)
    fun testAddUserToChatByExceptionUser(){
        val chat = ChatService.createChat()

        ChatService.addUserToChat(chat.id, 2)
    }

    @Test
    fun testAddUserToChat(){
        val chat = ChatService.createChat()
        val user = ChatService.createUser()

        assertEquals(true, ChatService.addUserToChat(chat.id, user.id))
    }

    @Test(expected = ChatNotFoundException::class)
    fun testDeleteChatByExceptionChat(){
        val chat = ChatService.createChat()

        ChatService.deleteChat(3)
    }

    @Test
    fun testDeleteChat(){
        val chat = ChatService.createChat()

        assertEquals(true, ChatService.deleteChat(chat.id))
    }

    @Test
    fun testGetChatsPositive(){
        ChatService.createChat()


        assertEquals(1, ChatService.getChats().size)
    }

    @Test
    fun testGetChatsNegative(){
        ChatService.createChat()


        assertNotEquals(2, ChatService.getChats().size)
    }

    @Test(expected = ChatNotFoundException::class)
    fun testCreateMessageByExceptionChat(){
        val user = ChatService.createUser()

        ChatService.createMessage(3, user)
    }

    @Test(expected = NotEnoughUsersException::class)
    fun testCreateMessageByExceptionNotEnoughUsers(){
        val chat = ChatService.createChat()
        val user = ChatService.createUser()

        ChatService.createMessage(chat.id, user)
    }

    @Test
    fun testCreateMessagePositive(){
        val chat = ChatService.createChat()
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        ChatService.addUserToChat(chat.id, user1.id)
        ChatService.addUserToChat(chat.id, user2.id)
        ChatService.createMessage(chat.id, user1)
        val message = ChatService.createMessage(chat.id, user1)

        assertEquals("test", message.text)
    }

    @Test(expected = ChatNotFoundException::class)
    fun testEditMessageByExceptionChatNotFound(){
        val chat = ChatService.createChat()
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        ChatService.addUserToChat(chat.id, user1.id)
        ChatService.addUserToChat(chat.id, user2.id)
        val editedText = "353"
        val message = ChatService.createMessage(chat.id, user1)

        ChatService.editMessage(3, message.id, editedText)
    }

    @Test(expected = MessageNotFoundException::class)
    fun testEditMessageByExceptionMessageNotFound(){
        val chat = ChatService.createChat()
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        ChatService.addUserToChat(chat.id, user1.id)
        ChatService.addUserToChat(chat.id, user2.id)
        val editedText = "353"
        val message = ChatService.createMessage(chat.id, user1)

        ChatService.editMessage(chat.id, 3, editedText)
    }

    @Test
    fun testEditMessagePositive(){
        val chat = ChatService.createChat()
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        ChatService.addUserToChat(chat.id, user1.id)
        ChatService.addUserToChat(chat.id, user2.id)
        ChatService.createMessage(chat.id, user1)
        val message = ChatService.createMessage(chat.id, user1)
        val editedText = "353"
        ChatService.editMessage(chat.id, message.id, editedText)

        assertEquals("353", message.text)
    }

    @Test(expected = ChatNotFoundException::class)
    fun testDeleteMessageByExceptionChatNotFound(){
        val chat = ChatService.createChat()
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        ChatService.addUserToChat(chat.id, user1.id)
        ChatService.addUserToChat(chat.id, user2.id)
        val editedText = "353"
        val message = ChatService.createMessage(chat.id, user1)

        ChatService.deleteMessage(3, message.id)
    }

    @Test(expected = MessageNotFoundException::class)
    fun testDeleteMessageByExceptionMessageNotFound(){
        val chat = ChatService.createChat()
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        ChatService.addUserToChat(chat.id, user1.id)
        ChatService.addUserToChat(chat.id, user2.id)
        val editedText = "353"
        val message = ChatService.createMessage(chat.id, user1)

        ChatService.deleteMessage(chat.id, 3)
    }

    @Test
    fun testDeleteMessagePositive(){
        val chat = ChatService.createChat()
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        ChatService.addUserToChat(chat.id, user1.id)
        ChatService.addUserToChat(chat.id, user2.id)
        ChatService.createMessage(chat.id, user1)
        val message = ChatService.createMessage(chat.id, user1)

        assertEquals(true, ChatService.deleteMessage(chat.id, message.id))
    }

    @Test(expected = ChatNotFoundException::class)
    fun testGetMessagesByIdOfUserByExceptionChatNotFound(){
        val chat = ChatService.createChat()
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        ChatService.addUserToChat(chat.id, user1.id)
        ChatService.addUserToChat(chat.id, user2.id)
        val message = ChatService.createMessage(chat.id, user1)

        ChatService.getMessagesByIdOfUser(3, 5, user1.id)
    }

    @Test
    fun testGetMessagesByIdOfUserPositive(){
        val chat = ChatService.createChat()
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        ChatService.addUserToChat(chat.id, user1.id)
        ChatService.addUserToChat(chat.id, user2.id)
        val message = ChatService.createMessage(chat.id, user1)
        ChatService.createMessage(chat.id, user1)
        ChatService.createMessage(chat.id, user2)
        ChatService.createMessage(chat.id, user1)
        val messages = ChatService.getMessagesByIdOfUser(chat.id, 3, user1.id)

        assertEquals(3, messages.size)
    }
}