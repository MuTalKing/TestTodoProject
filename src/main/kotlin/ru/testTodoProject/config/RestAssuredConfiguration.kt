package ru.testTodoProject.config

import io.qameta.allure.restassured.AllureRestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.builder.ResponseSpecBuilder
import io.restassured.config.LogConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.filter.log.LogDetail
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import io.restassured.specification.ResponseSpecification

class RestAssuredConfiguration(
    private val todoPojectConfigurationProperties: TodoPojectConfigurationProperties
) {

    private val logConfig = LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
    private val config = RestAssuredConfig.config().logConfig(logConfig)

    val requestSpec: RequestSpecification = RequestSpecBuilder()
        .setBaseUri(todoPojectConfigurationProperties.url)
        .addHeader("Accept", "application/json")
        .setContentType(ContentType.JSON)
        .setConfig(config)
        .log(LogDetail.URI)
        .addFilter(AllureRestAssured())
        .build()

    val responseSpec: ResponseSpecification = ResponseSpecBuilder()
        .log(LogDetail.BODY)
        .build()
}