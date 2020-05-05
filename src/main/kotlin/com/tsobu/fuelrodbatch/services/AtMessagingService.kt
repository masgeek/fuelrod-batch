package com.tsobu.fuelrodbatch.services


import com.africastalking.AfricasTalking
import com.africastalking.SmsService
import com.africastalking.sms.Recipient
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.tsobu.fuelrodbatch.entities.ApiUser
import com.tsobu.fuelrodbatch.entities.ApiUserServices
import com.tsobu.fuelrodbatch.entities.MessageQueue
import com.tsobu.fuelrodbatch.entities.SmsOutbox
import com.tsobu.fuelrodbatch.repositories.ApiUsersRepository
import com.tsobu.fuelrodbatch.repositories.MessageQueueRepository
import com.tsobu.fuelrodbatch.repositories.SmsOutboxRepository
import com.tsobu.fuelrodbatch.request.ApiUserQueue
import com.tsobu.fuelrodbatch.request.MessageRequest
import com.tsobu.fuelrodbatch.utils.SmsLengthCalculator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class AtMessagingService
@Autowired
constructor(val messageQueueRepository: MessageQueueRepository,
            val apiUsersRepository: ApiUsersRepository,
            val smsOutboxRepository: SmsOutboxRepository) {

    private val logger = LoggerFactory.getLogger(AtMessagingService::class.java)
    private val phoneUtil = PhoneNumberUtil.getInstance()
    private val mapper = ObjectMapper()
    private val smsCalc = SmsLengthCalculator()

    fun sendQueuedMessages(apiUserQueue: ApiUserQueue, apiUserServices: ApiUserServices, apiUser: ApiUser) {
        val messages = messageQueueRepository.findAllByMessageSentIsFalse()

        for (message in messages) {
            val messageRequest = MessageRequest(
                    user = apiUserServices.serviceUser!!,
                    password = apiUserServices.servicePassword!!,
                    to = message.phoneNumber.toString(),
                    text = message.message!!
            )

            messageRequest.queue = false
            message.messageSent = true

            sendTextMessage(messageRequest = messageRequest, apiUser = apiUser, apiUserServices = apiUserServices)

            messageQueueRepository.save(message)
        }
    }

    fun sendTextMessage(messageRequest: MessageRequest, apiUser: ApiUser, apiUserServices: ApiUserServices) {
        AfricasTalking.initialize(apiUserServices.serviceUser, apiUserServices.servicePassword)


        val sms = AfricasTalking.getService<SmsService>(AfricasTalking.SERVICE_SMS)


        logger.info("Receiving request message")
        val requestString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(messageRequest)
        logger.info(requestString)


        val numberList = buildMessagePayload(messageRequest)
        try {


            val response: MutableList<Recipient> = when {
                (apiUserServices.sender == null) -> {
                    //use default service sender ID
                    sms.send(messageRequest.text, numberList, messageRequest.queue)
                }
                else -> {
                    sms.send(messageRequest.text, apiUserServices.sender, numberList, messageRequest.queue)
                }
            }

            //save to database
            val recipientMessage = response[0]

            val smsOutbox = SmsOutbox()

            val textRegex = "[a-zA-Z]+".toRegex()
            val numberRegex = "\\d*\\.?\\d*\$".toRegex()
            val costString = recipientMessage.cost

            val extractText = textRegex.find(costString)
            val extractNumber = numberRegex.find(costString)

            val smsText = messageRequest.text
            val smsCount = smsCalc.getPartCount(smsText)
            val smsLength = smsText.length

            if (extractText != null) {
                val currency = extractText.value
                smsOutbox.currency = currency
            }

            if (extractNumber != null) {
                val cost = extractNumber.value
                smsOutbox.messageCost = cost.toDouble()
            }

            smsOutbox.singleSmsCost = apiUser.messageCost
            smsOutbox.actualCost = apiUser.messageCost * smsCount
            smsOutbox.userId = apiUser.id
            smsOutbox.smsCount = smsCount
            smsOutbox.characterCount = smsLength
            smsOutbox.senderId = apiUserServices.sender
            smsOutbox.messageId = recipientMessage.messageId
            smsOutbox.messageStringCost = recipientMessage.cost
            smsOutbox.deliveryStatus = recipientMessage.status
            smsOutbox.phoneNumber = recipientMessage.number
            smsOutbox.smsText = messageRequest.text

            //let us save this one now
            val saved = smsOutboxRepository.save(smsOutbox)


            val responseString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response)
            val savedResponse = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(saved)

            logger.info(responseString)
            logger.info(savedResponse)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    private fun buildMessagePayload(messageRequest: MessageRequest): Array<String> {

        var phoneNumber = messageRequest.to

        if (phoneNumber == "0" || phoneNumber.isEmpty()) {
            phoneNumber = "9999999999" //if numbers passed are invalid
        }
        if (phoneNumber.startsWith("0")) {
            val phoneNumberObject = processPhoneNumber(phoneNumber)
            phoneNumber = phoneUtil.format(phoneNumberObject, PhoneNumberUtil.PhoneNumberFormat.E164)
        }
        if (!phoneNumber.startsWith("+")) {
            phoneNumber = "+$phoneNumber"
        }

        return arrayOf(phoneNumber)
    }

    fun processPhoneNumber(phoneNumberString: String, countryCode: String = "KE"): Phonenumber.PhoneNumber? {
        return phoneUtil.parse(phoneNumberString, countryCode)
    }

    private fun isNumberValid(phoneNumber: String): Boolean {
        val number = processPhoneNumber(phoneNumber)

        return phoneUtil.isValidNumber(number)

    }

    fun updateQueueItem(item: MessageQueue): MessageQueue {
        item.messageSent = true
        return messageQueueRepository.saveAndFlush(item)
    }
}