package core;

import common.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;

public class DataPipeline {
    private String path;

    public DataPipeline() {
        path = Configuration.URL_DATA_PATH;
    }

    public void saveUrlToLocal(HashSet<String> urlStorage) {
        Path path = Paths.get(this.path);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            Path filePath = Paths.get(path + "/" + System.currentTimeMillis() + ".dat");
            for (String url : urlStorage) {
                Files.write(filePath,
                        (url + "\n").getBytes(),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
