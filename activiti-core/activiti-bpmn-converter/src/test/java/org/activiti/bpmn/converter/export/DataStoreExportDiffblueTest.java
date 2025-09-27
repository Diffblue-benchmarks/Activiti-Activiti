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

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.DataStore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DataStoreExportDiffblueTest {
  /**
   * Test {@link DataStoreExport#writeDataStores(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link DataStore} {@link DataStore#getDataState()} return empty string.
   *   <li>Then calls {@link DataStore#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link DataStoreExport#writeDataStores(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeDataStores(BpmnModel, XMLStreamWriter); given DataStore getDataState() return empty string; then calls getId()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DataStoreExport.writeDataStores(BpmnModel, XMLStreamWriter)"})
  void testWriteDataStores_givenDataStoreGetDataStateReturnEmptyString_thenCallsGetId()
      throws Exception {
    // Arrange
    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getDataState()).thenReturn("");
    when(dataStore.getItemSubjectRef()).thenReturn("");
    when(dataStore.getId()).thenReturn("42");
    when(dataStore.getName()).thenReturn("Name");

    BpmnModel model = new BpmnModel();
    model.addDataStore("42", dataStore);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    DataStoreExport.writeDataStores(model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("dataStore");
    verify(dataStore).getId();
    verify(dataStore).getDataState();
    verify(dataStore).getItemSubjectRef();
    verify(dataStore).getName();
  }

  /**
   * Test {@link DataStoreExport#writeDataStores(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link DataStore} (default constructor).
   *   <li>When {@link BpmnModel} (default constructor) addDataStore {@code 42} and {@link
   *       DataStore} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DataStoreExport#writeDataStores(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeDataStores(BpmnModel, XMLStreamWriter); given DataStore (default constructor); when BpmnModel (default constructor) addDataStore '42' and DataStore (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DataStoreExport.writeDataStores(BpmnModel, XMLStreamWriter)"})
  void testWriteDataStores_givenDataStore_whenBpmnModelAddDataStore42AndDataStore()
      throws Exception {
    // Arrange
    BpmnModel model = new BpmnModel();
    model.addDataStore("42", new DataStore());

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    DataStoreExport.writeDataStores(model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), isNull());
    verify(writer).writeEndElement();
    verify(writer).writeStartElement("dataStore");
  }

  /**
   * Test {@link DataStoreExport#writeDataStores(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeCharacters(String)}.
   * </ul>
   *
   * <p>Method under test: {@link DataStoreExport#writeDataStores(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeDataStores(BpmnModel, XMLStreamWriter); then calls writeCharacters(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void DataStoreExport.writeDataStores(BpmnModel, XMLStreamWriter)"})
  void testWriteDataStores_thenCallsWriteCharacters() throws Exception {
    // Arrange
    DataStore dataStore = mock(DataStore.class);
    when(dataStore.getDataState()).thenReturn("not empty");
    when(dataStore.getItemSubjectRef()).thenReturn("not empty");
    when(dataStore.getId()).thenReturn("42");
    when(dataStore.getName()).thenReturn("Name");

    BpmnModel model = new BpmnModel();
    model.addDataStore("42", dataStore);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any());
    IndentingXMLStreamWriter writer2 = new IndentingXMLStreamWriter(writer);

    // Act
    DataStoreExport.writeDataStores(model, new IndentingXMLStreamWriter(writer2));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(Mockito.<String>any());
    verify(dataStore).getId();
    verify(dataStore, atLeast(1)).getDataState();
    verify(dataStore, atLeast(1)).getItemSubjectRef();
    verify(dataStore).getName();
  }
}
