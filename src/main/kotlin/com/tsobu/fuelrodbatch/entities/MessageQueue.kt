package com.tsobu.fuelrodbatch.entities

import lombok.Data
import javax.persistence.Column
import javax.persistence.Entity

@Data
@Entity
class MessageQueue : BaseEntity() {

    var userId: Long? = null

    @Column(name = "broadcast_name")
    var broadCastName: String? = null

    @Column(name = "message", columnDefinition = "TEXT")
    var message: String? = null

    @Column(name = "campaign_id")
    var campaignId: Long? = null

    var phoneNumber: String? = null

    @Column(name = "sms_count")
    var smsCount: Int = 0

    @Column(name = "message_length")
    var messageLength: Int = 0

    var messageSent: Boolean = false

    @Column(name = "message_cost", columnDefinition = "decimal", precision = 10, scale = 2)
    var messageCost: Double = 0.0
}