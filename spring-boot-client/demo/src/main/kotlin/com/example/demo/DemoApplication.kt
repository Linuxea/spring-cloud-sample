package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableDiscoveryClient
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}


@RestController
@RequestMapping
class DemoController {

	@GetMapping("/demo")
	fun get(): String {
		return "two-service"
	}

	@GetMapping("/fuck")
	fun fuck(@RequestParam("name")name: String): String {
		return "two-service-fuck-${name}"
	}

}
