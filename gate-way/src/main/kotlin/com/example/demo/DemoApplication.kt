package com.example.demo

import com.sun.jersey.core.header.LinkHeader.uri
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@SpringBootApplication
@EnableDiscoveryClient
class DemoApplication {

	@Bean
	open fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator? {
		println("我是叮当猫")
		return builder.routes() //basic proxy
				.route { r: PredicateSpec ->
					r.path("/demo/**").uri("lb://hello-service")
				}
				.route {
					it.path("/fuck/**").uri("lb://hello-service")
				}
				.build()
	}


}

fun main(args: Array<String>) {
	runApplication<DemoApplication>(*args)
}

@Component
class TokenZuulFilter: GatewayFilter {

	override fun filter(exchange: ServerWebExchange?, chain: GatewayFilterChain?): Mono<Void> {
		TODO("Not yet implemented")
	}

}
