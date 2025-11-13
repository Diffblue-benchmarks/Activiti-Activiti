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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.SendTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SendTaskXMLConverterDiffblueTest {
  /**
   * Test {@link SendTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code type}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given 'type'; then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SendTaskXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_givenType_thenCallsWriteAttribute() throws Exception {
    // Arrange
    SendTaskXMLConverter sendTaskXMLConverter = new SendTaskXMLConverter();

    SendTask element = new SendTask();
    element.setType("type");
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());

    // Act
    sendTaskXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeAttribute("activiti", "http://activiti.org/bpmn", "type", "type");
  }

  /**
   * Test {@link SendTaskXMLConverter#parseOperationRef(String, BpmnModel)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseOperationRef(String, BpmnModel); when empty string; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SendTaskXMLConverter.parseOperationRef(String, BpmnModel)"})
  void testParseOperationRef_whenEmptyString_thenReturnNull() {
    // Arrange
    SendTaskXMLConverter sendTaskXMLConverter = new SendTaskXMLConverter();

    // Act and Assert
    assertNull(sendTaskXMLConverter.parseOperationRef("", new BpmnModel()));
  }

  /**
   * Test {@link SendTaskXMLConverter#parseOperationRef(String, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseOperationRef(String, BpmnModel); when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SendTaskXMLConverter.parseOperationRef(String, BpmnModel)"})
  void testParseOperationRef_whenNull_thenReturnNull() {
    // Arrange
    SendTaskXMLConverter sendTaskXMLConverter = new SendTaskXMLConverter();

    // Act and Assert
    assertNull(sendTaskXMLConverter.parseOperationRef(null, new BpmnModel()));
  }

  /**
   * Test {@link SendTaskXMLConverter#parseOperationRef(String, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@code Operation Ref}.
   *   <li>Then return {@code null:Operation Ref}.
   * </ul>
   *
   * <p>Method under test: {@link SendTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseOperationRef(String, BpmnModel); when 'Operation Ref'; then return 'null:Operation Ref'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String SendTaskXMLConverter.parseOperationRef(String, BpmnModel)"})
  void testParseOperationRef_whenOperationRef_thenReturnNullOperationRef() {
    // Arrange
    SendTaskXMLConverter sendTaskXMLConverter = new SendTaskXMLConverter();

    // Act and Assert
    assertEquals(
        "null:Operation Ref",
        sendTaskXMLConverter.parseOperationRef("Operation Ref", new BpmnModel()));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link SendTaskXMLConverter}
   *   <li>{@link SendTaskXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel,
   *       XMLStreamWriter)}
   *   <li>{@link SendTaskXMLConverter#getBpmnElementType()}
   *   <li>{@link SendTaskXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SendTaskXMLConverter.<init>()",
    "Class SendTaskXMLConverter.getBpmnElementType()",
    "String SendTaskXMLConverter.getXMLElementName()",
    "void SendTaskXMLConverter.writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testGettersAndSetters() throws Exception {
    // Arrange and Act
    SendTaskXMLConverter actualSendTaskXMLConverter = new SendTaskXMLConverter();
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
    actualSendTaskXMLConverter.writeAdditionalChildElements(
        element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType =
        actualSendTaskXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("sendTask", actualSendTaskXMLConverter.getXMLElementName());
    Class<SendTask> expectedBpmnElementType = SendTask.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
