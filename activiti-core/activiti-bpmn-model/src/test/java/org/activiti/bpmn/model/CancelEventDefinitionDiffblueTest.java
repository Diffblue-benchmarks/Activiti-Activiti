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

public class CancelEventDefinitionDiffblueTest {
  /**
   * Test {@link CancelEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link CancelEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link CancelEventDefinition#clone()}
   */
  @Test
  public void testClone_givenCancelEventDefinition() {
    // Arrange and Act
    CancelEventDefinition actualCloneResult = (new CancelEventDefinition()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CancelEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link CancelEventDefinition#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    CancelEventDefinition cancelEventDefinition = new CancelEventDefinition();
    cancelEventDefinition.setExtensionElements(extensionElements);

    // Act
    CancelEventDefinition actualCloneResult = cancelEventDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link CancelEventDefinition#setValues(CancelEventDefinition)} with
   * {@code otherDefinition}.
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CancelEventDefinition#setValues(CancelEventDefinition)}
   */
  @Test
  public void testSetValuesWithOtherDefinition_thenCallsGetName() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    CancelEventDefinition cancelEventDefinition = new CancelEventDefinition();
    cancelEventDefinition.addExtensionElement(extensionElement);

    // Act
    cancelEventDefinition.setValues(new CancelEventDefinition());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Test new {@link CancelEventDefinition} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link CancelEventDefinition}
   */
  @Test
  public void testNewCancelEventDefinition() {
    // Arrange and Act
    CancelEventDefinition actualCancelEventDefinition = new CancelEventDefinition();

    // Assert
    assertNull(actualCancelEventDefinition.getId());
    assertEquals(0, actualCancelEventDefinition.getXmlColumnNumber());
    assertEquals(0, actualCancelEventDefinition.getXmlRowNumber());
    assertTrue(actualCancelEventDefinition.getAttributes().isEmpty());
    assertTrue(actualCancelEventDefinition.getExtensionElements().isEmpty());
  }
}
