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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class ResourceDiffblueTest {
  /**
   * Test {@link Resource#Resource(String, String)}.
   * <p>
   * Method under test: {@link Resource#Resource(String, String)}
   */
  @Test
  public void testNewResource() {
    // Arrange and Act
    Resource actualResource = new Resource("42", "Resource Name");

    // Assert
    assertEquals("42", actualResource.getId());
    assertEquals("Resource Name", actualResource.getName());
    assertEquals(0, actualResource.getXmlColumnNumber());
    assertEquals(0, actualResource.getXmlRowNumber());
    assertTrue(actualResource.getAttributes().isEmpty());
    assertTrue(actualResource.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Resource#setName(String)}
   *   <li>{@link Resource#getName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    Resource resource = new Resource("42", "Resource Name");

    // Act
    resource.setName("Name");

    // Assert that nothing has changed
    assertEquals("Name", resource.getName());
  }

  /**
   * Test {@link Resource#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Resource#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    Resource resource = new Resource("42", "Resource Name");
    resource.setExtensionElements(extensionElements);

    // Act
    BaseElement actualCloneResult = resource.clone();

    // Assert
    assertTrue(actualCloneResult instanceof Resource);
    assertEquals("42", actualCloneResult.getId());
    assertEquals("Resource Name", ((Resource) actualCloneResult).getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Resource#clone()}.
   * <ul>
   *   <li>Given {@link Resource#Resource(String, String)} with resourceId is
   * {@code 42} and {@code Resource Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Resource#clone()}
   */
  @Test
  public void testClone_givenResourceWithResourceIdIs42AndResourceName() {
    // Arrange and Act
    BaseElement actualCloneResult = (new Resource("42", "Resource Name")).clone();

    // Assert
    assertTrue(actualCloneResult instanceof Resource);
    assertEquals("42", actualCloneResult.getId());
    assertEquals("Resource Name", ((Resource) actualCloneResult).getName());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }
}
