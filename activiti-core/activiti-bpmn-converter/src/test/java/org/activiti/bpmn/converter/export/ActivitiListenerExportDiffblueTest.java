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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamWriter;
import org.activiti.bpmn.converter.IndentingXMLStreamWriter;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.EventListener;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.UserTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ActivitiListenerExportDiffblueTest {
  /**
   * Test {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)} with
   * {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   *
   * <p>Method under test: {@link ActivitiListenerExport#writeListeners(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeListeners(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ActivitiListenerExport.writeListeners(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteListenersWithElementDidWriteExtensionStartElementXtw() throws Exception {
    // Arrange
    Process element = new Process();
    element.setEventListeners(null);

    // Act and Assert
    assertFalse(
        ActivitiListenerExport.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)} with
   * {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   *
   * <p>Method under test: {@link ActivitiListenerExport#writeListeners(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeListeners(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ActivitiListenerExport.writeListeners(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteListenersWithElementDidWriteExtensionStartElementXtw2() throws Exception {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setEvent("");
    activitiListener.setImplementation("not empty");
    activitiListener.setOnTransaction("not empty");
    activitiListener.setCustomPropertiesResolverImplementation("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(activitiListener);

    UserTask element = new UserTask();
    element.setTaskListeners(taskListeners);

    // Act and Assert
    assertFalse(
        ActivitiListenerExport.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)} with
   * {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   *
   * <p>Method under test: {@link ActivitiListenerExport#writeListeners(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeListeners(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ActivitiListenerExport.writeListeners(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteListenersWithElementDidWriteExtensionStartElementXtw3() throws Exception {
    // Arrange
    UserTask element = new UserTask();
    element.setTaskListeners(null);

    // Act and Assert
    assertFalse(
        ActivitiListenerExport.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)} with
   * {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   *
   * <p>Method under test: {@link ActivitiListenerExport#writeListeners(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeListeners(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ActivitiListenerExport.writeListeners(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteListenersWithElementDidWriteExtensionStartElementXtw4() throws Exception {
    // Arrange
    ActivitiListener activitiListener = new ActivitiListener();
    activitiListener.setEvent("");
    activitiListener.setImplementation("not empty");
    activitiListener.setOnTransaction("not empty");
    activitiListener.setCustomPropertiesResolverImplementation("not empty");

    ArrayList<ActivitiListener> taskListeners = new ArrayList<>();
    taskListeners.add(new ActivitiListener());
    taskListeners.add(activitiListener);

    UserTask element = new UserTask();
    element.setTaskListeners(taskListeners);

    // Act and Assert
    assertFalse(
        ActivitiListenerExport.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)} with
   * {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiListenerExport#writeListeners(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeListeners(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'; given ArrayList()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ActivitiListenerExport.writeListeners(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteListenersWithElementDidWriteExtensionStartElementXtw_givenArrayList()
      throws Exception {
    // Arrange
    Process element = new Process();
    element.setEventListeners(new ArrayList<>());

    // Act and Assert
    assertFalse(
        ActivitiListenerExport.writeListeners(element, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link ActivitiListenerExport#writeListeners(BaseElement, boolean, XMLStreamWriter)} with
   * {@code element}, {@code didWriteExtensionStartElement}, {@code xtw}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiListenerExport#writeListeners(BaseElement, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeListeners(BaseElement, boolean, XMLStreamWriter) with 'element', 'didWriteExtensionStartElement', 'xtw'; then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ActivitiListenerExport.writeListeners(BaseElement, boolean, XMLStreamWriter)"
  })
  void testWriteListenersWithElementDidWriteExtensionStartElementXtw_thenReturnTrue()
      throws Exception {
    // Arrange
    ActivitiListener element = new ActivitiListener();

    // Act and Assert
    assertTrue(
        ActivitiListenerExport.writeListeners(element, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link ActivitiListenerExport#writeEventListeners(List, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiListenerExport#writeEventListeners(List, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventListeners(List, boolean, XMLStreamWriter); when ArrayList(); then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ActivitiListenerExport.writeEventListeners(List, boolean, XMLStreamWriter)"
  })
  void testWriteEventListeners_whenArrayList_thenReturnFalse() throws Exception {
    // Arrange
    ArrayList<EventListener> eventListeners = new ArrayList<>();

    // Act and Assert
    assertFalse(
        ActivitiListenerExport.writeEventListeners(
            eventListeners, false, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link ActivitiListenerExport#writeEventListeners(List, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiListenerExport#writeEventListeners(List, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventListeners(List, boolean, XMLStreamWriter); when ArrayList(); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ActivitiListenerExport.writeEventListeners(List, boolean, XMLStreamWriter)"
  })
  void testWriteEventListeners_whenArrayList_thenReturnTrue() throws Exception {
    // Arrange
    ArrayList<EventListener> eventListeners = new ArrayList<>();

    // Act and Assert
    assertTrue(
        ActivitiListenerExport.writeEventListeners(
            eventListeners, true, new IndentingXMLStreamWriter(null)));
  }

  /**
   * Test {@link ActivitiListenerExport#writeEventListeners(List, boolean, XMLStreamWriter)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiListenerExport#writeEventListeners(List, boolean,
   * XMLStreamWriter)}
   */
  @Test
  @DisplayName(
      "Test writeEventListeners(List, boolean, XMLStreamWriter); when 'null'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean ActivitiListenerExport.writeEventListeners(List, boolean, XMLStreamWriter)"
  })
  void testWriteEventListeners_whenNull_thenReturnFalse() throws Exception {
    // Arrange, Act and Assert
    assertFalse(
        ActivitiListenerExport.writeEventListeners(
            null, false, new IndentingXMLStreamWriter(null)));
  }
}
