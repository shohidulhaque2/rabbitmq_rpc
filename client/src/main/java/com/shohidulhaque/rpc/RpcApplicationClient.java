package com.shohidulhaque.rpc;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class RpcApplicationClient {

	public static void main(String[] args) {
		SpringApplication.run(RpcApplicationClient.class, args);
	}

	@Component
	static class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

		public static final String directExchangeName_reply = "rpc-exchange_reply";
		public static final String queueName_reply = "rpc-queue_reply";

		public static final String directExchangeName_send = "rpc-exchange_send";
		public static final String routingKey_send = "rpc_send";

		@Autowired
		private RabbitTemplate template;

		@Autowired
		private DirectExchange exchange;

		@Bean
		DirectExchange exchange() {
			return new DirectExchange(directExchangeName_send);
		}

		@Override
		public void run(String... args) throws Exception {
			System.out.println("this is the client.");
			System.out.println("Sending name: " + "John");
			template.setReplyTimeout(60000);

			while(true){
				System.out.println("Sending Response To Server");
				Simple simple = new Simple();
				Simple response = (Simple) template.convertSendAndReceive(exchange.getName(), routingKey_send,
						simple);
				System.out.println("Got '" + response + "'");
			}
		}
	}


}
