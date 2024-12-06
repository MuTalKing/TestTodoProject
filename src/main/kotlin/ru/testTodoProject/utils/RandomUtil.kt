package ru.testTodoProject.utils

object RandomUtil {
    fun randomLong(min: Long = 0, max: Long = Long.MAX_VALUE) = (min..max).random()

    fun randomEngLetters(length: Int): String {
        val letters = ('a'..'z').plus('A'..'Z')
        return (1..length).map { letters.random() }.joinToString("")
    }
}