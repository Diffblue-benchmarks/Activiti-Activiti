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
import org.junit.Test;

public class MultiInstanceLoopCharacteristicsDiffblueTest {
  /**
   * Test {@link MultiInstanceLoopCharacteristics#clone()}.
   * <ul>
   *   <li>Given {@link MultiInstanceLoopCharacteristics} (default
   * constructor).</li>
   *   <li>Then return not Sequential.</li>
   * </ul>
   * <p>
   * Method under test: {@link MultiInstanceLoopCharacteristics#clone()}
   */
  @Test
  public void testClone_givenMultiInstanceLoopCharacteristics_thenReturnNotSequential() {
    // Arrange and Act
    MultiInstanceLoopCharacteristics actualCloneResult = (new MultiInstanceLoopCharacteristics()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getCompletionCondition());
    assertNull(actualCloneResult.getElementIndexVariable());
    assertNull(actualCloneResult.getElementVariable());
    assertNull(actualCloneResult.getInputDataItem());
    assertNull(actualCloneResult.getLoopCardinality());
    assertNull(actualCloneResult.getLoopDataOutputRef());
    assertNull(actualCloneResult.getOutputDataItem());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isSequential());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link MultiInstanceLoopCharacteristics#clone()}.
   * <ul>
   *   <li>Then return Sequential.</li>
   * </ul>
   * <p>
   * Method under test: {@link MultiInstanceLoopCharacteristics#clone()}
   */
  @Test
  public void testClone_thenReturnSequential() {
    // Arrange
    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.setSequential(true);

    // Act
    MultiInstanceLoopCharacteristics actualCloneResult = multiInstanceLoopCharacteristics.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getCompletionCondition());
    assertNull(actualCloneResult.getElementIndexVariable());
    assertNull(actualCloneResult.getElementVariable());
    assertNull(actualCloneResult.getInputDataItem());
    assertNull(actualCloneResult.getLoopCardinality());
    assertNull(actualCloneResult.getLoopDataOutputRef());
    assertNull(actualCloneResult.getOutputDataItem());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isSequential());
  }

  /**
   * Test
   * {@link MultiInstanceLoopCharacteristics#setValues(MultiInstanceLoopCharacteristics)}
   * with {@code otherLoopCharacteristics}.
   * <ul>
   *   <li>Then calls {@link ExtensionElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link MultiInstanceLoopCharacteristics#setValues(MultiInstanceLoopCharacteristics)}
   */
  @Test
  public void testSetValuesWithOtherLoopCharacteristics_thenCallsGetName() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
    multiInstanceLoopCharacteristics.addExtensionElement(extensionElement);

    // Act
    multiInstanceLoopCharacteristics.setValues(new MultiInstanceLoopCharacteristics());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link MultiInstanceLoopCharacteristics}
   *   <li>{@link MultiInstanceLoopCharacteristics#setCompletionCondition(String)}
   *   <li>{@link MultiInstanceLoopCharacteristics#setElementIndexVariable(String)}
   *   <li>{@link MultiInstanceLoopCharacteristics#setElementVariable(String)}
   *   <li>{@link MultiInstanceLoopCharacteristics#setInputDataItem(String)}
   *   <li>{@link MultiInstanceLoopCharacteristics#setLoopCardinality(String)}
   *   <li>{@link MultiInstanceLoopCharacteristics#setLoopDataOutputRef(String)}
   *   <li>{@link MultiInstanceLoopCharacteristics#setOutputDataItem(String)}
   *   <li>{@link MultiInstanceLoopCharacteristics#setSequential(boolean)}
   *   <li>{@link MultiInstanceLoopCharacteristics#getCompletionCondition()}
   *   <li>{@link MultiInstanceLoopCharacteristics#getElementIndexVariable()}
   *   <li>{@link MultiInstanceLoopCharacteristics#getElementVariable()}
   *   <li>{@link MultiInstanceLoopCharacteristics#getInputDataItem()}
   *   <li>{@link MultiInstanceLoopCharacteristics#getLoopCardinality()}
   *   <li>{@link MultiInstanceLoopCharacteristics#getLoopDataOutputRef()}
   *   <li>{@link MultiInstanceLoopCharacteristics#getOutputDataItem()}
   *   <li>{@link MultiInstanceLoopCharacteristics#isSequential()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    MultiInstanceLoopCharacteristics actualMultiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
    actualMultiInstanceLoopCharacteristics.setCompletionCondition("Completion Condition");
    actualMultiInstanceLoopCharacteristics.setElementIndexVariable("Element Index Variable");
    actualMultiInstanceLoopCharacteristics.setElementVariable("Element Variable");
    actualMultiInstanceLoopCharacteristics.setInputDataItem("Input Data Item");
    actualMultiInstanceLoopCharacteristics.setLoopCardinality("Loop Cardinality");
    actualMultiInstanceLoopCharacteristics.setLoopDataOutputRef("Loop Data Output Ref");
    actualMultiInstanceLoopCharacteristics.setOutputDataItem("Output Data Item");
    actualMultiInstanceLoopCharacteristics.setSequential(true);
    String actualCompletionCondition = actualMultiInstanceLoopCharacteristics.getCompletionCondition();
    String actualElementIndexVariable = actualMultiInstanceLoopCharacteristics.getElementIndexVariable();
    String actualElementVariable = actualMultiInstanceLoopCharacteristics.getElementVariable();
    String actualInputDataItem = actualMultiInstanceLoopCharacteristics.getInputDataItem();
    String actualLoopCardinality = actualMultiInstanceLoopCharacteristics.getLoopCardinality();
    String actualLoopDataOutputRef = actualMultiInstanceLoopCharacteristics.getLoopDataOutputRef();
    String actualOutputDataItem = actualMultiInstanceLoopCharacteristics.getOutputDataItem();
    boolean actualIsSequentialResult = actualMultiInstanceLoopCharacteristics.isSequential();

    // Assert that nothing has changed
    assertEquals("Completion Condition", actualCompletionCondition);
    assertEquals("Element Index Variable", actualElementIndexVariable);
    assertEquals("Element Variable", actualElementVariable);
    assertEquals("Input Data Item", actualInputDataItem);
    assertEquals("Loop Cardinality", actualLoopCardinality);
    assertEquals("Loop Data Output Ref", actualLoopDataOutputRef);
    assertEquals("Output Data Item", actualOutputDataItem);
    assertEquals(0, actualMultiInstanceLoopCharacteristics.getXmlColumnNumber());
    assertEquals(0, actualMultiInstanceLoopCharacteristics.getXmlRowNumber());
    assertTrue(actualMultiInstanceLoopCharacteristics.getAttributes().isEmpty());
    assertTrue(actualMultiInstanceLoopCharacteristics.getExtensionElements().isEmpty());
    assertTrue(actualIsSequentialResult);
  }
}
