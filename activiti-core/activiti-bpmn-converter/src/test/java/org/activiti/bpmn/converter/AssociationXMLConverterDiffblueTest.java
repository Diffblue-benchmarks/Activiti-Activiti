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
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.AssociationDirection;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AssociationXMLConverterDiffblueTest {
  /**
   * Test {@link AssociationXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given empty string.
   * </ul>
   *
   * <p>Method under test: {@link AssociationXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AssociationXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_givenEmptyString() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    Association element = mock(Association.class);
    when(element.getSourceRef()).thenReturn("");
    when(element.getTargetRef()).thenReturn("Target Ref");
    when(element.getAssociationDirection()).thenReturn(AssociationDirection.NONE);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(element).getAssociationDirection();
    verify(element).getSourceRef();
    verify(element).getTargetRef();
  }

  /**
   * Test {@link AssociationXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@code Source Ref}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given 'Source Ref'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AssociationXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_givenSourceRef() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    Association element = mock(Association.class);
    when(element.getSourceRef()).thenReturn("Source Ref");
    when(element.getTargetRef()).thenReturn("Target Ref");
    when(element.getAssociationDirection()).thenReturn(AssociationDirection.NONE);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(element).getAssociationDirection();
    verify(element).getSourceRef();
    verify(element).getTargetRef();
  }

  /**
   * Test {@link AssociationXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link Association} (default constructor) AssociationDirection is {@code NONE}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); when Association (default constructor) AssociationDirection is 'NONE'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AssociationXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_whenAssociationAssociationDirectionIsNone() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    Association element = new Association();
    element.setAssociationDirection(AssociationDirection.NONE);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeAttribute("associationDirection", "None");
  }

  /**
   * Test {@link AssociationXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link Association} {@link Association#getSourceRef()} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AssociationXMLConverter#writeAdditionalAttributes(BaseElement,
   * BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); when Association getSourceRef() return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AssociationXMLConverter.writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalAttributes_whenAssociationGetSourceRefReturnNull() throws Exception {
    // Arrange
    AssociationXMLConverter associationXMLConverter = new AssociationXMLConverter();

    Association element = mock(Association.class);
    when(element.getSourceRef()).thenReturn("null");
    when(element.getTargetRef()).thenReturn("Target Ref");
    when(element.getAssociationDirection()).thenReturn(AssociationDirection.NONE);
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    associationXMLConverter.writeAdditionalAttributes(
        element, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(element).getAssociationDirection();
    verify(element).getSourceRef();
    verify(element).getTargetRef();
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link AssociationXMLConverter}
   *   <li>{@link AssociationXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel,
   *       XMLStreamWriter)}
   *   <li>{@link AssociationXMLConverter#getBpmnElementType()}
   *   <li>{@link AssociationXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void AssociationXMLConverter.<init>()",
    "Class AssociationXMLConverter.getBpmnElementType()",
    "String AssociationXMLConverter.getXMLElementName()",
    "void AssociationXMLConverter.writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testGettersAndSetters() throws Exception {
    // Arrange and Act
    AssociationXMLConverter actualAssociationXMLConverter = new AssociationXMLConverter();
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
    actualAssociationXMLConverter.writeAdditionalChildElements(
        element, model, new IndentingXMLStreamWriter(null));
    Class<? extends BaseElement> actualBpmnElementType =
        actualAssociationXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("association", actualAssociationXMLConverter.getXMLElementName());
    Class<Association> expectedBpmnElementType = Association.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
