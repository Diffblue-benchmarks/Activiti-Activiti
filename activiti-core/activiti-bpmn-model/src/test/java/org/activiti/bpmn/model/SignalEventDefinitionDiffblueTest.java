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

public class SignalEventDefinitionDiffblueTest {
  /**
   * Test {@link SignalEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return not Async.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalEventDefinition#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnNotAsync() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setExtensionElements(extensionElements);

    // Act
    SignalEventDefinition actualCloneResult = signalEventDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getSignalExpression());
    assertNull(actualCloneResult.getSignalRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsync());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SignalEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link SignalEventDefinition} (default constructor) Async is
   * {@code true}.</li>
   *   <li>Then return Async.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalEventDefinition#clone()}
   */
  @Test
  public void testClone_givenSignalEventDefinitionAsyncIsTrue_thenReturnAsync() {
    // Arrange
    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.setAsync(true);

    // Act
    SignalEventDefinition actualCloneResult = signalEventDefinition.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getSignalExpression());
    assertNull(actualCloneResult.getSignalRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isAsync());
  }

  /**
   * Test {@link SignalEventDefinition#clone()}.
   * <ul>
   *   <li>Given {@link SignalEventDefinition} (default constructor).</li>
   *   <li>Then return not Async.</li>
   * </ul>
   * <p>
   * Method under test: {@link SignalEventDefinition#clone()}
   */
  @Test
  public void testClone_givenSignalEventDefinition_thenReturnNotAsync() {
    // Arrange and Act
    SignalEventDefinition actualCloneResult = (new SignalEventDefinition()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getSignalExpression());
    assertNull(actualCloneResult.getSignalRef());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsync());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link SignalEventDefinition#setValues(SignalEventDefinition)} with
   * {@code otherDefinition}.
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SignalEventDefinition#setValues(SignalEventDefinition)}
   */
  @Test
  public void testSetValuesWithOtherDefinition_thenCallsGetName() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    SignalEventDefinition signalEventDefinition = new SignalEventDefinition();
    signalEventDefinition.addExtensionElement(extensionElement);

    // Act
    signalEventDefinition.setValues(new SignalEventDefinition());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SignalEventDefinition}
   *   <li>{@link SignalEventDefinition#setAsync(boolean)}
   *   <li>{@link SignalEventDefinition#setSignalExpression(String)}
   *   <li>{@link SignalEventDefinition#setSignalRef(String)}
   *   <li>{@link SignalEventDefinition#getSignalExpression()}
   *   <li>{@link SignalEventDefinition#getSignalRef()}
   *   <li>{@link SignalEventDefinition#isAsync()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    SignalEventDefinition actualSignalEventDefinition = new SignalEventDefinition();
    actualSignalEventDefinition.setAsync(true);
    actualSignalEventDefinition.setSignalExpression("Signal Expression");
    actualSignalEventDefinition.setSignalRef("Signal Ref");
    String actualSignalExpression = actualSignalEventDefinition.getSignalExpression();
    String actualSignalRef = actualSignalEventDefinition.getSignalRef();
    boolean actualIsAsyncResult = actualSignalEventDefinition.isAsync();

    // Assert that nothing has changed
    assertEquals("Signal Expression", actualSignalExpression);
    assertEquals("Signal Ref", actualSignalRef);
    assertEquals(0, actualSignalEventDefinition.getXmlColumnNumber());
    assertEquals(0, actualSignalEventDefinition.getXmlRowNumber());
    assertTrue(actualSignalEventDefinition.getAttributes().isEmpty());
    assertTrue(actualSignalEventDefinition.getExtensionElements().isEmpty());
    assertTrue(actualIsAsyncResult);
  }
}
