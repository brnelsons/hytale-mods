package com.unnatural.hytale.common.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class InMemoryStore<K, V> implements Store<K, V> {

    public static <K, V> InMemoryStore<K, V> create() {
        return new InMemoryStore<>();
    }

    private final Map<K, V> map = new HashMap<>();

    @Override
    public Optional<V> find(K key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public Stream<K> keys() {
        return map.keySet().stream();
    }

    @Override
    public Stream<V> values() {
        return map.values().stream();
    }

    @Override
    public Stream<Map.Entry<K, V>> entries() {
        return map.entrySet().stream();
    }

    @Override
    public void save(K key, V value) {
        // TODO do something if a value was already stored?
        map.put(key, value);
    }

    @Override
    public boolean exists(K key) {
        return map.containsKey(key);
    }

    @Override
    public void delete(K key) {
        map.remove(key);
    }
}
