package com.umerscode.servers.Service;

import com.umerscode.servers.Entity.Servers;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Optional;

public interface ServerFeatures {
    public List<Servers> getAllServers(int limit);
    public Optional<Servers> getServerById(Long id);
    public Optional<Servers> getByIpAddress(String ipAddress);
    public Servers updateServer(Long id, Servers servers);
    public Servers ping(String ipAddress) throws IOException;
    public Servers createServer(Servers servers);
    public boolean deleteServerById(Long id);
}
