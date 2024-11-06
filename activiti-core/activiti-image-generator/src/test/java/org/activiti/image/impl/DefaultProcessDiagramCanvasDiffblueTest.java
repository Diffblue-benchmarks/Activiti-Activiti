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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
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
import java.util.function.BiFunction;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AssociationDirection;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.CancelEventDefinition;
import org.activiti.bpmn.model.ExtensionAttribute;
import org.activiti.bpmn.model.ExtensionElement;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.image.impl.icon.BusinessRuleTaskIconType;
import org.activiti.image.impl.icon.CompensateIconType;
import org.activiti.image.impl.icon.IconType;
import org.activiti.image.impl.icon.TaskIconType;
import org.apache.batik.dom.GenericComment;
import org.apache.batik.dom.GenericDocument;
import org.apache.batik.dom.GenericElementNS;
import org.apache.batik.ext.awt.g2d.GraphicContext;
import org.apache.batik.svggen.DOMTreeManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

class DefaultProcessDiagramCanvasDiffblueTest {
  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int)")
  void testNewDefaultProcessDiagramCanvas() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-1, 1, 1, 1);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    assertNull(((GenericElementNS) lastChild).getFirstElementChild());
    assertNull(((GenericElementNS) lastChild).getLastElementChild());
    assertNull(((GenericElementNS) lastChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) lastChild).getXblLastElementChild());
    assertNull(((GenericElementNS) lastChild).getXblFirstChild());
    assertNull(((GenericElementNS) lastChild).getXblLastChild());
    assertNull(lastChild.getFirstChild());
    assertNull(lastChild.getLastChild());
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(-1, sVGCanvasSize.width);
    assertEquals(-1, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(-1.0d, sVGCanvasSize.getWidth());
    assertEquals(0, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(lastChild.hasChildNodes());
    Font expectedFont = actualDefaultProcessDiagramCanvas.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)")
  void testNewDefaultProcessDiagramCanvas2() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-1, 1, 1, 1,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    assertNull(((GenericElementNS) lastChild).getFirstElementChild());
    assertNull(((GenericElementNS) lastChild).getLastElementChild());
    assertNull(((GenericElementNS) lastChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) lastChild).getXblLastElementChild());
    assertNull(((GenericElementNS) lastChild).getXblFirstChild());
    assertNull(((GenericElementNS) lastChild).getXblLastChild());
    assertNull(lastChild.getFirstChild());
    assertNull(lastChild.getLastChild());
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(-1, sVGCanvasSize.width);
    assertEquals(-1, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(-1.0d, sVGCanvasSize.getWidth());
    assertEquals(0, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(lastChild.hasChildNodes());
    Font expectedFont = actualDefaultProcessDiagramCanvas.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int)")
  void testNewDefaultProcessDiagramCanvas3() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-1, 1, 1, 1);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    Font expectedFont = actualDefaultProcessDiagramCanvas.fontMetrics.getFont();
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
    assertSame(lastChild, ((GenericElementNS) root).getLastElementChild());
    assertSame(lastChild, ((GenericElementNS) firstElementChild).getNextElementSibling());
    assertSame(lastChild, ((GenericElementNS) root).getXblLastChild());
    assertSame(lastChild, ((GenericElementNS) root).getXblLastElementChild());
    assertSame(lastChild, ((GenericElementNS) firstElementChild).getXblNextElementSibling());
    assertSame(lastChild, ((GenericElementNS) firstElementChild).getXblNextSibling());
    assertSame(lastChild, firstElementChild.getNextSibling());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)")
  void testNewDefaultProcessDiagramCanvas4() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-1, 1, 1, 1,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    assertNull(((GenericElementNS) lastChild).getFirstElementChild());
    assertNull(((GenericElementNS) lastChild).getLastElementChild());
    assertNull(((GenericElementNS) lastChild).getXblFirstElementChild());
    assertNull(((GenericElementNS) lastChild).getXblLastElementChild());
    assertNull(((GenericElementNS) lastChild).getXblFirstChild());
    assertNull(((GenericElementNS) lastChild).getXblLastChild());
    assertNull(lastChild.getFirstChild());
    assertNull(lastChild.getLastChild());
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(-1, sVGCanvasSize.width);
    assertEquals(-1, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(-1.0d, sVGCanvasSize.getWidth());
    assertEquals(0, ((GenericElementNS) lastChild).getChildElementCount());
    assertFalse(lastChild.hasChildNodes());
    Font expectedFont = actualDefaultProcessDiagramCanvas.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <ul>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#fontMetrics} Font Family
   * is {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int, String, String, String); then return fontMetrics Font Family is 'Arial'")
  void testNewDefaultProcessDiagramCanvas_thenReturnFontMetricsFontFamilyIsArial() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1, null,
        null, null);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    FontMetrics fontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Arial", font.getFamily());
    assertEquals("Arial", font.getName());
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.activityFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.annotationFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.labelFontName);
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <ul>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#fontMetrics} Font Family
   * is {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int, String, String, String); then return fontMetrics Font Family is 'Arial'")
  void testNewDefaultProcessDiagramCanvas_thenReturnFontMetricsFontFamilyIsArial2() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1, null,
        null, null);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    FontMetrics fontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
    Font font = fontMetrics.getFont();
    assertEquals("Arial", font.getFamily());
    assertEquals("Arial", font.getName());
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.activityFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.annotationFontName);
    assertEquals("Arial", actualDefaultProcessDiagramCanvas.labelFontName);
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font2, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <ul>
   *   <li>When {@code Activity Font Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int, String, String, String); when 'Activity Font Name'")
  void testNewDefaultProcessDiagramCanvas_whenActivityFontName() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    Font expectedFont = actualDefaultProcessDiagramCanvas.fontMetrics.getFont();
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}.
   * <ul>
   *   <li>When {@code Activity Font Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int, String, String, String)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int, String, String, String); when 'Activity Font Name'")
  void testNewDefaultProcessDiagramCanvas_whenActivityFontName2() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1,
        "Activity Font Name", "Label Font Name", "Annotation Font Name");

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    Font expectedFont = actualDefaultProcessDiagramCanvas.fontMetrics.getFont();
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <ul>
   *   <li>When minus one.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#minY} is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int); when minus one; then return minY is two")
  void testNewDefaultProcessDiagramCanvas_whenMinusOne_thenReturnMinYIsTwo() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-1, 1, 1, 2);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    assertEquals(2, actualDefaultProcessDiagramCanvas.minY);
    Font expectedFont = actualDefaultProcessDiagramCanvas.fontMetrics.getFont();
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
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
    assertSame(lastChild, ((GenericElementNS) root).getLastElementChild());
    assertSame(lastChild, ((GenericElementNS) firstElementChild).getNextElementSibling());
    assertSame(lastChild, ((GenericElementNS) root).getXblLastChild());
    assertSame(lastChild, ((GenericElementNS) root).getXblLastElementChild());
    assertSame(lastChild, ((GenericElementNS) firstElementChild).getXblNextElementSibling());
    assertSame(lastChild, ((GenericElementNS) firstElementChild).getXblNextSibling());
    assertSame(lastChild, firstElementChild.getNextSibling());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <ul>
   *   <li>When nine.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#minX} is minus one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int); when nine; then return minX is minus one")
  void testNewDefaultProcessDiagramCanvas_whenNine_thenReturnMinXIsMinusOne() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(9, 1, -1, 1);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    assertEquals(-1, actualDefaultProcessDiagramCanvas.minX);
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(9, sVGCanvasSize.width);
    assertEquals(9, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(9.0d, sVGCanvasSize.getWidth());
    Font expectedFont = actualDefaultProcessDiagramCanvas.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#g} SVGCanvasSize
   * {@link Dimension#width} is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int); when one; then return g SVGCanvasSize width is one")
  void testNewDefaultProcessDiagramCanvas_whenOne_thenReturnGSVGCanvasSizeWidthIsOne() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(1, sVGCanvasSize.width);
    assertEquals(1, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(1.0d, sVGCanvasSize.getWidth());
    Font expectedFont = actualDefaultProcessDiagramCanvas.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#g} SVGCanvasSize
   * {@link Dimension#width} is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int); when one; then return g SVGCanvasSize width is one")
  void testNewDefaultProcessDiagramCanvas_whenOne_thenReturnGSVGCanvasSizeWidthIsOne2() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(1, sVGCanvasSize.width);
    assertEquals(1, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(1.0d, sVGCanvasSize.getWidth());
    Font expectedFont = actualDefaultProcessDiagramCanvas.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#g} SVGCanvasSize
   * {@link Dimension#width} is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int); when two; then return g SVGCanvasSize width is two")
  void testNewDefaultProcessDiagramCanvas_whenTwo_thenReturnGSVGCanvasSizeWidthIsTwo() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(2, 1, 1, 1);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(2, sVGCanvasSize.width);
    assertEquals(2, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(2.0d, sVGCanvasSize.getWidth());
    Font expectedFont = actualDefaultProcessDiagramCanvas.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}.
   * <ul>
   *   <li>When two.</li>
   *   <li>Then return {@link DefaultProcessDiagramCanvas#g} SVGCanvasSize
   * {@link Dimension#width} is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#DefaultProcessDiagramCanvas(int, int, int, int)}
   */
  @Test
  @DisplayName("Test new DefaultProcessDiagramCanvas(int, int, int, int); when two; then return g SVGCanvasSize width is two")
  void testNewDefaultProcessDiagramCanvas_whenTwo_thenReturnGSVGCanvasSizeWidthIsTwo2() {
    // Arrange and Act
    DefaultProcessDiagramCanvas actualDefaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(2, 1, 1, 1);

    // Assert
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = actualDefaultProcessDiagramCanvas.g;
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
    Dimension sVGCanvasSize = processDiagramSVGGraphics2D.getSVGCanvasSize();
    assertEquals(2, sVGCanvasSize.width);
    assertEquals(2, actualDefaultProcessDiagramCanvas.canvasWidth);
    assertEquals(2.0d, sVGCanvasSize.getWidth());
    Font expectedFont = actualDefaultProcessDiagramCanvas.fontMetrics.getFont();
    Font font = processDiagramSVGGraphics2D.getFont();
    assertEquals(expectedFont, font);
    Color background = processDiagramSVGGraphics2D.getBackground();
    assertEquals(background, background.darker().brighter().brighter());
    assertEquals(background, background.brighter());
    FontRenderContext expectedFontRenderContext = processDiagramSVGGraphics2D.getFontRenderContext();
    GraphicContext graphicContext = processDiagramSVGGraphics2D.getGraphicContext();
    assertEquals(expectedFontRenderContext, graphicContext.getFontRenderContext());
    assertEquals(sVGCanvasSize, sVGCanvasSize.getSize());
    Color expectedColor = actualDefaultProcessDiagramCanvas.SUBPROCESS_BORDER_COLOR;
    Color color = processDiagramSVGGraphics2D.getColor();
    assertEquals(expectedColor, color);
    assertSame(background, graphicContext.getBackground());
    assertSame(color, processDiagramSVGGraphics2D.getPaint());
    assertSame(color, graphicContext.getColor());
    assertSame(color, graphicContext.getPaint());
    assertSame(font, graphicContext.getFont());
    FontMetrics expectedFontMetrics = actualDefaultProcessDiagramCanvas.fontMetrics;
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
   * Test {@link DefaultProcessDiagramCanvas#generateImage()}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#generateImage()}
   */
  @Test
  @DisplayName("Test generateImage()")
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
   * Test {@link DefaultProcessDiagramCanvas#generateImage()}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#generateImage()}
   */
  @Test
  @DisplayName("Test generateImage()")
  void testGenerateImage2() throws IOException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 0, -1, 1);

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
  void testClose() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.close();

    // Assert
    assertTrue(defaultProcessDiagramCanvas.closed);
  }

  /**
   * Test {@link DefaultProcessDiagramCanvas#close()}.
   * <p>
   * Method under test: {@link DefaultProcessDiagramCanvas#close()}
   */
  @Test
  @DisplayName("Test close()")
  void testClose2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    // Act
    defaultProcessDiagramCanvas.close();

    // Assert
    assertTrue(defaultProcessDiagramCanvas.closed);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneStartEvent(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneStartEvent(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneStartEvent(String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneStartEvent(String, GraphicInfo)")
  void testDrawNoneStartEvent4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    ActivitiListener element = new ActivitiListener();
    element.addAttribute(new ExtensionAttribute("id"));

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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneStartEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTimerStartEvent(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTimerStartEvent(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTimerStartEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTimerStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTimerStartEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSignalStartEvent(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSignalStartEvent(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSignalStartEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSignalStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSignalStartEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo)")
  void testDrawMessageStartEvent3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo)")
  void testDrawMessageStartEvent4() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
  void testDrawMessageStartEvent_given05_whenGraphicInfoHeightIs052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  void testDrawMessageStartEvent_givenGraphicInfoElementIsActivitiListener2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given zero.</li>
   *   <li>When {@link GraphicInfo} (default constructor) XmlColumnNumber is
   * zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMessageStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawMessageStartEvent(String, GraphicInfo); given zero; when GraphicInfo (default constructor) XmlColumnNumber is zero")
  void testDrawMessageStartEvent_givenZero_whenGraphicInfoXmlColumnNumberIsZero() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(0);
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  @DisplayName("Test drawStartEvent(String, GraphicInfo, IconType)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  @DisplayName("Test drawStartEvent(String, GraphicInfo, IconType)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) X is {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  @DisplayName("Test drawStartEvent(String, GraphicInfo, IconType); given '-0.5'; when GraphicInfo (default constructor) X is '-0.5'")
  void testDrawStartEvent_given05_whenGraphicInfoXIs05() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  @DisplayName("Test drawStartEvent(String, GraphicInfo, IconType); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}.
   * <ul>
   *   <li>When {@link CompensateIconType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawStartEvent(String, GraphicInfo, IconType)}
   */
  @Test
  @DisplayName("Test drawStartEvent(String, GraphicInfo, IconType); when CompensateIconType (default constructor)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo)")
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo)")
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawNoneEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawNoneEndEvent(String, String, GraphicInfo); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(4, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(5, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
  void testDrawErrorEndEvent6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
  void testDrawErrorEndEvent7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
  void testDrawErrorEndEvent8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
  void testDrawErrorEndEvent9() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo)")
  void testDrawErrorEndEvent10() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo); when empty string")
  void testDrawErrorEndEvent_whenEmptyString2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorEndEvent(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorEndEvent(String, String, GraphicInfo); when 'null'")
  void testDrawErrorEndEvent_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo)")
  void testDrawErrorStartEvent3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo)")
  void testDrawErrorStartEvent4() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
  void testDrawErrorStartEvent_given05_whenGraphicInfoHeightIs052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawErrorStartEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawErrorStartEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  void testDrawErrorStartEvent_givenGraphicInfoElementIsActivitiListener2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)")
  void testDrawCatchingEvent4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)")
  void testDrawCatchingEvent5() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)")
  void testDrawCatchingEvent6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <ul>
   *   <li>Given {@code 0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String); given '0.5'; when GraphicInfo (default constructor) Height is '0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <ul>
   *   <li>Given {@code 0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String); given '0.5'; when GraphicInfo (default constructor) Height is '0.5'")
  void testDrawCatchingEvent_given05_whenGraphicInfoHeightIs052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  void testDrawCatchingEvent_givenGraphicInfoElementIsActivitiListener2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <ul>
   *   <li>When {@link CompensateIconType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String); when CompensateIconType (default constructor)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <ul>
   *   <li>When {@link CompensateIconType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String); when CompensateIconType (default constructor)")
  void testDrawCatchingEvent_whenCompensateIconType2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <ul>
   *   <li>When {@code timer}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String); when 'timer'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}.
   * <ul>
   *   <li>When {@code timer}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingEvent(String, GraphicInfo, boolean, IconType, String)}
   */
  @Test
  @DisplayName("Test drawCatchingEvent(String, GraphicInfo, boolean, IconType, String); when 'timer'")
  void testDrawCatchingEvent_whenTimer2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting5() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting9() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting10() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  void testDrawCatchingCompensateEventWithIdGraphicInfoIsInterrupting_given052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting7() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting9() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting10() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting11() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting12() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting13() {
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
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting_given052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingCompensateEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingCompensateEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
  void testDrawCatchingCompensateEventWithIdNameGraphicInfoIsInterrupting_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting9() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting10() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  void testDrawCatchingTimerEventWithIdGraphicInfoIsInterrupting_given052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting9() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting10() {
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
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting_given052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when empty string")
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting_whenEmptyString2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingTimerEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingTimerEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
  void testDrawCatchingTimerEventWithIdNameGraphicInfoIsInterrupting_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting9() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting10() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  void testDrawCatchingErrorEventWithIdGraphicInfoIsInterrupting_given052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting9() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting10() {
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
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting_given052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when empty string")
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting_whenEmptyString2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingErrorEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingErrorEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
  void testDrawCatchingErrorEventWithIdNameGraphicInfoIsInterrupting_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting9() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting10() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  void testDrawCatchingSignalEventWithIdGraphicInfoIsInterrupting_given052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting5() {
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
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting9() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting10() {
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
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting_given052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when empty string")
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting_whenEmptyString2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code fill}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'fill'")
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting_whenFill() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(10);
    graphicInfo.setY(3.0d);

    // Act
    defaultProcessDiagramCanvas.drawCatchingSignalEvent("42", "fill", graphicInfo, true);

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(3, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingSignalEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingSignalEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
  void testDrawCatchingSignalEventWithIdNameGraphicInfoIsInterrupting_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting9() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting10() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   * with {@code id}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, GraphicInfo, boolean) with 'id', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  void testDrawCatchingMessageEventWithIdGraphicInfoIsInterrupting_given052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting6() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting9() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'")
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting10() {
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
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given '-0.5'")
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting_given052() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>Given minus one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; given minus one")
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting_givenMinusOne() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(-1);
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when empty string")
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting_whenEmptyString2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code isInterrupting}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCatchingMessageEvent(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCatchingMessageEvent(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'isInterrupting'; when 'null'")
  void testDrawCatchingMessageEventWithIdNameGraphicInfoIsInterrupting_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingCompensateEvent(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingCompensateEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingCompensateEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>When {@link GraphicInfo} (default constructor) Height is ten.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingCompensateEvent(String, GraphicInfo); when GraphicInfo (default constructor) Height is ten")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>When {@link GraphicInfo} (default constructor) Height is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingCompensateEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingCompensateEvent(String, GraphicInfo); when GraphicInfo (default constructor) Height is two")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingSignalEvent(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingSignalEvent(String, GraphicInfo); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingSignalEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>When {@link GraphicInfo} (default constructor) Height is ten.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingSignalEvent(String, GraphicInfo); when GraphicInfo (default constructor) Height is ten")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>When {@link GraphicInfo} (default constructor) Height is two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingSignalEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingSignalEvent(String, GraphicInfo); when GraphicInfo (default constructor) Height is two")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingNoneEvent(String, GraphicInfo)")
  void testDrawThrowingNoneEvent() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingNoneEvent(String, GraphicInfo)")
  void testDrawThrowingNoneEvent2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingNoneEvent(String, GraphicInfo)")
  void testDrawThrowingNoneEvent3() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@code 0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code 0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingNoneEvent(String, GraphicInfo); given '0.5'; when GraphicInfo (default constructor) Height is '0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawThrowingNoneEvent(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawThrowingNoneEvent(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   * with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY},
   * {@code conditional}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   * with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY},
   * {@code conditional}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   * with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY},
   * {@code conditional}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   * with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY},
   * {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
  void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditionalHighLighted() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   * with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY},
   * {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
  void testDrawSequenceflowWithSrcXSrcYTargetXTargetYConditionalHighLighted2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   * with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY},
   * {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   * with {@code xPoints}, {@code yPoints}, {@code conditional},
   * {@code isDefault}, {@code highLighted}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int[], int[], boolean, boolean, boolean) with 'xPoints', 'yPoints', 'conditional', 'isDefault', 'highLighted'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   * with {@code xPoints}, {@code yPoints}, {@code conditional},
   * {@code isDefault}, {@code highLighted}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int[], int[], boolean, boolean, boolean) with 'xPoints', 'yPoints', 'conditional', 'isDefault', 'highLighted'")
  void testDrawSequenceflowWithXPointsYPointsConditionalIsDefaultHighLighted2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   * with {@code xPoints}, {@code yPoints}, {@code conditional},
   * {@code isDefault}, {@code highLighted}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int[], int[], boolean, boolean, boolean) with 'xPoints', 'yPoints', 'conditional', 'isDefault', 'highLighted'")
  void testDrawSequenceflowWithXPointsYPointsConditionalIsDefaultHighLighted3() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   * with {@code xPoints}, {@code yPoints}, {@code conditional},
   * {@code isDefault}, {@code highLighted}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflow(int[], int[], boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflow(int[], int[], boolean, boolean, boolean) with 'xPoints', 'yPoints', 'conditional', 'isDefault', 'highLighted'")
  void testDrawSequenceflowWithXPointsYPointsConditionalIsDefaultHighLighted4() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawAssociation(int[], int[], AssociationDirection, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawAssociation(int[], int[], AssociationDirection, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawAssociation(int[], int[], AssociationDirection, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawAssociation(int[], int[], AssociationDirection, boolean)")
  void testDrawAssociation4() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawAssociation(int[], int[], AssociationDirection, boolean)")
  void testDrawAssociation5() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawAssociation(int[], int[], AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawAssociation(int[], int[], AssociationDirection, boolean)")
  void testDrawAssociation6() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
  void testDrawConnection7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
  void testDrawConnection8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
  void testDrawConnection9() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
  void testDrawConnection10() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
  void testDrawConnection11() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)")
  void testDrawConnection12() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <ul>
   *   <li>When {@code association}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean); when 'association'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}.
   * <ul>
   *   <li>When {@code association}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean)}
   */
  @Test
  @DisplayName("Test drawConnection(int[], int[], boolean, boolean, String, AssociationDirection, boolean); when 'association'")
  void testDrawConnection_whenAssociation2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)}
   * with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY},
   * {@code conditional}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflowWithoutArrow(int, int, int, int, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)}
   * with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY},
   * {@code conditional}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflowWithoutArrow(int, int, int, int, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)}
   * with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY},
   * {@code conditional}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflowWithoutArrow(int, int, int, int, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   * with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY},
   * {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   * with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY},
   * {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   * with {@code srcX}, {@code srcY}, {@code targetX}, {@code targetY},
   * {@code conditional}, {@code highLighted}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawSequenceflowWithoutArrow(int, int, int, int, boolean, boolean) with 'srcX', 'srcY', 'targetX', 'targetY', 'conditional', 'highLighted'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   * with {@code icon}, {@code id}, {@code name}, {@code graphicInfo}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(TaskIconType, String, String, GraphicInfo) with 'icon', 'id', 'name', 'graphicInfo'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   * with {@code icon}, {@code id}, {@code name}, {@code graphicInfo}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(TaskIconType, String, String, GraphicInfo) with 'icon', 'id', 'name', 'graphicInfo'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   * with {@code icon}, {@code id}, {@code name}, {@code graphicInfo}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(TaskIconType, String, String, GraphicInfo) with 'icon', 'id', 'name', 'graphicInfo'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   * with {@code icon}, {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(TaskIconType, String, String, GraphicInfo) with 'icon', 'id', 'name', 'graphicInfo'; given 'java.lang.Object'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   * with {@code icon}, {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(TaskIconType, String, String, GraphicInfo) with 'icon', 'id', 'name', 'graphicInfo'; when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   * with {@code icon}, {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(TaskIconType, String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(TaskIconType, String, String, GraphicInfo) with 'icon', 'id', 'name', 'graphicInfo'; when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   * with {@code id}, {@code name}, {@code graphicInfo}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo) with 'id', 'name', 'graphicInfo'")
  void testDrawTaskWithIdNameGraphicInfo() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'; given 'java.lang.Object'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'; when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'; when 'false'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   * with {@code id}, {@code name}, {@code graphicInfo}, {@code thickBorder}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo, boolean) with 'id', 'name', 'graphicInfo', 'thickBorder'; when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   * with {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo) with 'id', 'name', 'graphicInfo'; given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   * with {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo) with 'id', 'name', 'graphicInfo'; when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   * with {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo) with 'id', 'name', 'graphicInfo'; when 'Name'")
  void testDrawTaskWithIdNameGraphicInfo_whenName() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   * with {@code id}, {@code name}, {@code graphicInfo}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTask(String, String, GraphicInfo) with 'id', 'name', 'graphicInfo'; when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawPoolOrLane(String, String, GraphicInfo)")
  void testDrawPoolOrLane() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawPoolOrLane(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawPoolOrLane(String, String, GraphicInfo)")
  void testDrawPoolOrLane3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-6, 1, 1, 1);
    defaultProcessDiagramCanvas.drawMultiInstanceMarker(true, 2, 3, 1, 4);

    GraphicInfo graphicInfo = new GraphicInfo();
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
    assertTrue(lastChild instanceof GenericElementNS);
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawPoolOrLane(String, String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  void testDrawPoolOrLane_givenGraphicInfoElementIsActivitiListener() {
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
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawPoolOrLane(String, String, GraphicInfo); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawPoolOrLane(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawPoolOrLane(String, String, GraphicInfo); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineCentredText(String, int, int, int, int)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineCentredText(String, int, int, int, int)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineCentredText(String, int, int, int, int)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <ul>
   *   <li>When {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineCentredText(String, int, int, int, int); when 'Arial'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <ul>
   *   <li>When {@link Float#PRECISION}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineCentredText(String, int, int, int, int); when PRECISION")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}.
   * <ul>
   *   <li>When twelve.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineCentredText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineCentredText(String, int, int, int, int); when twelve")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int)")
  void testDrawMultilineAnnotationText4() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int)")
  void testDrawMultilineAnnotationText5() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int)")
  void testDrawMultilineAnnotationText6() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <ul>
   *   <li>When {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int); when 'Arial'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <ul>
   *   <li>When {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int); when 'Arial'")
  void testDrawMultilineAnnotationText_whenArial2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <ul>
   *   <li>When {@link Float#PRECISION}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int); when PRECISION")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <ul>
   *   <li>When {@link Float#PRECISION}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int); when PRECISION")
  void testDrawMultilineAnnotationText_whenPrecision2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <ul>
   *   <li>When twelve.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int); when twelve")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}.
   * <ul>
   *   <li>When twelve.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineAnnotationText(String, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultilineAnnotationText(String, int, int, int, int); when twelve")
  void testDrawMultilineAnnotationText_whenTwelve2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <ul>
   *   <li>When {@code Arial}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean); when 'Arial'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <ul>
   *   <li>When {@link Float#PRECISION}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean); when PRECISION")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <ul>
   *   <li>When twelve.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean); when twelve")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}.
   * <ul>
   *   <li>When zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultilineText(String, int, int, int, int, boolean)}
   */
  @Test
  @DisplayName("Test drawMultilineText(String, int, int, int, int, boolean); when zero")
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#fitTextToWidth(String, int)}
   */
  @Test
  @DisplayName("Test fitTextToWidth(String, int); when empty string; then return empty string")
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#fitTextToWidth(String, int)}
   */
  @Test
  @DisplayName("Test fitTextToWidth(String, int); when fifty-one; then return 'Origi...'")
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#fitTextToWidth(String, int)}
   */
  @Test
  @DisplayName("Test fitTextToWidth(String, int); when 'Original'; then return '...'")
  void testFitTextToWidth_whenOriginal_thenReturnDotDotDot() {
    // Arrange, Act and Assert
    assertEquals("...", (new DefaultProcessDiagramCanvas(1, 1, 1, 1)).fitTextToWidth("Original", 1));
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawUserTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawUserTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawUserTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawUserTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawUserTask(String, String, GraphicInfo); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawUserTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawUserTask(String, String, GraphicInfo); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawScriptTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawScriptTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawScriptTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawScriptTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawScriptTask(String, String, GraphicInfo); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawScriptTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawScriptTask(String, String, GraphicInfo); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawServiceTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawServiceTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawServiceTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawServiceTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawServiceTask(String, String, GraphicInfo); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawServiceTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawServiceTask(String, String, GraphicInfo); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawReceiveTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawReceiveTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawReceiveTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawReceiveTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawReceiveTask(String, String, GraphicInfo); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawReceiveTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawReceiveTask(String, String, GraphicInfo); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSendTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSendTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSendTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSendTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSendTask(String, String, GraphicInfo); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawSendTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawSendTask(String, String, GraphicInfo); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo)")
  void testDrawManualTask5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo)")
  void testDrawManualTask6() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo)")
  void testDrawManualTask7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo)")
  void testDrawManualTask8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo); when empty string")
  void testDrawManualTask_whenEmptyString2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawManualTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawManualTask(String, String, GraphicInfo); when 'null'")
  void testDrawManualTask_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo)")
  void testDrawBusinessRuleTask5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo)")
  void testDrawBusinessRuleTask6() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo)")
  void testDrawBusinessRuleTask7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo)")
  void testDrawBusinessRuleTask8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo); when empty string")
  void testDrawBusinessRuleTask_whenEmptyString2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawBusinessRuleTask(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawBusinessRuleTask(String, String, GraphicInfo); when 'null'")
  void testDrawBusinessRuleTask_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class)")
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals("", root.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class)")
  void testDrawExpandedSubProcess4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class)")
  void testDrawExpandedSubProcess5() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class)")
  void testDrawExpandedSubProcess6() throws DOMException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class)")
  void testDrawExpandedSubProcess7() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);
    defaultProcessDiagramCanvas.drawMultiInstanceMarker(true, 2, 3, 1, 10);

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
    assertEquals(1, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <ul>
   *   <li>Given {@code -0.5}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Height is
   * {@code -0.5}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class); given '-0.5'; when GraphicInfo (default constructor) Height is '-0.5'")
  void testDrawExpandedSubProcess_given05_whenGraphicInfoHeightIs052() {
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
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  void testDrawExpandedSubProcess_givenGraphicInfoElementIsActivitiListener2() {
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
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class); when empty string")
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
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals("", root.getTextContent());
    assertEquals("", lastChild.getTextContent());
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExpandedSubProcess(String, String, GraphicInfo, Class)}
   */
  @Test
  @DisplayName("Test drawExpandedSubProcess(String, String, GraphicInfo, Class); when empty string")
  void testDrawExpandedSubProcess_whenEmptyString2() throws DOMException {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)")
  void testDrawCollapsedSubProcess2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor); when 'Name'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor); when 'Name'")
  void testDrawCollapsedSubProcess_givenGraphicInfoElementIsActivitiListener_whenName2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean); when empty string")
  void testDrawCollapsedSubProcess_whenEmptyString2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean); when 'Name'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <ul>
   *   <li>When {@code Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean); when 'Name'")
  void testDrawCollapsedSubProcess_whenName2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedSubProcess(String, String, GraphicInfo, Boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedSubProcess(String, String, GraphicInfo, Boolean); when 'null'")
  void testDrawCollapsedSubProcess_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo)")
  void testDrawCollapsedCallActivity5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo)")
  void testDrawCollapsedCallActivity6() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo)")
  void testDrawCollapsedCallActivity7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo)")
  void testDrawCollapsedCallActivity8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo); when empty string")
  void testDrawCollapsedCallActivity_whenEmptyString2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedCallActivity(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawCollapsedCallActivity(String, String, GraphicInfo); when 'null'")
  void testDrawCollapsedCallActivity_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean)")
  void testDrawCollapsedTask5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean)")
  void testDrawCollapsedTask6() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean)")
  void testDrawCollapsedTask7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean)")
  void testDrawCollapsedTask8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean); when empty string")
  void testDrawCollapsedTask_whenEmptyString2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean); when 'false'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean); when 'false'")
  void testDrawCollapsedTask_whenFalse2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedTask(String, String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawCollapsedTask(String, String, GraphicInfo, boolean); when 'null'")
  void testDrawCollapsedTask_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedMarker(int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedMarker(int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawCollapsedMarker(int, int, int, int)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawCollapsedMarker(int, int, int, int)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawCollapsedMarker(int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawCollapsedMarker(int, int, int, int)")
  void testDrawCollapsedMarker2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
  void testDrawActivityMarkers7() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
  void testDrawActivityMarkers8() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
  void testDrawActivityMarkers9() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
  void testDrawActivityMarkers10() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
  void testDrawActivityMarkers11() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)}
   */
  @Test
  @DisplayName("Test drawActivityMarkers(int, int, int, int, boolean, boolean, boolean)")
  void testDrawActivityMarkers12() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGateway(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGateway(GraphicInfo)")
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGateway(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGateway(GraphicInfo)")
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
   * Test {@link DefaultProcessDiagramCanvas#drawGateway(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGateway(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGateway(GraphicInfo)")
  void testDrawGateway3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGateway(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGateway(GraphicInfo)")
  void testDrawGateway4() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLight(GraphicInfo, Color)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLight(GraphicInfo, Color)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLight(GraphicInfo, Color)")
  void testDrawGatewayHighLight() throws NumberFormatException {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLight(GraphicInfo, Color)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLight(GraphicInfo, Color)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLight(GraphicInfo, Color)")
  void testDrawGatewayHighLight2() throws NumberFormatException {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLight(GraphicInfo, Color)}.
   * <ul>
   *   <li>When decode {@code 42}.</li>
   *   <li>Then calls {@link GraphicInfo#getHeight()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLight(GraphicInfo, Color)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLight(GraphicInfo, Color); when decode '42'; then calls getHeight()")
  void testDrawGatewayHighLight_whenDecode42_thenCallsGetHeight() throws NumberFormatException {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLight(GraphicInfo, Color)}.
   * <ul>
   *   <li>When decode {@code 42}.</li>
   *   <li>Then calls {@link GraphicInfo#getHeight()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLight(GraphicInfo, Color)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLight(GraphicInfo, Color); when decode '42'; then calls getHeight()")
  void testDrawGatewayHighLight_whenDecode42_thenCallsGetHeight2() throws NumberFormatException {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLightCompleted(GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLightCompleted(GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLightCompleted(GraphicInfo)")
  void testDrawGatewayHighLightCompleted3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLightCompleted(GraphicInfo)")
  void testDrawGatewayHighLightCompleted4() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLightErrored(GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLightErrored(GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLightErrored(GraphicInfo)")
  void testDrawGatewayHighLightErrored3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawGatewayHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawGatewayHighLightErrored(GraphicInfo)")
  void testDrawGatewayHighLightErrored4() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawParallelGateway(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawParallelGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawParallelGateway(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawParallelGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawParallelGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawParallelGateway(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawExclusiveGateway(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawExclusiveGateway(String, GraphicInfo); given ActivitiListener (default constructor)")
  void testDrawExclusiveGateway_givenActivitiListener() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) XmlColumnNumber is
   * ten.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawExclusiveGateway(String, GraphicInfo); given ActivitiListener (default constructor) XmlColumnNumber is ten")
  void testDrawExclusiveGateway_givenActivitiListenerXmlColumnNumberIsTen() {
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

    ActivitiListener element = new ActivitiListener();
    element.setXmlColumnNumber(10);

    GraphicInfo graphicInfo2 = new GraphicInfo();
    graphicInfo2.setElement(element);
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawExclusiveGateway(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawExclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawExclusiveGateway(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  void testDrawExclusiveGateway_givenGraphicInfoElementIsActivitiListener2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawInclusiveGateway(String, GraphicInfo); given ActivitiListener (default constructor)")
  void testDrawInclusiveGateway_givenActivitiListener() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor) ExtensionElements is
   * {@link HashMap#HashMap()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawInclusiveGateway(String, GraphicInfo); given ActivitiListener (default constructor) ExtensionElements is HashMap()")
  void testDrawInclusiveGateway_givenActivitiListenerExtensionElementsIsHashMap() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    ActivitiListener element = new ActivitiListener();
    element.setExtensionElements(new HashMap<>());
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawInclusiveGateway(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawInclusiveGateway(String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor)")
  void testDrawInclusiveGateway_givenGraphicInfoElementIsActivitiListener2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code id} and
   * {@link BiFunction}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawInclusiveGateway(String, GraphicInfo); given HashMap() computeIfPresent 'id' and BiFunction")
  void testDrawInclusiveGateway_givenHashMapComputeIfPresentIdAndBiFunction() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    HashMap<String, List<ExtensionElement>> extensionElements = new HashMap<>();
    extensionElements.computeIfPresent("id", mock(BiFunction.class));

    ActivitiListener element = new ActivitiListener();
    element.setExtensionElements(extensionElements);
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given zero.</li>
   *   <li>When {@link GraphicInfo} (default constructor) XmlRowNumber is zero.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawInclusiveGateway(String, GraphicInfo); given zero; when GraphicInfo (default constructor) XmlRowNumber is zero")
  void testDrawInclusiveGateway_givenZero_whenGraphicInfoXmlRowNumberIsZero() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new ActivitiListener());
    graphicInfo.setExpanded(true);
    graphicInfo.setHeight(10.0d);
    graphicInfo.setWidth(10.0d);
    graphicInfo.setX(2.0d);
    graphicInfo.setXmlColumnNumber(10);
    graphicInfo.setXmlRowNumber(0);
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>When {@link GraphicInfo} (default constructor) XmlRowNumber is ten.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawInclusiveGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawInclusiveGateway(String, GraphicInfo); when GraphicInfo (default constructor) XmlRowNumber is ten")
  void testDrawInclusiveGateway_whenGraphicInfoXmlRowNumberIsTen() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventBasedGateway(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventBasedGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventBasedGateway(String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventBasedGateway(String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventBasedGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventBasedGateway(String, GraphicInfo)")
  void testDrawEventBasedGateway2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    ActivitiListener element = new ActivitiListener();
    element.addAttribute(new ExtensionAttribute("42Name"));

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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventBasedGateway(String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link ActivitiListener} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventBasedGateway(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventBasedGateway(String, GraphicInfo); given ActivitiListener (default constructor)")
  void testDrawEventBasedGateway_givenActivitiListener() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultiInstanceMarker(boolean, int, int, int, int); when 'false'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}.
   * <ul>
   *   <li>When {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultiInstanceMarker(boolean, int, int, int, int); when 'false'")
  void testDrawMultiInstanceMarker_whenFalse2() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}.
   * <ul>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultiInstanceMarker(boolean, int, int, int, int); when 'true'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}.
   * <ul>
   *   <li>When {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawMultiInstanceMarker(boolean, int, int, int, int)}
   */
  @Test
  @DisplayName("Test drawMultiInstanceMarker(boolean, int, int, int, int); when 'true'")
  void testDrawMultiInstanceMarker_whenTrue2() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCurrent(GraphicInfo)")
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCurrent(GraphicInfo)")
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
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCurrent(GraphicInfo)")
  void testDrawHighLightCurrent3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCurrent(GraphicInfo)")
  void testDrawHighLightCurrent4() {
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
   *   <li>Given {@code null}.</li>
   *   <li>When {@link GraphicInfo} (default constructor) Element is
   * {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCurrent(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCurrent(GraphicInfo); given 'null'; when GraphicInfo (default constructor) Element is 'null'")
  void testDrawHighLightCurrent_givenNull_whenGraphicInfoElementIsNull() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(null);
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCompleted(GraphicInfo)")
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCompleted(GraphicInfo)")
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
   * Test {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCompleted(GraphicInfo)")
  void testDrawHighLightCompleted3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightCompleted(GraphicInfo)")
  void testDrawHighLightCompleted4() {
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightErrored(GraphicInfo)")
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightErrored(GraphicInfo)")
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
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightErrored(GraphicInfo)")
  void testDrawHighLightErrored3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightErrored(GraphicInfo)")
  void testDrawHighLightErrored4() {
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
   *   <li>Given {@link CancelEventDefinition} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawHighLightErrored(GraphicInfo); given CancelEventDefinition (default constructor)")
  void testDrawHighLightErrored_givenCancelEventDefinition() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(-5, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
    graphicInfo.setElement(new CancelEventDefinition());
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
   * Test {@link DefaultProcessDiagramCanvas#drawHighLight(GraphicInfo, Color)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLight(GraphicInfo, Color)}
   */
  @Test
  @DisplayName("Test drawHighLight(GraphicInfo, Color)")
  void testDrawHighLight() throws NumberFormatException {
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
   * Test {@link DefaultProcessDiagramCanvas#drawHighLight(GraphicInfo, Color)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLight(GraphicInfo, Color)}
   */
  @Test
  @DisplayName("Test drawHighLight(GraphicInfo, Color)")
  void testDrawHighLight2() throws NumberFormatException {
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
   * Test {@link DefaultProcessDiagramCanvas#drawHighLight(GraphicInfo, Color)}.
   * <ul>
   *   <li>When decode {@code 42}.</li>
   *   <li>Then calls {@link GraphicInfo#getHeight()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLight(GraphicInfo, Color)}
   */
  @Test
  @DisplayName("Test drawHighLight(GraphicInfo, Color); when decode '42'; then calls getHeight()")
  void testDrawHighLight_whenDecode42_thenCallsGetHeight() throws NumberFormatException {
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
   * Test {@link DefaultProcessDiagramCanvas#drawHighLight(GraphicInfo, Color)}.
   * <ul>
   *   <li>When decode {@code 42}.</li>
   *   <li>Then calls {@link GraphicInfo#getHeight()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawHighLight(GraphicInfo, Color)}
   */
  @Test
  @DisplayName("Test drawHighLight(GraphicInfo, Color); when decode '42'; then calls getHeight()")
  void testDrawHighLight_whenDecode42_thenCallsGetHeight2() throws NumberFormatException {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventHighLight(GraphicInfo, Color)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLight(GraphicInfo, Color)}
   */
  @Test
  @DisplayName("Test drawEventHighLight(GraphicInfo, Color)")
  void testDrawEventHighLight() throws NumberFormatException {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventHighLight(GraphicInfo, Color)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLight(GraphicInfo, Color)}
   */
  @Test
  @DisplayName("Test drawEventHighLight(GraphicInfo, Color)")
  void testDrawEventHighLight2() throws NumberFormatException {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventHighLight(GraphicInfo, Color)}.
   * <ul>
   *   <li>When decode {@code 42}.</li>
   *   <li>Then calls {@link GraphicInfo#getHeight()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLight(GraphicInfo, Color)}
   */
  @Test
  @DisplayName("Test drawEventHighLight(GraphicInfo, Color); when decode '42'; then calls getHeight()")
  void testDrawEventHighLight_whenDecode42_thenCallsGetHeight() throws NumberFormatException {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventHighLight(GraphicInfo, Color)}.
   * <ul>
   *   <li>When decode {@code 42}.</li>
   *   <li>Then calls {@link GraphicInfo#getHeight()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLight(GraphicInfo, Color)}
   */
  @Test
  @DisplayName("Test drawEventHighLight(GraphicInfo, Color); when decode '42'; then calls getHeight()")
  void testDrawEventHighLight_whenDecode42_thenCallsGetHeight2() throws NumberFormatException {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightCompleted(GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightCompleted(GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightCompleted(GraphicInfo)")
  void testDrawEventHighLightCompleted3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightCompleted(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightCompleted(GraphicInfo)")
  void testDrawEventHighLightCompleted4() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightErrored(GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightErrored(GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightErrored(GraphicInfo)")
  void testDrawEventHighLightErrored3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawEventHighLightErrored(GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawEventHighLightErrored(GraphicInfo)")
  void testDrawEventHighLightErrored4() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTextAnnotation(String, String, GraphicInfo)")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Element is
   * {@link ActivitiListener} (default constructor).</li>
   *   <li>When {@code Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTextAnnotation(String, String, GraphicInfo); given GraphicInfo (default constructor) Element is ActivitiListener (default constructor); when 'Text'")
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

    // Assert
    Element root = defaultProcessDiagramCanvas.g.getRoot();
    assertTrue(root instanceof GenericElementNS);
    Node lastChild = root.getLastChild();
    assertTrue(lastChild instanceof GenericElementNS);
    assertTrue(lastChild.getLastChild() instanceof GenericElementNS);
    assertEquals(2, ((GenericElementNS) lastChild).getChildElementCount());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTextAnnotation(String, String, GraphicInfo); when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTextAnnotation(String, String, GraphicInfo); when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}.
   * <ul>
   *   <li>When {@code Text}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawTextAnnotation(String, String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawTextAnnotation(String, String, GraphicInfo); when 'Text'")
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
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)} with
   * {@code text}, {@code graphicInfo}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo) with 'text', 'graphicInfo'")
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
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)} with
   * {@code text}, {@code graphicInfo}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo) with 'text', 'graphicInfo'")
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
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)} with
   * {@code text}, {@code graphicInfo}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo) with 'text', 'graphicInfo'")
  void testDrawLabelWithTextGraphicInfo3() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)} with
   * {@code text}, {@code graphicInfo}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo) with 'text', 'graphicInfo'")
  void testDrawLabelWithTextGraphicInfo4() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   * with {@code text}, {@code graphicInfo}, {@code centered}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   * with {@code text}, {@code graphicInfo}, {@code centered}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   * with {@code text}, {@code graphicInfo}, {@code centered}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   * with {@code text}, {@code graphicInfo}, {@code centered}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'")
  void testDrawLabelWithTextGraphicInfoCentered4() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   * with {@code text}, {@code graphicInfo}, {@code centered}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'")
  void testDrawLabelWithTextGraphicInfoCentered5() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   * with {@code text}, {@code graphicInfo}, {@code centered}.
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'")
  void testDrawLabelWithTextGraphicInfoCentered6() {
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   * with {@code text}, {@code graphicInfo}, {@code centered}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'; when empty string")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   * with {@code text}, {@code graphicInfo}, {@code centered}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'; when empty string")
  void testDrawLabelWithTextGraphicInfoCentered_whenEmptyString2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   * with {@code text}, {@code graphicInfo}, {@code centered}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'; when 'null'")
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
   * Test
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   * with {@code text}, {@code graphicInfo}, {@code centered}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo, boolean)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo, boolean) with 'text', 'graphicInfo', 'centered'; when 'null'")
  void testDrawLabelWithTextGraphicInfoCentered_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)} with
   * {@code text}, {@code graphicInfo}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo) with 'text', 'graphicInfo'; when empty string")
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
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)} with
   * {@code text}, {@code graphicInfo}.
   * <ul>
   *   <li>When empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo) with 'text', 'graphicInfo'; when empty string")
  void testDrawLabelWithTextGraphicInfo_whenEmptyString2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)} with
   * {@code text}, {@code graphicInfo}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo) with 'text', 'graphicInfo'; when 'null'")
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
   * Test {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)} with
   * {@code text}, {@code graphicInfo}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#drawLabel(String, GraphicInfo)}
   */
  @Test
  @DisplayName("Test drawLabel(String, GraphicInfo) with 'text', 'graphicInfo'; when 'null'")
  void testDrawLabelWithTextGraphicInfo_whenNull2() {
    // Arrange
    DefaultProcessDiagramCanvas defaultProcessDiagramCanvas = new DefaultProcessDiagramCanvas(1, 1, 1, 1);

    GraphicInfo graphicInfo = new GraphicInfo();
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
   * Test
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Height is
   * {@code 0.5}.</li>
   *   <li>Then return first Y is ten.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); given GraphicInfo (default constructor) Height is '0.5'; then return first Y is ten")
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
        DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, graphicInfoList);

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
   * Test
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) Height is
   * {@code 0.5}.</li>
   *   <li>Then return first Y is ten.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); given GraphicInfo (default constructor) Height is '0.5'; then return first Y is ten")
  void testConnectionPerfectionizer_givenGraphicInfoHeightIs05_thenReturnFirstYIsTen2() {
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
        DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, graphicInfoList);

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
   * Test
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) X is ten.</li>
   *   <li>Then return first X is seven.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); given GraphicInfo (default constructor) X is ten; then return first X is seven")
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
        DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
        sourceGraphicInfo, targetGraphicInfo, graphicInfoList);

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
   * Test
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>Given {@link GraphicInfo} (default constructor) X is ten.</li>
   *   <li>Then return first X is seven.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); given GraphicInfo (default constructor) X is ten; then return first X is seven")
  void testConnectionPerfectionizer_givenGraphicInfoXIsTen_thenReturnFirstXIsSeven2() {
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
        DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
        sourceGraphicInfo, targetGraphicInfo, graphicInfoList);

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
   * Test
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); when ArrayList(); then return Empty")
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
        .connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
            DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, new ArrayList<>())
        .isEmpty());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); when ArrayList(); then return Empty")
  void testConnectionPerfectionizer_whenArrayList_thenReturnEmpty2() {
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
        .connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle,
            DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, new ArrayList<>())
        .isEmpty());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>When {@code Ellipse}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); when 'Ellipse'; then return Empty")
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
        .connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE.Ellipse,
            DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, new ArrayList<>())
        .isEmpty());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>When {@code Ellipse}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); when 'Ellipse'; then return Empty")
  void testConnectionPerfectionizer_whenEllipse_thenReturnEmpty2() {
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
        .connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE.Ellipse,
            DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, new ArrayList<>())
        .isEmpty());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); when 'null'; then return Empty")
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
        .connectionPerfectionizer(null, DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo,
            targetGraphicInfo, new ArrayList<>())
        .isEmpty());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); when 'null'; then return Empty")
  void testConnectionPerfectionizer_whenNull_thenReturnEmpty2() {
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
        .connectionPerfectionizer(null, DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo,
            targetGraphicInfo, new ArrayList<>())
        .isEmpty());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>When {@code Rhombus}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); when 'Rhombus'; then return Empty")
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
        .connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rhombus,
            DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, new ArrayList<>())
        .isEmpty());
  }

  /**
   * Test
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}.
   * <ul>
   *   <li>When {@code Rhombus}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DefaultProcessDiagramCanvas#connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE, DefaultProcessDiagramCanvas.SHAPE_TYPE, GraphicInfo, GraphicInfo, List)}
   */
  @Test
  @DisplayName("Test connectionPerfectionizer(SHAPE_TYPE, SHAPE_TYPE, GraphicInfo, GraphicInfo, List); when 'Rhombus'; then return Empty")
  void testConnectionPerfectionizer_whenRhombus_thenReturnEmpty2() {
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
        .connectionPerfectionizer(DefaultProcessDiagramCanvas.SHAPE_TYPE.Rhombus,
            DefaultProcessDiagramCanvas.SHAPE_TYPE.Rectangle, sourceGraphicInfo, targetGraphicInfo, new ArrayList<>())
        .isEmpty());
  }
}
