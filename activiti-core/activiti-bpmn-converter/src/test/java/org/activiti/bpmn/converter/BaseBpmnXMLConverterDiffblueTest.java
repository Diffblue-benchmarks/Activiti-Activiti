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
import ch.qos.logback.core.util.COWArrayList;
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
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.FormValue;
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.SignalEventDefinition;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.TerminateEventDefinition;
import org.activiti.bpmn.model.ThrowEvent;
import org.activiti.bpmn.model.TimerEventDefinition;
import org.activiti.bpmn.model.UserTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BaseBpmnXMLConverterDiffblueTest {
  /**
   * Test
   * {@link BaseBpmnXMLConverter#convertToXML(XMLStreamWriter, BaseElement, BpmnModel)}.
   * <ul>
   *   <li>When {@link Association} (default constructor).</li>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToXML(XMLStreamWriter, BaseElement, BpmnModel)}
   */
  @Test
  @DisplayName("Test convertToXML(XMLStreamWriter, BaseElement, BpmnModel); when Association (default constructor); then calls writeAttribute(String, String)")
  void testConvertToXML_whenAssociation_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    Association baseElement = new Association();

    // Act
    associationXMLConverter.convertToXML(xtw, baseElement, new BpmnModel());

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("associationDirection"), eq("None"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("bpmn2"), eq("association"), eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); when 'false'; then return 'false'")
  void testWriteExtensionChildElements_whenFalse_thenReturnFalse() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertFalse(
        associationXMLConverter.writeExtensionChildElements(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@code true}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); when 'true'; then return 'true'")
  void testWriteExtensionChildElements_whenTrue_thenReturnTrue() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertTrue(associationXMLConverter.writeExtensionChildElements(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#parseDelimitedList(String)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnXMLConverter#parseDelimitedList(String)}
   */
  @Test
  @DisplayName("Test parseDelimitedList(String); when empty string; then return Empty")
  void testParseDelimitedList_whenEmptyString_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new AssociationXMLConverter()).parseDelimitedList("").isEmpty());
  }

  /**
   * Test {@link BaseBpmnXMLConverter#parseDelimitedList(String)}.
   * <ul>
   *   <li>When {@code Expression}.</li>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnXMLConverter#parseDelimitedList(String)}
   */
  @Test
  @DisplayName("Test parseDelimitedList(String); when 'Expression'; then return size is one")
  void testParseDelimitedList_whenExpression_thenReturnSizeIsOne() {
    // Arrange and Act
    List<String> actualParseDelimitedListResult = (new AssociationXMLConverter()).parseDelimitedList("Expression");

    // Assert
    assertEquals(1, actualParseDelimitedListResult.size());
    assertEquals("Expression", actualParseDelimitedListResult.get(0));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#parseDelimitedList(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseBpmnXMLConverter#parseDelimitedList(String)}
   */
  @Test
  @DisplayName("Test parseDelimitedList(String); when 'null'; then return Empty")
  void testParseDelimitedList_whenNull_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue((new AssociationXMLConverter()).parseDelimitedList(null).isEmpty());
  }

  /**
   * Test {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code 42,foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); given '42'; when ArrayList() add '42'; then return '42,foo'")
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
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@code 42}.</li>
   *   <li>Then return {@code 42,foo}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); given ArrayList() add '42'; then return '42,foo'")
  void testConvertToDelimitedString_givenArrayListAdd42_thenReturn42Foo() {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("42");
    stringList.add("foo");
    COWArrayList<String> stringList2 = mock(COWArrayList.class);
    when(stringList2.iterator()).thenReturn(stringList.iterator());

    // Act
    String actualConvertToDelimitedStringResult = associationXMLConverter.convertToDelimitedString(stringList2);

    // Assert
    verify(stringList2).iterator();
    assertEquals("42,foo", actualConvertToDelimitedStringResult);
  }

  /**
   * Test {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} iterator.</li>
   *   <li>Then calls {@link COWArrayList#iterator()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); given ArrayList() iterator; then calls iterator()")
  void testConvertToDelimitedString_givenArrayListIterator_thenCallsIterator() {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    COWArrayList<String> stringList = mock(COWArrayList.class);

    ArrayList<String> stringList2 = new ArrayList<>();
    when(stringList.iterator()).thenReturn(stringList2.iterator());

    // Act
    String actualConvertToDelimitedStringResult = associationXMLConverter.convertToDelimitedString(stringList);

    // Assert
    verify(stringList).iterator();
    assertEquals("", actualConvertToDelimitedStringResult);
  }

  /**
   * Test {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}.
   * <ul>
   *   <li>Given {@code String List}.</li>
   *   <li>Then return {@code String List}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); given 'String List'; then return 'String List'")
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
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); when ArrayList(); then return empty string")
  void testConvertToDelimitedString_whenArrayList_thenReturnEmptyString() {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    // Act and Assert
    assertEquals("", associationXMLConverter.convertToDelimitedString(new ArrayList<>()));
  }

  /**
   * Test {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  @DisplayName("Test convertToDelimitedString(List); when 'null'; then return empty string")
  void testConvertToDelimitedString_whenNull_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", (new AssociationXMLConverter()).convertToDelimitedString(null));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link FormValue} (default
   * constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeFormProperties(FlowElement, boolean, XMLStreamWriter); given ArrayList() add FormValue (default constructor); then return 'false'")
  void testWriteFormProperties_givenArrayListAddFormValue_thenReturnFalse() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    ArrayList<FormValue> formValues = new ArrayList<>();
    formValues.add(new FormValue());

    FormProperty formProperty = new FormProperty();
    formProperty.setReadable(false);
    formProperty.setWriteable(false);
    formProperty.setRequired(false);
    formProperty.setFormValues(formValues);

    ArrayList<FormProperty> formProperties = new ArrayList<>();
    formProperties.add(formProperty);

    UserTask flowElement = new UserTask();
    flowElement.setFormProperties(formProperties);

    // Act and Assert
    assertFalse(associationXMLConverter.writeFormProperties(flowElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeFormProperties(FlowElement, boolean, XMLStreamWriter); given ArrayList(); then return 'false'")
  void testWriteFormProperties_givenArrayList_thenReturnFalse() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    UserTask flowElement = new UserTask();
    flowElement.setFormProperties(new ArrayList<>());

    // Act and Assert
    assertFalse(associationXMLConverter.writeFormProperties(flowElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link StartEvent} (default constructor) FormProperties is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeFormProperties(FlowElement, boolean, XMLStreamWriter); given 'null'; when StartEvent (default constructor) FormProperties is 'null'")
  void testWriteFormProperties_givenNull_whenStartEventFormPropertiesIsNull() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    StartEvent flowElement = new StartEvent();
    flowElement.setFormProperties(null);

    // Act and Assert
    assertFalse(associationXMLConverter.writeFormProperties(flowElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link UserTask} (default constructor) FormProperties is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeFormProperties(FlowElement, boolean, XMLStreamWriter); given 'null'; when UserTask (default constructor) FormProperties is 'null'")
  void testWriteFormProperties_givenNull_whenUserTaskFormPropertiesIsNull() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    UserTask flowElement = new UserTask();
    flowElement.setFormProperties(null);

    // Act and Assert
    assertFalse(associationXMLConverter.writeFormProperties(flowElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeFormProperties(FlowElement, boolean, XMLStreamWriter); when AdhocSubProcess (default constructor); then return 'true'")
  void testWriteFormProperties_whenAdhocSubProcess_thenReturnTrue() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    AdhocSubProcess flowElement = new AdhocSubProcess();

    // Act and Assert
    assertTrue(associationXMLConverter.writeFormProperties(flowElement, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeListeners(BaseElement, boolean, XMLStreamWriter); when ActivitiListener (default constructor); then return 'true'")
  void testWriteListeners_whenActivitiListener_thenReturnTrue() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertTrue(associationXMLConverter.writeListeners(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeListeners(BaseElement, boolean, XMLStreamWriter); when AdhocSubProcess (default constructor); then return 'true'")
  void testWriteListeners_whenAdhocSubProcess_thenReturnTrue() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    AdhocSubProcess element = new AdhocSubProcess();

    // Act and Assert
    assertTrue(associationXMLConverter.writeListeners(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@code false}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeListeners(BaseElement, boolean, XMLStreamWriter); when 'false'; then return 'false'")
  void testWriteListeners_whenFalse_thenReturnFalse() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertFalse(associationXMLConverter.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)")
  void testWriteTimerDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getCalendarName()).thenReturn("");
    when(timerDefinition.getTimeDate()).thenReturn("2020-03-01");
    when(timerDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeCharacters(eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerDefinition).getExtensionElements();
    verify(timerDefinition).getCalendarName();
    verify(timerDefinition, atLeast(1)).getTimeDate();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default
   * constructor).</li>
   *   <li>Then calls {@link TimerEventDefinition#getEndDate()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor); then calls getEndDate()")
  void testWriteTimerDefinition_givenArrayListAddExtensionElement_thenCallsGetEndDate() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("timerEventDefinition", extensionElementList);
    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getEndDate()).thenReturn("2020-03-01");
    when(timerDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerDefinition.getTimeCycle()).thenReturn("Time Cycle");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeCharacters(eq("Time Cycle"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerDefinition, atLeast(1)).getExtensionElements();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition, atLeast(1)).getEndDate();
    verify(timerDefinition, atLeast(1)).getTimeCycle();
    verify(timerDefinition).getTimeDate();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ExtensionElement} {@link ExtensionElement#getNamespace()}
   * return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); given ExtensionElement getNamespace() return empty string")
  void testWriteTimerDefinition_givenExtensionElementGetNamespaceReturnEmptyString() throws Exception {
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
    stringListMap.put("timerEventDefinition", extensionElementList);
    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getEndDate()).thenReturn("2020-03-01");
    when(timerDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerDefinition.getTimeCycle()).thenReturn("Time Cycle");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeCharacters(eq("Time Cycle"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(extensionElement).getAttributes();
    verify(timerDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition, atLeast(1)).getEndDate();
    verify(timerDefinition, atLeast(1)).getTimeCycle();
    verify(timerDefinition).getTimeDate();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code timerEventDefinition} is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); given HashMap() 'timerEventDefinition' is ArrayList()")
  void testWriteTimerDefinition_givenHashMapTimerEventDefinitionIsArrayList() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("timerEventDefinition", new ArrayList<>());
    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getEndDate()).thenReturn("2020-03-01");
    when(timerDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerDefinition.getTimeCycle()).thenReturn("Time Cycle");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeCharacters(eq("Time Cycle"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerDefinition, atLeast(1)).getExtensionElements();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition, atLeast(1)).getEndDate();
    verify(timerDefinition, atLeast(1)).getTimeCycle();
    verify(timerDefinition).getTimeDate();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls {@link TimerEventDefinition#getEndDate()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); given HashMap(); then calls getEndDate()")
  void testWriteTimerDefinition_givenHashMap_thenCallsGetEndDate() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getEndDate()).thenReturn("2020-03-01");
    when(timerDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerDefinition.getTimeCycle()).thenReturn("Time Cycle");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeCharacters(eq("Time Cycle"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerDefinition).getExtensionElements();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition, atLeast(1)).getEndDate();
    verify(timerDefinition, atLeast(1)).getTimeCycle();
    verify(timerDefinition).getTimeDate();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls {@link TimerEventDefinition#getTimeDuration()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); then calls getTimeDuration()")
  void testWriteTimerDefinition_thenCallsGetTimeDuration() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerDefinition.getTimeCycle()).thenReturn("");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getTimeDuration()).thenReturn("Time Duration");
    when(timerDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("businessCalendarName"),
        eq("Calendar Name"));
    verify(xtw).writeCharacters(eq("Time Duration"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerDefinition).getExtensionElements();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition).getTimeCycle();
    verify(timerDefinition).getTimeDate();
    verify(timerDefinition, atLeast(1)).getTimeDuration();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); then calls writeNamespace(String, String)")
  void testWriteTimerDefinition_thenCallsWriteNamespace() throws Exception {
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
    stringListMap.put("timerEventDefinition", extensionElementList);
    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getEndDate()).thenReturn("2020-03-01");
    when(timerDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerDefinition.getTimeCycle()).thenReturn("Time Cycle");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeNamespace(eq("Namespace Prefix"), eq("Namespace"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeCharacters(eq("Time Cycle"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace Prefix"), eq("Name"), eq("Namespace"));
    verify(extensionElement).getAttributes();
    verify(timerDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition, atLeast(1)).getEndDate();
    verify(timerDefinition, atLeast(1)).getTimeCycle();
    verify(timerDefinition).getTimeDate();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
  void testWriteTimerDefinition_thenCallsWriteStartElement() throws Exception {
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
    stringListMap.put("timerEventDefinition", extensionElementList);
    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getEndDate()).thenReturn("2020-03-01");
    when(timerDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerDefinition.getTimeCycle()).thenReturn("Time Cycle");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeCharacters(eq("Time Cycle"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(timerDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition, atLeast(1)).getEndDate();
    verify(timerDefinition, atLeast(1)).getTimeCycle();
    verify(timerDefinition).getTimeDate();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
  void testWriteTimerDefinition_thenCallsWriteStartElement2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("timerEventDefinition", new ArrayList<>());
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap2 = new HashMap<>();
    stringListMap2.put("timerEventDefinition", extensionElementList);
    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getEndDate()).thenReturn("2020-03-01");
    when(timerDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerDefinition.getTimeCycle()).thenReturn("Time Cycle");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getExtensionElements()).thenReturn(stringListMap2);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw).writeCharacters(eq("Time Cycle"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(timerDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition, atLeast(1)).getEndDate();
    verify(timerDefinition, atLeast(1)).getTimeCycle();
    verify(timerDefinition).getTimeDate();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link TimerEventDefinition}
   * {@link TimerEventDefinition#getEndDate()} return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); when TimerEventDefinition getEndDate() return empty string")
  void testWriteTimerDefinition_whenTimerEventDefinitionGetEndDateReturnEmptyString() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getEndDate()).thenReturn("");
    when(timerDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerDefinition.getTimeCycle()).thenReturn("Time Cycle");
    when(timerDefinition.getTimeDate()).thenReturn("");
    when(timerDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("businessCalendarName"),
        eq("Calendar Name"));
    verify(xtw).writeCharacters(eq("Time Cycle"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerDefinition).getExtensionElements();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition).getEndDate();
    verify(timerDefinition, atLeast(1)).getTimeCycle();
    verify(timerDefinition).getTimeDate();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link TimerEventDefinition}
   * {@link TimerEventDefinition#getTimeDate()} return {@code 2020-03-01}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); when TimerEventDefinition getTimeDate() return '2020-03-01'")
  void testWriteTimerDefinition_whenTimerEventDefinitionGetTimeDateReturn20200301() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    TimerEventDefinition timerDefinition = mock(TimerEventDefinition.class);
    when(timerDefinition.getCalendarName()).thenReturn("Calendar Name");
    when(timerDefinition.getTimeDate()).thenReturn("2020-03-01");
    when(timerDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCharacters(Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("businessCalendarName"),
        eq("Calendar Name"));
    verify(xtw).writeCharacters(eq("2020-03-01"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(timerDefinition).getExtensionElements();
    verify(timerDefinition, atLeast(1)).getCalendarName();
    verify(timerDefinition, atLeast(1)).getTimeDate();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link TimerEventDefinition} (default constructor).</li>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndElement()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter); when TimerEventDefinition (default constructor); then calls writeEndElement()")
  void testWriteTimerDefinition_whenTimerEventDefinition_thenCallsWriteEndElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    TimerEventDefinition timerDefinition = new TimerEventDefinition();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTimerDefinition(parentEvent, timerDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("timerEventDefinition"));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)")
  void testWriteSignalDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("signalEventDefinition"));

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("signalEventDefinition", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap2 = new HashMap<>();
    stringListMap2.put("signalEventDefinition", extensionElementList);
    SignalEventDefinition signalDefinition = mock(SignalEventDefinition.class);
    when(signalDefinition.isAsync()).thenReturn(true);
    when(signalDefinition.getSignalRef()).thenReturn("Signal Ref");
    when(signalDefinition.getExtensionElements()).thenReturn(stringListMap2);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("signalRef"), eq("Signal Ref"));
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("async"), eq("true"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(signalDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
    verify(signalDefinition).getSignalRef();
    verify(signalDefinition).isAsync();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ExtensionAttribute#ExtensionAttribute()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionAttribute()")
  void testWriteSignalDefinition_givenArrayListAddExtensionAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute());

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("signalEventDefinition", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap2 = new HashMap<>();
    stringListMap2.put("signalEventDefinition", extensionElementList);
    SignalEventDefinition signalDefinition = mock(SignalEventDefinition.class);
    when(signalDefinition.isAsync()).thenReturn(true);
    when(signalDefinition.getSignalRef()).thenReturn("Signal Ref");
    when(signalDefinition.getExtensionElements()).thenReturn(stringListMap2);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("signalRef"), eq("Signal Ref"));
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("async"), eq("true"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(signalDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
    verify(signalDefinition).getSignalRef();
    verify(signalDefinition).isAsync();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor)")
  void testWriteSignalDefinition_givenArrayListAddExtensionElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(new ExtensionElement());

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("signalEventDefinition", extensionElementList);
    SignalEventDefinition signalDefinition = mock(SignalEventDefinition.class);
    when(signalDefinition.isAsync()).thenReturn(true);
    when(signalDefinition.getSignalRef()).thenReturn("Signal Ref");
    when(signalDefinition.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("signalRef"), eq("Signal Ref"));
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("async"), eq("true"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(signalDefinition, atLeast(1)).getExtensionElements();
    verify(signalDefinition).getSignalRef();
    verify(signalDefinition).isAsync();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given empty string")
  void testWriteSignalDefinition_givenEmptyString() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();
    SignalEventDefinition signalDefinition = mock(SignalEventDefinition.class);
    when(signalDefinition.isAsync()).thenReturn(true);
    when(signalDefinition.getSignalRef()).thenReturn("");
    when(signalDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("async"), eq("true"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("signalEventDefinition"));
    verify(signalDefinition).getExtensionElements();
    verify(signalDefinition).getSignalRef();
    verify(signalDefinition).isAsync();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ExtensionElement} {@link ExtensionElement#getNamespace()}
   * return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given ExtensionElement getNamespace() return empty string")
  void testWriteSignalDefinition_givenExtensionElementGetNamespaceReturnEmptyString() throws Exception {
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

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("signalEventDefinition", extensionElementList);
    SignalEventDefinition signalDefinition = mock(SignalEventDefinition.class);
    when(signalDefinition.isAsync()).thenReturn(true);
    when(signalDefinition.getSignalRef()).thenReturn("Signal Ref");
    when(signalDefinition.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("signalRef"), eq("Signal Ref"));
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("async"), eq("true"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(extensionElement).getAttributes();
    verify(signalDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
    verify(signalDefinition).getSignalRef();
    verify(signalDefinition).isAsync();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given HashMap(); then calls writeAttribute(String, String)")
  void testWriteSignalDefinition_givenHashMap_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();
    SignalEventDefinition signalDefinition = mock(SignalEventDefinition.class);
    when(signalDefinition.isAsync()).thenReturn(true);
    when(signalDefinition.getSignalRef()).thenReturn("Signal Ref");
    when(signalDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("signalRef"), eq("Signal Ref"));
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("async"), eq("true"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("signalEventDefinition"));
    verify(signalDefinition).getExtensionElements();
    verify(signalDefinition).getSignalRef();
    verify(signalDefinition).isAsync();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>When {@link BoundaryEvent} (default constructor).</li>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given HashMap(); when BoundaryEvent (default constructor); then calls writeAttribute(String, String)")
  void testWriteSignalDefinition_givenHashMap_whenBoundaryEvent_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    SignalEventDefinition signalDefinition = mock(SignalEventDefinition.class);
    when(signalDefinition.getSignalRef()).thenReturn("Signal Ref");
    when(signalDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("signalRef"), eq("Signal Ref"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("signalEventDefinition"));
    verify(signalDefinition).getExtensionElements();
    verify(signalDefinition).getSignalRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); given 'null'")
  void testWriteSignalDefinition_givenNull() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();
    SignalEventDefinition signalDefinition = mock(SignalEventDefinition.class);
    when(signalDefinition.isAsync()).thenReturn(true);
    when(signalDefinition.getSignalRef()).thenReturn("null");
    when(signalDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("async"), eq("true"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("signalEventDefinition"));
    verify(signalDefinition).getExtensionElements();
    verify(signalDefinition).getSignalRef();
    verify(signalDefinition).isAsync();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); then calls writeAttribute(String, String)")
  void testWriteSignalDefinition_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("signalEventDefinition", new ArrayList<>());
    SignalEventDefinition signalDefinition = mock(SignalEventDefinition.class);
    when(signalDefinition.isAsync()).thenReturn(true);
    when(signalDefinition.getSignalRef()).thenReturn("Signal Ref");
    when(signalDefinition.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("signalRef"), eq("Signal Ref"));
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("async"), eq("true"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(signalDefinition, atLeast(1)).getExtensionElements();
    verify(signalDefinition).getSignalRef();
    verify(signalDefinition).isAsync();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); then calls writeNamespace(String, String)")
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

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("signalEventDefinition", extensionElementList);
    SignalEventDefinition signalDefinition = mock(SignalEventDefinition.class);
    when(signalDefinition.isAsync()).thenReturn(true);
    when(signalDefinition.getSignalRef()).thenReturn("Signal Ref");
    when(signalDefinition.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert
    verify(xtw).writeAttribute(eq("signalRef"), eq("Signal Ref"));
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("async"), eq("true"));
    verify(xtw).writeNamespace(eq("Namespace Prefix"), eq("Namespace"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace Prefix"), eq("Name"), eq("Namespace"));
    verify(extensionElement).getAttributes();
    verify(signalDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
    verify(signalDefinition).getSignalRef();
    verify(signalDefinition).isAsync();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
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

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("signalEventDefinition", extensionElementList);
    SignalEventDefinition signalDefinition = mock(SignalEventDefinition.class);
    when(signalDefinition.isAsync()).thenReturn(true);
    when(signalDefinition.getSignalRef()).thenReturn("Signal Ref");
    when(signalDefinition.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("signalRef"), eq("Signal Ref"));
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("async"), eq("true"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(signalDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
    verify(signalDefinition).getSignalRef();
    verify(signalDefinition).isAsync();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
  void testWriteSignalDefinition_thenCallsWriteStartElement2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("signalEventDefinition", new ArrayList<>());
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
    when(extensionElement.getElementText()).thenReturn("Element Text");
    when(extensionElement.getNamespace()).thenReturn("Namespace");
    when(extensionElement.getAttributes()).thenReturn(stringListMap);
    when(extensionElement.getName()).thenReturn("Name");

    ArrayList<ExtensionElement> extensionElementList = new ArrayList<>();
    extensionElementList.add(extensionElement);

    HashMap<String, List<ExtensionElement>> stringListMap2 = new HashMap<>();
    stringListMap2.put("signalEventDefinition", extensionElementList);
    SignalEventDefinition signalDefinition = mock(SignalEventDefinition.class);
    when(signalDefinition.isAsync()).thenReturn(true);
    when(signalDefinition.getSignalRef()).thenReturn("Signal Ref");
    when(signalDefinition.getExtensionElements()).thenReturn(stringListMap2);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("signalRef"), eq("Signal Ref"));
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("async"), eq("true"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(signalDefinition, atLeast(1)).getExtensionElements();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
    verify(signalDefinition).getSignalRef();
    verify(signalDefinition).isAsync();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).</li>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndElement()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); when BoundaryEvent (default constructor); then calls writeEndElement()")
  void testWriteSignalDefinition_whenBoundaryEvent_thenCallsWriteEndElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("signalEventDefinition"));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link SignalEventDefinition} (default constructor).</li>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndElement()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter); when SignalEventDefinition (default constructor); then calls writeEndElement()")
  void testWriteSignalDefinition_whenSignalEventDefinition_thenCallsWriteEndElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ThrowEvent parentEvent = new ThrowEvent();
    SignalEventDefinition signalDefinition = new SignalEventDefinition();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeSignalDefinition(parentEvent, signalDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("signalEventDefinition"));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCancelDefinition(Event, CancelEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndElement()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCancelDefinition(Event, CancelEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCancelDefinition(Event, CancelEventDefinition, XMLStreamWriter); then calls writeEndElement()")
  void testWriteCancelDefinition_thenCallsWriteEndElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    CancelEventDefinition cancelEventDefinition = new CancelEventDefinition();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCancelDefinition(parentEvent, cancelEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("cancelEventDefinition"));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)")
  void testWriteCompensateDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("compensateEventDefinition"));

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activityRef"), eq("Activity Ref"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)")
  void testWriteCompensateDefinition2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionAttribute extensionAttribute = new ExtensionAttribute("compensateEventDefinition");
    extensionAttribute.setValue("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ExtensionAttribute#ExtensionAttribute()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionAttribute()")
  void testWriteCompensateDefinition_givenArrayListAddExtensionAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute());

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activityRef"), eq("Activity Ref"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor)")
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activityRef"), eq("Activity Ref"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); given empty string")
  void testWriteCompensateDefinition_givenEmptyString() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("compensateEventDefinition"));
    verify(compensateEventDefinition).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ExtensionElement} {@link ExtensionElement#getNamespace()}
   * return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); given ExtensionElement getNamespace() return empty string")
  void testWriteCompensateDefinition_givenExtensionElementGetNamespaceReturnEmptyString() throws Exception {
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activityRef"), eq("Activity Ref"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(extensionElement).getAttributes();
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); given HashMap(); then calls writeAttribute(String, String)")
  void testWriteCompensateDefinition_givenHashMap_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("Activity Ref");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activityRef"), eq("Activity Ref"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("compensateEventDefinition"));
    verify(compensateEventDefinition).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); given 'null'")
  void testWriteCompensateDefinition_givenNull() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("null");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("compensateEventDefinition"));
    verify(compensateEventDefinition).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); then calls writeAttribute(String, String)")
  void testWriteCompensateDefinition_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", new ArrayList<>());
    CompensateEventDefinition compensateEventDefinition = mock(CompensateEventDefinition.class);
    when(compensateEventDefinition.getActivityRef()).thenReturn("Activity Ref");
    when(compensateEventDefinition.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activityRef"), eq("Activity Ref"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); then calls writeNamespace(String, String)")
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert
    verify(xtw).writeAttribute(eq("activityRef"), eq("Activity Ref"));
    verify(xtw).writeNamespace(eq("Namespace Prefix"), eq("Namespace"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace Prefix"), eq("Name"), eq("Namespace"));
    verify(extensionElement).getAttributes();
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activityRef"), eq("Activity Ref"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
  void testWriteCompensateDefinition_thenCallsWriteStartElement2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("compensateEventDefinition", new ArrayList<>());
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activityRef"), eq("Activity Ref"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(compensateEventDefinition, atLeast(1)).getExtensionElements();
    verify(compensateEventDefinition).getActivityRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link CompensateEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter); when CompensateEventDefinition (default constructor)")
  void testWriteCompensateDefinition_whenCompensateEventDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    CompensateEventDefinition compensateEventDefinition = new CompensateEventDefinition();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeCompensateDefinition(parentEvent, compensateEventDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("compensateEventDefinition"));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)")
  void testWriteMessageDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("Message Ref");
    when(messageDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = new BpmnModel();
    model.setTargetNamespace("messageEventDefinition");
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("messageRef"), eq("Message Ref"));
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("messageExpression"),
        eq("Message Expression"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("messageEventDefinition"));
    verify(messageDefinition).getExtensionElements();
    verify(messageDefinition).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)")
  void testWriteMessageDefinition2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("messageEventDefinition");
    when(messageDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = new BpmnModel();
    model.setTargetNamespace("messageEventDefinition");
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("messageEventDefinition"));
    verify(messageDefinition).getExtensionElements();
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code :}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); given ':'")
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
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("messageEventDefinition"));
    verify(messageDefinition).getExtensionElements();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(model).setTargetNamespace(eq("messageEventDefinition"));
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} {@code messageRef} is
   * {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); given HashMap() 'messageRef' is ArrayList()")
  void testWriteMessageDefinition_givenHashMapMessageRefIsArrayList() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("messageRef", new ArrayList<>());
    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("Message Ref");
    when(messageDefinition.getExtensionElements()).thenReturn(stringListMap);

    BpmnModel model = new BpmnModel();
    model.setTargetNamespace("messageEventDefinition");
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("messageRef"), eq("Message Ref"));
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(messageDefinition, atLeast(1)).getExtensionElements();
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code http://activiti.org/bpmn}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); given 'http://activiti.org/bpmn'")
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
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("messageRef"), eq("http//activiti.org/bpmn"));
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("messageEventDefinition"));
    verify(messageDefinition).getExtensionElements();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(model).setTargetNamespace(eq("messageEventDefinition"));
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code Namespace}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); given 'Namespace'")
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
    stringStringMap.put("messageEventDefinition", "messageEventDefinition");
    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("Namespace");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    doNothing().when(model).setTargetNamespace(Mockito.<String>any());
    model.setTargetNamespace("messageEventDefinition");
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("messageRef"), eq("Message Ref"));
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("messageEventDefinition"));
    verify(messageDefinition).getExtensionElements();
    verify(model).getNamespace(eq("messageEventDefinition"));
    verify(model).getNamespaces();
    verify(model).getTargetNamespace();
    verify(model).setTargetNamespace(eq("messageEventDefinition"));
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); given 'null'")
  void testWriteMessageDefinition_givenNull() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    MessageEventDefinition messageDefinition = mock(MessageEventDefinition.class);
    when(messageDefinition.getCorrelationKey()).thenReturn("Correlation Key");
    when(messageDefinition.getMessageExpression()).thenReturn("Message Expression");
    when(messageDefinition.getMessageRef()).thenReturn("null");
    when(messageDefinition.getExtensionElements()).thenReturn(new HashMap<>());

    BpmnModel model = new BpmnModel();
    model.setTargetNamespace("messageEventDefinition");
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("messageEventDefinition"));
    verify(messageDefinition).getExtensionElements();
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code Target Namespace}.</li>
   *   <li>Then calls {@link BpmnModel#getNamespaces()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); given 'Target Namespace'; then calls getNamespaces()")
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
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("messageRef"), eq("Message Ref"));
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("messageEventDefinition"));
    verify(messageDefinition).getExtensionElements();
    verify(model).getNamespaces();
    verify(model).getTargetNamespace();
    verify(model).setTargetNamespace(eq("messageEventDefinition"));
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#getNamespace(String)} return
   * empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); when BpmnModel getNamespace(String) return empty string")
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
    stringStringMap.put("messageEventDefinition", "messageEventDefinition");
    BpmnModel model = mock(BpmnModel.class);
    when(model.getNamespace(Mockito.<String>any())).thenReturn("");
    when(model.getNamespaces()).thenReturn(stringStringMap);
    when(model.getTargetNamespace()).thenReturn("Target Namespace");
    doNothing().when(model).setTargetNamespace(Mockito.<String>any());
    model.setTargetNamespace("messageEventDefinition");
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("messageRef"), eq("messageEventDefinitionMessage Ref"));
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("messageEventDefinition"));
    verify(messageDefinition).getExtensionElements();
    verify(model).getNamespace(eq("messageEventDefinition"));
    verify(model).getNamespaces();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(model).setTargetNamespace(eq("messageEventDefinition"));
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#getTargetNamespace()} return
   * empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); when BpmnModel getTargetNamespace() return empty string")
  void testWriteMessageDefinition_whenBpmnModelGetTargetNamespaceReturnEmptyString() throws Exception {
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
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("messageRef"), eq("Message Ref"));
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("messageEventDefinition"));
    verify(messageDefinition).getExtensionElements();
    verify(model, atLeast(1)).getTargetNamespace();
    verify(model).setTargetNamespace(eq("messageEventDefinition"));
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor) TargetNamespace is
   * {@code messageEventDefinition}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); when BpmnModel (default constructor) TargetNamespace is 'messageEventDefinition'")
  void testWriteMessageDefinition_whenBpmnModelTargetNamespaceIsMessageEventDefinition() throws Exception {
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
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeMessageDefinition(parentEvent, messageDefinition, model, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("messageRef"), eq("Message Ref"));
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("messageEventDefinition"));
    verify(messageDefinition).getExtensionElements();
    verify(messageDefinition, atLeast(1)).getCorrelationKey();
    verify(messageDefinition, atLeast(1)).getMessageExpression();
    verify(messageDefinition).getMessageRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link MessageEventDefinition} (default constructor).</li>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndElement()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter); when MessageEventDefinition (default constructor); then calls writeEndElement()")
  void testWriteMessageDefinition_whenMessageEventDefinition_thenCallsWriteEndElement() throws Exception {
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

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("messageEventDefinition"));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)")
  void testWriteErrorDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("errorEventDefinition"));

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("errorRef"), eq("An error occurred"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)")
  void testWriteErrorDefinition2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionAttribute extensionAttribute = new ExtensionAttribute("errorEventDefinition");
    extensionAttribute.setValue("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ExtensionAttribute#ExtensionAttribute()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionAttribute()")
  void testWriteErrorDefinition_givenArrayListAddExtensionAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute());

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("errorRef"), eq("An error occurred"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor)")
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("errorRef"), eq("An error occurred"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); given empty string")
  void testWriteErrorDefinition_givenEmptyString() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("");
    when(errorDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("errorEventDefinition"));
    verify(errorDefinition).getExtensionElements();
    verify(errorDefinition).getErrorRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ExtensionElement} {@link ExtensionElement#getNamespace()}
   * return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); given ExtensionElement getNamespace() return empty string")
  void testWriteErrorDefinition_givenExtensionElementGetNamespaceReturnEmptyString() throws Exception {
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("errorRef"), eq("An error occurred"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(extensionElement).getAttributes();
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement).getNamespace();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); given HashMap(); then calls writeAttribute(String, String)")
  void testWriteErrorDefinition_givenHashMap_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("An error occurred");
    when(errorDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("errorRef"), eq("An error occurred"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("errorEventDefinition"));
    verify(errorDefinition).getExtensionElements();
    verify(errorDefinition).getErrorRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link ErrorEventDefinition}
   * {@link ErrorEventDefinition#getErrorRef()} return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); given 'null'; when ErrorEventDefinition getErrorRef() return 'null'")
  void testWriteErrorDefinition_givenNull_whenErrorEventDefinitionGetErrorRefReturnNull() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("null");
    when(errorDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("errorEventDefinition"));
    verify(errorDefinition).getExtensionElements();
    verify(errorDefinition).getErrorRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); then calls writeAttribute(String, String)")
  void testWriteErrorDefinition_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionElement>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", new ArrayList<>());
    ErrorEventDefinition errorDefinition = mock(ErrorEventDefinition.class);
    when(errorDefinition.getErrorRef()).thenReturn("An error occurred");
    when(errorDefinition.getExtensionElements()).thenReturn(stringListMap);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("errorRef"), eq("An error occurred"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); then calls writeNamespace(String, String)")
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeNamespace(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert
    verify(xtw).writeAttribute(eq("errorRef"), eq("An error occurred"));
    verify(xtw).writeNamespace(eq("Namespace Prefix"), eq("Namespace"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace Prefix"), eq("Name"), eq("Namespace"));
    verify(extensionElement).getAttributes();
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement, atLeast(1)).getNamespacePrefix();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("errorRef"), eq("An error occurred"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
  void testWriteErrorDefinition_thenCallsWriteStartElement2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("errorEventDefinition", new ArrayList<>());
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("errorRef"), eq("An error occurred"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
    verify(extensionElement).getAttributes();
    verify(errorDefinition, atLeast(1)).getExtensionElements();
    verify(errorDefinition).getErrorRef();
    verify(extensionElement, atLeast(1)).getElementText();
    verify(extensionElement, atLeast(1)).getName();
    verify(extensionElement, atLeast(1)).getNamespace();
    verify(extensionElement).getNamespacePrefix();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link ErrorEventDefinition} (default constructor).</li>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeEndElement()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter); when ErrorEventDefinition (default constructor); then calls writeEndElement()")
  void testWriteErrorDefinition_whenErrorEventDefinition_thenCallsWriteEndElement() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    ErrorEventDefinition errorDefinition = new ErrorEventDefinition();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeErrorDefinition(parentEvent, errorDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("errorEventDefinition"));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)")
  void testWriteTerminateDefinition() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute("terminateEventDefinition"));

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("terminateEventDefinition", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        eq("true"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
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
   * Test
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add
   * {@link ExtensionAttribute#ExtensionAttribute()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionAttribute()")
  void testWriteTerminateDefinition_givenArrayListAddExtensionAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(new ExtensionAttribute());

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("terminateEventDefinition", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        eq("true"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
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
   * Test
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ExtensionElement} (default
   * constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); given ArrayList() add ExtensionElement (default constructor)")
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
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        eq("true"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(terminateDefinition, atLeast(1)).getExtensionElements();
    verify(terminateDefinition).isTerminateAll();
    verify(terminateDefinition).isTerminateMultiInstance();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ExtensionElement} {@link ExtensionElement#getNamespace()}
   * return empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); given ExtensionElement getNamespace() return empty string")
  void testWriteTerminateDefinition_givenExtensionElementGetNamespaceReturnEmptyString() throws Exception {
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
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        eq("true"));
    verify(xtw).writeCData(eq("Element Text"));
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
   * Test
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); given HashMap(); then calls writeAttribute(String, String, String, String)")
  void testWriteTerminateDefinition_givenHashMap_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();
    TerminateEventDefinition terminateDefinition = mock(TerminateEventDefinition.class);
    when(terminateDefinition.isTerminateAll()).thenReturn(true);
    when(terminateDefinition.isTerminateMultiInstance()).thenReturn(true);
    when(terminateDefinition.getExtensionElements()).thenReturn(new HashMap<>());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        eq("true"));
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("terminateEventDefinition"));
    verify(terminateDefinition).getExtensionElements();
    verify(terminateDefinition).isTerminateAll();
    verify(terminateDefinition).isTerminateMultiInstance();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); then calls writeAttribute(String, String, String, String)")
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
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        eq("true"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(terminateDefinition, atLeast(1)).getExtensionElements();
    verify(terminateDefinition).isTerminateAll();
    verify(terminateDefinition).isTerminateMultiInstance();
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); then calls writeAttribute(String, String)")
  void testWriteTerminateDefinition_thenCallsWriteAttribute2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionAttribute extensionAttribute = new ExtensionAttribute("terminateEventDefinition");
    extensionAttribute.setValue("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("terminateEventDefinition", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("terminateEventDefinition"), eq("42"));
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        eq("true"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
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
   * Test
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); then calls writeAttribute(String, String, String)")
  void testWriteTerminateDefinition_thenCallsWriteAttribute3() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    ExtensionAttribute extensionAttribute = new ExtensionAttribute("terminateEventDefinition",
        "terminateEventDefinition");
    extensionAttribute.setValue("42");

    ArrayList<ExtensionAttribute> extensionAttributeList = new ArrayList<>();
    extensionAttributeList.add(extensionAttribute);

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("terminateEventDefinition", extensionAttributeList);
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("terminateEventDefinition"), eq("terminateEventDefinition"), eq("42"));
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        eq("true"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
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
   * Test
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeNamespace(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); then calls writeNamespace(String, String)")
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
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        eq("true"));
    verify(xtw).writeNamespace(eq("Namespace Prefix"), eq("Namespace"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace Prefix"), eq("Name"), eq("Namespace"));
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
   * Test
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
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
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        eq("true"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
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
   * Test
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link IndentingXMLStreamWriter#writeStartElement(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); then calls writeStartElement(String, String)")
  void testWriteTerminateDefinition_thenCallsWriteStartElement2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    BoundaryEvent parentEvent = new BoundaryEvent();

    HashMap<String, List<ExtensionAttribute>> stringListMap = new HashMap<>();
    stringListMap.put("terminateEventDefinition", new ArrayList<>());
    ExtensionElement extensionElement = mock(ExtensionElement.class);
    when(extensionElement.getNamespacePrefix()).thenReturn("");
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
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeCData(Mockito.<String>any());
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any());

    // Act
    associationXMLConverter.writeTerminateDefinition(parentEvent, terminateDefinition, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        eq("true"));
    verify(xtw).writeCData(eq("Element Text"));
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(xtw).writeStartElement(eq("Namespace"), eq("Name"));
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
   * Test
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link TerminateEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter); when TerminateEventDefinition (default constructor)")
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

    // Assert that nothing has changed
    verify(xtw).writeEndElement();
    verify(xtw).writeStartElement(eq("terminateEventDefinition"));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeDefaultAttribute(String, String, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeDefaultAttribute(String, String, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeDefaultAttribute(String, String, XMLStreamWriter); when '42'; then calls writeAttribute(String, String)")
  void testWriteDefaultAttribute_when42_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    associationXMLConverter.writeDefaultAttribute("Attribute Name", "42", xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("Attribute Name"), eq("42"));
  }

  /**
   * Test
   * {@link BaseBpmnXMLConverter#writeQualifiedAttribute(String, String, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeQualifiedAttribute(String, String, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeQualifiedAttribute(String, String, XMLStreamWriter); when '42'; then calls writeAttribute(String, String, String, String)")
  void testWriteQualifiedAttribute_when42_thenCallsWriteAttribute() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    associationXMLConverter.writeQualifiedAttribute("Attribute Name", "42", xtw);

    // Assert that nothing has changed
    verify(xtw).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("Attribute Name"), eq("42"));
  }
}
