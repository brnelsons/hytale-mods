package com.unnatural.hytale.common.storage;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public interface Store<K, V> {
    Optional<V> find(K key);

    void save(K key, V value);

    Stream<K> keys();

    Stream<V> values();

    Stream<Map.Entry<K, V>> entries();

    boolean exists(K key);

    void delete(K key);
}
