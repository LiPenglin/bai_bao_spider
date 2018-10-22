package core;

import beans.HotImageListJson;
import common.Configuration;

import java.util.ArrayList;
import java.util.HashSet;

public class Engine {


    public static class Producer {
        Downloader httpClient = new Downloader();
        JsonAnalyser analyser = new JsonAnalyser();

        HashSet<String> produce() {
            HashSet<String> urlStorage = new HashSet<>();
            ArrayList<String> cache;
            int counter = 0;

            do {
                String htmlStr = httpClient.get(Configuration.SEED_URL, counter);
                cache = analyser
                        .parse(htmlStr, HotImageListJson.class)
                        .getResult()
                        .getHtml();
                if (cache.size() == 0) {
                    break;
                }
                urlStorage.addAll(cache);
                counter += cache.size();
            } while (true);
            return urlStorage;
        }
    }

    public static class Consumer {
        private DataPipeline dataHandler = new DataPipeline();

        void consume(HashSet<String> urlStorage) {
            dataHandler.saveUrlToLocal(urlStorage);
        }
    }

    public void run() {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        consumer.consume(producer.produce());
    }
}
