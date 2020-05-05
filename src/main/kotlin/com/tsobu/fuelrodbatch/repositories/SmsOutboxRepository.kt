package com.tsobu.fuelrodbatch.repositories

import com.tsobu.fuelrodbatch.entities.SmsOutbox
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SmsOutboxRepository : JpaRepository<SmsOutbox, Long> {

    fun findAllByUserId(userId: Long): List<SmsOutbox>

    fun findByMessageId(messageId: String): SmsOutbox?
}