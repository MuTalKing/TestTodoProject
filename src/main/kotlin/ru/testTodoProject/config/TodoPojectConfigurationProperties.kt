package ru.testTodoProject.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("todo-project")
data class TodoPojectConfigurationProperties(
    val url: String,
    val username: String,
    val password: String,
    val wsUrl: String
)
