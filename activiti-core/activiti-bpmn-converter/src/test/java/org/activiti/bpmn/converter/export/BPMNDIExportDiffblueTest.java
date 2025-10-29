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
import java.util.List;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.MessageFlow;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BPMNDIExportDiffblueTest {
  /**
   * Method under test:
   * {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteBPMNDI() throws Exception {
    // Arrange
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationMap()).thenReturn(new HashMap<>());
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getMainProcess()).thenReturn(new Process());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmndi"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/DI"));
    verify(model).getFlowLocationMap();
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getPools();
  }

  /**
   * Method under test:
   * {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteBPMNDI2() throws Exception {
    // Arrange
    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getMainProcess()).thenReturn(new Process());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmndi"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/DI"));
    verify(model, atLeast(1)).getFlowElement(eq("bpmndi"));
    verify(model).getFlowLocationGraphicInfo(eq("bpmndi"));
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo(eq("bpmndi"));
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getPools();
  }

  /**
   * Method under test:
   * {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteBPMNDI3() throws Exception {
    // Arrange
    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getMainProcess()).thenReturn(new Process());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getFlowElement(eq("bpmndi"));
    verify(model).getFlowLocationGraphicInfo(eq("bpmndi"));
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo(eq("bpmndi"));
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getPools();
  }

  /**
   * Method under test:
   * {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteBPMNDI4() throws Exception {
    // Arrange
    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(model.getMessageFlow(Mockito.<String>any())).thenReturn(new MessageFlow("Source Ref", "Target Ref"));
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getMainProcess()).thenReturn(new Process());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmndi"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/DI"));
    verify(model).getArtifact(eq("bpmndi"));
    verify(model, atLeast(1)).getFlowElement(eq("bpmndi"));
    verify(model).getFlowLocationGraphicInfo(eq("bpmndi"));
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo(eq("bpmndi"));
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getMessageFlow(eq("bpmndi"));
    verify(model).getPools();
  }

  /**
   * Method under test:
   * {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteBPMNDI5() throws Exception {
    // Arrange
    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getName()).thenReturn("Name");

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(flowElement);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getMainProcess()).thenReturn(new Process());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getFlowElement(eq("bpmndi"));
    verify(model).getFlowLocationGraphicInfo(eq("bpmndi"));
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo(eq("bpmndi"));
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getPools();
    verify(flowElement).getName();
  }

  /**
   * Method under test:
   * {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteBPMNDI6() throws Exception {
    // Arrange
    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getName()).thenReturn("");

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(flowElement);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getMainProcess()).thenReturn(new Process());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(eq("bpmndi"), Mockito.<String>any(),
        eq("http://www.omg.org/spec/BPMN/20100524/DI"));
    verify(model, atLeast(1)).getFlowElement(eq("bpmndi"));
    verify(model).getFlowLocationGraphicInfo(eq("bpmndi"));
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo(eq("bpmndi"));
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getPools();
    verify(flowElement).getName();
  }

  /**
   * Method under test:
   * {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteBPMNDI7() throws Exception {
    // Arrange
    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("bpmndi", graphicInfo);
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();
    graphicInfo3.setElement(new ActivitiListener());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(flowElement);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getPools()).thenReturn(new ArrayList<>());
    when(model.getMainProcess()).thenReturn(new Process());
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getFlowElement(eq("bpmndi"));
    verify(model).getFlowLocationGraphicInfo(eq("bpmndi"));
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo(eq("bpmndi"));
    verify(model).getLabelGraphicInfo(eq("bpmndi"));
    verify(model).getLocationMap();
    verify(model).getMainProcess();
    verify(model).getPools();
    verify(flowElement).getName();
  }

  /**
   * Method under test:
   * {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  void testWriteBPMNDI8() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(new Pool());

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("bpmndi", graphicInfo);
    FlowElement flowElement = mock(FlowElement.class);
    when(flowElement.getName()).thenReturn("Name");

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();
    graphicInfo3.setElement(new ActivitiListener());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);
    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(flowElement);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getPools()).thenReturn(poolList);
    IndentingXMLStreamWriter xtw = mock(IndentingXMLStreamWriter.class);
    doNothing().when(xtw).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(xtw).writeEndElement();
    doNothing().when(xtw).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, xtw);

    // Assert that nothing has changed
    verify(xtw, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(xtw, atLeast(1)).writeEndElement();
    verify(xtw, atLeast(1)).writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getFlowElement(eq("bpmndi"));
    verify(model).getFlowLocationGraphicInfo(eq("bpmndi"));
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo(eq("bpmndi"));
    verify(model).getLabelGraphicInfo(eq("bpmndi"));
    verify(model).getLocationMap();
    verify(model).getPools();
    verify(flowElement).getName();
  }
}
