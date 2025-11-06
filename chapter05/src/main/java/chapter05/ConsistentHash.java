package chapter05;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map;
import chapter05.HashFunction;

public class ConsistentHash<T> {

    private final HashFunction hashFunction;
    private final SortedMap<Integer, T> ring = new TreeMap<>();

    public ConsistentHash(HashFunction hashFunction) {
        this.hashFunction = hashFunction;
    }

    /**
     * 해시 링에 노드를 추가합니다.
     * @param node 추가할 서버 노드
     * @param virtualNodeCount 생성할 가상 노드의 수
     */
    public void add(T node, int virtualNodeCount) {
        // TODO: 가상 노드를 생성하여 해시 링에 추가하는 로직을 구현하세요.
    }

    /**
     * 해시 링에서 노드를 제거합니다.
     * @param node 제거할 서버 노드
     */
    public void remove(T node) {
        // TODO: 해시 링에서 해당 노드와 연관된 모든 가상 노드를 제거하는 로직을 구현하세요.
    }

    /**
     * 주어진 키가 할당될 노드를 찾습니다.
     * @param key 찾고자 하는 데이터의 키
     * @return 키가 할당될 서버 노드 (링이 비어있으면 null 반환)
     */
    public T get(Object key) {
        // TODO: 주어진 키의 해시 값을 계산하고, 해시 링에서 키가 할당될 노드를 찾는 로직을 구현하세요.
        return null;
    }
}