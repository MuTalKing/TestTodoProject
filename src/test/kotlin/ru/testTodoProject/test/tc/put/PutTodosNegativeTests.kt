package ru.testTodoProject.test.tc.put

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.testTodoProject.assertions.TodoProjectAssertions
import ru.testTodoProject.generator.TodoGenerator
import ru.testTodoProject.adapter.TodoProjectAdapter
import ru.testTodoProject.test.common.BaseTest
import java.lang.AssertionError

@Epic("Todo project")
@Feature("PUT requests")
@Story("Negative tests")
class PutTodosNegativeTests @Autowired constructor(
    private val todoProjectAdapter: TodoProjectAdapter,
    private val todoGenerator: TodoGenerator,
    private val todoProjectAssertions: TodoProjectAssertions
) : BaseTest(todoProjectAdapter = todoProjectAdapter) {

    @Test
    fun `should return not found for updating non-existing todo`() {
        val todo = todoGenerator.generateTodoRequest()

        todoProjectAssertions.assertExceptionContainsMessage(
            expectedExceptionType = AssertionError::class.java,
            expectedMessageFragment = "Expected status code <200> but was <404>"
        ) {
            todoProjectAdapter.putTodo(todoId = todo.id, todo = todo).statusCode(200)
        }
    }
}