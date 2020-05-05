package com.tsobu.fuelrodbatch.config

import com.tsobu.fuelrodbatch.properties.MyBatchProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class AppConfig {

    @Bean
    @ConfigurationProperties(prefix = "batch")
    fun batchProperties(): MyBatchProperties {
        return MyBatchProperties()
    }

}