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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import groovy.lang.GroovyClassLoader;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ResourceStreamSourceDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@link GroovyClassLoader#GroovyClassLoader()}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ResourceStreamSource#ResourceStreamSource(String, ClassLoader)}
   *   <li>{@link ResourceStreamSource#toString()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ResourceStreamSource.<init>(String)",
    "void ResourceStreamSource.<init>(String, ClassLoader)",
    "String ResourceStreamSource.toString()"
  })
  public void testGettersAndSetters_whenGroovyClassLoader() {
    // Arrange and Act
    ResourceStreamSource actualResourceStreamSource =
        new ResourceStreamSource("Resource", new GroovyClassLoader());

    // Assert
    assertEquals("Resource[Resource]", actualResourceStreamSource.toString());
  }

  /**
   * Test getters and setters.
   *
   * <ul>
   *   <li>When {@code Resource}.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ResourceStreamSource#ResourceStreamSource(String)}
   *   <li>{@link ResourceStreamSource#toString()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ResourceStreamSource.<init>(String)",
    "void ResourceStreamSource.<init>(String, ClassLoader)",
    "String ResourceStreamSource.toString()"
  })
  public void testGettersAndSetters_whenResource() {
    // Arrange, Act and Assert
    assertEquals("Resource[Resource]", new ResourceStreamSource("Resource").toString());
  }

  /**
   * Test {@link ResourceStreamSource#getInputStream()}.
   *
   * <p>Method under test: {@link ResourceStreamSource#getInputStream()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.io.InputStream ResourceStreamSource.getInputStream()"})
  public void testGetInputStream() {
    // Arrange
    ResourceStreamSource resourceStreamSource =
        new ResourceStreamSource("Resource", new GroovyClassLoader());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> resourceStreamSource.getInputStream());
  }

  /**
   * Test {@link ResourceStreamSource#getInputStream()}.
   *
   * <ul>
   *   <li>Given {@link ResourceStreamSource#ResourceStreamSource(String)} with {@code Resource}.
   * </ul>
   *
   * <p>Method under test: {@link ResourceStreamSource#getInputStream()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.io.InputStream ResourceStreamSource.getInputStream()"})
  public void testGetInputStream_givenResourceStreamSourceWithResource() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> new ResourceStreamSource("Resource").getInputStream());
  }
}
