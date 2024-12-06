package ru.testTodoProject.test.tc.post

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.testTodoProject.assertions.TodoProjectAssertions
import ru.testTodoProject.adapter.TodoProjectAdapter
import ru.testTodoProject.steps.TodoSteps
import ru.testTodoProject.test.common.BaseTest
import java.lang.AssertionError

@Epic("Todo project")
@Feature("POST requests")
@Story("Negative tests")
class PostTodosNegativeTests @Autowired constructor(
    private val todoProjectAdapter: TodoProjectAdapter,
    private val todoProjectAssertions: TodoProjectAssertions,
    private val todoSteps: TodoSteps
) : BaseTest(todoProjectAdapter = todoProjectAdapter) {

    @Test
    fun `should fail to create todo with duplicated ID`() {
        val todo = todoSteps.generateAndSaveTodo()
        todoIds.add(todo.id)

        todoProjectAssertions.assertExceptionContainsMessage(
            expectedExceptionType = AssertionError::class.java,
            expectedMessageFragment = "Expected status code <201> but was <400>"
        ) {
            todoProjectAdapter.postTodo(todo = todo).statusCode(201)
        }
    }

    /*
    More scenarios:
    1. Create todo without required parameters
    2. Create todo with invalid data parameters
     */
}