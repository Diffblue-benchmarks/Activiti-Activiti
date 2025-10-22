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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.util.HashMap;
import java.util.Map;
import org.apache.batik.anim.dom.SVG12DOMImplementation;
import org.apache.batik.anim.dom.SVG12OMDocument;
import org.apache.batik.anim.dom.SVGOMGElement;
import org.apache.batik.anim.dom.SVGOMSVGElement;
import org.apache.batik.dom.GenericDocumentType;
import org.apache.batik.svggen.DefaultExtensionHandler;
import org.apache.batik.svggen.ImageHandlerBase64Encoder;
import org.apache.batik.svggen.SimpleImageHandler;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.w3c.dom.Document;

public class ProcessDiagramSVGGraphics2DDiffblueTest {
  /**
   * Test {@link ProcessDiagramSVGGraphics2D#ProcessDiagramSVGGraphics2D(Document)}.
   * <ul>
   *   <li>Then Composite return {@link AlphaComposite}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessDiagramSVGGraphics2D#ProcessDiagramSVGGraphics2D(Document)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessDiagramSVGGraphics2D.<init>(Document)"})
  public void testNewProcessDiagramSVGGraphics2D_thenCompositeReturnAlphaComposite() {
    // Arrange
    GenericDocumentType dt = new GenericDocumentType("Qualified Name", "42", "42");

    SVG12OMDocument domFactory = new SVG12OMDocument(dt, new SVG12DOMImplementation());

    // Act
    ProcessDiagramSVGGraphics2D actualProcessDiagramSVGGraphics2D = new ProcessDiagramSVGGraphics2D(domFactory);

    // Assert
    assertTrue(actualProcessDiagramSVGGraphics2D.getComposite() instanceof AlphaComposite);
    assertTrue(actualProcessDiagramSVGGraphics2D.getStroke() instanceof BasicStroke);
    Document dOMFactory = actualProcessDiagramSVGGraphics2D.getDOMFactory();
    assertTrue(dOMFactory instanceof SVG12OMDocument);
    assertTrue(actualProcessDiagramSVGGraphics2D.getTopLevelGroup() instanceof SVGOMGElement);
    assertTrue(actualProcessDiagramSVGGraphics2D.getRoot() instanceof SVGOMSVGElement);
    assertTrue(actualProcessDiagramSVGGraphics2D.getExtensionHandler() instanceof DefaultExtensionHandler);
    assertTrue(actualProcessDiagramSVGGraphics2D.getImageHandler() instanceof ImageHandlerBase64Encoder);
    assertTrue(actualProcessDiagramSVGGraphics2D.getGenericImageHandler() instanceof SimpleImageHandler);
    assertNull(actualProcessDiagramSVGGraphics2D.getSVGCanvasSize());
    assertNull(actualProcessDiagramSVGGraphics2D.getDeviceConfiguration());
    assertNull(actualProcessDiagramSVGGraphics2D.getClipRect());
    assertNull(actualProcessDiagramSVGGraphics2D.getClipBounds());
    assertNull(actualProcessDiagramSVGGraphics2D.getClip());
    assertEquals(1, actualProcessDiagramSVGGraphics2D.getRenderingHints().size());
    assertTrue(actualProcessDiagramSVGGraphics2D.getDefinitionSet().isEmpty());
    assertSame(domFactory, dOMFactory);
  }

  /**
   * Test {@link ProcessDiagramSVGGraphics2D#setRenderingHints(Map)}.
   * <p>
   * Method under test: {@link ProcessDiagramSVGGraphics2D#setRenderingHints(Map)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ProcessDiagramSVGGraphics2D.setRenderingHints(Map)"})
  public void testSetRenderingHints() {
    // Arrange
    GenericDocumentType dt = new GenericDocumentType("Qualified Name", "42", "42");

    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D = new ProcessDiagramSVGGraphics2D(
        new SVG12OMDocument(dt, new SVG12DOMImplementation()));
    HashMap<Object, Object> hints = new HashMap<>();

    // Act
    processDiagramSVGGraphics2D.setRenderingHints((Map) hints);

    // Assert
    assertEquals(hints, processDiagramSVGGraphics2D.getRenderingHints());
  }
}
