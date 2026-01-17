package org.example.mytestproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mytestproject.entity.Client;
import org.example.mytestproject.entity.DTO.ClientDTO;
import org.example.mytestproject.repository.ClientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/testClients")
public class TestClientsController {

    private final ClientRepository ClientRepository;
    private final ClientRepository clientRepository;

    @GetMapping("/getAllJoin")
    public List<ClientDTO> getClients() {
        List<Client> clients = clientRepository.findAllClientsFoJoinFeatch();
        return clients.stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/allClientGraph")
    public List<ClientDTO> getAllClientsFoGraph() {
        List<Client> clients = clientRepository.findAllClientsFoEntityGraph();

        return clients.stream()
                .map(ClientDTO::new)
                .collect(Collectors.toList());
    }
}
