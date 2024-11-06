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
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.MessageFlow;
import org.activiti.bpmn.model.Pool;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CollaborationExportDiffblueTest {
  /**
   * Test {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}.
   * <p>
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writePools(BpmnModel, XMLStreamWriter)")
  void testWritePools() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(new Pool());

    HashMap<String, MessageFlow> stringMessageFlowMap = new HashMap<>();
    stringMessageFlowMap.put("bpmn2", new MessageFlow("bpmn2", "bpmn2"));
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(stringMessageFlowMap);
    when(model.getPools()).thenReturn(poolList);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getPools();
  }

  /**
   * Test {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>When
   * {@link IndentingXMLStreamWriter#IndentingXMLStreamWriter(XMLStreamWriter)}
   * with writer is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writePools(BpmnModel, XMLStreamWriter); given ArrayList(); when IndentingXMLStreamWriter(XMLStreamWriter) with writer is 'null'")
  void testWritePools_givenArrayList_whenIndentingXMLStreamWriterWithWriterIsNull() throws Exception {
    // Arrange
    BpmnModel model = mock(BpmnModel.class);
    when(model.getPools()).thenReturn(new ArrayList<>());

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getPools();
  }

  /**
   * Test {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}
   * {@code http://www.omg.org/spec/BPMN/20100524/MODEL} is
   * {@link MessageFlow#MessageFlow()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writePools(BpmnModel, XMLStreamWriter); given HashMap() 'http://www.omg.org/spec/BPMN/20100524/MODEL' is MessageFlow()")
  void testWritePools_givenHashMapHttpWwwOmgOrgSpecBpmn20100524ModelIsMessageFlow() throws Exception {
    // Arrange
    Pool pool = mock(Pool.class);
    when(pool.getId()).thenReturn("42");
    when(pool.getName()).thenReturn("Name");
    when(pool.getProcessRef()).thenReturn("Process Ref");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    HashMap<String, MessageFlow> stringMessageFlowMap = new HashMap<>();
    stringMessageFlowMap.put("http://www.omg.org/spec/BPMN/20100524/MODEL", new MessageFlow());
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(stringMessageFlowMap);
    when(model.getPools()).thenReturn(poolList);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(pool).getId();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getPools();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
  }

  /**
   * Test {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link MessageFlow#MessageFlow()} MessageRef is {@code bpmn2}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writePools(BpmnModel, XMLStreamWriter); given MessageFlow() MessageRef is 'bpmn2'; then calls getId()")
  void testWritePools_givenMessageFlowMessageRefIsBpmn2_thenCallsGetId() throws Exception {
    // Arrange
    Pool pool = mock(Pool.class);
    when(pool.getId()).thenReturn("42");
    when(pool.getName()).thenReturn("Name");
    when(pool.getProcessRef()).thenReturn("Process Ref");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    MessageFlow messageFlow = new MessageFlow();
    messageFlow.setMessageRef("bpmn2");

    HashMap<String, MessageFlow> stringMessageFlowMap = new HashMap<>();
    stringMessageFlowMap.put("http://www.omg.org/spec/BPMN/20100524/MODEL", messageFlow);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(stringMessageFlowMap);
    when(model.getPools()).thenReturn(poolList);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(pool).getId();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getPools();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
  }

  /**
   * Test {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link MessageFlow#MessageFlow()} Name is {@code bpmn2}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writePools(BpmnModel, XMLStreamWriter); given MessageFlow() Name is 'bpmn2'; then calls getId()")
  void testWritePools_givenMessageFlowNameIsBpmn2_thenCallsGetId() throws Exception {
    // Arrange
    Pool pool = mock(Pool.class);
    when(pool.getId()).thenReturn("42");
    when(pool.getName()).thenReturn("Name");
    when(pool.getProcessRef()).thenReturn("Process Ref");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);

    MessageFlow messageFlow = new MessageFlow();
    messageFlow.setName("bpmn2");

    HashMap<String, MessageFlow> stringMessageFlowMap = new HashMap<>();
    stringMessageFlowMap.put("http://www.omg.org/spec/BPMN/20100524/MODEL", messageFlow);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(stringMessageFlowMap);
    when(model.getPools()).thenReturn(poolList);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(pool).getId();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getPools();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
  }

  /**
   * Test {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link Pool} {@link Pool#getName()} return empty string.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writePools(BpmnModel, XMLStreamWriter); given Pool getName() return empty string; then calls getId()")
  void testWritePools_givenPoolGetNameReturnEmptyString_thenCallsGetId() throws Exception {
    // Arrange
    Pool pool = mock(Pool.class);
    when(pool.getId()).thenReturn("42");
    when(pool.getName()).thenReturn("");
    when(pool.getProcessRef()).thenReturn("Process Ref");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(poolList);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(pool).getId();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getPools();
    verify(pool).getName();
    verify(pool, atLeast(1)).getProcessRef();
  }

  /**
   * Test {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}.
   * <ul>
   *   <li>Given {@link Pool} {@link Pool#getName()} return {@code Name}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writePools(BpmnModel, XMLStreamWriter); given Pool getName() return 'Name'; then calls getId()")
  void testWritePools_givenPoolGetNameReturnName_thenCallsGetId() throws Exception {
    // Arrange
    Pool pool = mock(Pool.class);
    when(pool.getId()).thenReturn("42");
    when(pool.getName()).thenReturn("Name");
    when(pool.getProcessRef()).thenReturn("Process Ref");

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(pool);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getMessageFlows()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(poolList);
    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing().when(writer).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(pool).getId();
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getPools();
    verify(pool, atLeast(1)).getName();
    verify(pool, atLeast(1)).getProcessRef();
  }
}
