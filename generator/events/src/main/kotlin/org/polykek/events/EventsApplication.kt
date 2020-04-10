package org.polykek.events

import org.polykek.events.service.TestDataService
import org.polykek.events.service.generator.GeneratorProperties
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableConfigurationProperties(GeneratorProperties::class)
class EventsApplication {

	@Bean
	fun init(testDataService: TestDataService) = CommandLineRunner {
		testDataService.generateTestData()
	}
}

fun main(args: Array<String>) {
	runApplication<EventsApplication>(*args)
}