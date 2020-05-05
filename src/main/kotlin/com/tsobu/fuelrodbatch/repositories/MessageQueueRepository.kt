package com.tsobu.fuelrodbatch.repositories

import com.tsobu.fuelrodbatch.entities.MessageQueue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface MessageQueueRepository : JpaRepository<MessageQueue, Long> {

    override fun findById(id: Long): Optional<MessageQueue>

    override fun findAll(): List<MessageQueue>

    fun findAllByMessageSentIsFalse(): List<MessageQueue>

    fun findAllByUserIdAndMessageSentIsFalse(userId: Long): List<MessageQueue>

    fun findAllByMessageSentIsTrue(): List<MessageQueue>
}