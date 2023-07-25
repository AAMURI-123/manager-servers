package com.umerscode.servers.Service;

import com.umerscode.servers.Entity.Servers;
import com.umerscode.servers.Page.PageResponse;
import com.umerscode.servers.Repository.ServerRepo;
import com.umerscode.servers.enumaration.Status;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;

import static com.umerscode.servers.enumaration.Status.*;
import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServerServiceImplTest {

    @Mock
    private ServerRepo serverRepo;

    @InjectMocks
    private ServerServiceImpl underTest;

    private Servers server;

    @BeforeEach
    void setUp() {
         server = new Servers(1l,"192.168.1.58","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");
    }

        @Test
    void itShouldGetAllServersTest(){
        Servers server1 = new Servers("123.22.556.32","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");
        Servers server2 = new Servers("121.22.556.32","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");
        List<Servers> list = Arrays.asList(server1,server2);
            PageResponse pageResponse = new PageResponse(list);
        when(serverRepo.findAll(any(PageRequest.class))).thenReturn(pageResponse);
        //when
            List<Servers> response = underTest.getAllServers(10);

        //then
            assertThat(response.size()).isEqualTo(list.size());
        }
    @Test
    void itShouldGetServerByIdTest(){
        //given
        when(serverRepo.findById(server.getId())).thenReturn(of(server));

        //when
        Optional<Servers> actualResponse = underTest.getServerById(server.getId());

        //then
        assertThat(actualResponse).isNotEmpty();
        assertThat(actualResponse.get()).isEqualTo(server);
    }

    @Test
    void itShouldGetServerByIpAddressTest(){

        when(serverRepo.findByIpAddress(server.getIpAddress())).thenReturn(of(server));

        //when
        Optional<Servers> actualResponse = underTest.getByIpAddress(server.getIpAddress());

        //then
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(serverRepo).findByIpAddress(argumentCaptor.capture());
        String argumentValue = argumentCaptor.getValue();

        assertThat(argumentValue).isEqualTo(server.getIpAddress());
        assertThat(actualResponse).isNotEmpty();
        assertThat(actualResponse.get()).isEqualTo(server);
    }

    @Test
    void itShouldUpdateServerTest(){

        when(serverRepo.findById(server.getId())).thenReturn(of(server));

        //when
        Servers actualResponse = underTest.updateServer(server.getId(),server);

        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(serverRepo).findById(argumentCaptor.capture());
        Long argumentValue = argumentCaptor.getValue();

        assertThat(argumentValue).isEqualTo(server.getId());
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse).isEqualTo(server);
    }

    @Test
    void itShouldPingIfServerIsUpTest() throws IOException {
        //given
         server = new Servers(1l,"8.8.8.8","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");

        when(serverRepo.findByIpAddress(server.getIpAddress())).thenReturn(of(server));

        //when
        Servers actualResponse = underTest.ping(server.getIpAddress());

        //then
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(serverRepo).findByIpAddress(argumentCaptor.capture());
        String argumentValue = argumentCaptor.getValue();

        assertThat(argumentValue).isEqualTo(server.getIpAddress());
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getStatus()).isEqualTo(SERVER_UP);
    }
    @Test
    void itShouldNotPingIfServerIsDownTest() throws IOException {


        when(serverRepo.findByIpAddress(server.getIpAddress())).thenReturn(of(server));

        //when
        Servers actualResponse = underTest.ping(server.getIpAddress());

        //then
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(serverRepo).findByIpAddress(argumentCaptor.capture());
        String argumentValue = argumentCaptor.getValue();

        assertThat(argumentValue).isEqualTo(server.getIpAddress());
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getStatus()).isEqualTo(SERVER_DOWN);
    }

    @Test
    void itShouldCreateServerTest()  {
        when(serverRepo.save(server)).thenReturn(server);

        //when
        Servers actualResponse = underTest.createServer(server);

        //then
        ArgumentCaptor<Servers> argumentCaptor = ArgumentCaptor.forClass(Servers.class);
        verify(serverRepo).save(argumentCaptor.capture());
        Servers argumentValue = argumentCaptor.getValue();

        assertThat(argumentValue).isEqualTo(server);
        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.getIpAddress()).isEqualTo(server.getIpAddress());
    }


    @Test
    void itShouldDeleteServerByIdTest() {

       doNothing().when(serverRepo).deleteById(server.getId());

        //when
        boolean actualResponse = underTest.deleteServerById(server.getId());

        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(serverRepo).deleteById(argumentCaptor.capture());
        Long argumentValue = argumentCaptor.getValue();

        assertThat(argumentValue).isEqualTo(server.getId());
        assertThat(actualResponse).isTrue();
    }
}