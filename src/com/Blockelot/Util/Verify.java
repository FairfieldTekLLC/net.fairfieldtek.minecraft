/*
 * Copyright (C) 2020 geev
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
package com.Blockelot.Util;

import com.Blockelot.Plugin;
import com.Blockelot.PluginManager;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Verify {

    public static String Register(Plugin Plugin) {
        String body = RequestHttp("http://www.blockelot.com/api/worldeditor/v1/Version?version='" + PluginManager.Version + "'&serverName='" + Plugin.getServer().getName() + "'");
        System.out.println("#########################################################");
        System.out.println(body);
        
        String[] parts = body.split("\\|");
        String WorldId = parts[0];
        String MinimumVersion = parts[1];
        
        
        System.out.println("Minimum Local Version: " + MinimumVersion);
        System.out.println("Current Local Version: " + PluginManager.Version);
        System.out.println("WorldId: " + WorldId);

        if (!MinimumVersion.equalsIgnoreCase(PluginManager.Version)) {
            System.out.println("Blockelot is out of Date!");
        }
        System.out.println("#########################################################");
        return WorldId;

    }

    private static String RequestHttp(String uri) {
        try {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(uri);
            request.addHeader("content-type", "application/json");
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
