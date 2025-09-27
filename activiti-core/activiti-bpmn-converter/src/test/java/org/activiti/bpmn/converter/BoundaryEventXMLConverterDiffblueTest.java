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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.Activity;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ErrorEventDefinition;
import org.activiti.bpmn.model.EventDefinition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class BoundaryEventXMLConverterDiffblueTest {
  /**
   * Test {@link BoundaryEventXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link Activity} {@link Activity#getId()} return empty string.
   *   <li>Then calls {@link Activity#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link BoundaryEventXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given Activity getId() return empty string; then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BoundaryEventXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_givenActivityGetIdReturnEmptyString_thenCallsGetId()
      throws Exception {
    // Arrange
    BoundaryEventXMLConverter boundaryEventXMLConverter = new BoundaryEventXMLConverter();

    Activity attachedToRef = mock(Activity.class);
    when(attachedToRef.getId()).thenReturn("");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new ErrorEventDefinition());

    BoundaryEvent element = new BoundaryEvent();
    element.setAttachedToRef(attachedToRef);
    element.setEventDefinitions(eventDefinitions);
    BpmnModel model = new BpmnModel();

    // Act
    boundaryEventXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(null));

    // Assert
    verify(attachedToRef).getId();
  }

  /**
   * Test {@link BoundaryEventXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link Activity} {@link Activity#getId()} return {@code null}.
   *   <li>Then calls {@link Activity#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link BoundaryEventXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given Activity getId() return 'null'; then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BoundaryEventXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_givenActivityGetIdReturnNull_thenCallsGetId()
      throws Exception {
    // Arrange
    BoundaryEventXMLConverter boundaryEventXMLConverter = new BoundaryEventXMLConverter();

    Activity attachedToRef = mock(Activity.class);
    when(attachedToRef.getId()).thenReturn("null");

    ArrayList<EventDefinition> eventDefinitions = new ArrayList<>();
    eventDefinitions.add(new ErrorEventDefinition());

    BoundaryEvent element = new BoundaryEvent();
    element.setAttachedToRef(attachedToRef);
    element.setEventDefinitions(eventDefinitions);
    BpmnModel model = new BpmnModel();

    // Act
    boundaryEventXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(null));

    // Assert
    verify(attachedToRef).getId();
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link BoundaryEventXMLConverter}
   *   <li>{@link BoundaryEventXMLConverter#getBpmnElementType()}
   *   <li>{@link BoundaryEventXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void BoundaryEventXMLConverter.<init>()",
    "Class BoundaryEventXMLConverter.getBpmnElementType()",
    "java.lang.String BoundaryEventXMLConverter.getXMLElementName()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    BoundaryEventXMLConverter actualBoundaryEventXMLConverter = new BoundaryEventXMLConverter();
    Class<? extends BaseElement> actualBpmnElementType =
        actualBoundaryEventXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("boundaryEvent", actualBoundaryEventXMLConverter.getXMLElementName());
    Class<BoundaryEvent> expectedBpmnElementType = BoundaryEvent.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
