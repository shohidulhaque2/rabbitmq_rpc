package com.shohidulhaque.rpc;

import java.util.Scanner;
import lombok.SneakyThrows;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RpcApplicationServer {

	public static final String directExchangeName = "rpc-exchange_send";
	public static final String queueName = "rpc-queue_send";
	public static final String routingKey = "rpc_send";



	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(directExchangeName);
	}

	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingKey);
	}

	@Component
	static class CommandLineRunner implements org.springframework.boot.CommandLineRunner {
			@Override
			public void run(String... args) throws Exception {
				System.out.println("This is the server");
				System.out.println("Waiting...");
				new Scanner(System.in).nextLine();
			}
	}

		@Component
		static
		class RpcServer {

			@SneakyThrows
			@RabbitListener(queues = queueName)
			public Simple LongRunningTask(Simple name) {
				System.out.println("This is the server. Received: " + name);
				//Long running processing
				Thread.sleep(1000);
				System.out.println("This is the server. returning " + name);
				return name;
			}
	}
	public static void main(String[] args) {
		SpringApplication.run(RpcApplicationServer.class, args);
	}

}
