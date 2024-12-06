package ru.testTodoProject.test.tc.ws

import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import org.assertj.core.api.Assertions.assertThat
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import ru.testTodoProject.test.config.TestConfig
import java.net.URI

@Epic("Todo project")
@Feature("WS requests")
@Story("Negative tests")
@SpringBootTest(classes = [TestConfig::class])
class WsTodosNegativeTests {

    @Test
    fun `should handle incorrect protocol connection`() {
        val wsUrlWithIncorrectProtocol = URI("http://localhost:8080/ws")
        val connect =
            object : WebSocketClient(wsUrlWithIncorrectProtocol) {
                override fun onOpen(handshakedata: ServerHandshake?) { }
                override fun onMessage(message: String?) { }
                override fun onClose(code: Int, reason: String?, remote: Boolean) { }
                override fun onError(ex: Exception?) { }
            }.connectBlocking()

        assertThat(connect)
            .`as`("Connect should be false")
            .isEqualTo(false)
    }

    /*
    More scenarios:
    1. should handle invalid data format by creating todo
     */
}