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
package org.activiti.bpmn.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.CompensateEventDefinition;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.Event;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.FormValue;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.TerminateEventDefinition;
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.UserTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BaseBpmnXMLConverterDiffblueTest {
  /**
   * Test {@link BaseBpmnXMLConverter#convertToXML(XMLStreamWriter, BaseElement, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@link Association} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#convertToXML(XMLStreamWriter, BaseElement,
   * BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test convertToXML(XMLStreamWriter, BaseElement, BpmnModel); when Association (default constructor); then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.convertToXML(XMLStreamWriter, BaseElement, BpmnModel)"
  })
  void testConvertToXML_whenAssociation_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter xtw = new IndentingXMLStreamWriter(writer);
    Association baseElement = new Association();

    // Act
    associationXMLConverter.convertToXML(xtw, baseElement, new BpmnModel());

    // Assert
    verify(writer).writeAttribute("associationDirection", "None");
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement("bpmn2", "association", "http://www.omg.org/spec/BPMN/20100524/MODEL");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeExtensionChildElements(BaseElement, boolean,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@code false}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeExtensionChildElements(BaseElement,
   * boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); when 'false'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseBpmnXMLConverter.writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteExtensionChildElements_whenFalse_thenReturnFalse() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertFalse(
        associationXMLConverter.writeExtensionChildElements(
            element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeExtensionChildElements(BaseElement, boolean,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@code true}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeExtensionChildElements(BaseElement,
   * boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); when 'true'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseBpmnXMLConverter.writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteExtensionChildElements_whenTrue_thenReturnTrue() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertTrue(
        associationXMLConverter.writeExtensionChildElements(
            element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#parseDelimitedList(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#parseDelimitedList(String)}
   */
  @Test
  @DisplayName("Test parseDelimitedList(String); when empty string; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseBpmnXMLConverter.parseDelimitedList(String)"})
  void testParseDelimitedList_whenEmptyString_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new AssociationXMLConverter().parseDelimitedList("").isEmpty());
  }

  /**
   * Test {@link BaseBpmnXMLConverter#parseDelimitedList(String)}.
   *
   * <ul>
   *   <li>When {@code Expression}.
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#parseDelimitedList(String)}
   */
  @Test
  @DisplayName("Test parseDelimitedList(String); when 'Expression'; then return size is one")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseBpmnXMLConverter.parseDelimitedList(String)"})
  void testParseDelimitedList_whenExpression_thenReturnSizeIsOne() {
    // Arrange and Act
    List<String> actualParseDelimitedListResult =
        new AssociationXMLConverter().parseDelimitedList("Expression");

    // Assert
    assertEquals(1, actualParseDelimitedListResult.size());
    assertEquals("Expression", actualParseDelimitedListResult.get(0));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#parseDelimitedList(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#parseDelimitedList(String)}
   */
  @Test
  @DisplayName("Test parseDelimitedList(String); when 'null'; then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"List BaseBpmnXMLConverter.parseDelimitedList(String)"})
  void testParseDelimitedList_whenNull_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(new AssociationXMLConverter().parseDelimitedList(null).isEmpty());
  }

  /**
   * Test {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then return {@code 42,foo}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName(
      "Test convertToDelimitedString(List); given '42'; when ArrayList() add '42'; then return '42,foo'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseBpmnXMLConverter.convertToDelimitedString(List)"})
  void testConvertToDelimitedString_given42_whenArrayListAdd42_thenReturn42Foo() {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("42");
    stringList.add("foo");

    // Act and Assert
    assertEquals("42,foo", associationXMLConverter.convertToDelimitedString(stringList));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}.
   *
   * <ul>
   *   <li>Given {@code String List}.
   *   <li>Then return {@code String List}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName(
      "Test convertToDelimitedString(List); given 'String List'; then return 'String List'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseBpmnXMLConverter.convertToDelimitedString(List)"})
  void testConvertToDelimitedString_givenStringList_thenReturnStringList() {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("String List");

    // Act and Assert
    assertEquals("String List", associationXMLConverter.convertToDelimitedString(stringList));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); when ArrayList(); then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseBpmnXMLConverter.convertToDelimitedString(List)"})
  void testConvertToDelimitedString_whenArrayList_thenReturnEmptyString() {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    // Act and Assert
    assertEquals("", associationXMLConverter.convertToDelimitedString(new ArrayList<>()));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); when 'null'; then return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String BaseBpmnXMLConverter.convertToDelimitedString(List)"})
  void testConvertToDelimitedString_whenNull_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", new AssociationXMLConverter().convertToDelimitedString(null));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) Id is empty string.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeFormProperties(FlowElement, boolean, XMLStreamWriter); given FormProperty (default constructor) Id is empty string; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseBpmnXMLConverter.writeFormProperties(FlowElement, boolean, XMLStreamWriter)"
  })
  void testWriteFormProperties_givenFormPropertyIdIsEmptyString_thenReturnFalse() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    FormValue formValue = new FormValue();
    formValue.setId("not empty");

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setId("");
    formProperty.setReadable(false);
    formProperty.setWriteable(false);
    formProperty.setRequired(false);
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    UserTask flowElement = new UserTask();
    flowElement.setFormProperties(formProperties);

    // Act and Assert
    assertFalse(
        associationXMLConverter.writeFormProperties(
            flowElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link FormProperty} (default constructor) Id is empty string.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeFormProperties(FlowElement, boolean, XMLStreamWriter); given FormProperty (default constructor) Id is empty string; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseBpmnXMLConverter.writeFormProperties(FlowElement, boolean, XMLStreamWriter)"
  })
  void testWriteFormProperties_givenFormPropertyIdIsEmptyString_thenReturnFalse2()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    FormValue formValue = new FormValue();
    formValue.setId("not empty");

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setId("");
    formProperty.setReadable(false);
    formProperty.setWriteable(false);
    formProperty.setRequired(false);
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(new FormProperty());
    formProperties.add(formProperty);

    UserTask flowElement = new UserTask();
    flowElement.setFormProperties(formProperties);

    // Act and Assert
    assertFalse(
        associationXMLConverter.writeFormProperties(
            flowElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeFormProperties(FlowElement, boolean, XMLStreamWriter); when AdhocSubProcess (default constructor); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseBpmnXMLConverter.writeFormProperties(FlowElement, boolean, XMLStreamWriter)"
  })
  void testWriteFormProperties_whenAdhocSubProcess_thenReturnTrue() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    AdhocSubProcess flowElement = new AdhocSubProcess();

    // Act and Assert
    assertTrue(
        associationXMLConverter.writeFormProperties(
            flowElement, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link StartEvent} (default constructor) FormProperties is {@link
   *       ArrayList#ArrayList()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeFormProperties(FlowElement, boolean, XMLStreamWriter); when StartEvent (default constructor) FormProperties is ArrayList(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseBpmnXMLConverter.writeFormProperties(FlowElement, boolean, XMLStreamWriter)"
  })
  void testWriteFormProperties_whenStartEventFormPropertiesIsArrayList_thenReturnFalse()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    FormValue formValue = new FormValue();
    formValue.setId("not empty");

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(formValue);

    FormProperty formProperty = new FormProperty();
    formProperty.setId("");
    formProperty.setReadable(false);
    formProperty.setWriteable(false);
    formProperty.setRequired(false);
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    StartEvent flowElement = new StartEvent();
    flowElement.setFormProperties(formProperties);

    // Act and Assert
    assertFalse(
        associationXMLConverter.writeFormProperties(
            flowElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) Event is empty string.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeListeners(BaseElement, boolean, XMLStreamWriter); given ActivitiListener (default constructor) Event is empty string; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseBpmnXMLConverter.writeListeners(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteListeners_givenActivitiListenerEventIsEmptyString_thenReturnFalse()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setEvent("");
    activitiListener.setImplementation("not empty");
    activitiListener.setOnTransaction("not empty");
    activitiListener.setCustomPropertiesResolverImplementation("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask element = new UserTask();
    element.setTaskListeners(taskListeners);

    // Act and Assert
    assertFalse(
        associationXMLConverter.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) Event is empty string.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeListeners(BaseElement, boolean, XMLStreamWriter); given ActivitiListener (default constructor) Event is empty string; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseBpmnXMLConverter.writeListeners(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteListeners_givenActivitiListenerEventIsEmptyString_thenReturnFalse2()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setEvent("");
    activitiListener.setImplementation("not empty");
    activitiListener.setOnTransaction("not empty");
    activitiListener.setCustomPropertiesResolverImplementation("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(new ActivitiListener());
    taskListeners.add(activitiListener);

    UserTask element = new UserTask();
    element.setTaskListeners(taskListeners);

    // Act and Assert
    assertFalse(
        associationXMLConverter.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>When {@link Process} (default constructor) EventListeners is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeListeners(BaseElement, boolean, XMLStreamWriter); given ArrayList(); when Process (default constructor) EventListeners is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseBpmnXMLConverter.writeListeners(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteListeners_givenArrayList_whenProcessEventListenersIsArrayList() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    Process element = new Process();
    element.setEventListeners(new ArrayList<>());

    // Act and Assert
    assertFalse(
        associationXMLConverter.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link Process} (default constructor) EventListeners is {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeListeners(BaseElement, boolean, XMLStreamWriter); given 'null'; when Process (default constructor) EventListeners is 'null'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseBpmnXMLConverter.writeListeners(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteListeners_givenNull_whenProcessEventListenersIsNull_thenReturnFalse()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    Process element = new Process();
    element.setEventListeners(null);

    // Act and Assert
    assertFalse(
        associationXMLConverter.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link UserTask} (default constructor) TaskListeners is {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeListeners(BaseElement, boolean, XMLStreamWriter); given 'null'; when UserTask (default constructor) TaskListeners is 'null'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseBpmnXMLConverter.writeListeners(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteListeners_givenNull_whenUserTaskTaskListenersIsNull_thenReturnFalse()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    UserTask element = new UserTask();
    element.setTaskListeners(null);

    // Act and Assert
    assertFalse(
        associationXMLConverter.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeListeners(BaseElement, boolean, XMLStreamWriter); when ActivitiListener (default constructor); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean BaseBpmnXMLConverter.writeListeners(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteListeners_whenActivitiListener_thenReturnTrue() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertTrue(
        associationXMLConverter.writeListeners(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    TimerEventDefinition timerEventDefinition = mock(TimerEventDefinition.class);
    when(timerEventDefinition.getCalendarName()).thenReturn("");
    when(timerEventDefinition.getTimeDate()).thenReturn("2020-03-01");
    when(timerEventDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerEventDefinition).getExtensionElements();
    verify(timerEventDefinition).getCalendarName();
    verify(timerEventDefinition, atLeast(1)).getTimeDate();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link CancelEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); given CancelEventDefinition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_givenCancelEventDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("cancelEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link CancelEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); given CancelEventDefinition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_givenCancelEventDefinition2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    eventDefinitions.add(new CancelEventDefinition());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement("cancelEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link CompensateEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); given CompensateEventDefinition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_givenCompensateEventDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CompensateEventDefinition());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("compensateEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ErrorEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); given ErrorEventDefinition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_givenErrorEventDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new ErrorEventDefinition());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("errorEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code timerEventDefinition} is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); given HashMap() 'timerEventDefinition' is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_givenHashMapTimerEventDefinitionIsArrayList() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("timerEventDefinition", new ArrayList<>());

    TimerEventDefinition timerEventDefinition = mock(TimerEventDefinition.class);
    when(timerEventDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerEventDefinition.getTimeDate()).thenReturn("2020-03-01");
    when(timerEventDefinition.getExtensionElements()).thenReturn(stringListMap);

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer)
        .writeAttribute(
            "activiti", "http://activiti.org/bpmn", "businessCalendarName", "Calendar Name");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerEventDefinition, atLeast(1)).getExtensionElements();
    verify(timerEventDefinition, atLeast(1)).getCalendarName();
    verify(timerEventDefinition, atLeast(1)).getTimeDate();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link MessageEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); given MessageEventDefinition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_givenMessageEventDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new MessageEventDefinition());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("messageEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link SignalEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); given SignalEventDefinition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_givenSignalEventDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new SignalEventDefinition());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("signalEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link TerminateEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); given TerminateEventDefinition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_givenTerminateEventDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new TerminateEventDefinition());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("terminateEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link TimerEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); given TimerEventDefinition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_givenTimerEventDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new TimerEventDefinition());
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("timerEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link TimerEventDefinition} {@link TimerEventDefinition#getEndDate()} return
   *       {@code 2020-03-01}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); given TimerEventDefinition getEndDate() return '2020-03-01'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_givenTimerEventDefinitionGetEndDateReturn20200301()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    TimerEventDefinition timerEventDefinition = mock(TimerEventDefinition.class);
    when(timerEventDefinition.getEndDate()).thenReturn("2020-03-01");
    when(timerEventDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerEventDefinition.getTimeCycle()).thenReturn("Time Cycle");
    when(timerEventDefinition.getTimeDate()).thenReturn("");
    when(timerEventDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerEventDefinition).getExtensionElements();
    verify(timerEventDefinition, atLeast(1)).getCalendarName();
    verify(timerEventDefinition, atLeast(1)).getEndDate();
    verify(timerEventDefinition, atLeast(1)).getTimeCycle();
    verify(timerEventDefinition).getTimeDate();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link TimerEventDefinition} {@link TimerEventDefinition#getEndDate()} return empty
   *       string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); given TimerEventDefinition getEndDate() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_givenTimerEventDefinitionGetEndDateReturnEmptyString()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    TimerEventDefinition timerEventDefinition = mock(TimerEventDefinition.class);
    when(timerEventDefinition.getEndDate()).thenReturn("");
    when(timerEventDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerEventDefinition.getTimeCycle()).thenReturn("Time Cycle");
    when(timerEventDefinition.getTimeDate()).thenReturn("");
    when(timerEventDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer)
        .writeAttribute(
            "activiti", "http://activiti.org/bpmn", "businessCalendarName", "Calendar Name");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerEventDefinition).getExtensionElements();
    verify(timerEventDefinition, atLeast(1)).getCalendarName();
    verify(timerEventDefinition).getEndDate();
    verify(timerEventDefinition, atLeast(1)).getTimeCycle();
    verify(timerEventDefinition).getTimeDate();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link TimerEventDefinition} {@link TimerEventDefinition#getTimeDate()} return
   *       {@code 2020-03-01}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); given TimerEventDefinition getTimeDate() return '2020-03-01'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_givenTimerEventDefinitionGetTimeDateReturn20200301()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    TimerEventDefinition timerEventDefinition = mock(TimerEventDefinition.class);
    when(timerEventDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerEventDefinition.getTimeDate()).thenReturn("2020-03-01");
    when(timerEventDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer)
        .writeAttribute(
            "activiti", "http://activiti.org/bpmn", "businessCalendarName", "Calendar Name");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerEventDefinition).getExtensionElements();
    verify(timerEventDefinition, atLeast(1)).getCalendarName();
    verify(timerEventDefinition, atLeast(1)).getTimeDate();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link TimerEventDefinition#getTimeDuration()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeEventDefinitions(Event, List, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter); then calls getTimeDuration()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeEventDefinitions(Event, List, BpmnModel, XMLStreamWriter)"
  })
  void testWriteEventDefinitions_thenCallsGetTimeDuration() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    TimerEventDefinition timerEventDefinition = mock(TimerEventDefinition.class);
    when(timerEventDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerEventDefinition.getTimeCycle()).thenReturn("");
    when(timerEventDefinition.getTimeDate()).thenReturn("");
    when(timerEventDefinition.getTimeDuration()).thenReturn("Time Duration");
    when(timerEventDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(timerEventDefinition);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeEventDefinitions(
        parentEvent, eventDefinitions, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer)
        .writeAttribute(
            "activiti", "http://activiti.org/bpmn", "businessCalendarName", "Calendar Name");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerEventDefinition).getExtensionElements();
    verify(timerEventDefinition, atLeast(1)).getCalendarName();
    verify(timerEventDefinition).getTimeCycle();
    verify(timerEventDefinition).getTimeDate();
    verify(timerEventDefinition, atLeast(1)).getTimeDuration();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition,
   * XMLStreamWriter)}.
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTimerDefinition(Event,
   * TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)"
  })
  void testWriteTimerDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getCalendarName()).thenReturn("");
    when(timerDefinition.getTimeCycle()).thenReturn("");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getTimeDuration()).thenReturn("");
    when(timerDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("timerEventDefinition");
    verify(timerDefinition).getExtensionElements();
    verify(timerDefinition).getCalendarName();
    verify(timerDefinition).getTimeCycle();
    verify(timerDefinition).getTimeDate();
    verify(timerDefinition).getTimeDuration();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code timerEventDefinition} is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTimerDefinition(Event,
   * TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); given HashMap() 'timerEventDefinition' is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)"
  })
  void testWriteTimerDefinition_givenHashMapTimerEventDefinitionIsArrayList() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("timerEventDefinition", new ArrayList<>());

    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getCalendarName()).thenReturn("not empty");
    when(timerDefinition.getTimeDate()).thenReturn("not empty");
    when(timerDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert
    verify(xtw)
        .writeAttribute(
            "activiti", "http://activiti.org/bpmn", "businessCalendarName", "not empty");
    verify(xtw).writeCharacters("not empty");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerDefinition, atLeast(1)).getExtensionElements();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition, atLeast(1)).getTimeDate();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link TimerEventDefinition} {@link TimerEventDefinition#getEndDate()} return empty
   *       string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTimerDefinition(Event,
   * TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); when TimerEventDefinition getEndDate() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)"
  })
  void testWriteTimerDefinition_whenTimerEventDefinitionGetEndDateReturnEmptyString()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getEndDate()).thenReturn("");
    when(timerDefinition.getCalendarName()).thenReturn("not empty");
    when(timerDefinition.getTimeCycle()).thenReturn("not empty");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert
    verify(xtw)
        .writeAttribute(
            "activiti", "http://activiti.org/bpmn", "businessCalendarName", "not empty");
    verify(xtw).writeCharacters("not empty");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerDefinition).getExtensionElements();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition).getEndDate();
    verify(timerDefinition, atLeast(1)).getTimeCycle();
    verify(timerDefinition).getTimeDate();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link TimerEventDefinition} {@link TimerEventDefinition#getEndDate()} return {@code
   *       not empty}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTimerDefinition(Event,
   * TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); when TimerEventDefinition getEndDate() return 'not empty'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)"
  })
  void testWriteTimerDefinition_whenTimerEventDefinitionGetEndDateReturnNotEmpty()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getEndDate()).thenReturn("not empty");
    when(timerDefinition.getCalendarName()).thenReturn("not empty");
    when(timerDefinition.getTimeCycle()).thenReturn("not empty");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(), eq("not empty"));
    verify(xtw).writeCharacters("not empty");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerDefinition).getExtensionElements();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition, atLeast(1)).getEndDate();
    verify(timerDefinition, atLeast(1)).getTimeCycle();
    verify(timerDefinition).getTimeDate();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link TimerEventDefinition} {@link TimerEventDefinition#getTimeDate()} return
   *       {@code not empty}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTimerDefinition(Event,
   * TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); when TimerEventDefinition getTimeDate() return 'not empty'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)"
  })
  void testWriteTimerDefinition_whenTimerEventDefinitionGetTimeDateReturnNotEmpty()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getCalendarName()).thenReturn("not empty");
    when(timerDefinition.getTimeDate()).thenReturn("not empty");
    when(timerDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert
    verify(xtw)
        .writeAttribute(
            "activiti", "http://activiti.org/bpmn", "businessCalendarName", "not empty");
    verify(xtw).writeCharacters("not empty");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerDefinition).getExtensionElements();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition, atLeast(1)).getTimeDate();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link TimerEventDefinition} {@link TimerEventDefinition#getTimeDuration()} return
   *       {@code not empty}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTimerDefinition(Event,
   * TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); when TimerEventDefinition getTimeDuration() return 'not empty'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)"
  })
  void testWriteTimerDefinition_whenTimerEventDefinitionGetTimeDurationReturnNotEmpty()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getCalendarName()).thenReturn("not empty");
    when(timerDefinition.getTimeCycle()).thenReturn("");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getTimeDuration()).thenReturn("not empty");
    when(timerDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert
    verify(xtw)
        .writeAttribute(
            "activiti", "http://activiti.org/bpmn", "businessCalendarName", "not empty");
    verify(xtw).writeCharacters("not empty");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerDefinition).getExtensionElements();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition).getTimeCycle();
    verify(timerDefinition).getTimeDate();
    verify(timerDefinition, atLeast(1)).getTimeDuration();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link TimerEventDefinition} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndElement()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTimerDefinition(Event,
   * TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); when TimerEventDefinition (default constructor); then calls writeEndElement()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)"
  })
  void testWriteTimerDefinition_whenTimerEventDefinition_thenCallsWriteEndElement()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    TimerEventDefinition timerDefinition = new TimerEventDefinition();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("timerEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeSignalDefinition(Event,
   * SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)"
  })
  void testWriteSignalDefinition_givenArrayListAddExtensionElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("Key", extensionElementList);

    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    signalDefinition.setExtensionElements(extensionElements);
    signalDefinition.setAsync(true);
    signalDefinition.setSignalRef("signalEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeAttribute("signalRef", "signalEventDefinition");
    verify(xtw).writeAttribute("activiti", "http://activiti.org/bpmn", "async", "true");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeSignalDefinition(Event,
   * SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)"
  })
  void testWriteSignalDefinition_givenEmptyString() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    signalDefinition.setSignalRef("");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("signalEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ExtensionElement} {@link ExtensionElement#getNamespace()} return empty
   *       string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeSignalDefinition(Event,
   * SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given ExtensionElement getNamespace() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)"
  })
  void testWriteSignalDefinition_givenExtensionElementGetNamespaceReturnEmptyString()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("Key", extensionElementList);

    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    signalDefinition.setExtensionElements(extensionElements);
    signalDefinition.setAsync(true);
    signalDefinition.setSignalRef("signalEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeAttribute("signalRef", "signalEventDefinition");
    verify(xtw).writeAttribute("activiti", "http://activiti.org/bpmn", "async", "true");
    verify(xtw).writeCData("Element Text");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code Key} is {@link ArrayList#ArrayList()}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeSignalDefinition(Event,
   * SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given HashMap() 'Key' is ArrayList(); then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)"
  })
  void testWriteSignalDefinition_givenHashMapKeyIsArrayList_thenCallsWriteAttribute()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("Key", new ArrayList<>());

    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    signalDefinition.setExtensionElements(extensionElements);
    signalDefinition.setAsync(true);
    signalDefinition.setSignalRef("signalEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeAttribute("signalRef", "signalEventDefinition");
    verify(xtw).writeAttribute("activiti", "http://activiti.org/bpmn", "async", "true");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code signalEventDefinition} is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeSignalDefinition(Event,
   * SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given HashMap() 'signalEventDefinition' is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)"
  })
  void testWriteSignalDefinition_givenHashMapSignalEventDefinitionIsArrayList() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("signalEventDefinition", new ArrayList<>());

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("Key", extensionElementList);

    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    signalDefinition.setExtensionElements(extensionElements);
    signalDefinition.setAsync(true);
    signalDefinition.setSignalRef("signalEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeAttribute("signalRef", "signalEventDefinition");
    verify(xtw).writeAttribute("activiti", "http://activiti.org/bpmn", "async", "true");
    verify(xtw).writeNamespace("Namespace Prefix", "Namespace");
    verify(xtw).writeCData("Element Text");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("Namespace Prefix", "Name", "Namespace");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link SignalEventDefinition} (default constructor) SignalRef is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeSignalDefinition(Event,
   * SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given 'null'; when SignalEventDefinition (default constructor) SignalRef is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)"
  })
  void testWriteSignalDefinition_givenNull_whenSignalEventDefinitionSignalRefIsNull()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    signalDefinition.setSignalRef("null");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("signalEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code true}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeSignalDefinition(Event,
   * SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given 'true'; then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)"
  })
  void testWriteSignalDefinition_givenTrue_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    signalDefinition.setAsync(true);
    signalDefinition.setSignalRef("signalEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeAttribute("signalRef", "signalEventDefinition");
    verify(xtw).writeAttribute("activiti", "http://activiti.org/bpmn", "async", "true");
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("signalEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeNamespace(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeSignalDefinition(Event,
   * SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); then calls writeNamespace(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)"
  })
  void testWriteSignalDefinition_thenCallsWriteNamespace() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("Key", extensionElementList);

    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    signalDefinition.setExtensionElements(extensionElements);
    signalDefinition.setAsync(true);
    signalDefinition.setSignalRef("signalEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeAttribute("signalRef", "signalEventDefinition");
    verify(xtw).writeAttribute("activiti", "http://activiti.org/bpmn", "async", "true");
    verify(xtw).writeNamespace("Namespace Prefix", "Namespace");
    verify(xtw).writeCData("Element Text");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("Namespace Prefix", "Name", "Namespace");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeSignalDefinition(Event,
   * SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)"
  })
  void testWriteSignalDefinition_thenCallsWriteStartElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.put("Key", extensionElementList);

    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    signalDefinition.setExtensionElements(extensionElements);
    signalDefinition.setAsync(true);
    signalDefinition.setSignalRef("signalEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeAttribute("signalRef", "signalEventDefinition");
    verify(xtw).writeAttribute("activiti", "http://activiti.org/bpmn", "async", "true");
    verify(xtw).writeCData("Element Text");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("Namespace", "Name");
    verify(extensionElement).getAttributes();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeSignalDefinition(Event,
   * SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); when BoundaryEvent (default constructor); then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)"
  })
  void testWriteSignalDefinition_whenBoundaryEvent_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    signalDefinition.setSignalRef("signalEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeAttribute("signalRef", "signalEventDefinition");
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("signalEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link SignalEventDefinition} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndElement()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeSignalDefinition(Event,
   * SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); when SignalEventDefinition (default constructor); then calls writeEndElement()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)"
  })
  void testWriteSignalDefinition_whenSignalEventDefinition_thenCallsWriteEndElement()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    SignalEventDefinition signalDefinition = new SignalEventDefinition();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("signalEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link ThrowEvent} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeSignalDefinition(Event,
   * SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); when ThrowEvent (default constructor); then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)"
  })
  void testWriteSignalDefinition_whenThrowEvent_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    signalDefinition.setSignalRef("signalEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeAttribute("signalRef", "signalEventDefinition");
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("signalEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeCancelDefinition(Event, CancelEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link CancelEventDefinition} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndElement()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeCancelDefinition(Event,
   * CancelEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCancelDefinition(Event, CancelEventDefinition, XMLStreamWriter); when CancelEventDefinition (default constructor); then calls writeEndElement()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeCancelDefinition(Event, CancelEventDefinition, XMLStreamWriter)"
  })
  void testWriteCancelDefinition_whenCancelEventDefinition_thenCallsWriteEndElement()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    CancelEventDefinition cancelEventDefinition = new CancelEventDefinition();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCancelDefinition(
        parentEvent, cancelEventDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("cancelEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code Activity Ref}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event,
   * CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); given 'Activity Ref'; then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)"
  })
  void testWriteCompensateDefinition_givenActivityRef_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", new ArrayList<>());

    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("Activity Ref");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(
        parentEvent, compensateEventDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("activityRef", "Activity Ref");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event,
   * CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)"
  })
  void testWriteCompensateDefinition_givenArrayListAddExtensionElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", extensionElementList);

    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("Activity Ref");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(
        parentEvent, compensateEventDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("activityRef", "Activity Ref");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event,
   * CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); given empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)"
  })
  void testWriteCompensateDefinition_givenEmptyString() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", new ArrayList<>());

    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(
        parentEvent, compensateEventDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ExtensionElement} {@link ExtensionElement#getNamespace()} return empty
   *       string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event,
   * CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); given ExtensionElement getNamespace() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)"
  })
  void testWriteCompensateDefinition_givenExtensionElementGetNamespaceReturnEmptyString()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", extensionElementList);

    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("Activity Ref");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(
        parentEvent, compensateEventDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("activityRef", "Activity Ref");
    verify(writer).writeCData("Element Text");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(extensionElement).getAttributes();
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event,
   * CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); given HashMap(); then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)"
  })
  void testWriteCompensateDefinition_givenHashMap_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("Activity Ref");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(
        parentEvent, compensateEventDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("activityRef", "Activity Ref");
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("compensateEventDefinition");
    verify(compensateEventDefinition).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event,
   * CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); given 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)"
  })
  void testWriteCompensateDefinition_givenNull() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", new ArrayList<>());

    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("null");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(
        parentEvent, compensateEventDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeNamespace(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event,
   * CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); then calls writeNamespace(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)"
  })
  void testWriteCompensateDefinition_thenCallsWriteNamespace() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", extensionElementList);

    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("Activity Ref");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(
        parentEvent, compensateEventDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("activityRef", "Activity Ref");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(writer).writeCData("Element Text");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer).writeStartElement("Namespace Prefix", "Name", "Namespace");
    verify(extensionElement).getAttributes();
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeNamespace(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event,
   * CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); then calls writeNamespace(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)"
  })
  void testWriteCompensateDefinition_thenCallsWriteNamespace2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", new ArrayList<>());

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap2 = new HashMap<>();
    stringListMap2.put("compensateEventDefinition", extensionElementList);

    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("Activity Ref");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(stringListMap2);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(
        parentEvent, compensateEventDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("activityRef", "Activity Ref");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(writer).writeCData("Element Text");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer).writeStartElement("Namespace Prefix", "Name", "Namespace");
    verify(extensionElement).getAttributes();
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event,
   * CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)"
  })
  void testWriteCompensateDefinition_thenCallsWriteStartElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", extensionElementList);

    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("Activity Ref");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(
        parentEvent, compensateEventDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("activityRef", "Activity Ref");
    verify(writer).writeCData("Element Text");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer).writeStartElement("Namespace", "Name");
    verify(extensionElement).getAttributes();
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link CompensateEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event,
   * CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); when CompensateEventDefinition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)"
  })
  void testWriteCompensateDefinition_whenCompensateEventDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    CompensateEventDefinition compensateEventDefinition = new CompensateEventDefinition();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(
        parentEvent, compensateEventDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("compensateEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition,
   * BpmnModel, XMLStreamWriter)}.
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeMessageDefinition(Event,
   * MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)"
  })
  void testWriteMessageDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("Message Ref");
    when(messageDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("messageEventDefinition", "42");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    doNothing().when(model).setTargetNamespace(Mockito.<String>any());
    model.setTargetNamespace("messageEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert
    verify(xtw).writeAttribute("messageRef", "Message Ref");
    verify(xtw)
        .writeAttribute(
            "activiti", "http://activiti.org/bpmn", "messageExpression", "Message Expression");
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("messageEventDefinition");
    verify(messageDefinition).getExtensionElements();
    verify(model).getNamespace("messageEventDefinition");
    verify(model).getNamespaces();
    verify(model).getTargetNamespace();
    verify(model).setTargetNamespace("messageEventDefinition");
    verify(messageDefinition).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition,
   * BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code :}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeMessageDefinition(Event,
   * MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); given ':'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)"
  })
  void testWriteMessageDefinition_givenColon() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn(":");
    when(messageDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("");
    doNothing().when(model).setTargetNamespace(Mockito.<String>any());
    model.setTargetNamespace("messageEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("messageEventDefinition");
    verify(messageDefinition).getExtensionElements();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(model).setTargetNamespace("messageEventDefinition");
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition,
   * BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code messageEventDefinition} is {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeMessageDefinition(Event,
   * MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); given HashMap() 'messageEventDefinition' is ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)"
  })
  void testWriteMessageDefinition_givenHashMapMessageEventDefinitionIsArrayList() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("messageEventDefinition", new ArrayList<>());

    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("Message Ref");
    when(messageDefinition.getExtensionElements()).thenReturn(stringListMap);

    BpmnModel model = new BpmnModel();
    model.setTargetNamespace("messageEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert
    verify(xtw).writeAttribute("messageRef", "Message Ref");
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(messageDefinition, atLeast(1)).getExtensionElements();
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition,
   * BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code http://activiti.org/bpmn}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeMessageDefinition(Event,
   * MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); given 'http://activiti.org/bpmn'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)"
  })
  void testWriteMessageDefinition_givenHttpActivitiOrgBpmn() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("http://activiti.org/bpmn");
    when(messageDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("");
    doNothing().when(model).setTargetNamespace(Mockito.<String>any());
    model.setTargetNamespace("messageEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert
    verify(xtw).writeAttribute("messageRef", "http//activiti.org/bpmn");
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("messageEventDefinition");
    verify(messageDefinition).getExtensionElements();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(model).setTargetNamespace("messageEventDefinition");
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition,
   * BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code Namespace}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeMessageDefinition(Event,
   * MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); given 'Namespace'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)"
  })
  void testWriteMessageDefinition_givenNamespace() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("Message Ref");
    when(messageDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("messageEventDefinition", "42");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    doNothing().when(model).setTargetNamespace(Mockito.<String>any());
    model.setTargetNamespace("messageEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert
    verify(xtw).writeAttribute("messageRef", "Message Ref");
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("messageEventDefinition");
    verify(messageDefinition).getExtensionElements();
    verify(model).getNamespace("messageEventDefinition");
    verify(model).getNamespaces();
    verify(model).getTargetNamespace();
    verify(model).setTargetNamespace("messageEventDefinition");
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition,
   * BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeMessageDefinition(Event,
   * MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); given 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)"
  })
  void testWriteMessageDefinition_givenNull() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("null");
    when(messageDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("messageEventDefinition", "42");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    doNothing().when(model).setTargetNamespace(Mockito.<String>any());
    model.setTargetNamespace("messageEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("messageEventDefinition");
    verify(messageDefinition).getExtensionElements();
    verify(model).getNamespace("messageEventDefinition");
    verify(model).getNamespaces();
    verify(model).getTargetNamespace();
    verify(model).setTargetNamespace("messageEventDefinition");
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition,
   * BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code Target Namespace}.
   *   <li>Then calls {@link BpmnModel#getNamespaces()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeMessageDefinition(Event,
   * MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); given 'Target Namespace'; then calls getNamespaces()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)"
  })
  void testWriteMessageDefinition_givenTargetNamespace_thenCallsGetNamespaces() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("Message Ref");
    when(messageDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespaces()).thenReturn(new HashMap<>());
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    doNothing().when(model).setTargetNamespace(Mockito.<String>any());
    model.setTargetNamespace("messageEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert
    verify(xtw).writeAttribute("messageRef", "Message Ref");
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("messageEventDefinition");
    verify(messageDefinition).getExtensionElements();
    verify(model).getNamespaces();
    verify(model).getTargetNamespace();
    verify(model).setTargetNamespace("messageEventDefinition");
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition,
   * BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#getNamespace(String)} return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeMessageDefinition(Event,
   * MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); when BpmnModel getNamespace(String) return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)"
  })
  void testWriteMessageDefinition_whenBpmnModelGetNamespaceReturnEmptyString() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("Message Ref");
    when(messageDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    HashMap<String, String> stringStringMap = new HashMap<>();
    stringStringMap.put("messageEventDefinition", "42");

    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    doNothing().when(model).setTargetNamespace(Mockito.<String>any());
    model.setTargetNamespace("messageEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert
    verify(xtw).writeAttribute("messageRef", "messageEventDefinitionMessage Ref");
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("messageEventDefinition");
    verify(messageDefinition).getExtensionElements();
    verify(model).getNamespace("messageEventDefinition");
    verify(model).getNamespaces();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(model).setTargetNamespace("messageEventDefinition");
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition,
   * BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#getTargetNamespace()} return empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeMessageDefinition(Event,
   * MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); when BpmnModel getTargetNamespace() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)"
  })
  void testWriteMessageDefinition_whenBpmnModelGetTargetNamespaceReturnEmptyString()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("Message Ref");
    when(messageDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getTargetNamespace()).thenReturn("");
    doNothing().when(model).setTargetNamespace(Mockito.<String>any());
    model.setTargetNamespace("messageEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert
    verify(xtw).writeAttribute("messageRef", "Message Ref");
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("messageEventDefinition");
    verify(messageDefinition).getExtensionElements();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(model).setTargetNamespace("messageEventDefinition");
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition,
   * BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor) TargetNamespace is {@code
   *       messageEventDefinition}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeMessageDefinition(Event,
   * MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); when BpmnModel (default constructor) TargetNamespace is 'messageEventDefinition'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)"
  })
  void testWriteMessageDefinition_whenBpmnModelTargetNamespaceIsMessageEventDefinition()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("Message Ref");
    when(messageDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = new BpmnModel();
    model.setTargetNamespace("messageEventDefinition");

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert
    verify(xtw).writeAttribute("messageRef", "Message Ref");
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("messageEventDefinition");
    verify(messageDefinition).getExtensionElements();
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition,
   * BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link MessageEventDefinition} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndElement()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeMessageDefinition(Event,
   * MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); when MessageEventDefinition (default constructor); then calls writeEndElement()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)"
  })
  void testWriteMessageDefinition_whenMessageEventDefinition_thenCallsWriteEndElement()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    MessageEventDefinition messageDefinition = new MessageEventDefinition();
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("messageEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code An error occurred}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeErrorDefinition(Event,
   * ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); given 'An error occurred'; then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)"
  })
  void testWriteErrorDefinition_givenAnErrorOccurred_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", new ArrayList<>());

    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("An error occurred");
    when(errorDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(
        parentEvent, errorDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("errorRef", "An error occurred");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeErrorDefinition(Event,
   * ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)"
  })
  void testWriteErrorDefinition_givenArrayListAddExtensionElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", extensionElementList);

    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("An error occurred");
    when(errorDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(
        parentEvent, errorDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("errorRef", "An error occurred");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeErrorDefinition(Event,
   * ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); given empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)"
  })
  void testWriteErrorDefinition_givenEmptyString() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", new ArrayList<>());

    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("");
    when(errorDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(
        parentEvent, errorDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ExtensionElement} {@link ExtensionElement#getNamespace()} return empty
   *       string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeErrorDefinition(Event,
   * ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); given ExtensionElement getNamespace() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)"
  })
  void testWriteErrorDefinition_givenExtensionElementGetNamespaceReturnEmptyString()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", extensionElementList);

    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("An error occurred");
    when(errorDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(
        parentEvent, errorDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("errorRef", "An error occurred");
    verify(writer).writeCData("Element Text");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(extensionElement).getAttributes();
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeErrorDefinition(Event,
   * ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); given HashMap(); then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)"
  })
  void testWriteErrorDefinition_givenHashMap_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("An error occurred");
    when(errorDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(
        parentEvent, errorDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("errorRef", "An error occurred");
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("errorEventDefinition");
    verify(errorDefinition).getExtensionElements();
    verify(errorDefinition).getErrorRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link ErrorEventDefinition} {@link ErrorEventDefinition#getErrorRef()} return
   *       {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeErrorDefinition(Event,
   * ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); given 'null'; when ErrorEventDefinition getErrorRef() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)"
  })
  void testWriteErrorDefinition_givenNull_whenErrorEventDefinitionGetErrorRefReturnNull()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", new ArrayList<>());

    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("null");
    when(errorDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(
        parentEvent, errorDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeNamespace(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeErrorDefinition(Event,
   * ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); then calls writeNamespace(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)"
  })
  void testWriteErrorDefinition_thenCallsWriteNamespace() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", extensionElementList);

    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("An error occurred");
    when(errorDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(
        parentEvent, errorDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("errorRef", "An error occurred");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(writer).writeCData("Element Text");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer).writeStartElement("Namespace Prefix", "Name", "Namespace");
    verify(extensionElement).getAttributes();
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeNamespace(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeErrorDefinition(Event,
   * ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); then calls writeNamespace(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)"
  })
  void testWriteErrorDefinition_thenCallsWriteNamespace2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", new ArrayList<>());

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap2 = new HashMap<>();
    stringListMap2.put("errorEventDefinition", extensionElementList);

    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("An error occurred");
    when(errorDefinition.getExtensionElements()).thenReturn(stringListMap2);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(
        parentEvent, errorDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("errorRef", "An error occurred");
    verify(writer).writeNamespace("Namespace Prefix", "Namespace");
    verify(writer).writeCData("Element Text");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer).writeStartElement("Namespace Prefix", "Name", "Namespace");
    verify(extensionElement).getAttributes();
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeErrorDefinition(Event,
   * ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)"
  })
  void testWriteErrorDefinition_thenCallsWriteStartElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", extensionElementList);

    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("An error occurred");
    when(errorDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(
        parentEvent, errorDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("errorRef", "An error occurred");
    verify(writer).writeCData("Element Text");
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(writer).writeStartElement("Namespace", "Name");
    verify(extensionElement).getAttributes();
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link ErrorEventDefinition} (default constructor).
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndElement()}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeErrorDefinition(Event,
   * ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); when ErrorEventDefinition (default constructor); then calls writeEndElement()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)"
  })
  void testWriteErrorDefinition_whenErrorEventDefinition_thenCallsWriteEndElement()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    ErrorEventDefinition errorDefinition = new ErrorEventDefinition();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(
        parentEvent, errorDefinition, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("errorEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event,
   * TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)"
  })
  void testWriteTerminateDefinition_givenArrayListAddExtensionElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("terminateEventDefinition", extensionElementList);

    TerminateEventDefinition terminateDefinition = mock(TerminateEventDefinition.class);
    when(terminateDefinition.isTerminateAll()).thenReturn(true);
    when(terminateDefinition.isTerminateMultiInstance()).thenReturn(true);
    when(terminateDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(), eq("true"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(terminateDefinition, atLeast(1)).getExtensionElements();
    verify(terminateDefinition).isTerminateAll();
    verify(terminateDefinition).isTerminateMultiInstance();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ExtensionElement} {@link ExtensionElement#getNamespace()} return empty
   *       string.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event,
   * TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); given ExtensionElement getNamespace() return empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)"
  })
  void testWriteTerminateDefinition_givenExtensionElementGetNamespaceReturnEmptyString()
      throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("terminateEventDefinition", extensionElementList);

    TerminateEventDefinition terminateDefinition = mock(TerminateEventDefinition.class);
    when(terminateDefinition.isTerminateAll()).thenReturn(true);
    when(terminateDefinition.isTerminateMultiInstance()).thenReturn(true);
    when(terminateDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(), eq("true"));
    verify(xtw).writeCData("Element Text");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(extensionElement).getAttributes();
    verify(terminateDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
    verify(terminateDefinition).isTerminateAll();
    verify(terminateDefinition).isTerminateMultiInstance();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event,
   * TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); given HashMap(); then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)"
  })
  void testWriteTerminateDefinition_givenHashMap_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    TerminateEventDefinition terminateDefinition = mock(TerminateEventDefinition.class);
    when(terminateDefinition.isTerminateAll()).thenReturn(true);
    when(terminateDefinition.isTerminateMultiInstance()).thenReturn(true);
    when(terminateDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(), eq("true"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("terminateEventDefinition");
    verify(terminateDefinition).getExtensionElements();
    verify(terminateDefinition).isTerminateAll();
    verify(terminateDefinition).isTerminateMultiInstance();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event,
   * TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)"
  })
  void testWriteTerminateDefinition_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("terminateEventDefinition", new ArrayList<>());

    TerminateEventDefinition terminateDefinition = mock(TerminateEventDefinition.class);
    when(terminateDefinition.isTerminateAll()).thenReturn(true);
    when(terminateDefinition.isTerminateMultiInstance()).thenReturn(true);
    when(terminateDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(), eq("true"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(terminateDefinition, atLeast(1)).getExtensionElements();
    verify(terminateDefinition).isTerminateAll();
    verify(terminateDefinition).isTerminateMultiInstance();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeNamespace(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event,
   * TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); then calls writeNamespace(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)"
  })
  void testWriteTerminateDefinition_thenCallsWriteNamespace() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("terminateEventDefinition", extensionElementList);

    TerminateEventDefinition terminateDefinition = mock(TerminateEventDefinition.class);
    when(terminateDefinition.isTerminateAll()).thenReturn(true);
    when(terminateDefinition.isTerminateMultiInstance()).thenReturn(true);
    when(terminateDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(), eq("true"));
    verify(xtw).writeNamespace("Namespace Prefix", "Namespace");
    verify(xtw).writeCData("Element Text");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("Namespace Prefix", "Name", "Namespace");
    verify(extensionElement).getAttributes();
    verify(terminateDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
    verify(terminateDefinition).isTerminateAll();
    verify(terminateDefinition).isTerminateMultiInstance();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeNamespace(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event,
   * TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); then calls writeNamespace(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)"
  })
  void testWriteTerminateDefinition_thenCallsWriteNamespace2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("terminateEventDefinition", new ArrayList<>());

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("Namespace Prefix");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap2 = new HashMap<>();
    stringListMap2.put("terminateEventDefinition", extensionElementList);

    TerminateEventDefinition terminateDefinition = mock(TerminateEventDefinition.class);
    when(terminateDefinition.isTerminateAll()).thenReturn(true);
    when(terminateDefinition.isTerminateMultiInstance()).thenReturn(true);
    when(terminateDefinition.getExtensionElements()).thenReturn(stringListMap2);

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(), eq("true"));
    verify(xtw).writeNamespace("Namespace Prefix", "Namespace");
    verify(xtw).writeCData("Element Text");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("Namespace Prefix", "Name", "Namespace");
    verify(extensionElement).getAttributes();
    verify(terminateDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
    verify(terminateDefinition).isTerminateAll();
    verify(terminateDefinition).isTerminateMultiInstance();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event,
   * TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)"
  })
  void testWriteTerminateDefinition_thenCallsWriteStartElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(new HashMap<>());
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("terminateEventDefinition", extensionElementList);

    TerminateEventDefinition terminateDefinition = mock(TerminateEventDefinition.class);
    when(terminateDefinition.isTerminateAll()).thenReturn(true);
    when(terminateDefinition.isTerminateMultiInstance()).thenReturn(true);
    when(terminateDefinition.getExtensionElements()).thenReturn(stringListMap);

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert
    verify(xtw, atLeast(1))
        .writeAttribute(
            eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(), eq("true"));
    verify(xtw).writeCData("Element Text");
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement("Namespace", "Name");
    verify(extensionElement).getAttributes();
    verify(terminateDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
    verify(terminateDefinition).isTerminateAll();
    verify(terminateDefinition).isTerminateMultiInstance();
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link TerminateEventDefinition} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event,
   * TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); when TerminateEventDefinition (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)"
  })
  void testWriteTerminateDefinition_whenTerminateEventDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    TerminateEventDefinition terminateDefinition = new TerminateEventDefinition();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement("terminateEventDefinition");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeDefaultAttribute(String, String, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeDefaultAttribute(String, String,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeDefaultAttribute(String, String, XMLStreamWriter); when '42'; then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeDefaultAttribute(String, String, XMLStreamWriter)"
  })
  void testWriteDefaultAttribute_when42_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    associationXMLConverter.writeDefaultAttribute("Attribute Name", "42", xtw);

    // Assert
    verify(xtw).writeAttribute("Attribute Name", "42");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeQualifiedAttribute(String, String, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link BaseBpmnXMLConverter#writeQualifiedAttribute(String, String,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeQualifiedAttribute(String, String, XMLStreamWriter); then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeQualifiedAttribute(String, String, XMLStreamWriter)"
  })
  void testWriteQualifiedAttribute_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(xtw)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());

    // Act
    associationXMLConverter.writeQualifiedAttribute("Attribute Name", "42", xtw);

    // Assert
    verify(xtw).writeAttribute("activiti", "http://activiti.org/bpmn", "Attribute Name", "42");
  }

  /**
   * Test {@link BaseBpmnXMLConverter#writeIncomingOutgoingFlowElements(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * BaseBpmnXMLConverter#writeIncomingOutgoingFlowElements(BaseElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeIncomingOutgoingFlowElements(BaseElement, BpmnModel, XMLStreamWriter); then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BaseBpmnXMLConverter.writeIncomingOutgoingFlowElements(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteIncomingOutgoingFlowElements_thenCallsWriteCharacters() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    ArrayList<SequenceFlow> incomingFlows = new ArrayList<>();
    incomingFlows.add(new SequenceFlow());

    ArrayList<SequenceFlow> outgoingFlows = new ArrayList<>();
    outgoingFlows.add(new SequenceFlow());

    AdhocSubProcess element = new AdhocSubProcess();
    element.setIncomingFlows(incomingFlows);
    element.setOutgoingFlows(outgoingFlows);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeIncomingOutgoingFlowElements(
        element, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeCharacters(null);
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmn2"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
  }
}
