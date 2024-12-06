package ru.testTodoProject.test.tc.ws

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.assertj.core.api.Assertions.assertThat
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.testTodoProject.adapter.TodoProjectAdapter
import ru.testTodoProject.config.TodoPojectConfigurationProperties
import ru.testTodoProject.generator.TodoGenerator
import ru.testTodoProject.test.common.BaseTest
import java.net.URI
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@Epic("Todo project")
@Feature("WS requests")
@Story("Positive tests")
class WsTodosPositiveTests @Autowired constructor(
    private val todoProjectAdapter: TodoProjectAdapter,
    private val todoGenerator: TodoGenerator,
    private val todoPojectConfigurationProperties: TodoPojectConfigurationProperties
) : BaseTest(todoProjectAdapter = todoProjectAdapter) {
    private val wsUri = URI(todoPojectConfigurationProperties.wsUrl)

    @Test
    fun `should receive notification via WebSocket after updating message`() {
        val messageLatch = CountDownLatch(1)
        val receivedMessages = mutableListOf<String>()

        val client = object : WebSocketClient(wsUri) {
            override fun onOpen(handshakedata: ServerHandshake?) {}

            override fun onMessage(message: String?) {
                message?.let {
                    receivedMessages.add(it)
                    messageLatch.countDown()
                }
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {}
            override fun onError(ex: Exception?) {}
        }

        client.connectBlocking()

        val todo = todoGenerator.generateTodoRequest()
        todoIds.add(todo.id)
        todoProjectAdapter.postTodo(todo = todo)

        messageLatch.await(5, TimeUnit.SECONDS)
        client.close()

        assertThat(receivedMessages).isNotEmpty
        assertThat(receivedMessages.first()).contains(todo.text)
    }
}