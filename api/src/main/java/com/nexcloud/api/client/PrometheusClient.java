/*
 * Copyright 2019 NexCloud Co.,Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nexcloud.api.client;

import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class PrometheusClient {
    @Value("${prometheus.endpoint}")
    private String ENDPOINT;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Prometheus get series info
     *
     * @param query
     * @return
     */
    public ResponseEntity<String> getSeries(String workload) {
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> resData = null;
        try {
            resData = restTemplate.getForEntity(ENDPOINT + "/api/v1/series?match[]=kube_" + workload + "_labels", String.class);
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return resData;
    }

    /**
     * Prometheus get label info
     *
     * @param query
     * @return
     */
    public ResponseEntity<String> getLabel(String label) {
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> resData = null;
        try {
            resData = restTemplate.getForEntity(ENDPOINT + "/api/v1/label/" + label + "/values", String.class);
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return resData;
    }

    /**
     * Prometheus get Query
     *
     * @param query
     * @return
     */
    public ResponseEntity<String> getQuery(String query, String... param) {

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> resData = null;
        try {
            resData = restTemplate.getForEntity(ENDPOINT + "/api/v1/query?query=" + URLDecoder.decode(query, "UTF-8"), String.class, param);
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return resData;
    }

    /**
     * Prometheus get range query
     *
     * @param query
     * @return
     */
    public ResponseEntity<String> getQueryRange(String query, String... param) {
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> resData = null;
        try {
            resData = restTemplate.getForEntity(ENDPOINT + "/api/v1/query_range?query=" + URLDecoder.decode(query, "UTF-8"), String.class, param);
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return resData;
    }

    /**
     * Prometheus get dynamic Query
     *
     * @param query
     * @return
     */
    public ResponseEntity<String> getDynamicQuery(String query, String... param) {

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> resData = null;
        try {
            resData = restTemplate.getForEntity(ENDPOINT + "/api/v1/query?query=" + URLDecoder.decode(query, "UTF-8"), String.class, param);
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return resData;
    }

    /**
     * Prometheus get dynamic Query
     *
     * @param query
     * @return
     */
    public ResponseEntity<String> getDynamicRangeQuery(String query, String... param) {

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> resData = null;
        try {
            resData = restTemplate.getForEntity(ENDPOINT + "/api/v1/query_range?query=" + URLDecoder.decode(query, "UTF-8"), String.class, param);
        } catch (RestClientException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return resData;
    }
}
