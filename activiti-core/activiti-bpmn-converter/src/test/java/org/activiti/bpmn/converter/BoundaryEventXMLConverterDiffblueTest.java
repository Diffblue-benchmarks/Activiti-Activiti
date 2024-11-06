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
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoundaryEventXMLConverterDiffblueTest {
  /**
   * Test
   * {@link BoundaryEventXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls {@link BoundaryEvent#getAttachedToRef()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BoundaryEventXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); then calls getAttachedToRef()")
  void testWriteAdditionalAttributes_thenCallsGetAttachedToRef() throws Exception {
    // Arrange
    BoundaryEventXMLConverter boundaryEventXMLConverter = new BoundaryEventXMLConverter();
    BoundaryEvent element = mock(BoundaryEvent.class);
    when(element.getEventDefinitions()).thenReturn(new ArrayList<>());
    when(element.getAttachedToRef()).thenReturn(new AdhocSubProcess());
    BpmnModel model = new BpmnModel();

    // Act
    boundaryEventXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(element, atLeast(1)).getAttachedToRef();
    verify(element).getEventDefinitions();
  }

  /**
   * Test
   * {@link BoundaryEventXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>Then calls {@link Event#getEventDefinitions()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link BoundaryEventXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter); given ArrayList(); then calls getEventDefinitions()")
  void testWriteAdditionalChildElements_givenArrayList_thenCallsGetEventDefinitions() throws Exception {
    // Arrange
    BoundaryEventXMLConverter boundaryEventXMLConverter = new BoundaryEventXMLConverter();
    BoundaryEvent element = mock(BoundaryEvent.class);
    when(element.getEventDefinitions()).thenReturn(new ArrayList<>());
    BpmnModel model = new BpmnModel();

    // Act
    boundaryEventXMLConverter.writeAdditionalChildElements(element, model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(element).getEventDefinitions();
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BoundaryEventXMLConverter}
   *   <li>{@link BoundaryEventXMLConverter#getBpmnElementType()}
   *   <li>{@link BoundaryEventXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    BoundaryEventXMLConverter actualBoundaryEventXMLConverter = new BoundaryEventXMLConverter();
    Class<? extends BaseElement> actualBpmnElementType = actualBoundaryEventXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("boundaryEvent", actualBoundaryEventXMLConverter.getXMLElementName());
    Class<BoundaryEvent> expectedBpmnElementType = BoundaryEvent.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
