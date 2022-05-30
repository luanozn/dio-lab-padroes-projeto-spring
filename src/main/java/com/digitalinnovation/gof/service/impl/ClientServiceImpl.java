package com.digitalinnovation.gof.service.impl;

import com.digitalinnovation.gof.model.Adress;
import com.digitalinnovation.gof.model.AdressRepository;
import com.digitalinnovation.gof.model.Client;
import com.digitalinnovation.gof.model.ClientRepository;
import com.digitalinnovation.gof.service.ClientService;
import com.digitalinnovation.gof.service.ViaCepService;
import org.apache.tomcat.jni.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AdressRepository addressRepository;

    @Autowired
    private ViaCepService viaCepService;
    @Override
    public Iterable<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        Optional<Client> optional = clientRepository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public void insert(Client client) {
        saveClient(client);
    }

    @Override
    public void update(Long id, Client client) {
        Optional<Client> clientDb = clientRepository.findById(id);
        if(clientDb.isPresent())
            saveClient(client);

    }

    @Override
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    private void saveClient(Client client) {
        String cep = client.getAddress().getCep();
        Adress address = addressRepository.findById(cep).orElseGet(() -> {
            Adress newAddress = viaCepService.consultCep(cep);
            addressRepository.save(newAddress);
            return newAddress;
        });
        client.setAddress(address);
        clientRepository.save(client);
    }
}
