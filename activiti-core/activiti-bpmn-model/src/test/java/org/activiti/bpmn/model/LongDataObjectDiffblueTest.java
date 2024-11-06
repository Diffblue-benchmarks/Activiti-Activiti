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

public class LongDataObjectDiffblueTest {
  /**
   * Test {@link LongDataObject#clone()}.
   * <ul>
   *   <li>Given {@link LongDataObject} (default constructor) Value is
   * forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongDataObject#clone()}
   */
  @Test
  public void testClone_givenLongDataObjectValueIsFortyTwo() {
    // Arrange
    LongDataObject longDataObject = new LongDataObject();
    longDataObject.setValue(42);

    // Act
    LongDataObject actualCloneResult = longDataObject.clone();

    // Assert
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
   * Test {@link LongDataObject#clone()}.
   * <ul>
   *   <li>Given {@link LongDataObject} (default constructor).</li>
   *   <li>Then return Value is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongDataObject#clone()}
   */
  @Test
  public void testClone_givenLongDataObject_thenReturnValueIsNull() {
    // Arrange and Act
    LongDataObject actualCloneResult = (new LongDataObject()).clone();

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
   * Test new {@link LongDataObject} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link LongDataObject}
   */
  @Test
  public void testNewLongDataObject() {
    // Arrange and Act
    LongDataObject actualLongDataObject = new LongDataObject();

    // Assert
    assertNull(actualLongDataObject.getValue());
    assertNull(actualLongDataObject.getId());
    assertNull(actualLongDataObject.getDocumentation());
    assertNull(actualLongDataObject.getName());
    assertNull(actualLongDataObject.getParentContainer());
    assertNull(actualLongDataObject.getItemSubjectRef());
    assertEquals(0, actualLongDataObject.getXmlColumnNumber());
    assertEquals(0, actualLongDataObject.getXmlRowNumber());
    assertTrue(actualLongDataObject.getExecutionListeners().isEmpty());
    assertTrue(actualLongDataObject.getAttributes().isEmpty());
    assertTrue(actualLongDataObject.getExtensionElements().isEmpty());
  }
}
