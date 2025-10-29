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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SendTaskXMLConverterDiffblueTest {
  /**
   * Method under test:
   * {@link SendTaskXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteAdditionalAttributes() throws Exception {
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

  /**
   * Method under test:
   * {@link SendTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  void testParseOperationRef() {
    // Arrange
    SendTaskXMLConverter sendTaskXMLConverter = new SendTaskXMLConverter();

    // Act and Assert
    assertEquals("null:Operation Ref", sendTaskXMLConverter.parseOperationRef("Operation Ref", new BpmnModel()));
  }

  /**
   * Method under test:
   * {@link SendTaskXMLConverter#parseOperationRef(String, BpmnModel)}
   */
  @Test
  void testParseOperationRef2() {
    // Arrange
    SendTaskXMLConverter sendTaskXMLConverter = new SendTaskXMLConverter();

    // Act and Assert
    assertNull(sendTaskXMLConverter.parseOperationRef("", new BpmnModel()));
  }
}
