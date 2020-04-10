package org.polykek.eventsgraph

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class EventsGraphApplication

fun main(args: Array<String>) {
	runApplication<EventsGraphApplication>(*args)
}