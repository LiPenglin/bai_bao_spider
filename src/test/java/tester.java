import beans.HotImageListJson;
import core.DataPipeline;
import core.Downloader;
import core.JsonAnalyser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

class tester {

    static Downloader downloader;

    @BeforeAll
    static void setUp() {
        downloader = new Downloader();
    }

    @Test
    void should_get_json_string_when_post_skip_to_seed_url() {
        String html = getHtmlStr(0);
        System.out.println(html);
    }

    @Test
    void should_return_image_src_list_when_parse_json_string() {
        JsonAnalyser analyser = new JsonAnalyser();
        ArrayList<String> list;
        HashSet<String> set = new HashSet<>();
        int skipNumber = 0;
        int count = 0;
        for (int i = 0; i < 100; i++) {
            String str = getHtmlStr(skipNumber);
            list = analyser.parse(str, HotImageListJson.class).getResult().getHtml();
            if (list.size() == 0) break;
            list.forEach(System.out::println);
            set.addAll(list);
            count += list.size();
            skipNumber = count;
        }
        System.out.println(count);
        Assertions.assertEquals(set.size(), count);
    }
    
    @Test
    void should_save_csv_data_to_local() {
        DataPipeline dataHandler = new DataPipeline();
        String htmlStr = getHtmlStr(0);
        JsonAnalyser analyser = new JsonAnalyser();
        dataHandler.saveUrlToLocal(analyser.parse(htmlStr, HotImageListJson.class).getResult().getHtml());
    }

    private String getHtmlStr(int skipNumber) {
        String seedUrl = "http://pic.haibao.com/ajax/image:getHotImageList.json";
        return downloader.get(seedUrl, skipNumber);
    }
}
