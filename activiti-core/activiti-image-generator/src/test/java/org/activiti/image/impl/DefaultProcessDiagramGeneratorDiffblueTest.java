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
package org.activiti.image.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_ProfileRGB;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.Artifact;
import org.activiti.bpmn.model.Association;
import org.activiti.bpmn.model.AssociationDirection;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.BooleanDataObject;
import org.activiti.bpmn.model.BoundaryEvent;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.BusinessRuleTask;
import org.activiti.bpmn.model.ComplexGateway;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowElementsContainer;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.Task;
import org.activiti.bpmn.model.TextAnnotation;
import org.activiti.image.exception.ActivitiImageException;
import org.activiti.image.exception.ActivitiInterchangeInfoNotFoundException;
import org.apache.batik.dom.AbstractElement;
import org.apache.batik.dom.GenericComment;
import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.dom.GenericDocument;
import org.apache.batik.dom.GenericElementNS;
import org.apache.batik.dom.xbl.GenericXBLManager;
import org.apache.batik.dom.xbl.XBLManager;
import org.apache.batik.ext.awt.g2d.GraphicContext;
import org.apache.batik.svggen.DOMTreeManager;
import org.apache.batik.svggen.DefaultErrorHandler;
import org.apache.batik.svggen.DefaultExtensionHandler;
import org.apache.batik.svggen.DefaultStyleHandler;
import org.apache.batik.svggen.ExtensionHandler;
import org.apache.batik.svggen.ImageHandler;
import org.apache.batik.svggen.ImageHandlerBase64Encoder;
import org.apache.batik.svggen.SVGBufferedImageOp;
import org.apache.batik.svggen.SVGComposite;
import org.apache.batik.svggen.SVGGeneratorContext;
import org.apache.batik.svggen.SVGGraphicContextConverter;
import org.apache.batik.svggen.SVGPaint;
import org.apache.batik.svggen.SimpleImageHandler;
import org.junit.Test;
import org.mockito.Mockito;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;

