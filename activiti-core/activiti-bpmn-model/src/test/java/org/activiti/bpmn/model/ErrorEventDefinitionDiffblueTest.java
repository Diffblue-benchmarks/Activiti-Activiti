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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ErrorEventDefinitionDiffblueTest {
  /**
   * Test {@link ErrorEventDefinition#clone()}.
   *
   * <p>Method under test: {@link ErrorEventDefinition#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ErrorEventDefinition ErrorEventDefinition.clone()"})
  public void testClone() {
    // Arrange and Act
    ErrorEventDefinition actualCloneResult = new ErrorEventDefinition().clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getErrorRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ErrorEventDefinition}
   *   <li>{@link ErrorEventDefinition#setErrorRef(String)}
   *   <li>{@link ErrorEventDefinition#getErrorRef()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ErrorEventDefinition.<init>()",
    "String ErrorEventDefinition.getErrorRef()",
    "void ErrorEventDefinition.setErrorRef(String)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ErrorEventDefinition actualErrorEventDefinition = new ErrorEventDefinition();
    actualErrorEventDefinition.setErrorRef("An error occurred");

    // Assert
    assertEquals("An error occurred", actualErrorEventDefinition.getErrorRef());
    assertNull(actualErrorEventDefinition.getId());
    assertEquals(0, actualErrorEventDefinition.getXmlColumnNumber());
    assertEquals(0, actualErrorEventDefinition.getXmlRowNumber());
    assertTrue(actualErrorEventDefinition.getAttributes().isEmpty());
    assertTrue(actualErrorEventDefinition.getExtensionElements().isEmpty());
  }
}
