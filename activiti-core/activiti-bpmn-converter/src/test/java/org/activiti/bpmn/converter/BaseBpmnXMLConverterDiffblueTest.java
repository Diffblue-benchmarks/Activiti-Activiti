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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BaseBpmnXMLConverterDiffblueTest {
  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToXML(XMLStreamWriter, BaseElement, BpmnModel)}
   */
  @Test
  void testConvertToXML() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertTrue(associationXMLConverter.writeExtensionChildElements(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertFalse(
        associationXMLConverter.writeExtensionChildElements(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test: {@link BaseBpmnXMLConverter#parseDelimitedList(String)}
   */
  @Test
  void testParseDelimitedList() {
    // Arrange and Act
    List<String> actualParseDelimitedListResult = (new AssociationXMLConverter()).parseDelimitedList("Expression");

    // Assert
    assertEquals(1, actualParseDelimitedListResult.size());
    assertEquals("Expression", actualParseDelimitedListResult.get(0));
  }

  /**
   * Method under test: {@link BaseBpmnXMLConverter#parseDelimitedList(String)}
   */
  @Test
  void testParseDelimitedList2() {
    // Arrange, Act and Assert
    assertTrue((new AssociationXMLConverter()).parseDelimitedList(null).isEmpty());
  }

  /**
   * Method under test: {@link BaseBpmnXMLConverter#parseDelimitedList(String)}
   */
  @Test
  void testParseDelimitedList3() {
    // Arrange, Act and Assert
    assertTrue((new AssociationXMLConverter()).parseDelimitedList("").isEmpty());
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  void testConvertToDelimitedString() {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    // Act and Assert
    assertEquals("", associationXMLConverter.convertToDelimitedString(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  void testConvertToDelimitedString2() {
    // Arrange, Act and Assert
    assertEquals("", (new AssociationXMLConverter()).convertToDelimitedString(null));
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  void testConvertToDelimitedString3() {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("String List");

    // Act and Assert
    assertEquals("String List", associationXMLConverter.convertToDelimitedString(stringList));
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  void testConvertToDelimitedString4() {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  void testConvertToDelimitedString5() {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#convertToDelimitedString(List)}
   */
  @Test
  void testConvertToDelimitedString6() {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    ArrayList<String> stringList = new ArrayList<>();
    stringList.add("42");
    stringList.add("foo");

    // Act and Assert
    assertEquals("42,foo", associationXMLConverter.convertToDelimitedString(stringList));
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteFormProperties() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    AdhocSubProcess flowElement = new AdhocSubProcess();

    // Act and Assert
    assertTrue(associationXMLConverter.writeFormProperties(flowElement, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteFormProperties2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    UserTask flowElement = new UserTask();
    flowElement.setFormProperties(null);

    // Act and Assert
    assertFalse(associationXMLConverter.writeFormProperties(flowElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteFormProperties3() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    UserTask flowElement = new UserTask();
    flowElement.setFormProperties(new ArrayList<>());

    // Act and Assert
    assertFalse(associationXMLConverter.writeFormProperties(flowElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteFormProperties4() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeFormProperties(FlowElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteFormProperties5() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    StartEvent flowElement = new StartEvent();
    flowElement.setFormProperties(null);

    // Act and Assert
    assertFalse(associationXMLConverter.writeFormProperties(flowElement, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteListeners() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertTrue(associationXMLConverter.writeListeners(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteListeners2() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    AdhocSubProcess element = new AdhocSubProcess();

    // Act and Assert
    assertTrue(associationXMLConverter.writeListeners(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteListeners3() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertFalse(associationXMLConverter.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTimerDefinition() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTimerDefinition2() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTimerDefinition3() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTimerDefinition4() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTimerDefinition5() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTimerDefinition6() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTimerDefinition7() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTimerDefinition8() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTimerDefinition9() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTimerDefinition10() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTimerDefinition11() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTimerDefinition(Event, TimerEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTimerDefinition12() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition2() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition3() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition4() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition5() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition6() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition7() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition8() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition9() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition10() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition11() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition12() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition13() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeSignalDefinition(Event, SignalEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteSignalDefinition14() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCancelDefinition(Event, CancelEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCancelDefinition() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition2() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition3() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition4() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition5() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition6() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition7() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition8() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition9() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition10() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition11() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition12() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeCompensateDefinition(Event, CompensateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteCompensateDefinition13() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteMessageDefinition() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteMessageDefinition2() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteMessageDefinition3() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteMessageDefinition4() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteMessageDefinition5() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteMessageDefinition6() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteMessageDefinition7() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteMessageDefinition8() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteMessageDefinition9() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteMessageDefinition10() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteMessageDefinition11() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeMessageDefinition(Event, MessageEventDefinition, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteMessageDefinition12() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition2() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition3() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition4() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition5() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition6() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition7() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition8() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition9() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition10() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition11() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition12() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeErrorDefinition(Event, ErrorEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteErrorDefinition13() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTerminateDefinition() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTerminateDefinition2() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTerminateDefinition3() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTerminateDefinition4() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTerminateDefinition5() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTerminateDefinition6() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTerminateDefinition7() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTerminateDefinition8() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTerminateDefinition9() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTerminateDefinition10() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTerminateDefinition11() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeTerminateDefinition(Event, TerminateEventDefinition, XMLStreamWriter)}
   */
  @Test
  void testWriteTerminateDefinition12() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeDefaultAttribute(String, String, XMLStreamWriter)}
   */
  @Test
  void testWriteDefaultAttribute() throws Exception {
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
   * Method under test:
   * {@link BaseBpmnXMLConverter#writeQualifiedAttribute(String, String, XMLStreamWriter)}
   */
  @Test
  void testWriteQualifiedAttribute() throws Exception {
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
