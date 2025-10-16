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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.Map;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.child.BaseChildElementParser;
import org.activiti.bpmn.converter.child.TextAnnotationTextParser;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.TextAnnotation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TextAnnotationXMLConverterDiffblueTest {
  /**
   * Test new {@link TextAnnotationXMLConverter} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * TextAnnotationXMLConverter}
   */
  @Test
  @DisplayName("Test new TextAnnotationXMLConverter (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void TextAnnotationXMLConverter.<init>()"})
  void testNewTextAnnotationXMLConverter() {
    // Arrange and Act
    TextAnnotationXMLConverter actualTextAnnotationXMLConverter = new TextAnnotationXMLConverter();

    // Assert
    Map<String, BaseChildElementParser> stringBaseChildElementParserMap =
        actualTextAnnotationXMLConverter.childParserMap;
    assertEquals(1, stringBaseChildElementParserMap.size());
    BaseChildElementParser getResult = stringBaseChildElementParserMap.get("text");
    assertTrue(getResult instanceof TextAnnotationTextParser);
    assertEquals("text", getResult.getElementName());
    assertEquals("textAnnotation", actualTextAnnotationXMLConverter.getXMLElementName());
    Class<TextAnnotation> expectedBpmnElementType = TextAnnotation.class;
    assertEquals(expectedBpmnElementType, actualTextAnnotationXMLConverter.getBpmnElementType());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link TextAnnotationXMLConverter#getBpmnElementType()}
   *   <li>{@link TextAnnotationXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Class TextAnnotationXMLConverter.getBpmnElementType()",
    "String TextAnnotationXMLConverter.getXMLElementName()"
  })
  void testGettersAndSetters() {
    // Arrange
    TextAnnotationXMLConverter textAnnotationXMLConverter = new TextAnnotationXMLConverter();

    // Act
    Class<? extends BaseElement> actualBpmnElementType =
        textAnnotationXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("textAnnotation", textAnnotationXMLConverter.getXMLElementName());
    Class<TextAnnotation> expectedBpmnElementType = TextAnnotation.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }

  /**
   * Test {@link TextAnnotationXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel,
   * XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * TextAnnotationXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter); then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void TextAnnotationXMLConverter.writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)"
  })
  void testWriteAdditionalChildElements_thenCallsWriteCharacters() throws Exception {
    // Arrange
    TextAnnotationXMLConverter textAnnotationXMLConverter = new TextAnnotationXMLConverter();

    TextAnnotation element = new TextAnnotation();
    element.setText("not empty");
    BpmnModel model = new BpmnModel();

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    textAnnotationXMLConverter.writeAdditionalChildElements(
        element, model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer).writeCharacters("not empty");
    verify(writer).writeEndElement();
    verify(writer)
        .writeStartElement("bpmn2", "text", "http://www.omg.org/spec/BPMN/20100524/MODEL");
  }
}
