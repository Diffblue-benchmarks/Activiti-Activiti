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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.activiti.bpmn.model.StartEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class StartEventXMLConverterDiffblueTest {
  /**
   * Test
   * {@link StartEventXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link StartEventXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); then calls writeAttribute(String, String)")
  void testWriteAdditionalAttributes_thenCallsWriteAttribute() throws Exception {
    // Arrange
    StartEventXMLConverter startEventXMLConverter = new StartEventXMLConverter();

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new CancelEventDefinition());
    eventDefinitions.add(new CancelEventDefinition());

    StartEvent element = new StartEvent();
    element.setEventDefinitions(eventDefinitions);
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    startEventXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(writer));

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("isInterrupting"), eq("false"));
  }

  /**
   * Test
   * {@link StartEventXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link StartEventXMLConverter#writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeExtensionChildElements(BaseElement, boolean, XMLStreamWriter)")
  void testWriteExtensionChildElements() throws Exception {
    // Arrange
    StartEventXMLConverter startEventXMLConverter = new StartEventXMLConverter();
    StartEvent element = new StartEvent();

    // Act and Assert
    assertTrue(startEventXMLConverter.writeExtensionChildElements(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link StartEventXMLConverter}
   *   <li>{@link StartEventXMLConverter#getBpmnElementType()}
   *   <li>{@link StartEventXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    StartEventXMLConverter actualStartEventXMLConverter = new StartEventXMLConverter();
    Class<? extends BaseElement> actualBpmnElementType = actualStartEventXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("startEvent", actualStartEventXMLConverter.getXMLElementName());
    Class<StartEvent> expectedBpmnElementType = StartEvent.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
