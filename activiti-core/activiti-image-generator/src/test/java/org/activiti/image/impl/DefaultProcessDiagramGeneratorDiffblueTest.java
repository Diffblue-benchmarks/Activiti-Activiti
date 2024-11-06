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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.font.FontRenderContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.Activity;
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
import org.activiti.bpmn.model.SubProcess;
import org.activiti.bpmn.model.Task;
import org.activiti.bpmn.model.TextAnnotation;
import org.activiti.image.exception.ActivitiImageException;
import org.activiti.image.exception.ActivitiInterchangeInfoNotFoundException;
import org.apache.batik.dom.GenericComment;
import org.apache.batik.dom.GenericDocument;
import org.apache.batik.dom.GenericElementNS;
import org.apache.batik.ext.awt.g2d.GraphicContext;
import org.apache.batik.svggen.DOMTreeManager;
import org.apache.batik.svggen.SVGBufferedImageOp;
import org.apache.batik.svggen.SVGComposite;
import org.apache.batik.svggen.SVGGraphicContextConverter;
import org.apache.batik.svggen.SVGPaint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class DefaultProcessDiagramGeneratorDiffblueTest {
  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   * with {@code bpmnModel}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, String, String, String) with 'bpmnModel', 'activityFontName', 'labelFontName', 'annotationFontName'")
  void testGenerateDiagramWithBpmnModelActivityFontNameLabelFontNameAnnotationFontName() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    // Act and Assert
    assertThrows(ActivitiInterchangeInfoNotFoundException.class, () -> defaultProcessDiagramGenerator
        .generateDiagram(new BpmnModel(), "Activity Font Name", "Label Font Name", "Annotation Font Name"));
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   * with {@code bpmnModel}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, String, String, String) with 'bpmnModel', 'activityFontName', 'labelFontName', 'annotationFontName'")
  void testGenerateDiagramWithBpmnModelActivityFontNameLabelFontNameAnnotationFontName2() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   * with {@code bpmnModel}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, String, String, String) with 'bpmnModel', 'activityFontName', 'labelFontName', 'annotationFontName'")
  void testGenerateDiagramWithBpmnModelActivityFontNameLabelFontNameAnnotationFontName3() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   * with {@code bpmnModel}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, String, String, String) with 'bpmnModel', 'activityFontName', 'labelFontName', 'annotationFontName'")
  void testGenerateDiagramWithBpmnModelActivityFontNameLabelFontNameAnnotationFontName4() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   * with {@code bpmnModel}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, String, String, String) with 'bpmnModel', 'activityFontName', 'labelFontName', 'annotationFontName'")
  void testGenerateDiagramWithBpmnModelActivityFontNameLabelFontNameAnnotationFontName5() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(0.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   * with {@code bpmnModel}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, String, String, String) with 'bpmnModel', 'activityFontName', 'labelFontName', 'annotationFontName'")
  void testGenerateDiagramWithBpmnModelActivityFontNameLabelFontNameAnnotationFontName6() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(0.0d, getResult.getY());
    assertEquals(2.0d, getResult.getX());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   * with {@code bpmnModel}, {@code highLightedActivities}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List) with 'bpmnModel', 'highLightedActivities'")
  void testGenerateDiagramWithBpmnModelHighLightedActivities() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();

    // Act and Assert
    assertThrows(ActivitiInterchangeInfoNotFoundException.class,
        () -> defaultProcessDiagramGenerator.generateDiagram(bpmnModel, new ArrayList<>()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   * with {@code bpmnModel}, {@code highLightedActivities}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List) with 'bpmnModel', 'highLightedActivities'")
  void testGenerateDiagramWithBpmnModelHighLightedActivities2() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   * with {@code bpmnModel}, {@code highLightedActivities}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List) with 'bpmnModel', 'highLightedActivities'")
  void testGenerateDiagramWithBpmnModelHighLightedActivities3() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(0.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   * with {@code bpmnModel}, {@code highLightedActivities}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List) with 'bpmnModel', 'highLightedActivities'")
  void testGenerateDiagramWithBpmnModelHighLightedActivities4() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(0.0d, getResult.getY());
    assertEquals(2.0d, getResult.getX());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlows() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(ActivitiInterchangeInfoNotFoundException.class,
        () -> defaultProcessDiagramGenerator.generateDiagram(bpmnModel, highLightedActivities, new ArrayList<>()));
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlows2() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlows3() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlows4() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlows5() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(0.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlows6() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(0.0d, getResult.getY());
    assertEquals(2.0d, getResult.getX());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, String, String, String) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'activityFontName', 'labelFontName', 'annotationFontName'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontName() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, String, String, String) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'activityFontName', 'labelFontName', 'annotationFontName'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontName2()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, String, String, String) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'activityFontName', 'labelFontName', 'annotationFontName'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontName3()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, String, String, String) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'activityFontName', 'labelFontName', 'annotationFontName'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontName4()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, String, String, String) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'activityFontName', 'labelFontName', 'annotationFontName'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontName5()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(0.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, String, String, String) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'activityFontName', 'labelFontName', 'annotationFontName'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontName6()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(0.0d, getResult.getY());
    assertEquals(2.0d, getResult.getX());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}, {@code generateDefaultDiagram}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, String, String, String, boolean) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'activityFontName', 'labelFontName', 'annotationFontName', 'generateDefaultDiagram'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagram()
      throws IOException {
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
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE svg".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}, {@code generateDefaultDiagram}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, String, String, String, boolean) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'activityFontName', 'labelFontName', 'annotationFontName', 'generateDefaultDiagram'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagram2()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("/image/na.svg");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}, {@code generateDefaultDiagram}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, String, String, String, boolean) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'activityFontName', 'labelFontName', 'annotationFontName', 'generateDefaultDiagram'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagram3()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("/image/na.svg");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}, {@code generateDefaultDiagram}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, String, String, String, boolean) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'activityFontName', 'labelFontName', 'annotationFontName', 'generateDefaultDiagram'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagram4()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("/image/na.svg");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}, {@code generateDefaultDiagram}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, String, String, String, boolean) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'activityFontName', 'labelFontName', 'annotationFontName', 'generateDefaultDiagram'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagram5()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("/image/na.svg");
    assertEquals(0.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}, {@code generateDefaultDiagram}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String, String, String, boolean)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, String, String, String, boolean) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'activityFontName', 'labelFontName', 'annotationFontName', 'generateDefaultDiagram'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagram6()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("/image/na.svg");
    assertEquals(0.0d, getResult.getY());
    assertEquals(2.0d, getResult.getX());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code currentActivities},
   * {@code erroredActivities}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}, {@code generateDefaultDiagram},
   * {@code defaultDiagramImageFileName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'currentActivities', 'erroredActivities', 'activityFontName', 'labelFontName', 'annotationFontName', 'generateDefaultDiagram', 'defaultDiagramImageFileName'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsCurrentActivitiesErroredActivitiesActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagramDefaultDiagramImageFileName() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code currentActivities},
   * {@code erroredActivities}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}, {@code generateDefaultDiagram},
   * {@code defaultDiagramImageFileName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'currentActivities', 'erroredActivities', 'activityFontName', 'labelFontName', 'annotationFontName', 'generateDefaultDiagram', 'defaultDiagramImageFileName'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsCurrentActivitiesErroredActivitiesActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagramDefaultDiagramImageFileName2()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("Key");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code currentActivities},
   * {@code erroredActivities}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}, {@code generateDefaultDiagram},
   * {@code defaultDiagramImageFileName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'currentActivities', 'erroredActivities', 'activityFontName', 'labelFontName', 'annotationFontName', 'generateDefaultDiagram', 'defaultDiagramImageFileName'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsCurrentActivitiesErroredActivitiesActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagramDefaultDiagramImageFileName3()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("Key");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code currentActivities},
   * {@code erroredActivities}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}, {@code generateDefaultDiagram},
   * {@code defaultDiagramImageFileName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'currentActivities', 'erroredActivities', 'activityFontName', 'labelFontName', 'annotationFontName', 'generateDefaultDiagram', 'defaultDiagramImageFileName'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsCurrentActivitiesErroredActivitiesActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagramDefaultDiagramImageFileName4()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("Key");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code currentActivities},
   * {@code erroredActivities}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}, {@code generateDefaultDiagram},
   * {@code defaultDiagramImageFileName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'currentActivities', 'erroredActivities', 'activityFontName', 'labelFontName', 'annotationFontName', 'generateDefaultDiagram', 'defaultDiagramImageFileName'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsCurrentActivitiesErroredActivitiesActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagramDefaultDiagramImageFileName5()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("Key");
    assertEquals(0.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   * with {@code bpmnModel}, {@code highLightedActivities},
   * {@code highLightedFlows}, {@code currentActivities},
   * {@code erroredActivities}, {@code activityFontName}, {@code labelFontName},
   * {@code annotationFontName}, {@code generateDefaultDiagram},
   * {@code defaultDiagramImageFileName}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String) with 'bpmnModel', 'highLightedActivities', 'highLightedFlows', 'currentActivities', 'erroredActivities', 'activityFontName', 'labelFontName', 'annotationFontName', 'generateDefaultDiagram', 'defaultDiagramImageFileName'")
  void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsCurrentActivitiesErroredActivitiesActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagramDefaultDiagramImageFileName6()
      throws IOException {
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
    GraphicInfo getResult = locationMap.get("Key");
    assertEquals(0.0d, getResult.getY());
    assertEquals(2.0d, getResult.getX());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   * with {@code bpmnModel}, {@code highLightedActivities}.
   * <ul>
   *   <li>Given {@code http://www.w3.org/2000/svg}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List) with 'bpmnModel', 'highLightedActivities'; given 'http://www.w3.org/2000/svg'")
  void testGenerateDiagramWithBpmnModelHighLightedActivities_givenHttpWwwW3Org2000Svg() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   * with {@code bpmnModel}, {@code highLightedActivities}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  @DisplayName("Test generateDiagram(BpmnModel, List) with 'bpmnModel', 'highLightedActivities'; given Process (default constructor)")
  void testGenerateDiagramWithBpmnModelHighLightedActivities_givenProcess() throws IOException {
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
    GraphicInfo getResult = locationMap.get("No interchange information found.");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateDiagramResult.read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getDefaultDiagram(String)}.
   * <ul>
   *   <li>When {@code foo.txt}.</li>
   *   <li>Then throw {@link ActivitiImageException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#getDefaultDiagram(String)}
   */
  @Test
  @DisplayName("Test getDefaultDiagram(String); when 'foo.txt'; then throw ActivitiImageException")
  void testGetDefaultDiagram_whenFooTxt_thenThrowActivitiImageException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiImageException.class,
        () -> (new DefaultProcessDiagramGenerator()).getDefaultDiagram("foo.txt"));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getDefaultDiagram(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return read is fifty-one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#getDefaultDiagram(String)}
   */
  @Test
  @DisplayName("Test getDefaultDiagram(String); when 'null'; then return read is fifty-one")
  void testGetDefaultDiagram_whenNull_thenReturnReadIsFiftyOne() throws IOException {
    // Arrange, Act and Assert
    byte[] byteArray = new byte[51];
    assertEquals(51, (new DefaultProcessDiagramGenerator()).getDefaultDiagram(null).read(byteArray));
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE svg".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}
   */
  @Test
  @DisplayName("Test generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String); given ArrayList()")
  void testGenerateProcessDiagram_givenArrayList() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    bpmnModel.addFlowGraphicInfoList("Arial", graphicInfoList);
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act and Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramGenerator.generateProcessDiagram(
        bpmnModel, highLightedActivities, highLightedFlows, currentActivities, new ArrayList<>(), "Activity Font Name",
        "Label Font Name", "Annotation Font Name").g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertEquals(graphicInfoList, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(graphicInfoList, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(graphicInfoList, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(graphicInfoList, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(graphicInfoList, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(graphicInfoList, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(graphicInfoList, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertEquals(graphicInfoList, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(graphicInfoList, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(graphicInfoList, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(graphicInfoList, paintConverter.getTexturePaintConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(graphicInfoList, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(graphicInfoList, filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(graphicInfoList, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(graphicInfoList, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(graphicInfoList, dOMTreeManager.getDefinitionSet());
    assertEquals(graphicInfoList, filterConverter.getDefinitionSet());
    assertEquals(graphicInfoList, compositeConverter.getDefinitionSet());
    assertEquals(graphicInfoList, graphicContextConverter.getDefinitionSet());
    assertEquals(graphicInfoList, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(graphicInfoList, paintConverter.getDefinitionSet());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) X is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}
   */
  @Test
  @DisplayName("Test generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String); given GraphicInfo (default constructor) X is '-0.5'")
  void testGenerateProcessDiagram_givenGraphicInfoXIs05() {
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

    // Act and Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramGenerator.generateProcessDiagram(
        bpmnModel, highLightedActivities, highLightedFlows, currentActivities, new ArrayList<>(), "Activity Font Name",
        "Label Font Name", "Annotation Font Name").g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertEquals(highLightedActivities, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertEquals(highLightedActivities, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getTexturePaintConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(highLightedActivities, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, dOMTreeManager.getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getDefinitionSet());
    assertEquals(highLightedActivities, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getDefinitionSet());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) X is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}
   */
  @Test
  @DisplayName("Test generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String); given GraphicInfo (default constructor) X is two")
  void testGenerateProcessDiagram_givenGraphicInfoXIsTwo() {
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

    // Act and Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramGenerator.generateProcessDiagram(
        bpmnModel, highLightedActivities, highLightedFlows, currentActivities, new ArrayList<>(), "Activity Font Name",
        "Label Font Name", "Annotation Font Name").g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertEquals(highLightedActivities, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertEquals(highLightedActivities, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getTexturePaintConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(highLightedActivities, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, dOMTreeManager.getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getDefinitionSet());
    assertEquals(highLightedActivities, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getDefinitionSet());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Y is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}
   */
  @Test
  @DisplayName("Test generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String); given GraphicInfo (default constructor) Y is '-0.5'")
  void testGenerateProcessDiagram_givenGraphicInfoYIs05() {
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

    // Act and Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramGenerator.generateProcessDiagram(
        bpmnModel, highLightedActivities, highLightedFlows, currentActivities, new ArrayList<>(), "Activity Font Name",
        "Label Font Name", "Annotation Font Name").g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertEquals(highLightedActivities, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertEquals(highLightedActivities, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getTexturePaintConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(highLightedActivities, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, dOMTreeManager.getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getDefinitionSet());
    assertEquals(highLightedActivities, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getDefinitionSet());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>When {@link BpmnModel} (default constructor) addProcess {@link Process}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}
   */
  @Test
  @DisplayName("Test generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String); given Process (default constructor); when BpmnModel (default constructor) addProcess Process (default constructor)")
  void testGenerateProcessDiagram_givenProcess_whenBpmnModelAddProcessProcess() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act and Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramGenerator.generateProcessDiagram(
        bpmnModel, highLightedActivities, highLightedFlows, currentActivities, new ArrayList<>(), "Activity Font Name",
        "Label Font Name", "Annotation Font Name").g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertEquals(highLightedActivities, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertEquals(highLightedActivities, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getTexturePaintConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(highLightedActivities, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, dOMTreeManager.getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getDefinitionSet());
    assertEquals(highLightedActivities, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getDefinitionSet());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)}
   */
  @Test
  @DisplayName("Test generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String); when BpmnModel (default constructor)")
  void testGenerateProcessDiagram_whenBpmnModel() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator = new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act and Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramGenerator.generateProcessDiagram(
        bpmnModel, highLightedActivities, highLightedFlows, currentActivities, new ArrayList<>(), "Activity Font Name",
        "Label Font Name", "Annotation Font Name").g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter = dOMTreeManager.getGraphicContextConverter();
    SVGComposite compositeConverter = graphicContextConverter.getCompositeConverter();
    assertEquals(highLightedActivities, compositeConverter.getAlphaCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getCustomCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGPaint paintConverter = graphicContextConverter.getPaintConverter();
    assertEquals(highLightedActivities, paintConverter.getColorConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getCustomPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getGradientPaintConverter().getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getTexturePaintConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(highLightedActivities, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, dOMTreeManager.getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getDefinitionSet());
    assertEquals(highLightedActivities, compositeConverter.getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getDefinitionSet());
    assertEquals(highLightedActivities, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(highLightedActivities, paintConverter.getDefinitionSet());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) X is {@code -0.5}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) LocationMap {@code Key} X is
   * zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}
   */
  @Test
  @DisplayName("Test prepareBpmnModel(BpmnModel); given GraphicInfo (default constructor) X is '-0.5'; then BpmnModel (default constructor) LocationMap 'Key' X is zero")
  void testPrepareBpmnModel_givenGraphicInfoXIs05_thenBpmnModelLocationMapKeyXIsZero() {
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
    GraphicInfo getResult = locationMap.get("Key");
    assertEquals(0.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) X is two.</li>
   *   <li>Then {@link BpmnModel} (default constructor) LocationMap {@code Key} X is
   * two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}
   */
  @Test
  @DisplayName("Test prepareBpmnModel(BpmnModel); given GraphicInfo (default constructor) X is two; then BpmnModel (default constructor) LocationMap 'Key' X is two")
  void testPrepareBpmnModel_givenGraphicInfoXIsTwo_thenBpmnModelLocationMapKeyXIsTwo() {
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
    GraphicInfo getResult = locationMap.get("Key");
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Y is {@code -0.5}.</li>
   *   <li>Then {@link BpmnModel} (default constructor) LocationMap {@code Key} Y is
   * zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}
   */
  @Test
  @DisplayName("Test prepareBpmnModel(BpmnModel); given GraphicInfo (default constructor) Y is '-0.5'; then BpmnModel (default constructor) LocationMap 'Key' Y is zero")
  void testPrepareBpmnModel_givenGraphicInfoYIs05_thenBpmnModelLocationMapKeyYIsZero() {
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
    GraphicInfo getResult = locationMap.get("Key");
    assertEquals(0.0d, getResult.getY());
    assertEquals(2.0d, getResult.getX());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  @DisplayName("Test drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List); given AdhocSubProcess (default constructor)")
  void testDrawActivity_givenAdhocSubProcess() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  @DisplayName("Test drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List); given AdhocSubProcess (default constructor)")
  void testDrawActivity_givenAdhocSubProcess2() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} {@link BaseElement#getId()} return
   * {@code 42}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  @DisplayName("Test drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List); given AdhocSubProcess getId() return '42'; then calls getId()")
  void testDrawActivity_givenAdhocSubProcessGetIdReturn42_thenCallsGetId() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AdhocSubProcess} (default
   * constructor).</li>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  @DisplayName("Test drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List); given ArrayList() add AdhocSubProcess (default constructor); when BpmnModel (default constructor)")
  void testDrawActivity_givenArrayListAddAdhocSubProcess_whenBpmnModel() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}.
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.</li>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then calls {@link SubProcess#getFlowElements()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  @DisplayName("Test drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List); given ArrayList(); when BpmnModel (default constructor); then calls getFlowElements()")
  void testDrawActivity_givenArrayList_whenBpmnModel_thenCallsGetFlowElements() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}.
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  @DisplayName("Test drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List); given BusinessRuleTask (default constructor)")
  void testDrawActivity_givenBusinessRuleTask() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>When {@link AdhocSubProcess} {@link Activity#getDefaultFlow()} return
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  @DisplayName("Test drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List); given 'null'; when AdhocSubProcess getDefaultFlow() return 'null'")
  void testDrawActivity_givenNull_whenAdhocSubProcessGetDefaultFlowReturnNull() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>When {@link BpmnModel} (default constructor) addProcess {@link Process}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  @DisplayName("Test drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List); given Process (default constructor); when BpmnModel (default constructor) addProcess Process (default constructor)")
  void testDrawActivity_givenProcess_whenBpmnModelAddProcessProcess() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}.
   * <ul>
   *   <li>Given {@link SequenceFlow} {@link BaseElement#getId()} return
   * {@code 42}.</li>
   *   <li>Then calls {@link FlowElement#getName()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  @DisplayName("Test drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List); given SequenceFlow getId() return '42'; then calls getName()")
  void testDrawActivity_givenSequenceFlowGetIdReturn42_thenCallsGetName() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}.
   * <ul>
   *   <li>Then throw {@link ActivitiInterchangeInfoNotFoundException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  @DisplayName("Test drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List); then throw ActivitiInterchangeInfoNotFoundException")
  void testDrawActivity_thenThrowActivitiInterchangeInfoNotFoundException() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then calls {@link Activity#getDefaultFlow()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)}
   */
  @Test
  @DisplayName("Test drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List); when BpmnModel (default constructor); then calls getDefaultFlow()")
  void testDrawActivity_whenBpmnModel_thenCallsGetDefaultFlow() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Expanded is
   * {@code false}.</li>
   *   <li>Then return size is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List); given GraphicInfo (default constructor) Expanded is 'false'; then return size is two")
  void testConnectionPerfectionizer_givenGraphicInfoExpandedIsFalse_thenReturnSizeIsTwo() {
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
    GraphicInfo getResult = actualConnectionPerfectionizerResult.get(0);
    assertEquals(0.5d, getResult.getHeight());
    assertEquals(0.5d, getResult.getWidth());
    assertEquals(1, getResult.getXmlColumnNumber());
    assertEquals(1, getResult.getXmlRowNumber());
    assertEquals(10.0d, getResult.getX());
    assertEquals(10.0d, getResult.getY());
    assertFalse(getResult.getExpanded());
    assertSame(graphicInfo, actualConnectionPerfectionizerResult.get(1));
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List); then return size is one")
  void testConnectionPerfectionizer_thenReturnSizeIsOne() {
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
    GraphicInfo getResult = actualConnectionPerfectionizerResult.get(0);
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlRowNumber());
    assertEquals(10.0d, getResult.getHeight());
    assertEquals(10.0d, getResult.getWidth());
    assertEquals(2.0d, getResult.getX());
    assertEquals(3.0d, getResult.getY());
    assertTrue(getResult.getExpanded());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List); when ArrayList(); then return Empty")
  void testConnectionPerfectionizer_whenArrayList_thenReturnEmpty() {
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
   * Test {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}.
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  @DisplayName("Test getShapeType(BaseElement); when ActivitiListener (default constructor); then return 'null'")
  void testGetShapeType_whenActivitiListener_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(DefaultProcessDiagramGenerator.getShapeType(new ActivitiListener()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then return {@code Rectangle}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  @DisplayName("Test getShapeType(BaseElement); when AdhocSubProcess (default constructor); then return 'Rectangle'")
  void testGetShapeType_whenAdhocSubProcess_thenReturnRectangle() {
    // Arrange, Act and Assert
    assertEquals(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
        DefaultProcessDiagramGenerator.getShapeType(new AdhocSubProcess()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}.
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).</li>
   *   <li>Then return {@code Ellipse}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  @DisplayName("Test getShapeType(BaseElement); when BoundaryEvent (default constructor); then return 'Ellipse'")
  void testGetShapeType_whenBoundaryEvent_thenReturnEllipse() {
    // Arrange, Act and Assert
    assertEquals(DefaultProcessDiagramCanvas.SHAPE_TYPE.Ellipse,
        DefaultProcessDiagramGenerator.getShapeType(new BoundaryEvent()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}.
   * <ul>
   *   <li>When {@link ComplexGateway} (default constructor).</li>
   *   <li>Then return {@code Rhombus}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  @DisplayName("Test getShapeType(BaseElement); when ComplexGateway (default constructor); then return 'Rhombus'")
  void testGetShapeType_whenComplexGateway_thenReturnRhombus() {
    // Arrange, Act and Assert
    assertEquals(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rhombus,
        DefaultProcessDiagramGenerator.getShapeType(new ComplexGateway()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}.
   * <ul>
   *   <li>When {@link Task} (default constructor).</li>
   *   <li>Then return {@code Rectangle}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  @DisplayName("Test getShapeType(BaseElement); when Task (default constructor); then return 'Rectangle'")
  void testGetShapeType_whenTask_thenReturnRectangle() {
    // Arrange, Act and Assert
    assertEquals(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
        DefaultProcessDiagramGenerator.getShapeType(new Task()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}.
   * <ul>
   *   <li>When {@link TextAnnotation} (default constructor).</li>
   *   <li>Then return {@code Rectangle}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  @DisplayName("Test getShapeType(BaseElement); when TextAnnotation (default constructor); then return 'Rectangle'")
  void testGetShapeType_whenTextAnnotation_thenReturnRectangle() {
    // Arrange, Act and Assert
    assertEquals(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
        DefaultProcessDiagramGenerator.getShapeType(new TextAnnotation()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getLineCenter(List)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Expanded is
   * {@code false}.</li>
   *   <li>Then return Y is {@code 2.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramGenerator#getLineCenter(List)}
   */
  @Test
  @DisplayName("Test getLineCenter(List); given GraphicInfo (default constructor) Expanded is 'false'; then return Y is '2.5'")
  void testGetLineCenter_givenGraphicInfoExpandedIsFalse_thenReturnYIs25() {
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
    assertEquals(0.0d, actualLineCenter.getHeight());
    assertEquals(0.0d, actualLineCenter.getWidth());
    assertEquals(2.5d, actualLineCenter.getY());
    assertEquals(6.0d, actualLineCenter.getX());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getLineCenter(List)}.
   * <ul>
   *   <li>Then return Y is {@code 2.3245883961385942}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramGenerator#getLineCenter(List)}
   */
  @Test
  @DisplayName("Test getLineCenter(List); then return Y is '2.3245883961385942'")
  void testGetLineCenter_thenReturnYIs23245883961385942() {
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
    assertEquals(0.0d, actualLineCenter.getHeight());
    assertEquals(0.0d, actualLineCenter.getWidth());
    assertEquals(2.3245883961385942d, actualLineCenter.getY());
    assertEquals(7.403292830891248d, actualLineCenter.getX());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @DisplayName("Test drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)")
  void testDrawArtifact() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @DisplayName("Test drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)")
  void testDrawArtifact2() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @DisplayName("Test drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact); given AdhocSubProcess (default constructor)")
  void testDrawArtifact_givenAdhocSubProcess() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}.
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).</li>
   *   <li>Then calls
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @DisplayName("Test drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact); given AdhocSubProcess (default constructor); then calls connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)")
  void testDrawArtifact_givenAdhocSubProcess_thenCallsConnectionPerfectionizer() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}.
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @DisplayName("Test drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact); given BusinessRuleTask (default constructor)")
  void testDrawArtifact_givenBusinessRuleTask() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}.
   * <ul>
   *   <li>Given {@link BusinessRuleTask} {@link BaseElement#getId()} return
   * {@code 42}.</li>
   *   <li>Then calls {@link BaseElement#getId()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @DisplayName("Test drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact); given BusinessRuleTask getId() return '42'; then calls getId()")
  void testDrawArtifact_givenBusinessRuleTaskGetIdReturn42_thenCallsGetId() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}.
   * <ul>
   *   <li>Given {@code null}.</li>
   *   <li>Then calls {@link BpmnModel#getArtifact(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @DisplayName("Test drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact); given 'null'; then calls getArtifact(String)")
  void testDrawArtifact_givenNull_thenCallsGetArtifact() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}.
   * <ul>
   *   <li>When {@link DefaultProcessDiagramCanvas}
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   * does nothing.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @DisplayName("Test drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact); when DefaultProcessDiagramCanvas drawTextAnnotation(String, String, GraphicInfo) does nothing")
  void testDrawArtifact_whenDefaultProcessDiagramCanvasDrawTextAnnotationDoesNothing() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addArtifact
   * {@link Association} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test initProcessDiagramCanvas(BpmnModel, String, String, String); given Process (default constructor) addArtifact Association (default constructor)")
  void testInitProcessDiagramCanvas_givenProcessAddArtifactAssociation() {
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
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node firstChild = root.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild instanceof GenericElementNS);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions instanceof GenericElementNS);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2 instanceof GenericElementNS);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    Font expectedFont = actualInitProcessDiagramCanvasResult.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minY);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(firstChild, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, processDiagramSVGGraphics2D.getGeneratorContext().getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) root).getXblFirstElementChild());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextElementSibling());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousSibling());
    assertSame(firstElementChild, firstChild.getNextSibling());
    assertSame(firstElementChild, lastChild.getPreviousSibling());
    assertSame(root, ((GenericComment) firstChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement
   * {@link BooleanDataObject} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test initProcessDiagramCanvas(BpmnModel, String, String, String); given Process (default constructor) addFlowElement BooleanDataObject (default constructor)")
  void testInitProcessDiagramCanvas_givenProcessAddFlowElementBooleanDataObject() {
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
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node firstChild = root.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild instanceof GenericElementNS);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions instanceof GenericElementNS);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2 instanceof GenericElementNS);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minY);
    Font expectedFont = actualInitProcessDiagramCanvasResult.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(firstChild, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, processDiagramSVGGraphics2D.getGeneratorContext().getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) root).getXblFirstElementChild());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextElementSibling());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousSibling());
    assertSame(firstElementChild, firstChild.getNextSibling());
    assertSame(firstElementChild, lastChild.getPreviousSibling());
    assertSame(root, ((GenericComment) firstChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#minX} is zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test initProcessDiagramCanvas(BpmnModel, String, String, String); given Process (default constructor); then return minX is zero")
  void testInitProcessDiagramCanvas_givenProcess_thenReturnMinXIsZero() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult = DefaultProcessDiagramGenerator
        .initProcessDiagramCanvas(bpmnModel, "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualInitProcessDiagramCanvasResult.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node firstChild = root.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild instanceof GenericElementNS);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions instanceof GenericElementNS);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2 instanceof GenericElementNS);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minY);
    Font expectedFont = actualInitProcessDiagramCanvasResult.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(firstChild, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, processDiagramSVGGraphics2D.getGeneratorContext().getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) root).getXblFirstElementChild());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextElementSibling());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousSibling());
    assertSame(firstElementChild, firstChild.getNextSibling());
    assertSame(firstElementChild, lastChild.getPreviousSibling());
    assertSame(root, ((GenericComment) firstChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}.
   * <ul>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#minX} is
   * {@link Integer#MAX_VALUE}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test initProcessDiagramCanvas(BpmnModel, String, String, String); then return minX is MAX_VALUE")
  void testInitProcessDiagramCanvas_thenReturnMinXIsMax_value() {
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
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node firstChild = root.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild instanceof GenericElementNS);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions instanceof GenericElementNS);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2 instanceof GenericElementNS);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    Font expectedFont = actualInitProcessDiagramCanvasResult.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minY);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(firstChild, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, processDiagramSVGGraphics2D.getGeneratorContext().getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) root).getXblFirstElementChild());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextElementSibling());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousSibling());
    assertSame(firstElementChild, firstChild.getNextSibling());
    assertSame(firstElementChild, lastChild.getPreviousSibling());
    assertSame(root, ((GenericComment) firstChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#minX} is zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test initProcessDiagramCanvas(BpmnModel, String, String, String); when BpmnModel (default constructor); then return minX is zero")
  void testInitProcessDiagramCanvas_whenBpmnModel_thenReturnMinXIsZero() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult = DefaultProcessDiagramGenerator
        .initProcessDiagramCanvas(new BpmnModel(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualInitProcessDiagramCanvasResult.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node firstChild = root.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild instanceof GenericElementNS);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions instanceof GenericElementNS);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2 instanceof GenericElementNS);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minY);
    Font expectedFont = actualInitProcessDiagramCanvasResult.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(firstChild, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, processDiagramSVGGraphics2D.getGeneratorContext().getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) root).getXblFirstElementChild());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextElementSibling());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousSibling());
    assertSame(firstElementChild, firstChild.getNextSibling());
    assertSame(firstElementChild, lastChild.getPreviousSibling());
    assertSame(root, ((GenericComment) firstChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#annotationFontName} is
   * {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test initProcessDiagramCanvas(BpmnModel, String, String, String); when 'null'; then return annotationFontName is 'Arial'")
  void testInitProcessDiagramCanvas_whenNull_thenReturnAnnotationFontNameIsArial() {
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
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node firstChild = root.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild instanceof GenericElementNS);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions instanceof GenericElementNS);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2 instanceof GenericElementNS);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals("Arial", actualInitProcessDiagramCanvasResult.annotationFontName);
    Font expectedFont = actualInitProcessDiagramCanvasResult.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(firstChild, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, processDiagramSVGGraphics2D.getGeneratorContext().getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) root).getXblFirstElementChild());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextElementSibling());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousSibling());
    assertSame(firstElementChild, firstChild.getNextSibling());
    assertSame(firstElementChild, lastChild.getPreviousSibling());
    assertSame(root, ((GenericComment) firstChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#fontMetrics} Font Family
   * is {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test initProcessDiagramCanvas(BpmnModel, String, String, String); when 'null'; then return fontMetrics Font Family is 'Arial'")
  void testInitProcessDiagramCanvas_whenNull_thenReturnFontMetricsFontFamilyIsArial() {
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
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node firstChild = root.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild instanceof GenericElementNS);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions instanceof GenericElementNS);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2 instanceof GenericElementNS);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    FontMetrics fontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Arial", font.getFamily());
    assertEquals("Arial", font.getName());
    assertEquals("Arial", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Arial-BoldMT", font.getFontName());
    assertEquals("Arial-BoldMT", font.getPSName());
    assertEquals(10, fontMetrics.getAscent());
    assertEquals(10, fontMetrics.getMaxAscent());
    assertEquals(13, fontMetrics.getHeight());
    assertEquals(22, fontMetrics.getMaxAdvance());
    int[] widths = fontMetrics.getWidths();
    assertEquals(256, widths.length);
    assertEquals(3, widths[10]);
    assertEquals(3, widths[13]);
    assertEquals(3, widths[236]);
    assertEquals(3, widths[237]);
    assertEquals(3, widths[238]);
    assertEquals(3, widths[239]);
    assertEquals(3, widths[9]);
    assertEquals(3381, font.getNumGlyphs());
    assertEquals(6, widths[247]);
    assertEquals(8, widths[0]);
    Font font2 = processDiagramSVGGraphics2D.getFont();
    assertEquals(font, font2);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(firstChild, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, processDiagramSVGGraphics2D.getGeneratorContext().getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) root).getXblFirstElementChild());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextElementSibling());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousSibling());
    assertSame(firstElementChild, firstChild.getNextSibling());
    assertSame(firstElementChild, lastChild.getPreviousSibling());
    assertSame(root, ((GenericComment) firstChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#labelFontName} is
   * {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String, String)}
   */
  @Test
  @DisplayName("Test initProcessDiagramCanvas(BpmnModel, String, String, String); when 'null'; then return labelFontName is 'Arial'")
  void testInitProcessDiagramCanvas_whenNull_thenReturnLabelFontNameIsArial() {
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
    Element root = processDiagramSVGGraphics2D.getRoot();
    Node firstChild = root.getFirstChild();
    assertTrue(firstChild instanceof GenericComment);
    Document dOMFactory = processDiagramSVGGraphics2D.getDOMFactory();
    assertTrue(dOMFactory instanceof GenericDocument);
    Element firstElementChild = ((GenericElementNS) root).getFirstElementChild();
    assertTrue(firstElementChild instanceof GenericElementNS);
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions instanceof GenericElementNS);
    Element root2 = dOMTreeManager.getRoot();
    assertTrue(root2 instanceof GenericElementNS);
    Element topLevelGroup = dOMTreeManager.getTopLevelGroup();
    assertTrue(topLevelGroup instanceof GenericElementNS);
    assertTrue(root instanceof GenericElementNS);
    Element topLevelGroup2 = processDiagramSVGGraphics2D.getTopLevelGroup();
    assertTrue(topLevelGroup2 instanceof GenericElementNS);
    Element documentElement = dOMFactory.getDocumentElement();
    assertTrue(documentElement instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals("Arial", actualInitProcessDiagramCanvasResult.labelFontName);
    Font expectedFont = actualInitProcessDiagramCanvasResult.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualInitProcessDiagramCanvasResult.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualInitProcessDiagramCanvasResult.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
    assertSame(firstChild, ((GenericElementNS) root).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) firstElementChild).getXblPreviousSibling());
    assertSame(firstChild, firstElementChild.getPreviousSibling());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getParentNodeEventTarget());
    assertSame(dOMFactory, ((GenericElementNS) documentElement).getXblParentNode());
    assertSame(dOMFactory, processDiagramSVGGraphics2D.getGeneratorContext().getDOMFactory());
    assertSame(dOMFactory, firstElementChild.getOwnerDocument());
    assertSame(dOMFactory, genericDefinitions.getOwnerDocument());
    assertSame(dOMFactory, root2.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup.getOwnerDocument());
    assertSame(dOMFactory, root.getOwnerDocument());
    assertSame(dOMFactory, topLevelGroup2.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getOwnerDocument());
    assertSame(dOMFactory, firstChild.getOwnerDocument());
    assertSame(dOMFactory, lastChild.getOwnerDocument());
    assertSame(dOMFactory, documentElement.getParentNode());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) root).getXblFirstElementChild());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextElementSibling());
    assertSame(firstElementChild, ((GenericComment) firstChild).getXblNextSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousElementSibling());
    assertSame(firstElementChild, ((GenericElementNS) lastChild).getXblPreviousSibling());
    assertSame(firstElementChild, firstChild.getNextSibling());
    assertSame(firstElementChild, lastChild.getPreviousSibling());
    assertSame(root, ((GenericComment) firstChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) firstElementChild).getParentNodeEventTarget());
    assertSame(root, ((GenericElementNS) lastChild).getParentNodeEventTarget());
    assertSame(root, ((GenericComment) firstChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) firstElementChild).getXblParentNode());
    assertSame(root, ((GenericElementNS) lastChild).getXblParentNode());
    assertSame(root, firstElementChild.getParentNode());
    assertSame(root, firstChild.getParentNode());
    assertSame(root, lastChild.getParentNode());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblFirstElementChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastChild());
    assertSame(documentElement, ((GenericDocument) dOMFactory).getXblLastElementChild());
    assertSame(documentElement, dOMFactory.getFirstChild());
    assertSame(documentElement, dOMFactory.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllArtifacts(BpmnModel)}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>When {@link BpmnModel} (default constructor) addProcess {@link Process}
   * (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllArtifacts(BpmnModel)}
   */
  @Test
  @DisplayName("Test gatherAllArtifacts(BpmnModel); given Process (default constructor); when BpmnModel (default constructor) addProcess Process (default constructor)")
  void testGatherAllArtifacts_givenProcess_whenBpmnModelAddProcessProcess() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act
    List<Artifact> actualGatherAllArtifactsResult = DefaultProcessDiagramGenerator.gatherAllArtifacts(bpmnModel);

    // Assert
    assertTrue(actualGatherAllArtifactsResult.isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllArtifacts(BpmnModel)}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllArtifacts(BpmnModel)}
   */
  @Test
  @DisplayName("Test gatherAllArtifacts(BpmnModel); when BpmnModel (default constructor); then return Empty")
  void testGatherAllArtifacts_whenBpmnModel_thenReturnEmpty() {
    // Arrange and Act
    List<Artifact> actualGatherAllArtifactsResult = DefaultProcessDiagramGenerator.gatherAllArtifacts(new BpmnModel());

    // Assert
    assertTrue(actualGatherAllArtifactsResult.isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   * with {@code bpmnModel}.
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement
   * {@link BooleanDataObject} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   */
  @Test
  @DisplayName("Test gatherAllFlowNodes(BpmnModel) with 'bpmnModel'; given Process (default constructor) addFlowElement BooleanDataObject (default constructor)")
  void testGatherAllFlowNodesWithBpmnModel_givenProcessAddFlowElementBooleanDataObject() {
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
   * Test {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   * with {@code bpmnModel}.
   * <ul>
   *   <li>Given {@link Process} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   */
  @Test
  @DisplayName("Test gatherAllFlowNodes(BpmnModel) with 'bpmnModel'; given Process (default constructor); then return Empty")
  void testGatherAllFlowNodesWithBpmnModel_givenProcess_thenReturnEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act
    List<FlowNode> actualGatherAllFlowNodesResult = DefaultProcessDiagramGenerator.gatherAllFlowNodes(bpmnModel);

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   * with {@code bpmnModel}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   */
  @Test
  @DisplayName("Test gatherAllFlowNodes(BpmnModel) with 'bpmnModel'; then return size is one")
  void testGatherAllFlowNodesWithBpmnModel_thenReturnSizeIsOne() {
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
   * Test {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   * with {@code bpmnModel}.
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   */
  @Test
  @DisplayName("Test gatherAllFlowNodes(BpmnModel) with 'bpmnModel'; when BpmnModel (default constructor); then return Empty")
  void testGatherAllFlowNodesWithBpmnModel_whenBpmnModel_thenReturnEmpty() {
    // Arrange and Act
    List<FlowNode> actualGatherAllFlowNodesResult = DefaultProcessDiagramGenerator.gatherAllFlowNodes(new BpmnModel());

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   * with {@code flowElementsContainer}.
   * <ul>
   *   <li>Given {@link BooleanDataObject} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test gatherAllFlowNodes(FlowElementsContainer) with 'flowElementsContainer'; given BooleanDataObject (default constructor)")
  void testGatherAllFlowNodesWithFlowElementsContainer_givenBooleanDataObject() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   * with {@code flowElementsContainer}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test gatherAllFlowNodes(FlowElementsContainer) with 'flowElementsContainer'; then return size is one")
  void testGatherAllFlowNodesWithFlowElementsContainer_thenReturnSizeIsOne() {
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
   * Test
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   * with {@code flowElementsContainer}.
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test gatherAllFlowNodes(FlowElementsContainer) with 'flowElementsContainer'; when AdhocSubProcess (default constructor)")
  void testGatherAllFlowNodesWithFlowElementsContainer_whenAdhocSubProcess() {
    // Arrange and Act
    List<FlowNode> actualGatherAllFlowNodesResult = DefaultProcessDiagramGenerator
        .gatherAllFlowNodes(new AdhocSubProcess());

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   * with {@code flowElementsContainer}.
   * <ul>
   *   <li>When {@link Process} (default constructor).</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   */
  @Test
  @DisplayName("Test gatherAllFlowNodes(FlowElementsContainer) with 'flowElementsContainer'; when Process (default constructor); then return Empty")
  void testGatherAllFlowNodesWithFlowElementsContainer_whenProcess_thenReturnEmpty() {
    // Arrange and Act
    List<FlowNode> actualGatherAllFlowNodesResult = DefaultProcessDiagramGenerator.gatherAllFlowNodes(new Process());

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
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
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
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
