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
import org.junit.jupiter.api.Test;

class ActivitiListenerExportDiffblueTest {
  /**
   * Method under test:
   * {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteListeners() throws Exception {
    // Arrange
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertTrue(ActivitiListenerExport.writeListeners(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteListeners2() throws Exception {
    // Arrange
    AdhocSubProcess element = new AdhocSubProcess();

    // Act and Assert
    assertTrue(ActivitiListenerExport.writeListeners(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteListeners3() throws Exception {
    // Arrange
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertFalse(ActivitiListenerExport.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link ActivitiListenerExport#writeEventListeners(List, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteEventListeners() throws Exception {
    // Arrange
    ArrayList<EventListener> eventListeners = new ArrayList<>();

    // Act and Assert
    assertTrue(ActivitiListenerExport.writeEventListeners(eventListeners, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Method under test:
   * {@link ActivitiListenerExport#writeEventListeners(List, boolean, XMLStreamWriter)}
   */
  @Test
  void testWriteEventListeners2() throws Exception {
    // Arrange
    ArrayList<EventListener> eventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse(ActivitiListenerExport.writeEventListeners(eventListeners, false, new IndentingXMLStreamWriter(null)));
  }
}
