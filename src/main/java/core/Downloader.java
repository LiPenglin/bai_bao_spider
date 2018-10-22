package core;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

public class Downloader {
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private ArrayList<NameValuePair> params = new ArrayList<>();

    public String get(String seedUrl, Integer skipNumber) {
        HttpPost request = new HttpPost(seedUrl);
        params.add(new BasicNameValuePair("skip", String.valueOf(skipNumber)));
        try {
            request.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            params.clear();
        }
        return null;
    }
}
