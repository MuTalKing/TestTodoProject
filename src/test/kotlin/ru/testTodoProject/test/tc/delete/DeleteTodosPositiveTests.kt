package ru.testTodoProject.test.tc.delete

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.testTodoProject.generator.TodoGenerator
import ru.testTodoProject.adapter.TodoProjectAdapter
import ru.testTodoProject.steps.TodoSteps
import ru.testTodoProject.test.common.BaseTest

@Epic("Todo project")
@Feature("DELETE requests")
@Story("Positive tests")
class DeleteTodosPositiveTests @Autowired constructor(
    private val todoProjectAdapter: TodoProjectAdapter,
    private val todoGenerator: TodoGenerator,
    private val todoSteps: TodoSteps
) : BaseTest(todoProjectAdapter = todoProjectAdapter) {

    @Test
    fun `should successfully delete todo`() {
        val todo = todoSteps.generateAndSaveTodo()

        todoSteps.getTodoById(todoId = todo.id)

        todoProjectAdapter.deleteTodo(todoId = todo.id)

        val todosListAfterDeleting = todoSteps.getTodosList()

        Assertions.assertThat(todosListAfterDeleting)
            .`as`("Get request should return empty list")
            .isEmpty()
    }
}