public class DefaultProcessDiagramGeneratorDiffblueTest {
  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   */
  @Test
  public void testGenerateDiagram() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    // Act and Assert
    assertThrows(ActivitiInterchangeInfoNotFoundException.class, () -> defaultProcessDiagramGenerator
        .generateDiagram(new BpmnModel(), "Activity Font Name", "Label Font Name", "Annotation Font Name"));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   */
  @Test
  public void testGenerateDiagram2() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   */
  @Test
  public void testGenerateDiagram3() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   */
  @Test
  public void testGenerateDiagram4() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addFlowGraphicInfoList("http://www.w3.org/2000/svg", new ArrayList<>());
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   */
  @Test
  public void testGenerateDiagram5() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(-0.5d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   */
  @Test
  public void testGenerateDiagram6() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(-0.5d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  public void testGenerateDiagram7() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();

    // Act and Assert
    assertThrows(ActivitiInterchangeInfoNotFoundException.class,
        () -> defaultProcessDiagramGenerator.generateDiagram(bpmnModel, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  public void testGenerateDiagram8() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        new ArrayList<>());

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  public void testGenerateDiagram9() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        new ArrayList<>());

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  public void testGenerateDiagram10() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addFlowGraphicInfoList("http://www.w3.org/2000/svg", new ArrayList<>());
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        new ArrayList<>());

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  public void testGenerateDiagram11() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(-0.5d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        new ArrayList<>());

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  public void testGenerateDiagram12() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(-0.5d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        new ArrayList<>());

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   */
  @Test
  public void testGenerateDiagram13() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(ActivitiInterchangeInfoNotFoundException.class,
        () -> defaultProcessDiagramGenerator.generateDiagram(bpmnModel, highLightedActivities, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   */
  @Test
  public void testGenerateDiagram14() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>());

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   */
  @Test
  public void testGenerateDiagram15() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>());

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   */
  @Test
  public void testGenerateDiagram16() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addFlowGraphicInfoList("http://www.w3.org/2000/svg", new ArrayList<>());
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>());

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   */
  @Test
  public void testGenerateDiagram17() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(-0.5d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>());

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   */
  @Test
  public void testGenerateDiagram18() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(-0.5d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>());

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   */
  @Test
  public void testGenerateDiagram19() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(ActivitiInterchangeInfoNotFoundException.class,
        () -> defaultProcessDiagramGenerator.generateDiagram(bpmnModel, highLightedActivities, new ArrayList<>(),
            "Activity Font Name", "Label Font Name", "Annotation Font Name"));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   */
  @Test
  public void testGenerateDiagram20() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   */
  @Test
  public void testGenerateDiagram21() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   */
  @Test
  public void testGenerateDiagram22() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addFlowGraphicInfoList("http://www.w3.org/2000/svg", new ArrayList<>());
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   */
  @Test
  public void testGenerateDiagram23() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(-0.5d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   */
  @Test
  public void testGenerateDiagram24() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(-0.5d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("No interchange information found.", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("No interchange information found."));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   */
  @Test
  public void testGenerateDiagram25() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    byte[] byteArray = new byte[51];
    assertEquals(51,
        defaultProcessDiagramGenerator
            .generateDiagram(bpmnModel, highLightedActivities, new ArrayList<>(), "Activity Font Name",
                "Label Font Name", "Annotation Font Name", true)
            .read(byteArray));
    assertTrue(bpmnModel.getLocationMap().isEmpty());
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE svg".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   */
  @Test
  public void testGenerateDiagram26() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("/image/na.svg", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name",
        true);

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("/image/na.svg"));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   */
  @Test
  public void testGenerateDiagram27() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    bpmnModel.addGraphicInfo("/image/na.svg", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name",
        true);

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("/image/na.svg"));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   */
  @Test
  public void testGenerateDiagram28() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addFlowGraphicInfoList("http://www.w3.org/2000/svg", new ArrayList<>());
    bpmnModel.addGraphicInfo("/image/na.svg", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name",
        true);

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("/image/na.svg"));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   */
  @Test
  public void testGenerateDiagram29() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(-0.5d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("/image/na.svg", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name",
        true);

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("/image/na.svg"));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   */
  @Test
  public void testGenerateDiagram30() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(-0.5d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("/image/na.svg", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name",
        true);

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("/image/na.svg"));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   */
  @Test
  public void testGenerateDiagram31() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(ActivitiImageException.class,
        () -> defaultProcessDiagramGenerator.generateDiagram(bpmnModel, highLightedActivities, highLightedFlows,
            currentActivities, new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name", true,
            "foo.txt"));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   */
  @Test
  public void testGenerateDiagram32() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Key", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, highLightedFlows, currentActivities, new ArrayList<>(), "Activity Font Name",
        "Label Font Name", "Annotation Font Name", true, "foo.txt");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("Key"));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   */
  @Test
  public void testGenerateDiagram33() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    bpmnModel.addGraphicInfo("Key", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, highLightedFlows, currentActivities, new ArrayList<>(), "Activity Font Name",
        "Label Font Name", "Annotation Font Name", true, "foo.txt");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("Key"));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   */
  @Test
  public void testGenerateDiagram34() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addFlowGraphicInfoList("http://www.w3.org/2000/svg", new ArrayList<>());
    bpmnModel.addGraphicInfo("Key", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, highLightedFlows, currentActivities, new ArrayList<>(), "Activity Font Name",
        "Label Font Name", "Annotation Font Name", true, "foo.txt");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("Key"));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   */
  @Test
  public void testGenerateDiagram35() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(-0.5d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Key", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, highLightedFlows, currentActivities, new ArrayList<>(), "Activity Font Name",
        "Label Font Name", "Annotation Font Name", true, "foo.txt");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("Key"));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   */
  @Test
  public void testGenerateDiagram36() throws IOException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(-0.5d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Key", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    InputStream actualGenerateDiagramResult = defaultProcessDiagramGenerator.generateDiagram(bpmnModel,
        highLightedActivities, highLightedFlows, currentActivities, new ArrayList<>(), "Activity Font Name",
        "Label Font Name", "Annotation Font Name", true, "foo.txt");

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertSame(graphicInfo, locationMap.get("Key"));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#getDefaultDiagram(String)}
   */
  @Test
  public void testGetDefaultDiagram() {
    // Arrange, Act and Assert
    assertThrows(ActivitiImageException.class,
        () -> (new DefaultProcessDiagramGenerator()).getDefaultDiagram("foo.txt"));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#getDefaultDiagram(String)}
   */
  @Test
  public void testGetDefaultDiagram2() throws IOException {
    // Arrange, Act and Assert
    byte[] byteArray = new byte[51];
    assertEquals(51, (new DefaultProcessDiagramGenerator()).getDefaultDiagram(null).read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE svg".getBytes("UTF-8"), byteArray);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}
   */
  @Test
  public void testGenerateProcessDiagram() throws DOMException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    DefaultProcessDiagramCanvas actualGenerateProcessDiagramResult = defaultProcessDiagramGenerator
        .generateProcessDiagram(bpmnModel, highLightedActivities, highLightedFlows, currentActivities,
            new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualGenerateProcessDiagramResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualGenerateProcessDiagramResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualGenerateProcessDiagramResult.activityFontName);
    assertEquals("Annotation Font Name", actualGenerateProcessDiagramResult.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualGenerateProcessDiagramResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0, actualGenerateProcessDiagramResult.minX);
    assertEquals(0, actualGenerateProcessDiagramResult.minY);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualGenerateProcessDiagramResult.canvasHeight);
    assertEquals(10, actualGenerateProcessDiagramResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualGenerateProcessDiagramResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    assertTrue(processDiagramSVGGraphics2D.getDefinitionSet().isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    assertEquals(highLightedActivities, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getTexturePaintConverter().getDefinitionSet());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualGenerateProcessDiagramResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualGenerateProcessDiagramResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}
   */
  @Test
  public void testGenerateProcessDiagram2() throws DOMException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    DefaultProcessDiagramCanvas actualGenerateProcessDiagramResult = defaultProcessDiagramGenerator
        .generateProcessDiagram(bpmnModel, highLightedActivities, highLightedFlows, currentActivities,
            new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualGenerateProcessDiagramResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualGenerateProcessDiagramResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualGenerateProcessDiagramResult.activityFontName);
    assertEquals("Annotation Font Name", actualGenerateProcessDiagramResult.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualGenerateProcessDiagramResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0, actualGenerateProcessDiagramResult.minX);
    assertEquals(0, actualGenerateProcessDiagramResult.minY);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualGenerateProcessDiagramResult.canvasHeight);
    assertEquals(10, actualGenerateProcessDiagramResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualGenerateProcessDiagramResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    assertTrue(processDiagramSVGGraphics2D.getDefinitionSet().isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    assertEquals(highLightedActivities, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getTexturePaintConverter().getDefinitionSet());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualGenerateProcessDiagramResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualGenerateProcessDiagramResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}
   */
  @Test
  public void testGenerateProcessDiagram3() throws DOMException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(Double.MAX_VALUE);
    graphicInfo.setWidth(Double.MAX_VALUE);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Arial", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    DefaultProcessDiagramCanvas actualGenerateProcessDiagramResult = defaultProcessDiagramGenerator
        .generateProcessDiagram(bpmnModel, highLightedActivities, highLightedFlows, currentActivities,
            new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualGenerateProcessDiagramResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualGenerateProcessDiagramResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualGenerateProcessDiagramResult.activityFontName);
    assertEquals("Annotation Font Name", actualGenerateProcessDiagramResult.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualGenerateProcessDiagramResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0, actualGenerateProcessDiagramResult.minX);
    assertEquals(0, actualGenerateProcessDiagramResult.minY);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualGenerateProcessDiagramResult.canvasHeight);
    assertEquals(10, actualGenerateProcessDiagramResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualGenerateProcessDiagramResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    assertTrue(processDiagramSVGGraphics2D.getDefinitionSet().isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    assertEquals(highLightedActivities, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getTexturePaintConverter().getDefinitionSet());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualGenerateProcessDiagramResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualGenerateProcessDiagramResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}
   */
  @Test
  public void testGenerateProcessDiagram4() throws DOMException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    bpmnModel.addFlowGraphicInfoList("Arial", graphicInfoList);
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    DefaultProcessDiagramCanvas actualGenerateProcessDiagramResult = defaultProcessDiagramGenerator
        .generateProcessDiagram(bpmnModel, highLightedActivities, highLightedFlows, currentActivities,
            new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualGenerateProcessDiagramResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualGenerateProcessDiagramResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualGenerateProcessDiagramResult.activityFontName);
    assertEquals("Annotation Font Name", actualGenerateProcessDiagramResult.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualGenerateProcessDiagramResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0, actualGenerateProcessDiagramResult.minX);
    assertEquals(0, actualGenerateProcessDiagramResult.minY);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualGenerateProcessDiagramResult.canvasHeight);
    assertEquals(10, actualGenerateProcessDiagramResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualGenerateProcessDiagramResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    assertTrue(processDiagramSVGGraphics2D.getDefinitionSet().isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    assertEquals(graphicInfoList, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(graphicInfoList, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(graphicInfoList, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(graphicInfoList, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(graphicInfoList, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(graphicInfoList, paintConverter.getTexturePaintConverter().getDefinitionSet());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualGenerateProcessDiagramResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualGenerateProcessDiagramResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}
   */
  @Test
  public void testGenerateProcessDiagram5() throws DOMException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(Double.MAX_VALUE);
    graphicInfo.setWidth(Double.MAX_VALUE);
    graphicInfo.setX(-0.5d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Arial", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    DefaultProcessDiagramCanvas actualGenerateProcessDiagramResult = defaultProcessDiagramGenerator
        .generateProcessDiagram(bpmnModel, highLightedActivities, highLightedFlows, currentActivities,
            new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualGenerateProcessDiagramResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualGenerateProcessDiagramResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualGenerateProcessDiagramResult.activityFontName);
    assertEquals("Annotation Font Name", actualGenerateProcessDiagramResult.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualGenerateProcessDiagramResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0, actualGenerateProcessDiagramResult.minX);
    assertEquals(0, actualGenerateProcessDiagramResult.minY);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualGenerateProcessDiagramResult.canvasHeight);
    assertEquals(10, actualGenerateProcessDiagramResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualGenerateProcessDiagramResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    assertTrue(processDiagramSVGGraphics2D.getDefinitionSet().isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    assertEquals(highLightedActivities, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getTexturePaintConverter().getDefinitionSet());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualGenerateProcessDiagramResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualGenerateProcessDiagramResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}
   */
  @Test
  public void testGenerateProcessDiagram6() throws DOMException {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(Double.MAX_VALUE);
    graphicInfo.setWidth(Double.MAX_VALUE);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(-0.5d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Arial", graphicInfo);
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    DefaultProcessDiagramCanvas actualGenerateProcessDiagramResult = defaultProcessDiagramGenerator
        .generateProcessDiagram(bpmnModel, highLightedActivities, highLightedFlows, currentActivities,
            new ArrayList<>(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualGenerateProcessDiagramResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualGenerateProcessDiagramResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualGenerateProcessDiagramResult.activityFontName);
    assertEquals("Annotation Font Name", actualGenerateProcessDiagramResult.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualGenerateProcessDiagramResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0, actualGenerateProcessDiagramResult.minX);
    assertEquals(0, actualGenerateProcessDiagramResult.minY);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualGenerateProcessDiagramResult.canvasHeight);
    assertEquals(10, actualGenerateProcessDiagramResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualGenerateProcessDiagramResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    assertTrue(processDiagramSVGGraphics2D.getDefinitionSet().isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    assertEquals(highLightedActivities, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getTexturePaintConverter().getDefinitionSet());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualGenerateProcessDiagramResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualGenerateProcessDiagramResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}
   */
  @Test
  public void testPrepareBpmnModel() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    defaultProcessDiagramGenerator.prepareBpmnModel(bpmnModel);

    // Assert
    assertTrue(bpmnModel.getLocationMap().isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}
   */
  @Test
  public void testPrepareBpmnModel2() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Key", graphicInfo);

    // Act
    defaultProcessDiagramGenerator.prepareBpmnModel(bpmnModel);

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    assertSame(graphicInfo, locationMap.get("Key"));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}
   */
  @Test
  public void testPrepareBpmnModel3() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addFlowGraphicInfoList("Key", new ArrayList<>());

    // Act
    defaultProcessDiagramGenerator.prepareBpmnModel(bpmnModel);

    // Assert
    assertTrue(bpmnModel.getLocationMap().isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}
   */
  @Test
  public void testPrepareBpmnModel4() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(-0.5d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Key", graphicInfo);

    // Act
    defaultProcessDiagramGenerator.prepareBpmnModel(bpmnModel);

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    assertSame(graphicInfo, locationMap.get("Key"));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}
   */
  @Test
  public void testPrepareBpmnModel5() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(-0.5d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Key", graphicInfo);

    // Act
    defaultProcessDiagramGenerator.prepareBpmnModel(bpmnModel);

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    assertSame(graphicInfo, locationMap.get("Key"));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  public void testDrawActivity() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = new BpmnModel();
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(new ArrayList<>());
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(processDiagramCanvas, bpmnModel, flowNode, currentActivities,
        erroredActivities, highLightedActivities, new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  public void testDrawActivity2() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<FlowElement> flowElementList = new ArrayList<>();
    flowElementList.add(new AdhocSubProcess());
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getFlowElements()).thenReturn(flowElementList);
    when(flowNode.getOutgoingFlows()).thenReturn(new ArrayList<>());
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(processDiagramCanvas, bpmnModel, flowNode, currentActivities,
        erroredActivities, highLightedActivities, new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  public void testDrawActivity3() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(processDiagramCanvas, bpmnModel, flowNode, currentActivities,
        erroredActivities, highLightedActivities, new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  public void testDrawActivity4() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(processDiagramCanvas, bpmnModel, flowNode, currentActivities,
        erroredActivities, highLightedActivities, new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  public void testDrawActivity5() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(processDiagramCanvas, bpmnModel, flowNode, currentActivities,
        erroredActivities, highLightedActivities, new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo(isNull());
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  public void testDrawActivity6() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    doNothing().when(processDiagramCanvas).drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    when(processDiagramCanvas.connectionPerfectionizer(Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(),
        Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(), Mockito.<GraphicInfo>any(), Mockito.<GraphicInfo>any(),
        Mockito.<List<GraphicInfo>>any())).thenReturn(new ArrayList<>());
    doNothing().when(processDiagramCanvas)
        .drawSequenceflow(Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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

    GraphicInfo graphicInfo3 = new GraphicInfo();
    graphicInfo3.setElement(new ActivitiListener());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(processDiagramCanvas, bpmnModel, flowNode, currentActivities,
        erroredActivities, highLightedActivities, new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo(isNull());
    verify(bpmnModel, atLeast(1)).getGraphicInfo(isNull());
    verify(bpmnModel).getLabelGraphicInfo(isNull());
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas).connectionPerfectionizer(eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle),
        eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle), isA(GraphicInfo.class), isA(GraphicInfo.class),
        isA(List.class));
    verify(processDiagramCanvas).drawLabel(isNull(), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas).drawSequenceflow(isA(int[].class), isA(int[].class), eq(false), eq(false), eq(false));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  public void testDrawActivity7() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    doThrow(new ActivitiInterchangeInfoNotFoundException("An error occurred")).when(processDiagramCanvas)
        .drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    when(processDiagramCanvas.connectionPerfectionizer(Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(),
        Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(), Mockito.<GraphicInfo>any(), Mockito.<GraphicInfo>any(),
        Mockito.<List<GraphicInfo>>any())).thenReturn(new ArrayList<>());
    doNothing().when(processDiagramCanvas)
        .drawSequenceflow(Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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

    GraphicInfo graphicInfo3 = new GraphicInfo();
    graphicInfo3.setElement(new ActivitiListener());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(ActivitiInterchangeInfoNotFoundException.class,
        () -> defaultProcessDiagramGenerator.drawActivity(processDiagramCanvas, bpmnModel, flowNode, currentActivities,
            erroredActivities, highLightedActivities, new ArrayList<>()));
    verify(flowNode).getDefaultFlow();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo(isNull());
    verify(bpmnModel, atLeast(1)).getGraphicInfo(isNull());
    verify(bpmnModel).getLabelGraphicInfo(isNull());
    verify(flowNode).getOutgoingFlows();
    verify(processDiagramCanvas).connectionPerfectionizer(eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle),
        eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle), isA(GraphicInfo.class), isA(GraphicInfo.class),
        isA(List.class));
    verify(processDiagramCanvas).drawLabel(isNull(), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas).drawSequenceflow(isA(int[].class), isA(int[].class), eq(false), eq(false), eq(false));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  public void testDrawActivity8() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    doNothing().when(processDiagramCanvas).drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    when(processDiagramCanvas.connectionPerfectionizer(Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(),
        Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(), Mockito.<GraphicInfo>any(), Mockito.<GraphicInfo>any(),
        Mockito.<List<GraphicInfo>>any())).thenReturn(new ArrayList<>());
    doNothing().when(processDiagramCanvas)
        .drawSequenceflow(Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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

    GraphicInfo graphicInfo3 = new GraphicInfo();
    graphicInfo3.setElement(new ActivitiListener());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(10.0d);
    graphicInfo3.setWidth(10.0d);
    graphicInfo3.setX(2.0d);
    graphicInfo3.setXmlColumnNumber(10);
    graphicInfo3.setXmlRowNumber(10);
    graphicInfo3.setY(3.0d);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new BusinessRuleTask());

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(processDiagramCanvas, bpmnModel, flowNode, currentActivities,
        erroredActivities, highLightedActivities, new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo(isNull());
    verify(bpmnModel, atLeast(1)).getGraphicInfo(isNull());
    verify(bpmnModel).getLabelGraphicInfo(isNull());
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas).connectionPerfectionizer(eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle),
        eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle), isA(GraphicInfo.class), isA(GraphicInfo.class),
        isA(List.class));
    verify(processDiagramCanvas).drawLabel(isNull(), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas).drawSequenceflow(isA(int[].class), isA(int[].class), eq(false), eq(false), eq(false));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  public void testDrawActivity9() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    doNothing().when(processDiagramCanvas).drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    when(processDiagramCanvas.connectionPerfectionizer(Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(),
        Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(), Mockito.<GraphicInfo>any(), Mockito.<GraphicInfo>any(),
        Mockito.<List<GraphicInfo>>any())).thenReturn(new ArrayList<>());
    doNothing().when(processDiagramCanvas)
        .drawSequenceflow(Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(processDiagramCanvas, bpmnModel, flowNode, currentActivities,
        erroredActivities, highLightedActivities, new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo(isNull());
    verify(bpmnModel, atLeast(1)).getGraphicInfo(eq("42"));
    verify(bpmnModel).getLabelGraphicInfo(isNull());
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas).connectionPerfectionizer(eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle),
        eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle), isA(GraphicInfo.class), isA(GraphicInfo.class),
        isA(List.class));
    verify(processDiagramCanvas).drawLabel(isNull(), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas).drawSequenceflow(isA(int[].class), isA(int[].class), eq(false), eq(false), eq(false));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  public void testDrawActivity10() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    doNothing().when(processDiagramCanvas).drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    when(processDiagramCanvas.connectionPerfectionizer(Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(),
        Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(), Mockito.<GraphicInfo>any(), Mockito.<GraphicInfo>any(),
        Mockito.<List<GraphicInfo>>any())).thenReturn(new ArrayList<>());
    doNothing().when(processDiagramCanvas)
        .drawSequenceflow(Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow("Source Ref", "Target Ref"));
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn(null);
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(processDiagramCanvas, bpmnModel, flowNode, currentActivities,
        erroredActivities, highLightedActivities, new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo(isNull());
    verify(bpmnModel, atLeast(1)).getGraphicInfo(eq("42"));
    verify(bpmnModel).getLabelGraphicInfo(isNull());
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas).connectionPerfectionizer(eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle),
        eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle), isA(GraphicInfo.class), isA(GraphicInfo.class),
        isA(List.class));
    verify(processDiagramCanvas).drawLabel(isNull(), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas).drawSequenceflow(isA(int[].class), isA(int[].class), eq(false), eq(false), eq(false));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  public void testDrawActivity11() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    doNothing().when(processDiagramCanvas).drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    when(processDiagramCanvas.connectionPerfectionizer(Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(),
        Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(), Mockito.<GraphicInfo>any(), Mockito.<GraphicInfo>any(),
        Mockito.<List<GraphicInfo>>any())).thenReturn(new ArrayList<>());
    doNothing().when(processDiagramCanvas)
        .drawSequenceflow(Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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
    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo2);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);
    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    when(sequenceFlow.getSourceRef()).thenReturn("Source Ref");
    when(sequenceFlow.getTargetRef()).thenReturn("Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);
    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(processDiagramCanvas, bpmnModel, flowNode, currentActivities,
        erroredActivities, highLightedActivities, new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo(eq("42"));
    verify(bpmnModel, atLeast(1)).getGraphicInfo(eq("42"));
    verify(bpmnModel).getLabelGraphicInfo(eq("42"));
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas).connectionPerfectionizer(eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle),
        eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle), isA(GraphicInfo.class), isA(GraphicInfo.class),
        isA(List.class));
    verify(processDiagramCanvas).drawLabel(eq("Name"), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas).drawSequenceflow(isA(int[].class), isA(int[].class), eq(true), eq(false), eq(false));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List)}
   */
  @Test
  public void testConnectionPerfectionizer() {
    // Arrange
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = new BpmnModel();
    ActivitiListener sourceElement = new ActivitiListener();
    ActivitiListener targetElement = new ActivitiListener();

    // Act
    List<GraphicInfo> actualConnectionPerfectionizerResult = DefaultProcessDiagramGenerator
        .connectionPerfectionizer(processDiagramCanvas, bpmnModel, sourceElement, targetElement, new ArrayList<>());

    // Assert
    assertTrue(actualConnectionPerfectionizerResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List)}
   */
  @Test
  public void testConnectionPerfectionizer2() {
    // Arrange
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = new BpmnModel();
    ActivitiListener sourceElement = new ActivitiListener();
    ActivitiListener targetElement = new ActivitiListener();

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

    // Act
    List<GraphicInfo> actualConnectionPerfectionizerResult = DefaultProcessDiagramGenerator
        .connectionPerfectionizer(processDiagramCanvas, bpmnModel, sourceElement, targetElement, graphicInfoList);

    // Assert
    assertEquals(1, actualConnectionPerfectionizerResult.size());
    assertSame(graphicInfo, actualConnectionPerfectionizerResult.get(0));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List)}
   */
  @Test
  public void testConnectionPerfectionizer3() {
    // Arrange
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = new BpmnModel();
    ActivitiListener sourceElement = new ActivitiListener();
    ActivitiListener targetElement = new ActivitiListener();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(false);
    graphicInfo2.setHeight(0.5d);
    graphicInfo2.setWidth(0.5d);
    graphicInfo2.setX(10.0d);
    graphicInfo2.setXmlColumnNumber(1);
    graphicInfo2.setXmlRowNumber(1);
    graphicInfo2.setY(10.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);
    graphicInfoList.add(graphicInfo);

    // Act
    List<GraphicInfo> actualConnectionPerfectionizerResult = DefaultProcessDiagramGenerator
        .connectionPerfectionizer(processDiagramCanvas, bpmnModel, sourceElement, targetElement, graphicInfoList);

    // Assert
    assertEquals(2, actualConnectionPerfectionizerResult.size());
    assertSame(graphicInfo2, actualConnectionPerfectionizerResult.get(0));
    assertSame(graphicInfo, actualConnectionPerfectionizerResult.get(1));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  public void testGetShapeType() {
    // Arrange, Act and Assert
    assertNull(DefaultProcessDiagramGenerator.getShapeType(new ActivitiListener()));
    assertEquals(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
        DefaultProcessDiagramGenerator.getShapeType(new Task()));
    assertEquals(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
        DefaultProcessDiagramGenerator.getShapeType(new AdhocSubProcess()));
    assertEquals(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
        DefaultProcessDiagramGenerator.getShapeType(new TextAnnotation()));
    assertEquals(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rhombus,
        DefaultProcessDiagramGenerator.getShapeType(new ComplexGateway()));
    assertEquals(DefaultProcessDiagramCanvas.SHAPE_TYPE.Ellipse,
        DefaultProcessDiagramGenerator.getShapeType(new BoundaryEvent()));
  }

  /**
   * Method under test: {@link DefaultProcessDiagramGenerator#getLineCenter(List)}
   */
  @Test
  public void testGetLineCenter() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(false);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(10.0d);
    graphicInfo2.setXmlColumnNumber(1);
    graphicInfo2.setXmlRowNumber(1);
    graphicInfo2.setY(2.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);
    graphicInfoList.add(graphicInfo);

    // Act
    GraphicInfo actualLineCenter = DefaultProcessDiagramGenerator.getLineCenter(graphicInfoList);

    // Assert
    assertNull(actualLineCenter.getExpanded());
    assertNull(actualLineCenter.getElement());
    assertEquals(0, actualLineCenter.getXmlColumnNumber());
    assertEquals(0, actualLineCenter.getXmlRowNumber());
    assertEquals(0.0d, actualLineCenter.getHeight(), 0.0);
    assertEquals(0.0d, actualLineCenter.getWidth(), 0.0);
    assertEquals(2.5d, actualLineCenter.getY(), 0.0);
    assertEquals(6.0d, actualLineCenter.getX(), 0.0);
  }

  /**
   * Method under test: {@link DefaultProcessDiagramGenerator#getLineCenter(List)}
   */
  @Test
  public void testGetLineCenter2() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(false);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(10.0d);
    graphicInfo2.setXmlColumnNumber(1);
    graphicInfo2.setXmlRowNumber(1);
    graphicInfo2.setY(2.0d);

    GraphicInfo graphicInfo3 = new GraphicInfo();
    graphicInfo3.setElement(new ActivitiListener());
    graphicInfo3.setExpanded(true);
    graphicInfo3.setHeight(8.06225774829855d);
    graphicInfo3.setWidth(8.06225774829855d);
    graphicInfo3.setX(8.06225774829855d);
    graphicInfo3.setXmlColumnNumber(2);
    graphicInfo3.setXmlRowNumber(2);
    graphicInfo3.setY(4.031128874149275d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo3);
    graphicInfoList.add(graphicInfo2);
    graphicInfoList.add(graphicInfo);

    // Act
    GraphicInfo actualLineCenter = DefaultProcessDiagramGenerator.getLineCenter(graphicInfoList);

    // Assert
    assertNull(actualLineCenter.getExpanded());
    assertNull(actualLineCenter.getElement());
    assertEquals(0, actualLineCenter.getXmlColumnNumber());
    assertEquals(0, actualLineCenter.getXmlRowNumber());
    assertEquals(0.0d, actualLineCenter.getHeight(), 0.0);
    assertEquals(0.0d, actualLineCenter.getWidth(), 0.0);
    assertEquals(2.3245883961385942d, actualLineCenter.getY(), 0.0);
    assertEquals(7.403292830891248d, actualLineCenter.getX(), 0.0);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  public void testDrawArtifact() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new Association());

    // Assert
    verify(bpmnModel, atLeast(1)).getFlowElement(isNull());
    verify(bpmnModel).getFlowLocationGraphicInfo(isNull());
    verify(bpmnModel, atLeast(1)).getGraphicInfo(isNull());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  public void testDrawArtifact2() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any()))
        .thenThrow(new ActivitiInterchangeInfoNotFoundException("An error occurred"));
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    // Act and Assert
    assertThrows(ActivitiInterchangeInfoNotFoundException.class,
        () -> defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new Association()));
    verify(bpmnModel, atLeast(1)).getFlowElement(isNull());
    verify(bpmnModel).getFlowLocationGraphicInfo(isNull());
    verify(bpmnModel).getGraphicInfo(isNull());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  public void testDrawArtifact3() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(),
        Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(), Mockito.<GraphicInfo>any(), Mockito.<GraphicInfo>any(),
        Mockito.<List<GraphicInfo>>any())).thenReturn(new ArrayList<>());
    doNothing().when(processDiagramCanvas)
        .drawAssociation(Mockito.<int[]>any(), Mockito.<int[]>any(), Mockito.<AssociationDirection>any(), anyBoolean());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new Association());

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getFlowElement(isNull());
    verify(bpmnModel).getFlowLocationGraphicInfo(isNull());
    verify(bpmnModel, atLeast(1)).getGraphicInfo(isNull());
    verify(processDiagramCanvas).connectionPerfectionizer(eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle),
        eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle), isA(GraphicInfo.class), isA(GraphicInfo.class),
        isA(List.class));
    verify(processDiagramCanvas).drawAssociation(isA(int[].class), isA(int[].class), eq(AssociationDirection.NONE),
        eq(false));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  public void testDrawArtifact4() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(),
        Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(), Mockito.<GraphicInfo>any(), Mockito.<GraphicInfo>any(),
        Mockito.<List<GraphicInfo>>any())).thenReturn(new ArrayList<>());
    doNothing().when(processDiagramCanvas)
        .drawAssociation(Mockito.<int[]>any(), Mockito.<int[]>any(), Mockito.<AssociationDirection>any(), anyBoolean());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new BusinessRuleTask());

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new Association());

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getFlowElement(isNull());
    verify(bpmnModel).getFlowLocationGraphicInfo(isNull());
    verify(bpmnModel, atLeast(1)).getGraphicInfo(isNull());
    verify(processDiagramCanvas).connectionPerfectionizer(eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle),
        eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle), isA(GraphicInfo.class), isA(GraphicInfo.class),
        isA(List.class));
    verify(processDiagramCanvas).drawAssociation(isA(int[].class), isA(int[].class), eq(AssociationDirection.NONE),
        eq(false));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  public void testDrawArtifact5() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(),
        Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(), Mockito.<GraphicInfo>any(), Mockito.<GraphicInfo>any(),
        Mockito.<List<GraphicInfo>>any())).thenReturn(new ArrayList<>());
    doNothing().when(processDiagramCanvas)
        .drawAssociation(Mockito.<int[]>any(), Mockito.<int[]>any(), Mockito.<AssociationDirection>any(), anyBoolean());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(null);

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new Association());

    // Assert that nothing has changed
    verify(bpmnModel, atLeast(1)).getArtifact(isNull());
    verify(bpmnModel, atLeast(1)).getFlowElement(isNull());
    verify(bpmnModel).getFlowLocationGraphicInfo(isNull());
    verify(bpmnModel, atLeast(1)).getGraphicInfo(isNull());
    verify(processDiagramCanvas).connectionPerfectionizer(isNull(), isNull(), isA(GraphicInfo.class),
        isA(GraphicInfo.class), isA(List.class));
    verify(processDiagramCanvas).drawAssociation(isA(int[].class), isA(int[].class), eq(AssociationDirection.NONE),
        eq(false));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  public void testDrawArtifact6() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(),
        Mockito.<DefaultProcessDiagramCanvas.SHAPE_TYPE>any(), Mockito.<GraphicInfo>any(), Mockito.<GraphicInfo>any(),
        Mockito.<List<GraphicInfo>>any())).thenReturn(new ArrayList<>());
    doNothing().when(processDiagramCanvas)
        .drawAssociation(Mockito.<int[]>any(), Mockito.<int[]>any(), Mockito.<AssociationDirection>any(), anyBoolean());
    BusinessRuleTask businessRuleTask = mock(BusinessRuleTask.class);
    when(businessRuleTask.getId()).thenReturn("42");

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(businessRuleTask);

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new Association());

    // Assert that nothing has changed
    verify(businessRuleTask, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(isNull());
    verify(bpmnModel).getFlowLocationGraphicInfo(isNull());
    verify(bpmnModel, atLeast(1)).getGraphicInfo(eq("42"));
    verify(processDiagramCanvas).connectionPerfectionizer(eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle),
        eq(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle), isA(GraphicInfo.class), isA(GraphicInfo.class),
        isA(List.class));
    verify(processDiagramCanvas).drawAssociation(isA(int[].class), isA(int[].class), eq(AssociationDirection.NONE),
        eq(false));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  public void testDrawArtifact7() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    doNothing().when(processDiagramCanvas)
        .drawTextAnnotation(Mockito.<String>any(), Mockito.<String>any(), Mockito.<GraphicInfo>any());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new TextAnnotation());

    // Assert that nothing has changed
    verify(bpmnModel).getGraphicInfo(isNull());
    verify(processDiagramCanvas).drawTextAnnotation(isNull(), isNull(), isA(GraphicInfo.class));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  public void testDrawArtifact8() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    doThrow(new ActivitiInterchangeInfoNotFoundException("An error occurred")).when(processDiagramCanvas)
        .drawTextAnnotation(Mockito.<String>any(), Mockito.<String>any(), Mockito.<GraphicInfo>any());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);

    // Act and Assert
    assertThrows(ActivitiInterchangeInfoNotFoundException.class,
        () -> defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new TextAnnotation()));
    verify(bpmnModel).getGraphicInfo(isNull());
    verify(processDiagramCanvas).drawTextAnnotation(isNull(), isNull(), isA(GraphicInfo.class));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  public void testInitProcessDiagramCanvas() throws DOMException {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult = DefaultProcessDiagramGenerator
        .initProcessDiagramCanvas(new BpmnModel(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualInitProcessDiagramCanvasResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Annotation Font Name", actualInitProcessDiagramCanvasResult.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minY);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    List definitionSet = processDiagramSVGGraphics2D.getDefinitionSet();
    assertTrue(definitionSet.isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertEquals(definitionSet, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getTexturePaintConverter().getDefinitionSet());
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  public void testInitProcessDiagramCanvas2() throws DOMException {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult = DefaultProcessDiagramGenerator
        .initProcessDiagramCanvas(bpmnModel, "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualInitProcessDiagramCanvasResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Annotation Font Name", actualInitProcessDiagramCanvasResult.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minY);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    List definitionSet = processDiagramSVGGraphics2D.getDefinitionSet();
    assertTrue(definitionSet.isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertEquals(definitionSet, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getTexturePaintConverter().getDefinitionSet());
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  public void testInitProcessDiagramCanvas3() throws DOMException {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult = DefaultProcessDiagramGenerator
        .initProcessDiagramCanvas(bpmnModel, "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualInitProcessDiagramCanvasResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Annotation Font Name", actualInitProcessDiagramCanvasResult.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    List definitionSet = processDiagramSVGGraphics2D.getDefinitionSet();
    assertTrue(definitionSet.isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minY);
    assertEquals(definitionSet, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getTexturePaintConverter().getDefinitionSet());
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  public void testInitProcessDiagramCanvas4() throws DOMException {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult = DefaultProcessDiagramGenerator
        .initProcessDiagramCanvas(bpmnModel, "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualInitProcessDiagramCanvasResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Annotation Font Name", actualInitProcessDiagramCanvasResult.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    List definitionSet = processDiagramSVGGraphics2D.getDefinitionSet();
    assertTrue(definitionSet.isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minY);
    assertEquals(definitionSet, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getTexturePaintConverter().getDefinitionSet());
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  public void testInitProcessDiagramCanvas5() throws DOMException {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult = DefaultProcessDiagramGenerator
        .initProcessDiagramCanvas(bpmnModel, "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualInitProcessDiagramCanvasResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Annotation Font Name", actualInitProcessDiagramCanvasResult.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minY);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    List definitionSet = processDiagramSVGGraphics2D.getDefinitionSet();
    assertTrue(definitionSet.isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertEquals(definitionSet, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getTexturePaintConverter().getDefinitionSet());
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  public void testInitProcessDiagramCanvas6() throws DOMException {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult = DefaultProcessDiagramGenerator
        .initProcessDiagramCanvas(bpmnModel, null, "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualInitProcessDiagramCanvasResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    assertEquals("Annotation Font Name", actualInitProcessDiagramCanvasResult.annotationFontName);
    FontMetrics fontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Arial", font.getName());
    assertEquals("Arial", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    List definitionSet = processDiagramSVGGraphics2D.getDefinitionSet();
    assertTrue(definitionSet.isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minY);
    assertEquals(definitionSet, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getTexturePaintConverter().getDefinitionSet());
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  public void testInitProcessDiagramCanvas7() throws DOMException {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult = DefaultProcessDiagramGenerator
        .initProcessDiagramCanvas(bpmnModel, "Activity Font Name", null, "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualInitProcessDiagramCanvasResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Annotation Font Name", actualInitProcessDiagramCanvasResult.annotationFontName);
    assertEquals("Arial", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    List definitionSet = processDiagramSVGGraphics2D.getDefinitionSet();
    assertTrue(definitionSet.isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minY);
    assertEquals(definitionSet, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getTexturePaintConverter().getDefinitionSet());
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  public void testInitProcessDiagramCanvas8() throws DOMException {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult = DefaultProcessDiagramGenerator
        .initProcessDiagramCanvas(bpmnModel, "Activity Font Name", "Label Font Name", null);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualInitProcessDiagramCanvasResult.g;
    Composite composite = processDiagramSVGGraphics2D.getComposite();
    assertTrue(composite instanceof AlphaComposite);
    Stroke stroke = processDiagramSVGGraphics2D.getStroke();
    assertTrue(stroke instanceof BasicStroke);
    Color background = processDiagramSVGGraphics2D.getBackground();
    ColorSpace colorSpace = background.getColorSpace();
    assertTrue(colorSpace instanceof ICC_ColorSpace);
    assertTrue(((ICC_ColorSpace) colorSpace).getProfile() instanceof ICC_ProfileRGB);
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node lastChild = root.getLastChild();
    assertTrue(((GenericElementNS) lastChild).getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    TypeInfo schemaTypeInfo = root.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo instanceof AbstractElement.ElementTypeInfo);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    TypeInfo schemaTypeInfo2 = topLevelGroup2.getSchemaTypeInfo();
    assertTrue(schemaTypeInfo2 instanceof AbstractElement.ElementTypeInfo);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement.getSchemaTypeInfo() instanceof AbstractElement.ElementTypeInfo);
    assertTrue(firstElementChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(genericDefinitions.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(root2.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(topLevelGroup.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes = root.getAttributes();
    assertTrue(attributes instanceof AbstractElement.NamedNodeHashMap);
    NamedNodeMap attributes2 = topLevelGroup2.getAttributes();
    assertTrue(attributes2 instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(documentElement.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    assertTrue(lastChild.getAttributes() instanceof AbstractElement.NamedNodeHashMap);
    Node firstChild = root2.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Node firstChild2 = root.getFirstChild();
    assertTrue(firstChild2 instanceof GenericComment);
    DOMImplementation implementation = dOMFactory.getImplementation();
    assertTrue(implementation instanceof GenericDOMImplementation);
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild2 = ((GenericElementNS) root2).getFirstElementChild();
    assertTrue(firstElementChild2 instanceof GenericElementNS);
    assertTrue(firstElementChild instanceof GenericElementNS);
    assertTrue(genericDefinitions instanceof GenericElementNS);
    assertTrue(root2 instanceof GenericElementNS);
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild2 = root2.getLastChild();
    assertTrue(lastChild2 instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    XBLManager xBLManager = ((GenericDocument) dOMFactory).getXBLManager();
    assertTrue(xBLManager instanceof GenericXBLManager);
    SVGGeneratorContext generatorContext = processDiagramSVGGraphics2D.getGeneratorContext();
    assertTrue(generatorContext.getErrorHandler() instanceof DefaultErrorHandler);
    ExtensionHandler extensionHandler = processDiagramSVGGraphics2D.getExtensionHandler();
    assertTrue(extensionHandler instanceof DefaultExtensionHandler);
    assertTrue(generatorContext.getStyleHandler() instanceof DefaultStyleHandler);
    ImageHandler imageHandler = processDiagramSVGGraphics2D.getImageHandler();
    assertTrue(imageHandler instanceof ImageHandlerBase64Encoder);
    assertTrue(processDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertEquals("", firstElementChild.getTextContent());
    assertEquals("", genericDefinitions.getTextContent());
    assertEquals("", root2.getTextContent());
    assertEquals("", topLevelGroup.getTextContent());
    assertEquals("", dOMFactory.getTextContent());
    assertEquals("", root.getTextContent());
    assertEquals("", topLevelGroup2.getTextContent());
    assertEquals("", documentElement.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals("#comment", firstChild2.getNodeName());
    assertEquals("#document", dOMFactory.getNodeName());
    assertEquals("1.0", dOMFactory.getXmlVersion());
    FontMetrics fontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Arial", actualInitProcessDiagramCanvasResult.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals("defs", firstElementChild.getTagName());
    assertEquals("defs", genericDefinitions.getTagName());
    assertEquals("defs", firstElementChild.getLocalName());
    assertEquals("defs", genericDefinitions.getLocalName());
    assertEquals("defs", firstElementChild.getNodeName());
    assertEquals("defs", genericDefinitions.getNodeName());
    assertEquals("g", ((GenericElementNS) lastChild).getTagName());
    assertEquals("g", topLevelGroup.getTagName());
    assertEquals("g", topLevelGroup2.getTagName());
    assertEquals("g", topLevelGroup.getLocalName());
    assertEquals("g", topLevelGroup2.getLocalName());
    assertEquals("g", lastChild.getLocalName());
    assertEquals("g", topLevelGroup.getNodeName());
    assertEquals("g", topLevelGroup2.getNodeName());
    assertEquals("g", lastChild.getNodeName());
    assertEquals("http://www.w3.org/2000/svg", firstElementChild.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", genericDefinitions.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", root.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", topLevelGroup2.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", documentElement.getNamespaceURI());
    assertEquals("http://www.w3.org/2000/svg", lastChild.getNamespaceURI());
    assertEquals("svg", root2.getTagName());
    assertEquals("svg", root.getTagName());
    assertEquals("svg", documentElement.getTagName());
    assertEquals("svg", root2.getLocalName());
    assertEquals("svg", root.getLocalName());
    assertEquals("svg", documentElement.getLocalName());
    assertEquals("svg", root2.getNodeName());
    assertEquals("svg", root.getNodeName());
    assertEquals("svg", documentElement.getNodeName());
    assertNull(((BasicStroke) stroke).getDashArray());
    assertNull(processDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(processDiagramSVGGraphics2D.getClipRect());
    assertNull(processDiagramSVGGraphics2D.getClipBounds());
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertNull(graphicContext.getClipBounds());
    assertNull(processDiagramSVGGraphics2D.getClip());
    assertNull(graphicContext.getClip());
    assertNull(((GenericComment) firstChild2).getManagerData());
    assertNull(((GenericDocument) dOMFactory).getManagerData());
    assertNull(((GenericElementNS) firstElementChild).getManagerData());
    assertNull(((GenericElementNS) genericDefinitions).getManagerData());
    assertNull(((GenericElementNS) root2).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup).getManagerData());
    assertNull(((GenericElementNS) root).getManagerData());
    assertNull(((GenericElementNS) topLevelGroup2).getManagerData());
    assertNull(((GenericElementNS) documentElement).getManagerData());
    assertNull(((GenericElementNS) lastChild).getManagerData());
    assertNull(dOMFactory.getDocumentURI());
    assertNull(dOMFactory.getInputEncoding());
    assertNull(dOMFactory.getXmlEncoding());
    assertNull(firstElementChild.getBaseURI());
    assertNull(genericDefinitions.getBaseURI());
    assertNull(root2.getBaseURI());
    assertNull(topLevelGroup.getBaseURI());
    assertNull(dOMFactory.getBaseURI());
    assertNull(root.getBaseURI());
    assertNull(topLevelGroup2.getBaseURI());
    assertNull(documentElement.getBaseURI());
    assertNull(firstChild2.getBaseURI());
    assertNull(lastChild.getBaseURI());
    assertNull(dOMFactory.getLocalName());
    assertNull(firstChild2.getLocalName());
    assertNull(dOMFactory.getNamespaceURI());
    assertNull(firstChild2.getNamespaceURI());
    assertNull(firstElementChild.getNodeValue());
    assertNull(genericDefinitions.getNodeValue());
    assertNull(root2.getNodeValue());
    assertNull(topLevelGroup.getNodeValue());
    assertNull(dOMFactory.getNodeValue());
    assertNull(root.getNodeValue());
    assertNull(topLevelGroup2.getNodeValue());
    assertNull(documentElement.getNodeValue());
    assertNull(lastChild.getNodeValue());
    assertNull(firstElementChild.getPrefix());
    assertNull(genericDefinitions.getPrefix());
    assertNull(root2.getPrefix());
    assertNull(topLevelGroup.getPrefix());
    assertNull(dOMFactory.getPrefix());
    assertNull(root.getPrefix());
    assertNull(topLevelGroup2.getPrefix());
    assertNull(documentElement.getPrefix());
    assertNull(firstChild2.getPrefix());
    assertNull(lastChild.getPrefix());
    assertNull(schemaTypeInfo.getTypeName());
    assertNull(schemaTypeInfo2.getTypeName());
    assertNull(schemaTypeInfo.getTypeNamespace());
    assertNull(schemaTypeInfo2.getTypeNamespace());
    assertNull(((GenericDOMImplementation) implementation).getLocale());
    assertNull(((GenericDocument) dOMFactory).getLocale());
    assertNull(((GenericComment) firstChild2).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getEventSupport());
    assertNull(((GenericElementNS) firstElementChild).getEventSupport());
    assertNull(((GenericElementNS) genericDefinitions).getEventSupport());
    assertNull(((GenericElementNS) root2).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup).getEventSupport());
    assertNull(((GenericElementNS) root).getEventSupport());
    assertNull(((GenericElementNS) topLevelGroup2).getEventSupport());
    assertNull(((GenericElementNS) documentElement).getEventSupport());
    assertNull(((GenericElementNS) lastChild).getEventSupport());
    assertNull(((GenericDocument) dOMFactory).getParentNodeEventTarget());
    assertNull(((GenericElementNS) genericDefinitions).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root2).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup).getParentNodeEventTarget());
    assertNull(((GenericElementNS) root).getParentNodeEventTarget());
    assertNull(((GenericElementNS) topLevelGroup2).getParentNodeEventTarget());
    assertNull(generatorContext.getGraphicContextDefaults());
    assertNull(dOMFactory.getOwnerDocument());
    assertNull(dOMFactory.getDoctype());
    assertNull(((GenericDocument) dOMFactory).getXblBoundElement());
    assertNull(((GenericDocument) dOMFactory).getXblNextElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousElementSibling());
    assertNull(((GenericDocument) dOMFactory).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getNextElementSibling());
    assertNull(((GenericElementNS) root2).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getNextElementSibling());
    assertNull(((GenericElementNS) root).getNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getNextElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getPreviousElementSibling());
    assertNull(((GenericElementNS) root).getPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblBoundElement());
    assertNull(((GenericElementNS) firstElementChild).getXblBoundElement());
    assertNull(((GenericElementNS) genericDefinitions).getXblBoundElement());
    assertNull(((GenericElementNS) root2).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup).getXblBoundElement());
    assertNull(((GenericElementNS) root).getXblBoundElement());
    assertNull(((GenericElementNS) topLevelGroup2).getXblBoundElement());
    assertNull(((GenericElementNS) documentElement).getXblBoundElement());
    assertNull(((GenericElementNS) lastChild).getXblBoundElement());
    assertNull(((GenericComment) firstChild2).getXblFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstElementChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextElementSibling());
    assertNull(((GenericElementNS) root2).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextElementSibling());
    assertNull(((GenericElementNS) root).getXblNextElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextElementSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextElementSibling());
    assertNull(((GenericComment) firstChild2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) firstElementChild).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) root).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousElementSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousElementSibling());
    assertNull(((GenericComment) firstChild2).getXblShadowTree());
    assertNull(((GenericElementNS) firstElementChild).getXblShadowTree());
    assertNull(((GenericElementNS) genericDefinitions).getXblShadowTree());
    assertNull(((GenericElementNS) root2).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup).getXblShadowTree());
    assertNull(((GenericElementNS) root).getXblShadowTree());
    assertNull(((GenericElementNS) topLevelGroup2).getXblShadowTree());
    assertNull(((GenericElementNS) documentElement).getXblShadowTree());
    assertNull(((GenericElementNS) lastChild).getXblShadowTree());
    assertNull(dOMFactory.getAttributes());
    assertNull(firstChild2.getAttributes());
    assertNull(((GenericDocument) dOMFactory).getXblNextSibling());
    assertNull(((GenericDocument) dOMFactory).getXblParentNode());
    assertNull(((GenericDocument) dOMFactory).getXblPreviousSibling());
    assertNull(((GenericComment) firstChild2).getXblFirstChild());
    assertNull(((GenericElementNS) firstElementChild).getXblFirstChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblFirstChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblFirstChild());
    assertNull(((GenericElementNS) documentElement).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblNextSibling());
    assertNull(((GenericElementNS) root2).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblNextSibling());
    assertNull(((GenericElementNS) root).getXblNextSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblNextSibling());
    assertNull(((GenericElementNS) documentElement).getXblNextSibling());
    assertNull(((GenericElementNS) lastChild).getXblNextSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblParentNode());
    assertNull(((GenericElementNS) root2).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup).getXblParentNode());
    assertNull(((GenericElementNS) root).getXblParentNode());
    assertNull(((GenericElementNS) topLevelGroup2).getXblParentNode());
    assertNull(((GenericComment) firstChild2).getXblPreviousSibling());
    assertNull(((GenericElementNS) genericDefinitions).getXblPreviousSibling());
    assertNull(((GenericElementNS) root2).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup).getXblPreviousSibling());
    assertNull(((GenericElementNS) root).getXblPreviousSibling());
    assertNull(((GenericElementNS) topLevelGroup2).getXblPreviousSibling());
    assertNull(((GenericElementNS) documentElement).getXblPreviousSibling());
    assertNull(firstElementChild.getFirstChild());
    assertNull(genericDefinitions.getFirstChild());
    assertNull(topLevelGroup.getFirstChild());
    assertNull(topLevelGroup2.getFirstChild());
    assertNull(documentElement.getFirstChild());
    assertNull(firstChild2.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(genericDefinitions.getNextSibling());
    assertNull(root2.getNextSibling());
    assertNull(topLevelGroup.getNextSibling());
    assertNull(dOMFactory.getNextSibling());
    assertNull(root.getNextSibling());
    assertNull(topLevelGroup2.getNextSibling());
    assertNull(documentElement.getNextSibling());
    assertNull(lastChild.getNextSibling());
    assertNull(genericDefinitions.getParentNode());
    assertNull(root2.getParentNode());
    assertNull(topLevelGroup.getParentNode());
    assertNull(dOMFactory.getParentNode());
    assertNull(root.getParentNode());
    assertNull(topLevelGroup2.getParentNode());
    assertNull(genericDefinitions.getPreviousSibling());
    assertNull(root2.getPreviousSibling());
    assertNull(topLevelGroup.getPreviousSibling());
    assertNull(dOMFactory.getPreviousSibling());
    assertNull(root.getPreviousSibling());
    assertNull(topLevelGroup2.getPreviousSibling());
    assertNull(documentElement.getPreviousSibling());
    assertNull(firstChild2.getPreviousSibling());
    assertEquals(0, ((BasicStroke) stroke).getLineJoin());
    Color darkerResult = background.darker();
    Color brighterResult = darkerResult.brighter();
    assertEquals(0, brighterResult.getAlpha());
    Color darkerResult2 = brighterResult.darker();
    assertEquals(0, darkerResult2.getAlpha());
    Color darkerResult3 = darkerResult.darker();
    Color darkerResult4 = darkerResult3.darker();
    assertEquals(0, darkerResult4.getAlpha());
    assertEquals(0, darkerResult3.getAlpha());
    assertEquals(0, darkerResult.getAlpha());
    assertEquals(0, background.getAlpha());
    assertEquals(0, font.getMissingGlyphCode());
    assertEquals(0, fontMetrics.getLeading());
    FontRenderContext fontRenderContext = fontMetrics.getFontRenderContext();
    assertEquals(0, fontRenderContext.getTransformType());
    FontRenderContext fontRenderContext2 = processDiagramSVGGraphics2D.getFontRenderContext();
    assertEquals(0, fontRenderContext2.getTransformType());
    AffineTransform transform = processDiagramSVGGraphics2D.getTransform();
    assertEquals(0, transform.getType());
    assertEquals(0, ((GenericElementNS) firstElementChild).getChildElementCount());
    assertEquals(0, ((GenericElementNS) genericDefinitions).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup).getChildElementCount());
    assertEquals(0, ((GenericElementNS) topLevelGroup2).getChildElementCount());
    assertEquals(0, ((GenericElementNS) documentElement).getChildElementCount());
    assertEquals(0, attributes2.getLength());
    int[] widths = fontMetrics.getWidths();
    assertEquals(0, widths[10]);
    assertEquals(0, widths[13]);
    assertEquals(0, widths[9]);
    assertEquals(0, graphicContext.getTransformStack().length);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(10, sVGCanvasSize.height);
    assertEquals(10, sVGCanvasSize.width);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertEquals(10.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(10.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(10.0f, ((BasicStroke) stroke).getMiterLimit(), 0.0f);
    assertEquals(11, font.getSize());
    assertEquals(11, fontMetrics.getAscent());
    assertEquals(11, fontMetrics.getMaxAscent());
    assertEquals(11.0f, font.getSize2D(), 0.0f);
    assertEquals(11645361, darkerResult2.getRGB());
    assertEquals(11711154, darkerResult.getRGB());
    assertEquals(124, darkerResult3.getBlue());
    assertEquals(124, darkerResult3.getGreen());
    assertEquals(124, darkerResult3.getRed());
    assertEquals(14, fontMetrics.getHeight());
    assertEquals(16711422, brighterResult.getRGB());
    assertEquals(16777215, background.getRGB());
    assertEquals(177, darkerResult2.getBlue());
    assertEquals(177, darkerResult2.getGreen());
    assertEquals(177, darkerResult2.getRed());
    assertEquals(178, darkerResult.getBlue());
    assertEquals(178, darkerResult.getGreen());
    assertEquals(178, darkerResult.getRed());
    assertEquals((short) 1, firstElementChild.getNodeType());
    assertEquals((short) 1, genericDefinitions.getNodeType());
    assertEquals((short) 1, root2.getNodeType());
    assertEquals((short) 1, topLevelGroup.getNodeType());
    assertEquals((short) 1, root.getNodeType());
    assertEquals((short) 1, topLevelGroup2.getNodeType());
    assertEquals((short) 1, documentElement.getNodeType());
    assertEquals((short) 1, lastChild.getNodeType());
    assertEquals(2, ((BasicStroke) stroke).getEndCap());
    assertEquals(2, brighterResult.getTransparency());
    assertEquals(2, darkerResult2.getTransparency());
    assertEquals(2, darkerResult4.getTransparency());
    assertEquals(2, darkerResult3.getTransparency());
    assertEquals(2, darkerResult.getTransparency());
    assertEquals(2, background.getTransparency());
    RenderingHints renderingHints = processDiagramSVGGraphics2D.getRenderingHints();
    assertEquals(2, renderingHints.size());
    assertEquals(2, ((GenericElementNS) root2).getChildElementCount());
    assertEquals(2, ((GenericElementNS) root).getChildElementCount());
    assertEquals(21, attributes.getLength());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(22, font.getAvailableAttributes().length);
    assertEquals(254, brighterResult.getBlue());
    assertEquals(254, brighterResult.getGreen());
    assertEquals(254, brighterResult.getRed());
    assertEquals(255, background.getBlue());
    assertEquals(255, background.getGreen());
    assertEquals(255, background.getRed());
    assertEquals(256, widths.length);
    assertEquals(3, ((AlphaComposite) composite).getRule());
    assertEquals(3, fontMetrics.getDescent());
    assertEquals(3, fontMetrics.getMaxDecent());
    assertEquals(3, fontMetrics.getMaxDescent());
    assertEquals(3, colorSpace.getNumComponents());
    assertEquals(4, generatorContext.getPrecision());
    assertEquals(4, widths[236]);
    assertEquals(4, widths[237]);
    assertEquals(4, widths[238]);
    assertEquals(4, widths[239]);
    assertEquals(47, ((GenericComment) firstChild2).getLength());
    assertEquals(5, colorSpace.getType());
    assertEquals(5658198, darkerResult4.getRGB());
    assertEquals(6196, font.getNumGlyphs());
    assertEquals(7, widths[0]);
    assertEquals(7, widths[1]);
    assertEquals(7, widths[11]);
    assertEquals(7, widths[12]);
    assertEquals(7, widths[14]);
    assertEquals(7, widths[15]);
    assertEquals(7, widths[17]);
    assertEquals(7, widths[18]);
    assertEquals(7, widths[19]);
    assertEquals(7, widths[2]);
    assertEquals(7, widths[20]);
    assertEquals(7, widths[21]);
    assertEquals(7, widths[22]);
    assertEquals(7, widths[23]);
    assertEquals(7, widths[231]);
    assertEquals(7, widths[253]);
    assertEquals(7, widths[255]);
    assertEquals(7, widths[3]);
    assertEquals(7, widths[4]);
    assertEquals(7, widths[5]);
    assertEquals(7, widths[6]);
    assertEquals(7, widths[7]);
    assertEquals(7, widths[8]);
    assertEquals(7, widths[Float.PRECISION]);
    assertEquals(7, widths[Short.SIZE]);
    assertEquals(8, font.getAttributes().size());
    assertEquals(8, widths[232]);
    assertEquals(8, widths[233]);
    assertEquals(8, widths[234]);
    assertEquals(8, widths[235]);
    assertEquals(8, widths[241]);
    assertEquals(8, widths[242]);
    assertEquals(8, widths[243]);
    assertEquals(8, widths[244]);
    assertEquals(8, widths[245]);
    assertEquals(8, widths[246]);
    assertEquals(8, widths[248]);
    assertEquals(8, widths[249]);
    assertEquals(8, widths[250]);
    assertEquals(8, widths[251]);
    assertEquals(8, widths[252]);
    assertEquals(8, widths[254]);
    assertEquals(8158332, darkerResult3.getRGB());
    assertEquals(86, darkerResult4.getBlue());
    assertEquals(86, darkerResult4.getGreen());
    assertEquals(86, darkerResult4.getRed());
    assertEquals((short) 8, firstChild2.getNodeType());
    assertEquals(9, widths[240]);
    assertEquals(9, widths[247]);
    assertEquals((short) 9, dOMFactory.getNodeType());
    assertFalse(font.hasLayoutAttributes());
    assertFalse(font.hasUniformLineMetrics());
    assertFalse(font.isItalic());
    assertFalse(font.isPlain());
    assertFalse(font.isTransformed());
    assertFalse(fontMetrics.hasUniformLineMetrics());
    assertFalse(fontRenderContext.isAntiAliased());
    assertFalse(fontRenderContext.isTransformed());
    assertFalse(fontRenderContext2.isTransformed());
    assertFalse(((GenericDocument) dOMFactory).getEventsEnabled());
    assertFalse(((GenericComment) firstChild2).isReadonly());
    assertFalse(((GenericDocument) dOMFactory).isReadonly());
    assertFalse(((GenericElementNS) firstElementChild).isReadonly());
    assertFalse(((GenericElementNS) genericDefinitions).isReadonly());
    assertFalse(((GenericElementNS) root2).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup).isReadonly());
    assertFalse(((GenericElementNS) root).isReadonly());
    assertFalse(((GenericElementNS) topLevelGroup2).isReadonly());
    assertFalse(((GenericElementNS) documentElement).isReadonly());
    assertFalse(((GenericElementNS) lastChild).isReadonly());
    assertFalse(xBLManager.isProcessing());
    assertFalse(generatorContext.isEmbeddedFontsOn());
    assertFalse(dOMFactory.getXmlStandalone());
    assertFalse(topLevelGroup.hasAttributes());
    assertFalse(dOMFactory.hasAttributes());
    assertFalse(topLevelGroup2.hasAttributes());
    assertFalse(documentElement.hasAttributes());
    assertFalse(firstChild2.hasAttributes());
    assertFalse(lastChild.hasAttributes());
    assertFalse(firstElementChild.hasChildNodes());
    assertFalse(genericDefinitions.hasChildNodes());
    assertFalse(topLevelGroup.hasChildNodes());
    assertFalse(topLevelGroup2.hasChildNodes());
    assertFalse(documentElement.hasChildNodes());
    assertFalse(firstChild2.hasChildNodes());
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
    assertTrue(font.isBold());
    assertTrue(colorSpace.isCS_sRGB());
    assertTrue(fontRenderContext2.isAntiAliased());
    assertTrue(transform.isIdentity());
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    assertTrue(graphicContextConverter.getClipConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getFontConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getHintsConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getStrokeConverter().getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getTransformConverter().getDefinitionSet().isEmpty());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertTrue(filterConverter.getConvolveOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getLookupOpConverter().getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getRescaleOpConverter().getDefinitionSet().isEmpty());
    assertTrue(dOMTreeManager.getDefinitionSet().isEmpty());
    assertTrue(filterConverter.getDefinitionSet().isEmpty());
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertTrue(compositeConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContextConverter.getDefinitionSet().isEmpty());
    List definitionSet = processDiagramSVGGraphics2D.getDefinitionSet();
    assertTrue(definitionSet.isEmpty());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertTrue(paintConverter.getDefinitionSet().isEmpty());
    assertTrue(graphicContext.isTransformStackValid());
    assertTrue(dOMFactory.getStrictErrorChecking());
    assertTrue(firstElementChild.hasAttributes());
    assertTrue(genericDefinitions.hasAttributes());
    assertTrue(root2.hasAttributes());
    assertTrue(root.hasAttributes());
    assertTrue(root2.hasChildNodes());
    assertTrue(dOMFactory.hasChildNodes());
    assertTrue(root.hasChildNodes());
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    assertEquals(background, brighterResult.brighter());
    assertEquals(background, background.brighter());
    assertEquals(fontRenderContext2, graphicContext.getFontRenderContext());
    assertEquals(transform, font.getTransform());
    assertEquals(transform, fontRenderContext.getTransform());
    assertEquals(transform, fontRenderContext2.getTransform());
    assertEquals(transform, graphicContext.getTransform());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minY);
    assertEquals(definitionSet, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(definitionSet, paintConverter.getTexturePaintConverter().getDefinitionSet());
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(composite, graphicContext.getComposite());
    assertSame(stroke, graphicContext.getStroke());
    assertSame(colorSpace, brighterResult.getColorSpace());
    assertSame(colorSpace, darkerResult2.getColorSpace());
    assertSame(colorSpace, darkerResult4.getColorSpace());
    assertSame(colorSpace, darkerResult3.getColorSpace());
    assertSame(colorSpace, darkerResult.getColorSpace());
    assertSame(renderingHints, graphicContext.getRenderingHints());
    assertSame(firstChild, ((GenericElementNS) root2).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild2, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild2, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, generatorContext.getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild2.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild2, ((GenericElementNS) root2).getXblFirstElementChild());
    assertSame(root, ((GenericComment) firstChild2).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild2).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild2.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(lastChild.getFirstChild(), ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(lastChild.getFirstChild(), lastChild.getLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllArtifacts(BpmnModel)}
   */
  @Test
  public void testGatherAllArtifacts() {
    // Arrange and Act
    List<Artifact> actualGatherAllArtifactsResult = DefaultProcessDiagramGenerator.gatherAllArtifacts(new BpmnModel());

    // Assert
    assertTrue(actualGatherAllArtifactsResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllArtifacts(BpmnModel)}
   */
  @Test
  public void testGatherAllArtifacts2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act
    List<Artifact> actualGatherAllArtifactsResult = DefaultProcessDiagramGenerator.gatherAllArtifacts(bpmnModel);

    // Assert
    assertTrue(actualGatherAllArtifactsResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   */
  @Test
  public void testGatherAllFlowNodes() {
    // Arrange and Act
    List<FlowNode> actualGatherAllFlowNodesResult = DefaultProcessDiagramGenerator.gatherAllFlowNodes(new BpmnModel());

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   */
  @Test
  public void testGatherAllFlowNodes2() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act
    List<FlowNode> actualGatherAllFlowNodesResult = DefaultProcessDiagramGenerator.gatherAllFlowNodes(bpmnModel);

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   */
  @Test
  public void testGatherAllFlowNodes3() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess element = new AdhocSubProcess();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    List<FlowNode> actualGatherAllFlowNodesResult = DefaultProcessDiagramGenerator.gatherAllFlowNodes(bpmnModel);

    // Assert
    assertEquals(1, actualGatherAllFlowNodesResult.size());
    assertSame(element, actualGatherAllFlowNodesResult.get(0));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   */
  @Test
  public void testGatherAllFlowNodes4() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    List<FlowNode> actualGatherAllFlowNodesResult = DefaultProcessDiagramGenerator.gatherAllFlowNodes(bpmnModel);

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   */
  @Test
  public void testGatherAllFlowNodes5() {
    // Arrange and Act
    List<FlowNode> actualGatherAllFlowNodesResult = DefaultProcessDiagramGenerator
        .gatherAllFlowNodes(new AdhocSubProcess());

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   */
  @Test
  public void testGatherAllFlowNodes6() {
    // Arrange and Act
    List<FlowNode> actualGatherAllFlowNodesResult = DefaultProcessDiagramGenerator.gatherAllFlowNodes(new Process());

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   */
  @Test
  public void testGatherAllFlowNodes7() {
    // Arrange
    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    AdhocSubProcess element = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element);

    // Act
    List<FlowNode> actualGatherAllFlowNodesResult = DefaultProcessDiagramGenerator
        .gatherAllFlowNodes(flowElementsContainer);

    // Assert
    assertEquals(1, actualGatherAllFlowNodesResult.size());
    assertSame(element, actualGatherAllFlowNodesResult.get(0));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   */
  @Test
  public void testGatherAllFlowNodes8() {
    // Arrange
    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(new BooleanDataObject());

    // Act
    List<FlowNode> actualGatherAllFlowNodesResult = DefaultProcessDiagramGenerator
        .gatherAllFlowNodes(flowElementsContainer);

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link DefaultProcessDiagramGenerator#setActivityDrawInstructions(Map)}
   *   <li>{@link DefaultProcessDiagramGenerator#setArtifactDrawInstructions(Map)}
   *   <li>{@link DefaultProcessDiagramGenerator#getActivityDrawInstructions()}
   *   <li>{@link DefaultProcessDiagramGenerator#getArtifactDrawInstructions()}
   *   <li>{@link DefaultProcessDiagramGenerator#getDefaultActivityFontName()}
   *   <li>{@link DefaultProcessDiagramGenerator#getDefaultAnnotationFontName()}
   *   <li>{@link DefaultProcessDiagramGenerator#getDefaultDiagramImageFileName()}
   *   <li>{@link DefaultProcessDiagramGenerator#getDefaultLabelFontName()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    HashMap<Class<? extends BaseElement>, DefaultProcessDiagramGenerator.ActivityDrawInstruction> activityDrawInstructions = new HashMap<>();

    // Act
    defaultProcessDiagramGenerator.setActivityDrawInstructions(activityDrawInstructions);
    HashMap<Class<? extends BaseElement>, DefaultProcessDiagramGenerator.ArtifactDrawInstruction> artifactDrawInstructions = new HashMap<>();
    defaultProcessDiagramGenerator.setArtifactDrawInstructions(artifactDrawInstructions);
    Map<Class<? extends BaseElement>, DefaultProcessDiagramGenerator.ActivityDrawInstruction> actualActivityDrawInstructions = defaultProcessDiagramGenerator
        .getActivityDrawInstructions();
    Map<Class<? extends BaseElement>, DefaultProcessDiagramGenerator.ArtifactDrawInstruction> actualArtifactDrawInstructions = defaultProcessDiagramGenerator
        .getArtifactDrawInstructions();
    String actualDefaultActivityFontName = defaultProcessDiagramGenerator.getDefaultActivityFontName();
    String actualDefaultAnnotationFontName = defaultProcessDiagramGenerator.getDefaultAnnotationFontName();
    String actualDefaultDiagramImageFileName = defaultProcessDiagramGenerator.getDefaultDiagramImageFileName();

    // Assert that nothing has changed
    assertEquals("/image/na.svg", actualDefaultDiagramImageFileName);
    assertEquals("Arial", actualDefaultActivityFontName);
    assertEquals("Arial", actualDefaultAnnotationFontName);
    assertEquals("Arial", defaultProcessDiagramGenerator.getDefaultLabelFontName());
    assertTrue(actualActivityDrawInstructions.isEmpty());
    assertTrue(actualArtifactDrawInstructions.isEmpty());
    assertSame(activityDrawInstructions, actualActivityDrawInstructions);
    assertSame(artifactDrawInstructions, actualArtifactDrawInstructions);
  }
}
