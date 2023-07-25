package com.umerscode.servers.Repository;

import com.umerscode.servers.Entity.Servers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerRepo extends JpaRepository<Servers,Long> {
    Optional<Servers> findByIpAddress(String ipAddress);
}
