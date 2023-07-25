package com.umerscode.servers.Service;

import com.umerscode.servers.Entity.Servers;
import com.umerscode.servers.Repository.ServerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import java.util.Optional;

import static com.umerscode.servers.enumaration.Status.SERVER_DOWN;
import static com.umerscode.servers.enumaration.Status.SERVER_UP;
import static java.util.Map.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ServerServiceImpl implements ServerFeatures {

    private final ServerRepo repository;

    @Override
    public List<Servers> getAllServers(int limit) {

        return repository.findAll(PageRequest.of(0,20)).getContent();
    }

    @Override
    public Optional<Servers> getServerById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Servers> getByIpAddress(String ipAddress) {
        return repository.findByIpAddress(ipAddress);
    }

    @Override
    public Servers updateServer(Long id, Servers servers) {
            Servers server = getServerById(id).get();
            server.setIpAddress(servers.getIpAddress());
            server.setMemory(servers.getMemory());
            server.setName(servers.getName());
            server.setType(servers.getType());
        return server;
    }

    @Override
    public Servers ping(String ipAddress) throws IOException {
       Optional<Servers> server = getByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(ipAddress);
       server.get().setStatus((address.isReachable(10000) ? SERVER_UP : SERVER_DOWN));
       return server.get();
    }

    @Override
    public Servers createServer(Servers servers) {
        return repository.save(servers);
    }

    @Override
    public boolean deleteServerById(Long id) {
        repository.deleteById(id);
        return true;
    }
}
