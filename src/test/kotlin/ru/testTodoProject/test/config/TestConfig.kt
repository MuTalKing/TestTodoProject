package ru.testTodoProject.test.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.testTodoProject.assertions.TodoProjectAssertions
import ru.testTodoProject.config.RestAssuredConfiguration
import ru.testTodoProject.config.TodoPojectConfigurationProperties
import ru.testTodoProject.generator.TodoGenerator
import ru.testTodoProject.adapter.TodoProjectAdapter
import ru.testTodoProject.steps.TodoSteps

@Configuration
@EnableConfigurationProperties(TodoPojectConfigurationProperties::class)
class TestConfig(
    private val todoPojectConfigurationProperties: TodoPojectConfigurationProperties
) {
    @Bean
    fun objectMapper() = ObjectMapper().apply {
        registerKotlinModule()
    }

    @Bean
    fun todoProjectRestAssuredConfiguration() = RestAssuredConfiguration(todoPojectConfigurationProperties)

    @Bean
    fun todoProjectAdapter(objectMapper: ObjectMapper, todoProjectRestAssuredConfiguration: RestAssuredConfiguration) =
        TodoProjectAdapter(
            restAssuredConfiguration = todoProjectRestAssuredConfiguration,
            todoPojectConfigurationProperties = todoPojectConfigurationProperties
        )

    @Bean
    fun todoRequestGenerator() = TodoGenerator()

    @Bean
    fun todoProjectAssertions() = TodoProjectAssertions()

    @Bean
    fun todoSteps(objectMapper: ObjectMapper, todoProjectAdapter: TodoProjectAdapter, todoGenerator: TodoGenerator) =
        TodoSteps(objectMapper = objectMapper, todoProjectAdapter = todoProjectAdapter, todoGenerator = todoGenerator)
}