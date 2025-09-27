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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Lane;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.MessageFlow;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.SubProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BPMNDIExportDiffblueTest {
  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeBPMNDI(BpmnModel, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    SequenceFlow sequenceFlow = new SequenceFlow("bpmndi", "bpmndi");
    sequenceFlow.setName("not empty");

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(sequenceFlow);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationGraphicInfo("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("bpmndi");
    verify(model).getLocationMap();
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeBPMNDI(BpmnModel, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI2() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    SequenceFlow sequenceFlow = new SequenceFlow("bpmndi", "bpmndi");
    sequenceFlow.setName("");

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(sequenceFlow);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationGraphicInfo("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("bpmndi");
    verify(model).getLocationMap();
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeBPMNDI(BpmnModel, XMLStreamWriter)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI3() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("bpmndi", graphicInfo);

    MessageFlow messageFlow = new MessageFlow("bpmndi", "bpmndi");
    messageFlow.setName("not empty");

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getLane(Mockito.<String>any())).thenReturn(null);
    when(model.getPool(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(null);
    when(model.getArtifact(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(model.getMessageFlow(Mockito.<String>any())).thenReturn(messageFlow);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getArtifact("bpmndi");
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationGraphicInfo("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("bpmndi");
    verify(model).getLane("bpmndi");
    verify(model).getLocationMap();
    verify(model, atLeast(1)).getMessageFlow("bpmndi");
    verify(model).getPool("bpmndi");
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   *   <li>When {@link BpmnModel} {@link BpmnModel#getLabelGraphicInfo(String)} return {@link
   *       GraphicInfo} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given ArrayList(); when BpmnModel getLabelGraphicInfo(String) return GraphicInfo (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenArrayList_whenBpmnModelGetLabelGraphicInfoReturnGraphicInfo()
      throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
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
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new SubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmndi"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/DI"));
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationGraphicInfo("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("bpmndi");
    verify(model).getLocationMap();
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link Association} (default constructor).
   *   <li>When {@link BpmnModel} {@link BpmnModel#getArtifact(String)} return {@link Association}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given Association (default constructor); when BpmnModel getArtifact(String) return Association (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenAssociation_whenBpmnModelGetArtifactReturnAssociation()
      throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("bpmndi", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);
    graphicInfo3.setExpanded(null);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(null);
    when(model.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(model.getMessageFlow(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getArtifact("bpmndi");
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationGraphicInfo("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("bpmndi");
    verify(model).getLabelGraphicInfo("bpmndi");
    verify(model).getLocationMap();
    verify(model).getMessageFlow("bpmndi");
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Expanded is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given GraphicInfo (default constructor) Expanded is 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenGraphicInfoExpandedIsNull() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("bpmndi", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);
    graphicInfo3.setExpanded(null);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new SubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationGraphicInfo("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("bpmndi");
    verify(model).getLabelGraphicInfo("bpmndi");
    verify(model).getLocationMap();
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>Then calls {@link IndentingXMLStreamWriter#writeAttribute(String, String)}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given HashMap(); then calls writeAttribute(String, String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenHashMap_thenCallsWriteAttribute() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationMap()).thenReturn(new HashMap<>());
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmndi"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/DI"));
    verify(model).getFlowLocationMap();
    verify(model).getLocationMap();
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link HashMap#HashMap()}.
   *   <li>When {@link BpmnModel} {@link BpmnModel#getMessageFlow(String)} return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given HashMap(); when BpmnModel getMessageFlow(String) return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenHashMap_whenBpmnModelGetMessageFlowReturnNull() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    BpmnModel model = mock(BpmnModel.class);
    when(model.getArtifact(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(model.getMessageFlow(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmndi"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/DI"));
    verify(model).getArtifact("bpmndi");
    verify(model).getFlowElement("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getLocationMap();
    verify(model).getMessageFlow("bpmndi");
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link Lane} (default constructor).
   *   <li>When {@link BpmnModel} {@link BpmnModel#getLane(String)} return {@link Lane} (default
   *       constructor).
   *   <li>Then calls {@link BpmnModel#getLane(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given Lane (default constructor); when BpmnModel getLane(String) return Lane (default constructor); then calls getLane(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenLane_whenBpmnModelGetLaneReturnLane_thenCallsGetLane()
      throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("bpmndi", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);
    graphicInfo2.setExpanded(null);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getLane(Mockito.<String>any())).thenReturn(new Lane());
    when(model.getPool(Mockito.<String>any())).thenReturn(null);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getArtifact(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(model.getMessageFlow(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getArtifact("bpmndi");
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("bpmndi");
    verify(model).getLane("bpmndi");
    verify(model).getLocationMap();
    verify(model).getMessageFlow("bpmndi");
    verify(model).getPool("bpmndi");
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link MessageFlow#MessageFlow(String, String)} with sourceRef is {@code bpmndi}
   *       and targetRef is {@code bpmndi}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given MessageFlow(String, String) with sourceRef is 'bpmndi' and targetRef is 'bpmndi'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenMessageFlowWithSourceRefIsBpmndiAndTargetRefIsBpmndi()
      throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("bpmndi", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getLane(Mockito.<String>any())).thenReturn(null);
    when(model.getPool(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(null);
    when(model.getArtifact(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(model.getMessageFlow(Mockito.<String>any()))
        .thenReturn(new MessageFlow("bpmndi", "bpmndi"));
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getArtifact("bpmndi");
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationGraphicInfo("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("bpmndi");
    verify(model).getLane("bpmndi");
    verify(model).getLocationMap();
    verify(model, atLeast(1)).getMessageFlow("bpmndi");
    verify(model).getPool("bpmndi");
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link Pool} (default constructor).
   *   <li>When {@link BpmnModel} {@link BpmnModel#getPool(String)} return {@link Pool} (default
   *       constructor).
   *   <li>Then calls {@link BpmnModel#getGraphicInfo(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given Pool (default constructor); when BpmnModel getPool(String) return Pool (default constructor); then calls getGraphicInfo(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenPool_whenBpmnModelGetPoolReturnPool_thenCallsGetGraphicInfo()
      throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("bpmndi", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);
    graphicInfo2.setExpanded(null);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getPool(Mockito.<String>any())).thenReturn(new Pool());
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getArtifact(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(model.getMessageFlow(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getArtifact("bpmndi");
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("bpmndi");
    verify(model).getLocationMap();
    verify(model).getMessageFlow("bpmndi");
    verify(model).getPool("bpmndi");
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).
   *   <li>When {@link BpmnModel} {@link BpmnModel#getFlowElement(String)} return {@link SubProcess}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given SubProcess (default constructor); when BpmnModel getFlowElement(String) return SubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenSubProcess_whenBpmnModelGetFlowElementReturnSubProcess()
      throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new SubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(new HashMap<>());
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationGraphicInfo("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("bpmndi");
    verify(model).getLocationMap();
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link SubProcess} (default constructor).
   *   <li>When {@link BpmnModel} {@link BpmnModel#getFlowElement(String)} return {@link SubProcess}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given SubProcess (default constructor); when BpmnModel getFlowElement(String) return SubProcess (default constructor)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenSubProcess_whenBpmnModelGetFlowElementReturnSubProcess2()
      throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("bpmndi", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();

    Builder builderResult2 = Message.builder();

    Builder attributesResult2 = builderResult2.attributes(new HashMap<>());
    graphicInfo2.setElement(
        attributesResult2
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);

    GraphicInfo graphicInfo3 = new GraphicInfo();

    Builder builderResult3 = Message.builder();

    Builder attributesResult3 = builderResult3.attributes(new HashMap<>());
    graphicInfo3.setElement(
        attributesResult3
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);
    graphicInfo3.setExpanded(true);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(new SubProcess());
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationGraphicInfo("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getGraphicInfo("bpmndi");
    verify(model).getLabelGraphicInfo("bpmndi");
    verify(model).getLocationMap();
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} {@link BpmnModel#getLane(String)} return {@code null}.
   *   <li>Then calls {@link BpmnModel#getLane(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); when BpmnModel getLane(String) return 'null'; then calls getLane(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_whenBpmnModelGetLaneReturnNull_thenCallsGetLane() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(null);

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    GraphicInfo graphicInfo = new GraphicInfo();

    Builder builderResult = Message.builder();

    Builder attributesResult = builderResult.attributes(new HashMap<>());
    graphicInfo.setElement(
        attributesResult
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    HashMap<String, GraphicInfo> stringGraphicInfoMap = new HashMap<>();
    stringGraphicInfoMap.put("bpmndi", graphicInfo);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getLane(Mockito.<String>any())).thenReturn(null);
    when(model.getPool(Mockito.<String>any())).thenReturn(null);
    when(model.getArtifact(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(model.getMessageFlow(Mockito.<String>any())).thenReturn(null);
    when(model.getFlowLocationMap()).thenReturn(stringListMap);
    when(model.getLocationMap()).thenReturn(stringGraphicInfoMap);
    when(model.getPools()).thenReturn(poolList);

    IndentingXMLStreamWriter writer = mock(IndentingXMLStreamWriter.class);
    doNothing().when(writer).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    doNothing().when(writer).writeCharacters(Mockito.<String>any());
    doNothing().when(writer).writeEndElement();
    doNothing()
        .when(writer)
        .writeStartElement(Mockito.<String>any(), Mockito.<String>any(), Mockito.<String>any());

    // Act
    BPMNDIExport.writeBPMNDI(model, new IndentingXMLStreamWriter(writer));

    // Assert
    verify(writer, atLeast(1)).writeAttribute(Mockito.<String>any(), Mockito.<String>any());
    verify(writer, atLeast(1)).writeCharacters(Mockito.<String>any());
    verify(writer, atLeast(1)).writeEndElement();
    verify(writer, atLeast(1))
        .writeStartElement(
            eq("bpmndi"), Mockito.<String>any(), eq("http://www.omg.org/spec/BPMN/20100524/DI"));
    verify(model, atLeast(1)).getArtifact("bpmndi");
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getLane("bpmndi");
    verify(model).getLocationMap();
    verify(model).getMessageFlow("bpmndi");
    verify(model).getPool("bpmndi");
    verify(model).getPools();
  }
}
