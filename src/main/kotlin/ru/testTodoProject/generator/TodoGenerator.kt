package ru.testTodoProject.generator

import io.qameta.allure.Step
import ru.testTodoProject.model.Todo
import ru.testTodoProject.utils.RandomUtil.randomEngLetters
import ru.testTodoProject.utils.RandomUtil.randomLong

class TodoGenerator {
    @Step("Generate Todo with id = {id}")
    fun generateTodoRequest(
        id: Long = randomLong(),
        text: String = randomEngLetters(32),
        completed: Boolean = false
    ) = Todo(
        id = id,
        text = text,
        completed = completed
    )
}