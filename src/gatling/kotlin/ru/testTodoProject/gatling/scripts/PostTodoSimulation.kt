package ru.testTodoProject.gatling.scripts

import io.gatling.javaapi.core.CoreDsl.StringBody
import io.gatling.javaapi.core.CoreDsl.constantUsersPerSec
import io.gatling.javaapi.core.CoreDsl.nothingFor
import io.gatling.javaapi.core.CoreDsl.rampUsersPerSec
import io.gatling.javaapi.core.CoreDsl.scenario
import io.gatling.javaapi.core.Simulation
import io.gatling.javaapi.http.HttpDsl.http
import io.gatling.javaapi.http.HttpDsl.status

class PostTodoSimulation : Simulation() {
    val httpConfig = http
        .baseUrl("http://localhost:8080/todos")
        .acceptHeader("application/json")
        .contentTypeHeader("application/json")
        .shareConnections()
        .connectionHeader("keep-alive")

    var todoBody = StringBody { session ->
        """{"id":${System.currentTimeMillis()}, "text": "Load Test TODO", "completed": false}"""
    }

    val scenario = scenario("Post todos Scenario")
        .exec(
            http("Create Todo")
                .post("")
                .body(todoBody)
                .check(status().`is`(201))
        )

    init {
        setUp(
            scenario.injectOpen(
                nothingFor(5),
                rampUsersPerSec(0.0).to(10.0).during(10),
                constantUsersPerSec(10.0).during(10),
                rampUsersPerSec(10.0).to(25.0).during(10),
                constantUsersPerSec(25.0).during(10)
            )
        ).protocols(httpConfig)
    }
}