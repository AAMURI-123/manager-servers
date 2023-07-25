package com.umerscode.servers.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umerscode.servers.Entity.Servers;
import com.umerscode.servers.Service.ServerServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static com.umerscode.servers.enumaration.Status.SERVER_DOWN;
import static com.umerscode.servers.enumaration.Status.SERVER_UP;
import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ServerController.class)
@ExtendWith(MockitoExtension.class)
class ServerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServerServiceImpl serverService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void itShouldGetAllServersTest() throws Exception {
        //given
        Servers server1 = new Servers("123.22.556.32","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");
        Servers server2 = new Servers("121.22.556.32","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");
        List<Servers> list = Arrays.asList(server1,server2);

        when(serverService.getAllServers(10)).thenReturn(list);

        //when & then
        mockMvc.perform(get("/server/getAll"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.message").value("Server Retrieved"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    void itShouldGetServerByIdTest() throws Exception {
        //given
        Servers server = new Servers(1l,"123.22.556.32","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");

        when(serverService.getServerById(server.getId())).thenReturn(of(server));

        //when & then
        mockMvc.perform(get("/server/{id}",server.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully retrieved"));

    }


    @Test
    void itShouldUpdateServerTest() throws Exception {
        //given
        Servers server = new Servers(1l,"123.22.556.32","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");

        when(serverService.updateServer(server.getId(),server)).thenReturn(server);

        //when & then
        mockMvc.perform(put("/server/{id}",server.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(server)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Update Success"));

        ArgumentCaptor<Servers> argumentCaptor = ArgumentCaptor.forClass(Servers.class);
        ArgumentCaptor<Long> argumentCaptor2 = ArgumentCaptor.forClass(Long.class);
        verify(serverService).updateServer(argumentCaptor2.capture(),argumentCaptor.capture());
        Servers captorServer = argumentCaptor.getValue();
        Long captorId = argumentCaptor2.getValue();
        assertThat(captorServer.getId()).isEqualTo(server.getId());
        assertThat(captorId).isEqualTo(server.getId());
    }

    @Test
    void itShouldGetServerByIpAddressTest() throws Exception {
        //given
        Servers server = new Servers("123.22.556.32","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");

        when(serverService.getByIpAddress(server.getIpAddress())).thenReturn(of(server));

        //when & then
        mockMvc.perform(get("/server/ipAddress/{address}",server.getIpAddress()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Successfully retrieved"));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(serverService).getByIpAddress(argumentCaptor.capture());
        String captorValue = argumentCaptor.getValue();
        assertEquals(server.getIpAddress(),captorValue);
    }

    @Test
    void itShouldPingIfServerIsUpTest() throws Exception {
        //given
        Servers server = new Servers("8.8.8.8","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");

        when(serverService.ping(server.getIpAddress())).thenReturn(server);

        //when & then
        mockMvc.perform(get("/server/ping/{ipAddress}",server.getIpAddress()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Ping Success"));

    }

    @Test
    void itShouldNotPingIfServerIsDownTest() throws Exception {
        //given
        Servers server = new Servers("192.168.1.12","Mac","Pc",
                "16GB", SERVER_DOWN,"imageUrl");

        when(serverService.ping(server.getIpAddress())).thenReturn(server);

        //when & then
        mockMvc.perform(get("/server/ping/{ipAddress}",server.getIpAddress()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Ping failed"));

    }

    @Test
    void itShouldCreateServerTest() throws Exception {
        //given
        Servers server = new Servers(1l,"123.22.556.32","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");

        when(serverService.createServer(server)).thenReturn(server);

        //when & then
        mockMvc.perform(post("/server/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(server)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Created Successfully"));


    }

    @Test
    void itShouldDeleteServerByIdTest() throws Exception {
        Servers server = new Servers(1l,"123.22.556.32","Mac","Pc",
                "16GB", SERVER_UP,"imageUrl");

       when(serverService.deleteServerById(server.getId())).thenReturn(true);

       //when & then
        mockMvc.perform(delete("/server/{id}",server.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true))
                .andDo(print());

        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(serverService).deleteServerById(argumentCaptor.capture());
        Long captorId = argumentCaptor.getValue();
        assertThat(captorId).isEqualTo(server.getId());
    }
}