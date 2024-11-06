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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class AssignmentDiffblueTest {
  /**
   * Test {@link Assignment#clone()}.
   * <ul>
   *   <li>Given {@link Assignment} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link Assignment#clone()}
   */
  @Test
  public void testClone_givenAssignment() {
    // Arrange and Act
    Assignment actualCloneResult = (new Assignment()).clone();

    // Assert
    assertNull(actualCloneResult.getFrom());
    assertNull(actualCloneResult.getTo());
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Assignment#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Assignment#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    Assignment assignment = new Assignment();
    assignment.setExtensionElements(extensionElements);

    // Act
    Assignment actualCloneResult = assignment.clone();

    // Assert
    assertNull(actualCloneResult.getFrom());
    assertNull(actualCloneResult.getTo());
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link Assignment#setValues(Assignment)} with {@code otherAssignment}.
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Assignment#setValues(Assignment)}
   */
  @Test
  public void testSetValuesWithOtherAssignment_thenCallsGetName() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    Assignment assignment = new Assignment();
    assignment.addExtensionElement(extensionElement);

    // Act
    assignment.setValues(new Assignment());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link Assignment}
   *   <li>{@link Assignment#setFrom(String)}
   *   <li>{@link Assignment#setTo(String)}
   *   <li>{@link Assignment#getFrom()}
   *   <li>{@link Assignment#getTo()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    Assignment actualAssignment = new Assignment();
    actualAssignment.setFrom("jane.doe@example.org");
    actualAssignment.setTo("alice.liddell@example.org");
    String actualFrom = actualAssignment.getFrom();

    // Assert that nothing has changed
    assertEquals("alice.liddell@example.org", actualAssignment.getTo());
    assertEquals("jane.doe@example.org", actualFrom);
    assertEquals(0, actualAssignment.getXmlColumnNumber());
    assertEquals(0, actualAssignment.getXmlRowNumber());
    assertTrue(actualAssignment.getAttributes().isEmpty());
    assertTrue(actualAssignment.getExtensionElements().isEmpty());
  }
}
