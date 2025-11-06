package chapter05.test;

import chapter05.ConsistentHash;
import chapter05.ServerNode;
import chapter05.HashFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

public class ConsistentHashTest {

    private ConsistentHash<ServerNode> consistentHash;
    private TestHashFunction hashFunction;

    private static class TestHashFunction implements HashFunction {
        private static final int MAX_HASH = 100000; // 해시 값 범위
        /**
        * 간단한 해시 함수 
        * 문자열의 hashCode를 이용하되, MAX_HASH 범위 내로 매핑
        */
        @Override
        public int hash(Object obj) {
            return Math.abs(obj.toString().hashCode()) % MAX_HASH;
        }
    }

    @BeforeEach
    void setUp() {
        hashFunction = new TestHashFunction();
        consistentHash = new ConsistentHash<>(hashFunction);
    }

    @Test
    @DisplayName("노드 추가 테스트")
    void testAddNode() {
        ServerNode node1 = new ServerNode("Server1", "192.168.0.1", 8080);
        int virtualNodeCount = 3;
        consistentHash.add(node1, virtualNodeCount);

        assertNotNull(consistentHash.get("someKey"));
    }

    @Test
    @DisplayName("노드 제거 테스트")
    void testRemoveNode() {
        ServerNode node1 = new ServerNode("Server1", "192.168.0.1", 8080);
        consistentHash.add(node1, 3);
        assertNotNull(consistentHash.get("someKey"));

        consistentHash.remove(node1);
        assertNull(consistentHash.get("someKey"));

        ServerNode nonExistentNode = new ServerNode("NonExistent", "192.168.0.99", 8080);
        consistentHash.remove(nonExistentNode);
        assertNull(consistentHash.get("someKey"));
    }

    @Test
    @DisplayName("키에 해당하는 노드 조회 테스트")
    void testGetNode() {
        ServerNode node1 = new ServerNode("Server1", "192.168.0.1", 8080);
        ServerNode node2 = new ServerNode("Server2", "192.168.0.2", 8080);
        consistentHash.add(node1, 3);
        consistentHash.add(node2, 3);

        assertNotNull(consistentHash.get("key1"));
        assertNotNull(consistentHash.get("key2"));
        assertNotNull(consistentHash.get("key3"));
    }

    @Test
    @DisplayName("링이 비어있을 때 노드 조회 테스트")
    void testGetNodeWhenRingIsEmpty() {
        assertNull(consistentHash.get("someKey"));
    }

    @Test
    @DisplayName("노드 분배 테스트")
    void testNodeDistribution() {
        ServerNode node1 = new ServerNode("Server1", "192.168.0.1", 8080);
        ServerNode node2 = new ServerNode("Server2", "192.168.0.2", 8080);
        ServerNode node3 = new ServerNode("Server3", "192.168.0.3", 8080);

        consistentHash.add(node1, 100);
        consistentHash.add(node2, 100);
        consistentHash.add(node3, 100);

        String[] keys = {"key1", "key2", "key3", "key4", "key5", "key6", "key7", "key8", "key9", "key10"};

        for (String key : keys) {
            ServerNode server = consistentHash.get(key);
            assertNotNull(server, "Key " + key + " should be mapped to a server");
            assertTrue(server.equals(node1) || server.equals(node2) || server.equals(node3));
        }
    }

    @Test
    @DisplayName("노드 제거 후 키 재매핑 테스트")
    void testKeyRemappingAfterNodeRemoval() {
        ServerNode node1 = new ServerNode("Server1", "192.168.0.1", 8080);
        ServerNode node2 = new ServerNode("Server2", "192.168.0.2", 8080);
        consistentHash.add(node1, 100);
        consistentHash.add(node2, 100);

        String testKey = "keyToRemap";
        ServerNode initialNode = consistentHash.get(testKey);
        assertNotNull(initialNode);

        consistentHash.remove(node1);

        ServerNode remappedNode = consistentHash.get(testKey);
        assertNotNull(remappedNode, "Key should be remapped to another node, not null");
        assertFalse(remappedNode.equals(node1), "Key should not be mapped to the removed node");
    }
}
