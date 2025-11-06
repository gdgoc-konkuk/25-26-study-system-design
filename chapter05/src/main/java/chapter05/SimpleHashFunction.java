package chapter05;

public class SimpleHashFunction implements HashFunction {
    @Override
    public int hash(Object obj) {
        return obj.toString().hashCode();
    }
}
