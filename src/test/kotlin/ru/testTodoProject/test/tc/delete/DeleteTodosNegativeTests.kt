package ru.testTodoProject.test.tc.delete

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.testTodoProject.assertions.TodoProjectAssertions
import ru.testTodoProject.adapter.TodoProjectAdapter
import ru.testTodoProject.test.common.BaseTest
import ru.testTodoProject.utils.RandomUtil.randomLong
import java.lang.AssertionError

@Epic("Todo project")
@Feature("DELETE requests")
@Story("Negative tests")
class DeleteTodosNegativeTests @Autowired constructor(
    private val todoProjectAdapter: TodoProjectAdapter,
    private val todoProjectAssertions: TodoProjectAssertions
) : BaseTest(todoProjectAdapter = todoProjectAdapter) {
    @Test
    fun `should fail by deleting non-existing todo`() {
        todoProjectAssertions.assertExceptionContainsMessage(
            expectedExceptionType = AssertionError::class.java,
            expectedMessageFragment = "Expected status code <204> but was <404>"
        ) {
            todoProjectAdapter.deleteTodo(todoId = randomLong()).statusCode(204)
        }
    }
}