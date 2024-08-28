import Exceptions.ChatNotFoundException
import Exceptions.MessageNotFoundException
import Exceptions.NotEnoughUsersException
import Exceptions.UserNotFoundException
import Services.Chat
import Services.ChatService
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ChatServiceTest {

    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test
    fun testAddUserPositive() {
        val user = ChatService.createUser()
        assertEquals(1, user.id)
    }

    @Test
    fun testAddUserNegative() {
        val user = ChatService.createUser()
        assertNotEquals(2, user.id)
    }

    @Test
    fun testGetUserByIdPositive() {
        val user = ChatService.createUser()
        assertEquals(user, ChatService.getUserById(user.id))
    }

    @Test
    fun testGetUserByIdNegative() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        assertNotEquals(user1, ChatService.getUserById(user2.id))
    }

    @Test
    fun testAddChatPositive() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        assertEquals(1, chat.id)
    }

    @Test
    fun testAddChatNegative() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        assertNotEquals(2, chat.id)
    }

    @Test
    fun testGetChatByIdPositive() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        assertEquals(chat, ChatService.getChatById(chat.id))
    }

    @Test
    fun testGetChatByIdNegative() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat1 = ChatService.createMessage(user1, user2 = user2, "test")
        val user3 = ChatService.createUser()
        val user4 = ChatService.createUser()
        val chat2 = ChatService.createMessage(user3, user2 = user4, "test")
        assertNotEquals(chat1, ChatService.getChatById(chat2.id))
    }

    @Test(expected = ChatNotFoundException::class)
    fun testAddUserToChatByExceptionChat() {
        val user = ChatService.createUser()
        ChatService.addUserToChat(2, user.id)
    }

    @Test(expected = UserNotFoundException::class)
    fun testAddUserToChatByExceptionUser() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        ChatService.addUserToChat(chat.id, 3)
    }

    @Test
    fun testAddUserToChat() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val user3 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        assertEquals(true, ChatService.addUserToChat(chat.id, user3.id))
    }

    @Test(expected = ChatNotFoundException::class)
    fun testDeleteChatByExceptionChat() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        ChatService.deleteChat(3)
    }

    @Test
    fun testDeleteChat() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        assertEquals(true, ChatService.deleteChat(chat.id))
    }

    @Test
    fun testGetChatsPositive() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        assertEquals(1, ChatService.getChats().size)
    }

    @Test
    fun testGetChatsNegative() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        assertNotEquals(2, ChatService.getChats().size)
    }

    @Test
    fun testCreateMessagePositive() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        assertEquals(1, chat.messages.size)
    }

    @Test(expected = ChatNotFoundException::class)
    fun testEditMessageByExceptionChatNotFound() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        val editedText = "353"
        ChatService.editMessage(3, chat.messages[0].id, editedText)
    }

    @Test(expected = MessageNotFoundException::class)
    fun testEditMessageByExceptionMessageNotFound() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        val editedText = "353"
        ChatService.editMessage(chat.id, 3, editedText)
    }

    @Test
    fun testEditMessagePositive() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        val editedText = "353"
        ChatService.editMessage(chat.id, chat.messages[0].id, editedText)
        assertEquals("353", chat.messages[0].text)
    }

    @Test(expected = ChatNotFoundException::class)
    fun testDeleteMessageByExceptionChatNotFound() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        ChatService.deleteMessage(3, chat.messages[0].id)
    }

    @Test(expected = MessageNotFoundException::class)
    fun testDeleteMessageByExceptionMessageNotFound() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        ChatService.deleteMessage(chat.id, 3)
    }

    @Test
    fun testDeleteMessagePositive() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        assertEquals(true, ChatService.deleteMessage(chat.id, chat.messages[0].id))
    }

    @Test(expected = ChatNotFoundException::class)
    fun testGetMessagesByIdOfUserByExceptionChatNotFound() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        ChatService.getMessagesByIdOfUser(3, 5, user1.id)
    }

    @Test
    fun testGetMessagesByIdOfUserPositive() {
        val user1 = ChatService.createUser()
        val user2 = ChatService.createUser()
        val chat = ChatService.createMessage(user1, user2 = user2, "test")
        ChatService.createMessage(user1, user2 = user2, "test")
        ChatService.createMessage(user1, user2 = user2, "test")
        val messages = ChatService.getMessagesByIdOfUser(chat.id, 3, user1.id)
        assertEquals(3, messages.size)
    }
}