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
import java.util.Map;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultDeploymentCacheDiffblueTest {
  @InjectMocks
  private DefaultDeploymentCache<Object> defaultDeploymentCache;

  /**
   * Test {@link DefaultDeploymentCache#DefaultDeploymentCache()}.
   * <p>
   * Method under test: {@link DefaultDeploymentCache#DefaultDeploymentCache()}
   */
  @Test
  public void testNewDefaultDeploymentCache() {
    // Arrange and Act
    DefaultDeploymentCache<Object> actualDefaultDeploymentCache = new DefaultDeploymentCache<>();

    // Assert
    assertEquals(0, actualDefaultDeploymentCache.size());
    assertTrue(actualDefaultDeploymentCache.cache.isEmpty());
  }

  /**
   * Test {@link DefaultDeploymentCache#DefaultDeploymentCache(int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return size is zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultDeploymentCache#DefaultDeploymentCache(int)}
   */
  @Test
  public void testNewDefaultDeploymentCache_whenOne_thenReturnSizeIsZero() {
    // Arrange and Act
    DefaultDeploymentCache<Object> actualDefaultDeploymentCache = new DefaultDeploymentCache<>(1);

    // Assert
    assertEquals(0, actualDefaultDeploymentCache.size());
    assertTrue(actualDefaultDeploymentCache.cache.isEmpty());
  }

  /**
   * Test {@link DefaultDeploymentCache#get(String)}.
   * <p>
   * Method under test: {@link DefaultDeploymentCache#get(String)}
   */
  @Test
  public void testGet() {
    // Arrange, Act and Assert
    assertNull(defaultDeploymentCache.get("42"));
  }

  /**
   * Test {@link DefaultDeploymentCache#add(String, Object)}.
   * <p>
   * Method under test: {@link DefaultDeploymentCache#add(String, Object)}
   */
  @Test
  public void testAdd() {
    // Arrange
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
   * <p>
   * Method under test: {@link DefaultDeploymentCache#contains(String)}
   */
  @Test
  public void testContains() {
    // Arrange, Act and Assert
    assertFalse(defaultDeploymentCache.contains("42"));
  }

  /**
   * Test {@link DefaultDeploymentCache#size()}.
   * <p>
   * Method under test: {@link DefaultDeploymentCache#size()}
   */
  @Test
  public void testSize() {
    // Arrange
    DefaultDeploymentCache<Object> defaultDeploymentCache = new DefaultDeploymentCache<>();

    // Act and Assert
    assertEquals(0, defaultDeploymentCache.size());
  }
}
