package com.umerscode.servers.Repository;

import com.umerscode.servers.Entity.Servers;
import com.umerscode.servers.enumaration.Status;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.*;

@DataJpaTest(
properties = {
        "spring.datasource.url=jdbc:h2://mem:db;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa",
        "spring.datasource.password=sa",
        "spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        })
class ServerRepoTest {

    @Autowired
    private  ServerRepo underTest;

    @Test
    void itShouldGetServerByIpAddress(){
        //given
        Servers server = new Servers("123.22.556.32","Mac","Pc",
                "16GB", Status.SERVER_UP,"imageUrl");

            underTest.save(server);

        //when
        Optional<Servers> response = underTest.findByIpAddress(server.getIpAddress());

        //then
        assertThat(response).isNotEmpty();
       // assertThat(response.get().getId()).isNotNull();
    }
}