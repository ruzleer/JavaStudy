//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Тест 1: Базовая работа ===");
        CustomHashMap<String, Integer> map = new CustomHashMap<>();

        map.put("Apple", 100);
        map.put("Banana", 200);

        System.out.println("Размер карты: " + map.getSize());
        System.out.println("Цена Apple: " + map.get("Apple"));
        System.out.println("Цена Banana: " + map.get("Banana"));

        System.out.println("\n=== Тест 2: Обновление значения ===");
        map.put("Apple", 150);
        System.out.println("Новая цена Apple: " + map.get("Apple"));
        System.out.println("Размер карты (не должен увеличиться): " + map.getSize());

        System.out.println("\n=== Тест 3: Проверка коллизий ===");

        CustomHashMap<BadKey, String> collisionMap = new CustomHashMap<>();

        BadKey key1 = new BadKey("Первый");
        BadKey key2 = new BadKey("Второй");
        BadKey key3 = new BadKey("Третий");

        collisionMap.put(key1, "Данные 1");
        collisionMap.put(key2, "Данные 2");
        collisionMap.put(key3, "Данные 3");

        System.out.println("Размер с коллизиями: " + collisionMap.getSize());
        System.out.println("Получаем второй: " + collisionMap.get(key2));

        System.out.println("\n=== Тест 4: Удаление элемента ===");

        String removedValue = collisionMap.remove(key2);
        System.out.println("Удалено значение: " + removedValue);
        System.out.println("Размер после удаления: " + collisionMap.getSize());
        System.out.println("Пытаемся получить удаленный: " + collisionMap.get(key2));
        System.out.println("Сосед по цепочке (третий) на месте? " + collisionMap.get(key3));
    }

    static class BadKey {
        String name;

        BadKey(String name) {
            this.name = name;
        }

        @Override
        public int hashCode() {
            return 42;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            BadKey badKey = (BadKey) obj;
            return name.equals(badKey.name);
        }

        @Override
        public String toString() {
            return name;
        }
    }

}