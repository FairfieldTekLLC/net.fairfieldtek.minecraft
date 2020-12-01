package com.Blockelot.worldeditor.commands.tasks;

import com.Blockelot.Util.ServerUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author geev
 */
public abstract class HttpRequestor extends BukkitRunnable {

    public String RequestHttp(String uri, String postBody) {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost(uri);
            StringEntity params = new StringEntity(postBody);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).setConnectionRequestTimeout(120000).build();
            request.setConfig(requestConfig);
            CloseableHttpResponse result = httpClient.execute(request);
            return EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (Exception e) {
            ServerUtil.consoleLog(e.getLocalizedMessage());
            ServerUtil.consoleLog(e.getMessage());
        }
        return null;
    }
}
