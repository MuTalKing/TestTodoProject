package ru.testTodoProject.test.tc.get

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.testTodoProject.assertions.TodoProjectAssertions
import ru.testTodoProject.adapter.TodoProjectAdapter
import ru.testTodoProject.test.common.BaseTest
import java.lang.AssertionError

@Epic("Todo project")
@Feature("GET requests")
@Story("Negative tests")
class GetTodosNegativeTests @Autowired constructor(
    private val todoProjectAdapter: TodoProjectAdapter,
    private val todoProjectAssertions: TodoProjectAssertions
) : BaseTest(todoProjectAdapter = todoProjectAdapter) {
    @Test
    fun `should return 400 for negative offset`() {
        todoProjectAssertions.assertExceptionContainsMessage(
            expectedExceptionType = AssertionError::class.java,
            expectedMessageFragment = "Expected status code <200> but was <400>"
        ) {
            todoProjectAdapter.getTodos(offset = -123).statusCode(200)
        }
    }

    @Test
    fun `should return 400 for negative limit`() {
        todoProjectAssertions.assertExceptionContainsMessage(
            expectedExceptionType = AssertionError::class.java,
            expectedMessageFragment = "Expected status code <200> but was <400>"
        ) {
            todoProjectAdapter.getTodos(limit = -123).statusCode(200)
        }
    }

    /*
    More scenarios:
    1. Invalid data type scenario for limit and offset (for example 'abc')
    2. SQL injection script in query parameters
     */
}