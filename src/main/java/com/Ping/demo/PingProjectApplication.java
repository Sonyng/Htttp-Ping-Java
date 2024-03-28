package com.Ping.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.net.InetAddress;

@SpringBootApplication
public class PingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PingProjectApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://127.0.0.1:5500");
			}
		};
	}

	@RestController
	public static class PingController {

		@GetMapping("/ping")
		public String ping(@RequestParam String ip, @RequestParam int port) {
			try {
				InetAddress inetAddress = InetAddress.getByName(ip);
				long startTime = System.currentTimeMillis();
				boolean reachable = inetAddress.isReachable(5000);
				long endTime = System.currentTimeMillis();

				if (reachable) {
					long latency = endTime - startTime;
					return "Latência para " + ip + ":" + port + " - Tempo: " + latency + "ms";
				} else {
					return "Host " + ip + ":" + port + " não está acessível";
				}
			} catch (IOException e) {
				return "Erro ao verificar a latência para " + ip + ":" + port + ": " + e.getMessage();
			}
		}
	}
}
