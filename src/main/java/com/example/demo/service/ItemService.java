package com.example.demo.service;

import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ItemService {
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> findAll() {
        return repository.findAll();
    }

    public Optional<Item> findById(Long id) {
        return repository.findById(id);
    }

    public Item save(Item item) {
        return repository.save(item);
    }

    public Optional<Item> update(Long id, Item item) {
        return repository.findById(id).map(existing -> {
            existing.setName(item.getName());
            existing.setDescription(item.getDescription());
            return repository.save(existing);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
