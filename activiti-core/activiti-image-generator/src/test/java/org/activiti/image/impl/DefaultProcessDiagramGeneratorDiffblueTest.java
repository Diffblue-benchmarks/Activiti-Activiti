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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.Collection;
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
import org.activiti.bpmn.model.Message;
import org.activiti.bpmn.model.Message.Builder;
import org.activiti.bpmn.model.Pool;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.Task;
import org.activiti.bpmn.model.TextAnnotation;
import org.activiti.image.exception.ActivitiImageException;
import org.activiti.image.exception.ActivitiInterchangeInfoNotFoundException;
import org.activiti.image.impl.DefaultProcessDiagramCanvas.SHAPE_TYPE;
import org.activiti.image.impl.DefaultProcessDiagramGenerator.ActivityDrawInstruction;
import org.activiti.image.impl.DefaultProcessDiagramGenerator.ArtifactDrawInstruction;
import org.apache.batik.dom.GenericElementNS;
import org.apache.batik.svggen.DOMTreeManager;
import org.apache.batik.svggen.SVGBufferedImageOp;
import org.apache.batik.svggen.SVGGraphicContextConverter;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DefaultProcessDiagramGeneratorDiffblueTest {
  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String, String, String)}
   * with {@code bpmnModel}, {@code activityFontName}, {@code labelFontName}, {@code
   * annotationFontName}.
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, String,
   * String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.io.InputStream DefaultProcessDiagramGenerator.generateDiagram(BpmnModel, String, String, String)"
  })
  public void testGenerateDiagramWithBpmnModelActivityFontNameLabelFontNameAnnotationFontName() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    // Act and Assert
    assertThrows(
        ActivitiInterchangeInfoNotFoundException.class,
        () ->
            defaultProcessDiagramGenerator.generateDiagram(
                new BpmnModel(), "Activity Font Name", "Label Font Name", "Annotation Font Name"));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)} with {@code
   * bpmnModel}, {@code highLightedActivities}.
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.io.InputStream DefaultProcessDiagramGenerator.generateDiagram(BpmnModel, List)"
  })
  public void testGenerateDiagramWithBpmnModelHighLightedActivities() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();

    // Act and Assert
    assertThrows(
        ActivitiInterchangeInfoNotFoundException.class,
        () -> defaultProcessDiagramGenerator.generateDiagram(bpmnModel, new ArrayList<>()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List)} with {@code
   * bpmnModel}, {@code highLightedActivities}, {@code highLightedFlows}.
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List,
   * List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.io.InputStream DefaultProcessDiagramGenerator.generateDiagram(BpmnModel, List, List)"
  })
  public void testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlows() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(
        ActivitiInterchangeInfoNotFoundException.class,
        () ->
            defaultProcessDiagramGenerator.generateDiagram(
                bpmnModel, highLightedActivities, new ArrayList<>()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String,
   * String, String)} with {@code bpmnModel}, {@code highLightedActivities}, {@code
   * highLightedFlows}, {@code activityFontName}, {@code labelFontName}, {@code annotationFontName}.
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List,
   * List, String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.io.InputStream DefaultProcessDiagramGenerator.generateDiagram(BpmnModel, List, List, String, String, String)"
  })
  public void
      testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontName() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(
        ActivitiInterchangeInfoNotFoundException.class,
        () ->
            defaultProcessDiagramGenerator.generateDiagram(
                bpmnModel,
                highLightedActivities,
                new ArrayList<>(),
                "Activity Font Name",
                "Label Font Name",
                "Annotation Font Name"));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String,
   * String, String, boolean)} with {@code bpmnModel}, {@code highLightedActivities}, {@code
   * highLightedFlows}, {@code activityFontName}, {@code labelFontName}, {@code annotationFontName},
   * {@code generateDefaultDiagram}.
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List,
   * List, String, String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.io.InputStream DefaultProcessDiagramGenerator.generateDiagram(BpmnModel, List, List, String, String, String, boolean)"
  })
  public void
      testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagram() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLocationMap())
        .thenThrow(new ActivitiInterchangeInfoNotFoundException("An error occurred"));
    when(bpmnModel.hasDiagramInterchangeInfo()).thenReturn(true);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(
        ActivitiInterchangeInfoNotFoundException.class,
        () ->
            defaultProcessDiagramGenerator.generateDiagram(
                bpmnModel,
                highLightedActivities,
                new ArrayList<>(),
                "Activity Font Name",
                "Label Font Name",
                "Annotation Font Name",
                true));
    verify(bpmnModel).getLocationMap();
    verify(bpmnModel).hasDiagramInterchangeInfo();
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, String,
   * String, String, boolean)} with {@code bpmnModel}, {@code highLightedActivities}, {@code
   * highLightedFlows}, {@code activityFontName}, {@code labelFontName}, {@code annotationFontName},
   * {@code generateDefaultDiagram}.
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List,
   * List, String, String, String, boolean)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.io.InputStream DefaultProcessDiagramGenerator.generateDiagram(BpmnModel, List, List, String, String, String, boolean)"
  })
  public void
      testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagram2() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    ArrayList<Pool> poolList = new ArrayList<>();
    poolList.add(new Pool());

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any()))
        .thenThrow(new ActivitiInterchangeInfoNotFoundException("An error occurred"));
    when(bpmnModel.getPools()).thenReturn(poolList);
    when(bpmnModel.getFlowLocationMap()).thenReturn(new HashMap<>());
    when(bpmnModel.getLabelLocationMap()).thenReturn(new HashMap<>());
    when(bpmnModel.getLocationMap()).thenReturn(new HashMap<>());
    when(bpmnModel.hasDiagramInterchangeInfo()).thenReturn(true);
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(
        ActivitiInterchangeInfoNotFoundException.class,
        () ->
            defaultProcessDiagramGenerator.generateDiagram(
                bpmnModel,
                highLightedActivities,
                new ArrayList<>(),
                "Activity Font Name",
                "Label Font Name",
                "Annotation Font Name",
                true));
    verify(bpmnModel, atLeast(1)).getFlowLocationMap();
    verify(bpmnModel).getGraphicInfo(null);
    verify(bpmnModel, atLeast(1)).getLabelLocationMap();
    verify(bpmnModel, atLeast(1)).getLocationMap();
    verify(bpmnModel).getPools();
    verify(bpmnModel).hasDiagramInterchangeInfo();
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List, List, List, List,
   * String, String, String, boolean, String)} with {@code bpmnModel}, {@code
   * highLightedActivities}, {@code highLightedFlows}, {@code currentActivities}, {@code
   * erroredActivities}, {@code activityFontName}, {@code labelFontName}, {@code
   * annotationFontName}, {@code generateDefaultDiagram}, {@code defaultDiagramImageFileName}.
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateDiagram(BpmnModel, List,
   * List, List, List, String, String, String, boolean, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.io.InputStream DefaultProcessDiagramGenerator.generateDiagram(BpmnModel, List, List, List, List, String, String, String, boolean, String)"
  })
  public void
      testGenerateDiagramWithBpmnModelHighLightedActivitiesHighLightedFlowsCurrentActivitiesErroredActivitiesActivityFontNameLabelFontNameAnnotationFontNameGenerateDefaultDiagramDefaultDiagramImageFileName() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(
        ActivitiImageException.class,
        () ->
            defaultProcessDiagramGenerator.generateDiagram(
                bpmnModel,
                highLightedActivities,
                highLightedFlows,
                currentActivities,
                new ArrayList<>(),
                "Activity Font Name",
                "Label Font Name",
                "Annotation Font Name",
                true,
                "foo.txt"));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getDefaultDiagram(String)}.
   *
   * <ul>
   *   <li>When {@code foo.txt}.
   *   <li>Then throw {@link ActivitiImageException}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#getDefaultDiagram(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.io.InputStream DefaultProcessDiagramGenerator.getDefaultDiagram(String)"
  })
  public void testGetDefaultDiagram_whenFooTxt_thenThrowActivitiImageException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiImageException.class,
        () -> new DefaultProcessDiagramGenerator().getDefaultDiagram("foo.txt"));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List,
   * List, String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel,
   * List, List, List, List, String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultProcessDiagramCanvas DefaultProcessDiagramGenerator.generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)"
  })
  public void testGenerateProcessDiagram_givenArrayList() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    bpmnModel.addFlowGraphicInfoList("Arial", graphicInfoList);
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act and Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D =
        defaultProcessDiagramGenerator.generateProcessDiagram(
                bpmnModel,
                highLightedActivities,
                highLightedFlows,
                currentActivities,
                new ArrayList<>(),
                "Activity Font Name",
                "Label Font Name",
                "Annotation Font Name")
            .g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter =
        dOMTreeManager.getGraphicContextConverter();
    assertEquals(graphicInfoList, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(graphicInfoList, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(graphicInfoList, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(graphicInfoList, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(
        graphicInfoList, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(graphicInfoList, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(
        graphicInfoList, filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(graphicInfoList, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(graphicInfoList, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(graphicInfoList, dOMTreeManager.getDefinitionSet());
    assertEquals(graphicInfoList, filterConverter.getDefinitionSet());
    assertEquals(
        graphicInfoList, graphicContextConverter.getCompositeConverter().getDefinitionSet());
    assertEquals(graphicInfoList, graphicContextConverter.getDefinitionSet());
    assertEquals(graphicInfoList, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(graphicInfoList, graphicContextConverter.getPaintConverter().getDefinitionSet());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List,
   * List, String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) X is {@code -0.5}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel,
   * List, List, List, List, String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultProcessDiagramCanvas DefaultProcessDiagramGenerator.generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)"
  })
  public void testGenerateProcessDiagram_givenGraphicInfoXIs05() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

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
    DefaultProcessDiagramCanvas actualGenerateProcessDiagramResult =
        defaultProcessDiagramGenerator.generateProcessDiagram(
            bpmnModel,
            highLightedActivities,
            highLightedFlows,
            currentActivities,
            new ArrayList<>(),
            "Activity Font Name",
            "Label Font Name",
            "Annotation Font Name");

    // Assert
    assertEquals(0, actualGenerateProcessDiagramResult.minX);
    assertEquals(0, actualGenerateProcessDiagramResult.minY);
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualGenerateProcessDiagramResult.g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter =
        dOMTreeManager.getGraphicContextConverter();
    assertEquals(
        highLightedActivities, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(
        highLightedActivities, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities,
        filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, dOMTreeManager.getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getDefinitionSet());
    assertEquals(highLightedActivities, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getPaintConverter().getDefinitionSet());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List,
   * List, String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) X is two.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel,
   * List, List, List, List, String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultProcessDiagramCanvas DefaultProcessDiagramGenerator.generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)"
  })
  public void testGenerateProcessDiagram_givenGraphicInfoXIsTwo() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

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
    DefaultProcessDiagramCanvas actualGenerateProcessDiagramResult =
        defaultProcessDiagramGenerator.generateProcessDiagram(
            bpmnModel,
            highLightedActivities,
            highLightedFlows,
            currentActivities,
            new ArrayList<>(),
            "Activity Font Name",
            "Label Font Name",
            "Annotation Font Name");

    // Assert
    assertEquals(0, actualGenerateProcessDiagramResult.minX);
    assertEquals(0, actualGenerateProcessDiagramResult.minY);
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualGenerateProcessDiagramResult.g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter =
        dOMTreeManager.getGraphicContextConverter();
    assertEquals(
        highLightedActivities, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(
        highLightedActivities, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities,
        filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, dOMTreeManager.getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getDefinitionSet());
    assertEquals(highLightedActivities, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getPaintConverter().getDefinitionSet());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List,
   * List, String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Y is {@code -0.5}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel,
   * List, List, List, List, String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultProcessDiagramCanvas DefaultProcessDiagramGenerator.generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)"
  })
  public void testGenerateProcessDiagram_givenGraphicInfoYIs05() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

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
    DefaultProcessDiagramCanvas actualGenerateProcessDiagramResult =
        defaultProcessDiagramGenerator.generateProcessDiagram(
            bpmnModel,
            highLightedActivities,
            highLightedFlows,
            currentActivities,
            new ArrayList<>(),
            "Activity Font Name",
            "Label Font Name",
            "Annotation Font Name");

    // Assert
    assertEquals(0, actualGenerateProcessDiagramResult.minX);
    assertEquals(0, actualGenerateProcessDiagramResult.minY);
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualGenerateProcessDiagramResult.g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter =
        dOMTreeManager.getGraphicContextConverter();
    assertEquals(
        highLightedActivities, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(
        highLightedActivities, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities,
        filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, dOMTreeManager.getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getDefinitionSet());
    assertEquals(highLightedActivities, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getPaintConverter().getDefinitionSet());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List,
   * List, String, String, String)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>When {@link BpmnModel} (default constructor) addProcess {@link Process} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel,
   * List, List, List, List, String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultProcessDiagramCanvas DefaultProcessDiagramGenerator.generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)"
  })
  public void testGenerateProcessDiagram_givenProcess_whenBpmnModelAddProcessProcess() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    DefaultProcessDiagramCanvas actualGenerateProcessDiagramResult =
        defaultProcessDiagramGenerator.generateProcessDiagram(
            bpmnModel,
            highLightedActivities,
            highLightedFlows,
            currentActivities,
            new ArrayList<>(),
            "Activity Font Name",
            "Label Font Name",
            "Annotation Font Name");

    // Assert
    assertEquals(0, actualGenerateProcessDiagramResult.minX);
    assertEquals(0, actualGenerateProcessDiagramResult.minY);
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualGenerateProcessDiagramResult.g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter =
        dOMTreeManager.getGraphicContextConverter();
    assertEquals(
        highLightedActivities, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(
        highLightedActivities, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities,
        filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, dOMTreeManager.getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getDefinitionSet());
    assertEquals(highLightedActivities, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getPaintConverter().getDefinitionSet());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List,
   * List, String, String, String)}.
   *
   * <ul>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#minX} is {@link Integer#MAX_VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel,
   * List, List, List, List, String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultProcessDiagramCanvas DefaultProcessDiagramGenerator.generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)"
  })
  public void testGenerateProcessDiagram_thenReturnMinXIsMax_value() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    DefaultProcessDiagramCanvas actualGenerateProcessDiagramResult =
        defaultProcessDiagramGenerator.generateProcessDiagram(
            bpmnModel,
            highLightedActivities,
            highLightedFlows,
            currentActivities,
            new ArrayList<>(),
            "Activity Font Name",
            "Label Font Name",
            "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualGenerateProcessDiagramResult.g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter =
        dOMTreeManager.getGraphicContextConverter();
    assertEquals(
        highLightedActivities, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(
        highLightedActivities, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities,
        filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, dOMTreeManager.getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getDefinitionSet());
    assertEquals(highLightedActivities, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getPaintConverter().getDefinitionSet());
    assertEquals(Integer.MAX_VALUE, actualGenerateProcessDiagramResult.minX);
    assertEquals(Integer.MAX_VALUE, actualGenerateProcessDiagramResult.minY);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel, List, List, List,
   * List, String, String, String)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   *   <li>Then return {@link DefaultProcessDiagramCanvas#minX} is zero.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#generateProcessDiagram(BpmnModel,
   * List, List, List, List, String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultProcessDiagramCanvas DefaultProcessDiagramGenerator.generateProcessDiagram(BpmnModel, List, List, List, List, String, String, String)"
  })
  public void testGenerateProcessDiagram_whenBpmnModel_thenReturnMinXIsZero() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    BpmnModel bpmnModel = new BpmnModel();
    ArrayList<String> highLightedActivities = new ArrayList<>();
    ArrayList<String> highLightedFlows = new ArrayList<>();
    ArrayList<String> currentActivities = new ArrayList<>();

    // Act
    DefaultProcessDiagramCanvas actualGenerateProcessDiagramResult =
        defaultProcessDiagramGenerator.generateProcessDiagram(
            bpmnModel,
            highLightedActivities,
            highLightedFlows,
            currentActivities,
            new ArrayList<>(),
            "Activity Font Name",
            "Label Font Name",
            "Annotation Font Name");

    // Assert
    assertEquals(0, actualGenerateProcessDiagramResult.minX);
    assertEquals(0, actualGenerateProcessDiagramResult.minY);
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualGenerateProcessDiagramResult.g;
    DOMTreeManager dOMTreeManager = processDiagramSVGGraphics2D.getDOMTreeManager();
    SVGGraphicContextConverter graphicContextConverter =
        dOMTreeManager.getGraphicContextConverter();
    assertEquals(
        highLightedActivities, graphicContextConverter.getClipConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getFontConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getHintsConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getStrokeConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getTransformConverter().getDefinitionSet());
    SVGBufferedImageOp filterConverter = dOMTreeManager.getFilterConverter();
    assertEquals(
        highLightedActivities, filterConverter.getConvolveOpConverter().getDefinitionSet());
    assertEquals(
        highLightedActivities,
        filterConverter.getCustomBufferedImageOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getLookupOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getRescaleOpConverter().getDefinitionSet());
    assertEquals(highLightedActivities, dOMTreeManager.getDefinitionSet());
    assertEquals(highLightedActivities, filterConverter.getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getCompositeConverter().getDefinitionSet());
    assertEquals(highLightedActivities, graphicContextConverter.getDefinitionSet());
    assertEquals(highLightedActivities, processDiagramSVGGraphics2D.getDefinitionSet());
    assertEquals(
        highLightedActivities, graphicContextConverter.getPaintConverter().getDefinitionSet());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) X is {@code -0.5}.
   *   <li>Then {@link BpmnModel} (default constructor) LocationMap {@code Key} X is zero.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultProcessDiagramGenerator.prepareBpmnModel(BpmnModel)"})
  public void testPrepareBpmnModel_givenGraphicInfoXIs05_thenBpmnModelLocationMapKeyXIsZero() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

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
    assertEquals(0.0d, getResult.getX(), 0.0);
    assertEquals(3.0d, getResult.getY(), 0.0);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) X is two.
   *   <li>Then {@link BpmnModel} (default constructor) LocationMap {@code Key} X is two.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultProcessDiagramGenerator.prepareBpmnModel(BpmnModel)"})
  public void testPrepareBpmnModel_givenGraphicInfoXIsTwo_thenBpmnModelLocationMapKeyXIsTwo() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

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

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Key", graphicInfo);

    // Act
    defaultProcessDiagramGenerator.prepareBpmnModel(bpmnModel);

    // Assert that nothing has changed
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    GraphicInfo getResult = locationMap.get("Key");
    assertEquals(2.0d, getResult.getX(), 0.0);
    assertEquals(3.0d, getResult.getY(), 0.0);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Y is {@code -0.5}.
   *   <li>Then {@link BpmnModel} (default constructor) LocationMap {@code Key} Y is zero.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#prepareBpmnModel(BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DefaultProcessDiagramGenerator.prepareBpmnModel(BpmnModel)"})
  public void testPrepareBpmnModel_givenGraphicInfoYIs05_thenBpmnModelLocationMapKeyYIsZero() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

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
    graphicInfo.setY(-0.5d);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addGraphicInfo("Key", graphicInfo);

    // Act
    defaultProcessDiagramGenerator.prepareBpmnModel(bpmnModel);

    // Assert
    Map<String, GraphicInfo> locationMap = bpmnModel.getLocationMap();
    assertEquals(1, locationMap.size());
    GraphicInfo getResult = locationMap.get("Key");
    assertEquals(0.0d, getResult.getY(), 0.0);
    assertEquals(2.0d, getResult.getX(), 0.0);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BpmnModel bpmnModel = new BpmnModel();

    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(new ArrayList<>());
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity2() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BpmnModel bpmnModel = new BpmnModel();

    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getOutgoingFlows())
        .thenThrow(new ActivitiInterchangeInfoNotFoundException("An error occurred"));
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(
        ActivitiInterchangeInfoNotFoundException.class,
        () ->
            defaultProcessDiagramGenerator.drawActivity(
                processDiagramCanvas,
                bpmnModel,
                flowNode,
                currentActivities,
                erroredActivities,
                highLightedActivities,
                new ArrayList<>()));
    verify(flowNode).getOutgoingFlows();
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity3() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow());

    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow())
        .thenThrow(new ActivitiInterchangeInfoNotFoundException("An error occurred"));
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(
        ActivitiInterchangeInfoNotFoundException.class,
        () ->
            defaultProcessDiagramGenerator.drawActivity(
                processDiagramCanvas,
                bpmnModel,
                flowNode,
                currentActivities,
                erroredActivities,
                highLightedActivities,
                new ArrayList<>()));
    verify(flowNode).getDefaultFlow();
    verify(flowNode).getOutgoingFlows();
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity4() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
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
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity5() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any()))
        .thenThrow(new ActivitiInterchangeInfoNotFoundException("An error occurred"));
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    when(sequenceFlow.getSourceRef()).thenReturn("Source Ref");
    when(sequenceFlow.getTargetRef()).thenReturn("Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);

    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(
        ActivitiInterchangeInfoNotFoundException.class,
        () ->
            defaultProcessDiagramGenerator.drawActivity(
                processDiagramCanvas,
                bpmnModel,
                flowNode,
                currentActivities,
                erroredActivities,
                highLightedActivities,
                new ArrayList<>()));
    verify(flowNode).getDefaultFlow();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo(null);
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity6() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo(null);
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity7() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName())
        .thenThrow(new ActivitiInterchangeInfoNotFoundException("An error occurred"));
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    when(sequenceFlow.getSourceRef()).thenReturn("Source Ref");
    when(sequenceFlow.getTargetRef()).thenReturn("Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);

    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act and Assert
    assertThrows(
        ActivitiInterchangeInfoNotFoundException.class,
        () ->
            defaultProcessDiagramGenerator.drawActivity(
                processDiagramCanvas,
                bpmnModel,
                flowNode,
                currentActivities,
                erroredActivities,
                highLightedActivities,
                new ArrayList<>()));
    verify(flowNode).getDefaultFlow();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo(null);
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity8() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity9() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    when(sequenceFlow.getSourceRef()).thenReturn("Source Ref");
    when(sequenceFlow.getTargetRef()).thenReturn("Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow());
    sequenceFlowList.add(sequenceFlow);

    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode, atLeast(1)).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel, atLeast(1)).getFlowLocationGraphicInfo(Mockito.<String>any());
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getLabelGraphicInfo(Mockito.<String>any());
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(7, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link AdhocSubProcess} {@link AdhocSubProcess#getDefaultFlow()} return {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_given42_whenAdhocSubProcessGetDefaultFlowReturn42() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<List<GraphicInfo>>any()))
        .thenReturn(new ArrayList<>());
    doNothing()
        .when(processDiagramCanvas)
        .drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    doNothing()
        .when(processDiagramCanvas)
        .drawSequenceflow(
            Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    when(sequenceFlow.getSourceRef()).thenReturn("Source Ref");
    when(sequenceFlow.getTargetRef()).thenReturn("Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);

    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("42");
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas)
        .connectionPerfectionizer(
            eq(SHAPE_TYPE.Rectangle),
            eq(SHAPE_TYPE.Rectangle),
            isA(GraphicInfo.class),
            isA(GraphicInfo.class),
            isA(List.class));
    verify(processDiagramCanvas).drawLabel(eq("Name"), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas)
        .drawSequenceflow(isA(int[].class), isA(int[].class), eq(true), eq(true), eq(false));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_given42_whenArrayListAdd42() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
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

    ArrayList<String> highLightedFlows = new ArrayList<>();
    highLightedFlows.add("42");
    highLightedFlows.add("foo");

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        highLightedFlows);

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then calls {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE,
   *       SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_given42_whenArrayListAdd42_thenCallsConnectionPerfectionizer() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<List<GraphicInfo>>any()))
        .thenReturn(new ArrayList<>());
    doNothing()
        .when(processDiagramCanvas)
        .drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    doNothing()
        .when(processDiagramCanvas)
        .drawSequenceflow(
            Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    currentActivities.add("42");
    currentActivities.add("foo");
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas)
        .connectionPerfectionizer(
            eq(SHAPE_TYPE.Rectangle),
            eq(SHAPE_TYPE.Rectangle),
            isA(GraphicInfo.class),
            isA(GraphicInfo.class),
            isA(List.class));
    verify(processDiagramCanvas).drawLabel(eq("Name"), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas)
        .drawSequenceflow(isA(int[].class), isA(int[].class), eq(true), eq(false), eq(false));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then calls {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE,
   *       SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_given42_whenArrayListAdd42_thenCallsConnectionPerfectionizer2() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<List<GraphicInfo>>any()))
        .thenReturn(new ArrayList<>());
    doNothing()
        .when(processDiagramCanvas)
        .drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    doNothing()
        .when(processDiagramCanvas)
        .drawSequenceflow(
            Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    erroredActivities.add("42");
    erroredActivities.add("foo");
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas)
        .connectionPerfectionizer(
            eq(SHAPE_TYPE.Rectangle),
            eq(SHAPE_TYPE.Rectangle),
            isA(GraphicInfo.class),
            isA(GraphicInfo.class),
            isA(List.class));
    verify(processDiagramCanvas).drawLabel(eq("Name"), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas)
        .drawSequenceflow(isA(int[].class), isA(int[].class), eq(true), eq(false), eq(false));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code 42}.
   *   <li>Then calls {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE,
   *       SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_given42_whenArrayListAdd42_thenCallsConnectionPerfectionizer3() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<List<GraphicInfo>>any()))
        .thenReturn(new ArrayList<>());
    doNothing()
        .when(processDiagramCanvas)
        .drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    doNothing()
        .when(processDiagramCanvas)
        .drawSequenceflow(
            Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    highLightedActivities.add("42");
    highLightedActivities.add("foo");

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas)
        .connectionPerfectionizer(
            eq(SHAPE_TYPE.Rectangle),
            eq(SHAPE_TYPE.Rectangle),
            isA(GraphicInfo.class),
            isA(GraphicInfo.class),
            isA(List.class));
    verify(processDiagramCanvas).drawLabel(eq("Name"), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas)
        .drawSequenceflow(isA(int[].class), isA(int[].class), eq(true), eq(false), eq(false));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code 42}.
   *   <li>When {@link BpmnModel} (default constructor) addFlowGraphicInfoList {@code 42} and {@link
   *       ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_given42_whenBpmnModelAddFlowGraphicInfoList42AndArrayList() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addFlowGraphicInfoList("42", new ArrayList<>());

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
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
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenAdhocSubProcess() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
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
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenArrayListAddAdhocSubProcess() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
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
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link SequenceFlow#SequenceFlow()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenArrayListAddSequenceFlow() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BpmnModel bpmnModel = new BpmnModel();

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow());

    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenBusinessRuleTask() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new BusinessRuleTask());

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo(null);
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@link ComplexGateway} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenComplexGateway() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new ComplexGateway());

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo(null);
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenFoo_whenArrayListAddFoo() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BpmnModel bpmnModel = new BpmnModel();

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getId()).thenReturn("42");
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

    ArrayList<String> highLightedFlows = new ArrayList<>();
    highLightedFlows.add("foo");

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        highLightedFlows);

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   *   <li>Then calls {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE,
   *       SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenFoo_whenArrayListAddFoo_thenCallsConnectionPerfectionizer() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<List<GraphicInfo>>any()))
        .thenReturn(new ArrayList<>());
    doNothing()
        .when(processDiagramCanvas)
        .drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    doNothing()
        .when(processDiagramCanvas)
        .drawSequenceflow(
            Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    currentActivities.add("foo");
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas)
        .connectionPerfectionizer(
            eq(SHAPE_TYPE.Rectangle),
            eq(SHAPE_TYPE.Rectangle),
            isA(GraphicInfo.class),
            isA(GraphicInfo.class),
            isA(List.class));
    verify(processDiagramCanvas).drawLabel(eq("Name"), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas)
        .drawSequenceflow(isA(int[].class), isA(int[].class), eq(true), eq(false), eq(false));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   *   <li>Then calls {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE,
   *       SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenFoo_whenArrayListAddFoo_thenCallsConnectionPerfectionizer2() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<List<GraphicInfo>>any()))
        .thenReturn(new ArrayList<>());
    doNothing()
        .when(processDiagramCanvas)
        .drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    doNothing()
        .when(processDiagramCanvas)
        .drawSequenceflow(
            Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    erroredActivities.add("foo");
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas)
        .connectionPerfectionizer(
            eq(SHAPE_TYPE.Rectangle),
            eq(SHAPE_TYPE.Rectangle),
            isA(GraphicInfo.class),
            isA(GraphicInfo.class),
            isA(List.class));
    verify(processDiagramCanvas).drawLabel(eq("Name"), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas)
        .drawSequenceflow(isA(int[].class), isA(int[].class), eq(true), eq(false), eq(false));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code foo}.
   *   <li>When {@link ArrayList#ArrayList()} add {@code foo}.
   *   <li>Then calls {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE,
   *       SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenFoo_whenArrayListAddFoo_thenCallsConnectionPerfectionizer3() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<List<GraphicInfo>>any()))
        .thenReturn(new ArrayList<>());
    doNothing()
        .when(processDiagramCanvas)
        .drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    doNothing()
        .when(processDiagramCanvas)
        .drawSequenceflow(
            Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    highLightedActivities.add("foo");

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas)
        .connectionPerfectionizer(
            eq(SHAPE_TYPE.Rectangle),
            eq(SHAPE_TYPE.Rectangle),
            isA(GraphicInfo.class),
            isA(GraphicInfo.class),
            isA(List.class));
    verify(processDiagramCanvas).drawLabel(eq("Name"), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas)
        .drawSequenceflow(isA(int[].class), isA(int[].class), eq(true), eq(false), eq(false));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Height is {@link Double#NaN}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenGraphicInfoHeightIsNaN() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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
    graphicInfo3.setHeight(Double.NaN);
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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo(null);
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) XmlColumnNumber is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenGraphicInfoXmlColumnNumberIsTwo() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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
    graphicInfo2.setExpanded(false);
    graphicInfo2.setHeight(0.5d);
    graphicInfo2.setWidth(0.5d);
    graphicInfo2.setX(10.0d);
    graphicInfo2.setXmlColumnNumber(1);
    graphicInfo2.setXmlRowNumber(1);
    graphicInfo2.setY(10.0d);

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
    graphicInfo3.setHeight(0.5d);
    graphicInfo3.setWidth(0.5d);
    graphicInfo3.setX(10.0d);
    graphicInfo3.setXmlColumnNumber(2);
    graphicInfo3.setXmlRowNumber(2);
    graphicInfo3.setY(10.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo3);
    graphicInfoList.add(graphicInfo2);
    graphicInfoList.add(graphicInfo);

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    GraphicInfo graphicInfo5 = new GraphicInfo();

    Builder builderResult5 = Message.builder();

    Builder attributesResult5 = builderResult5.attributes(new HashMap<>());
    graphicInfo5.setElement(
        attributesResult5
            .extensionElements(new HashMap<>())
            .id("42")
            .itemRef("Item Ref")
            .name("Name")
            .xmlColumnNumber(10)
            .xmlRowNumber(10)
            .build());
    graphicInfo5.setExpanded(true);
    graphicInfo5.setHeight(10.0d);
    graphicInfo5.setWidth(10.0d);
    graphicInfo5.setX(2.0d);
    graphicInfo5.setXmlColumnNumber(10);
    graphicInfo5.setXmlRowNumber(10);
    graphicInfo5.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo5);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo(null);
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>When {@link AdhocSubProcess} {@link AdhocSubProcess#getDefaultFlow()} return {@code
   *       null}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenNull_whenAdhocSubProcessGetDefaultFlowReturnNull() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<List<GraphicInfo>>any()))
        .thenReturn(new ArrayList<>());
    doNothing()
        .when(processDiagramCanvas)
        .drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    doNothing()
        .when(processDiagramCanvas)
        .drawSequenceflow(
            Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
    when(sequenceFlow.getConditionExpression()).thenReturn("Condition Expression");
    when(sequenceFlow.getSourceRef()).thenReturn("Source Ref");
    when(sequenceFlow.getTargetRef()).thenReturn("Target Ref");

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(sequenceFlow);

    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn(null);
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas)
        .connectionPerfectionizer(
            eq(SHAPE_TYPE.Rectangle),
            eq(SHAPE_TYPE.Rectangle),
            isA(GraphicInfo.class),
            isA(GraphicInfo.class),
            isA(List.class));
    verify(processDiagramCanvas).drawLabel(eq("Name"), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas)
        .drawSequenceflow(isA(int[].class), isA(int[].class), eq(true), eq(false), eq(false));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>When {@link BpmnModel} (default constructor) addProcess {@link Process} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_givenProcess_whenBpmnModelAddProcessProcess() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    ArrayList<SequenceFlow> sequenceFlowList = new ArrayList<>();
    sequenceFlowList.add(new SequenceFlow());

    AdhocSubProcess flowNode = mock(AdhocSubProcess.class);
    when(flowNode.getDefaultFlow()).thenReturn("Default Flow");
    when(flowNode.getFlowElements()).thenReturn(new ArrayList<>());
    when(flowNode.getOutgoingFlows()).thenReturn(sequenceFlowList);
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert that nothing has changed
    verify(flowNode).getDefaultFlow();
    verify(flowNode).getOutgoingFlows();
    verify(flowNode).getFlowElements();
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>Then calls {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE,
   *       SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_thenCallsConnectionPerfectionizer() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<List<GraphicInfo>>any()))
        .thenReturn(new ArrayList<>());
    doNothing()
        .when(processDiagramCanvas)
        .drawLabel(Mockito.<String>any(), Mockito.<GraphicInfo>any(), anyBoolean());
    doNothing()
        .when(processDiagramCanvas)
        .drawSequenceflow(
            Mockito.<int[]>any(), Mockito.<int[]>any(), anyBoolean(), anyBoolean(), anyBoolean());

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

    AdhocSubProcess adhocSubProcess = mock(AdhocSubProcess.class);
    when(adhocSubProcess.getId()).thenReturn("42");

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
    graphicInfo4.setExpanded(true);
    graphicInfo4.setHeight(10.0d);
    graphicInfo4.setWidth(10.0d);
    graphicInfo4.setX(2.0d);
    graphicInfo4.setXmlColumnNumber(10);
    graphicInfo4.setXmlRowNumber(10);
    graphicInfo4.setY(3.0d);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getLabelGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo4);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(adhocSubProcess);

    SequenceFlow sequenceFlow = mock(SequenceFlow.class);
    when(sequenceFlow.getName()).thenReturn("Name");
    when(sequenceFlow.getId()).thenReturn("42");
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
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert
    verify(flowNode).getDefaultFlow();
    verify(adhocSubProcess, atLeast(1)).getId();
    verify(sequenceFlow, atLeast(1)).getId();
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    verify(bpmnModel).getLabelGraphicInfo("42");
    verify(sequenceFlow).getName();
    verify(flowNode).getOutgoingFlows();
    verify(sequenceFlow).getConditionExpression();
    verify(sequenceFlow).getSourceRef();
    verify(sequenceFlow).getTargetRef();
    verify(flowNode).getFlowElements();
    verify(processDiagramCanvas)
        .connectionPerfectionizer(
            eq(SHAPE_TYPE.Rectangle),
            eq(SHAPE_TYPE.Rectangle),
            isA(GraphicInfo.class),
            isA(GraphicInfo.class),
            isA(List.class));
    verify(processDiagramCanvas).drawLabel(eq("Name"), isA(GraphicInfo.class), eq(false));
    verify(processDiagramCanvas)
        .drawSequenceflow(isA(int[].class), isA(int[].class), eq(true), eq(false), eq(false));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel,
   * FlowNode, List, List, List, List)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode,
   * List, List, List, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawActivity(DefaultProcessDiagramCanvas, BpmnModel, FlowNode, List, List, List, List)"
  })
  public void testDrawActivity_whenAdhocSubProcess() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BpmnModel bpmnModel = new BpmnModel();
    AdhocSubProcess flowNode = new AdhocSubProcess();
    ArrayList<String> currentActivities = new ArrayList<>();
    ArrayList<String> erroredActivities = new ArrayList<>();
    ArrayList<String> highLightedActivities = new ArrayList<>();

    // Act
    defaultProcessDiagramGenerator.drawActivity(
        processDiagramCanvas,
        bpmnModel,
        flowNode,
        currentActivities,
        erroredActivities,
        highLightedActivities,
        new ArrayList<>());

    // Assert that nothing has changed
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link
   * DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel,
   * BaseElement, BaseElement, List)}.
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Expanded is {@code false}.
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel,
   * BaseElement, BaseElement, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DefaultProcessDiagramGenerator.connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List)"
  })
  public void testConnectionPerfectionizer_givenGraphicInfoExpandedIsFalse_thenReturnSizeIsTwo() {
    // Arrange
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BpmnModel bpmnModel = new BpmnModel();
    ActivitiListener sourceElement = new ActivitiListener();
    ActivitiListener targetElement = new ActivitiListener();

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
    List<GraphicInfo> actualConnectionPerfectionizerResult =
        DefaultProcessDiagramGenerator.connectionPerfectionizer(
            processDiagramCanvas, bpmnModel, sourceElement, targetElement, graphicInfoList);

    // Assert
    assertEquals(2, actualConnectionPerfectionizerResult.size());
    GraphicInfo getResult = actualConnectionPerfectionizerResult.get(0);
    assertEquals(0.5d, getResult.getHeight(), 0.0);
    assertEquals(0.5d, getResult.getWidth(), 0.0);
    assertEquals(1, getResult.getXmlColumnNumber());
    assertEquals(1, getResult.getXmlRowNumber());
    assertEquals(10.0d, getResult.getX(), 0.0);
    assertEquals(10.0d, getResult.getY(), 0.0);
    assertFalse(getResult.getExpanded());
    assertSame(graphicInfo, actualConnectionPerfectionizerResult.get(1));
  }

  /**
   * Test {@link
   * DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel,
   * BaseElement, BaseElement, List)}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel,
   * BaseElement, BaseElement, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DefaultProcessDiagramGenerator.connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List)"
  })
  public void testConnectionPerfectionizer_thenReturnSizeIsOne() {
    // Arrange
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BpmnModel bpmnModel = new BpmnModel();
    ActivitiListener sourceElement = new ActivitiListener();
    ActivitiListener targetElement = new ActivitiListener();

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

    // Act
    List<GraphicInfo> actualConnectionPerfectionizerResult =
        DefaultProcessDiagramGenerator.connectionPerfectionizer(
            processDiagramCanvas, bpmnModel, sourceElement, targetElement, graphicInfoList);

    // Assert
    assertEquals(1, actualConnectionPerfectionizerResult.size());
    GraphicInfo getResult = actualConnectionPerfectionizerResult.get(0);
    assertEquals(10, getResult.getXmlColumnNumber());
    assertEquals(10, getResult.getXmlRowNumber());
    assertEquals(10.0d, getResult.getHeight(), 0.0);
    assertEquals(10.0d, getResult.getWidth(), 0.0);
    assertEquals(2.0d, getResult.getX(), 0.0);
    assertEquals(3.0d, getResult.getY(), 0.0);
    assertTrue(getResult.getExpanded());
  }

  /**
   * Test {@link
   * DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel,
   * BaseElement, BaseElement, List)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel,
   * BaseElement, BaseElement, List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DefaultProcessDiagramGenerator.connectionPerfectionizer(DefaultProcessDiagramCanvas, BpmnModel, BaseElement, BaseElement, List)"
  })
  public void testConnectionPerfectionizer_whenArrayList_thenReturnEmpty() {
    // Arrange
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BpmnModel bpmnModel = new BpmnModel();
    ActivitiListener sourceElement = new ActivitiListener();
    ActivitiListener targetElement = new ActivitiListener();

    // Act
    List<GraphicInfo> actualConnectionPerfectionizerResult =
        DefaultProcessDiagramGenerator.connectionPerfectionizer(
            processDiagramCanvas, bpmnModel, sourceElement, targetElement, new ArrayList<>());

    // Assert
    assertTrue(actualConnectionPerfectionizerResult.isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}.
   *
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SHAPE_TYPE DefaultProcessDiagramGenerator.getShapeType(BaseElement)"})
  public void testGetShapeType_whenActivitiListener_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(DefaultProcessDiagramGenerator.getShapeType(new ActivitiListener()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   *   <li>Then return {@code Rectangle}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SHAPE_TYPE DefaultProcessDiagramGenerator.getShapeType(BaseElement)"})
  public void testGetShapeType_whenAdhocSubProcess_thenReturnRectangle() {
    // Arrange, Act and Assert
    assertEquals(
        SHAPE_TYPE.Rectangle, DefaultProcessDiagramGenerator.getShapeType(new AdhocSubProcess()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}.
   *
   * <ul>
   *   <li>When {@link BoundaryEvent} (default constructor).
   *   <li>Then return {@code Ellipse}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SHAPE_TYPE DefaultProcessDiagramGenerator.getShapeType(BaseElement)"})
  public void testGetShapeType_whenBoundaryEvent_thenReturnEllipse() {
    // Arrange, Act and Assert
    assertEquals(
        SHAPE_TYPE.Ellipse, DefaultProcessDiagramGenerator.getShapeType(new BoundaryEvent()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}.
   *
   * <ul>
   *   <li>When {@link ComplexGateway} (default constructor).
   *   <li>Then return {@code Rhombus}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SHAPE_TYPE DefaultProcessDiagramGenerator.getShapeType(BaseElement)"})
  public void testGetShapeType_whenComplexGateway_thenReturnRhombus() {
    // Arrange, Act and Assert
    assertEquals(
        SHAPE_TYPE.Rhombus, DefaultProcessDiagramGenerator.getShapeType(new ComplexGateway()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}.
   *
   * <ul>
   *   <li>When {@link Task} (default constructor).
   *   <li>Then return {@code Rectangle}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SHAPE_TYPE DefaultProcessDiagramGenerator.getShapeType(BaseElement)"})
  public void testGetShapeType_whenTask_thenReturnRectangle() {
    // Arrange, Act and Assert
    assertEquals(SHAPE_TYPE.Rectangle, DefaultProcessDiagramGenerator.getShapeType(new Task()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}.
   *
   * <ul>
   *   <li>When {@link TextAnnotation} (default constructor).
   *   <li>Then return {@code Rectangle}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#getShapeType(BaseElement)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"SHAPE_TYPE DefaultProcessDiagramGenerator.getShapeType(BaseElement)"})
  public void testGetShapeType_whenTextAnnotation_thenReturnRectangle() {
    // Arrange, Act and Assert
    assertEquals(
        SHAPE_TYPE.Rectangle, DefaultProcessDiagramGenerator.getShapeType(new TextAnnotation()));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#getLineCenter(List)}.
   *
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Expanded is {@code false}.
   *   <li>Then return Y is {@code 2.5}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#getLineCenter(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"GraphicInfo DefaultProcessDiagramGenerator.getLineCenter(List)"})
  public void testGetLineCenter_givenGraphicInfoExpandedIsFalse_thenReturnYIs25() {
    // Arrange
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
   * Test {@link DefaultProcessDiagramGenerator#getLineCenter(List)}.
   *
   * <ul>
   *   <li>Then return Y is {@code 2.3245883961385942}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#getLineCenter(List)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"GraphicInfo DefaultProcessDiagramGenerator.getLineCenter(List)"})
  public void testGetLineCenter_thenReturnYIs23245883961385942() {
    // Arrange
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
    graphicInfo2.setExpanded(false);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(10.0d);
    graphicInfo2.setXmlColumnNumber(1);
    graphicInfo2.setXmlRowNumber(1);
    graphicInfo2.setY(2.0d);

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
   * Test {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel,
   * Artifact)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)"
  })
  public void testDrawArtifact() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    defaultProcessDiagramGenerator.setArtifactDrawInstructions(new HashMap<>());
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BpmnModel bpmnModel = new BpmnModel();

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new Association());

    // Assert that nothing has changed
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel,
   * Artifact)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)"
  })
  public void testDrawArtifact2() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any()))
        .thenThrow(new ActivitiInterchangeInfoNotFoundException("An error occurred"));
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    doNothing().when(bpmnModel).addProcess(Mockito.<Process>any());
    bpmnModel.addProcess(new Process());

    // Act and Assert
    assertThrows(
        ActivitiInterchangeInfoNotFoundException.class,
        () ->
            defaultProcessDiagramGenerator.drawArtifact(
                processDiagramCanvas, bpmnModel, new Association()));
    verify(bpmnModel).addProcess(isA(Process.class));
    verify(bpmnModel, atLeast(1)).getFlowElement(null);
    verify(bpmnModel).getFlowLocationGraphicInfo(null);
    verify(bpmnModel).getGraphicInfo(null);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel,
   * Artifact)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)"
  })
  public void testDrawArtifact3() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo3);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfoList);
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    doNothing().when(bpmnModel).addProcess(Mockito.<Process>any());
    bpmnModel.addProcess(new Process());

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new Association());

    // Assert
    verify(bpmnModel).addProcess(isA(Process.class));
    verify(bpmnModel, atLeast(1)).getFlowElement(null);
    verify(bpmnModel).getFlowLocationGraphicInfo(null);
    verify(bpmnModel, atLeast(1)).getGraphicInfo(null);
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel,
   * Artifact)}.
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)"
  })
  public void testDrawArtifact4() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BpmnModel bpmnModel = mock(BpmnModel.class);
    doNothing().when(bpmnModel).addProcess(Mockito.<Process>any());
    bpmnModel.addProcess(new Process());

    Association artifact = mock(Association.class);
    when(artifact.getSourceRef())
        .thenThrow(new ActivitiInterchangeInfoNotFoundException("An error occurred"));

    // Act and Assert
    assertThrows(
        ActivitiInterchangeInfoNotFoundException.class,
        () ->
            defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, artifact));
    verify(artifact).getSourceRef();
    verify(bpmnModel).addProcess(isA(Process.class));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel,
   * Artifact)}.
   *
   * <ul>
   *   <li>Given {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)"
  })
  public void testDrawArtifact_givenAdhocSubProcess() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new AdhocSubProcess());
    doNothing().when(bpmnModel).addProcess(Mockito.<Process>any());
    bpmnModel.addProcess(new Process());

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new Association());

    // Assert that nothing has changed
    verify(bpmnModel).addProcess(isA(Process.class));
    verify(bpmnModel, atLeast(1)).getFlowElement(null);
    verify(bpmnModel).getFlowLocationGraphicInfo(null);
    verify(bpmnModel, atLeast(1)).getGraphicInfo(null);
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel,
   * Artifact)}.
   *
   * <ul>
   *   <li>Given {@link BusinessRuleTask} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)"
  })
  public void testDrawArtifact_givenBusinessRuleTask() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(new BusinessRuleTask());
    doNothing().when(bpmnModel).addProcess(Mockito.<Process>any());
    bpmnModel.addProcess(new Process());

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new Association());

    // Assert that nothing has changed
    verify(bpmnModel).addProcess(isA(Process.class));
    verify(bpmnModel, atLeast(1)).getFlowElement(null);
    verify(bpmnModel).getFlowLocationGraphicInfo(null);
    verify(bpmnModel, atLeast(1)).getGraphicInfo(null);
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel,
   * Artifact)}.
   *
   * <ul>
   *   <li>Given {@link BusinessRuleTask} {@link BusinessRuleTask#getId()} return {@code 42}.
   *   <li>Then calls {@link BusinessRuleTask#getId()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)"
  })
  public void testDrawArtifact_givenBusinessRuleTaskGetIdReturn42_thenCallsGetId() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BusinessRuleTask businessRuleTask = mock(BusinessRuleTask.class);
    when(businessRuleTask.getId()).thenReturn("42");

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

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(businessRuleTask);
    doNothing().when(bpmnModel).addProcess(Mockito.<Process>any());
    bpmnModel.addProcess(new Process());

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new Association());

    // Assert that nothing has changed
    verify(businessRuleTask, atLeast(1)).getId();
    verify(bpmnModel).addProcess(isA(Process.class));
    verify(bpmnModel, atLeast(1)).getFlowElement(null);
    verify(bpmnModel).getFlowLocationGraphicInfo(null);
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel,
   * Artifact)}.
   *
   * <ul>
   *   <li>Given {@code null}.
   *   <li>Then calls {@link BpmnModel#getArtifact(String)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)"
  })
  public void testDrawArtifact_givenNull_thenCallsGetArtifact() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getArtifact(Mockito.<String>any())).thenReturn(new Association());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(null);
    doNothing().when(bpmnModel).addProcess(Mockito.<Process>any());
    bpmnModel.addProcess(new Process());

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, new Association());

    // Assert that nothing has changed
    verify(bpmnModel).addProcess(isA(Process.class));
    verify(bpmnModel, atLeast(1)).getArtifact(null);
    verify(bpmnModel, atLeast(1)).getFlowElement(null);
    verify(bpmnModel).getFlowLocationGraphicInfo(null);
    verify(bpmnModel, atLeast(1)).getGraphicInfo(null);
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel,
   * Artifact)}.
   *
   * <ul>
   *   <li>Given {@code Source Ref}.
   *   <li>Then calls {@link Association#getAssociationDirection()}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)"
  })
  public void testDrawArtifact_givenSourceRef_thenCallsGetAssociationDirection() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    DefaultProcessDiagramCanvas processDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    BusinessRuleTask businessRuleTask = mock(BusinessRuleTask.class);
    when(businessRuleTask.getId()).thenReturn("42");

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

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(businessRuleTask);
    doNothing().when(bpmnModel).addProcess(Mockito.<Process>any());
    bpmnModel.addProcess(new Process());

    Association artifact = mock(Association.class);
    when(artifact.getSourceRef()).thenReturn("Source Ref");
    when(artifact.getTargetRef()).thenReturn("Target Ref");
    when(artifact.getId()).thenReturn("42");
    when(artifact.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, artifact);

    // Assert that nothing has changed
    verify(artifact).getAssociationDirection();
    verify(artifact).getSourceRef();
    verify(artifact).getTargetRef();
    verify(artifact).getId();
    verify(businessRuleTask, atLeast(1)).getId();
    verify(bpmnModel).addProcess(isA(Process.class));
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    Element root = processDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel,
   * Artifact)}.
   *
   * <ul>
   *   <li>Then calls {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE,
   *       SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void DefaultProcessDiagramGenerator.drawArtifact(DefaultProcessDiagramCanvas, BpmnModel, Artifact)"
  })
  public void testDrawArtifact_thenCallsConnectionPerfectionizer() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();

    DefaultProcessDiagramCanvas processDiagramCanvas = mock(DefaultProcessDiagramCanvas.class);
    when(processDiagramCanvas.connectionPerfectionizer(
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<SHAPE_TYPE>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<GraphicInfo>any(),
            Mockito.<List<GraphicInfo>>any()))
        .thenReturn(new ArrayList<>());
    doNothing()
        .when(processDiagramCanvas)
        .drawAssociation(
            Mockito.<int[]>any(),
            Mockito.<int[]>any(),
            Mockito.<AssociationDirection>any(),
            anyBoolean());

    BusinessRuleTask businessRuleTask = mock(BusinessRuleTask.class);
    when(businessRuleTask.getId()).thenReturn("42");

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

    BpmnModel bpmnModel = mock(BpmnModel.class);
    when(bpmnModel.getGraphicInfo(Mockito.<String>any())).thenReturn(graphicInfo);
    when(bpmnModel.getFlowLocationGraphicInfo(Mockito.<String>any())).thenReturn(new ArrayList<>());
    when(bpmnModel.getFlowElement(Mockito.<String>any())).thenReturn(businessRuleTask);
    doNothing().when(bpmnModel).addProcess(Mockito.<Process>any());
    bpmnModel.addProcess(new Process());

    Association artifact = mock(Association.class);
    when(artifact.getSourceRef()).thenReturn("Source Ref");
    when(artifact.getTargetRef()).thenReturn("Target Ref");
    when(artifact.getId()).thenReturn("42");
    when(artifact.getAssociationDirection()).thenReturn(AssociationDirection.NONE);

    // Act
    defaultProcessDiagramGenerator.drawArtifact(processDiagramCanvas, bpmnModel, artifact);

    // Assert
    verify(artifact).getAssociationDirection();
    verify(artifact).getSourceRef();
    verify(artifact).getTargetRef();
    verify(artifact).getId();
    verify(businessRuleTask, atLeast(1)).getId();
    verify(bpmnModel).addProcess(isA(Process.class));
    verify(bpmnModel, atLeast(1)).getFlowElement(Mockito.<String>any());
    verify(bpmnModel).getFlowLocationGraphicInfo("42");
    verify(bpmnModel, atLeast(1)).getGraphicInfo("42");
    verify(processDiagramCanvas)
        .connectionPerfectionizer(
            eq(SHAPE_TYPE.Rectangle),
            eq(SHAPE_TYPE.Rectangle),
            isA(GraphicInfo.class),
            isA(GraphicInfo.class),
            isA(List.class));
    verify(processDiagramCanvas)
        .drawAssociation(
            isA(int[].class), isA(int[].class), eq(AssociationDirection.NONE), eq(false));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String,
   * String)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) addArtifact {@link Association} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultProcessDiagramCanvas DefaultProcessDiagramGenerator.initProcessDiagramCanvas(BpmnModel, String, String, String)"
  })
  public void testInitProcessDiagramCanvas_givenProcessAddArtifactAssociation() {
    // Arrange
    Process process = new Process();
    process.addArtifact(new Association());
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult =
        DefaultProcessDiagramGenerator.initProcessDiagramCanvas(
            bpmnModel, "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    assertEquals("Activity Font Name", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Annotation Font Name", actualInitProcessDiagramCanvasResult.annotationFontName);
    assertEquals("Label Font Name", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minY);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String,
   * String)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement {@link BooleanDataObject}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultProcessDiagramCanvas DefaultProcessDiagramGenerator.initProcessDiagramCanvas(BpmnModel, String, String, String)"
  })
  public void testInitProcessDiagramCanvas_givenProcessAddFlowElementBooleanDataObject() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult =
        DefaultProcessDiagramGenerator.initProcessDiagramCanvas(
            bpmnModel, "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    assertEquals("Activity Font Name", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Annotation Font Name", actualInitProcessDiagramCanvasResult.annotationFontName);
    assertEquals("Label Font Name", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minY);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String,
   * String)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>Then return {@link DefaultProcessDiagramCanvas#minX} is zero.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultProcessDiagramCanvas DefaultProcessDiagramGenerator.initProcessDiagramCanvas(BpmnModel, String, String, String)"
  })
  public void testInitProcessDiagramCanvas_givenProcess_thenReturnMinXIsZero() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult =
        DefaultProcessDiagramGenerator.initProcessDiagramCanvas(
            bpmnModel, "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    assertEquals("Activity Font Name", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Annotation Font Name", actualInitProcessDiagramCanvasResult.annotationFontName);
    assertEquals("Label Font Name", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minY);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String,
   * String)}.
   *
   * <ul>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#minX} is {@link Integer#MAX_VALUE}.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultProcessDiagramCanvas DefaultProcessDiagramGenerator.initProcessDiagramCanvas(BpmnModel, String, String, String)"
  })
  public void testInitProcessDiagramCanvas_thenReturnMinXIsMax_value() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new AdhocSubProcess());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult =
        DefaultProcessDiagramGenerator.initProcessDiagramCanvas(
            bpmnModel, "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    assertEquals("Activity Font Name", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Annotation Font Name", actualInitProcessDiagramCanvasResult.annotationFontName);
    assertEquals("Label Font Name", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(Integer.MAX_VALUE, actualInitProcessDiagramCanvasResult.minY);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel, String, String,
   * String)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   *   <li>Then return {@link DefaultProcessDiagramCanvas#minX} is zero.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#initProcessDiagramCanvas(BpmnModel,
   * String, String, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "DefaultProcessDiagramCanvas DefaultProcessDiagramGenerator.initProcessDiagramCanvas(BpmnModel, String, String, String)"
  })
  public void testInitProcessDiagramCanvas_whenBpmnModel_thenReturnMinXIsZero() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualInitProcessDiagramCanvasResult =
        DefaultProcessDiagramGenerator.initProcessDiagramCanvas(
            new BpmnModel(), "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    assertEquals("Activity Font Name", actualInitProcessDiagramCanvasResult.activityFontName);
    assertEquals("Annotation Font Name", actualInitProcessDiagramCanvasResult.annotationFontName);
    assertEquals("Label Font Name", actualInitProcessDiagramCanvasResult.labelFontName);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minX);
    assertEquals(0, actualInitProcessDiagramCanvasResult.minY);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasHeight);
    assertEquals(10, actualInitProcessDiagramCanvasResult.canvasWidth);
    assertFalse(actualInitProcessDiagramCanvasResult.closed);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllArtifacts(BpmnModel)}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>When {@link BpmnModel} (default constructor) addProcess {@link Process} (default
   *       constructor).
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#gatherAllArtifacts(BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List DefaultProcessDiagramGenerator.gatherAllArtifacts(BpmnModel)"})
  public void testGatherAllArtifacts_givenProcess_whenBpmnModelAddProcessProcess() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act
    List<Artifact> actualGatherAllArtifactsResult =
        DefaultProcessDiagramGenerator.gatherAllArtifacts(bpmnModel);

    // Assert
    assertTrue(actualGatherAllArtifactsResult.isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllArtifacts(BpmnModel)}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#gatherAllArtifacts(BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List DefaultProcessDiagramGenerator.gatherAllArtifacts(BpmnModel)"})
  public void testGatherAllArtifacts_whenBpmnModel_thenReturnEmpty() {
    // Arrange and Act
    List<Artifact> actualGatherAllArtifactsResult =
        DefaultProcessDiagramGenerator.gatherAllArtifacts(new BpmnModel());

    // Assert
    assertTrue(actualGatherAllArtifactsResult.isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)} with {@code
   * bpmnModel}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor) addFlowElement {@link BooleanDataObject}
   *       (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List DefaultProcessDiagramGenerator.gatherAllFlowNodes(BpmnModel)"})
  public void testGatherAllFlowNodesWithBpmnModel_givenProcessAddFlowElementBooleanDataObject() {
    // Arrange
    Process process = new Process();
    process.addFlowElement(new BooleanDataObject());

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    List<FlowNode> actualGatherAllFlowNodesResult =
        DefaultProcessDiagramGenerator.gatherAllFlowNodes(bpmnModel);

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)} with {@code
   * bpmnModel}.
   *
   * <ul>
   *   <li>Given {@link Process} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List DefaultProcessDiagramGenerator.gatherAllFlowNodes(BpmnModel)"})
  public void testGatherAllFlowNodesWithBpmnModel_givenProcess_thenReturnEmpty() {
    // Arrange
    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(new Process());

    // Act
    List<FlowNode> actualGatherAllFlowNodesResult =
        DefaultProcessDiagramGenerator.gatherAllFlowNodes(bpmnModel);

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)} with {@code
   * bpmnModel}.
   *
   * <ul>
   *   <li>Then return size is one.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List DefaultProcessDiagramGenerator.gatherAllFlowNodes(BpmnModel)"})
  public void testGatherAllFlowNodesWithBpmnModel_thenReturnSizeIsOne() {
    // Arrange
    Process process = new Process();
    AdhocSubProcess element = new AdhocSubProcess();
    process.addFlowElement(element);

    BpmnModel bpmnModel = new BpmnModel();
    bpmnModel.addProcess(process);

    // Act
    List<FlowNode> actualGatherAllFlowNodesResult =
        DefaultProcessDiagramGenerator.gatherAllFlowNodes(bpmnModel);

    // Assert
    assertEquals(1, actualGatherAllFlowNodesResult.size());
    FlowNode getResult = actualGatherAllFlowNodesResult.get(0);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertSame(element, getResult);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)} with {@code
   * bpmnModel}.
   *
   * <ul>
   *   <li>When {@link BpmnModel} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(BpmnModel)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"List DefaultProcessDiagramGenerator.gatherAllFlowNodes(BpmnModel)"})
  public void testGatherAllFlowNodesWithBpmnModel_whenBpmnModel_thenReturnEmpty() {
    // Arrange and Act
    List<FlowNode> actualGatherAllFlowNodesResult =
        DefaultProcessDiagramGenerator.gatherAllFlowNodes(new BpmnModel());

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)} with
   * {@code flowElementsContainer}.
   *
   * <ul>
   *   <li>Then return size is three.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DefaultProcessDiagramGenerator.gatherAllFlowNodes(FlowElementsContainer)"
  })
  public void testGatherAllFlowNodesWithFlowElementsContainer_thenReturnSizeIsThree() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    AdhocSubProcess element2 = new AdhocSubProcess();
    element.addFlowElement(element2);

    AdhocSubProcess element3 = new AdhocSubProcess();
    element3.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element3);

    // Act
    List<FlowNode> actualGatherAllFlowNodesResult =
        DefaultProcessDiagramGenerator.gatherAllFlowNodes(flowElementsContainer);

    // Assert
    assertEquals(3, actualGatherAllFlowNodesResult.size());
    assertTrue(actualGatherAllFlowNodesResult.get(1) instanceof AdhocSubProcess);
    FlowNode getResult = actualGatherAllFlowNodesResult.get(2);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertSame(element2, getResult);
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)} with
   * {@code flowElementsContainer}.
   *
   * <ul>
   *   <li>Then return size is two.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DefaultProcessDiagramGenerator.gatherAllFlowNodes(FlowElementsContainer)"
  })
  public void testGatherAllFlowNodesWithFlowElementsContainer_thenReturnSizeIsTwo() {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();
    BooleanDataObject element2 = new BooleanDataObject();
    element.addFlowElement(element2);

    AdhocSubProcess element3 = new AdhocSubProcess();
    element3.addFlowElement(element);

    AdhocSubProcess flowElementsContainer = new AdhocSubProcess();
    flowElementsContainer.addFlowElement(element3);

    // Act
    List<FlowNode> actualGatherAllFlowNodesResult =
        DefaultProcessDiagramGenerator.gatherAllFlowNodes(flowElementsContainer);

    // Assert
    assertEquals(2, actualGatherAllFlowNodesResult.size());
    FlowNode getResult = actualGatherAllFlowNodesResult.get(1);
    Collection<FlowElement> flowElements = ((AdhocSubProcess) getResult).getFlowElements();
    assertEquals(1, flowElements.size());
    assertTrue(flowElements instanceof List);
    assertTrue(getResult instanceof AdhocSubProcess);
    assertSame(element2, ((List<FlowElement>) flowElements).get(0));
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)} with
   * {@code flowElementsContainer}.
   *
   * <ul>
   *   <li>When {@link AdhocSubProcess} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DefaultProcessDiagramGenerator.gatherAllFlowNodes(FlowElementsContainer)"
  })
  public void testGatherAllFlowNodesWithFlowElementsContainer_whenAdhocSubProcess() {
    // Arrange and Act
    List<FlowNode> actualGatherAllFlowNodesResult =
        DefaultProcessDiagramGenerator.gatherAllFlowNodes(new AdhocSubProcess());

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)} with
   * {@code flowElementsContainer}.
   *
   * <ul>
   *   <li>When {@link Process} (default constructor).
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link
   * DefaultProcessDiagramGenerator#gatherAllFlowNodes(FlowElementsContainer)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DefaultProcessDiagramGenerator.gatherAllFlowNodes(FlowElementsContainer)"
  })
  public void testGatherAllFlowNodesWithFlowElementsContainer_whenProcess_thenReturnEmpty() {
    // Arrange and Act
    List<FlowNode> actualGatherAllFlowNodesResult =
        DefaultProcessDiagramGenerator.gatherAllFlowNodes(new Process());

    // Assert
    assertTrue(actualGatherAllFlowNodesResult.isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "Map DefaultProcessDiagramGenerator.getActivityDrawInstructions()",
    "Map DefaultProcessDiagramGenerator.getArtifactDrawInstructions()",
    "String DefaultProcessDiagramGenerator.getDefaultActivityFontName()",
    "String DefaultProcessDiagramGenerator.getDefaultAnnotationFontName()",
    "String DefaultProcessDiagramGenerator.getDefaultDiagramImageFileName()",
    "String DefaultProcessDiagramGenerator.getDefaultLabelFontName()",
    "void DefaultProcessDiagramGenerator.setActivityDrawInstructions(Map)",
    "void DefaultProcessDiagramGenerator.setArtifactDrawInstructions(Map)"
  })
  public void testGettersAndSetters() {
    // Arrange
    DefaultProcessDiagramGenerator defaultProcessDiagramGenerator =
        new DefaultProcessDiagramGenerator();
    HashMap<Class<? extends BaseElement>, ActivityDrawInstruction> activityDrawInstructions =
        new HashMap<>();

    // Act
    defaultProcessDiagramGenerator.setActivityDrawInstructions(activityDrawInstructions);
    HashMap<Class<? extends BaseElement>, ArtifactDrawInstruction> artifactDrawInstructions =
        new HashMap<>();
    defaultProcessDiagramGenerator.setArtifactDrawInstructions(artifactDrawInstructions);
    Map<Class<? extends BaseElement>, ActivityDrawInstruction> actualActivityDrawInstructions =
        defaultProcessDiagramGenerator.getActivityDrawInstructions();
    Map<Class<? extends BaseElement>, ArtifactDrawInstruction> actualArtifactDrawInstructions =
        defaultProcessDiagramGenerator.getArtifactDrawInstructions();
    String actualDefaultActivityFontName =
        defaultProcessDiagramGenerator.getDefaultActivityFontName();
    String actualDefaultAnnotationFontName =
        defaultProcessDiagramGenerator.getDefaultAnnotationFontName();
    String actualDefaultDiagramImageFileName =
        defaultProcessDiagramGenerator.getDefaultDiagramImageFileName();

    // Assert
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
