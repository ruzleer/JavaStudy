public class CustomHashMap<K, V>{

    CustomNode <K,V> [] table;
    int size;
    int threshold;

    public int getSize() {
        return size;
    }

    public CustomHashMap(){
        this.table = new CustomNode[16];
        this.threshold = (int)(16 * 0.75);
    }

    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public V get(K key) {
        int h = hash(key);
        int index = Math.abs(h) % table.length;
        CustomNode<K, V> bucket = table[index];

        while(bucket != null){
            if(bucket.hash == h && (bucket.key == key || (key != null && key.equals(bucket.key)))){
                return bucket.getValue();
            }
            bucket = bucket.next;
        }
        return null;
    }

    public V put(K key, V value) {
        int h = hash(key);
        int index = Math.abs(h) % table.length;
        CustomNode<K, V> bucket = table[index];

        if(bucket == null){
            table[index] = new CustomNode<>(h, key, value, null);
            size++;
            return null;
        }

        CustomNode<K,V> prevBucket = null;
        while(bucket != null){
            if(bucket.hash == h && (bucket.key == key || (key != null && key.equals(bucket.key)))){
                V oldValue = bucket.value;
                bucket.value = value;
                return oldValue;
            }
            prevBucket = bucket;
            bucket = bucket.next;
        }
        prevBucket.next = new CustomNode<>(h, key, value, null);
        size++;
        checkResize();

        return null;
    }

    public V remove(K key) {
        int h = hash(key);
        int index = Math.abs(h) % table.length;
        CustomNode<K, V> bucket = table[index];
        CustomNode<K, V> prevBucket = null;

        while(bucket != null){
            if(bucket.hash == h && (bucket.key == key || (key != null && key.equals(bucket.key)))){
                V oldValue = bucket.value;

                if(prevBucket == null){
                    table[index] = bucket.next;
                }
                else{
                    prevBucket.next = bucket.next;
                }
                size--;
                return oldValue;
            }
            prevBucket = bucket;
            bucket = bucket.next;
        }
        return null;
    }

    private void checkResize() {
        if (size >= threshold) {
            resize();
        }
    }

    private void resize() {
        CustomNode<K, V>[] oldTable = table;
        table = new CustomNode[oldTable.length * 2];
        threshold = (int) (table.length * 0.75);
        size = 0;

        for (CustomNode<K, V> headNode : oldTable) {
            while (headNode != null) {
                put(headNode.key, headNode.value);
                headNode = headNode.next;
            }
        }
    }

    static class CustomNode<K, V> {
        final int hash;
        final K key;
        V value;
        CustomNode <K, V> next;

        CustomNode(int hash, K key, V value, CustomNode<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey(){
            return key;
        }

        public V getValue(){
            return value;
        }

        public CustomNode<K, V> getNext(){
            return next;
        }

        public String toString() {
            return "{" + key + ", " + value + "}";
        }
    }
}

