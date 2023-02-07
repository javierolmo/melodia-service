package com.javi.uned.pfgbackend.adapters.composer;

import com.javi.uned.pfgbackend.domain.sheet.model.Request;
import org.apache.http.client.methods.HttpGet;

public class HttpAzureFunctionComposer implements Composer {

    private final String COMPOSITION_ENDPOINT = "";
    private final String HOST;
    private final String TOKEN;

    public HttpAzureFunctionComposer(String host, String token) {
        this.HOST = host;
        this.TOKEN = token;
    }


    @Override
    public void submitRequest(Request request) {
        HttpGet httpGet = new HttpGet();
    }
}
