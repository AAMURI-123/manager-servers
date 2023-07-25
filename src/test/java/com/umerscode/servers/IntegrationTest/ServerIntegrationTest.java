package com.umerscode.servers.IntegrationTest;

import com.umerscode.servers.Entity.Servers;
import com.umerscode.servers.Repository.ServerRepo;
import com.umerscode.servers.Response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.umerscode.servers.enumaration.Status.SERVER_UP;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost:" ;

    @Autowired
    private  static RestTemplate restTemplate;

    @Autowired
    private ServerRepo serverRepo;

    private Servers server;

    @BeforeAll
    static void beforeAll() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl + port + "/server";
         server = new Servers("192.168.1.58","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");
        serverRepo.save(server);
    }

    @AfterEach
    void tearDown() {
        serverRepo.deleteAll();

    }

    @Test
    void itShouldCreateServerTest(){
        //given
         Servers server1 = new Servers("192.168.1.55","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");

         //when
        Response responseServers = restTemplate.postForObject(baseUrl + "/add", server1, Response.class);
         //then
       assertThat(responseServers.getHttpStatus()).isEqualTo(CREATED);
        assertThat(responseServers.getData().size()).isEqualTo(1);

    }

    @Test
    void itShouldGetAllServer(){

        Servers server2 = new Servers("8.8.8.8","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");
        serverRepo.save(server2);

        //when
        Response response = restTemplate.getForObject(baseUrl + "/getAll", Response.class);

        //then
        assertThat(response.getHttpStatus()).isEqualTo(OK);
        assertThat(response.getData().size()).isEqualTo(2);
    }

    @Test
    void itShouldGetServerById(){

        //when
        Response response = restTemplate.getForObject(baseUrl + "/" + server.getId(), Response.class);

        //then
        assertThat(response.getHttpStatus()).isEqualTo(OK);
        assertThat(response.getData().size()).isNotNull();

    }

    @Test
    void itShouldGetServerByIpAddress(){

        //when
        Response response = restTemplate.getForObject(baseUrl + "/ipAddress" +"/"+ server.getIpAddress(), Response.class);

        //then
        assertThat(response.getHttpStatus()).isEqualTo(OK);
        assertThat(response.getData().size()).isNotNull();
    }

    @Test
    void itShouldUpdateServerTest(){
        server.setType("Desktop");
        server.setMemory("32GB");

        //when
        restTemplate.put(baseUrl+"/"+server.getId(),server,Response.class);
        Servers serverFromDB = serverRepo.findById(server.getId()).get();

        //then
        assertThat(serverFromDB.getType()).isEqualTo("Desktop");
        assertThat(serverFromDB.getMemory()).isEqualTo("32GB");
    }

    @Test
    void itShouldPingIfServerIsUpTest(){
        //given
        server = new Servers("8.8.8.8","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");
        serverRepo.save(server);
        //when
        Response response = restTemplate.getForObject(baseUrl + "/ping/" + server.getIpAddress(), Response.class);

        //then
        assertThat(response.getHttpStatus()).isEqualTo(OK);
        assertThat(response.getMessage()).isEqualTo("Ping Success");
    }

    @Test
    void itShouldNotPingIfServerIsDownTest(){

        //when
        Response response = restTemplate.getForObject(baseUrl + "/ping/" + server.getIpAddress(), Response.class);

        //then
        assertThat(response.getHttpStatus()).isEqualTo(OK);
        assertThat(response.getMessage()).isEqualTo("Ping failed");
    }

    @Test
    void itShouldDeleteServerByIdTest(){

        //when
        restTemplate.delete(baseUrl + "/" + server.getId());
        Optional<Servers> serverFromDb = serverRepo.findById(server.getId());
        //then
        assertThat(serverFromDb).isEmpty();
    }
}
