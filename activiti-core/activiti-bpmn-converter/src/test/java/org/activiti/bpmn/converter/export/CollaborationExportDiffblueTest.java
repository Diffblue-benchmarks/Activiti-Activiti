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
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.MessageFlow;
import org.activiti.bpmn.model.Pool;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CollaborationExportDiffblueTest {
  /**
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWritePools() throws Exception {
    // Arrange
    BpmnModel model = mock(BpmnModel.class);
    when(model.getPools()).thenReturn(new ArrayList<>());

    // Act
    CollaborationExport.writePools(model, new IndentingXMLStreamWriter(null));

    // Assert that nothing has changed
    verify(model).getPools();
  }

  /**
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWritePools2() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(new Pool());
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
    verify(writer, atLeast(1)).writeAttribute(eq("id"), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1)).writeStartElement(eq("bpmn2"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/MODEL"));
    verify(model).getMessageFlows();
    verify(model, atLeast(1)).getPools();
  }

  /**
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWritePools3() throws Exception {
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
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWritePools4() throws Exception {
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

  /**
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWritePools5() throws Exception {
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
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWritePools6() throws Exception {
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
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWritePools7() throws Exception {
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
   * Method under test:
   * {@link CollaborationExport#writePools(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWritePools8() throws Exception {
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
}
