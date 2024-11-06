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
package org.activiti.bpmn.converter.export;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.EventListener;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ActivitiListenerExportDiffblueTest {
  /**
   * Test
   * {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   * with {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   * <p>
   * Method under test:
   * {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeListeners(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'")
  void testWriteListenersWithElementDidWriteExtensionStartElementXtw() throws Exception {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();

    // Act and Assert
    assertTrue(ActivitiListenerExport.writeListeners(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   * with {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeListeners(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'; then return 'false'")
  void testWriteListenersWithElementDidWriteExtensionStartElementXtw_thenReturnFalse() throws Exception {
    // Arrange
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertFalse(ActivitiListenerExport.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   * with {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeListeners(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'; then return 'true'")
  void testWriteListenersWithElementDidWriteExtensionStartElementXtw_thenReturnTrue() throws Exception {
    // Arrange
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertTrue(ActivitiListenerExport.writeListeners(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link ActivitiListenerExport#writeEventListeners(List, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiListenerExport#writeEventListeners(List, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeEventListeners(List, boolean, XMLStreamWriter); when ArrayList(); then return 'false'")
  void testWriteEventListeners_whenArrayList_thenReturnFalse() throws Exception {
    // Arrange
    ArrayList<EventListener> eventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse(ActivitiListenerExport.writeEventListeners(eventListeners, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test
   * {@link ActivitiListenerExport#writeEventListeners(List, boolean, XMLStreamWriter)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiListenerExport#writeEventListeners(List, boolean, XMLStreamWriter)}
   */
  @Test
  @DisplayName("Test writeEventListeners(List, boolean, XMLStreamWriter); when ArrayList(); then return 'true'")
  void testWriteEventListeners_whenArrayList_thenReturnTrue() throws Exception {
    // Arrange
    ArrayList<EventListener> eventListeners = new ArrayList<>();

    // Act and Assert
    assertTrue(ActivitiListenerExport.writeEventListeners(eventListeners, true, new IndentingXMLStreamWriter(null)));
  }
}
