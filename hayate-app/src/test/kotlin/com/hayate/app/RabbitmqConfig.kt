package com.hayate.app

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


/**
 * Created by Flame on 2021/04/26.
 */

@Configuration
@EnableRabbit
class RabbitmqConfig {

    companion object {
        const val QUEUE_A = "queue.a"
        const val QUEUE_B = "queue.b"
        const val QUEUE_C = "queue.c"
        const val FANOUT_EXCHANGE_NAME = "fanout.exchange.name"
        const val TOPIC_EXCHANGE_NAME = "topic.exchange.name"
        const val HEADER_EXCHANGE_NAME = "header.exchange.name"
    }

    @Bean(FANOUT_EXCHANGE_NAME)
    fun fanoutExchange(): FanoutExchange {
        return ExchangeBuilder.fanoutExchange(FANOUT_EXCHANGE_NAME)
            .durable(true)
            .build()
    }

    @Bean(TOPIC_EXCHANGE_NAME)
    fun topicExchange(): TopicExchange {
        return ExchangeBuilder.topicExchange(TOPIC_EXCHANGE_NAME)
            .durable(true)
            .build()
    }

    @Bean(HEADER_EXCHANGE_NAME)
    fun headerExchange(): HeadersExchange {
        return ExchangeBuilder.headersExchange(HEADER_EXCHANGE_NAME)
            .durable(true)
            .build()
    }

    @Bean(QUEUE_A)
    fun queueA(): Queue {
        return Queue(QUEUE_A)
    }

    @Bean(QUEUE_B)
    fun queueB(): Queue {
        return Queue(QUEUE_B)
    }

    @Bean(QUEUE_C)
    fun queueC(): Queue {
        return Queue(QUEUE_C)
    }

    @Bean
    fun bindFanoutA(
        @Qualifier(QUEUE_A) queue: Queue,
        @Qualifier(FANOUT_EXCHANGE_NAME) exchange: FanoutExchange
    ): Binding {
        return BindingBuilder.bind(queue)
            .to(exchange)
    }

    @Bean
    fun bindFanoutB(
        @Qualifier(QUEUE_B) queue: Queue,
        @Qualifier(FANOUT_EXCHANGE_NAME) exchange: FanoutExchange
    ): Binding {
        return BindingBuilder.bind(queue)
            .to(exchange)
    }

    @Bean
    fun bindTopicA(
        @Qualifier(QUEUE_A) queue: Queue,
        @Qualifier(TOPIC_EXCHANGE_NAME) exchange: TopicExchange
    ): Binding {
        return BindingBuilder.bind(queue)
            .to(exchange)
            .with("topic1.#")
    }

    @Bean
    fun bindTopicB(
        @Qualifier(QUEUE_B) queue: Queue,
        @Qualifier(TOPIC_EXCHANGE_NAME) exchange: TopicExchange
    ): Binding {
        return BindingBuilder.bind(queue)
            .to(exchange)
            .with("topic1.#")
    }

    @Bean
    fun bindTopicC(
        @Qualifier(QUEUE_C) queue: Queue,
        @Qualifier(TOPIC_EXCHANGE_NAME) exchange: TopicExchange
    ): Binding {
        return BindingBuilder.bind(queue)
            .to(exchange)
            .with("topic2.#")
    }

    @Bean
    fun bindHeaderA(): Binding {
        val headerMap = hashMapOf<String, Any>()
        headerMap["header_a1"] = "header_a1"
        headerMap["header_a2"] = "header_a2"
        return BindingBuilder.bind(queueA())
            .to(headerExchange())
            .whereAll(headerMap)
            .match()
    }

    @Bean
    fun bindHeaderB(): Binding {
        val headerMap = hashMapOf<String, Any>()
        headerMap["header_a1"] = "header_a1"
        headerMap["header_b2"] = "header_b2"
        return BindingBuilder.bind(queueB())
            .to(headerExchange())
            .whereAny(headerMap)
            .match()
    }
}