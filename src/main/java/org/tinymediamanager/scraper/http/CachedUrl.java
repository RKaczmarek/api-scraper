/*
 * Copyright 2012 - 2015 Manuel Laggner
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
package org.tinymediamanager.scraper.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.io.IOUtils;
import org.tinymediamanager.scraper.util.CacheMap;
import org.tinymediamanager.scraper.util.Pair;

import okhttp3.Headers;

/**
 * The class CachedUrl is used to cache some sort of Urls (e.g. when they are accessed several times in a short period)
 */
public class CachedUrl extends Url {
  private final static CacheMap<String, CachedRequest> CACHE = new CacheMap<String, CachedRequest>(60, 5);

  public CachedUrl(String url) throws MalformedURLException {
    this.url = url;
    // morph to URI to check syntax of the url
    try {
      this.uri = morphStringToUri(url);
    }
    catch (URISyntaxException e) {
      throw new MalformedURLException(url);
    }
  }

  @Override
  public InputStream getInputStream() throws IOException, InterruptedException {
    CachedRequest cachedRequest = CACHE.get(url);
    if (cachedRequest == null) {
      // need to fetch it with a real request
      Url url = new Url(this.url);
      url.headersRequest = headersRequest;
      InputStream is = url.getInputStream();
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      GZIPOutputStream gzip = new GZIPOutputStream(outputStream);
      IOUtils.copy(is, gzip);
      IOUtils.closeQuietly(gzip);
      IOUtils.closeQuietly(outputStream);

      // and now fill the CachedRequest object with the result
      cachedRequest = new CachedRequest(url, outputStream.toByteArray());
      if (url.responseCode >= 200 && url.responseCode < 300) {
        CACHE.put(this.url, cachedRequest);
      }
    }

    responseCode = cachedRequest.responseCode;
    responseMessage = cachedRequest.responseMessage;
    responseCharset = cachedRequest.responseCharset;
    responseContentType = cachedRequest.responseContentType;
    responseContentLength = cachedRequest.responseContentLength;

    headersResponse = cachedRequest.headersResponse;
    headersRequest.addAll(cachedRequest.headersRequest);

    return new GZIPInputStream(new ByteArrayInputStream(cachedRequest.content));
  }

  public static void clearCache() {
    CACHE.cleanup(true);
  }

  /**
   * A inner class for representing cached entries
   */
  private static class CachedRequest {
    byte[]                     content;

    int                        responseCode          = 0;
    String                     responseMessage       = "";
    Charset                    responseCharset       = null;
    String                     responseContentType   = "";
    long                       responseContentLength = -1;

    Headers                    headersResponse       = null;
    List<Pair<String, String>> headersRequest        = new ArrayList<>();

    CachedRequest(Url url, byte[] content) {
      this.content = content;

      this.responseCode = url.responseCode;
      this.responseMessage = url.responseMessage;
      this.responseCharset = url.responseCharset;
      this.responseContentType = url.responseContentType;
      this.responseContentLength = url.responseContentLength;

      this.headersResponse = url.headersResponse;
      this.headersRequest.addAll(url.headersRequest);
    }
  }
}
