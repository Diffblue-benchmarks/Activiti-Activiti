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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.Map;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.child.BaseChildElementParser;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CallActivity;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CallActivityXMLConverterDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link CallActivityXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>{@link CallActivityXMLConverter#getBpmnElementType()}
   *   <li>{@link CallActivityXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();
    ActivitiListener element = new ActivitiListener();
    BpmnModel model = new BpmnModel();

    // Act
    callActivityXMLConverter.writeAdditionalChildElements(element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType = callActivityXMLConverter.getBpmnElementType();

    // Assert that nothing has changed
    assertEquals("callActivity", callActivityXMLConverter.getXMLElementName());
    Class<CallActivity> expectedBpmnElementType = CallActivity.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link CallActivityXMLConverter.InParameterParser#InParameterParser(CallActivityXMLConverter)}
   *   <li>{@link CallActivityXMLConverter.InParameterParser#getElementName()}
   * </ul>
   */
  @Test
  void testInParameterParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("in", ((new CallActivityXMLConverter()).new InParameterParser()).getElementName());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link CallActivityXMLConverter.OutParameterParser#OutParameterParser(CallActivityXMLConverter)}
   *   <li>{@link CallActivityXMLConverter.OutParameterParser#getElementName()}
   * </ul>
   */
  @Test
  void testOutParameterParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("out", ((new CallActivityXMLConverter()).new OutParameterParser()).getElementName());
  }

  /**
   * Method under test:
   * {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteAdditionalAttributes() throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();

    CallActivity element = new CallActivity();
    element.setInheritBusinessKey(false);
    element.setInheritVariables(false);
    element.setBusinessKey("Business Key");
    element.setCalledElement(null);
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    callActivityXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(writer));

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("businessKey"),
        eq("Business Key"));
  }

  /**
   * Method under test:
   * {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteAdditionalAttributes2() throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();

    CallActivity element = new CallActivity();
    element.setInheritBusinessKey(true);
    element.setInheritVariables(false);
    element.setBusinessKey("Business Key");
    element.setCalledElement(null);
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    callActivityXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(writer));

    // Assert that nothing has changed
    verify(writer, atLeast(1)).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), Mockito.<String>any(),
        Mockito.<String>any());
  }

  /**
   * Method under test:
   * {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteAdditionalAttributes3() throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();

    CallActivity element = new CallActivity();
    element.setInheritBusinessKey(false);
    element.setInheritVariables(true);
    element.setBusinessKey("Business Key");
    element.setCalledElement(null);
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    callActivityXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(writer));

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("http://activiti.org/bpmn"), eq("inheritVariables"), eq("true"));
    verify(writer).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("businessKey"),
        eq("Business Key"));
  }

  /**
   * Method under test:
   * {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteAdditionalAttributes4() throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();

    CallActivity element = new CallActivity();
    element.setInheritBusinessKey(false);
    element.setInheritVariables(true);
    element.setBusinessKey("Business Key");
    element.setCalledElement("businessKey");
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    callActivityXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(writer));

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("calledElement"), eq("businessKey"));
    verify(writer).writeAttribute(eq("http://activiti.org/bpmn"), eq("inheritVariables"), eq("true"));
    verify(writer).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("businessKey"),
        eq("Business Key"));
  }

  /**
   * Method under test:
   * {@link CallActivityXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteExtensionChildElements() throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();
    CallActivity element = new CallActivity();

    // Act and Assert
    assertTrue(callActivityXMLConverter.writeExtensionChildElements(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test: default or parameterless constructor of
   * {@link CallActivityXMLConverter}
   */
  @Test
  void testNewCallActivityXMLConverter() {
    // Arrange and Act
    CallActivityXMLConverter actualCallActivityXMLConverter = new CallActivityXMLConverter();

    // Assert
    Map<String, BaseChildElementParser> stringBaseChildElementParserMap = actualCallActivityXMLConverter.childParserMap;
    assertEquals(2, stringBaseChildElementParserMap.size());
    BaseChildElementParser getResult = stringBaseChildElementParserMap.get("in");
    assertTrue(getResult instanceof CallActivityXMLConverter.InParameterParser);
    BaseChildElementParser getResult2 = stringBaseChildElementParserMap.get("out");
    assertTrue(getResult2 instanceof CallActivityXMLConverter.OutParameterParser);
    assertEquals("callActivity", actualCallActivityXMLConverter.getXMLElementName());
    assertEquals("in", getResult.getElementName());
    assertEquals("out", getResult2.getElementName());
    Class<CallActivity> expectedBpmnElementType = CallActivity.class;
    assertEquals(expectedBpmnElementType, actualCallActivityXMLConverter.getBpmnElementType());
  }
}
