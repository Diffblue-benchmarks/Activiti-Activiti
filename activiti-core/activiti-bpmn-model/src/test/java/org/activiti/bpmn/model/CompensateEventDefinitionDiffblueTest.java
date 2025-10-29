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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class CompensateEventDefinitionDiffblueTest {
  /**
   * Method under test: {@link CompensateEventDefinition#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    CompensateEventDefinition actualCloneResult = (new CompensateEventDefinition()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getActivityRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isWaitForCompletion());
  }

  /**
   * Method under test: {@link CompensateEventDefinition#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    CompensateEventDefinition compensateEventDefinition = new CompensateEventDefinition();
    compensateEventDefinition.setWaitForCompletion(false);

    // Act
    CompensateEventDefinition actualCloneResult = compensateEventDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getActivityRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isWaitForCompletion());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Method under test: {@link CompensateEventDefinition#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    CompensateEventDefinition compensateEventDefinition = new CompensateEventDefinition();
    compensateEventDefinition.setExtensionElements(extensionElements);

    // Act
    CompensateEventDefinition actualCloneResult = compensateEventDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getActivityRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isWaitForCompletion());
  }

  /**
   * Method under test:
   * {@link CompensateEventDefinition#setValues(CompensateEventDefinition)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    CompensateEventDefinition compensateEventDefinition = new CompensateEventDefinition();
    compensateEventDefinition.addExtensionElement(extensionElement);

    // Act
    compensateEventDefinition.setValues(new CompensateEventDefinition());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Method under test:
   * {@link CompensateEventDefinition#setValues(CompensateEventDefinition)}
   */
  @Test
  public void testSetValues2() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    CompensateEventDefinition compensateEventDefinition = new CompensateEventDefinition();
    compensateEventDefinition.addExtensionElement(extensionElement);

    CompensateEventDefinition otherDefinition = new CompensateEventDefinition();
    otherDefinition.setWaitForCompletion(false);

    // Act
    compensateEventDefinition.setValues(otherDefinition);

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link CompensateEventDefinition}
   *   <li>{@link CompensateEventDefinition#setActivityRef(String)}
   *   <li>{@link CompensateEventDefinition#setWaitForCompletion(boolean)}
   *   <li>{@link CompensateEventDefinition#getActivityRef()}
   *   <li>{@link CompensateEventDefinition#isWaitForCompletion()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    CompensateEventDefinition actualCompensateEventDefinition = new CompensateEventDefinition();
    actualCompensateEventDefinition.setActivityRef("Activity Ref");
    actualCompensateEventDefinition.setWaitForCompletion(true);
    String actualActivityRef = actualCompensateEventDefinition.getActivityRef();
    boolean actualIsWaitForCompletionResult = actualCompensateEventDefinition.isWaitForCompletion();

    // Assert that nothing has changed
    assertEquals("Activity Ref", actualActivityRef);
    assertEquals(0, actualCompensateEventDefinition.getXmlColumnNumber());
    assertEquals(0, actualCompensateEventDefinition.getXmlRowNumber());
    assertTrue(actualCompensateEventDefinition.getAttributes().isEmpty());
    assertTrue(actualCompensateEventDefinition.getExtensionElements().isEmpty());
    assertTrue(actualIsWaitForCompletionResult);
  }
}
