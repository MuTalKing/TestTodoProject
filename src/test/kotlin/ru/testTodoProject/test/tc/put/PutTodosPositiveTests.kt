package ru.testTodoProject.test.tc.put

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.testTodoProject.assertions.TodoProjectAssertions
import ru.testTodoProject.generator.TodoGenerator
import ru.testTodoProject.adapter.TodoProjectAdapter
import ru.testTodoProject.steps.TodoSteps
import ru.testTodoProject.test.common.BaseTest

@Epic("Todo project")
@Feature("PUT requests")
@Story("Positive tests")
class PutTodosPositiveTests @Autowired constructor(
    private val todoProjectAdapter: TodoProjectAdapter,
    private val todoGenerator: TodoGenerator,
    private val todoProjectAssertions: TodoProjectAssertions,
    private val todoSteps: TodoSteps
) : BaseTest(todoProjectAdapter = todoProjectAdapter) {

    @Test
    fun `should successfully update todo`() {
        val firstVersionOfTodo = todoSteps.generateAndSaveTodo()
        todoIds.add(firstVersionOfTodo.id)
        val secondVersionOfTodo = todoGenerator.generateTodoRequest(id = firstVersionOfTodo.id)
        todoProjectAdapter.putTodo(todoId = firstVersionOfTodo.id, todo = secondVersionOfTodo)

        val actualTodo = todoSteps.getTodoById(todoId = firstVersionOfTodo.id)

        todoProjectAssertions.assertTodo(expectedTodo = secondVersionOfTodo, actualTodo = actualTodo)
    }

    @Test
    fun `should successfully update todo with another id`() {
        val firstVersionOfTodo = todoSteps.generateAndSaveTodo()
        todoIds.add(firstVersionOfTodo.id)
        val secondVersionOfTodo = todoGenerator.generateTodoRequest()
        todoIds.add(secondVersionOfTodo.id)
        todoProjectAdapter.putTodo(todoId = firstVersionOfTodo.id, todo = secondVersionOfTodo)

        val actualTodo = todoSteps.getTodoById(todoId = secondVersionOfTodo.id)

        todoProjectAssertions.assertTodo(expectedTodo = secondVersionOfTodo, actualTodo = actualTodo)
    }
}