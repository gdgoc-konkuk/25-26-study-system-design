package chapter05;

import chapter05.ConsistentHash;
import chapter05.ServerNode;
import chapter05.HashFunction;
import chapter05.SimpleHashFunction;

public class Application {

    public static void main(String[] args) {
        HashFunction hashFunction = new SimpleHashFunction();

        ConsistentHash<ServerNode> consistentHash = new ConsistentHash<>(hashFunction);

        ServerNode server1 = new ServerNode("ServerA", "192.168.1.100", 8080);
        ServerNode server2 = new ServerNode("ServerB", "192.168.1.101", 8080);
        ServerNode server3 = new ServerNode("ServerC", "192.168.1.102", 8080);

        consistentHash.add(server1, 100);
        consistentHash.add(server2, 100);
        consistentHash.add(server3, 100);

        System.out.println("초기 서버 노드 추가 완료.");

        String dataKey1 = "user123";
        ServerNode targetServer1 = consistentHash.get(dataKey1);
        if (targetServer1 != null) {
            System.out.println("Key '" + dataKey1 + "' maps to " + targetServer1.getId());
            targetServer1.connect();
        }

        String dataKey2 = "productABC";
        ServerNode targetServer2 = consistentHash.get(dataKey2);
        if (targetServer2 != null) {
            System.out.println("Key '" + dataKey2 + "' maps to " + targetServer2.getId());
            targetServer2.connect();
        }

        System.out.println("\nServerB 제거 중...");
        consistentHash.remove(server2);
        System.out.println("ServerB 제거 완료.");

        ServerNode newTargetServer1 = consistentHash.get(dataKey1);
        if (newTargetServer1 != null) {
            System.out.println("Key '" + dataKey1 + "' now maps to " + newTargetServer1.getId());
            newTargetServer1.connect();
        }
    }
}
