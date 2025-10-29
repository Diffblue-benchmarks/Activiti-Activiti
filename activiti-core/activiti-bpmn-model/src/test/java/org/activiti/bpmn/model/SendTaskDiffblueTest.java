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
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class SendTaskDiffblueTest {
  /**
   * Method under test: {@link SendTask#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    SendTask actualCloneResult = (new SendTask()).clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getImplementationType());
    assertNull(actualCloneResult.getOperationRef());
    assertNull(actualCloneResult.getType());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getFieldExtensions().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link SendTask#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    ArrayList<FieldExtension> fieldExtensions = new ArrayList<>();
    fieldExtensions.add(new FieldExtension());

    SendTask sendTask = new SendTask();
    sendTask.setFieldExtensions(fieldExtensions);

    // Act
    SendTask actualCloneResult = sendTask.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getDefaultFlow());
    assertNull(actualCloneResult.getFailedJobRetryTimeCycleValue());
    List<FieldExtension> fieldExtensions2 = actualCloneResult.getFieldExtensions();
    assertEquals(1, fieldExtensions2.size());
    FieldExtension getResult = fieldExtensions2.get(0);
    assertNull(getResult.getId());
    assertNull(actualCloneResult.getId());
    assertNull(getResult.getExpression());
    assertNull(getResult.getFieldName());
    assertNull(getResult.getStringValue());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getImplementationType());
    assertNull(actualCloneResult.getOperationRef());
    assertNull(actualCloneResult.getType());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getIoSpecification());
    assertNull(actualCloneResult.getLoopCharacteristics());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.hasMultiInstanceLoopCharacteristics());
    assertFalse(actualCloneResult.isForCompensation());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertTrue(actualCloneResult.getBoundaryEvents().isEmpty());
    assertTrue(actualCloneResult.getDataInputAssociations().isEmpty());
    assertTrue(actualCloneResult.getDataOutputAssociations().isEmpty());
    assertTrue(actualCloneResult.getMapExceptions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link SendTask#setValues(SendTask)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getName()).thenReturn("Name");

    SendTask sendTask = new SendTask();
    sendTask.addExtensionElement(extensionElement);

    // Act
    sendTask.setValues(new SendTask());

    // Assert
    verify(extensionElement, atLeast(1)).getName();
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SendTask}
   *   <li>{@link SendTask#setImplementationType(String)}
   *   <li>{@link SendTask#setOperationRef(String)}
   *   <li>{@link SendTask#setType(String)}
   *   <li>{@link SendTask#getImplementationType()}
   *   <li>{@link SendTask#getOperationRef()}
   *   <li>{@link SendTask#getType()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    SendTask actualSendTask = new SendTask();
    actualSendTask.setImplementationType("Implementation Type");
    actualSendTask.setOperationRef("Operation Ref");
    actualSendTask.setType("Type");
    String actualImplementationType = actualSendTask.getImplementationType();
    String actualOperationRef = actualSendTask.getOperationRef();

    // Assert that nothing has changed
    assertEquals("Implementation Type", actualImplementationType);
    assertEquals("Operation Ref", actualOperationRef);
    assertEquals("Type", actualSendTask.getType());
    assertEquals(0, actualSendTask.getXmlColumnNumber());
    assertEquals(0, actualSendTask.getXmlRowNumber());
    assertFalse(actualSendTask.isForCompensation());
    assertFalse(actualSendTask.isAsynchronous());
    assertFalse(actualSendTask.isNotExclusive());
    assertTrue(actualSendTask.getBoundaryEvents().isEmpty());
    assertTrue(actualSendTask.getDataInputAssociations().isEmpty());
    assertTrue(actualSendTask.getDataOutputAssociations().isEmpty());
    assertTrue(actualSendTask.getMapExceptions().isEmpty());
    assertTrue(actualSendTask.getExecutionListeners().isEmpty());
    assertTrue(actualSendTask.getIncomingFlows().isEmpty());
    assertTrue(actualSendTask.getOutgoingFlows().isEmpty());
    assertTrue(actualSendTask.getFieldExtensions().isEmpty());
    assertTrue(actualSendTask.getAttributes().isEmpty());
    assertTrue(actualSendTask.getExtensionElements().isEmpty());
  }
}
