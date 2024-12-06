package ru.testTodoProject.assertions

import io.qameta.allure.Step
import org.assertj.core.api.Assertions
import org.assertj.core.api.SoftAssertions
import ru.testTodoProject.model.Todo

class TodoProjectAssertions {

    @Step("Assert that the received Todo matches the expected one")
    fun assertTodo(expectedTodo: Todo, actualTodo: Todo?) {
        SoftAssertions.assertSoftly { softly ->
            softly.assertThat(expectedTodo.id)
                .`as`("Todo's id should be equal to ${expectedTodo.id}")
                .isEqualTo(actualTodo?.id)
            softly.assertThat(expectedTodo.text)
                .`as`("Todo's text should be equal to ${expectedTodo.text}")
                .isEqualTo(actualTodo?.text)
            softly.assertThat(expectedTodo.completed)
                .`as`("Todo's completed should be equal to ${expectedTodo.completed}")
                .isEqualTo(actualTodo?.completed)
        }
    }

    @Step("Assert that error {expectedExceptionType} has been thrown")
    fun <T : Throwable> assertExceptionContainsMessage(
        expectedExceptionType: Class<T>,
        expectedMessageFragment: String,
        block: () -> Unit
    ) {
        Assertions.assertThatThrownBy {
            block()
        }.isInstanceOf(expectedExceptionType)
            .hasMessageContaining(expectedMessageFragment)
    }
}