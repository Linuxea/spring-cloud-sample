package com.example.demo

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableDiscoveryClient
@EnableFeignClients
class DemoApplication

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}


@Configuration
class RibbonConfiguration {

	@Bean
	@LoadBalanced
	fun restTemplate(): RestTemplate {
		println("初始化 restTemplate")
		return RestTemplate()
	}

}

@RestController
@RequestMapping("/test")
class DemoController {

	@Autowired
	private lateinit var restTemplate: RestTemplate
	@Autowired
	private lateinit var userService: UserService

	@GetMapping
	@HystrixCommand(fallbackMethod = "error")
	fun get(): String?{
		return restTemplate.getForEntity("http://hello-service/demo", String::class.java).body
	}


	@GetMapping("/by-feign")
	fun getByFeign(@RequestParam("name")name: String): String {
		return userService.fuck(name)
	}


	open fun error(): String {
		return "调用出错了"
	}



}

