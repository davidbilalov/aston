public class Main {
    public static void main(String[] args) {

        MyHashMap<String, Integer> map = new MyHashMap<>(2, 0.75f, 0.25f);
        map.put("apple", 1);
        map.put("banana", 2);
        map.put("apple", 3);
        System.out.println(map.get("apple"));   // 1
        map.remove("apple");
        System.out.println(map.size());
        System.out.println(map.get("apple"));

    }
}