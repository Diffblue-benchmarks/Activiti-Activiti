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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.awt.Color;
import java.awt.FontMetrics;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AssociationDirection;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.image.impl.DefaultProcessDiagramCanvas.SHAPE_TYPE;
import org.activiti.image.impl.icon.BusinessRuleTaskIconType;
import org.activiti.image.impl.icon.CompensateIconType;
import org.activiti.image.impl.icon.IconType;
import org.activiti.image.impl.icon.TaskIconType;
import org.apache.batik.dom.GenericElementNS;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class DefaultProcessDiagramCanvasDiffblueTest {
  /**
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <ul>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#minY} is two hundred fifty-five.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int, String, String, String)"})
  public void testNewDefaultProcessDiagramCanvas_thenReturnMinYIsTwoHundredFiftyFive() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 255,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    assertEquals(255, actualDefaultProcessDiagramCanvas.minY);
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
   *   <li>When {@code Activity Font Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int, String, String, String)"})
  public void testNewDefaultProcessDiagramCanvas_whenActivityFontName() {
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
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <ul>
   *   <li>When eight.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#canvasWidth} is eight.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int)"})
  public void testNewDefaultProcessDiagramCanvas_whenEight_thenReturnCanvasWidthIsEight() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(8,
        Integer.MIN_VALUE, 1, 1);

    // Assert
    assertEquals(8, actualDefaultProcessDiagramCanvas.canvasWidth);
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertEquals(Integer.MIN_VALUE, actualDefaultProcessDiagramCanvas.canvasHeight);
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int)"})
  public void testNewDefaultProcessDiagramCanvas_whenMinusOne_thenReturnCanvasWidthIsMinusOne() {
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
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#canvasWidth} is minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int, String, String, String)"})
  public void testNewDefaultProcessDiagramCanvas_whenMinusOne_thenReturnCanvasWidthIsMinusOne2() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-1, 1, 1, 1,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

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
   * Test {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#activityFontName} is {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int, String, String, String)"})
  public void testNewDefaultProcessDiagramCanvas_whenNull_thenReturnActivityFontNameIsArial() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1, null,
        null, null);

    // Assert
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.activityFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.annotationFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.labelFontName);
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int)"})
  public void testNewDefaultProcessDiagramCanvas_whenOne_thenReturnCanvasWidthIsOne() {
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
   *   <li>Then return {@link DefaultProcessDiagramCanvas#canvasWidth} is two.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.<init>(int, int, int, int)"})
  public void testNewDefaultProcessDiagramCanvas_whenTwo_thenReturnCanvasWidthIsTwo() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(2, 1, 1, 1);

    // Assert
    assertEquals(2, actualDefaultProcessDiagramCanvas.canvasWidth);
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"InputStream DefaultProcessDiagramCanvas.generateImage()"})
  public void testGenerateImage() throws IOException {
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
   * Test {@link DefaultProcessDiagramCanvas#generateImage()}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#generateImage()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"InputStream DefaultProcessDiagramCanvas.generateImage()"})
  public void testGenerateImage2() throws IOException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(2, 1, 1, 1);

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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.close()"})
  public void testClose() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneStartEvent(String, GraphicInfo)"})
  public void testDrawNoneStartEvent() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneStartEvent(String, GraphicInfo)"})
  public void testDrawNoneStartEvent2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneStartEvent(String, GraphicInfo)"})
  public void testDrawNoneStartEvent3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneStartEvent(String, GraphicInfo)"})
  public void testDrawNoneStartEvent_givenGraphicInfoElementIsActivitiListener() {
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
   * Test {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTimerStartEvent(String, GraphicInfo)"})
  public void testDrawTimerStartEvent() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTimerStartEvent(String, GraphicInfo)"})
  public void testDrawTimerStartEvent2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTimerStartEvent(String, GraphicInfo)"})
  public void testDrawTimerStartEvent_given05_whenGraphicInfoHeightIs05() {
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
   *   <li>Given {@link ActivitiListener} (default constructor) Instance is {@code Instance}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTimerStartEvent(String, GraphicInfo)"})
  public void testDrawTimerStartEvent_givenActivitiListenerInstanceIsInstance() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    ActivitiListener element = new ActivitiListener();
    element.setInstance("Instance");

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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTimerStartEvent(String, GraphicInfo)"})
  public void testDrawTimerStartEvent_givenGraphicInfoElementIsActivitiListener() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSignalStartEvent(String, GraphicInfo)"})
  public void testDrawSignalStartEvent() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSignalStartEvent(String, GraphicInfo)"})
  public void testDrawSignalStartEvent2() {
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
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSignalStartEvent(String, GraphicInfo)"})
  public void testDrawSignalStartEvent_given05_whenGraphicInfoHeightIs05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSignalStartEvent(String, GraphicInfo)"})
  public void testDrawSignalStartEvent_givenGraphicInfoElementIsActivitiListener() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMessageStartEvent(String, GraphicInfo)"})
  public void testDrawMessageStartEvent() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMessageStartEvent(String, GraphicInfo)"})
  public void testDrawMessageStartEvent2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMessageStartEvent(String, GraphicInfo)"})
  public void testDrawMessageStartEvent_given05_whenGraphicInfoHeightIs05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMessageStartEvent(String, GraphicInfo)"})
  public void testDrawMessageStartEvent_givenGraphicInfoElementIsActivitiListener() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawStartEvent(String, GraphicInfo, IconType)"})
  public void testDrawStartEvent() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawStartEvent(String, GraphicInfo, IconType)"})
  public void testDrawStartEvent2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawStartEvent(String, GraphicInfo, IconType)"})
  public void testDrawStartEvent_givenGraphicInfoElementIsActivitiListener() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawStartEvent(String, GraphicInfo, IconType)"})
  public void testDrawStartEvent_whenCompensateIconType() {
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
   * Test {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  public void testDrawNoneEndEvent() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  public void testDrawNoneEndEvent2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  public void testDrawNoneEndEvent3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  public void testDrawNoneEndEvent4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  public void testDrawNoneEndEvent_given05_whenGraphicInfoHeightIs05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  public void testDrawNoneEndEvent_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawNoneEndEvent(String, String, GraphicInfo)"})
  public void testDrawNoneEndEvent_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  public void testDrawErrorEndEvent() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  public void testDrawErrorEndEvent2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  public void testDrawErrorEndEvent3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  public void testDrawErrorEndEvent4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  public void testDrawErrorEndEvent5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  public void testDrawErrorEndEvent_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorEndEvent(String, String, GraphicInfo)"})
  public void testDrawErrorEndEvent_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorStartEvent(String, GraphicInfo)"})
  public void testDrawErrorStartEvent() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorStartEvent(String, GraphicInfo)"})
  public void testDrawErrorStartEvent2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorStartEvent(String, GraphicInfo)"})
  public void testDrawErrorStartEvent_given05_whenGraphicInfoHeightIs05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawErrorStartEvent(String, GraphicInfo)"})
  public void testDrawErrorStartEvent_givenGraphicInfoElementIsActivitiListener() {
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
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  public void testDrawCatchingEvent() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  public void testDrawCatchingEvent2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  public void testDrawCatchingEvent3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  public void testDrawCatchingEvent_given05_whenGraphicInfoHeightIs05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  public void testDrawCatchingEvent_givenGraphicInfoElementIsActivitiListener() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  public void testDrawCatchingEvent_whenCompensateIconType() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)"})
  public void testDrawCatchingEvent_whenTimer() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting_given05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 4, 1, 1, "compensate",
        "compensate", "compensate");

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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting6() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting7() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting_given05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting_given05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting_given05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingTimerEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting_given05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting_given05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingErrorEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting_given05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting2() {
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
   * Test {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting_given05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingSignalEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting_given05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting_given05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCatchingMessageEvent(String, String, GraphicInfo, boolean)"})
  public void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingCompensateEvent(String, GraphicInfo)"})
  public void testDrawThrowingCompensateEvent() {
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
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingCompensateEvent(String, GraphicInfo)"})
  public void testDrawThrowingCompensateEvent_given05_whenGraphicInfoHeightIs05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingCompensateEvent(String, GraphicInfo)"})
  public void testDrawThrowingCompensateEvent_givenGraphicInfoElementIsActivitiListener() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingCompensateEvent(String, GraphicInfo)"})
  public void testDrawThrowingCompensateEvent_whenGraphicInfoHeightIsTen() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingCompensateEvent(String, GraphicInfo)"})
  public void testDrawThrowingCompensateEvent_whenGraphicInfoHeightIsTwo() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingSignalEvent(String, GraphicInfo)"})
  public void testDrawThrowingSignalEvent() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingSignalEvent(String, GraphicInfo)"})
  public void testDrawThrowingSignalEvent_given05_whenGraphicInfoHeightIs05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingSignalEvent(String, GraphicInfo)"})
  public void testDrawThrowingSignalEvent_givenGraphicInfoElementIsActivitiListener() {
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
   *   <li>When {@link GraphicInfo} (default constructor) Height is ten.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingSignalEvent(String, GraphicInfo)"})
  public void testDrawThrowingSignalEvent_whenGraphicInfoHeightIsTen() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingSignalEvent(String, GraphicInfo)"})
  public void testDrawThrowingSignalEvent_whenGraphicInfoHeightIsTwo() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingNoneEvent(String, GraphicInfo)"})
  public void testDrawThrowingNoneEvent() {
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
   * Test {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingNoneEvent(String, GraphicInfo)"})
  public void testDrawThrowingNoneEvent2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingNoneEvent(String, GraphicInfo)"})
  public void testDrawThrowingNoneEvent3() {
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
   * <ul>
   *   <li>Given {@code 0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingNoneEvent(String, GraphicInfo)"})
  public void testDrawThrowingNoneEvent_given05_whenGraphicInfoHeightIs05() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawThrowingNoneEvent(String, GraphicInfo)"})
  public void testDrawThrowingNoneEvent_givenGraphicInfoElementIsActivitiListener() {
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
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean)"})
  public void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditional() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean)"})
  public void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditional2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean)"})
  public void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditional3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean, boolean)"})
  public void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditionalHighLighted() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, true, true);

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
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean, boolean)"})
  public void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditionalHighLighted2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, false, false);

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
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)} with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY}, {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int, int, int, int, boolean, boolean)"})
  public void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditionalHighLighted3() {
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
   * Test {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)} with {@code xPoints}, {@code yPoints}, {@code conditional}, {@code isDefault}, {@code highLighted}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int[], int[], boolean, boolean, boolean)"})
  public void testDrawSequenceflowWithXPointsYPointsConditionalIsDefaultHighLighted() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int[], int[], boolean, boolean, boolean)"})
  public void testDrawSequenceflowWithXPointsYPointsConditionalIsDefaultHighLighted2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int[], int[], boolean, boolean, boolean)"})
  public void testDrawSequenceflowWithXPointsYPointsConditionalIsDefaultHighLighted3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflow(int[], int[], boolean, boolean, boolean)"})
  public void testDrawSequenceflowWithXPointsYPointsConditionalIsDefaultHighLighted4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawAssociation(int[], int[], AssociationDirection, boolean)"})
  public void testDrawAssociation() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawAssociation(int[], int[], AssociationDirection, boolean)"})
  public void testDrawAssociation2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawAssociation(int[], int[], AssociationDirection, boolean)"})
  public void testDrawAssociation3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  public void testDrawConnection() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  public void testDrawConnection2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  public void testDrawConnection3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  public void testDrawConnection4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  public void testDrawConnection5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  public void testDrawConnection6() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)"})
  public void testDrawConnection_whenAssociation() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(int, int, int, int, boolean)"})
  public void testDrawSequenceflowWithoutArrowWithSrcXSrcYTargetXTargetYConditional() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(int, int, int, int, boolean)"})
  public void testDrawSequenceflowWithoutArrowWithSrcXSrcYTargetXTargetYConditional2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(int, int, int, int, boolean)"})
  public void testDrawSequenceflowWithoutArrowWithSrcXSrcYTargetXTargetYConditional3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)"})
  public void testDrawSequenceflowWithoutArrowWithSrcXSrcYTargetXTargetYConditionalHighLighted() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)"})
  public void testDrawSequenceflowWithoutArrowWithSrcXSrcYTargetXTargetYConditionalHighLighted2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)"})
  public void testDrawSequenceflowWithoutArrowWithSrcXSrcYTargetXTargetYConditionalHighLighted3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(TaskIconType, String, String, GraphicInfo)"})
  public void testDrawTaskWithIconIdNameGraphicInfo() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(TaskIconType, String, String, GraphicInfo)"})
  public void testDrawTaskWithIconIdNameGraphicInfo2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(TaskIconType, String, String, GraphicInfo)"})
  public void testDrawTaskWithIconIdNameGraphicInfo3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(TaskIconType, String, String, GraphicInfo)"})
  public void testDrawTaskWithIconIdNameGraphicInfo_givenJavaLangObject() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(TaskIconType, String, String, GraphicInfo)"})
  public void testDrawTaskWithIconIdNameGraphicInfo_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(TaskIconType, String, String, GraphicInfo)"})
  public void testDrawTaskWithIconIdNameGraphicInfo_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo)"})
  public void testDrawTaskWithIdNameGraphicInfo() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo)"})
  public void testDrawTaskWithIdNameGraphicInfo2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo)"})
  public void testDrawTaskWithIdNameGraphicInfo3() {
    // Arrange
    DefaultProcessDiagramCanvas initProcessDiagramCanvasResult = DefaultProcessDiagramGenerator
        .initProcessDiagramCanvas(new BpmnModel(), "id", "id", "id");

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
    initProcessDiagramCanvasResult.drawTask("42", "Name", graphicInfo);

    // Assert
    Element root = initProcessDiagramCanvasResult.g.getRoot();
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  public void testDrawTaskWithIdNameGraphicInfoThickBorder() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  public void testDrawTaskWithIdNameGraphicInfoThickBorder2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  public void testDrawTaskWithIdNameGraphicInfoThickBorder3() {
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
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  public void testDrawTaskWithIdNameGraphicInfoThickBorder4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(10, 1, 1, 1);

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
   * Test {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)} with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <ul>
   *   <li>Given {@code Object}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  public void testDrawTaskWithIdNameGraphicInfoThickBorder_givenJavaLangObject() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  public void testDrawTaskWithIdNameGraphicInfoThickBorder_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  public void testDrawTaskWithIdNameGraphicInfoThickBorder_whenFalse() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo, boolean)"})
  public void testDrawTaskWithIdNameGraphicInfoThickBorder_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo)"})
  public void testDrawTaskWithIdNameGraphicInfo_givenGraphicInfoElementIsActivitiListener() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo)"})
  public void testDrawTaskWithIdNameGraphicInfo_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTask(String, String, GraphicInfo)"})
  public void testDrawTaskWithIdNameGraphicInfo_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawPoolOrLane(String, String, GraphicInfo)"})
  public void testDrawPoolOrLane() throws DOMException {
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
    assertEquals("...", root.getTextContent());
    assertEquals("...", lastChild.getTextContent());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawPoolOrLane(String, String, GraphicInfo)"})
  public void testDrawPoolOrLane2() {
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
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawPoolOrLane(String, String, GraphicInfo)"})
  public void testDrawPoolOrLane3() throws DOMException {
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
    assertEquals("", root.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertTrue(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawPoolOrLane(String, String, GraphicInfo)"})
  public void testDrawPoolOrLane4() {
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
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawPoolOrLane(String, String, GraphicInfo)"})
  public void testDrawPoolOrLane5() throws DOMException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 4, 1, 1, "Name",
        "Name", "Name");

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
    assertEquals("", root.getTextContent());
    assertEquals("", lastChild.getTextContent());
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawPoolOrLane(String, String, GraphicInfo)"})
  public void testDrawPoolOrLane_whenNull() throws DOMException {
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
    assertEquals("", root.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertTrue(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineCentredText(String, int, int, int, int)"})
  public void testDrawMultilineCentredText() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineCentredText(String, int, int, int, int)"})
  public void testDrawMultilineCentredText2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineCentredText(String, int, int, int, int)"})
  public void testDrawMultilineCentredText3() {
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
   *   <li>When twelve.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineCentredText(String, int, int, int, int)"})
  public void testDrawMultilineCentredText_whenTwelve() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineAnnotationText(String, int, int, int, int)"})
  public void testDrawMultilineAnnotationText() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineAnnotationText(String, int, int, int, int)"})
  public void testDrawMultilineAnnotationText2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineAnnotationText(String, int, int, int, int)"})
  public void testDrawMultilineAnnotationText3() {
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
   *   <li>When twelve.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineAnnotationText(String, int, int, int, int)"})
  public void testDrawMultilineAnnotationText_whenTwelve() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineText(String, int, int, int, int, boolean)"})
  public void testDrawMultilineText() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineText(String, int, int, int, int, boolean)"})
  public void testDrawMultilineText2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineText(String, int, int, int, int, boolean)"})
  public void testDrawMultilineText3() {
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
   *   <li>When twelve.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineText(String, int, int, int, int, boolean)"})
  public void testDrawMultilineText_whenTwelve() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultilineText(String, int, int, int, int, boolean)"})
  public void testDrawMultilineText_whenZero() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DefaultProcessDiagramCanvas.fitTextToWidth(String, int)"})
  public void testFitTextToWidth_whenEmptyString_thenReturnEmptyString() {
    // Arrange, Act and Assert
    assertEquals("", (new DefaultProcessDiagramCanvas(1, 1, 1, 1)).fitTextToWidth("", 1));
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DefaultProcessDiagramCanvas.fitTextToWidth(String, int)"})
  public void testFitTextToWidth_whenOriginal_thenReturnDotDotDot() {
    // Arrange, Act and Assert
    assertEquals("...", (new DefaultProcessDiagramCanvas(1, 1, 1, 1)).fitTextToWidth("Original", 1));
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#fitTextToWidth(String, int)}.
   * <ul>
   *   <li>When sixty-three.</li>
   *   <li>Then return {@code Origin...}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#fitTextToWidth(String, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"String DefaultProcessDiagramCanvas.fitTextToWidth(String, int)"})
  public void testFitTextToWidth_whenSixtyThree_thenReturnOrigin() {
    // Arrange, Act and Assert
    assertEquals("Origin...", (new DefaultProcessDiagramCanvas(1, 1, 1, 1)).fitTextToWidth("Original", 63));
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawUserTask(String, String, GraphicInfo)"})
  public void testDrawUserTask() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawUserTask(String, String, GraphicInfo)"})
  public void testDrawUserTask2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawUserTask(String, String, GraphicInfo)"})
  public void testDrawUserTask3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawUserTask(String, String, GraphicInfo)"})
  public void testDrawUserTask4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawUserTask(String, String, GraphicInfo)"})
  public void testDrawUserTask_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawUserTask(String, String, GraphicInfo)"})
  public void testDrawUserTask_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawScriptTask(String, String, GraphicInfo)"})
  public void testDrawScriptTask() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawScriptTask(String, String, GraphicInfo)"})
  public void testDrawScriptTask2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawScriptTask(String, String, GraphicInfo)"})
  public void testDrawScriptTask3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawScriptTask(String, String, GraphicInfo)"})
  public void testDrawScriptTask4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawScriptTask(String, String, GraphicInfo)"})
  public void testDrawScriptTask_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawScriptTask(String, String, GraphicInfo)"})
  public void testDrawScriptTask_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawServiceTask(String, String, GraphicInfo)"})
  public void testDrawServiceTask() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawServiceTask(String, String, GraphicInfo)"})
  public void testDrawServiceTask2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawServiceTask(String, String, GraphicInfo)"})
  public void testDrawServiceTask3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawServiceTask(String, String, GraphicInfo)"})
  public void testDrawServiceTask4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawServiceTask(String, String, GraphicInfo)"})
  public void testDrawServiceTask_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawServiceTask(String, String, GraphicInfo)"})
  public void testDrawServiceTask_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawReceiveTask(String, String, GraphicInfo)"})
  public void testDrawReceiveTask() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawReceiveTask(String, String, GraphicInfo)"})
  public void testDrawReceiveTask2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawReceiveTask(String, String, GraphicInfo)"})
  public void testDrawReceiveTask3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawReceiveTask(String, String, GraphicInfo)"})
  public void testDrawReceiveTask4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawReceiveTask(String, String, GraphicInfo)"})
  public void testDrawReceiveTask_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawReceiveTask(String, String, GraphicInfo)"})
  public void testDrawReceiveTask_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSendTask(String, String, GraphicInfo)"})
  public void testDrawSendTask() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSendTask(String, String, GraphicInfo)"})
  public void testDrawSendTask2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSendTask(String, String, GraphicInfo)"})
  public void testDrawSendTask3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSendTask(String, String, GraphicInfo)"})
  public void testDrawSendTask4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSendTask(String, String, GraphicInfo)"})
  public void testDrawSendTask_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawSendTask(String, String, GraphicInfo)"})
  public void testDrawSendTask_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  public void testDrawManualTask() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  public void testDrawManualTask2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  public void testDrawManualTask3() {
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
   *   <li>Given {@code null}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Expanded is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  public void testDrawManualTask_givenNull_whenGraphicInfoExpandedIsNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(null);
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
   * Test {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  public void testDrawManualTask_whenEmptyString() {
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
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  public void testDrawManualTask_whenName() {
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
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawManualTask(String, String, GraphicInfo)"})
  public void testDrawManualTask_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawBusinessRuleTask(String, String, GraphicInfo)"})
  public void testDrawBusinessRuleTask() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawBusinessRuleTask(String, String, GraphicInfo)"})
  public void testDrawBusinessRuleTask2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawBusinessRuleTask(String, String, GraphicInfo)"})
  public void testDrawBusinessRuleTask3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawBusinessRuleTask(String, String, GraphicInfo)"})
  public void testDrawBusinessRuleTask4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawBusinessRuleTask(String, String, GraphicInfo)"})
  public void testDrawBusinessRuleTask_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawBusinessRuleTask(String, String, GraphicInfo)"})
  public void testDrawBusinessRuleTask_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  public void testDrawExpandedSubProcess() throws DOMException {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  public void testDrawExpandedSubProcess2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  public void testDrawExpandedSubProcess3() throws DOMException {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals("", root.getTextContent());
    assertEquals("", lastChild.getTextContent());
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  public void testDrawExpandedSubProcess_given05_whenGraphicInfoHeightIs05() throws DOMException {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  public void testDrawExpandedSubProcess_givenGraphicInfoElementIsActivitiListener() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExpandedSubProcess(String, String, GraphicInfo, Class)"})
  public void testDrawExpandedSubProcess_whenEmptyString() throws DOMException {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals("", root.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)"})
  public void testDrawCollapsedSubProcess() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)"})
  public void testDrawCollapsedSubProcess_givenGraphicInfoElementIsActivitiListener_whenName() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)"})
  public void testDrawCollapsedSubProcess_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)"})
  public void testDrawCollapsedSubProcess_whenName() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)"})
  public void testDrawCollapsedSubProcess_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedCallActivity(String, String, GraphicInfo)"})
  public void testDrawCollapsedCallActivity() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedCallActivity(String, String, GraphicInfo)"})
  public void testDrawCollapsedCallActivity2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedCallActivity(String, String, GraphicInfo)"})
  public void testDrawCollapsedCallActivity3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedCallActivity(String, String, GraphicInfo)"})
  public void testDrawCollapsedCallActivity4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedCallActivity(String, String, GraphicInfo)"})
  public void testDrawCollapsedCallActivity_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedCallActivity(String, String, GraphicInfo)"})
  public void testDrawCollapsedCallActivity_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  public void testDrawCollapsedTask() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  public void testDrawCollapsedTask2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  public void testDrawCollapsedTask3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  public void testDrawCollapsedTask4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  public void testDrawCollapsedTask_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  public void testDrawCollapsedTask_whenFalse() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedTask(String, String, GraphicInfo, boolean)"})
  public void testDrawCollapsedTask_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawCollapsedMarker(int, int, int, int)"})
  public void testDrawCollapsedMarker() {
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
   * Test {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)"})
  public void testDrawActivityMarkers() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)"})
  public void testDrawActivityMarkers2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)"})
  public void testDrawActivityMarkers3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)"})
  public void testDrawActivityMarkers4() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)"})
  public void testDrawActivityMarkers5() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "void DefaultProcessDiagramCanvas.drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)"})
  public void testDrawActivityMarkers6() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawGateway(GraphicInfo)"})
  public void testDrawGateway() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawGateway(GraphicInfo)"})
  public void testDrawGateway2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawGatewayHighLightCompleted(GraphicInfo)"})
  public void testDrawGatewayHighLightCompleted() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawGatewayHighLightCompleted(GraphicInfo)"})
  public void testDrawGatewayHighLightCompleted2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawGatewayHighLightErrored(GraphicInfo)"})
  public void testDrawGatewayHighLightErrored() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawGatewayHighLightErrored(GraphicInfo)"})
  public void testDrawGatewayHighLightErrored2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawParallelGateway(String, GraphicInfo)"})
  public void testDrawParallelGateway() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawParallelGateway(String, GraphicInfo)"})
  public void testDrawParallelGateway_givenGraphicInfoElementIsActivitiListener() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExclusiveGateway(String, GraphicInfo)"})
  public void testDrawExclusiveGateway() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawExclusiveGateway(String, GraphicInfo)"})
  public void testDrawExclusiveGateway_givenGraphicInfoElementIsActivitiListener() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawInclusiveGateway(String, GraphicInfo)"})
  public void testDrawInclusiveGateway() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawInclusiveGateway(String, GraphicInfo)"})
  public void testDrawInclusiveGateway_givenGraphicInfoElementIsActivitiListener() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawEventBasedGateway(String, GraphicInfo)"})
  public void testDrawEventBasedGateway() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultiInstanceMarker(boolean, int, int, int, int)"})
  public void testDrawMultiInstanceMarker_whenFalse() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawMultiInstanceMarker(boolean, int, int, int, int)"})
  public void testDrawMultiInstanceMarker_whenTrue() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightCurrent(GraphicInfo)"})
  public void testDrawHighLightCurrent() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightCurrent(GraphicInfo)"})
  public void testDrawHighLightCurrent2() {
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
   * Test {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightCompleted(GraphicInfo)"})
  public void testDrawHighLightCompleted() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightCompleted(GraphicInfo)"})
  public void testDrawHighLightCompleted2() {
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
   * Test {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) ExtensionElements is {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightCompleted(GraphicInfo)"})
  public void testDrawHighLightCompleted_givenActivitiListenerExtensionElementsIsHashMap() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    ActivitiListener element = new ActivitiListener();
    element.setExtensionElements(new HashMap<>());
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightErrored(GraphicInfo)"})
  public void testDrawHighLightErrored() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawHighLightErrored(GraphicInfo)"})
  public void testDrawHighLightErrored2() {
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
   * Test {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawEventHighLightCompleted(GraphicInfo)"})
  public void testDrawEventHighLightCompleted() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawEventHighLightCompleted(GraphicInfo)"})
  public void testDrawEventHighLightCompleted2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawEventHighLightErrored(GraphicInfo)"})
  public void testDrawEventHighLightErrored() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawEventHighLightErrored(GraphicInfo)"})
  public void testDrawEventHighLightErrored2() {
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
   * Test {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTextAnnotation(String, String, GraphicInfo)"})
  public void testDrawTextAnnotation() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTextAnnotation(String, String, GraphicInfo)"})
  public void testDrawTextAnnotation_givenGraphicInfoElementIsActivitiListener_whenText() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTextAnnotation(String, String, GraphicInfo)"})
  public void testDrawTextAnnotation_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTextAnnotation(String, String, GraphicInfo)"})
  public void testDrawTextAnnotation_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawTextAnnotation(String, String, GraphicInfo)"})
  public void testDrawTextAnnotation_whenText() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo)"})
  public void testDrawLabelWithTextGraphicInfo() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo)"})
  public void testDrawLabelWithTextGraphicInfo2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo, boolean)"})
  public void testDrawLabelWithTextGraphicInfoCentered() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo, boolean)"})
  public void testDrawLabelWithTextGraphicInfoCentered2() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo, boolean)"})
  public void testDrawLabelWithTextGraphicInfoCentered3() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo, boolean)"})
  public void testDrawLabelWithTextGraphicInfoCentered_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo, boolean)"})
  public void testDrawLabelWithTextGraphicInfoCentered_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo)"})
  public void testDrawLabelWithTextGraphicInfo_whenEmptyString() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultProcessDiagramCanvas.drawLabel(String, GraphicInfo)"})
  public void testDrawLabelWithTextGraphicInfo_whenNull() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List DefaultProcessDiagramCanvas.connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)"})
  public void testConnectionPerfectionizer_givenGraphicInfoHeightIs05_thenReturnFirstYIsTen() {
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
    assertEquals(10.0d, getResult.getY(), 0.0);
    GraphicInfo getResult2 = actualConnectionPerfectionizerResult.get(1);
    assertEquals(10.0d, getResult2.getY(), 0.0);
    assertEquals(2.0d, getResult.getX(), 0.0);
    assertEquals(2.0d, getResult2.getX(), 0.0);
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List DefaultProcessDiagramCanvas.connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)"})
  public void testConnectionPerfectionizer_givenGraphicInfoXIsTen_thenReturnFirstXIsSeven() {
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
    assertEquals(7.0d, getResult.getX(), 0.0);
    GraphicInfo getResult2 = actualConnectionPerfectionizerResult.get(1);
    assertEquals(7.0d, getResult2.getX(), 0.0);
    assertEquals(8.0d, getResult.getY(), 0.0);
    assertEquals(8.0d, getResult2.getY(), 0.0);
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List DefaultProcessDiagramCanvas.connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)"})
  public void testConnectionPerfectionizer_whenArrayList_thenReturnEmpty() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List DefaultProcessDiagramCanvas.connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)"})
  public void testConnectionPerfectionizer_whenEllipse_thenReturnEmpty() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List DefaultProcessDiagramCanvas.connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)"})
  public void testConnectionPerfectionizer_whenNull_thenReturnEmpty() {
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "List DefaultProcessDiagramCanvas.connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)"})
  public void testConnectionPerfectionizer_whenRhombus_thenReturnEmpty() {
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
