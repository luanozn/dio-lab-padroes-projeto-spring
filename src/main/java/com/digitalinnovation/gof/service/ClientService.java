package com.digitalinnovation.gof.service;

import com.digitalinnovation.gof.model.Client;

public interface ClientService {

    Iterable<Client> findAll();

    Client findById();

    void insert(Client client);

    void update(Long id, Client client);

    void delete(Long id);

}
