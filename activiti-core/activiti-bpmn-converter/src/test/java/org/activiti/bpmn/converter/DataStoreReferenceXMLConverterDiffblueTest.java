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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DataStoreReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DataStoreReferenceXMLConverterDiffblueTest {
  /**
   * Test
   * {@link DataStoreReferenceXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code Data Store Ref}.</li>
   *   <li>Then calls
   * {@link DelegatingXMLStreamWriter#writeAttribute(String, String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataStoreReferenceXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given 'Data Store Ref'; then calls writeAttribute(String, String)")
  void testWriteAdditionalAttributes_givenDataStoreRef_thenCallsWriteAttribute() throws Exception {
    // Arrange
    DataStoreReferenceXMLConverter dataStoreReferenceXMLConverter = new DataStoreReferenceXMLConverter();

    DataStoreReference element = new DataStoreReference();
    element.setDataStoreRef("Data Store Ref");
    element.setItemSubjectRef(null);
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    dataStoreReferenceXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(writer));

    // Assert that nothing has changed
    verify(writer).writeAttribute(eq("dataStoreRef"), eq("Data Store Ref"));
  }

  /**
   * Test
   * {@link DataStoreReferenceXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code Hello from the Dreaming Spires}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataStoreReferenceXMLConverter#writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalAttributes(BaseElement, BpmnModel, XMLStreamWriter); given 'Hello from the Dreaming Spires'")
  void testWriteAdditionalAttributes_givenHelloFromTheDreamingSpires() throws Exception {
    // Arrange
    DataStoreReferenceXMLConverter dataStoreReferenceXMLConverter = new DataStoreReferenceXMLConverter();

    DataStoreReference element = new DataStoreReference();
    element.setDataStoreRef("Data Store Ref");
    element.setItemSubjectRef("Hello from the Dreaming Spires");
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());

    // Act
    dataStoreReferenceXMLConverter.writeAdditionalAttributes(element, model, new IndentingXMLStreamWriter(writer));

    // Assert that nothing has changed
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
  }

  /**
   * Test
   * {@link DataStoreReferenceXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@code Data State}.</li>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataStoreReferenceXMLConverter#writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeAdditionalChildElements(BaseElement, BpmnModel, XMLStreamWriter); given 'Data State'; then calls writeCharacters(String)")
  void testWriteAdditionalChildElements_givenDataState_thenCallsWriteCharacters() throws Exception {
    // Arrange
    DataStoreReferenceXMLConverter dataStoreReferenceXMLConverter = new DataStoreReferenceXMLConverter();

    DataStoreReference element = new DataStoreReference();
    element.setDataState("Data State");
    BpmnModel model = new BpmnModel();
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    dataStoreReferenceXMLConverter.writeAdditionalChildElements(element, model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer).writeCharacters(eq("Data State"));
    verify(writer).writeEndElement();
    verify(writer).writeStartElement(eq("dataState"));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link DataStoreReferenceXMLConverter}
   *   <li>{@link DataStoreReferenceXMLConverter#getBpmnElementType()}
   *   <li>{@link DataStoreReferenceXMLConverter#getXMLElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    DataStoreReferenceXMLConverter actualDataStoreReferenceXMLConverter = new DataStoreReferenceXMLConverter();
    Class<? extends BaseElement> actualBpmnElementType = actualDataStoreReferenceXMLConverter.getBpmnElementType();

    // Assert
    assertEquals("dataStoreReference", actualDataStoreReferenceXMLConverter.getXMLElementName());
    Class<DataStoreReference> expectedBpmnElementType = DataStoreReference.class;
    assertEquals(expectedBpmnElementType, actualBpmnElementType);
  }
}
