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

public class TerminateEventDefinitionDiffblueTest {
  /**
   * Test {@link TerminateEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return not TerminateAll.</li>
   * </ul>
   * <p>
   * Method under test: {@link TerminateEventDefinition#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnNotTerminateAll() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    TerminateEventDefinition terminateEventDefinition = new TerminateEventDefinition();
    terminateEventDefinition.setExtensionElements(extensionElements);

    // Act
    TerminateEventDefinition actualCloneResult = terminateEventDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isTerminateAll());
    assertFalse(actualCloneResult.isTerminateMultiInstance());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link TerminateEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link TerminateEventDefinition} (default constructor) TerminateAll
   * is {@code true}.</li>
   *   <li>Then return TerminateAll.</li>
   * </ul>
   * <p>
   * Method under test: {@link TerminateEventDefinition#clone()}
   */
  @Test
  public void testClone_givenTerminateEventDefinitionTerminateAllIsTrue_thenReturnTerminateAll() {
    // Arrange
    TerminateEventDefinition terminateEventDefinition = new TerminateEventDefinition();
    terminateEventDefinition.setTerminateAll(true);

    // Act
    TerminateEventDefinition actualCloneResult = terminateEventDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isTerminateMultiInstance());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isTerminateAll());
  }

  /**
   * Test {@link TerminateEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link TerminateEventDefinition} (default constructor).</li>
   *   <li>Then return not TerminateAll.</li>
   * </ul>
   * <p>
   * Method under test: {@link TerminateEventDefinition#clone()}
   */
  @Test
  public void testClone_givenTerminateEventDefinition_thenReturnNotTerminateAll() {
    // Arrange and Act
    TerminateEventDefinition actualCloneResult = (new TerminateEventDefinition()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isTerminateAll());
    assertFalse(actualCloneResult.isTerminateMultiInstance());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link TerminateEventDefinition#setValues(TerminateEventDefinition)}
   * with {@code otherDefinition}.
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TerminateEventDefinition#setValues(TerminateEventDefinition)}
   */
  @Test
  public void testSetValuesWithOtherDefinition_thenCallsGetName() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    TerminateEventDefinition terminateEventDefinition = new TerminateEventDefinition();
    terminateEventDefinition.addExtensionElement(extensionElement);

    // Act
    terminateEventDefinition.setValues(new TerminateEventDefinition());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link TerminateEventDefinition}
   *   <li>{@link TerminateEventDefinition#setTerminateAll(boolean)}
   *   <li>{@link TerminateEventDefinition#setTerminateMultiInstance(boolean)}
   *   <li>{@link TerminateEventDefinition#isTerminateAll()}
   *   <li>{@link TerminateEventDefinition#isTerminateMultiInstance()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    TerminateEventDefinition actualTerminateEventDefinition = new TerminateEventDefinition();
    actualTerminateEventDefinition.setTerminateAll(true);
    actualTerminateEventDefinition.setTerminateMultiInstance(true);
    boolean actualIsTerminateAllResult = actualTerminateEventDefinition.isTerminateAll();
    boolean actualIsTerminateMultiInstanceResult = actualTerminateEventDefinition.isTerminateMultiInstance();

    // Assert that nothing has changed
    assertEquals(0, actualTerminateEventDefinition.getXmlColumnNumber());
    assertEquals(0, actualTerminateEventDefinition.getXmlRowNumber());
    assertTrue(actualTerminateEventDefinition.getAttributes().isEmpty());
    assertTrue(actualTerminateEventDefinition.getExtensionElements().isEmpty());
    assertTrue(actualIsTerminateAllResult);
    assertTrue(actualIsTerminateMultiInstanceResult);
  }
}
