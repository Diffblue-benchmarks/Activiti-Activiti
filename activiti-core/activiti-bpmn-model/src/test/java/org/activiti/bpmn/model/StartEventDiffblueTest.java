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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.alfresco.AlfrescoStartEvent;
import org.junit.Test;
import org.mockito.Mockito;

public class StartEventDiffblueTest {
  /**
   * Method under test: {@link StartEvent#clone()}
   */
  @Test
  public void testClone() {
    // Arrange and Act
    StartEvent actualCloneResult = (new StartEvent()).clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getInitiator());
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
   * Method under test: {@link StartEvent#clone()}
   */
  @Test
  public void testClone2() {
    // Arrange
    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    // Act
    StartEvent actualCloneResult = startEvent.clone();

    // Assert
    List<EventDefinition> eventDefinitions2 = actualCloneResult.getEventDefinitions();
    assertEquals(1, eventDefinitions2.size());
    EventDefinition getResult = eventDefinitions2.get(0);
    assertTrue(getResult instanceof CancelEventDefinition);
    assertNull(actualCloneResult.getBehavior());
    assertNull(getResult.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getInitiator());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(actualCloneResult.isInterrupting());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Method under test: {@link StartEvent#clone()}
   */
  @Test
  public void testClone3() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setExtensionElements(null);
    formProperty.setAttributes(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent startEvent = new StartEvent();
    startEvent.setFormProperties(formProperties);

    // Act
    StartEvent actualCloneResult = startEvent.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    List<FormProperty> formProperties2 = actualCloneResult.getFormProperties();
    assertEquals(1, formProperties2.size());
    FormProperty getResult = formProperties2.get(0);
    assertNull(getResult.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(getResult.getDatePattern());
    assertNull(getResult.getDefaultExpression());
    assertNull(getResult.getExpression());
    assertNull(getResult.getName());
    assertNull(getResult.getType());
    assertNull(getResult.getVariable());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getInitiator());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(getResult.isRequired());
    assertFalse(actualCloneResult.isInterrupting());
    assertTrue(actualCloneResult.getEventDefinitions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getFormValues().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertTrue(getResult.isReadable());
    assertTrue(getResult.isWriteable());
  }

  /**
   * Method under test: {@link StartEvent#clone()}
   */
  @Test
  public void testClone4() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent startEvent = new StartEvent();
    startEvent.setFormProperties(formProperties);

    // Act
    StartEvent actualCloneResult = startEvent.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    List<FormProperty> formProperties2 = actualCloneResult.getFormProperties();
    assertEquals(1, formProperties2.size());
    FormProperty getResult = formProperties2.get(0);
    assertNull(getResult.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(getResult.getDatePattern());
    assertNull(getResult.getDefaultExpression());
    assertNull(getResult.getExpression());
    assertNull(getResult.getName());
    assertNull(getResult.getType());
    assertNull(getResult.getVariable());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getInitiator());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(getResult.isRequired());
    assertFalse(actualCloneResult.isInterrupting());
    assertTrue(actualCloneResult.getEventDefinitions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getFormValues().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertTrue(getResult.isReadable());
    assertTrue(getResult.isWriteable());
  }

  /**
   * Method under test: {@link StartEvent#clone()}
   */
  @Test
  public void testClone5() {
    // Arrange
    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent startEvent = new StartEvent();
    startEvent.setFormProperties(formProperties);

    // Act
    StartEvent actualCloneResult = startEvent.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    List<FormProperty> formProperties2 = actualCloneResult.getFormProperties();
    assertEquals(1, formProperties2.size());
    FormProperty getResult = formProperties2.get(0);
    assertNull(getResult.getId());
    List<FormValue> formValues2 = getResult.getFormValues();
    assertEquals(1, formValues2.size());
    FormValue getResult2 = formValues2.get(0);
    assertNull(getResult2.getId());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(getResult.getDatePattern());
    assertNull(getResult.getDefaultExpression());
    assertNull(getResult.getExpression());
    assertNull(getResult.getName());
    assertNull(getResult.getType());
    assertNull(getResult.getVariable());
    assertNull(getResult2.getName());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getInitiator());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult2.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertEquals(0, getResult2.getXmlRowNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(getResult.isRequired());
    assertFalse(actualCloneResult.isInterrupting());
    assertTrue(actualCloneResult.getEventDefinitions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult2.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(getResult2.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertTrue(getResult.isReadable());
    assertTrue(getResult.isWriteable());
  }

  /**
   * Method under test: {@link StartEvent#clone()}
   */
  @Test
  public void testClone6() {
    // Arrange
    StartEvent startEvent = new StartEvent();
    startEvent.setFormProperties(null);

    // Act
    StartEvent actualCloneResult = startEvent.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getInitiator());
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
   * Method under test: {@link StartEvent#clone()}
   */
  @Test
  public void testClone7() {
    // Arrange and Act
    AlfrescoStartEvent actualCloneResult = (new AlfrescoStartEvent()).clone();

    // Assert
    assertTrue(actualCloneResult instanceof AlfrescoStartEvent);
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getInitiator());
    assertNull(((AlfrescoStartEvent) actualCloneResult).getRunAs());
    assertNull(((AlfrescoStartEvent) actualCloneResult).getScriptProcessor());
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
   * Method under test: {@link StartEvent#clone()}
   */
  @Test
  public void testClone8() {
    // Arrange
    StartEvent startEvent = new StartEvent();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    startEvent.addAttribute(attribute);

    // Act
    StartEvent actualCloneResult = startEvent.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getInitiator());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(actualCloneResult.isInterrupting());
    assertTrue(actualCloneResult.getEventDefinitions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Method under test: {@link StartEvent#clone()}
   */
  @Test
  public void testClone9() {
    // Arrange
    StartEvent startEvent = new StartEvent();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    startEvent.addAttribute(attribute);
    ExtensionAttribute attribute2 = new ExtensionAttribute("Name");
    startEvent.addAttribute(attribute2);

    // Act
    StartEvent actualCloneResult = startEvent.clone();

    // Assert
    assertNull(actualCloneResult.getBehavior());
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getFormKey());
    assertNull(actualCloneResult.getInitiator());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    Map<String, List<ExtensionAttribute>> attributes = actualCloneResult.getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    List<ExtensionAttribute> getResult2 = attributes.get("Name");
    assertEquals(1, getResult2.size());
    assertFalse(actualCloneResult.isAsynchronous());
    assertFalse(actualCloneResult.isNotExclusive());
    assertFalse(actualCloneResult.isInterrupting());
    assertTrue(actualCloneResult.getEventDefinitions().isEmpty());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getIncomingFlows().isEmpty());
    assertTrue(actualCloneResult.getOutgoingFlows().isEmpty());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
    assertSame(attribute, getResult.get(0));
    assertSame(attribute2, getResult2.get(0));
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues() {
    // Arrange
    StartEvent startEvent = new StartEvent();
    StartEvent otherEvent = new StartEvent();

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    assertNull(otherEvent.getId());
    assertNull(otherEvent.getDocumentation());
    assertNull(otherEvent.getName());
    assertNull(otherEvent.getFormKey());
    assertNull(otherEvent.getInitiator());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getFormProperties().isEmpty());
    assertTrue(otherEvent.isExclusive());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues2() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    StartEvent otherEvent = new StartEvent();
    otherEvent.setEventDefinitions(eventDefinitions);

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    assertNull(otherEvent.getId());
    assertNull(otherEvent.getDocumentation());
    assertNull(otherEvent.getName());
    assertNull(otherEvent.getFormKey());
    assertNull(otherEvent.getInitiator());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getFormProperties().isEmpty());
    assertTrue(otherEvent.isExclusive());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues3() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    StartEvent otherEvent = new StartEvent();
    otherEvent.setExtensionElements(null);

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    assertNull(otherEvent.getId());
    assertNull(otherEvent.getDocumentation());
    assertNull(otherEvent.getName());
    assertNull(otherEvent.getFormKey());
    assertNull(otherEvent.getInitiator());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getFormProperties().isEmpty());
    assertTrue(otherEvent.isExclusive());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues4() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    StartEvent otherEvent = new StartEvent();
    otherEvent.setFormProperties(null);

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    assertNull(otherEvent.getId());
    assertNull(otherEvent.getDocumentation());
    assertNull(otherEvent.getName());
    assertNull(otherEvent.getFormKey());
    assertNull(otherEvent.getInitiator());
    assertNull(otherEvent.getFormProperties());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.isExclusive());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues5() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    FormProperty formProperty = new FormProperty();
    formProperty.setExtensionElements(null);
    formProperty.setAttributes(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent otherEvent = new StartEvent();
    otherEvent.setFormProperties(formProperties);

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    assertNull(otherEvent.getId());
    assertNull(otherEvent.getDocumentation());
    assertNull(otherEvent.getName());
    assertNull(otherEvent.getFormKey());
    assertNull(otherEvent.getInitiator());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.isExclusive());
    assertSame(formProperties, otherEvent.getFormProperties());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues6() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent otherEvent = new StartEvent();
    otherEvent.setFormProperties(formProperties);

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    assertNull(otherEvent.getId());
    assertNull(otherEvent.getDocumentation());
    assertNull(otherEvent.getName());
    assertNull(otherEvent.getFormKey());
    assertNull(otherEvent.getInitiator());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.isExclusive());
    assertSame(formProperties, otherEvent.getFormProperties());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues7() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent otherEvent = new StartEvent();
    otherEvent.setFormProperties(formProperties);

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    assertNull(otherEvent.getId());
    assertNull(otherEvent.getDocumentation());
    assertNull(otherEvent.getName());
    assertNull(otherEvent.getFormKey());
    assertNull(otherEvent.getInitiator());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.isExclusive());
    assertSame(formProperties, otherEvent.getFormProperties());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues8() {
    // Arrange
    StartEvent startEvent = new StartEvent();
    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
    when(otherEvent.getEventDefinitions()).thenReturn(new ArrayList<>());
    when(otherEvent.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherEvent.getFormProperties()).thenReturn(new ArrayList<>());
    when(otherEvent.getAttributes()).thenReturn(new HashMap<>());
    when(otherEvent.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    verify(otherEvent, atLeast(1)).getAttributes();
    verify(otherEvent, atLeast(1)).getExtensionElements();
    verify(otherEvent).getId();
    verify(otherEvent, atLeast(1)).getEventDefinitions();
    verify(otherEvent).getDocumentation();
    verify(otherEvent, atLeast(1)).getExecutionListeners();
    verify(otherEvent).getName();
    verify(otherEvent).isAsynchronous();
    verify(otherEvent).isNotExclusive();
    verify(otherEvent).getFormKey();
    verify(otherEvent, atLeast(1)).getFormProperties();
    verify(otherEvent).getInitiator();
    assertEquals("42", startEvent.getId());
    assertEquals("Documentation", startEvent.getDocumentation());
    assertEquals("Form Key", startEvent.getFormKey());
    assertEquals("Initiator", startEvent.getInitiator());
    assertEquals("Name", startEvent.getName());
    assertFalse(startEvent.isExclusive());
    assertTrue(startEvent.getFormProperties().isEmpty());
    assertTrue(startEvent.isAsynchronous());
    assertTrue(startEvent.isNotExclusive());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues9() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(new FormProperty());
    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
    when(otherEvent.getEventDefinitions()).thenReturn(new ArrayList<>());
    when(otherEvent.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherEvent.getFormProperties()).thenReturn(formPropertyList);
    when(otherEvent.getAttributes()).thenReturn(new HashMap<>());
    when(otherEvent.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    verify(otherEvent, atLeast(1)).getAttributes();
    verify(otherEvent, atLeast(1)).getExtensionElements();
    verify(otherEvent).getId();
    verify(otherEvent, atLeast(1)).getEventDefinitions();
    verify(otherEvent).getDocumentation();
    verify(otherEvent, atLeast(1)).getExecutionListeners();
    verify(otherEvent).getName();
    verify(otherEvent).isAsynchronous();
    verify(otherEvent).isNotExclusive();
    verify(otherEvent).getFormKey();
    verify(otherEvent, atLeast(1)).getFormProperties();
    verify(otherEvent).getInitiator();
    assertEquals("42", startEvent.getId());
    assertEquals("Documentation", startEvent.getDocumentation());
    assertEquals("Form Key", startEvent.getFormKey());
    assertEquals("Initiator", startEvent.getInitiator());
    assertEquals("Name", startEvent.getName());
    List<FormProperty> formProperties = startEvent.getFormProperties();
    assertEquals(1, formProperties.size());
    FormProperty getResult = formProperties.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getDatePattern());
    assertNull(getResult.getDefaultExpression());
    assertNull(getResult.getExpression());
    assertNull(getResult.getName());
    assertNull(getResult.getType());
    assertNull(getResult.getVariable());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertFalse(startEvent.isExclusive());
    assertFalse(getResult.isRequired());
    assertTrue(getResult.getFormValues().isEmpty());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
    assertTrue(startEvent.isAsynchronous());
    assertTrue(startEvent.isNotExclusive());
    assertTrue(getResult.isReadable());
    assertTrue(getResult.isWriteable());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues10() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("foo", new ArrayList<>());
    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
    when(otherEvent.getEventDefinitions()).thenReturn(new ArrayList<>());
    when(otherEvent.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherEvent.getFormProperties()).thenReturn(new ArrayList<>());
    when(otherEvent.getAttributes()).thenReturn(stringListMap);
    when(otherEvent.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    verify(otherEvent, atLeast(1)).getAttributes();
    verify(otherEvent, atLeast(1)).getExtensionElements();
    verify(otherEvent).getId();
    verify(otherEvent, atLeast(1)).getEventDefinitions();
    verify(otherEvent).getDocumentation();
    verify(otherEvent, atLeast(1)).getExecutionListeners();
    verify(otherEvent).getName();
    verify(otherEvent).isAsynchronous();
    verify(otherEvent).isNotExclusive();
    verify(otherEvent).getFormKey();
    verify(otherEvent, atLeast(1)).getFormProperties();
    verify(otherEvent).getInitiator();
    assertEquals("42", startEvent.getId());
    assertEquals("Documentation", startEvent.getDocumentation());
    assertEquals("Form Key", startEvent.getFormKey());
    assertEquals("Initiator", startEvent.getInitiator());
    assertEquals("Name", startEvent.getName());
    assertFalse(startEvent.isExclusive());
    assertTrue(startEvent.getFormProperties().isEmpty());
    assertTrue(startEvent.isAsynchronous());
    assertTrue(startEvent.isNotExclusive());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues11() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("foo", new ArrayList<>());
    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
    when(otherEvent.getEventDefinitions()).thenReturn(new ArrayList<>());
    when(otherEvent.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherEvent.getFormProperties()).thenReturn(new ArrayList<>());
    when(otherEvent.getAttributes()).thenReturn(stringListMap);
    when(otherEvent.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    verify(otherEvent, atLeast(1)).getAttributes();
    verify(otherEvent, atLeast(1)).getExtensionElements();
    verify(otherEvent).getId();
    verify(otherEvent, atLeast(1)).getEventDefinitions();
    verify(otherEvent).getDocumentation();
    verify(otherEvent, atLeast(1)).getExecutionListeners();
    verify(otherEvent).getName();
    verify(otherEvent).isAsynchronous();
    verify(otherEvent).isNotExclusive();
    verify(otherEvent).getFormKey();
    verify(otherEvent, atLeast(1)).getFormProperties();
    verify(otherEvent).getInitiator();
    assertEquals("42", startEvent.getId());
    assertEquals("Documentation", startEvent.getDocumentation());
    assertEquals("Form Key", startEvent.getFormKey());
    assertEquals("Initiator", startEvent.getInitiator());
    assertEquals("Name", startEvent.getName());
    assertFalse(startEvent.isExclusive());
    assertTrue(startEvent.getFormProperties().isEmpty());
    assertTrue(startEvent.isAsynchronous());
    assertTrue(startEvent.isNotExclusive());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues12() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("foo", new ArrayList<>());
    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
    when(otherEvent.getEventDefinitions()).thenReturn(new ArrayList<>());
    when(otherEvent.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherEvent.getFormProperties()).thenReturn(new ArrayList<>());
    when(otherEvent.getAttributes()).thenReturn(new HashMap<>());
    when(otherEvent.getExtensionElements()).thenReturn(stringListMap);

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    verify(otherEvent, atLeast(1)).getAttributes();
    verify(otherEvent, atLeast(1)).getExtensionElements();
    verify(otherEvent).getId();
    verify(otherEvent, atLeast(1)).getEventDefinitions();
    verify(otherEvent).getDocumentation();
    verify(otherEvent, atLeast(1)).getExecutionListeners();
    verify(otherEvent).getName();
    verify(otherEvent).isAsynchronous();
    verify(otherEvent).isNotExclusive();
    verify(otherEvent).getFormKey();
    verify(otherEvent, atLeast(1)).getFormProperties();
    verify(otherEvent).getInitiator();
    assertEquals("42", startEvent.getId());
    assertEquals("Documentation", startEvent.getDocumentation());
    assertEquals("Form Key", startEvent.getFormKey());
    assertEquals("Initiator", startEvent.getInitiator());
    assertEquals("Name", startEvent.getName());
    assertFalse(startEvent.isExclusive());
    assertTrue(startEvent.getFormProperties().isEmpty());
    assertTrue(startEvent.isAsynchronous());
    assertTrue(startEvent.isNotExclusive());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues13() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("foo", new ArrayList<>());
    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
    when(otherEvent.getEventDefinitions()).thenReturn(new ArrayList<>());
    when(otherEvent.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherEvent.getFormProperties()).thenReturn(new ArrayList<>());
    when(otherEvent.getAttributes()).thenReturn(new HashMap<>());
    when(otherEvent.getExtensionElements()).thenReturn(stringListMap);

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    verify(otherEvent, atLeast(1)).getAttributes();
    verify(otherEvent, atLeast(1)).getExtensionElements();
    verify(otherEvent).getId();
    verify(otherEvent, atLeast(1)).getEventDefinitions();
    verify(otherEvent).getDocumentation();
    verify(otherEvent, atLeast(1)).getExecutionListeners();
    verify(otherEvent).getName();
    verify(otherEvent).isAsynchronous();
    verify(otherEvent).isNotExclusive();
    verify(otherEvent).getFormKey();
    verify(otherEvent, atLeast(1)).getFormProperties();
    verify(otherEvent).getInitiator();
    assertEquals("42", startEvent.getId());
    assertEquals("Documentation", startEvent.getDocumentation());
    assertEquals("Form Key", startEvent.getFormKey());
    assertEquals("Initiator", startEvent.getInitiator());
    assertEquals("Name", startEvent.getName());
    assertFalse(startEvent.isExclusive());
    assertTrue(startEvent.getFormProperties().isEmpty());
    assertTrue(startEvent.isAsynchronous());
    assertTrue(startEvent.isNotExclusive());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues14() {
    // Arrange
    StartEvent startEvent = new StartEvent();
    FormProperty formProperty = mock(FormProperty.class);
    FormProperty formProperty2 = new FormProperty();
    when(formProperty.clone()).thenReturn(formProperty2);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);
    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
    when(otherEvent.getEventDefinitions()).thenReturn(new ArrayList<>());
    when(otherEvent.getExecutionListeners()).thenReturn(new ArrayList<>());
    when(otherEvent.getFormProperties()).thenReturn(formPropertyList);
    when(otherEvent.getAttributes()).thenReturn(new HashMap<>());
    when(otherEvent.getExtensionElements()).thenReturn(new HashMap<>());

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    verify(otherEvent, atLeast(1)).getAttributes();
    verify(otherEvent, atLeast(1)).getExtensionElements();
    verify(otherEvent).getId();
    verify(otherEvent, atLeast(1)).getEventDefinitions();
    verify(otherEvent).getDocumentation();
    verify(otherEvent, atLeast(1)).getExecutionListeners();
    verify(otherEvent).getName();
    verify(otherEvent).isAsynchronous();
    verify(otherEvent).isNotExclusive();
    verify(formProperty).clone();
    verify(otherEvent).getFormKey();
    verify(otherEvent, atLeast(1)).getFormProperties();
    verify(otherEvent).getInitiator();
    assertEquals("42", startEvent.getId());
    assertEquals("Documentation", startEvent.getDocumentation());
    assertEquals("Form Key", startEvent.getFormKey());
    assertEquals("Initiator", startEvent.getInitiator());
    assertEquals("Name", startEvent.getName());
    List<FormProperty> formProperties = startEvent.getFormProperties();
    assertEquals(1, formProperties.size());
    assertFalse(startEvent.isExclusive());
    assertTrue(startEvent.isAsynchronous());
    assertTrue(startEvent.isNotExclusive());
    assertSame(formProperty2, formProperties.get(0));
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues15() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    CancelEventDefinition cancelEventDefinition = new CancelEventDefinition();
    cancelEventDefinition.addAttribute(new ExtensionAttribute("Name"));

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(cancelEventDefinition);

    StartEvent otherEvent = new StartEvent();
    otherEvent.setEventDefinitions(eventDefinitions);

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    assertNull(otherEvent.getId());
    assertNull(otherEvent.getDocumentation());
    assertNull(otherEvent.getName());
    assertNull(otherEvent.getFormKey());
    assertNull(otherEvent.getInitiator());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getFormProperties().isEmpty());
    assertTrue(otherEvent.isExclusive());
  }

  /**
   * Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  public void testSetValues16() {
    // Arrange
    StartEvent startEvent = new StartEvent();
    FormValue formValue = mock(FormValue.class);
    when(formValue.clone()).thenReturn(new FormValue());

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent otherEvent = new StartEvent();
    otherEvent.setFormProperties(formProperties);

    // Act
    startEvent.setValues(otherEvent);

    // Assert
    verify(formValue).clone();
    assertNull(otherEvent.getId());
    assertNull(otherEvent.getDocumentation());
    assertNull(otherEvent.getName());
    assertNull(otherEvent.getFormKey());
    assertNull(otherEvent.getInitiator());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.isExclusive());
    assertSame(formProperties, otherEvent.getFormProperties());
  }

  /**
   * Method under test: {@link StartEvent#accept(ReferenceOverrider)}
   */
  @Test
  public void testAccept() {
    // Arrange
    StartEvent startEvent = new StartEvent();
    ReferenceOverrider referenceOverrider = mock(ReferenceOverrider.class);
    doNothing().when(referenceOverrider).override(Mockito.<StartEvent>any());

    // Act
    startEvent.accept(referenceOverrider);

    // Assert
    verify(referenceOverrider).override(isA(StartEvent.class));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link StartEvent}
   *   <li>{@link StartEvent#setFormKey(String)}
   *   <li>{@link StartEvent#setFormProperties(List)}
   *   <li>{@link StartEvent#setInitiator(String)}
   *   <li>{@link StartEvent#setInterrupting(boolean)}
   *   <li>{@link StartEvent#getFormKey()}
   *   <li>{@link StartEvent#getFormProperties()}
   *   <li>{@link StartEvent#getInitiator()}
   *   <li>{@link StartEvent#isInterrupting()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    StartEvent actualStartEvent = new StartEvent();
    actualStartEvent.setFormKey("Form Key");
    ArrayList<FormProperty> formProperties = new ArrayList<>();
    actualStartEvent.setFormProperties(formProperties);
    actualStartEvent.setInitiator("Initiator");
    actualStartEvent.setInterrupting(true);
    String actualFormKey = actualStartEvent.getFormKey();
    List<FormProperty> actualFormProperties = actualStartEvent.getFormProperties();
    String actualInitiator = actualStartEvent.getInitiator();
    boolean actualIsInterruptingResult = actualStartEvent.isInterrupting();

    // Assert that nothing has changed
    assertEquals("Form Key", actualFormKey);
    assertEquals("Initiator", actualInitiator);
    assertEquals(0, actualStartEvent.getXmlColumnNumber());
    assertEquals(0, actualStartEvent.getXmlRowNumber());
    assertFalse(actualStartEvent.isAsynchronous());
    assertFalse(actualStartEvent.isNotExclusive());
    assertTrue(actualStartEvent.getEventDefinitions().isEmpty());
    assertTrue(actualStartEvent.getExecutionListeners().isEmpty());
    assertTrue(actualStartEvent.getIncomingFlows().isEmpty());
    assertTrue(actualStartEvent.getOutgoingFlows().isEmpty());
    assertTrue(actualFormProperties.isEmpty());
    assertTrue(actualStartEvent.getAttributes().isEmpty());
    assertTrue(actualStartEvent.getExtensionElements().isEmpty());
    assertTrue(actualIsInterruptingResult);
    assertSame(formProperties, actualFormProperties);
  }
}
