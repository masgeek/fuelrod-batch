package com.tsobu.fuelrodbatch.processor

import com.tsobu.fuelrodbatch.entities.MessageQueue
import com.tsobu.fuelrodbatch.services.AtMessagingService
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor

class MessageQueueProcessor(
        private val applicationSubmission: AtMessagingService
) : ItemProcessor<MessageQueue, MessageQueue> {
    private val logger = LoggerFactory.getLogger(MessageQueueProcessor::class.java)

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
        TODO("Not yet implemented")
    }


}