/*
 * Copyright 2010-2020 Alfresco Software, Ltd.
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
package org.activiti.engine.impl.persistence.deploy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DefaultDeploymentCacheDiffblueTest {
  /**
   * Test {@link DefaultDeploymentCache#DefaultDeploymentCache()}.
   *
   * <p>Method under test: {@link DefaultDeploymentCache#DefaultDeploymentCache()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultDeploymentCache.<init>()"})
  public void testNewDefaultDeploymentCache() {
    // Arrange and Act
    DefaultDeploymentCache<Object> actualDefaultDeploymentCache = new DefaultDeploymentCache<>();

    // Assert
    assertEquals(0, actualDefaultDeploymentCache.size());
    assertTrue(actualDefaultDeploymentCache.cache.isEmpty());
  }

  /**
   * Test {@link DefaultDeploymentCache#DefaultDeploymentCache(int)}.
   *
   * <ul>
   *   <li>When one.
   *   <li>Then return size is zero.
   * </ul>
   *
   * <p>Method under test: {@link DefaultDeploymentCache#DefaultDeploymentCache(int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultDeploymentCache.<init>(int)"})
  public void testNewDefaultDeploymentCache_whenOne_thenReturnSizeIsZero() {
    // Arrange and Act
    DefaultDeploymentCache<Object> actualDefaultDeploymentCache = new DefaultDeploymentCache<>(1);

    // Assert
    assertEquals(0, actualDefaultDeploymentCache.size());
    assertTrue(actualDefaultDeploymentCache.cache.isEmpty());
  }

  /**
   * Test {@link DefaultDeploymentCache#get(String)}.
   *
   * <p>Method under test: {@link DefaultDeploymentCache#get(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object DefaultDeploymentCache.get(String)"})
  public void testGet() {
    // Arrange
    DefaultDeploymentCache<Object> defaultDeploymentCache = new DefaultDeploymentCache<>();

    // Act and Assert
    assertNull(defaultDeploymentCache.get("42"));
  }

  /**
   * Test {@link DefaultDeploymentCache#add(String, Object)}.
   *
   * <ul>
   *   <li>Given {@link DefaultDeploymentCache#DefaultDeploymentCache(int)} with limit is one.
   * </ul>
   *
   * <p>Method under test: {@link DefaultDeploymentCache#add(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultDeploymentCache.add(String, Object)"})
  public void testAdd_givenDefaultDeploymentCacheWithLimitIsOne() {
    // Arrange
    DefaultDeploymentCache<Object> defaultDeploymentCache = new DefaultDeploymentCache<>(1);
    Object object = JSONObject.NULL;

    // Act
    defaultDeploymentCache.add("42", object);

    // Assert
    Map<String, Object> stringObjectMap = defaultDeploymentCache.cache;
    assertEquals(1, stringObjectMap.size());
    assertEquals(1, defaultDeploymentCache.size());
    assertSame(object, stringObjectMap.get("42"));
  }

  /**
   * Test {@link DefaultDeploymentCache#add(String, Object)}.
   *
   * <ul>
   *   <li>Given {@link DefaultDeploymentCache#DefaultDeploymentCache(int)} with limit is one add
   *       {@code Id} and {@link JSONObject#NULL}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultDeploymentCache#add(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultDeploymentCache.add(String, Object)"})
  public void testAdd_givenDefaultDeploymentCacheWithLimitIsOneAddIdAndNull() {
    // Arrange
    DefaultDeploymentCache<Object> defaultDeploymentCache = new DefaultDeploymentCache<>(1);
    defaultDeploymentCache.add("Id", JSONObject.NULL);
    Object object = JSONObject.NULL;

    // Act
    defaultDeploymentCache.add("42", object);

    // Assert
    Map<String, Object> stringObjectMap = defaultDeploymentCache.cache;
    assertEquals(1, stringObjectMap.size());
    assertEquals(1, defaultDeploymentCache.size());
    assertSame(object, stringObjectMap.get("42"));
  }

  /**
   * Test {@link DefaultDeploymentCache#add(String, Object)}.
   *
   * <ul>
   *   <li>Given {@link DefaultDeploymentCache#DefaultDeploymentCache()}.
   *   <li>Then {@link DefaultDeploymentCache#DefaultDeploymentCache()} {@link
   *       DefaultDeploymentCache#cache} size is one.
   * </ul>
   *
   * <p>Method under test: {@link DefaultDeploymentCache#add(String, Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultDeploymentCache.add(String, Object)"})
  public void testAdd_givenDefaultDeploymentCache_thenDefaultDeploymentCacheCacheSizeIsOne() {
    // Arrange
    DefaultDeploymentCache<Object> defaultDeploymentCache = new DefaultDeploymentCache<>();
    Object object = JSONObject.NULL;

    // Act
    defaultDeploymentCache.add("42", object);

    // Assert
    Map<String, Object> stringObjectMap = defaultDeploymentCache.cache;
    assertEquals(1, stringObjectMap.size());
    assertEquals(1, defaultDeploymentCache.size());
    assertSame(object, stringObjectMap.get("42"));
  }

  /**
   * Test {@link DefaultDeploymentCache#contains(String)}.
   *
   * <ul>
   *   <li>Given {@link DefaultDeploymentCache#DefaultDeploymentCache()} add {@code 42} and {@link
   *       JSONObject#NULL}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultDeploymentCache#contains(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DefaultDeploymentCache.contains(String)"})
  public void testContains_givenDefaultDeploymentCacheAdd42AndNull_thenReturnTrue() {
    // Arrange
    DefaultDeploymentCache<Object> defaultDeploymentCache = new DefaultDeploymentCache<>();
    defaultDeploymentCache.add("42", JSONObject.NULL);

    // Act and Assert
    assertTrue(defaultDeploymentCache.contains("42"));
  }

  /**
   * Test {@link DefaultDeploymentCache#contains(String)}.
   *
   * <ul>
   *   <li>Given {@link DefaultDeploymentCache#DefaultDeploymentCache()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultDeploymentCache#contains(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean DefaultDeploymentCache.contains(String)"})
  public void testContains_givenDefaultDeploymentCache_thenReturnFalse() {
    // Arrange
    DefaultDeploymentCache<Object> defaultDeploymentCache = new DefaultDeploymentCache<>();

    // Act and Assert
    assertFalse(defaultDeploymentCache.contains("42"));
  }

  /**
   * Test {@link DefaultDeploymentCache#size()}.
   *
   * <p>Method under test: {@link DefaultDeploymentCache#size()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"int DefaultDeploymentCache.size()"})
  public void testSize() {
    // Arrange
    DefaultDeploymentCache<Object> defaultDeploymentCache = new DefaultDeploymentCache<>();

    // Act and Assert
    assertEquals(0, defaultDeploymentCache.size());
  }
}
