package com.example.demo

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient("hello-service")
interface UserService {

    @GetMapping("/demo")
    fun getByUserId(): String


    @GetMapping("/fuck")
    fun fuck(@RequestParam("name") name: String): String

}
