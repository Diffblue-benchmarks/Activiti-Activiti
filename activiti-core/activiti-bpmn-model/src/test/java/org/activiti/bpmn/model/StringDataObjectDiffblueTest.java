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

public class StringDataObjectDiffblueTest {
  /**
   * Method under test: {@link StringDataObject#setValue(Object)}
   */
  @Test
  public void testSetValue() {
    // Arrange
    StringDataObject stringDataObject = new StringDataObject();

    // Act
    stringDataObject.setValue("Value");

    // Assert
    assertEquals("Value", stringDataObject.getValue());
  }

  /**
   * Method under test: {@link StringDataObject#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    StringDataObject actualCloneResult = (new StringDataObject()).clone();

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
   * Method under test: {@link StringDataObject#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    StringDataObject stringDataObject = new StringDataObject();
    stringDataObject.setValue("Value");

    // Act
    StringDataObject actualCloneResult = stringDataObject.clone();

    // Assert
    assertEquals("Value", actualCloneResult.getValue());
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
   * Method under test: default or parameterless constructor of
   * {@link StringDataObject}
   */
  @Test
  public void testNewStringDataObject() {
    // Arrange and Act
    StringDataObject actualStringDataObject = new StringDataObject();

    // Assert
    assertNull(actualStringDataObject.getValue());
    assertNull(actualStringDataObject.getId());
    assertNull(actualStringDataObject.getDocumentation());
    assertNull(actualStringDataObject.getName());
    assertNull(actualStringDataObject.getParentContainer());
    assertNull(actualStringDataObject.getItemSubjectRef());
    assertEquals(0, actualStringDataObject.getXmlColumnNumber());
    assertEquals(0, actualStringDataObject.getXmlRowNumber());
    assertTrue(actualStringDataObject.getExecutionListeners().isEmpty());
    assertTrue(actualStringDataObject.getAttributes().isEmpty());
    assertTrue(actualStringDataObject.getExtensionElements().isEmpty());
  }
}
