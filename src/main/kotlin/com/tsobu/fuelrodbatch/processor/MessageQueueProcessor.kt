package com.tsobu.fuelrodbatch.processor

import com.fasterxml.jackson.databind.ObjectMapper
import com.tsobu.fuelrodbatch.entities.MessageQueue
import com.tsobu.fuelrodbatch.request.MessageRequest
import com.tsobu.fuelrodbatch.services.ApiUserService
import com.tsobu.fuelrodbatch.services.AtMessagingService
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor

class MessageQueueProcessor(
        private val messagingService: AtMessagingService,
        private val userService: ApiUserService
) : ItemProcessor<MessageQueue, MessageQueue> {
    private val logger = LoggerFactory.getLogger(MessageQueueProcessor::class.java)
    private val mapper = ObjectMapper()

    /**
     * Process the provided item, returning a potentially modified or new item for continued
     * processing.  If the returned result is null, it is assumed that processing of the item
     * should not continue.
     *
     * @param item to be processed
     * @return potentially modified or new item for continued processing, `null` if processing of the
     * provided item should not continue.
     *
     * @throws Exception thrown if exception occurs during processing.
     */
    override fun process(item: MessageQueue): MessageQueue? {
        //val savedResponse = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item)

        val apiUserServices = userService.getActiveService(item.userId!!)
        val apiUser = apiUserServices.apiUser

        if (apiUser != null) {
            val messageRequest = MessageRequest(
                    user = apiUserServices.serviceUser!!,
                    password = apiUserServices.servicePassword!!,
                    to = item.phoneNumber.toString(),
                    text = item.message!!
            )
            messageRequest.queue = false

            val resp = messagingService.sendTextMessage(messageRequest = messageRequest, apiUser = apiUser, apiUserServices = apiUserServices)

            val saved = messagingService.updateQueueItem(item)
            val savedResponse = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(saved)

            logger.info(saved.id.toString())
            logger.info(savedResponse)
        }

        return item
    }


}