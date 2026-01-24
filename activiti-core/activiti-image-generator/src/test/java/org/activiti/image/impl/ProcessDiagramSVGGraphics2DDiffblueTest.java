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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.apache.batik.anim.dom.SVG12DOMImplementation;
import org.apache.batik.anim.dom.SVG12OMDocument;
import org.apache.batik.anim.dom.SVGOMDefsElement;
import org.apache.batik.anim.dom.SVGOMSVGElement;
import org.apache.batik.anim.dom.XBLEventSupport;
import org.apache.batik.dom.GenericDocumentType;
import org.apache.batik.svggen.DOMTreeManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.events.EventListener;

public class ProcessDiagramSVGGraphics2DDiffblueTest {
  /**
   * Test {@link ProcessDiagramSVGGraphics2D#ProcessDiagramSVGGraphics2D(Document)}.
   *
   * <p>Method under test: {@link ProcessDiagramSVGGraphics2D#ProcessDiagramSVGGraphics2D(Document)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDiagramSVGGraphics2D.<init>(Document)"})
  public void testNewProcessDiagramSVGGraphics2D() {
    // Arrange
    GenericDocumentType dt = new GenericDocumentType("Qualified Name", "42", "42");
    SVG12OMDocument domFactory = new SVG12OMDocument(dt, new SVG12DOMImplementation());

    // Act
    ProcessDiagramSVGGraphics2D actualProcessDiagramSVGGraphics2D =
        new ProcessDiagramSVGGraphics2D(domFactory);

    // Assert
    Document dOMFactory = actualProcessDiagramSVGGraphics2D.getDOMFactory();
    assertTrue(dOMFactory instanceof SVG12OMDocument);
    DOMTreeManager dOMTreeManager = actualProcessDiagramSVGGraphics2D.getDOMTreeManager();
    Element genericDefinitions = dOMTreeManager.getGenericDefinitions();
    assertTrue(genericDefinitions instanceof SVGOMDefsElement);
    Element root = dOMTreeManager.getRoot();
    assertTrue(root instanceof SVGOMSVGElement);
    Element root2 = actualProcessDiagramSVGGraphics2D.getRoot();
    assertTrue(root2 instanceof SVGOMSVGElement);
    assertNull(((SVG12OMDocument) dOMFactory).getEventSupport());
    assertNull(((SVGOMDefsElement) genericDefinitions).getEventSupport());
    assertNull(((SVGOMSVGElement) root).getEventSupport());
    assertNull(((SVGOMSVGElement) root2).getEventSupport());
    assertFalse(((SVG12OMDocument) dOMFactory).getEventsEnabled());
  }

  /**
   * Test {@link ProcessDiagramSVGGraphics2D#ProcessDiagramSVGGraphics2D(Document)}.
   *
   * <p>Method under test: {@link ProcessDiagramSVGGraphics2D#ProcessDiagramSVGGraphics2D(Document)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDiagramSVGGraphics2D.<init>(Document)"})
  public void testNewProcessDiagramSVGGraphics2D2() {
    // Arrange
    GenericDocumentType dt = new GenericDocumentType("Qualified Name", "42", "42");

    SVG12OMDocument domFactory = new SVG12OMDocument(dt, new SVG12DOMImplementation());
    domFactory.addEventListenerNS("Namespace URI", "Type", mock(EventListener.class), true, "42");

    // Act
    ProcessDiagramSVGGraphics2D actualProcessDiagramSVGGraphics2D =
        new ProcessDiagramSVGGraphics2D(domFactory);

    // Assert
    Document dOMFactory = actualProcessDiagramSVGGraphics2D.getDOMFactory();
    assertTrue(dOMFactory instanceof SVG12OMDocument);
    Element root = actualProcessDiagramSVGGraphics2D.getRoot();
    assertTrue(root instanceof SVGOMSVGElement);
    assertTrue(((SVG12OMDocument) dOMFactory).getEventSupport() instanceof XBLEventSupport);
    assertTrue(((SVGOMSVGElement) root).getEventSupport() instanceof XBLEventSupport);
    assertTrue(((SVG12OMDocument) dOMFactory).getEventsEnabled());
  }

  /**
   * Test {@link ProcessDiagramSVGGraphics2D#setRenderingHints(Map)}.
   *
   * <p>Method under test: {@link ProcessDiagramSVGGraphics2D#setRenderingHints(Map)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDiagramSVGGraphics2D.setRenderingHints(Map)"})
  public void testSetRenderingHints() {
    // Arrange
    GenericDocumentType dt = new GenericDocumentType("Qualified Name", "42", "42");
    SVG12OMDocument domFactory = new SVG12OMDocument(dt, new SVG12DOMImplementation());
    ProcessDiagramSVGGraphics2D processDiagramSVGGraphics2D =
        new ProcessDiagramSVGGraphics2D(domFactory);
    HashMap<Object, Object> hints = new HashMap<>();

    // Act
    processDiagramSVGGraphics2D.setRenderingHints((Map) hints);

    // Assert
    assertEquals(hints, processDiagramSVGGraphics2D.getRenderingHints());
  }
}
