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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CallActivityXMLConverterDiffblueTest {
  /**
   * Test InParameterParser getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link CallActivityXMLConverter.InParameterParser#InParameterParser(CallActivityXMLConverter)}
   *   <li>{@link CallActivityXMLConverter.InParameterParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test InParameterParser getters and setters")
  void testInParameterParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("in", ((new CallActivityXMLConverter()).new InParameterParser()).getElementName());
  }

  /**
   * Test new {@link CallActivityXMLConverter} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link CallActivityXMLConverter}
   */
  @Test
  @DisplayName("Test new CallActivityXMLConverter (default constructor)")
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

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link CallActivityXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>{@link CallActivityXMLConverter#getBpmnElementType()}
   *   <li>{@link CallActivityXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
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
   * Test OutParameterParser getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link CallActivityXMLConverter.OutParameterParser#OutParameterParser(CallActivityXMLConverter)}
   *   <li>{@link CallActivityXMLConverter.OutParameterParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test OutParameterParser getters and setters")
  void testOutParameterParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("out", ((new CallActivityXMLConverter()).new OutParameterParser()).getElementName());
  }

  /**
   * Test
   * {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code businessKey}.</li>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given 'businessKey'; then calls writeAttribute(String, String)")
  void testWriteAdditionalAttributes_givenBusinessKey_thenCallsWriteAttribute() throws Exception {
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
   * Test
   * {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); then calls writeAttribute(String, String, String, String)")
  void testWriteAdditionalAttributes_thenCallsWriteAttribute() throws Exception {
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
   * Test
   * {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); then calls writeAttribute(String, String, String)")
  void testWriteAdditionalAttributes_thenCallsWriteAttribute2() throws Exception {
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
   * Test
   * {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link CallActivity} (default constructor) InheritBusinessKey is
   * {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); when CallActivity (default constructor) InheritBusinessKey is 'true'")
  void testWriteAdditionalAttributes_whenCallActivityInheritBusinessKeyIsTrue() throws Exception {
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
   * Test
   * {@link CallActivityXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link CallActivity} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CallActivityXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); when CallActivity (default constructor); then return 'true'")
  void testWriteExtensionChildElements_whenCallActivity_thenReturnTrue() throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();
    CallActivity element = new CallActivity();

    // Act and Assert
    assertTrue(callActivityXMLConverter.writeExtensionChildElements(element, true, new IndentingXMLStreamWriter(null)));
  }
}
