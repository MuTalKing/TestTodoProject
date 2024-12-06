package ru.testTodoProject.test.tc.post

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import ru.testTodoProject.assertions.TodoProjectAssertions
import ru.testTodoProject.generator.TodoGenerator
import ru.testTodoProject.model.Todo
import ru.testTodoProject.adapter.TodoProjectAdapter
import ru.testTodoProject.steps.TodoSteps
import ru.testTodoProject.test.common.BaseTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Epic("Todo project")
@Feature("POST requests")
@Story("Positive tests")
class PostTodosPositiveTests @Autowired constructor(
    private val todoProjectAdapter: TodoProjectAdapter,
    private val todoGenerator: TodoGenerator,
    private val todoProjectAssertions: TodoProjectAssertions,
    private val todoSteps: TodoSteps
) : BaseTest(todoProjectAdapter = todoProjectAdapter) {
    private fun todosProvider() = listOf(
        todoGenerator.generateTodoRequest(),
        todoGenerator.generateTodoRequest(text = "Special characters: !@#$%^&*()"),
        todoGenerator.generateTodoRequest(completed = true)
    )

    @ParameterizedTest(name = "{0}")
    @MethodSource("todosProvider")
    @DisplayName("Check todo creation with various properties - ")
    fun `should create a new todo with various properties`(todo: Todo) {
        todoIds.add(todo.id)
        todoProjectAdapter.postTodo(todo = todo)

        val actualTodo = todoSteps.getTodoById(todoId = todo.id)

        todoProjectAssertions.assertTodo(expectedTodo = todo, actualTodo = actualTodo)
    }

    /*
    More scenarios:
    1. Create todo with long text (check out of length)
    2. Create todo without text (if it's possible)
     */
}