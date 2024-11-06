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

public class ErrorEventDefinitionDiffblueTest {
  /**
   * Test {@link ErrorEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link ErrorEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorEventDefinition#clone()}
   */
  @Test
  public void testClone_givenErrorEventDefinition() {
    // Arrange and Act
    ErrorEventDefinition actualCloneResult = (new ErrorEventDefinition()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getErrorRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link ErrorEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ErrorEventDefinition#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    ErrorEventDefinition errorEventDefinition = new ErrorEventDefinition();
    errorEventDefinition.setExtensionElements(extensionElements);

    // Act
    ErrorEventDefinition actualCloneResult = errorEventDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getErrorRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link ErrorEventDefinition#setValues(ErrorEventDefinition)} with
   * {@code otherDefinition}.
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ErrorEventDefinition#setValues(ErrorEventDefinition)}
   */
  @Test
  public void testSetValuesWithOtherDefinition_thenCallsGetName() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    ErrorEventDefinition errorEventDefinition = new ErrorEventDefinition();
    errorEventDefinition.addExtensionElement(extensionElement);

    // Act
    errorEventDefinition.setValues(new ErrorEventDefinition());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ErrorEventDefinition}
   *   <li>{@link ErrorEventDefinition#setErrorRef(String)}
   *   <li>{@link ErrorEventDefinition#getErrorRef()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ErrorEventDefinition actualErrorEventDefinition = new ErrorEventDefinition();
    actualErrorEventDefinition.setErrorRef("An error occurred");

    // Assert that nothing has changed
    assertEquals("An error occurred", actualErrorEventDefinition.getErrorRef());
    assertEquals(0, actualErrorEventDefinition.getXmlColumnNumber());
    assertEquals(0, actualErrorEventDefinition.getXmlRowNumber());
    assertTrue(actualErrorEventDefinition.getAttributes().isEmpty());
    assertTrue(actualErrorEventDefinition.getExtensionElements().isEmpty());
  }
}
