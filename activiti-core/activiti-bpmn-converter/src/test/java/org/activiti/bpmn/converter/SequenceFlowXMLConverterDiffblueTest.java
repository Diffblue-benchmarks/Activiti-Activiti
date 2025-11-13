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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.SequenceFlow;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class SequenceFlowXMLConverterDiffblueTest {
  /**
   * Test {@link SequenceFlowXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_thenCallsWriteAttribute() throws Exception {
    // Arrange
    SequenceFlowXMLConverter sequenceFlowXMLConverter = new SequenceFlowXMLConverter();

    SequenceFlow element = new SequenceFlow("sourceRef", "Target Ref");
    element.setSkipExpression("not empty");
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    sequenceFlowXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
  }

  /**
   * Test {@link SequenceFlowXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String, String,
   *       String)}.
   * </ul>
   *
   * <p>Method under test: {@link SequenceFlowXMLConverter#writeAdditionalChildElements(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter); then calls writeAttribute(String, String, String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowXMLConverter.writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalChildElements_thenCallsWriteAttribute() throws Exception {
    // Arrange
    SequenceFlowXMLConverter sequenceFlowXMLConverter = new SequenceFlowXMLConverter();

    SequenceFlow element = new SequenceFlow("Source Ref", "Target Ref");
    element.setConditionExpression("not empty");
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing()
        .when(writer)
        .writeAttribute(
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any(),
            Mockito.<String>any());
    doNothing().when(writer).writeCData(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    sequenceFlowXMLConverter.writeAdditionalChildElements(
        element, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer)
        .writeAttribute(
            "xsi", "http://www.w3.org/2001/XMLSchema-instance", "type", "tFormalExpression");
    verify(writer).writeCData("not empty");
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("conditionExpression");
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link SequenceFlowXMLConverter}
   *   <li>{@link SequenceFlowXMLConverter#getBpmnElementType()}
   *   <li>{@link SequenceFlowXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void SequenceFlowXMLConverter.<init>()",
    "Class SequenceFlowXMLConverter.getBpmnElementType()",
    "String SequenceFlowXMLConverter.getXMLElementName()"
  })
  void testGettersAndSetters() {
    // Arrange and Act
    SequenceFlowXMLConverter actualSequenceFlowXMLConverter = new SequenceFlowXMLConverter();
    Class<? extends BaseElement> actualBpmnElementType =
        actualSequenceFlowXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("sequenceFlow", actualSequenceFlowXMLConverter.getXMLElementName());
    Class<SequenceFlow> expectedBpmnElementType = SequenceFlow.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
