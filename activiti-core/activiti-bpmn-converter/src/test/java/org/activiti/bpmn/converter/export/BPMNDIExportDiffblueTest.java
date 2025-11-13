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
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.MessageFlow;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.SubProcess;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BPMNDIExportDiffblueTest {
  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor) Name is empty string.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given AdhocSubProcess (default constructor) Name is empty string")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenAdhocSubProcessNameIsEmptyString() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(new Pool());

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setName("");

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

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
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
   *   <li>Given {@link AdhocSubProcess} (default constructor) Name is {@code not empty}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given AdhocSubProcess (default constructor) Name is 'not empty'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenAdhocSubProcessNameIsNotEmpty() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(new Pool());

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    AdhocSubProcess adhocSubProcess = new AdhocSubProcess();
    adhocSubProcess.setName("not empty");

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

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
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
   *   <li>Given {@link GraphicInfo} (default constructor) Expanded is {@code null}.
   *   <li>Then calls {@link BpmnModel#getGraphicInfo(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given GraphicInfo (default constructor) Expanded is 'null'; then calls getGraphicInfo(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenGraphicInfoExpandedIsNull_thenCallsGetGraphicInfo() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(new Pool());

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
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);
    graphicInfo4.setExpanded(null);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
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
   *   <li>Given {@link HashMap#HashMap()} {@code bpmndi} is {@link GraphicInfo} (default
   *       constructor).
   *   <li>Then calls {@link BpmnModel#getGraphicInfo(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeBPMNDI(BpmnModel, XMLStreamWriter); given HashMap() 'bpmndi' is GraphicInfo (default constructor); then calls getGraphicInfo(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_givenHashMapBpmndiIsGraphicInfo_thenCallsGetGraphicInfo() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(new Pool());

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
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);
    graphicInfo4.setExpanded(true);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
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
    poolList.add(new Pool());

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
    poolList.add(new Pool());

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

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
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
   *   <li>Then calls {@link BpmnModel#getArtifact(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeBPMNDI(BpmnModel, XMLStreamWriter); then calls getArtifact(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_thenCallsGetArtifact() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(new Pool());

    HashMap<String, List<GraphicInfo>> stringListMap = new HashMap<>();
    stringListMap.put("bpmndi", new ArrayList<>());

    MessageFlow messageFlow = new MessageFlow("Source Ref", "Target Ref");
    messageFlow.setName("not empty");

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

    BpmnModel model = mock(BpmnModel.class);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(model.getMessageFlow(Mockito.<String>any())).thenReturn(messageFlow);
    when(model.getArtifact(Mockito.<String>any())).thenReturn(new Association());
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
    verify(model).getArtifact("bpmndi");
    verify(model, atLeast(1)).getFlowElement("bpmndi");
    verify(model).getFlowLocationGraphicInfo("bpmndi");
    verify(model).getFlowLocationMap();
    verify(model).getLabelGraphicInfo("bpmndi");
    verify(model).getLocationMap();
    verify(model).getMessageFlow("bpmndi");
    verify(model).getPools();
  }

  /**
   * Test {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>Then calls {@link BpmnModel#getArtifact(String)}.
   * </ul>
   *
   * <p>Method under test: {@link BPMNDIExport#writeBPMNDI(BpmnModel, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeBPMNDI(BpmnModel, XMLStreamWriter); then calls getArtifact(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void BPMNDIExport.writeBPMNDI(BpmnModel, XMLStreamWriter)"})
  void testWriteBPMNDI_thenCallsGetArtifact2() throws Exception {
    // Arrange
    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(new Pool());

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

    MessageFlow messageFlow = new MessageFlow("Source Ref", "Target Ref");
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
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);

    GraphicInfo graphicInfo4 = new GraphicInfo();

    Builder builderResult4 = Message.builder();

    Builder attributesResult4 = builderResult4.attributes(new HashMap<>());
    graphicInfo4.setElement(
        attributesResult4
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);
    graphicInfo4.setExpanded(true);

    BpmnModel model = mock(BpmnModel.class);
    when(model.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(model.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(model.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(model.getFlowElement(Mockito.<String>any())).thenReturn(null);
    when(model.getMessageFlow(Mockito.<String>any())).thenReturn(messageFlow);
    when(model.getArtifact(Mockito.<String>any())).thenReturn(new Association());
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
}
