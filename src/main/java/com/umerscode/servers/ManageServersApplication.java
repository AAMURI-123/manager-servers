package com.umerscode.servers;

import com.umerscode.servers.Entity.Servers;
import com.umerscode.servers.Repository.ServerRepo;
import com.umerscode.servers.enumaration.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

import static com.umerscode.servers.enumaration.Status.SERVER_DOWN;

@SpringBootApplication
public class ManageServersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManageServersApplication.class, args);
	}

//	@Bean
//	CommandLineRunner commandLineRunner(ServerRepo repo){
//		return args -> {
//			Servers server1 = new Servers(null,"192.23.4.182","Ubuntu linux","Desktop",
//					"32 GB", SERVER_DOWN,"imageUrl");
//			Servers server2 = new Servers(null,"192.23.4.183","Ubuntu linux","Desktop",
//					"16 GB", SERVER_DOWN,"imageUrl");
//			Servers server3 = new Servers(null,"192.23.4.184","Ubuntu linux","Desktop",
//					"8 GB", SERVER_DOWN,"imageUrl");
//			Servers server4 = new Servers(null,"192.23.4.185","Ubuntu linux","Desktop",
//					"12 GB", SERVER_DOWN,"imageUrl");
//		repo.saveAll(Arrays.asList(server1,server2,server3,server4));
//		};


}
