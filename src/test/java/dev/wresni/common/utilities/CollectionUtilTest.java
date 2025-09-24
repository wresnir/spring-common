package dev.wresni.common.utilities;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class CollectionUtilTest {
    @Test
    void isBlank() {
        String[] array = null;
        assertTrue(CollectionUtil.isBlank(array));
        array = new String[]{};
        assertTrue(CollectionUtil.isBlank(array));
        array = new String[]{null};
        assertFalse(CollectionUtil.isBlank(array));
        array[0] = "value";
        assertFalse(CollectionUtil.isBlank(array));

        Collection<String> collection = null;
        assertTrue(CollectionUtil.isBlank(collection));
        collection = new ArrayList<>();
        assertTrue(CollectionUtil.isBlank(collection));
        collection.add(null);
        assertFalse(CollectionUtil.isBlank(collection));
        collection.clear();
        collection.add("value");
        assertFalse(CollectionUtil.isBlank(collection));

        Map<String, String> map = null;
        assertTrue(CollectionUtil.isBlank(map));
        map = new HashMap<>();
        assertTrue(CollectionUtil.isBlank(map));
        map.put(null, null);
        assertFalse(CollectionUtil.isBlank(map));
        map.put("key", null);
        assertFalse(CollectionUtil.isBlank(map));
        map.put("key2", "value");
        assertFalse(CollectionUtil.isBlank(map));

        Page<String> page = null;
        assertTrue(CollectionUtil.isBlank(page));
        page = new PageImpl<>(new ArrayList<>());
        assertTrue(CollectionUtil.isBlank(page));
        List<String> list = new ArrayList<>();
        list.add(null);
        page = new PageImpl<>(list);
        assertFalse(CollectionUtil.isBlank(page));
        list.clear();
        list.add("value");
        page = new PageImpl<>(list);
        assertFalse(CollectionUtil.isBlank(page));
    }

    @Test
    void nonBlank() {
        String[] array = null;
        assertFalse(CollectionUtil.nonBlank(array));
        array = new String[]{};
        assertFalse(CollectionUtil.nonBlank(array));
        array = new String[]{null};
        assertTrue(CollectionUtil.nonBlank(array));
        array[0] = "value";
        assertTrue(CollectionUtil.nonBlank(array));

        Collection<String> collection = null;
        assertFalse(CollectionUtil.nonBlank(collection));
        collection = new ArrayList<>();
        assertFalse(CollectionUtil.nonBlank(collection));
        collection.add(null);
        assertTrue(CollectionUtil.nonBlank(collection));
        collection.clear();
        collection.add("value");
        assertTrue(CollectionUtil.nonBlank(collection));

        Map<String, String> map = null;
        assertFalse(CollectionUtil.nonBlank(map));
        map = new HashMap<>();
        assertFalse(CollectionUtil.nonBlank(map));
        map.put(null, null);
        System.out.println(map);
        System.out.println(CollectionUtil.isBlank(map));
        assertTrue(CollectionUtil.nonBlank(map));
        map.put("key", null);
        assertTrue(CollectionUtil.nonBlank(map));
        map.put("key2", "value");
        assertTrue(CollectionUtil.nonBlank(map));

        Page<String> page = null;
        assertFalse(CollectionUtil.nonBlank(page));
        page = new PageImpl<>(new ArrayList<>());
        assertFalse(CollectionUtil.nonBlank(page));
        List<String> list = new ArrayList<>();
        list.add(null);
        page = new PageImpl<>(list);
        assertTrue(CollectionUtil.nonBlank(page));
        list.clear();
        list.add("value");
        page = new PageImpl<>(list);
        assertTrue(CollectionUtil.nonBlank(page));
    }

    @Test
    void doIfNonBlank() {
        Consumer<Collection<String>> consumer = collection -> collection.add("newvalue");

        Collection<String> collection = null;
        CollectionUtil.doIfNonBlank(collection, consumer);
        assertTrue(CollectionUtil.isBlank(collection));

        collection = new ArrayList<>();
        CollectionUtil.doIfNonBlank(collection, consumer);
        assertTrue(CollectionUtil.isBlank(collection));

        collection.add("value");
        CollectionUtil.doIfNonBlank(collection, consumer);
        assertTrue(CollectionUtil.nonBlank(collection));
        assertTrue(collection.contains("newvalue"));
    }

    @Test
    void toMap() {
        Function<Integer, Integer> powFunction = i -> (int) Math.pow(2, i);
        Collection<Integer> collection = new ArrayList<>();
        collection.add(1);
        collection.add(2);

        Map<Integer, Integer> map = CollectionUtil.toMap(collection, powFunction);
        System.out.println(map);
        map.forEach((k, v) -> assertEquals(k, powFunction.apply(v)));

        AtomicInteger atomicInteger = new AtomicInteger(1);
        Function<String, String> valueFunction = s -> s + atomicInteger.getAndIncrement();
        Collection<String> stringCollection = new ArrayList<>();
        stringCollection.add("a");
        stringCollection.add("a");

        Map<String, String> stringMap = CollectionUtil.toMap(stringCollection, Function.identity(), valueFunction);
        assertTrue(CollectionUtil.nonBlank(stringMap));
        assertEquals(1, stringMap.size());
        assertEquals("a1", stringMap.get("a"));

        atomicInteger.set(1);
        Map<String, String> stringMap2 = CollectionUtil.toMap(stringCollection, Function.identity(), valueFunction, FunctionUtil.replace());
        assertTrue(CollectionUtil.nonBlank(stringMap2));
        assertEquals(1, stringMap2.size());
        assertEquals("a2", stringMap2.get("a"));

        Map<String, String> stringMap3 = CollectionUtil.toMap(null, Function.identity());
        assertTrue(Objects.nonNull(stringMap3));
        assertTrue(CollectionUtil.isBlank(stringMap3));

        Map<String, String> stringMap4 = CollectionUtil.toMap(new ArrayList<>(), Function.identity());
        assertTrue(Objects.nonNull(stringMap4));
        assertTrue(CollectionUtil.isBlank(stringMap4));
    }
}