package ru.testTodoProject.test.tc.get

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.testTodoProject.assertions.TodoProjectAssertions
import ru.testTodoProject.adapter.TodoProjectAdapter
import ru.testTodoProject.model.Todo
import ru.testTodoProject.steps.TodoSteps
import ru.testTodoProject.test.common.BaseTest

@Epic("Todo project")
@Feature("GET requests")
@Story("Positive tests")
class GetTodosPositiveTests @Autowired constructor(
    private val todoProjectAdapter: TodoProjectAdapter,
    private val todoProjectAssertions: TodoProjectAssertions,
    private val todoSteps: TodoSteps
) : BaseTest(todoProjectAdapter = todoProjectAdapter) {

    @Test
    fun `should return created todo`() {
        val expectedTodo = todoSteps.generateAndSaveTodo()
        todoIds.add(expectedTodo.id)

        val actualTodo = todoSteps.getTodoById(todoId = expectedTodo.id)

        todoProjectAssertions.assertTodo(expectedTodo = expectedTodo, actualTodo = actualTodo)
    }

    @Test
    fun `should return todos with specified offset and limit`() {
        repeat(times = 3) {
            val todo = todoSteps.generateAndSaveTodo()
            todoIds.add(todo.id)
        }

        val todosList: List<Todo?> = todoSteps.getTodosList(offset = 1, limit = 2)

        Assertions.assertThat(todosList.size)
            .`as`("Size of the todo list should be equal to 2")
            .isEqualTo(2)
    }

    @Test
    fun `should return empty todo list`() {
        val todosList = todoSteps.getTodosList()

        Assertions.assertThat(todosList)
            .`as`("Todos list should be empty")
            .isEmpty()
    }

    /*
    More scenarios:
    1. Checking for data when the end of the list is reached: If offset is equal to the total number of records, make sure that an empty list is returned without errors.
    2. Getting the first element: Using offset=0 and limit=1 to verify that we are getting the first element.
    3. Getting the last element
     */
}