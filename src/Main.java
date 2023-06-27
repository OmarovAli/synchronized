import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    final static int ROUTS = 100;
    final static int THREADS = 1000;
    final static String text = "RLRFR";


    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < ROUTS; i++) {
            new Thread(() -> {
                String route = generateRoute(text, ROUTS);
                int frq = (int) route.chars().filter(ch -> ch == 'R').count();
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(frq)) {
                        sizeToFreq.put(frq, sizeToFreq.get(frq) + 1);
                    } else {
                        sizeToFreq.put(frq, 1);
                    }
                }
            }).start();
        }


        Map.Entry<Integer, Integer> max = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.println("Самое частое количество повторений" + max.getKey() + "(встретились" + max.getValue() + "раз)");

        System.out.println("Другие размеры: " );
        sizeToFreq
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .forEach(e -> System.out.println(" . " + e.getKey() + " ( " + e.getValue() + " раз"));
    }
    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}