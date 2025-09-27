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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.CallActivityXMLConverter.InParameterParser;
import org.activiti.bpmn.converter.CallActivityXMLConverter.OutParameterParser;
import org.activiti.bpmn.converter.child.BaseChildElementParser;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CallActivityXMLConverterDiffblueTest {
  /**
   * Test InParameterParser getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link InParameterParser#InParameterParser(CallActivityXMLConverter)}
   *   <li>{@link InParameterParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test InParameterParser getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void InParameterParser.<init>(CallActivityXMLConverter)",
    "String InParameterParser.getElementName()"
  })
  void testInParameterParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("in", new CallActivityXMLConverter().new InParameterParser().getElementName());
  }

  /**
   * Test new {@link CallActivityXMLConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link CallActivityXMLConverter}
   */
  @Test
  @DisplayName("Test new CallActivityXMLConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void CallActivityXMLConverter.<init>()"})
  void testNewCallActivityXMLConverter() {
    // Arrange and Act
    CallActivityXMLConverter actualCallActivityXMLConverter = new CallActivityXMLConverter();

    // Assert
    Map<String, BaseChildElementParser> stringBaseChildElementParserMap =
        actualCallActivityXMLConverter.childParserMap;
    assertEquals(2, stringBaseChildElementParserMap.size());
    BaseChildElementParser getResult = stringBaseChildElementParserMap.get("in");
    assertTrue(getResult instanceof InParameterParser);
    BaseChildElementParser getResult2 = stringBaseChildElementParserMap.get("out");
    assertTrue(getResult2 instanceof OutParameterParser);
    assertEquals("callActivity", actualCallActivityXMLConverter.getXMLElementName());
    assertEquals("in", getResult.getElementName());
    assertEquals("out", getResult2.getElementName());
    Class<CallActivity> expectedBpmnElementType = CallActivity.class;
    assertEquals(expectedBpmnElementType, actualCallActivityXMLConverter.getBpmnElementType());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link CallActivityXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel,
   *       XMLStreamWriter)}
   *   <li>{@link CallActivityXMLConverter#getBpmnElementType()}
   *   <li>{@link CallActivityXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Class CallActivityXMLConverter.getBpmnElementType()",
    "String CallActivityXMLConverter.getXMLElementName()",
    "void CallActivityXMLConverter.writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testGettersAndSetters() throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    Message element =
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build();
    BpmnModel model = new BpmnModel();

    // Act
    callActivityXMLConverter.writeAdditionalChildElements(
        element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType =
        callActivityXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("callActivity", callActivityXMLConverter.getXMLElementName());
    Class<CallActivity> expectedBpmnElementType = CallActivity.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }

  /**
   * Test OutParameterParser getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link OutParameterParser#OutParameterParser(CallActivityXMLConverter)}
   *   <li>{@link OutParameterParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test OutParameterParser getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void OutParameterParser.<init>(CallActivityXMLConverter)",
    "String OutParameterParser.getElementName()"
  })
  void testOutParameterParserGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("out", new CallActivityXMLConverter().new OutParameterParser().getElementName());
  }

  /**
   * Test {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code calledElement}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given 'calledElement'; then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CallActivityXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_givenCalledElement_thenCallsWriteAttribute() throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();

    CallActivity element = new CallActivity();
    element.setCalledElement("http://activiti.org/bpmn");
    element.setBusinessKey("calledElement");
    element.setInheritBusinessKey(false);
    element.setInheritVariables(true);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    callActivityXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeAttribute("calledElement", "http://activiti.org/bpmn");
    verify(writer).writeAttribute("http://activiti.org/bpmn", "inheritVariables", "true");
    verify(writer)
        .writeAttribute("activiti", "http://activiti.org/bpmn", "businessKey", "calledElement");
  }

  /**
   * Test {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code calledElement}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given 'calledElement'; then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CallActivityXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_givenCalledElement_thenCallsWriteAttribute2()
      throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();

    CallActivity element = new CallActivity();
    element.setCalledElement("http://activiti.org/bpmn");
    element.setBusinessKey("calledElement");
    element.setInheritBusinessKey(true);
    element.setInheritVariables(true);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    callActivityXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeAttribute("calledElement", "http://activiti.org/bpmn");
    verify(writer).writeAttribute("http://activiti.org/bpmn", "inheritVariables", "true");
    verify(writer, atLeast(1))
        .writeAttribute(
            eq("activiti"),
            eq("http://activiti.org/bpmn"),
            Mockito.<String>any(),
            Mockito.<String>any());
  }

  /**
   * Test {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code http://activiti.org/bpmn}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given 'http://activiti.org/bpmn'; then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CallActivityXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_givenHttpActivitiOrgBpmn_thenCallsWriteAttribute()
      throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();

    CallActivity element = new CallActivity();
    element.setCalledElement("http://activiti.org/bpmn");
    element.setBusinessKey("");
    element.setInheritBusinessKey(false);
    element.setInheritVariables(true);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    callActivityXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeAttribute("calledElement", "http://activiti.org/bpmn");
    verify(writer).writeAttribute("http://activiti.org/bpmn", "inheritVariables", "true");
  }

  /**
   * Test {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivityXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); then calls writeAttribute(String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void CallActivityXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_thenCallsWriteAttribute() throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();

    CallActivity element = new CallActivity();
    element.setCalledElement("");
    element.setBusinessKey("");
    element.setInheritBusinessKey(false);
    element.setInheritVariables(true);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    callActivityXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeAttribute("http://activiti.org/bpmn", "inheritVariables", "true");
  }

  /**
   * Test {@link CallActivityXMLConverter#writeExtensionChildElements(BaseElement, boolean,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link CallActivity} (default constructor).
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link CallActivityXMLConverter#writeExtensionChildElements(BaseElement,
   * boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter); when CallActivity (default constructor); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean CallActivityXMLConverter.writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteExtensionChildElements_whenCallActivity_thenReturnTrue() throws Exception {
    // Arrange
    CallActivityXMLConverter callActivityXMLConverter = new CallActivityXMLConverter();
    CallActivity element = new CallActivity();

    // Act and Assert
    assertTrue(
        callActivityXMLConverter.writeExtensionChildElements(
            element, true, new IndentingXMLStreamWriter(null)));
  }
}
