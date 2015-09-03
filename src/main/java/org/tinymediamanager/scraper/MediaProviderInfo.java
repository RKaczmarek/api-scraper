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
package org.tinymediamanager.scraper;

import java.net.URL;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The class ProviderInfo is used to store provider related information for
 * further usage.
 * 
 * @author Manuel Laggner
 * @since 1.0
 */
public class MediaProviderInfo {
  private static final URL EMPTY_LOGO = MediaProviderInfo.class.getResource("emtpyLogo.png");

  private String id;
  private String name;
  private String description;
  private URL    providerLogo;

  /**
   * Instantiates a new provider info.
   * 
   * @param id
   *          the id of the provider
   * @param name
   *          the name of the provider
   * @param description
   *          a description of the provider
   */
  public MediaProviderInfo(String id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  /**
   * Instantiates a new provider info.
   *
   * @param id
   *          the id of the provider
   * @param name
   *          the name of the provider
   * @param description
   *          a description of the provider
   * @param providerLogo
   *          the URL to the (embedded) provider logo
   */
  public MediaProviderInfo(String id, String name, String description, URL providerLogo) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.providerLogo = providerLogo;
  }

  /**
   * Gets the id.
   * 
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id.
   * 
   * @param id
   *          the new id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Gets the name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the name.
   * 
   * @param name
   *          the new name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the description.
   * 
   * @return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   * 
   * @param description
   *          the new description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Get the URL to the (embedded) provider logo
   * 
   * @return the URL to the logo
   */
  public URL getProviderLogo() {
    if (providerLogo != null) {
      return providerLogo;
    }
    else {
      return EMPTY_LOGO;
    }
  }

  /**
   * Set the URL to the (embedded) provider logo
   * 
   * @param providerLogo
   *          the URL to the logo
   */
  public void setProviderLogo(URL providerLogo) {
    this.providerLogo = providerLogo;
  }

  /**
   * <p>
   * Uses <code>ReflectionToStringBuilder</code> to generate a
   * <code>toString</code> for the specified object.
   * </p>
   * 
   * @return the String result
   * @see ReflectionToStringBuilder#toString(Object)
   */
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
