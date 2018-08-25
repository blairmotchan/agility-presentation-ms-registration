package com.agilemidwest.agility.registration.receiver

import com.agilemidwest.agility.contract.RegistrationRequest
import com.agilemidwest.agility.contract.RegistrationResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
open class QueueService(
        private val tempalate: RabbitTemplate,
        @Qualifier("confirmationQueue")
        private val confirmationQueue: Queue) {
    private var latch: CountDownLatch = CountDownLatch(1)
    private var objectMapper = ObjectMapper()

    fun receiveMessage(payload: String) {
        try {
            val message = objectMapper.readValue(payload, RegistrationRequest::class.java)
            Thread.sleep(5000)
            sendMessage(RegistrationResponse(true, message.sessionId, message.attendeeId))
        } catch (ignored: Exception) {
            println(ignored)
        }
        latch.countDown()
    }

    fun sendMessage(confirmation: RegistrationResponse) {
        val message = objectMapper.writeValueAsString(confirmation)
        tempalate.convertAndSend(confirmationQueue.name, message)
    }

    fun getLatch(): CountDownLatch {
        return latch
    }
}