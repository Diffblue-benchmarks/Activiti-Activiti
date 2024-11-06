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
import org.junit.Test;

public class BooleanDataObjectDiffblueTest {
  /**
   * Test {@link BooleanDataObject#setValue(Object)}.
   * <ul>
   *   <li>Given {@link ExtensionElement} {@link ExtensionElement#getName()} return
   * {@code Name}.</li>
   *   <li>Then calls {@link ExtensionElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanDataObject#setValue(Object)}
   */
  @Test
  public void testSetValue_givenExtensionElementGetNameReturnName_thenCallsGetName() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.addExtensionElement(extensionElement);

    // Act
    booleanDataObject.setValue("Value");

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Test {@link BooleanDataObject#clone()}.
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor) Value is
   * {@code Value}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanDataObject#clone()}
   */
  @Test
  public void testClone_givenBooleanDataObjectValueIsValue() {
    // Arrange
    BooleanDataObject booleanDataObject = new BooleanDataObject();
    booleanDataObject.setValue("Value");

    // Act
    BooleanDataObject actualCloneResult = booleanDataObject.clone();

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
   * Test {@link BooleanDataObject#clone()}.
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).</li>
   *   <li>Then return Value is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BooleanDataObject#clone()}
   */
  @Test
  public void testClone_givenBooleanDataObject_thenReturnValueIsNull() {
    // Arrange and Act
    BooleanDataObject actualCloneResult = (new BooleanDataObject()).clone();

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
   * Test new {@link BooleanDataObject} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link BooleanDataObject}
   */
  @Test
  public void testNewBooleanDataObject() {
    // Arrange and Act
    BooleanDataObject actualBooleanDataObject = new BooleanDataObject();

    // Assert
    assertNull(actualBooleanDataObject.getValue());
    assertNull(actualBooleanDataObject.getId());
    assertNull(actualBooleanDataObject.getDocumentation());
    assertNull(actualBooleanDataObject.getName());
    assertNull(actualBooleanDataObject.getParentContainer());
    assertNull(actualBooleanDataObject.getItemSubjectRef());
    assertEquals(0, actualBooleanDataObject.getXmlColumnNumber());
    assertEquals(0, actualBooleanDataObject.getXmlRowNumber());
    assertTrue(actualBooleanDataObject.getExecutionListeners().isEmpty());
    assertTrue(actualBooleanDataObject.getAttributes().isEmpty());
    assertTrue(actualBooleanDataObject.getExtensionElements().isEmpty());
  }
}
