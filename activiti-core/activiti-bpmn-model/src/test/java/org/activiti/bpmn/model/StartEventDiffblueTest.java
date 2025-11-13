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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.alfresco.AlfrescoStartEvent;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class StartEventDiffblueTest {
  /**
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Given {@link AlfrescoStartEvent} (default constructor).
   *   <li>Then return {@link AlfrescoStartEvent}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_givenAlfrescoStartEvent_thenReturnAlfrescoStartEvent() {
    // Arrange and Act
    AlfrescoStartEvent actualCloneResult = new AlfrescoStartEvent().clone();

    // Assert
    assertTrue(actualCloneResult instanceof AlfrescoStartEvent);
    assertNull(((AlfrescoStartEvent) actualCloneResult).getRunAs());
    assertNull(((AlfrescoStartEvent) actualCloneResult).getScriptProcessor());
    assertTrue(actualCloneResult.getFormProperties().isEmpty());
  }

  /**
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) ExtensionElements is {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_givenFormPropertyExtensionElementsIsHashMap() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setExtensionElements(new HashMap<>());
    formProperty.setAttributes(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent startEvent = new StartEvent();
    startEvent.setFormProperties(formProperties);

    // Act and Assert
    List<FormProperty> formProperties2 = startEvent.clone().getFormProperties();
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
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) ExtensionElements is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_givenFormPropertyExtensionElementsIsNull() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setExtensionElements(null);
    formProperty.setAttributes(new HashMap<>());

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent startEvent = new StartEvent();
    startEvent.setFormProperties(formProperties);

    // Act and Assert
    List<FormProperty> formProperties2 = startEvent.clone().getFormProperties();
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
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) FormValues is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_givenFormPropertyFormValuesIsArrayList() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(new ArrayList<>());

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent startEvent = new StartEvent();
    startEvent.setFormProperties(formProperties);

    // Act and Assert
    List<FormProperty> formProperties2 = startEvent.clone().getFormProperties();
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
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) FormValues is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_givenFormPropertyFormValuesIsNull() {
    // Arrange
    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent startEvent = new StartEvent();
    startEvent.setFormProperties(formProperties);

    // Act and Assert
    List<FormProperty> formProperties2 = startEvent.clone().getFormProperties();
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
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.
   *   <li>Then return FormProperties first Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_givenHashMap42IsArrayList_thenReturnFormPropertiesFirstIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("42", new ArrayList<>());
    extensionElements.put("Key", new ArrayList<>());

    FormProperty formProperty = new FormProperty();
    formProperty.setExtensionElements(extensionElements);
    formProperty.setAttributes(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent startEvent = new StartEvent();
    startEvent.setFormProperties(formProperties);

    // Act and Assert
    List<FormProperty> formProperties2 = startEvent.clone().getFormProperties();
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
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code Key} is {@link ArrayList#ArrayList()}.
   *   <li>Then return FormProperties first Id is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_givenHashMapKeyIsArrayList_thenReturnFormPropertiesFirstIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("Key", new ArrayList<>());

    FormProperty formProperty = new FormProperty();
    formProperty.setExtensionElements(extensionElements);
    formProperty.setAttributes(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent startEvent = new StartEvent();
    startEvent.setFormProperties(formProperties);

    // Act and Assert
    List<FormProperty> formProperties2 = startEvent.clone().getFormProperties();
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
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Given {@link StartEvent} (default constructor) FormProperties is {@code null}.
   *   <li>Then return Behavior is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_givenStartEventFormPropertiesIsNull_thenReturnBehaviorIsNull() {
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
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Given {@link StartEvent} (default constructor).
   *   <li>Then return Behavior is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_givenStartEvent_thenReturnBehaviorIsNull() {
    // Arrange and Act
    StartEvent actualCloneResult = new StartEvent().clone();

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
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
    assertTrue(actualCloneResult.isExclusive());
  }

  /**
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Then return Attributes size is one.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_thenReturnAttributesSizeIsOne() {
    // Arrange
    StartEvent startEvent = new StartEvent();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    startEvent.addAttribute(attribute);

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = startEvent.clone().getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("Name");
    assertEquals(1, getResult.size());
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Then return Attributes size is two.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_thenReturnAttributesSizeIsTwo() {
    // Arrange
    StartEvent startEvent = new StartEvent();
    ExtensionAttribute attribute = new ExtensionAttribute("42");
    startEvent.addAttribute(attribute);
    startEvent.addAttribute(new ExtensionAttribute("Name"));

    // Act and Assert
    Map<String, List<ExtensionAttribute>> attributes = startEvent.clone().getAttributes();
    assertEquals(2, attributes.size());
    List<ExtensionAttribute> getResult = attributes.get("42");
    assertEquals(1, getResult.size());
    assertTrue(attributes.containsKey("Name"));
    assertSame(attribute, getResult.get(0));
  }

  /**
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Then return EventDefinitions size is one.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_thenReturnEventDefinitionsSizeIsOne() {
    // Arrange
    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    StartEvent startEvent = new StartEvent();
    startEvent.setEventDefinitions(eventDefinitions);

    // Act and Assert
    List<EventDefinition> eventDefinitions2 = startEvent.clone().getEventDefinitions();
    assertEquals(1, eventDefinitions2.size());
    EventDefinition getResult = eventDefinitions2.get(0);
    assertTrue(getResult instanceof CancelEventDefinition);
    assertNull(getResult.getId());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link StartEvent#clone()}.
   *
   * <ul>
   *   <li>Then return FormProperties first FormValues size is one.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#clone()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"StartEvent StartEvent.clone()"})
  public void testClone_thenReturnFormPropertiesFirstFormValuesSizeIsOne() {
    // Arrange
    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent startEvent = new StartEvent();
    startEvent.setFormProperties(formProperties);

    // Act and Assert
    List<FormProperty> formProperties2 = startEvent.clone().getFormProperties();
    assertEquals(1, formProperties2.size());
    List<FormValue> formValues2 = formProperties2.get(0).getFormValues();
    assertEquals(1, formValues2.size());
    FormValue getResult = formValues2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    CancelEventDefinition cancelEventDefinition = new CancelEventDefinition();
    ExtensionAttribute attribute = new ExtensionAttribute("Name");
    cancelEventDefinition.addAttribute(attribute);

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(cancelEventDefinition);

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(otherEvent.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherEvent.getFormProperties()).thenReturn(formPropertyList);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
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
    List<EventDefinition> eventDefinitions = startEvent.getEventDefinitions();
    assertEquals(1, eventDefinitions.size());
    EventDefinition getResult = eventDefinitions.get(0);
    assertTrue(getResult instanceof CancelEventDefinition);
    Map<String, List<ExtensionAttribute>> attributes = getResult.getAttributes();
    assertEquals(1, attributes.size());
    List<ExtensionAttribute> getResult2 = attributes.get("Name");
    assertEquals(1, getResult2.size());
    assertSame(attribute, getResult2.get(0));
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) ExtensionElements is {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_givenFormPropertyExtensionElementsIsHashMap() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    FormProperty formProperty = new FormProperty();
    formProperty.setExtensionElements(new HashMap<>());
    formProperty.setAttributes(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent otherEvent = new StartEvent();
    otherEvent.setFormProperties(formProperties);

    // Act
    startEvent.setValues(otherEvent);

    // Assert that nothing has changed
    List<FormProperty> formProperties2 = otherEvent.getFormProperties();
    assertEquals(1, formProperties2.size());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getEventDefinitions().isEmpty());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.isExclusive());
    assertSame(formProperties, formProperties2);
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) ExtensionElements is {@link
   *       HashMap#HashMap()}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_givenFormPropertyExtensionElementsIsHashMap2() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("Key", new ArrayList<>());

    FormProperty formProperty = new FormProperty();
    formProperty.setExtensionElements(extensionElements);
    formProperty.setAttributes(null);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent otherEvent = new StartEvent();
    otherEvent.setFormProperties(formProperties);

    // Act
    startEvent.setValues(otherEvent);

    // Assert that nothing has changed
    List<FormProperty> formProperties2 = otherEvent.getFormProperties();
    assertEquals(1, formProperties2.size());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getEventDefinitions().isEmpty());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.isExclusive());
    assertSame(formProperties, formProperties2);
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) FormValues is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_givenFormPropertyFormValuesIsNull() {
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

    // Assert that nothing has changed
    List<FormProperty> formProperties2 = otherEvent.getFormProperties();
    assertEquals(1, formProperties2.size());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getEventDefinitions().isEmpty());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.isExclusive());
    assertSame(formProperties, formProperties2);
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Given {@link FormValue} {@link FormValue#clone()} return {@link FormValue} (default
   *       constructor).
   *   <li>Then calls {@link FormValue#clone()}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_givenFormValueCloneReturnFormValue_thenCallsClone() {
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

    // Assert that nothing has changed
    verify(formValue).clone();
    List<FormProperty> formProperties2 = otherEvent.getFormProperties();
    assertEquals(1, formProperties2.size());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getEventDefinitions().isEmpty());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.isExclusive());
    assertSame(formProperties, formProperties2);
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.
   *   <li>Then {@link StartEvent} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_givenHashMap42IsArrayList_thenStartEventIdIs42() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    when(formProperty.clone()).thenReturn(new FormProperty());
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("Key", new ArrayList<>());

    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(otherEvent.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherEvent.getFormProperties()).thenReturn(formPropertyList);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
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
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
    verify(otherEvent).getFormKey();
    verify(otherEvent, atLeast(1)).getFormProperties();
    verify(otherEvent).getInitiator();
    assertEquals("42", startEvent.getId());
    assertEquals("Documentation", startEvent.getDocumentation());
    assertEquals("Form Key", startEvent.getFormKey());
    assertEquals("Initiator", startEvent.getInitiator());
    assertEquals("Name", startEvent.getName());
    assertEquals(1, startEvent.getExecutionListeners().size());
    assertFalse(startEvent.isExclusive());
    assertTrue(startEvent.isAsynchronous());
    assertTrue(startEvent.isNotExclusive());
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code 42} is {@link ArrayList#ArrayList()}.
   *   <li>Then {@link StartEvent} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_givenHashMap42IsArrayList_thenStartEventIdIs422() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    when(formProperty.clone()).thenReturn(new FormProperty());
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("42", new ArrayList<>());
    stringListMap.put("Key", new ArrayList<>());

    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(otherEvent.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherEvent.getFormProperties()).thenReturn(formPropertyList);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
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
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
    verify(otherEvent).getFormKey();
    verify(otherEvent, atLeast(1)).getFormProperties();
    verify(otherEvent).getInitiator();
    assertEquals("42", startEvent.getId());
    assertEquals("Documentation", startEvent.getDocumentation());
    assertEquals("Form Key", startEvent.getFormKey());
    assertEquals("Initiator", startEvent.getInitiator());
    assertEquals("Name", startEvent.getName());
    assertEquals(1, startEvent.getExecutionListeners().size());
    assertFalse(startEvent.isExclusive());
    assertTrue(startEvent.isAsynchronous());
    assertTrue(startEvent.isNotExclusive());
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link StartEvent} (default constructor) ExtensionElements is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_givenNull_whenStartEventExtensionElementsIsNull() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    StartEvent otherEvent = new StartEvent();
    otherEvent.setExtensionElements(null);

    // Act
    startEvent.setValues(otherEvent);

    // Assert that nothing has changed
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getEventDefinitions().isEmpty());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.getFormProperties().isEmpty());
    assertTrue(otherEvent.isExclusive());
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link StartEvent} (default constructor) FormProperties is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_givenNull_whenStartEventFormPropertiesIsNull() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    StartEvent otherEvent = new StartEvent();
    otherEvent.setFormProperties(null);

    // Act
    startEvent.setValues(otherEvent);

    // Assert that nothing has changed
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getEventDefinitions().isEmpty());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.isExclusive());
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Then {@link StartEvent} (default constructor) EventDefinitions is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_thenStartEventEventDefinitionsIsArrayList() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());

    StartEvent otherEvent = new StartEvent();
    otherEvent.setEventDefinitions(eventDefinitions);

    // Act
    startEvent.setValues(otherEvent);

    // Assert that nothing has changed
    List<EventDefinition> eventDefinitions2 = otherEvent.getEventDefinitions();
    assertEquals(1, eventDefinitions2.size());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.getFormProperties().isEmpty());
    assertTrue(otherEvent.isExclusive());
    assertSame(eventDefinitions, eventDefinitions2);
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Then {@link StartEvent} (default constructor) FormProperties first FormValues size is
   *       one.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_thenStartEventFormPropertiesFirstFormValuesSizeIsOne() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(otherEvent.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherEvent.getFormProperties()).thenReturn(formPropertyList);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
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
    List<FormProperty> formProperties = startEvent.getFormProperties();
    assertEquals(1, formProperties.size());
    List<FormValue> formValues2 = formProperties.get(0).getFormValues();
    assertEquals(1, formValues2.size());
    FormValue getResult = formValues2.get(0);
    assertNull(getResult.getId());
    assertNull(getResult.getName());
    assertEquals(0, getResult.getXmlColumnNumber());
    assertEquals(0, getResult.getXmlRowNumber());
    assertTrue(getResult.getAttributes().isEmpty());
    assertTrue(getResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Then {@link StartEvent} (default constructor) FormProperties is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_thenStartEventFormPropertiesIsArrayList() {
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

    // Assert that nothing has changed
    List<FormProperty> formProperties2 = otherEvent.getFormProperties();
    assertEquals(1, formProperties2.size());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getEventDefinitions().isEmpty());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.isExclusive());
    assertSame(formProperties, formProperties2);
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Then {@link StartEvent} (default constructor) FormProperties is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_thenStartEventFormPropertiesIsArrayList2() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    FormProperty formProperty = new FormProperty();
    formProperty.setFormValues(new ArrayList<>());

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent otherEvent = new StartEvent();
    otherEvent.setFormProperties(formProperties);

    // Act
    startEvent.setValues(otherEvent);

    // Assert that nothing has changed
    List<FormProperty> formProperties2 = otherEvent.getFormProperties();
    assertEquals(1, formProperties2.size());
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getEventDefinitions().isEmpty());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.isExclusive());
    assertSame(formProperties, formProperties2);
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Then {@link StartEvent} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_thenStartEventIdIs42() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    when(formProperty.clone()).thenReturn(new FormProperty());
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(otherEvent.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherEvent.getFormProperties()).thenReturn(formPropertyList);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
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
    verify(formProperty).setFormValues(isA(List.class));
    verify(otherEvent).getFormKey();
    verify(otherEvent, atLeast(1)).getFormProperties();
    verify(otherEvent).getInitiator();
    assertEquals("42", startEvent.getId());
    assertEquals("Documentation", startEvent.getDocumentation());
    assertEquals("Form Key", startEvent.getFormKey());
    assertEquals("Initiator", startEvent.getInitiator());
    assertEquals("Name", startEvent.getName());
    assertEquals(1, startEvent.getExecutionListeners().size());
    assertFalse(startEvent.isExclusive());
    assertTrue(startEvent.isAsynchronous());
    assertTrue(startEvent.isNotExclusive());
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Then {@link StartEvent} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_thenStartEventIdIs422() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    when(formProperty.clone()).thenReturn(new FormProperty());
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("Key", new ArrayList<>());

    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(otherEvent.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherEvent.getFormProperties()).thenReturn(formPropertyList);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
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
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
    verify(otherEvent).getFormKey();
    verify(otherEvent, atLeast(1)).getFormProperties();
    verify(otherEvent).getInitiator();
    assertEquals("42", startEvent.getId());
    assertEquals("Documentation", startEvent.getDocumentation());
    assertEquals("Form Key", startEvent.getFormKey());
    assertEquals("Initiator", startEvent.getInitiator());
    assertEquals("Name", startEvent.getName());
    assertEquals(1, startEvent.getExecutionListeners().size());
    assertFalse(startEvent.isExclusive());
    assertTrue(startEvent.isAsynchronous());
    assertTrue(startEvent.isNotExclusive());
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>Then {@link StartEvent} (default constructor) Id is {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_thenStartEventIdIs423() {
    // Arrange
    StartEvent startEvent = new StartEvent();

    ArrayList<EventDefinition> eventDefinitionList = new ArrayList<>();
    eventDefinitionList.add(new CancelEventDefinition());

    ArrayList<ActivitiListener> activitiListenerList = new ArrayList<>();
    activitiListenerList.add(new ActivitiListener());

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = mock(FormProperty.class);
    when(formProperty.clone()).thenReturn(new FormProperty());
    doNothing().when(formProperty).setFormValues(Mockito.<List<FormValue>>any());
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formPropertyList = new ArrayList<>();
    formPropertyList.add(formProperty);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("Key", new ArrayList<>());

    AlfrescoStartEvent otherEvent = mock(AlfrescoStartEvent.class);
    when(otherEvent.getEventDefinitions()).thenReturn(eventDefinitionList);
    when(otherEvent.getExecutionListeners()).thenReturn(activitiListenerList);
    when(otherEvent.getFormProperties()).thenReturn(formPropertyList);
    when(otherEvent.isAsynchronous()).thenReturn(true);
    when(otherEvent.isNotExclusive()).thenReturn(true);
    when(otherEvent.getId()).thenReturn("42");
    when(otherEvent.getDocumentation()).thenReturn("Documentation");
    when(otherEvent.getName()).thenReturn("Name");
    when(otherEvent.getFormKey()).thenReturn("Form Key");
    when(otherEvent.getInitiator()).thenReturn("Initiator");
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
    verify(formProperty).clone();
    verify(formProperty).setFormValues(isA(List.class));
    verify(otherEvent).getFormKey();
    verify(otherEvent, atLeast(1)).getFormProperties();
    verify(otherEvent).getInitiator();
    assertEquals("42", startEvent.getId());
    assertEquals("Documentation", startEvent.getDocumentation());
    assertEquals("Form Key", startEvent.getFormKey());
    assertEquals("Initiator", startEvent.getInitiator());
    assertEquals("Name", startEvent.getName());
    assertEquals(1, startEvent.getExecutionListeners().size());
    assertFalse(startEvent.isExclusive());
    assertTrue(startEvent.isAsynchronous());
    assertTrue(startEvent.isNotExclusive());
  }

  /**
   * Test {@link StartEvent#setValues(StartEvent)} with {@code StartEvent}.
   *
   * <ul>
   *   <li>When {@link StartEvent} (default constructor).
   *   <li>Then {@link StartEvent} (default constructor) FormProperties Empty.
   * </ul>
   *
   * <p>Method under test: {@link StartEvent#setValues(StartEvent)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.setValues(StartEvent)"})
  public void testSetValuesWithStartEvent_whenStartEvent_thenStartEventFormPropertiesEmpty() {
    // Arrange
    StartEvent startEvent = new StartEvent();
    StartEvent otherEvent = new StartEvent();

    // Act
    startEvent.setValues(otherEvent);

    // Assert that nothing has changed
    assertFalse(otherEvent.isAsynchronous());
    assertFalse(otherEvent.isNotExclusive());
    assertTrue(otherEvent.getEventDefinitions().isEmpty());
    assertTrue(otherEvent.getExecutionListeners().isEmpty());
    assertTrue(otherEvent.getFormProperties().isEmpty());
    assertTrue(otherEvent.isExclusive());
  }

  /**
   * Test {@link StartEvent#accept(ReferenceOverrider)}.
   *
   * <p>Method under test: {@link StartEvent#accept(ReferenceOverrider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartEvent.accept(ReferenceOverrider)"})
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
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void StartEvent.<init>()",
    "String StartEvent.getFormKey()",
    "List StartEvent.getFormProperties()",
    "String StartEvent.getInitiator()",
    "boolean StartEvent.isInterrupting()",
    "void StartEvent.setFormKey(String)",
    "void StartEvent.setFormProperties(List)",
    "void StartEvent.setInitiator(String)",
    "void StartEvent.setInterrupting(boolean)"
  })
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

    // Assert
    assertEquals("Form Key", actualFormKey);
    assertEquals("Initiator", actualInitiator);
    assertNull(actualStartEvent.getBehavior());
    assertNull(actualStartEvent.getId());
    assertNull(actualStartEvent.getDocumentation());
    assertNull(actualStartEvent.getName());
    assertNull(actualStartEvent.getParentContainer());
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
