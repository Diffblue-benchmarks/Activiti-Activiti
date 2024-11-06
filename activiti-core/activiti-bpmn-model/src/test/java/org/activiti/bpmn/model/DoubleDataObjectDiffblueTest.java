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
import org.junit.Test;

public class DoubleDataObjectDiffblueTest {
  /**
   * Test {@link DoubleDataObject#setValue(Object)}.
   * <ul>
   *   <li>When forty-two.</li>
   *   <li>Then {@link DoubleDataObject} (default constructor) Value doubleValue is
   * forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link DoubleDataObject#setValue(Object)}
   */
  @Test
  public void testSetValue_whenFortyTwo_thenDoubleDataObjectValueDoubleValueIsFortyTwo() {
    // Arrange
    DoubleDataObject doubleDataObject = new DoubleDataObject();

    // Act
    doubleDataObject.setValue(42);

    // Assert
    assertEquals(42.0d, ((Double) doubleDataObject.getValue()).doubleValue(), 0.0);
  }

  /**
   * Test {@link DoubleDataObject#clone()}.
   * <ul>
   *   <li>Given {@link DoubleDataObject} (default constructor).</li>
   *   <li>Then return Value is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DoubleDataObject#clone()}
   */
  @Test
  public void testClone_givenDoubleDataObject_thenReturnValueIsNull() {
    // Arrange and Act
    DoubleDataObject actualCloneResult = (new DoubleDataObject()).clone();

    // Assert
    assertNull(actualCloneResult.getValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DoubleDataObject#clone()}.
   * <ul>
   *   <li>Then return Value doubleValue is forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link DoubleDataObject#clone()}
   */
  @Test
  public void testClone_thenReturnValueDoubleValueIsFortyTwo() {
    // Arrange
    DoubleDataObject doubleDataObject = new DoubleDataObject();
    doubleDataObject.setValue(42);

    // Act
    DoubleDataObject actualCloneResult = doubleDataObject.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertEquals(42.0d, ((Double) actualCloneResult.getValue()).doubleValue(), 0.0);
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test new {@link DoubleDataObject} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link DoubleDataObject}
   */
  @Test
  public void testNewDoubleDataObject() {
    // Arrange and Act
    DoubleDataObject actualDoubleDataObject = new DoubleDataObject();

    // Assert
    assertNull(actualDoubleDataObject.getValue());
    assertNull(actualDoubleDataObject.getId());
    assertNull(actualDoubleDataObject.getDocumentation());
    assertNull(actualDoubleDataObject.getName());
    assertNull(actualDoubleDataObject.getParentContainer());
    assertNull(actualDoubleDataObject.getItemSubjectRef());
    assertEquals(0, actualDoubleDataObject.getXmlColumnNumber());
    assertEquals(0, actualDoubleDataObject.getXmlRowNumber());
    assertTrue(actualDoubleDataObject.getExecutionListeners().isEmpty());
    assertTrue(actualDoubleDataObject.getAttributes().isEmpty());
    assertTrue(actualDoubleDataObject.getExtensionElements().isEmpty());
  }
}
