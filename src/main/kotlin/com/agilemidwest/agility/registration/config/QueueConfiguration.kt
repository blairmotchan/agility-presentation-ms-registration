package com.agilemidwest.agility.registration.config

import com.agilemidwest.agility.registration.receiver.QueueService
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
open class QueueConfiguration {
    private val topicExchangeName = "agility-exchange"
    private val registrationQueueName = "registration"
    private val confirmationQueueName = "confirmation"

    @Bean("registrationQueue")
    open fun registrationQueue(): Queue {
        return Queue(registrationQueueName, true)
    }

    @Bean("confirmationQueue")
    open fun confirmationQueue(): Queue {
        return Queue(confirmationQueueName, true)
    }

    @Bean
    open fun exchange(): TopicExchange {
        return TopicExchange(topicExchangeName)
    }

    @Bean
    open fun registrationBinding(@Qualifier("registrationQueue") registrationQueue: Queue,
                                 exchange: TopicExchange): Binding {
        return BindingBuilder.bind(registrationQueue).to(exchange).with("agility.registration.#")
    }

    @Bean
    open fun confirmationBinding(@Qualifier("confirmationQueue") confirmationQueue: Queue,
                                 exchange: TopicExchange): Binding {
        return BindingBuilder.bind(confirmationQueue).to(exchange).with("agility.confirmation.#")
    }

    @Bean
    open fun listenerAdapter(queueService: QueueService): MessageListenerAdapter {
        return MessageListenerAdapter(queueService, "receiveMessage")
    }

    @Bean
    open fun container(connectionFactory: ConnectionFactory,
                       listenerAdapter: MessageListenerAdapter): SimpleMessageListenerContainer {
        val container = SimpleMessageListenerContainer(connectionFactory)
        container.setQueueNames(registrationQueueName)
        container.setMessageListener(listenerAdapter)

        return container
    }
}