package ru.testTodoProject.steps

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.qameta.allure.Step
import ru.testTodoProject.adapter.TodoProjectAdapter
import ru.testTodoProject.generator.TodoGenerator
import ru.testTodoProject.model.Todo
import ru.testTodoProject.utils.RandomUtil.randomEngLetters
import ru.testTodoProject.utils.RandomUtil.randomLong

class TodoSteps(
    private val objectMapper: ObjectMapper,
    private val todoProjectAdapter: TodoProjectAdapter,
    private val todoGenerator: TodoGenerator
) {
    @Step("Get list of todos")
    fun getTodosList(offset: Int? = null, limit: Int? = null): List<Todo?> =
        objectMapper.readValue(
            todoProjectAdapter.getTodos(offset = offset, limit = limit).extract().response().asString()
        )

    @Step("Get todo with id = {todoId}")
    fun getTodoById(todoId: Long) = getTodosList().find { it?.id == todoId }

    @Step("Generate and save todo")
    fun generateAndSaveTodo(): Todo {
        val todo = todoGenerator.generateTodoRequest()
        todoProjectAdapter.postTodo(todo = todo)
        return todo
    }
}