package ru.liga.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.liga.common.repository.OrderItemRepository;

@EnableRabbit
@Configuration
public class RabbitConfig {
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");
        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");
        return cachingConnectionFactory;
    }

    //AmqpAdmin занимается обслуживанием очередей, обменника, сообщений
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    //RabbitTemplate основной класс для отправки сообщения, так же имеет гибкие настройки, такие как
    //явное указание типа конвертации.
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Declarables myQueue() {
        Queue queueDirectFirst = new Queue("restaurants", false);
        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(queueDirectFirst, directExchange,
                BindingBuilder.bind(queueDirectFirst).to(directExchange).with("restaurants"));
    }
}
