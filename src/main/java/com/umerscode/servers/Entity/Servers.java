package com.umerscode.servers.Entity;

import com.umerscode.servers.enumaration.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.*;

@Entity
@Data
@Table
@AllArgsConstructor
@NoArgsConstructor


public class Servers {

    public Servers(@NotEmpty(message = "IpAddress can't be empty or null") String ipAddress, String name, String type, String memory, Status status, String imageUrl) {
        this.ipAddress = ipAddress;
        this.name = name;
        this.type = type;
        this.memory = memory;
        this.status = status;
        this.imageUrl = imageUrl;
    }

    @Id
    @SequenceGenerator(name = "Sequence_generator",sequenceName = "Sequence_generator",
                        allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE,generator = "Sequence_generator")
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "IpAddress can't be empty or null")
    private String ipAddress;
    private String name;
    private String type;
    private String memory;
    private Status status;
    private String imageUrl;

}
