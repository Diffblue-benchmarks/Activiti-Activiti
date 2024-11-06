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
package org.activiti.bpmn.converter.export;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DataStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DataStoreExportDiffblueTest {
  /**
   * Test {@link DataStoreExport#writeDataStores(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link DataStore} {@link DataStore#getDataState()} return empty
   * string.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataStoreExport#writeDataStores(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeDataStores(BpmnModel, XMLStreamWriter); given DataStore getDataState() return empty string; then calls getId()")
  void testWriteDataStores_givenDataStoreGetDataStateReturnEmptyString_thenCallsGetId() throws Exception {
    // Arrange
    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getId()).thenReturn("42");
    when(dataStore.getDataState()).thenReturn("");
    when(dataStore.getItemSubjectRef()).thenReturn("Hello from the Dreaming Spires");
    when(dataStore.getName()).thenReturn("Name");

    HashMap<String, DataStore> stringDataStoreMap = new HashMap<>();
    stringDataStoreMap.putIfAbsent("foo", dataStore);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getDataStores()).thenReturn(stringDataStoreMap);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    DataStoreExport.writeDataStores(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeEndElement();
    verify(writer).writeStartElement(eq("dataStore"));
    verify(dataStore).getId();
    verify(model).getDataStores();
    verify(dataStore).getDataState();
    verify(dataStore, atLeast(1)).getItemSubjectRef();
    verify(dataStore).getName();
  }

  /**
   * Test {@link DataStoreExport#writeDataStores(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.</li>
   *   <li>Then calls {@link BpmnModel#getDataStores()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataStoreExport#writeDataStores(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeDataStores(BpmnModel, XMLStreamWriter); given HashMap(); then calls getDataStores()")
  void testWriteDataStores_givenHashMap_thenCallsGetDataStores() throws Exception {
    // Arrange
    BpmnModel model = mock(BpmnModel.class);
    when(model.getDataStores()).thenReturn(new HashMap<>());

    // Act
    DataStoreExport.writeDataStores(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getDataStores();
  }

  /**
   * Test {@link DataStoreExport#writeDataStores(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DataStoreExport#writeDataStores(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeDataStores(BpmnModel, XMLStreamWriter); then calls writeCharacters(String)")
  void testWriteDataStores_thenCallsWriteCharacters() throws Exception {
    // Arrange
    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getId()).thenReturn("42");
    when(dataStore.getDataState()).thenReturn("Data State");
    when(dataStore.getItemSubjectRef()).thenReturn("Hello from the Dreaming Spires");
    when(dataStore.getName()).thenReturn("Name");

    HashMap<String, DataStore> stringDataStoreMap = new HashMap<>();
    stringDataStoreMap.putIfAbsent("foo", dataStore);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getDataStores()).thenReturn(stringDataStoreMap);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());

    // Act
    DataStoreExport.writeDataStores(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(dataStore).getId();
    verify(model).getDataStores();
    verify(dataStore, atLeast(1)).getDataState();
    verify(dataStore, atLeast(1)).getItemSubjectRef();
    verify(dataStore).getName();
  }
}
