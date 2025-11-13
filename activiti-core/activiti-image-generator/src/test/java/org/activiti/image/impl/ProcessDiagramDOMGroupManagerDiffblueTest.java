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

import static org.junit.Assert.assertThrows;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.apache.batik.ext.awt.g2d.GraphicContext;
import org.apache.batik.svggen.DOMTreeManager;
import org.apache.batik.svggen.SVGGraphics2DRuntimeException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessDiagramDOMGroupManagerDiffblueTest {
  /**
   * Test {@link ProcessDiagramDOMGroupManager#ProcessDiagramDOMGroupManager(GraphicContext,
   * DOMTreeManager)}.
   *
   * <ul>
   *   <li>Then throw {@link SVGGraphics2DRuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDiagramDOMGroupManager#ProcessDiagramDOMGroupManager(GraphicContext, DOMTreeManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDiagramDOMGroupManager.<init>(GraphicContext, DOMTreeManager)"})
  public void testNewProcessDiagramDOMGroupManager_thenThrowSVGGraphics2DRuntimeException() {
    // Arrange, Act and Assert
    assertThrows(
        SVGGraphics2DRuntimeException.class,
        () -> new ProcessDiagramDOMGroupManager(new GraphicContext(), null));
  }

  /**
   * Test {@link ProcessDiagramDOMGroupManager#ProcessDiagramDOMGroupManager(GraphicContext,
   * DOMTreeManager)}.
   *
   * <ul>
   *   <li>Then throw {@link SVGGraphics2DRuntimeException}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessDiagramDOMGroupManager#ProcessDiagramDOMGroupManager(GraphicContext, DOMTreeManager)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ProcessDiagramDOMGroupManager.<init>(GraphicContext, DOMTreeManager)"})
  public void testNewProcessDiagramDOMGroupManager_thenThrowSVGGraphics2DRuntimeException2() {
    // Arrange, Act and Assert
    assertThrows(
        SVGGraphics2DRuntimeException.class, () -> new ProcessDiagramDOMGroupManager(null, null));
  }
}
