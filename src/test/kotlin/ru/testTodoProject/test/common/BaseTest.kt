package ru.testTodoProject.test.common

import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import ru.testTodoProject.adapter.TodoProjectAdapter
import ru.testTodoProject.test.config.TestConfig

@SpringBootTest(classes = [TestConfig::class])
class BaseTest @Autowired constructor(
    private val todoProjectAdapter: TodoProjectAdapter
) {
    val todoIds: MutableList<Long> = mutableListOf()

    @AfterEach
    fun tearDown() {
        if (todoIds.isNotEmpty()) {
            todoIds.forEach {
                todoProjectAdapter.deleteTodo(todoId = it)
            }
            todoIds.clear()
        }
    }
}