/*
 * Copyright 2012 - 2016 Manuel Laggner
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

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.tinymediamanager.scraper.entities.MediaType;
import org.tinymediamanager.scraper.util.MetadataUtil;
import org.tinymediamanager.scraper.util.StrgUtils;

/**
 * The Class MediaSearchResult.
 * 
 * @author Manuel Laggner
 * @since 1.0
 */
public class MediaSearchResult implements Comparable<MediaSearchResult> {
  private String        providerId;
  private String        url;
  private String        title;
  private int           year;
  private String        originalTitle;
  private String        originalLanguage;
  private String        id;
  private float         score;
  private String        imdbId;
  private MediaMetadata metadata = null;
  private MediaType     type;
  private String        posterUrl;

  public MediaSearchResult(String providerId, MediaType type) {
    this.providerId = providerId;
    this.type = type;
  }

  public MediaSearchResult(String providerId, MediaType type, float score) {
    this.providerId = providerId;
    this.type = type;
    this.score = score;
  }

  public MediaSearchResult(String providerId, MediaType type, String id, String title, int year, float score) {
    this.providerId = providerId;
    this.type = type;
    this.id = StrgUtils.getNonNullString(id);
    this.title = StrgUtils.getNonNullString(title);
    this.year = year;
    this.score = score;
  }

  /**
   * merges all entries from other MSR into ours, IF VALUES ARE EMPTY<br>
   * <b>needs testing!</b>
   * 
   * @param msr
   *          other MediaSerachResult
   */
  public void mergeFrom(MediaSearchResult msr) {
    if (msr == null) {
      return;
    }

    url = StringUtils.isEmpty(url) ? msr.getUrl() : url;
    title = StringUtils.isEmpty(title) ? msr.getTitle() : title;
    year = year == 0 ? msr.getYear() : year;
    originalTitle = StringUtils.isEmpty(originalTitle) ? msr.getOriginalTitle() : originalTitle;
    originalLanguage = StringUtils.isEmpty(originalLanguage) ? msr.getOriginalLanguage() : originalLanguage;
    id = StringUtils.isEmpty(id) ? msr.getId() : id;
    imdbId = StringUtils.isEmpty(imdbId) ? msr.getIMDBId() : imdbId;
    posterUrl = StringUtils.isEmpty(posterUrl) ? msr.getPosterUrl() : posterUrl;

    if (metadata == null) {
      metadata = msr.getMediaMetadata();
    }
    else {
      metadata.mergeFrom(msr.getMediaMetadata());
    }
  }

  /**
   * Get the original title of this search result
   * 
   * @return the original title
   */
  public String getOriginalTitle() {
    return originalTitle;
  }

  /**
   * Set the original title for this search result
   * 
   * @param originalTitle
   *          the original title
   */
  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = StrgUtils.getNonNullString(originalTitle);
  }

  /**
   * Get the original language of this search result
   *
   * @return the original language
   */
  public String getOriginalLanguage() {
    return originalLanguage;
  }

  /**
   * Set the original language for this search result
   *
   * @param originalLanguage
   *          the original language
   */
  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = StrgUtils.getNonNullString(originalLanguage);
  }

  /**
   * Get the provider id
   * 
   * @return the provider id
   */
  public String getProviderId() {
    return providerId;
  }

  /**
   * Set the provider id
   * 
   * @param providerId
   *          the provider id
   */
  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }

  /**
   * Get the title of this search result
   * 
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Set the title of this search result
   * 
   * @param title
   *          the title
   */
  public void setTitle(String title) {
    this.title = StrgUtils.getNonNullString(title);
  }

  /**
   * Get the year of this search result
   * 
   * @return the year
   */
  public int getYear() {
    return year;
  }

  /**
   * Set the year of this search result
   * 
   * @param year
   *          the year
   */
  public void setYear(int year) {
    this.year = year;
  }

  /**
   * Set the year of this search result (nullsafe)
   *
   * @param year
   *          the year
   */
  public void setYear(Integer year) {
    if (year != null) {
      setYear(year.intValue());
    }
  }

  /**
   * Get the score of this search result. 1.0 is perfect match
   * 
   * @return the score
   */
  public float getScore() {
    return score;
  }

  /**
   * Set the score of this result
   * 
   * @param score
   *          the result
   */
  public void setScore(float score) {
    this.score = score;
  }

  /**
   * Set the score of this result (nullsafe)
   *
   * @param score
   *          the result
   */
  public void setScore(Float score) {
    if (score != null) {
      setScore(score.floatValue());
    }
  }

  /**
   * Get the url to this search result
   * 
   * @return the url
   */
  public String getUrl() {
    return url;
  }

  /**
   * Set the url to this search result
   * 
   * @param url
   *          the url
   */
  public void setUrl(String url) {
    this.url = StrgUtils.getNonNullString(url);
  }

  /**
   * Get the media type this search result is for
   * 
   * @return the media type
   */
  public MediaType getMediaType() {
    return type;
  }

  /**
   * Get the id of this search result
   * 
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Set the id of this search result
   * 
   * @param id
   *          the search result id
   */
  public void setId(String id) {
    this.id = StrgUtils.getNonNullString(id);
  }

  /**
   * Get the IMDB id
   * 
   * @return the IMDB id
   */
  public String getIMDBId() {
    return imdbId;
  }

  /**
   * Set the IMDB id
   * 
   * @param imdbid
   *          the IMDB id
   */
  public void setIMDBId(String imdbid) {
    if (MetadataUtil.isValidImdbId(imdbid)) {
      imdbId = imdbid;
    }
  }

  /**
   * Get the MediaMetadata
   * 
   * @return the MediaMetadata
   */
  public MediaMetadata getMediaMetadata() {
    return metadata;
  }

  /**
   * Set the MediaMetadata if you already got the whole meta data while searching (for buffering)
   * 
   * @param md
   *          the MediaMetadata
   */
  public void setMetadata(MediaMetadata md) {
    metadata = md;
  }

  /**
   * Get the poster url
   * 
   * @return the poster url
   */
  public String getPosterUrl() {
    return posterUrl;
  }

  /**
   * Set the poster url
   * 
   * @param posterUrl
   *          the poster url
   */
  public void setPosterUrl(String posterUrl) {
    this.posterUrl = StrgUtils.getNonNullString(posterUrl);
  }

  @Override
  public int compareTo(MediaSearchResult arg0) {
    if (getScore() < arg0.getScore()) {
      return -1;
    }
    else if (getScore() == arg0.getScore()) {
      // same score - rank on year
      return Integer.compare(getYear(),arg0.getYear());
    }
    else {
      return 1;
    }
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    result = prime * result + ((url == null) ? 0 : url.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MediaSearchResult other = (MediaSearchResult) obj;
    if (type != other.type)
      return false;
    if (url == null) {
      if (other.url != null)
        return false;
    }
    else if (!url.equals(other.url))
      return false;
    return true;
  }

  /**
   * <p>
   * Uses <code>ReflectionToStringBuilder</code> to generate a <code>toString</code> for the specified object.
   * </p>
   * 
   * @return the String result
   * @see ReflectionToStringBuilder#toString(Object)
   */
  @Override
  public String toString() {
    return (new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE) {
      @Override
      protected boolean accept(Field f) {
        return super.accept(f) && !f.getName().equals("metadata");
      }
    }).toString();
  }
}
