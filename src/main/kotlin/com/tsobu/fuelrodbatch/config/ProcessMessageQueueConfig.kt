package com.tsobu.fuelrodbatch.config

import com.tsobu.fuelrodbatch.entities.MessageQueue
import com.tsobu.fuelrodbatch.processor.MessageQueueProcessor
import com.tsobu.fuelrodbatch.repositories.MessageQueueRepository
import com.tsobu.fuelrodbatch.services.ApiUserService
import com.tsobu.fuelrodbatch.services.AtMessagingService
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.data.RepositoryItemReader
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Sort

@Configuration
class ProcessMessageQueueConfig {

    @Bean
    fun processMessageJob(
            @Qualifier("processMessageStep")
            createAccountStep: Step,
            jobBuilderFactory: JobBuilderFactory
    ): Job {
        return jobBuilderFactory.get("processMessage")
                .incrementer(RunIdIncrementer())
                .start(createAccountStep)
                .build()
    }

    @Bean
    fun processMessageStep(
            messageQueueProcessor: MessageQueueProcessor,
            messageQueueRepository: MessageQueueRepository,
            stepBuilderFactory: StepBuilderFactory
    ): Step {
        val reader = RepositoryItemReader<MessageQueue>()
        reader.setRepository(messageQueueRepository)
        reader.setSort(mapOf(Pair("id", Sort.Direction.ASC)))
        reader.setMethodName("findAllByMessageSentIsFalse")

        return stepBuilderFactory.get("processMessageStep")
                .chunk<MessageQueue, MessageQueue>(10)
                .reader(reader)
                .processor(messageQueueProcessor)
                .faultTolerant()
                //.skipLimit(10)
                //.skip(ServiceIntegrationException::class.java)
                .writer { }
                .build()
    }

    @Bean
    fun messageQueueProcessor(
            atMessagingService: AtMessagingService,
            userService: ApiUserService
    ): MessageQueueProcessor {
        return MessageQueueProcessor(atMessagingService, userService)
    }
}