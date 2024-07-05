package br.nullexcept.package_tracker.models;

import br.nullexcept.mux.app.Activity;
import br.nullexcept.package_tracker.utils.FileUtils;
import br.nullexcept.koalas.KoalaMap;

import java.io.File;
import java.util.HashMap;

public class Config {
    static final HashMap<String, Order> orders = new HashMap<>();
    private static File CONFIG_FILE;

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(Config::write));
    }

    public static void init(Activity ctx) {
        CONFIG_FILE = new File(ctx.getFilesDir(), "config.db");
        if (CONFIG_FILE.exists()) {
            KoalaMap data = new KoalaMap(FileUtils.readText(CONFIG_FILE));
            if (data.has("orders")) {
                data.getMap("orders").values().forEach((item)->{
                    Order order = new Order((KoalaMap) item);
                    orders.put(order.getId(), order);
                });
            }
        }
    }

    public synchronized static void write() {
        //Log.log("Config", "writing...");
        KoalaMap config = new KoalaMap();
        {
            KoalaMap ordersMap = new KoalaMap();
            for (String key: orders.keySet()) {
                ordersMap.put(key, orders.get(key).serialize());
            }
            config.put("orders", ordersMap);
        }
        try {
            FileUtils.writeText(CONFIG_FILE, config.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
