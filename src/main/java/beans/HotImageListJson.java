package beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class HotImageListJson {
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @JsonIgnoreProperties({
            "hasMore",
            "ids",
            "isFirstPage",
            "skip"
    })
    public static class Result {
        private int hasMore;
        private String html;
        private ArrayList<Integer> ids;
        private int isFirstPage;
        private int skip;

        public ArrayList<String> getHtml() {
            ArrayList<String> srcList = new ArrayList<>();
            Document document = Jsoup.parse(html);
            Elements images = document.getElementsByTag("img");
            for (Element image : images) {
                String src = image.attr("data-original");
                srcList.add(src);
            }
            return srcList;
        }

        public void setHtml(String html) {
            this.html = html;
        }
    }
}
