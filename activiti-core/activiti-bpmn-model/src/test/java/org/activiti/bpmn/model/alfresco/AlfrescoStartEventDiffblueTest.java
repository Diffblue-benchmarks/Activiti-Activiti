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
package org.activiti.bpmn.model.alfresco;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FormProperty;
import org.junit.Test;

public class AlfrescoStartEventDiffblueTest {
  /**
   * Test {@link AlfrescoStartEvent#clone()}.
   * <ul>
   *   <li>Given {@link AlfrescoStartEvent} (default constructor).</li>
   *   <li>Then return Behavior is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoStartEvent#clone()}
   */
  @Test
  public void testClone_givenAlfrescoStartEvent_thenReturnBehaviorIsNull() {
    // Arrange and Act
    AlfrescoStartEvent actualCloneResult = (new AlfrescoStartEvent()).clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getInitiator());
    assertNull(actualCloneResult.getRunAs());
    assertNull(actualCloneResult.getScriptProcessor());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(actualCloneResult.isInterrupting());
    assertTrue(actualCloneResult.getEventDefinitions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link AlfrescoStartEvent#clone()}.
   * <ul>
   *   <li>Then return FormProperties size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoStartEvent#clone()}
   */
  @Test
  public void testClone_thenReturnFormPropertiesSizeIsOne() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("foo", mock(BiFunction.class));

    FormProperty formProperty = new FormProperty();
    formProperty.setExtensionElements(extensionElements);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    AlfrescoStartEvent alfrescoStartEvent = new AlfrescoStartEvent();
    alfrescoStartEvent.setFormProperties(formProperties);

    // Act and Assert
    List<FormProperty> formProperties2 = alfrescoStartEvent.clone().getFormProperties();
    assertEquals(1, formProperties2.size());
    FormProperty getResult = formProperties2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getDatePattern());
    assertNull(getResult.getDefaultExpression());
    assertNull(getResult.getExpression());
    assertNull(getResult.getName());
    assertNull(getResult.getType());
    assertNull(getResult.getVariable());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(getResult.isRequired());
    assertTrue(getResult.getFormValues().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(getResult.isReadable());
    assertTrue(getResult.isWriteable());
  }

  /**
   * Test {@link AlfrescoStartEvent#setValues(AlfrescoStartEvent)} with
   * {@code AlfrescoStartEvent}.
   * <ul>
   *   <li>Then calls {@link CancelEventDefinition#clone()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AlfrescoStartEvent#setValues(AlfrescoStartEvent)}
   */
  @Test
  public void testSetValuesWithAlfrescoStartEvent_thenCallsClone() {
    // Arrange
    AlfrescoStartEvent alfrescoStartEvent = new AlfrescoStartEvent();
    CancelEventDefinition cancelEventDefinition = mock(CancelEventDefinition.class);
    when(cancelEventDefinition.clone()).thenReturn(new CancelEventDefinition());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(cancelEventDefinition);

    AlfrescoStartEvent otherElement = new AlfrescoStartEvent();
    otherElement.setEventDefinitions(eventDefinitions);

    // Act
    alfrescoStartEvent.setValues(otherElement);

    // Assert
    verify(cancelEventDefinition).clone();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link AlfrescoStartEvent}
   *   <li>{@link AlfrescoStartEvent#setRunAs(String)}
   *   <li>{@link AlfrescoStartEvent#setScriptProcessor(String)}
   *   <li>{@link AlfrescoStartEvent#getRunAs()}
   *   <li>{@link AlfrescoStartEvent#getScriptProcessor()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    AlfrescoStartEvent actualAlfrescoStartEvent = new AlfrescoStartEvent();
    actualAlfrescoStartEvent.setRunAs("Run As");
    actualAlfrescoStartEvent.setScriptProcessor("Script Processor");
    String actualRunAs = actualAlfrescoStartEvent.getRunAs();

    // Assert that nothing has changed
    assertEquals("Run As", actualRunAs);
    assertEquals("Script Processor", actualAlfrescoStartEvent.getScriptProcessor());
    assertEquals(0, actualAlfrescoStartEvent.getXmlColumnNumber());
    assertEquals(0, actualAlfrescoStartEvent.getXmlRowNumber());
    assertFalse(actualAlfrescoStartEvent.isAsynchronous());
    assertFalse(actualAlfrescoStartEvent.isNotExclusive());
    assertFalse(actualAlfrescoStartEvent.isInterrupting());
    assertTrue(actualAlfrescoStartEvent.getEventDefinitions().isEmpty());
    assertTrue(actualAlfrescoStartEvent.getExecutionListeners().isEmpty());
    assertTrue(actualAlfrescoStartEvent.getIncomingFlows().isEmpty());
    assertTrue(actualAlfrescoStartEvent.getOutgoingFlows().isEmpty());
    assertTrue(actualAlfrescoStartEvent.getFormProperties().isEmpty());
    assertTrue(actualAlfrescoStartEvent.getAttributes().isEmpty());
    assertTrue(actualAlfrescoStartEvent.getExtensionElements().isEmpty());
  }
}
