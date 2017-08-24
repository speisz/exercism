package io.exercism.speisz.etl;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

class Etl {
    Map<String, Integer> transform(Map<Integer, List<String>> old) {
        return old.entrySet().stream()
                .flatMap(flattenValueCollection())
                .map(mapKeyAndValue(identity(), String::toLowerCase))
                .map(flipKeyAndValue())
                .collect(entriesToMap());
    }

    private <K, V> Function<Entry<K, ? extends Collection<V>>, Stream<? extends Entry<K, V>>> flattenValueCollection() {
        return entry -> entry.getValue().stream().map(value -> newEntry(entry.getKey(), value));
    }

    private <K, V> Function<Entry<K, V>, Entry<V, K>> flipKeyAndValue() {
        return entry -> newEntry(entry.getValue(), entry.getKey());
    }

    private <K, V> Collector<Entry<K, V>, ?, Map<K, V>> entriesToMap() {
        return toMap(Entry::getKey, Entry::getValue);
    }

    private <K, V> Function<Entry<K, V>, Entry<K, V>> mapKeyAndValue(
            Function<K, ? extends K> keyMapping,
            Function<V, ? extends V> valueMapping) {
        return entry -> newEntry(keyMapping.apply(entry.getKey()), valueMapping.apply(entry.getValue()));
    }

    private <K, V> Entry<K, V> newEntry(K key, V value) {
        return new SimpleEntry<>(key, value);
    }
}
