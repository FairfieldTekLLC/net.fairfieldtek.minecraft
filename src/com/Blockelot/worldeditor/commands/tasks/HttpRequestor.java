/*
 * Copyright (C) 2018 geev
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.Blockelot.worldeditor.commands.tasks;

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
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }
}
