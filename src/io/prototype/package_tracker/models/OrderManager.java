package io.prototype.package_tracker.models;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    public static List<Order> getAll() {
        return new ArrayList<>(Config.orders.values());
    }

    public static Order get(String id) {
        return Config.orders.get(id);
    }

    public static void register(Order order) {
        if(!Config.orders.containsKey(order.getId())){
            Config.orders.put(order.getId(), order);
        }
    }

    public static void remove(Order order) {
        Config.orders.remove(order.getId());
    }

    public static boolean has(String id) {
        return Config.orders.containsKey(id);
    }
}
