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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
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
import java.util.List;
import java.util.Map;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AssociationDirection;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.image.impl.icon.BusinessRuleTaskIconType;
import org.activiti.image.impl.icon.CompensateIconType;
import org.activiti.image.impl.icon.IconType;
import org.activiti.image.impl.icon.TaskIconType;
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

public class DefaultProcessDiagramCanvasDiffblueTest {
  /**
   * Method under test: {@link DefaultProcessDiagramCanvas#generateImage()}
   */
  @Test
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
   * Method under test: {@link DefaultProcessDiagramCanvas#generateImage()}
   */
  @Test
  public void testGenerateImage2() throws IOException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, -1);

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
   * Method under test: {@link DefaultProcessDiagramCanvas#close()}
   */
  @Test
  public void testClose() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.close();

    // Assert
    assertTrue(defaultProcessDiagramCanvas.closed);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawNoneStartEvent4() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawTimerStartEvent3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawTimerStartEvent4() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawSignalStartEvent3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawSignalStartEvent4() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawMessageStartEvent3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawMessageStartEvent4() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  public void testDrawStartEvent2() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  public void testDrawStartEvent3() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  public void testDrawStartEvent4() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawNoneEndEvent3() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawNoneEndEvent4() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawNoneEndEvent5() {
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
    defaultProcessDiagramCanvas.drawNoneEndEvent("42", "Name", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawNoneEndEvent6() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawNoneEndEvent7() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawNoneEndEvent8() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawErrorEndEvent3() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawErrorEndEvent4() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawErrorEndEvent5() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawErrorEndEvent6() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawErrorEndEvent7() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawErrorStartEvent3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawErrorStartEvent4() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  public void testDrawCatchingEvent2() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  public void testDrawCatchingEvent3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  public void testDrawCatchingEvent4() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  public void testDrawCatchingEvent5() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  public void testDrawCatchingEvent6() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  public void testDrawCatchingEvent7() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent2() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent3() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent4() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent5() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent6() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent7() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent8() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent9() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent10() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent11() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent12() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent13() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingCompensateEvent14() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent2() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent3() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent4() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent5() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent6() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent7() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent8() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent9() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent10() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent11() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent12() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent13() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingTimerEvent14() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent2() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent3() {
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
    defaultProcessDiagramCanvas.drawCatchingErrorEvent("42", "path", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent4() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent5() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent6() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent7() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent8() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent9() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent10() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent11() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent12() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent13() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent14() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingErrorEvent15() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent2() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent3() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent4() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent5() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent6() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent7() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent8() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent9() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent10() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent11() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent12() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent13() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingSignalEvent14() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent2() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent3() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent4() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent5() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent6() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent7() {
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent8() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent9() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent10() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent11() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent12() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent13() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCatchingMessageEvent14() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingCompensateEvent() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingCompensateEvent2() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingCompensateEvent3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingCompensateEvent4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingCompensateEvent5() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingSignalEvent() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingSignalEvent2() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingSignalEvent3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingSignalEvent4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingSignalEvent5() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingNoneEvent3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingNoneEvent4() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingNoneEvent5() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  public void testDrawThrowingNoneEvent6() {
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
    defaultProcessDiagramCanvas.drawThrowingNoneEvent("Id", graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   */
  @Test
  public void testDrawSequenceflow() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, true);

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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   */
  @Test
  public void testDrawSequenceflow2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, false);

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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   */
  @Test
  public void testDrawSequenceflow3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   */
  @Test
  public void testDrawSequenceflow4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 4, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, false);

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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  public void testDrawSequenceflow5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, true, true);

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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  public void testDrawSequenceflow6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, false, false);

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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  public void testDrawSequenceflow7() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, true, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  public void testDrawSequenceflow8() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(3, -1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflow(1, 1, 1, 1, true, true);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  public void testDrawSequenceflow9() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  public void testDrawSequenceflow10() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  public void testDrawSequenceflow11() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  public void testDrawSequenceflow12() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  public void testDrawConnection4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  public void testDrawConnection5() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  public void testDrawConnection6() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  public void testDrawConnection7() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)}
   */
  @Test
  public void testDrawSequenceflowWithoutArrow() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(1, 1, 1, 1, true);

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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)}
   */
  @Test
  public void testDrawSequenceflowWithoutArrow2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(1, 1, 1, 1, false);

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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)}
   */
  @Test
  public void testDrawSequenceflowWithoutArrow3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   */
  @Test
  public void testDrawSequenceflowWithoutArrow4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(1, 1, 1, 1, true, true);

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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   */
  @Test
  public void testDrawSequenceflowWithoutArrow5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(1, 1, 1, 1, false, false);

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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   */
  @Test
  public void testDrawSequenceflowWithoutArrow6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
    assertTrue(lastChild.hasChildNodes());
    assertSame(firstChild, ((GenericElementNS) lastChild).getFirstElementChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstChild());
    assertSame(firstChild, ((GenericElementNS) lastChild).getXblFirstElementChild());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   */
  @Test
  public void testDrawSequenceflowWithoutArrow7() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 12, 1);

    // Act
    defaultProcessDiagramCanvas.drawSequenceflowWithoutArrow(1, 1, 1, 1, false, false);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTask() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTask2() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTask3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTask4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTask5() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawTask6() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawTask7() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawTask8() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawTask9() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawTask10() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawTask11() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(-0.5d);
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawTask12() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawTask13() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTask14() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTask15() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTask16() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTask17() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTask18() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTask19() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals("...", root.getTextContent());
    assertEquals("...", lastChild.getTextContent());
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawPoolOrLane2() throws DOMException {
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
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals("...", root.getTextContent());
    assertEquals("...", lastChild.getTextContent());
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals("", root.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
    assertTrue(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawPoolOrLane4() throws DOMException {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawPoolOrLane5() throws DOMException {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawPoolOrLane6() throws DOMException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-6, 1, 1, 1);

    ActivitiListener element = new ActivitiListener();
    element.setValues(new ActivitiListener());

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
    defaultProcessDiagramCanvas.drawPoolOrLane("42", "Name", graphicInfo);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = defaultProcessDiagramCanvas.g;
    Element root = processDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    Node firstChild = lastChild.getFirstChild();
    assertTrue(firstChild instanceof GenericElementNS);
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals("...", root.getTextContent());
    assertEquals("...", lastChild.getTextContent());
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(processDiagramSVGGraphics2D.getGraphicContext().isTransformStackValid());
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  public void testDrawMultilineCentredText4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  public void testDrawMultilineAnnotationText4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  public void testDrawMultilineText2() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  public void testDrawMultilineText3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  public void testDrawMultilineText4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  public void testDrawMultilineText5() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#fitTextToWidth(String, int)}
   */
  @Test
  public void testFitTextToWidth() {
    // Arrange, Act and Assert
    assertEquals("...", (new DefaultProcessDiagramCanvas(1, 1, 1, 1)).fitTextToWidth("Original", 1));
    assertEquals("", (new DefaultProcessDiagramCanvas(1, 1, 1, 1)).fitTextToWidth("", 1));
    assertEquals("Origin...", (new DefaultProcessDiagramCanvas(1, 1, 1, 1)).fitTextToWidth("Original", 63));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawUserTask3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawUserTask4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawUserTask5() {
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
    defaultProcessDiagramCanvas.drawUserTask("top left", "Name", graphicInfo);

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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawUserTask6() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawUserTask7() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawScriptTask3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawScriptTask4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawScriptTask5() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawScriptTask6() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawServiceTask3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawServiceTask4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawServiceTask5() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawServiceTask6() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawReceiveTask3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawReceiveTask4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawReceiveTask5() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawReceiveTask6() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawSendTask3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawSendTask4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawSendTask5() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawSendTask6() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawManualTask() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawManualTask2() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawManualTask3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawManualTask4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawManualTask5() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawManualTask6() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawBusinessRuleTask3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawBusinessRuleTask4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawBusinessRuleTask5() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawBusinessRuleTask6() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  public void testDrawExpandedSubProcess2() throws DOMException {
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
    assertEquals("...", root.getTextContent());
    assertEquals("...", lastChild.getTextContent());
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  public void testDrawExpandedSubProcess4() throws DOMException {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  public void testDrawExpandedSubProcess5() throws DOMException {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  public void testDrawExpandedSubProcess6() throws DOMException {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  public void testDrawCollapsedSubProcess() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  public void testDrawCollapsedSubProcess2() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  public void testDrawCollapsedSubProcess3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  public void testDrawCollapsedSubProcess4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  public void testDrawCollapsedSubProcess5() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawCollapsedCallActivity3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawCollapsedCallActivity4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawCollapsedCallActivity5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    ActivitiListener element = new ActivitiListener();
    element.addAttribute(new ExtensionAttribute("42"));

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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawCollapsedCallActivity6() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawCollapsedCallActivity7() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCollapsedTask3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCollapsedTask4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCollapsedTask5() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCollapsedTask6() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawCollapsedTask7() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedMarker(int, int, int, int)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGateway(GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGateway(GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLight(GraphicInfo, Color)}
   */
  @Test
  public void testDrawGatewayHighLight() throws NumberFormatException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    GraphicInfo graphicInfo = mock(GraphicInfo.class);
    when(graphicInfo.getHeight()).thenReturn(10.0d);
    when(graphicInfo.getWidth()).thenReturn(10.0d);
    when(graphicInfo.getX()).thenReturn(2.0d);
    when(graphicInfo.getY()).thenReturn(3.0d);
    doNothing().when(graphicInfo).setElement(Mockito.<BaseElement>any());
    doNothing().when(graphicInfo).setExpanded(Mockito.<Boolean>any());
    doNothing().when(graphicInfo).setHeight(anyDouble());
    doNothing().when(graphicInfo).setWidth(anyDouble());
    doNothing().when(graphicInfo).setX(anyDouble());
    doNothing().when(graphicInfo).setXmlColumnNumber(anyInt());
    doNothing().when(graphicInfo).setXmlRowNumber(anyInt());
    doNothing().when(graphicInfo).setY(anyDouble());
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawGatewayHighLight(graphicInfo, Color.decode("42"));

    // Assert
    verify(graphicInfo).getHeight();
    verify(graphicInfo).getWidth();
    verify(graphicInfo).getX();
    verify(graphicInfo).getY();
    verify(graphicInfo).setElement(isA(BaseElement.class));
    verify(graphicInfo).setExpanded(eq(true));
    verify(graphicInfo).setHeight(eq(10.0d));
    verify(graphicInfo).setWidth(eq(10.0d));
    verify(graphicInfo).setX(eq(2.0d));
    verify(graphicInfo).setXmlColumnNumber(eq(10));
    verify(graphicInfo).setXmlRowNumber(eq(10));
    verify(graphicInfo).setY(eq(3.0d));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLight(GraphicInfo, Color)}
   */
  @Test
  public void testDrawGatewayHighLight2() throws NumberFormatException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);
    GraphicInfo graphicInfo = mock(GraphicInfo.class);
    when(graphicInfo.getHeight()).thenReturn(10.0d);
    when(graphicInfo.getWidth()).thenReturn(10.0d);
    when(graphicInfo.getX()).thenReturn(2.0d);
    when(graphicInfo.getY()).thenReturn(3.0d);
    doNothing().when(graphicInfo).setElement(Mockito.<BaseElement>any());
    doNothing().when(graphicInfo).setExpanded(Mockito.<Boolean>any());
    doNothing().when(graphicInfo).setHeight(anyDouble());
    doNothing().when(graphicInfo).setWidth(anyDouble());
    doNothing().when(graphicInfo).setX(anyDouble());
    doNothing().when(graphicInfo).setXmlColumnNumber(anyInt());
    doNothing().when(graphicInfo).setXmlRowNumber(anyInt());
    doNothing().when(graphicInfo).setY(anyDouble());
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawGatewayHighLight(graphicInfo, Color.decode("42"));

    // Assert
    verify(graphicInfo).getHeight();
    verify(graphicInfo).getWidth();
    verify(graphicInfo).getX();
    verify(graphicInfo).getY();
    verify(graphicInfo).setElement(isA(BaseElement.class));
    verify(graphicInfo).setExpanded(eq(true));
    verify(graphicInfo).setHeight(eq(10.0d));
    verify(graphicInfo).setWidth(eq(10.0d));
    verify(graphicInfo).setX(eq(2.0d));
    verify(graphicInfo).setXmlColumnNumber(eq(10));
    verify(graphicInfo).setXmlRowNumber(eq(10));
    verify(graphicInfo).setY(eq(3.0d));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}
   */
  @Test
  public void testDrawGatewayHighLightCompleted3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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
    defaultProcessDiagramCanvas.drawGatewayHighLightCompleted(graphicInfo);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawParallelGateway(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawParallelGateway(String, GraphicInfo)}
   */
  @Test
  public void testDrawParallelGateway2() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawParallelGateway(String, GraphicInfo)}
   */
  @Test
  public void testDrawParallelGateway3() {
    // Arrange
    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(0.5d);
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}
   */
  @Test
  public void testDrawExclusiveGateway2() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}
   */
  @Test
  public void testDrawInclusiveGateway2() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventBasedGateway(String, GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}
   */
  @Test
  public void testDrawMultiInstanceMarker() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}
   */
  @Test
  public void testDrawMultiInstanceMarker2() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}
   */
  @Test
  public void testDrawHighLightErrored3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    ActivitiListener element = new ActivitiListener();
    element.setEvent("Event");
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
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLight(GraphicInfo, Color)}
   */
  @Test
  public void testDrawHighLight() throws NumberFormatException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    GraphicInfo graphicInfo = mock(GraphicInfo.class);
    when(graphicInfo.getHeight()).thenReturn(10.0d);
    when(graphicInfo.getWidth()).thenReturn(10.0d);
    when(graphicInfo.getX()).thenReturn(2.0d);
    when(graphicInfo.getY()).thenReturn(3.0d);
    doNothing().when(graphicInfo).setElement(Mockito.<BaseElement>any());
    doNothing().when(graphicInfo).setExpanded(Mockito.<Boolean>any());
    doNothing().when(graphicInfo).setHeight(anyDouble());
    doNothing().when(graphicInfo).setWidth(anyDouble());
    doNothing().when(graphicInfo).setX(anyDouble());
    doNothing().when(graphicInfo).setXmlColumnNumber(anyInt());
    doNothing().when(graphicInfo).setXmlRowNumber(anyInt());
    doNothing().when(graphicInfo).setY(anyDouble());
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawHighLight(graphicInfo, Color.decode("42"));

    // Assert
    verify(graphicInfo).getHeight();
    verify(graphicInfo).getWidth();
    verify(graphicInfo).getX();
    verify(graphicInfo).getY();
    verify(graphicInfo).setElement(isA(BaseElement.class));
    verify(graphicInfo).setExpanded(eq(true));
    verify(graphicInfo).setHeight(eq(10.0d));
    verify(graphicInfo).setWidth(eq(10.0d));
    verify(graphicInfo).setX(eq(2.0d));
    verify(graphicInfo).setXmlColumnNumber(eq(10));
    verify(graphicInfo).setXmlRowNumber(eq(10));
    verify(graphicInfo).setY(eq(3.0d));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLight(GraphicInfo, Color)}
   */
  @Test
  public void testDrawHighLight2() throws NumberFormatException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);
    GraphicInfo graphicInfo = mock(GraphicInfo.class);
    when(graphicInfo.getHeight()).thenReturn(10.0d);
    when(graphicInfo.getWidth()).thenReturn(10.0d);
    when(graphicInfo.getX()).thenReturn(2.0d);
    when(graphicInfo.getY()).thenReturn(3.0d);
    doNothing().when(graphicInfo).setElement(Mockito.<BaseElement>any());
    doNothing().when(graphicInfo).setExpanded(Mockito.<Boolean>any());
    doNothing().when(graphicInfo).setHeight(anyDouble());
    doNothing().when(graphicInfo).setWidth(anyDouble());
    doNothing().when(graphicInfo).setX(anyDouble());
    doNothing().when(graphicInfo).setXmlColumnNumber(anyInt());
    doNothing().when(graphicInfo).setXmlRowNumber(anyInt());
    doNothing().when(graphicInfo).setY(anyDouble());
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawHighLight(graphicInfo, Color.decode("42"));

    // Assert
    verify(graphicInfo).getHeight();
    verify(graphicInfo).getWidth();
    verify(graphicInfo).getX();
    verify(graphicInfo).getY();
    verify(graphicInfo).setElement(isA(BaseElement.class));
    verify(graphicInfo).setExpanded(eq(true));
    verify(graphicInfo).setHeight(eq(10.0d));
    verify(graphicInfo).setWidth(eq(10.0d));
    verify(graphicInfo).setX(eq(2.0d));
    verify(graphicInfo).setXmlColumnNumber(eq(10));
    verify(graphicInfo).setXmlRowNumber(eq(10));
    verify(graphicInfo).setY(eq(3.0d));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLight(GraphicInfo, Color)}
   */
  @Test
  public void testDrawEventHighLight() throws NumberFormatException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);
    GraphicInfo graphicInfo = mock(GraphicInfo.class);
    when(graphicInfo.getHeight()).thenReturn(10.0d);
    when(graphicInfo.getWidth()).thenReturn(10.0d);
    when(graphicInfo.getX()).thenReturn(2.0d);
    when(graphicInfo.getY()).thenReturn(3.0d);
    doNothing().when(graphicInfo).setElement(Mockito.<BaseElement>any());
    doNothing().when(graphicInfo).setExpanded(Mockito.<Boolean>any());
    doNothing().when(graphicInfo).setHeight(anyDouble());
    doNothing().when(graphicInfo).setWidth(anyDouble());
    doNothing().when(graphicInfo).setX(anyDouble());
    doNothing().when(graphicInfo).setXmlColumnNumber(anyInt());
    doNothing().when(graphicInfo).setXmlRowNumber(anyInt());
    doNothing().when(graphicInfo).setY(anyDouble());
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawEventHighLight(graphicInfo, Color.decode("42"));

    // Assert
    verify(graphicInfo).getHeight();
    verify(graphicInfo).getWidth();
    verify(graphicInfo).getX();
    verify(graphicInfo).getY();
    verify(graphicInfo).setElement(isA(BaseElement.class));
    verify(graphicInfo).setExpanded(eq(true));
    verify(graphicInfo).setHeight(eq(10.0d));
    verify(graphicInfo).setWidth(eq(10.0d));
    verify(graphicInfo).setX(eq(2.0d));
    verify(graphicInfo).setXmlColumnNumber(eq(10));
    verify(graphicInfo).setXmlRowNumber(eq(10));
    verify(graphicInfo).setY(eq(3.0d));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLight(GraphicInfo, Color)}
   */
  @Test
  public void testDrawEventHighLight2() throws NumberFormatException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);
    GraphicInfo graphicInfo = mock(GraphicInfo.class);
    when(graphicInfo.getHeight()).thenReturn(10.0d);
    when(graphicInfo.getWidth()).thenReturn(10.0d);
    when(graphicInfo.getX()).thenReturn(2.0d);
    when(graphicInfo.getY()).thenReturn(3.0d);
    doNothing().when(graphicInfo).setElement(Mockito.<BaseElement>any());
    doNothing().when(graphicInfo).setExpanded(Mockito.<Boolean>any());
    doNothing().when(graphicInfo).setHeight(anyDouble());
    doNothing().when(graphicInfo).setWidth(anyDouble());
    doNothing().when(graphicInfo).setX(anyDouble());
    doNothing().when(graphicInfo).setXmlColumnNumber(anyInt());
    doNothing().when(graphicInfo).setXmlRowNumber(anyInt());
    doNothing().when(graphicInfo).setY(anyDouble());
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawEventHighLight(graphicInfo, Color.decode("42"));

    // Assert
    verify(graphicInfo).getHeight();
    verify(graphicInfo).getWidth();
    verify(graphicInfo).getX();
    verify(graphicInfo).getY();
    verify(graphicInfo).setElement(isA(BaseElement.class));
    verify(graphicInfo).setExpanded(eq(true));
    verify(graphicInfo).setHeight(eq(10.0d));
    verify(graphicInfo).setWidth(eq(10.0d));
    verify(graphicInfo).setX(eq(2.0d));
    verify(graphicInfo).setXmlColumnNumber(eq(10));
    verify(graphicInfo).setXmlRowNumber(eq(10));
    verify(graphicInfo).setY(eq(3.0d));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}
   */
  @Test
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
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}
   */
  @Test
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTextAnnotation() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTextAnnotation2() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTextAnnotation3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTextAnnotation4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  public void testDrawTextAnnotation5() {
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  public void testDrawLabel() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  public void testDrawLabel2() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  public void testDrawLabel3() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  public void testDrawLabel4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawLabel5() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawLabel6() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawLabel7() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawLabel8() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  public void testDrawLabel9() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  public void testConnectionPerfectionizer() {
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
    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();

    // Act
    List<GraphicInfo> actualConnectionPerfectionizerResult = defaultProcessDiagramCanvas.connectionPerfectionizer(
        DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
        sourceGraphicInfo, targetGraphicInfo, graphicInfoList);

    // Assert
    assertTrue(actualConnectionPerfectionizerResult.isEmpty());
    assertSame(graphicInfoList, actualConnectionPerfectionizerResult);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  public void testConnectionPerfectionizer2() {
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
    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();

    // Act
    List<GraphicInfo> actualConnectionPerfectionizerResult = defaultProcessDiagramCanvas.connectionPerfectionizer(null,
        DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, graphicInfoList);

    // Assert
    assertTrue(actualConnectionPerfectionizerResult.isEmpty());
    assertSame(graphicInfoList, actualConnectionPerfectionizerResult);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  public void testConnectionPerfectionizer3() {
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
    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();

    // Act
    List<GraphicInfo> actualConnectionPerfectionizerResult = defaultProcessDiagramCanvas.connectionPerfectionizer(
        DefaultProcessDiagramCanvas.SHAPE_TYPE.Rhombus, DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
        sourceGraphicInfo, targetGraphicInfo, graphicInfoList);

    // Assert
    assertTrue(actualConnectionPerfectionizerResult.isEmpty());
    assertSame(graphicInfoList, actualConnectionPerfectionizerResult);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  public void testConnectionPerfectionizer4() {
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
    ArrayList<GraphicInfo> graphicInfoList = new ArrayList<>();

    // Act
    List<GraphicInfo> actualConnectionPerfectionizerResult = defaultProcessDiagramCanvas.connectionPerfectionizer(
        DefaultProcessDiagramCanvas.SHAPE_TYPE.Ellipse, DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
        sourceGraphicInfo, targetGraphicInfo, graphicInfoList);

    // Assert
    assertTrue(actualConnectionPerfectionizerResult.isEmpty());
    assertSame(graphicInfoList, actualConnectionPerfectionizerResult);
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  public void testConnectionPerfectionizer5() {
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

    // Act and Assert
    assertSame(graphicInfoList,
        defaultProcessDiagramCanvas.connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
            DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, graphicInfoList));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  public void testConnectionPerfectionizer6() {
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

    // Act and Assert
    assertSame(graphicInfoList, defaultProcessDiagramCanvas.connectionPerfectionizer(null,
        DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, graphicInfoList));
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  public void testNewDefaultProcessDiagramCanvas() throws DOMException {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    FontMetrics fontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Arial", font.getName());
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.activityFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.annotationFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.labelFontName);
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
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(1, sVGCanvasSize.height);
    assertEquals(1, sVGCanvasSize.width);
    assertEquals(1, actualDefaultProcessDiagramCanvas.canvasHeight);
    assertEquals(1, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minX);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minY);
    assertEquals(1.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(1.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
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
    assertFalse(actualDefaultProcessDiagramCanvas.closed);
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
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
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  public void testNewDefaultProcessDiagramCanvas2() throws DOMException {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(2, 1, 1, 1);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    FontMetrics fontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Arial", font.getName());
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.activityFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.annotationFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.labelFontName);
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
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(1, sVGCanvasSize.height);
    assertEquals(1, actualDefaultProcessDiagramCanvas.canvasHeight);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minX);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minY);
    assertEquals(1.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
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
    assertEquals(2, sVGCanvasSize.width);
    assertEquals(2, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(2.0d, sVGCanvasSize.getWidth(), 0.0);
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
    assertFalse(actualDefaultProcessDiagramCanvas.closed);
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
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
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  public void testNewDefaultProcessDiagramCanvas3() throws DOMException {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-1, 1, 1, 1);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    FontMetrics fontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Arial", font.getName());
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.activityFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.annotationFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.labelFontName);
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
    assertNull(((GenericElementNS) lastChild).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) lastChild).getLastElementChild());
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
    assertNull(((GenericElementNS) lastChild).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) lastChild).getXblLastElementChild());
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
    assertNull(((GenericElementNS) lastChild).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) lastChild).getXblLastChild());
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
    assertNull(lastChild.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(lastChild.getLastChild());
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
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(-1, sVGCanvasSize.width);
    assertEquals(-1, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(-1.0d, sVGCanvasSize.getWidth(), 0.0);
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
    assertEquals(0, ((GenericElementNS) lastChild).getChildElementCount());
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
    assertEquals(1, sVGCanvasSize.height);
    assertEquals(1, actualDefaultProcessDiagramCanvas.canvasHeight);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minX);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minY);
    assertEquals(1.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
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
    assertFalse(lastChild.hasChildNodes());
    assertFalse(actualDefaultProcessDiagramCanvas.closed);
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
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
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }

  /**
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  public void testNewDefaultProcessDiagramCanvas4() throws DOMException {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(255, 0, 1, 1);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    FontMetrics fontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Arial", font.getName());
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.activityFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.annotationFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.labelFontName);
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
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(0, sVGCanvasSize.height);
    assertEquals(0, actualDefaultProcessDiagramCanvas.canvasHeight);
    assertEquals(0.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(0.0d, transform.getShearX(), 0.0);
    assertEquals(0.0d, transform.getShearY(), 0.0);
    assertEquals(0.0d, transform.getTranslateX(), 0.0);
    assertEquals(0.0d, transform.getTranslateY(), 0.0);
    assertEquals(0.0f, ((BasicStroke) stroke).getDashPhase(), 0.0f);
    assertEquals(0.0f, font.getItalicAngle(), 0.0f);
    assertEquals(1, font.getStyle());
    assertEquals(1, actualDefaultProcessDiagramCanvas.minX);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minY);
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
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
    assertEquals(255, sVGCanvasSize.width);
    assertEquals(255, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(255.0d, sVGCanvasSize.getWidth(), 0.0);
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
    assertFalse(actualDefaultProcessDiagramCanvas.closed);
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
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
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  public void testNewDefaultProcessDiagramCanvas5() throws DOMException {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    FontMetrics fontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualDefaultProcessDiagramCanvas.activityFontName);
    assertEquals("Annotation Font Name", actualDefaultProcessDiagramCanvas.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualDefaultProcessDiagramCanvas.labelFontName);
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
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(1, sVGCanvasSize.height);
    assertEquals(1, sVGCanvasSize.width);
    assertEquals(1, actualDefaultProcessDiagramCanvas.canvasHeight);
    assertEquals(1, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minX);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minY);
    assertEquals(1.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(1.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
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
    assertFalse(actualDefaultProcessDiagramCanvas.closed);
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
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
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  public void testNewDefaultProcessDiagramCanvas6() throws DOMException {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1, null,
        null, null);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    FontMetrics fontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Arial", font.getName());
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.activityFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.annotationFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.labelFontName);
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
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(1, sVGCanvasSize.height);
    assertEquals(1, sVGCanvasSize.width);
    assertEquals(1, actualDefaultProcessDiagramCanvas.canvasHeight);
    assertEquals(1, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minX);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minY);
    assertEquals(1.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(1.0d, sVGCanvasSize.getWidth(), 0.0);
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
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
    assertFalse(actualDefaultProcessDiagramCanvas.closed);
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
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
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  public void testNewDefaultProcessDiagramCanvas7() throws DOMException {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-1, 1, 1, 1,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    FontMetrics fontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Activity Font Name", font.getName());
    assertEquals("Activity Font Name", actualDefaultProcessDiagramCanvas.activityFontName);
    assertEquals("Annotation Font Name", actualDefaultProcessDiagramCanvas.annotationFontName);
    assertEquals("Dialog", font.getFamily());
    assertEquals("Dialog.bold", font.getFontName());
    assertEquals("Dialog.bold", font.getPSName());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", ((GenericComment) firstChild2).getData());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", generatorContext.getComment());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getNodeValue());
    assertEquals("Generated by the Batik Graphics2D SVG Generator", firstChild2.getTextContent());
    assertEquals("Label Font Name", actualDefaultProcessDiagramCanvas.labelFontName);
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
    assertNull(((GenericElementNS) lastChild).getFirstElementChild());
    assertNull(((GenericElementNS) firstElementChild).getLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getLastElementChild());
    assertNull(((GenericElementNS) documentElement).getLastElementChild());
    assertNull(((GenericElementNS) lastChild).getLastElementChild());
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
    assertNull(((GenericElementNS) lastChild).getXblFirstElementChild());
    assertNull(((GenericComment) firstChild2).getXblLastElementChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastElementChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastElementChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastElementChild());
    assertNull(((GenericElementNS) documentElement).getXblLastElementChild());
    assertNull(((GenericElementNS) lastChild).getXblLastElementChild());
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
    assertNull(((GenericElementNS) lastChild).getXblFirstChild());
    assertNull(((GenericComment) firstChild2).getXblLastChild());
    assertNull(((GenericElementNS) firstElementChild).getXblLastChild());
    assertNull(((GenericElementNS) genericDefinitions).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup).getXblLastChild());
    assertNull(((GenericElementNS) topLevelGroup2).getXblLastChild());
    assertNull(((GenericElementNS) documentElement).getXblLastChild());
    assertNull(((GenericElementNS) lastChild).getXblLastChild());
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
    assertNull(lastChild.getFirstChild());
    assertNull(firstElementChild.getLastChild());
    assertNull(genericDefinitions.getLastChild());
    assertNull(topLevelGroup.getLastChild());
    assertNull(topLevelGroup2.getLastChild());
    assertNull(documentElement.getLastChild());
    assertNull(firstChild2.getLastChild());
    assertNull(lastChild.getLastChild());
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
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(-1, sVGCanvasSize.width);
    assertEquals(-1, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(-1.0d, sVGCanvasSize.getWidth(), 0.0);
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
    assertEquals(0, ((GenericElementNS) lastChild).getChildElementCount());
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
    assertEquals(1, sVGCanvasSize.height);
    assertEquals(1, actualDefaultProcessDiagramCanvas.canvasHeight);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minX);
    assertEquals(1, actualDefaultProcessDiagramCanvas.minY);
    assertEquals(1.0d, sVGCanvasSize.getHeight(), 0.0);
    assertEquals(1.0d, transform.getDeterminant(), 0.0);
    assertEquals(1.0d, transform.getScaleX(), 0.0);
    assertEquals(1.0d, transform.getScaleY(), 0.0);
    assertEquals(1.0f, ((AlphaComposite) composite).getAlpha(), 0.0f);
    assertEquals(1.0f, ((BasicStroke) stroke).getLineWidth(), 0.0f);
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
    assertFalse(lastChild.hasChildNodes());
    assertFalse(actualDefaultProcessDiagramCanvas.closed);
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
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
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
    assertSame(lastChild2, ((GenericElementNS) root2).getLastElementChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastChild());
    assertSame(lastChild2, ((GenericElementNS) root2).getXblLastElementChild());
    assertSame(extensionHandler, dOMTreeManager.getExtensionHandler());
    assertSame(extensionHandler, generatorContext.getExtensionHandler());
    assertSame(imageHandler, generatorContext.getImageHandler());
  }
}
