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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ResourceDiffblueTest {
  /**
   * Test {@link Resource#Resource(String, String)}.
   * <p>
   * Method under test: {@link Resource#Resource(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void Resource.<init>(String, String)"})
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String Resource.getName()", "void Resource.setName(String)"})
  public void testGettersAndSetters() {
    // Arrange
    Resource resource = new Resource("42", "Resource Name");

    // Act
    resource.setName("Name");

    // Assert
    assertEquals("Name", resource.getName());
  }

  /**
   * Test {@link Resource#clone()}.
   * <p>
   * Method under test: {@link Resource#clone()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"BaseElement Resource.clone()"})
  public void testClone() {
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
