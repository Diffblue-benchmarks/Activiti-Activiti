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
package org.activiti.engine.impl.util.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import groovy.lang.GroovyClassLoader;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;

public class ResourceStreamSourceDiffblueTest {
  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@link GroovyClassLoader#GroovyClassLoader()}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ResourceStreamSource#ResourceStreamSource(String, ClassLoader)}
   *   <li>{@link ResourceStreamSource#toString()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenGroovyClassLoader() {
    // Arrange, Act and Assert
    assertEquals("Resource[Resource]", (new ResourceStreamSource("Resource", new GroovyClassLoader())).toString());
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code Resource}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ResourceStreamSource#ResourceStreamSource(String)}
   *   <li>{@link ResourceStreamSource#toString()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters_whenResource() {
    // Arrange, Act and Assert
    assertEquals("Resource[Resource]", (new ResourceStreamSource("Resource")).toString());
  }

  /**
   * Test {@link ResourceStreamSource#getInputStream()}.
   * <p>
   * Method under test: {@link ResourceStreamSource#getInputStream()}
   */
  @Test
  public void testGetInputStream() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ResourceStreamSource("Resource", new GroovyClassLoader())).getInputStream());
  }

  /**
   * Test {@link ResourceStreamSource#getInputStream()}.
   * <ul>
   *   <li>Given {@link ResourceStreamSource#ResourceStreamSource(String)} with
   * {@code Resource}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ResourceStreamSource#getInputStream()}
   */
  @Test
  public void testGetInputStream_givenResourceStreamSourceWithResource() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> (new ResourceStreamSource("Resource")).getInputStream());
  }
}
