package com.umerscode.servers.Resource;

import com.umerscode.servers.Entity.Servers;
import com.umerscode.servers.Response.Response;
import com.umerscode.servers.Service.ServerFeatures;
import com.umerscode.servers.Service.ServerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static com.umerscode.servers.enumaration.Status.SERVER_UP;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerController  {

    private final ServerServiceImpl serverService;

    @GetMapping("/getAll")
    public ResponseEntity<Response> getAllServers() {
        return  ResponseEntity.ok(
                Response.builder()
                .timeStamp(now())
                .httpStatus(OK)
                .message("Server Retrieved")
                .data(serverService.getAllServers(10))
                .statusCode(OK.value())
                .build()

        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getServerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                .timeStamp(now())
                .httpStatus(OK)
                .message((serverService.getServerById(id) != null) ?
                        "Successfully retrieved" : "Server with "+ id + "not available")
                .statusCode(OK.value())
                .data(Arrays.asList(serverService.getServerById(id)))
                .build()
        );
                  

    }

    @GetMapping("/ipAddress/{address}")
    public ResponseEntity<Response> getByIpAddress(@PathVariable("address") String address) {
        return ResponseEntity.ok(
               Response.builder()
                .timeStamp(now())
                .httpStatus(OK)
                .message("Successfully retrieved")
                .statusCode(OK.value())
                .data(Arrays.asList(serverService.getByIpAddress(address)))
                .build()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateServer(@PathVariable("id") Long id, @RequestBody Servers servers) {
        return ResponseEntity.ok(
                Response.builder()
                .timeStamp(now())
                .httpStatus(OK)
                .statusCode(OK.value())
                .message("Update Success")
                .data(Arrays.asList(serverService.updateServer(id,servers)))
                .build()
        );
    }

     @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> ping(@PathVariable("ipAddress") String ipAddress) throws IOException {
        return ResponseEntity.ok(
                Response.builder()
                .timeStamp(now())
                .statusCode(OK.value())
                .httpStatus(OK)
                .message((serverService.ping(ipAddress).getStatus().equals(SERVER_UP)) ?
                        "Ping Success" : "Ping failed")
                .data(Arrays.asList(serverService.ping(ipAddress)))
                .build()
        );
    }

    @PostMapping("/add")
    @ResponseStatus(CREATED)
    public ResponseEntity<Response> createServer(@RequestBody Servers servers) {
        return ResponseEntity.ok(
                Response.builder()
                .timeStamp(now())
                .httpStatus(CREATED)
                .statusCode(CREATED.value())
                .message((serverService.createServer(servers) != null)?
                         "Created Successfully": "Not created")
                 .data(Arrays.asList(serverService.createServer(servers)))
                .build()
        );
    }

    @DeleteMapping("/{id}")
    public boolean deleteServerById(@PathVariable("id") Long id) {
        return serverService.deleteServerById(id);
    }
}
