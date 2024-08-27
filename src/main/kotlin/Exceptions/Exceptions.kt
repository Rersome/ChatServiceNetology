package Exceptions

class ChatNotFoundException(message: String) : Exception(message)
class MessageNotFoundException(message: String) : Exception(message)
class UserNotFoundException(message: String) : Exception(message)
class NotEnoughUsersException(message: String) : Exception(message)