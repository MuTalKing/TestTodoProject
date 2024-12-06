package ru.testTodoProject.adapter

import io.qameta.allure.Step
import io.restassured.RestAssured
import io.restassured.http.Header
import io.restassured.response.ValidatableResponse
import org.apache.http.HttpHeaders
import ru.testTodoProject.config.RestAssuredConfiguration
import ru.testTodoProject.config.TodoPojectConfigurationProperties
import ru.testTodoProject.model.Todo
import java.util.Base64

class TodoProjectAdapter(
    private val restAssuredConfiguration: RestAssuredConfiguration,
    private val todoPojectConfigurationProperties: TodoPojectConfigurationProperties
) {
    @Step("[DELETE] Delete todo with id = {todoId}")
    fun deleteTodo(todoId: Long) = RestAssured
        .given()
        .spec(
            restAssuredConfiguration.requestSpec.header(
                Header(
                    HttpHeaders.AUTHORIZATION,
                    "Basic ${
                        Base64.getEncoder()
                            .encodeToString("${todoPojectConfigurationProperties.username}:${todoPojectConfigurationProperties.password}".toByteArray())
                    }"
                )
            )
        )
        .`when`()
        .delete("/$todoId")
        .then()
        .spec(restAssuredConfiguration.responseSpec)

    @Step("[GET] Get response from /todos")
    fun getTodos(offset: Int? = null, limit: Int? = null): ValidatableResponse {
        val requestSpec = RestAssured.given()
        offset?.let { requestSpec.queryParam("offset", it) }
        limit?.let { requestSpec.queryParam("limit", it) }
        val response = requestSpec
            .spec(restAssuredConfiguration.requestSpec)
            .`when`()
            .get()
            .then()
            .spec(restAssuredConfiguration.responseSpec)
        return response
    }

    @Step("[POST] Create Todo with id = {todoRequest.id}")
    fun postTodo(todo: Todo) = RestAssured
        .given()
        .spec(restAssuredConfiguration.requestSpec)
        .body(todo)
        .`when`()
        .post()
        .then()
        .spec(restAssuredConfiguration.responseSpec)

    @Step("[PUT] Update todo with id = {todoId}")
    fun putTodo(todoId: Long, todo: Todo) = RestAssured
        .given()
        .spec(restAssuredConfiguration.requestSpec)
        .body(todo)
        .`when`()
        .put("/$todoId")
        .then()
        .spec(restAssuredConfiguration.responseSpec)
}