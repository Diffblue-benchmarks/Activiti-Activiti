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
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AssociationDirection;
import org.activiti.bpmn.model.CallActivity;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.image.impl.DefaultProcessDiagramCanvas.SHAPE_TYPE;
import org.activiti.image.impl.icon.BusinessRuleTaskIconType;
import org.activiti.image.impl.icon.CompensateIconType;
import org.activiti.image.impl.icon.IconType;
import org.activiti.image.impl.icon.TaskIconType;
import org.apache.batik.dom.GenericElementNS;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class DefaultProcessDiagramCanvasDiffblueTest {
  /**
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <ul>
   *   <li>When {@code Label Font Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int, String, String, String); when 'Label Font Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int, String, String, String)"})
  void testNewDefaultProcessDiagramCanvas_whenLabelFontName() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <ul>
   *   <li>When minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int, String, String, String); when minus one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int, String, String, String)"})
  void testNewDefaultProcessDiagramCanvas_whenMinusOne() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-1, 1, 1, 1,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#canvasWidth} is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int); when minus one; then return canvasWidth is minus one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int)"})
  void testNewDefaultProcessDiagramCanvas_whenMinusOne_thenReturnCanvasWidthIsMinusOne() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-1, 1, 1, 1);

    // Assert
    assertEquals(-1, actualDefaultProcessDiagramCanvas.canvasWidth);
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <ul>
   *   <li>When nine.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#canvasHeight} is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int); when nine; then return canvasHeight is minus one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int)"})
  void testNewDefaultProcessDiagramCanvas_whenNine_thenReturnCanvasHeightIsMinusOne() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(9, -1, 1, 1);

    // Assert
    assertEquals(-1, actualDefaultProcessDiagramCanvas.canvasHeight);
    assertEquals(9, actualDefaultProcessDiagramCanvas.canvasWidth);
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#activityFontName} is {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int, String, String, String); when 'null'; then return activityFontName is 'Arial'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int, String, String, String)"})
  void testNewDefaultProcessDiagramCanvas_whenNull_thenReturnActivityFontNameIsArial() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1, null,
        null, null);

    // Assert
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.activityFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.annotationFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.labelFontName);
    FontMetrics fontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    assertEquals(10, fontMetrics.getAscent());
    assertEquals(10, fontMetrics.getMaxAscent());
    assertEquals(13, fontMetrics.getHeight());
    assertEquals(22, fontMetrics.getMaxAdvance());
    assertEquals(256, fontMetrics.getWidths().length);
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#canvasWidth} is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int); when one; then return canvasWidth is one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int)"})
  void testNewDefaultProcessDiagramCanvas_whenOne_thenReturnCanvasWidthIsOne() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Assert
    assertEquals(1, actualDefaultProcessDiagramCanvas.canvasWidth);
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <ul>
   *   <li>When two.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int); when two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int)"})
  void testNewDefaultProcessDiagramCanvas_whenTwo() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(2, 1, 1, 1);

    // Assert
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#minY} is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int); when two; then return minY is minus one")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int)"})
  void testNewDefaultProcessDiagramCanvas_whenTwo_thenReturnMinYIsMinusOne() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(2, 1, 1, -1);

    // Assert
    assertEquals(-1, actualDefaultProcessDiagramCanvas.minY);
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <ul>
   *   <li>When {@code UTF-8}.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#labelFontName} is {@code UTF-8}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int, String, String, String); when 'UTF-8'; then return labelFontName is 'UTF-8'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int, String, String, String)"})
  void testNewDefaultProcessDiagramCanvas_whenUtf8_thenReturnLabelFontNameIsUtf8() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-1, 1, 1, 1,
        "Activity Font Name", "UTF-8", "Annotation Font Name");

    // Assert
    assertEquals("UTF-8", actualDefaultProcessDiagramCanvas.labelFontName);
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    assertSame(expectedFontMetrics, processDiagramSVGGraphics2D.getFontMetrics());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#generateImage()}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#generateImage()}
   */
  @Test
  @DisplayName("Test generateImage()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"InputStream DefaultProcessDiagramCanvas.generateImage()"})
  void testGenerateImage() throws IOException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    InputStream actualGenerateImageResult = defaultProcessDiagramCanvas.generateImage();

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertNull(((GenericElementNS) lastChild).getFirstElementChild());
    assertNull(((GenericElementNS) lastChild).getLastElementChild());
    assertNull(((GenericElementNS) lastChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) lastChild).getXblLastElementChild());
    assertNull(((GenericElementNS) lastChild).getXblFirstChild());
    assertNull(((GenericElementNS) lastChild).getXblLastChild());
    assertNull(lastChild.getFirstChild());
    assertNull(lastChild.getLastChild());
    assertEquals(0, ((GenericElementNS) lastChild).getChildElementCount());
    byte[] byteArray = new byte[51];
    assertEquals(51, actualGenerateImageResult.read(byteArray));
    assertFalse(lastChild.hasChildNodes());
    assertArrayEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<!DOCTYPE sv".getBytes("UTF-8"), byteArray);
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#close()}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#close()}
   */
  @Test
  @DisplayName("Test close()")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.close()"})
  void testClose() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.close();

    // Assert
    assertTrue(defaultProcessDiagramCanvas.closed);
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneStartEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneStartEvent(String, GraphicInfo)"})
  void testDrawNoneStartEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawNoneStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneStartEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneStartEvent(String, GraphicInfo)"})
  void testDrawNoneStartEvent2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawNoneStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneStartEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneStartEvent(String, GraphicInfo)"})
  void testDrawNoneStartEvent3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawNoneStartEvent("42", graphicInfo);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneStartEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneStartEvent(String, GraphicInfo)"})
  void testDrawNoneStartEvent_givenGraphicInfoElementIsActivitiListener() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawNoneStartEvent("42", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code compensate}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneStartEvent(String, GraphicInfo); when 'compensate'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneStartEvent(String, GraphicInfo)"})
  void testDrawNoneStartEvent_whenCompensate() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawNoneStartEvent("compensate", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTimerStartEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTimerStartEvent(String, GraphicInfo)"})
  void testDrawTimerStartEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTimerStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTimerStartEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTimerStartEvent(String, GraphicInfo)"})
  void testDrawTimerStartEvent2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTimerStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTimerStartEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTimerStartEvent(String, GraphicInfo)"})
  void testDrawTimerStartEvent_given05_whenGraphicInfoHeightIs05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTimerStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTimerStartEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTimerStartEvent(String, GraphicInfo)"})
  void testDrawTimerStartEvent_givenGraphicInfoElementIsActivitiListener() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "g", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTimerStartEvent("42", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSignalStartEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSignalStartEvent(String, GraphicInfo)"})
  void testDrawSignalStartEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawSignalStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSignalStartEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSignalStartEvent(String, GraphicInfo)"})
  void testDrawSignalStartEvent2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawSignalStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSignalStartEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSignalStartEvent(String, GraphicInfo)"})
  void testDrawSignalStartEvent3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 7, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawSignalStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSignalStartEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSignalStartEvent(String, GraphicInfo)"})
  void testDrawSignalStartEvent_given05_whenGraphicInfoHeightIs05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawSignalStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSignalStartEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSignalStartEvent(String, GraphicInfo)"})
  void testDrawSignalStartEvent_givenGraphicInfoElementIsActivitiListener() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "g", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawSignalStartEvent("42", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMessageStartEvent(String, GraphicInfo)"})
  void testDrawMessageStartEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawMessageStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMessageStartEvent(String, GraphicInfo)"})
  void testDrawMessageStartEvent2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawMessageStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMessageStartEvent(String, GraphicInfo)"})
  void testDrawMessageStartEvent_given05_whenGraphicInfoHeightIs05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawMessageStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMessageStartEvent(String, GraphicInfo)"})
  void testDrawMessageStartEvent_givenGraphicInfoElementIsActivitiListener() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "g", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawMessageStartEvent("42", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  @DisplayName("Test drawStartEvent(String, GraphicInfo, IconType)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawStartEvent(String, GraphicInfo, IconType)"})
  void testDrawStartEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawStartEvent("42", graphicInfo, null);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  @DisplayName("Test drawStartEvent(String, GraphicInfo, IconType)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawStartEvent(String, GraphicInfo, IconType)"})
  void testDrawStartEvent2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawStartEvent("42", graphicInfo, null);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  @DisplayName("Test drawStartEvent(String, GraphicInfo, IconType); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawStartEvent(String, GraphicInfo, IconType)"})
  void testDrawStartEvent_givenGraphicInfoElementIsActivitiListener() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawStartEvent("42", graphicInfo2, null);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}.
   * <ul>
   *   <li>When {@link CompensateIconType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  @DisplayName("Test drawStartEvent(String, GraphicInfo, IconType); when CompensateIconType (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawStartEvent(String, GraphicInfo, IconType)"})
  void testDrawStartEvent_whenCompensateIconType() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawStartEvent("42", graphicInfo, new CompensateIconType());

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}.
   * <ul>
   *   <li>When {@link CompensateIconType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  @DisplayName("Test drawStartEvent(String, GraphicInfo, IconType); when CompensateIconType (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawStartEvent(String, GraphicInfo, IconType)"})
  void testDrawStartEvent_whenCompensateIconType2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawStartEvent(null, graphicInfo, new CompensateIconType());

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  void testDrawNoneEndEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawNoneEndEvent("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  void testDrawNoneEndEvent2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawNoneEndEvent("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  void testDrawNoneEndEvent3() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawNoneEndEvent("42", "Name", graphicInfo2);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  void testDrawNoneEndEvent4() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTextAnnotation("42", "id", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawNoneEndEvent("42", "Name", graphicInfo2);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  void testDrawNoneEndEvent_given05_whenGraphicInfoHeightIs05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawNoneEndEvent("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  void testDrawNoneEndEvent_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawNoneEndEvent("42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  void testDrawNoneEndEvent_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawNoneEndEvent("42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  void testDrawErrorEndEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawErrorEndEvent("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  void testDrawErrorEndEvent2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawErrorEndEvent("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  void testDrawErrorEndEvent3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawErrorEndEvent("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  void testDrawErrorEndEvent4() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawErrorEndEvent("42", "Name", graphicInfo2);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  void testDrawErrorEndEvent5() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTextAnnotation("42", "id", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawErrorEndEvent("42", "Name", graphicInfo2);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  void testDrawErrorEndEvent_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawErrorEndEvent("42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  void testDrawErrorEndEvent_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawErrorEndEvent("42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorStartEvent(String, GraphicInfo)"})
  void testDrawErrorStartEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawErrorStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorStartEvent(String, GraphicInfo)"})
  void testDrawErrorStartEvent2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawErrorStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorStartEvent(String, GraphicInfo)"})
  void testDrawErrorStartEvent_given05_whenGraphicInfoHeightIs05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawErrorStartEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorStartEvent(String, GraphicInfo)"})
  void testDrawErrorStartEvent_givenGraphicInfoElementIsActivitiListener() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(4.0d);
    graphicInfo.setWidth(4.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawErrorStartEvent("42", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code fill}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo); when 'fill'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorStartEvent(String, GraphicInfo)"})
  void testDrawErrorStartEvent_whenFill() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawErrorStartEvent("fill", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  void testDrawCatchingEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingEvent("42", graphicInfo, true, null, "Event Type");

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  void testDrawCatchingEvent2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingEvent("42", graphicInfo, true, null, "Event Type");

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  void testDrawCatchingEvent3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingEvent("42", graphicInfo, false, null, "Event Type");

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <ul>
   *   <li>Given {@code 0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String); given '0.5'; when GraphicInfo (default constructor) Height is '0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  void testDrawCatchingEvent_given05_whenGraphicInfoHeightIs05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingEvent("42", graphicInfo, true, null, "Event Type");

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  void testDrawCatchingEvent_givenGraphicInfoElementIsActivitiListener() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingEvent("42", graphicInfo2, true, null, "Event Type");

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <ul>
   *   <li>When {@link CompensateIconType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String); when CompensateIconType (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  void testDrawCatchingEvent_whenCompensateIconType() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingEvent("42", graphicInfo, true, new CompensateIconType(), "Event Type");

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <ul>
   *   <li>When {@code timer}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String); when 'timer'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  void testDrawCatchingEvent_whenTimer() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingEvent("42", graphicInfo, true, new CompensateIconType(), "timer");

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawConditionalSequenceFlowIndicator(new Double(2.0d, 2.0d, 2.0d, 2.0d));

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", graphicInfo, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting6() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "compensate", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting_given05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", "", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", "Name", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting6() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "compensate", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", "Name", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    assertTrue(root.getLastChild() instanceof GenericElementNS);
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting_given05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingCompensateEvent("42", null, graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting5() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "timer", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting_given05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", "Name", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting5() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "timer", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", "Name", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    assertTrue(root.getLastChild() instanceof GenericElementNS);
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting_given05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", "", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingTimerEvent("42", null, graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting5() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "error", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting_given05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", "Name", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting5() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "error", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", "Name", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    assertTrue(root.getLastChild() instanceof GenericElementNS);
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting_given05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", "", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", null, graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting5() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "signal", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting_given05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    assertTrue(root.getLastChild() instanceof GenericElementNS);
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", "Name", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 100, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    assertTrue(root.getLastChild() instanceof GenericElementNS);
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting6() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "signal", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", "Name", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    assertTrue(root.getLastChild() instanceof GenericElementNS);
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting_given05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", "", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", null, graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting5() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "message", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)} with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting_given05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", "Name", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting5() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "message", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", "Name", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    assertTrue(root.getLastChild() instanceof GenericElementNS);
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting_given05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", "", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingMessageEvent("42", null, graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingCompensateEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingCompensateEvent(String, GraphicInfo)"})
  void testDrawThrowingCompensateEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingCompensateEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingCompensateEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingCompensateEvent(String, GraphicInfo)"})
  void testDrawThrowingCompensateEvent2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(7, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingCompensateEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingCompensateEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingCompensateEvent(String, GraphicInfo)"})
  void testDrawThrowingCompensateEvent_given05_whenGraphicInfoHeightIs05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingCompensateEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingCompensateEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingCompensateEvent(String, GraphicInfo)"})
  void testDrawThrowingCompensateEvent_givenGraphicInfoElementIsActivitiListener() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "compensate", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingCompensateEvent("42", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>When {@link GraphicInfo} (default constructor) Height is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingCompensateEvent(String, GraphicInfo); when GraphicInfo (default constructor) Height is ten")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingCompensateEvent(String, GraphicInfo)"})
  void testDrawThrowingCompensateEvent_whenGraphicInfoHeightIsTen() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingCompensateEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>When {@link GraphicInfo} (default constructor) Height is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingCompensateEvent(String, GraphicInfo); when GraphicInfo (default constructor) Height is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingCompensateEvent(String, GraphicInfo)"})
  void testDrawThrowingCompensateEvent_whenGraphicInfoHeightIsTwo() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingCompensateEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingSignalEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingSignalEvent(String, GraphicInfo)"})
  void testDrawThrowingSignalEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingSignalEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingSignalEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingSignalEvent(String, GraphicInfo)"})
  void testDrawThrowingSignalEvent_given05_whenGraphicInfoHeightIs05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingSignalEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingSignalEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingSignalEvent(String, GraphicInfo)"})
  void testDrawThrowingSignalEvent_givenGraphicInfoElementIsActivitiListener() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(2.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "signal", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingSignalEvent("42", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link Integer#MIN_VALUE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingSignalEvent(String, GraphicInfo); given MIN_VALUE")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingSignalEvent(String, GraphicInfo)"})
  void testDrawThrowingSignalEvent_givenMin_value() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(Integer.MIN_VALUE);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingSignalEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>When {@link GraphicInfo} (default constructor) Height is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingSignalEvent(String, GraphicInfo); when GraphicInfo (default constructor) Height is ten")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingSignalEvent(String, GraphicInfo)"})
  void testDrawThrowingSignalEvent_whenGraphicInfoHeightIsTen() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingSignalEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>When {@link GraphicInfo} (default constructor) Height is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingSignalEvent(String, GraphicInfo); when GraphicInfo (default constructor) Height is two")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingSignalEvent(String, GraphicInfo)"})
  void testDrawThrowingSignalEvent_whenGraphicInfoHeightIsTwo() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(2.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingSignalEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingNoneEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingNoneEvent(String, GraphicInfo)"})
  void testDrawThrowingNoneEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingNoneEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingNoneEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingNoneEvent(String, GraphicInfo)"})
  void testDrawThrowingNoneEvent2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingNoneEvent("42", graphicInfo);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingNoneEvent(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingNoneEvent(String, GraphicInfo)"})
  void testDrawThrowingNoneEvent3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    ActivitiListener element = new ActivitiListener();
    element.addExtensionElement(new ExtensionElement());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(element);
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingNoneEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code 0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingNoneEvent(String, GraphicInfo); given '0.5'; when GraphicInfo (default constructor) Height is '0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingNoneEvent(String, GraphicInfo)"})
  void testDrawThrowingNoneEvent_given05_whenGraphicInfoHeightIs05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingNoneEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingNoneEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingNoneEvent(String, GraphicInfo)"})
  void testDrawThrowingNoneEvent_givenGraphicInfoElementIsActivitiListener() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "none", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingNoneEvent("42", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>When {@link GraphicInfo} (default constructor) Height is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingNoneEvent(String, GraphicInfo); when GraphicInfo (default constructor) Height is ten")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingNoneEvent(String, GraphicInfo)"})
  void testDrawThrowingNoneEvent_whenGraphicInfoHeightIsTen() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawThrowingNoneEvent("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean)"})
  void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditional() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean)"})
  void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditional2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean)"})
  void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditional3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean, boolean)"})
  void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditionalHighLighted() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, true, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean, boolean)"})
  void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditionalHighLighted2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, false, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean, boolean)"})
  void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditionalHighLighted3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, true, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean, boolean)"})
  void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditionalHighLighted4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);
    defaultProcessDiagramCanvas
        .drawArrowHead(new Double(1.5707963267948966d, 1.5707963267948966d, 1.5707963267948966d, 1.5707963267948966d));

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, true, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)} with {@code xPoints}, {@code yPoints}, {@code conditional}, {@code isDefault}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int[], int[], boolean, boolean, boolean) with 'xPoints', 'yPoints', 'conditional', 'isDefault', 'highLighted'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int[], int[], boolean, boolean, boolean)"})
  void testDrawSequenceflowWithXPointsYPointsConditionalIsDefaultHighLighted() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5}, true, true, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)} with {@code xPoints}, {@code yPoints}, {@code conditional}, {@code isDefault}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int[], int[], boolean, boolean, boolean) with 'xPoints', 'yPoints', 'conditional', 'isDefault', 'highLighted'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int[], int[], boolean, boolean, boolean)"})
  void testDrawSequenceflowWithXPointsYPointsConditionalIsDefaultHighLighted2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, 2}, true, true, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)} with {@code xPoints}, {@code yPoints}, {@code conditional}, {@code isDefault}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int[], int[], boolean, boolean, boolean) with 'xPoints', 'yPoints', 'conditional', 'isDefault', 'highLighted'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int[], int[], boolean, boolean, boolean)"})
  void testDrawSequenceflowWithXPointsYPointsConditionalIsDefaultHighLighted3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5}, false, true, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)} with {@code xPoints}, {@code yPoints}, {@code conditional}, {@code isDefault}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int[], int[], boolean, boolean, boolean) with 'xPoints', 'yPoints', 'conditional', 'isDefault', 'highLighted'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int[], int[], boolean, boolean, boolean)"})
  void testDrawSequenceflowWithXPointsYPointsConditionalIsDefaultHighLighted4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5}, true, false, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)} with {@code xPoints}, {@code yPoints}, {@code conditional}, {@code isDefault}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int[], int[], boolean, boolean, boolean) with 'xPoints', 'yPoints', 'conditional', 'isDefault', 'highLighted'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int[], int[], boolean, boolean, boolean)"})
  void testDrawSequenceflowWithXPointsYPointsConditionalIsDefaultHighLighted5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5}, true, true, false);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawAssociation(int[], int[], AssociationDirection, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawAssociation(int[], int[], AssociationDirection, boolean)"})
  void testDrawAssociation() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawAssociation(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5},
        AssociationDirection.NONE, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawAssociation(int[], int[], AssociationDirection, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawAssociation(int[], int[], AssociationDirection, boolean)"})
  void testDrawAssociation2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawAssociation(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5},
        AssociationDirection.ONE, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawAssociation(int[], int[], AssociationDirection, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawAssociation(int[], int[], AssociationDirection, boolean)"})
  void testDrawAssociation3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawAssociation(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5},
        AssociationDirection.BOTH, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  void testDrawConnection() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawConnection(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5}, true, true,
        "Connection Type", AssociationDirection.NONE, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  void testDrawConnection2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawConnection(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5}, false, true,
        "Connection Type", AssociationDirection.NONE, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  void testDrawConnection3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawConnection(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5}, true, false,
        "Connection Type", AssociationDirection.NONE, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  void testDrawConnection4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawConnection(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5}, true, true,
        "Connection Type", AssociationDirection.ONE, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  void testDrawConnection5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawConnection(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5}, true, true,
        "Connection Type", AssociationDirection.BOTH, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(6, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  void testDrawConnection6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawConnection(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5}, true, true,
        "Connection Type", AssociationDirection.NONE, false);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <ul>
   *   <li>When {@code association}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean); when 'association'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  void testDrawConnection_whenAssociation() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawConnection(new int[]{1, -5, 1, -5}, new int[]{1, -5, 1, -5}, true, true,
        "association", AssociationDirection.NONE, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflowWithoutArrow(int, int, int, int, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(int, int, int, int, boolean)"})
  void testDrawSequenceflowWithoutArrowWithSrcXSrcYTargetXTargetYConditional() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(1, 1, 1, 1, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflowWithoutArrow(int, int, int, int, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(int, int, int, int, boolean)"})
  void testDrawSequenceflowWithoutArrowWithSrcXSrcYTargetXTargetYConditional2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(1, 1, 1, 1, false);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflowWithoutArrow(int, int, int, int, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(int, int, int, int, boolean)"})
  void testDrawSequenceflowWithoutArrowWithSrcXSrcYTargetXTargetYConditional3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(1, 1, 1, 1, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)"})
  void testDrawSequenceflowWithoutArrowWithSrcXSrcYTargetXTargetYConditionalHighLighted() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(1, 1, 1, 1, true, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)"})
  void testDrawSequenceflowWithoutArrowWithSrcXSrcYTargetXTargetYConditionalHighLighted2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(1, 1, 1, 1, false, false);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)"})
  void testDrawSequenceflowWithoutArrowWithSrcXSrcYTargetXTargetYConditionalHighLighted3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(1, 1, 1, 1, true, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)} with {@code icon}, {@code id}, {@code name}, {@code graphicInfo}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(TaskIconType, String, String, GraphicInfo) with 'icon', 'id', 'name', 'graphicInfo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(TaskIconType, String, String, GraphicInfo)"})
  void testDrawTaskWithIconIdNameGraphicInfo() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BusinessRuleTaskIconType icon = new BusinessRuleTaskIconType();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask(icon, "42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)} with {@code icon}, {@code id}, {@code name}, {@code graphicInfo}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(TaskIconType, String, String, GraphicInfo) with 'icon', 'id', 'name', 'graphicInfo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(TaskIconType, String, String, GraphicInfo)"})
  void testDrawTaskWithIconIdNameGraphicInfo2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);
    BusinessRuleTaskIconType icon = new BusinessRuleTaskIconType();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask(icon, "42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)} with {@code icon}, {@code id}, {@code name}, {@code graphicInfo}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(TaskIconType, String, String, GraphicInfo) with 'icon', 'id', 'name', 'graphicInfo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(TaskIconType, String, String, GraphicInfo)"})
  void testDrawTaskWithIconIdNameGraphicInfo3() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawParallelGateway("42", graphicInfo);
    BusinessRuleTaskIconType icon = new BusinessRuleTaskIconType();

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask(icon, "42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)} with {@code icon}, {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>Given {@code Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(TaskIconType, String, String, GraphicInfo) with 'icon', 'id', 'name', 'graphicInfo'; given 'java.lang.Object'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(TaskIconType, String, String, GraphicInfo)"})
  void testDrawTaskWithIconIdNameGraphicInfo_givenJavaLangObject() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    Class<Object> type = Object.class;
    defaultProcessDiagramCanvas.drawExpandedSubProcess("42", "id", graphicInfo, type);
    BusinessRuleTaskIconType icon = new BusinessRuleTaskIconType();

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask(icon, "42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)} with {@code icon}, {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(TaskIconType, String, String, GraphicInfo) with 'icon', 'id', 'name', 'graphicInfo'; when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(TaskIconType, String, String, GraphicInfo)"})
  void testDrawTaskWithIconIdNameGraphicInfo_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BusinessRuleTaskIconType icon = new BusinessRuleTaskIconType();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask(icon, "42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)} with {@code icon}, {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(TaskIconType, String, String, GraphicInfo) with 'icon', 'id', 'name', 'graphicInfo'; when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(TaskIconType, String, String, GraphicInfo)"})
  void testDrawTaskWithIconIdNameGraphicInfo_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    BusinessRuleTaskIconType icon = new BusinessRuleTaskIconType();

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask(icon, "42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)} with {@code id}, {@code name}, {@code graphicInfo}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo) with 'id', 'name', 'graphicInfo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo)"})
  void testDrawTaskWithIdNameGraphicInfo() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)} with {@code id}, {@code name}, {@code graphicInfo}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo) with 'id', 'name', 'graphicInfo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo)"})
  void testDrawTaskWithIdNameGraphicInfo2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)} with {@code id}, {@code name}, {@code graphicInfo}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo) with 'id', 'name', 'graphicInfo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo)"})
  void testDrawTaskWithIdNameGraphicInfo3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, -32);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  void testDrawTaskWithIdNameGraphicInfoThickBorder() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  void testDrawTaskWithIdNameGraphicInfoThickBorder2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  void testDrawTaskWithIdNameGraphicInfoThickBorder3() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTextAnnotation("42", "id", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo2, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <ul>
   *   <li>Given {@code Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'; given 'java.lang.Object'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  void testDrawTaskWithIdNameGraphicInfoThickBorder_givenJavaLangObject() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    Class<Object> type = Object.class;
    defaultProcessDiagramCanvas.drawExpandedSubProcess("42", "id", graphicInfo, type);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'; when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  void testDrawTaskWithIdNameGraphicInfoThickBorder_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", "", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'; when 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  void testDrawTaskWithIdNameGraphicInfoThickBorder_whenFalse() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'; when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  void testDrawTaskWithIdNameGraphicInfoThickBorder_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", null, graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)} with {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo) with 'id', 'name', 'graphicInfo'; given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo)"})
  void testDrawTaskWithIdNameGraphicInfo_givenGraphicInfoElementIsActivitiListener() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    Class<Object> type = Object.class;
    defaultProcessDiagramCanvas.drawExpandedSubProcess("42", "id", graphicInfo, type);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)} with {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo) with 'id', 'name', 'graphicInfo'; when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo)"})
  void testDrawTaskWithIdNameGraphicInfo_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)} with {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo) with 'id', 'name', 'graphicInfo'; when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo)"})
  void testDrawTaskWithIdNameGraphicInfo_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTask("42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawPoolOrLane(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawPoolOrLane(String, String, GraphicInfo)"})
  void testDrawPoolOrLane() throws DOMException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawPoolOrLane("42", "Name", graphicInfo);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals("...", root.getTextContent());
    assertEquals("...", lastChild.getTextContent());
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawPoolOrLane(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawPoolOrLane(String, String, GraphicInfo)"})
  void testDrawPoolOrLane2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-6, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawPoolOrLane("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawPoolOrLane(String, String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor); when 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawPoolOrLane(String, String, GraphicInfo)"})
  void testDrawPoolOrLane_givenGraphicInfoElementIsActivitiListener_whenName() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(4.71238898038469d);
    graphicInfo.setWidth(4.71238898038469d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawPoolOrLane("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawPoolOrLane(String, String, GraphicInfo); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawPoolOrLane(String, String, GraphicInfo)"})
  void testDrawPoolOrLane_whenEmptyString() throws DOMException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawPoolOrLane("42", "", graphicInfo);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals("", root.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawPoolOrLane(String, String, GraphicInfo); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawPoolOrLane(String, String, GraphicInfo)"})
  void testDrawPoolOrLane_whenNull() throws DOMException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawPoolOrLane("42", null, graphicInfo);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals("", root.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineCentredText(String, int, int, int, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineCentredText(String, int, int, int, int)"})
  void testDrawMultilineCentredText() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineCentredText("Text", 2, 3, 1, 1);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineCentredText(String, int, int, int, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineCentredText(String, int, int, int, int)"})
  void testDrawMultilineCentredText2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineCentredText("Text", 2, 3, 1, -1);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineCentredText(String, int, int, int, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineCentredText(String, int, int, int, int)"})
  void testDrawMultilineCentredText3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineCentredText("Text", 2, 3, 1, 1);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <ul>
   *   <li>When {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineCentredText(String, int, int, int, int); when 'Arial'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineCentredText(String, int, int, int, int)"})
  void testDrawMultilineCentredText_whenArial() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineCentredText("Arial", 2, 3, Float.PRECISION, 12);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <ul>
   *   <li>When {@link Float#PRECISION}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineCentredText(String, int, int, int, int); when PRECISION")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineCentredText(String, int, int, int, int)"})
  void testDrawMultilineCentredText_whenPrecision() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineCentredText("Text", 2, 3, Float.PRECISION, 12);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <ul>
   *   <li>When twelve.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineCentredText(String, int, int, int, int); when twelve")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineCentredText(String, int, int, int, int)"})
  void testDrawMultilineCentredText_whenTwelve() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineCentredText("Text", 2, 3, 1, 12);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineAnnotationText(String, int, int, int, int)"})
  void testDrawMultilineAnnotationText() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineAnnotationText("Text", 2, 3, 1, 1);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineAnnotationText(String, int, int, int, int)"})
  void testDrawMultilineAnnotationText2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineAnnotationText("Text", 2, 3, 1, -1);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineAnnotationText(String, int, int, int, int)"})
  void testDrawMultilineAnnotationText3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineAnnotationText("Text", 2, 3, 1, 1);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <ul>
   *   <li>When {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int); when 'Arial'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineAnnotationText(String, int, int, int, int)"})
  void testDrawMultilineAnnotationText_whenArial() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineAnnotationText("Arial", 2, 3, Float.PRECISION, 12);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <ul>
   *   <li>When {@link Float#PRECISION}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int); when PRECISION")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineAnnotationText(String, int, int, int, int)"})
  void testDrawMultilineAnnotationText_whenPrecision() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineAnnotationText("Text", 2, 3, Float.PRECISION, 12);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <ul>
   *   <li>When twelve.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int); when twelve")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineAnnotationText(String, int, int, int, int)"})
  void testDrawMultilineAnnotationText_whenTwelve() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineAnnotationText("Text", 2, 3, 1, 12);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineText(String, int, int, int, int, boolean)"})
  void testDrawMultilineText() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineText("Text", 2, 3, 1, 1, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineText(String, int, int, int, int, boolean)"})
  void testDrawMultilineText2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineText("Text", 2, 3, 1, -1, false);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineText(String, int, int, int, int, boolean)"})
  void testDrawMultilineText3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineText("Text", 2, 3, 1, 1, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <ul>
   *   <li>When {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean); when 'Arial'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineText(String, int, int, int, int, boolean)"})
  void testDrawMultilineText_whenArial() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineText("Arial", 2, 3, Float.PRECISION, 12, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <ul>
   *   <li>When {@link Float#PRECISION}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean); when PRECISION")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineText(String, int, int, int, int, boolean)"})
  void testDrawMultilineText_whenPrecision() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineText("Text", 2, 3, Float.PRECISION, 12, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <ul>
   *   <li>When twelve.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean); when twelve")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineText(String, int, int, int, int, boolean)"})
  void testDrawMultilineText_whenTwelve() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineText("Text", 2, 3, 1, 12, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <ul>
   *   <li>When zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean); when zero")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineText(String, int, int, int, int, boolean)"})
  void testDrawMultilineText_whenZero() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultilineText("Text", 2, 3, 1, 0, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#fitTextToWidth(String, int)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#fitTextToWidth(String, int)}
   */
  @Test
  @DisplayName("Test fitTextToWidth(String, int); when empty string; then return empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultProcessDiagramCanvas.fitTextToWidth(String, int)"})
  void testFitTextToWidth_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", (new DefaultProcessDiagramCanvas(1, 1, 1, 1)).fitTextToWidth("", 1));
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#fitTextToWidth(String, int)}.
   * <ul>
   *   <li>When fifty-one.</li>
   *   <li>Then return {@code Origi...}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#fitTextToWidth(String, int)}
   */
  @Test
  @DisplayName("Test fitTextToWidth(String, int); when fifty-one; then return 'Origi...'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultProcessDiagramCanvas.fitTextToWidth(String, int)"})
  void testFitTextToWidth_whenFiftyOne_thenReturnOrigi() {
    // Arrange, Act and Assert
    assertEquals("Origi...", (new DefaultProcessDiagramCanvas(1, 1, 1, 1)).fitTextToWidth("Original", 51));
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#fitTextToWidth(String, int)}.
   * <ul>
   *   <li>When {@code Original}.</li>
   *   <li>Then return {@code ...}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#fitTextToWidth(String, int)}
   */
  @Test
  @DisplayName("Test fitTextToWidth(String, int); when 'Original'; then return '...'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"String DefaultProcessDiagramCanvas.fitTextToWidth(String, int)"})
  void testFitTextToWidth_whenOriginal_thenReturnDotDotDot() {
    // Arrange, Act and Assert
    assertEquals("...", (new DefaultProcessDiagramCanvas(1, 1, 1, 1)).fitTextToWidth("Original", 1));
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawUserTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawUserTask(String, String, GraphicInfo)"})
  void testDrawUserTask() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawUserTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawUserTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawUserTask(String, String, GraphicInfo)"})
  void testDrawUserTask2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawUserTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawUserTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawUserTask(String, String, GraphicInfo)"})
  void testDrawUserTask3() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawUserTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawUserTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawUserTask(String, String, GraphicInfo)"})
  void testDrawUserTask4() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawParallelGateway("42", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawUserTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawUserTask(String, String, GraphicInfo); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawUserTask(String, String, GraphicInfo)"})
  void testDrawUserTask_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawUserTask("42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawUserTask(String, String, GraphicInfo); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawUserTask(String, String, GraphicInfo)"})
  void testDrawUserTask_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawUserTask("42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawScriptTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawScriptTask(String, String, GraphicInfo)"})
  void testDrawScriptTask() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawScriptTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawScriptTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawScriptTask(String, String, GraphicInfo)"})
  void testDrawScriptTask2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawScriptTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawScriptTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawScriptTask(String, String, GraphicInfo)"})
  void testDrawScriptTask3() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawScriptTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawScriptTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawScriptTask(String, String, GraphicInfo)"})
  void testDrawScriptTask4() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawParallelGateway("42", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawScriptTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawScriptTask(String, String, GraphicInfo); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawScriptTask(String, String, GraphicInfo)"})
  void testDrawScriptTask_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawScriptTask("42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawScriptTask(String, String, GraphicInfo); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawScriptTask(String, String, GraphicInfo)"})
  void testDrawScriptTask_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawScriptTask("42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawServiceTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawServiceTask(String, String, GraphicInfo)"})
  void testDrawServiceTask() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawServiceTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawServiceTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawServiceTask(String, String, GraphicInfo)"})
  void testDrawServiceTask2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawServiceTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawServiceTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawServiceTask(String, String, GraphicInfo)"})
  void testDrawServiceTask3() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawServiceTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawServiceTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawServiceTask(String, String, GraphicInfo)"})
  void testDrawServiceTask4() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawParallelGateway("42", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawServiceTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawServiceTask(String, String, GraphicInfo); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawServiceTask(String, String, GraphicInfo)"})
  void testDrawServiceTask_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawServiceTask("42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawServiceTask(String, String, GraphicInfo); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawServiceTask(String, String, GraphicInfo)"})
  void testDrawServiceTask_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawServiceTask("42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawReceiveTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawReceiveTask(String, String, GraphicInfo)"})
  void testDrawReceiveTask() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawReceiveTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawReceiveTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawReceiveTask(String, String, GraphicInfo)"})
  void testDrawReceiveTask2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawReceiveTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawReceiveTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawReceiveTask(String, String, GraphicInfo)"})
  void testDrawReceiveTask3() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawReceiveTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawReceiveTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawReceiveTask(String, String, GraphicInfo)"})
  void testDrawReceiveTask4() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawParallelGateway("42", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawReceiveTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawReceiveTask(String, String, GraphicInfo); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawReceiveTask(String, String, GraphicInfo)"})
  void testDrawReceiveTask_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawReceiveTask("42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawReceiveTask(String, String, GraphicInfo); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawReceiveTask(String, String, GraphicInfo)"})
  void testDrawReceiveTask_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawReceiveTask("42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSendTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSendTask(String, String, GraphicInfo)"})
  void testDrawSendTask() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawSendTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSendTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSendTask(String, String, GraphicInfo)"})
  void testDrawSendTask2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawSendTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSendTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSendTask(String, String, GraphicInfo)"})
  void testDrawSendTask3() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawSendTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSendTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSendTask(String, String, GraphicInfo)"})
  void testDrawSendTask4() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawParallelGateway("42", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawSendTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSendTask(String, String, GraphicInfo); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSendTask(String, String, GraphicInfo)"})
  void testDrawSendTask_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawSendTask("42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSendTask(String, String, GraphicInfo); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSendTask(String, String, GraphicInfo)"})
  void testDrawSendTask_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawSendTask("42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  void testDrawManualTask() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawManualTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  void testDrawManualTask2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawManualTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  void testDrawManualTask3() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawManualTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  void testDrawManualTask4() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawParallelGateway("42", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawManualTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  void testDrawManualTask_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawManualTask("42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  void testDrawManualTask_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawManualTask("42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawBusinessRuleTask(String, String, GraphicInfo)"})
  void testDrawBusinessRuleTask() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawBusinessRuleTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawBusinessRuleTask(String, String, GraphicInfo)"})
  void testDrawBusinessRuleTask2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawBusinessRuleTask("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawBusinessRuleTask(String, String, GraphicInfo)"})
  void testDrawBusinessRuleTask3() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawBusinessRuleTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawBusinessRuleTask(String, String, GraphicInfo)"})
  void testDrawBusinessRuleTask4() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawParallelGateway("42", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawBusinessRuleTask("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawBusinessRuleTask(String, String, GraphicInfo)"})
  void testDrawBusinessRuleTask_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawBusinessRuleTask("42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawBusinessRuleTask(String, String, GraphicInfo)"})
  void testDrawBusinessRuleTask_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawBusinessRuleTask("42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  void testDrawExpandedSubProcess() throws DOMException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    Class<Object> type = Object.class;

    // Act
    defaultProcessDiagramCanvas.drawExpandedSubProcess("42", "Name", graphicInfo, type);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals("...", root.getTextContent());
    assertEquals("...", lastChild.getTextContent());
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  void testDrawExpandedSubProcess2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    Class<Object> type = Object.class;

    // Act
    defaultProcessDiagramCanvas.drawExpandedSubProcess("42", "Name", graphicInfo, type);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  void testDrawExpandedSubProcess3() throws DOMException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    Class<Object> type = Object.class;

    // Act
    defaultProcessDiagramCanvas.drawExpandedSubProcess("42", null, graphicInfo, type);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals("", root.getTextContent());
    assertEquals("", lastChild.getTextContent());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  void testDrawExpandedSubProcess4() throws DOMException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 17, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    Class<Object> type = Object.class;

    // Act
    defaultProcessDiagramCanvas.drawExpandedSubProcess("42", "Name", graphicInfo, type);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals("...", root.getTextContent());
    assertEquals("...", lastChild.getTextContent());
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  void testDrawExpandedSubProcess_given05_whenGraphicInfoHeightIs05() throws DOMException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(-0.5d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    Class<Object> type = Object.class;

    // Act
    defaultProcessDiagramCanvas.drawExpandedSubProcess("42", "Name", graphicInfo, type);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals("...", root.getTextContent());
    assertEquals("...", lastChild.getTextContent());
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  void testDrawExpandedSubProcess_givenGraphicInfoElementIsActivitiListener() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(8.0d);
    graphicInfo.setWidth(8.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "Name", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);
    Class<Object> type = Object.class;

    // Act
    defaultProcessDiagramCanvas.drawExpandedSubProcess("42", "Name", graphicInfo2, type);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  void testDrawExpandedSubProcess_whenEmptyString() throws DOMException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);
    Class<Object> type = Object.class;

    // Act
    defaultProcessDiagramCanvas.drawExpandedSubProcess("42", "", graphicInfo, type);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals("", root.getTextContent());
    assertEquals("", lastChild.getTextContent());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)"})
  void testDrawCollapsedSubProcess() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedSubProcess("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor); when 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)"})
  void testDrawCollapsedSubProcess_givenGraphicInfoElementIsActivitiListener_whenName() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedSubProcess("42", "Name", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)"})
  void testDrawCollapsedSubProcess_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedSubProcess("42", "", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean); when 'Name'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)"})
  void testDrawCollapsedSubProcess_whenName() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedSubProcess("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)"})
  void testDrawCollapsedSubProcess_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedSubProcess("42", null, graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedCallActivity(String, String, GraphicInfo)"})
  void testDrawCollapsedCallActivity() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedCallActivity("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedCallActivity(String, String, GraphicInfo)"})
  void testDrawCollapsedCallActivity2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedCallActivity("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedCallActivity(String, String, GraphicInfo)"})
  void testDrawCollapsedCallActivity3() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedCallActivity("42", "Name", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedCallActivity(String, String, GraphicInfo)"})
  void testDrawCollapsedCallActivity4() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTextAnnotation("42", "id", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedCallActivity("42", "Name", graphicInfo2);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedCallActivity(String, String, GraphicInfo)"})
  void testDrawCollapsedCallActivity_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedCallActivity("42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedCallActivity(String, String, GraphicInfo)"})
  void testDrawCollapsedCallActivity_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedCallActivity("42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  void testDrawCollapsedTask() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedTask("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  void testDrawCollapsedTask2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedTask("42", "Name", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  void testDrawCollapsedTask3() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedTask("42", "Name", graphicInfo2, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  void testDrawCollapsedTask4() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTextAnnotation("42", "id", graphicInfo);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedTask("42", "Name", graphicInfo2, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  void testDrawCollapsedTask_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedTask("42", "", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean); when 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  void testDrawCollapsedTask_whenFalse() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedTask("42", "Name", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  void testDrawCollapsedTask_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedTask("42", null, graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedMarker(int, int, int, int)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedMarker(int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawCollapsedMarker(int, int, int, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedMarker(int, int, int, int)"})
  void testDrawCollapsedMarker() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedMarker(2, 3, 1, 1);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedMarker(int, int, int, int)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedMarker(int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawCollapsedMarker(int, int, int, int)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedMarker(int, int, int, int)"})
  void testDrawCollapsedMarker2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(2, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawCollapsedMarker(2, 3, 10, 1);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)"})
  void testDrawActivityMarkers() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawActivityMarkers(2, 3, 1, 1, true, true, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)"})
  void testDrawActivityMarkers2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawActivityMarkers(2, 3, 1, 1, false, false, false);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)"})
  void testDrawActivityMarkers3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawActivityMarkers(2, 3, 1, 1, false, true, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)"})
  void testDrawActivityMarkers4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawActivityMarkers(2, 3, 1, 1, true, true, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)"})
  void testDrawActivityMarkers5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawActivityMarkers(2, 3, 1, 1, false, false, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)"})
  void testDrawActivityMarkers6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawActivityMarkers(2, 3, 1, 1, false, true, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawGateway(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawGateway(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGateway(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawGateway(GraphicInfo)"})
  void testDrawGateway() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawGateway(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawGateway(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawGateway(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGateway(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawGateway(GraphicInfo)"})
  void testDrawGateway2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawGateway(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLightCompleted(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawGatewayHighLightCompleted(GraphicInfo)"})
  void testDrawGatewayHighLightCompleted() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawGatewayHighLightCompleted(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLightCompleted(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawGatewayHighLightCompleted(GraphicInfo)"})
  void testDrawGatewayHighLightCompleted2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawGatewayHighLightCompleted(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLightErrored(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawGatewayHighLightErrored(GraphicInfo)"})
  void testDrawGatewayHighLightErrored() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawGatewayHighLightErrored(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLightErrored(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawGatewayHighLightErrored(GraphicInfo)"})
  void testDrawGatewayHighLightErrored2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawGatewayHighLightErrored(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawParallelGateway(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawParallelGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawParallelGateway(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawParallelGateway(String, GraphicInfo)"})
  void testDrawParallelGateway() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawParallelGateway("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawParallelGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawParallelGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawParallelGateway(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawParallelGateway(String, GraphicInfo)"})
  void testDrawParallelGateway_givenGraphicInfoElementIsActivitiListener() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawParallelGateway("42", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawExclusiveGateway(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExclusiveGateway(String, GraphicInfo)"})
  void testDrawExclusiveGateway() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawExclusiveGateway("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawExclusiveGateway(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExclusiveGateway(String, GraphicInfo)"})
  void testDrawExclusiveGateway_givenGraphicInfoElementIsActivitiListener() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawExclusiveGateway("42", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawInclusiveGateway(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawInclusiveGateway(String, GraphicInfo)"})
  void testDrawInclusiveGateway() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawInclusiveGateway("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawInclusiveGateway(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawInclusiveGateway(String, GraphicInfo)"})
  void testDrawInclusiveGateway_givenGraphicInfoElementIsActivitiListener() {
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

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    defaultProcessDiagramCanvas.drawTask("42", "id", graphicInfo, true);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawInclusiveGateway("42", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawEventBasedGateway(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawEventBasedGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventBasedGateway(String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawEventBasedGateway(String, GraphicInfo)"})
  void testDrawEventBasedGateway() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawEventBasedGateway("42", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultiInstanceMarker(boolean, int, int, int, int); when 'false'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultiInstanceMarker(boolean, int, int, int, int)"})
  void testDrawMultiInstanceMarker_whenFalse() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultiInstanceMarker(false, 2, 3, 1, 1);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}.
   * <ul>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultiInstanceMarker(boolean, int, int, int, int); when 'true'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultiInstanceMarker(boolean, int, int, int, int)"})
  void testDrawMultiInstanceMarker_whenTrue() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawMultiInstanceMarker(true, 2, 3, 1, 1);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCurrent(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightCurrent(GraphicInfo)"})
  void testDrawHighLightCurrent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawHighLightCurrent(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCurrent(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightCurrent(GraphicInfo)"})
  void testDrawHighLightCurrent2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawHighLightCurrent(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) OnTransaction is {@code On Transaction}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCurrent(GraphicInfo); given ActivitiListener (default constructor) OnTransaction is 'On Transaction'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightCurrent(GraphicInfo)"})
  void testDrawHighLightCurrent_givenActivitiListenerOnTransactionIsOnTransaction() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    ActivitiListener element = new ActivitiListener();
    element.setOnTransaction("On Transaction");
    element.addAttribute(new ExtensionAttribute("Name"));

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(element);
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawHighLightCurrent(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCompleted(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightCompleted(GraphicInfo)"})
  void testDrawHighLightCompleted() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawHighLightCompleted(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCompleted(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightCompleted(GraphicInfo)"})
  void testDrawHighLightCompleted2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawHighLightCompleted(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightErrored(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightErrored(GraphicInfo)"})
  void testDrawHighLightErrored() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawHighLightErrored(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightErrored(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightErrored(GraphicInfo)"})
  void testDrawHighLightErrored2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawHighLightErrored(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) Values is {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightErrored(GraphicInfo); given ActivitiListener (default constructor) Values is ActivitiListener (default constructor)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightErrored(GraphicInfo)"})
  void testDrawHighLightErrored_givenActivitiListenerValuesIsActivitiListener() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    ActivitiListener element = new ActivitiListener();
    element.setValues(new ActivitiListener());
    element.addExtensionElement(new ExtensionElement());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(element);
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawHighLightErrored(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightCompleted(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawEventHighLightCompleted(GraphicInfo)"})
  void testDrawEventHighLightCompleted() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawEventHighLightCompleted(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightCompleted(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawEventHighLightCompleted(GraphicInfo)"})
  void testDrawEventHighLightCompleted2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawEventHighLightCompleted(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightErrored(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawEventHighLightErrored(GraphicInfo)"})
  void testDrawEventHighLightErrored() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawEventHighLightErrored(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightErrored(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawEventHighLightErrored(GraphicInfo)"})
  void testDrawEventHighLightErrored2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawEventHighLightErrored(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightErrored(GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawEventHighLightErrored(GraphicInfo)"})
  void testDrawEventHighLightErrored3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    CallActivity element = new CallActivity();
    element.addExtensionElement(new ExtensionElement());

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(element);
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawEventHighLightErrored(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTextAnnotation(String, String, GraphicInfo)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTextAnnotation(String, String, GraphicInfo)"})
  void testDrawTextAnnotation() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-4, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTextAnnotation("42", "Text", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is {@link ActivitiListener} (default constructor).</li>
   *   <li>When {@code Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTextAnnotation(String, String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor); when 'Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTextAnnotation(String, String, GraphicInfo)"})
  void testDrawTextAnnotation_givenGraphicInfoElementIsActivitiListener_whenText() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(0.5d);
    graphicInfo.setWidth(0.5d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    Class<Object> type = Object.class;
    defaultProcessDiagramCanvas.drawExpandedSubProcess("42", "id", graphicInfo, type);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(10.0d);
    graphicInfo2.setWidth(10.0d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(10);
    graphicInfo2.setXmlRowNumber(10);
    graphicInfo2.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTextAnnotation("42", "Text", graphicInfo2);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTextAnnotation(String, String, GraphicInfo); when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTextAnnotation(String, String, GraphicInfo)"})
  void testDrawTextAnnotation_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTextAnnotation("42", "", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTextAnnotation(String, String, GraphicInfo); when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTextAnnotation(String, String, GraphicInfo)"})
  void testDrawTextAnnotation_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTextAnnotation("42", null, graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code Text}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTextAnnotation(String, String, GraphicInfo); when 'Text'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTextAnnotation(String, String, GraphicInfo)"})
  void testDrawTextAnnotation_whenText() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawTextAnnotation("42", "Text", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)} with {@code text}, {@code graphicInfo}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo) with 'text', 'graphicInfo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo)"})
  void testDrawLabelWithTextGraphicInfo() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawLabel("Text", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)} with {@code text}, {@code graphicInfo}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo) with 'text', 'graphicInfo'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo)"})
  void testDrawLabelWithTextGraphicInfo2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawLabel("Text", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)} with {@code text}, {@code graphicInfo}, {@code centered}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo, boolean)"})
  void testDrawLabelWithTextGraphicInfoCentered() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawLabel("Text", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)} with {@code text}, {@code graphicInfo}, {@code centered}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo, boolean)"})
  void testDrawLabelWithTextGraphicInfoCentered2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawLabel("Text", graphicInfo, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)} with {@code text}, {@code graphicInfo}, {@code centered}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo, boolean)"})
  void testDrawLabelWithTextGraphicInfoCentered3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawLabel("Text", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getLastElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblLastElementChild());
    assertSame(firstChild, lastChild.getLastChild());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)} with {@code text}, {@code graphicInfo}, {@code centered}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'; when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo, boolean)"})
  void testDrawLabelWithTextGraphicInfoCentered_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawLabel("", graphicInfo, true);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)} with {@code text}, {@code graphicInfo}, {@code centered}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'; when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo, boolean)"})
  void testDrawLabelWithTextGraphicInfoCentered_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawLabel(null, graphicInfo, false);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)} with {@code text}, {@code graphicInfo}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo) with 'text', 'graphicInfo'; when empty string")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo)"})
  void testDrawLabelWithTextGraphicInfo_whenEmptyString() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawLabel("", graphicInfo);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)} with {@code text}, {@code graphicInfo}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo) with 'text', 'graphicInfo'; when 'null'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo)"})
  void testDrawLabelWithTextGraphicInfo_whenNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawLabel(null, graphicInfo);

    // Assert that nothing has changed
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Height is {@code 0.5}.</li>
   *   <li>Then return first Y is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); given GraphicInfo (default constructor) Height is '0.5'; then return first Y is ten")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List DefaultProcessDiagramCanvas.connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)"})
  void testConnectionPerfectionizer_givenGraphicInfoHeightIs05_thenReturnFirstYIsTen() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo sourceGraphicInfo = new GraphicInfo();
    sourceGraphicInfo.setElement(new ActivitiListener());
    sourceGraphicInfo.setExpanded(true);
    sourceGraphicInfo.setHeight(10.0d);
    sourceGraphicInfo.setWidth(10.0d);
    sourceGraphicInfo.setX(2.0d);
    sourceGraphicInfo.setXmlColumnNumber(10);
    sourceGraphicInfo.setXmlRowNumber(10);
    sourceGraphicInfo.setY(3.0d);

    GraphicInfo targetGraphicInfo = new GraphicInfo();
    targetGraphicInfo.setElement(new ActivitiListener());
    targetGraphicInfo.setExpanded(true);
    targetGraphicInfo.setHeight(10.0d);
    targetGraphicInfo.setWidth(10.0d);
    targetGraphicInfo.setX(2.0d);
    targetGraphicInfo.setXmlColumnNumber(10);
    targetGraphicInfo.setXmlRowNumber(10);
    targetGraphicInfo.setY(3.0d);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(false);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(new ActivitiListener());
    graphicInfo2.setExpanded(true);
    graphicInfo2.setHeight(0.5d);
    graphicInfo2.setWidth(0.5d);
    graphicInfo2.setX(2.0d);
    graphicInfo2.setXmlColumnNumber(1);
    graphicInfo2.setXmlRowNumber(1);
    graphicInfo2.setY(10.0d);

    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();
    graphicInfoList.add(graphicInfo2);
    graphicInfoList.add(graphicInfo);

    // Act
    List<GraphicInfo> actualConnectionPerfectionizerResult = defaultProcessDiagramCanvas.connectionPerfectionizer(null,
        SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, graphicInfoList);

    // Assert
    assertEquals(2, actualConnectionPerfectionizerResult.size());
    GraphicInfo getResult = actualConnectionPerfectionizerResult.get(0);
    assertEquals(10.0d, getResult.getY());
    GraphicInfo getResult2 = actualConnectionPerfectionizerResult.get(1);
    assertEquals(10.0d, getResult2.getY());
    assertEquals(2.0d, getResult.getX());
    assertEquals(2.0d, getResult2.getX());
    assertFalse(getResult2.getExpanded());
    assertTrue(getResult.getExpanded());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) X is ten.</li>
   *   <li>Then return first X is seven.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); given GraphicInfo (default constructor) X is ten; then return first X is seven")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List DefaultProcessDiagramCanvas.connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)"})
  void testConnectionPerfectionizer_givenGraphicInfoXIsTen_thenReturnFirstXIsSeven() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo sourceGraphicInfo = new GraphicInfo();
    sourceGraphicInfo.setElement(new ActivitiListener());
    sourceGraphicInfo.setExpanded(true);
    sourceGraphicInfo.setHeight(10.0d);
    sourceGraphicInfo.setWidth(10.0d);
    sourceGraphicInfo.setX(2.0d);
    sourceGraphicInfo.setXmlColumnNumber(10);
    sourceGraphicInfo.setXmlRowNumber(10);
    sourceGraphicInfo.setY(3.0d);

    GraphicInfo targetGraphicInfo = new GraphicInfo();
    targetGraphicInfo.setElement(new ActivitiListener());
    targetGraphicInfo.setExpanded(true);
    targetGraphicInfo.setHeight(10.0d);
    targetGraphicInfo.setWidth(10.0d);
    targetGraphicInfo.setX(2.0d);
    targetGraphicInfo.setXmlColumnNumber(10);
    targetGraphicInfo.setXmlRowNumber(10);
    targetGraphicInfo.setY(3.0d);

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
    List<GraphicInfo> actualConnectionPerfectionizerResult = defaultProcessDiagramCanvas.connectionPerfectionizer(
        SHAPE_TYPE.Rectangle, SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, graphicInfoList);

    // Assert
    assertEquals(2, actualConnectionPerfectionizerResult.size());
    GraphicInfo getResult = actualConnectionPerfectionizerResult.get(0);
    assertEquals(7.0d, getResult.getX());
    GraphicInfo getResult2 = actualConnectionPerfectionizerResult.get(1);
    assertEquals(7.0d, getResult2.getX());
    assertEquals(8.0d, getResult.getY());
    assertEquals(8.0d, getResult2.getY());
    assertFalse(getResult.getExpanded());
    assertTrue(getResult2.getExpanded());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); when ArrayList(); then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List DefaultProcessDiagramCanvas.connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)"})
  void testConnectionPerfectionizer_whenArrayList_thenReturnEmpty() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo sourceGraphicInfo = new GraphicInfo();
    sourceGraphicInfo.setElement(new ActivitiListener());
    sourceGraphicInfo.setExpanded(true);
    sourceGraphicInfo.setHeight(10.0d);
    sourceGraphicInfo.setWidth(10.0d);
    sourceGraphicInfo.setX(2.0d);
    sourceGraphicInfo.setXmlColumnNumber(10);
    sourceGraphicInfo.setXmlRowNumber(10);
    sourceGraphicInfo.setY(3.0d);

    GraphicInfo targetGraphicInfo = new GraphicInfo();
    targetGraphicInfo.setElement(new ActivitiListener());
    targetGraphicInfo.setExpanded(true);
    targetGraphicInfo.setHeight(10.0d);
    targetGraphicInfo.setWidth(10.0d);
    targetGraphicInfo.setX(2.0d);
    targetGraphicInfo.setXmlColumnNumber(10);
    targetGraphicInfo.setXmlRowNumber(10);
    targetGraphicInfo.setY(3.0d);

    // Act and Assert
    assertTrue(defaultProcessDiagramCanvas
        .connectionPerfectionizer(SHAPE_TYPE.Rectangle, SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo,
            new ArrayList<>())
        .isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>When {@code Ellipse}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); when 'Ellipse'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List DefaultProcessDiagramCanvas.connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)"})
  void testConnectionPerfectionizer_whenEllipse_thenReturnEmpty() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo sourceGraphicInfo = new GraphicInfo();
    sourceGraphicInfo.setElement(new ActivitiListener());
    sourceGraphicInfo.setExpanded(true);
    sourceGraphicInfo.setHeight(10.0d);
    sourceGraphicInfo.setWidth(10.0d);
    sourceGraphicInfo.setX(2.0d);
    sourceGraphicInfo.setXmlColumnNumber(10);
    sourceGraphicInfo.setXmlRowNumber(10);
    sourceGraphicInfo.setY(3.0d);

    GraphicInfo targetGraphicInfo = new GraphicInfo();
    targetGraphicInfo.setElement(new ActivitiListener());
    targetGraphicInfo.setExpanded(true);
    targetGraphicInfo.setHeight(10.0d);
    targetGraphicInfo.setWidth(10.0d);
    targetGraphicInfo.setX(2.0d);
    targetGraphicInfo.setXmlColumnNumber(10);
    targetGraphicInfo.setXmlRowNumber(10);
    targetGraphicInfo.setY(3.0d);

    // Act and Assert
    assertTrue(defaultProcessDiagramCanvas
        .connectionPerfectionizer(SHAPE_TYPE.Ellipse, SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo,
            new ArrayList<>())
        .isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); when 'null'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List DefaultProcessDiagramCanvas.connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)"})
  void testConnectionPerfectionizer_whenNull_thenReturnEmpty() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo sourceGraphicInfo = new GraphicInfo();
    sourceGraphicInfo.setElement(new ActivitiListener());
    sourceGraphicInfo.setExpanded(true);
    sourceGraphicInfo.setHeight(10.0d);
    sourceGraphicInfo.setWidth(10.0d);
    sourceGraphicInfo.setX(2.0d);
    sourceGraphicInfo.setXmlColumnNumber(10);
    sourceGraphicInfo.setXmlRowNumber(10);
    sourceGraphicInfo.setY(3.0d);

    GraphicInfo targetGraphicInfo = new GraphicInfo();
    targetGraphicInfo.setElement(new ActivitiListener());
    targetGraphicInfo.setExpanded(true);
    targetGraphicInfo.setHeight(10.0d);
    targetGraphicInfo.setWidth(10.0d);
    targetGraphicInfo.setX(2.0d);
    targetGraphicInfo.setXmlColumnNumber(10);
    targetGraphicInfo.setXmlRowNumber(10);
    targetGraphicInfo.setY(3.0d);

    // Act and Assert
    assertTrue(defaultProcessDiagramCanvas
        .connectionPerfectionizer(null, SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, new ArrayList<>())
        .isEmpty());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>When {@code Rhombus}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); when 'Rhombus'; then return Empty")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({
      "List DefaultProcessDiagramCanvas.connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)"})
  void testConnectionPerfectionizer_whenRhombus_thenReturnEmpty() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo sourceGraphicInfo = new GraphicInfo();
    sourceGraphicInfo.setElement(new ActivitiListener());
    sourceGraphicInfo.setExpanded(true);
    sourceGraphicInfo.setHeight(10.0d);
    sourceGraphicInfo.setWidth(10.0d);
    sourceGraphicInfo.setX(2.0d);
    sourceGraphicInfo.setXmlColumnNumber(10);
    sourceGraphicInfo.setXmlRowNumber(10);
    sourceGraphicInfo.setY(3.0d);

    GraphicInfo targetGraphicInfo = new GraphicInfo();
    targetGraphicInfo.setElement(new ActivitiListener());
    targetGraphicInfo.setExpanded(true);
    targetGraphicInfo.setHeight(10.0d);
    targetGraphicInfo.setWidth(10.0d);
    targetGraphicInfo.setX(2.0d);
    targetGraphicInfo.setXmlColumnNumber(10);
    targetGraphicInfo.setXmlRowNumber(10);
    targetGraphicInfo.setY(3.0d);

    // Act and Assert
    assertTrue(defaultProcessDiagramCanvas
        .connectionPerfectionizer(SHAPE_TYPE.Rhombus, SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo,
            new ArrayList<>())
        .isEmpty());
  }
}
