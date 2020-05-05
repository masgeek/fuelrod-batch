package com.tsobu.fuelrodbatch.entities

import lombok.Data
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table


@Data
@Entity
@Table(name = "sms_outbox")
class SmsOutbox : BaseEntity() {

    //    @Column(name = "user_id", insertable = false, updatable = false)
    var userId: Long? = null

    var dateTime: String? = null
    var messageId: String? = null
    var senderId: String? = null
    var currency: String = "KES"
    var phoneNumber: String? = null

    @Column(name = "sms_text", columnDefinition = "TEXT")
    var smsText: String? = null

    var networkCode: Int = 0
    var characterCount: Int = 0
    var smsCount: Int = 0
    var retryCount: Int = 0

    @Column(name = "single_sms_cost", columnDefinition = "decimal", precision = 10, scale = 2)
    var singleSmsCost: Double = 0.0

    @Column(name = "actual_cost", columnDefinition = "decimal", precision = 10, scale = 2)
    var actualCost: Double = 0.0

    @Column(name = "message_cost", columnDefinition = "decimal", precision = 10, scale = 2)
    var messageCost: Double = 0.0

    @Column(name = "message_string_cost")
    var messageStringCost: String? = null

    var deliveryStatus: String? = null
    var failureReason: String? = null
}