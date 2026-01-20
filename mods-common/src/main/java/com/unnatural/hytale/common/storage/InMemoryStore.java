package com.unnatural.hytale.common.storage;

import javax.annotation.concurrent.ThreadSafe;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@ThreadSafe
public class InMemoryStore<K, V> implements Store<K, V> {

    public static <K, V> InMemoryStore<K, V> create() {
        return new InMemoryStore<>();
    }

    private final Map<K, V> map = new ConcurrentHashMap<>();

    @Override
    public Optional<V> find(K key) {
        return Optional.ofNullable(map.get(key));
    }

    @Override
    public Stream<K> keys() {
        return List.copyOf(map.keySet()).stream();
    }

    @Override
    public Stream<V> values() {
        return List.copyOf(map.values()).stream();
    }

    @Override
    public Stream<Map.Entry<K, V>> entries() {
        return List.copyOf(map.entrySet()).stream();
    }

    @Override
    public void save(K key, V value) {
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
