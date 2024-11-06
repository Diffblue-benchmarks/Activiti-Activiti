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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.SendTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SendTaskXMLConverterDiffblueTest {
  /**
   * Test
   * {@link SendTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code Type}.</li>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String, String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given 'Type'; then calls writeAttribute(String, String, String, String)")
  void testWriteAdditionalAttributes_givenType_thenCallsWriteAttribute() throws Exception {
    // Arrange
    SendTaskXMLConverter sendTaskXMLConverter = new SendTaskXMLConverter();

    SendTask element = new SendTask();
    element.setType("Type");
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer)
        .writeAttribute(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    sendTaskXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(writer));

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("activiti"), eq("http://activiti.org/bpmn"), eq("type"), eq("Type"));
  }

  /**
   * Test {@link SendTaskXMLConverter#parseOperationRef(String, BpmnModel)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseOperationRef(String, BpmnModel); when empty string; then return 'null'")
  void testParseOperationRef_whenEmptyString_thenReturnNull() {
    // Arrange
    SendTaskXMLConverter sendTaskXMLConverter = new SendTaskXMLConverter();

    // Act and Assert
    assertNull(sendTaskXMLConverter.parseOperationRef("", new BpmnModel()));
  }

  /**
   * Test {@link SendTaskXMLConverter#parseOperationRef(String, BpmnModel)}.
   * <ul>
   *   <li>When {@code Operation Ref}.</li>
   *   <li>Then return {@code null:Operation Ref}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link SendTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseOperationRef(String, BpmnModel); when 'Operation Ref'; then return 'null:Operation Ref'")
  void testParseOperationRef_whenOperationRef_thenReturnNullOperationRef() {
    // Arrange
    SendTaskXMLConverter sendTaskXMLConverter = new SendTaskXMLConverter();

    // Act and Assert
    assertEquals("null:Operation Ref", sendTaskXMLConverter.parseOperationRef("Operation Ref", new BpmnModel()));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link SendTaskXMLConverter}
   *   <li>
   * {@link SendTaskXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   *   <li>{@link SendTaskXMLConverter#getBpmnElementType()}
   *   <li>{@link SendTaskXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() throws Exception {
    // Arrange and Act
    SendTaskXMLConverter actualSendTaskXMLConverter = new SendTaskXMLConverter();
    ActivitiListener element = new ActivitiListener();
    BpmnModel model = new BpmnModel();
    actualSendTaskXMLConverter.writeAdditionalChildElements(element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType = actualSendTaskXMLConverter.getBpmnElementType();

    // Assert that nothing has changed
    assertEquals("sendTask", actualSendTaskXMLConverter.getXMLElementName());
    Class<SendTask> expectedBpmnElementType = SendTask.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
