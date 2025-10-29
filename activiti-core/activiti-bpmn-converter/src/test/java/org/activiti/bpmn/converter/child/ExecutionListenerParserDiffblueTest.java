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
package org.activiti.bpmn.converter.child;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.AdhocSubProcess;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.SequenceFlow;
import org.junit.jupiter.api.Test;

class ExecutionListenerParserDiffblueTest {
  /**
   * Method under test:
   * {@link ExecutionListenerParser#addListenerToParent(ActivitiListener, BaseElement)}
   */
  @Test
  void testAddListenerToParent() {
    // Arrange
    ExecutionListenerParser executionListenerParser = new ExecutionListenerParser();
    ActivitiListener listener = new ActivitiListener();

    // Act
    executionListenerParser.addListenerToParent(listener, new ActivitiListener());

    // Assert that nothing has changed
    assertNull(listener.getEvent());
  }

  /**
   * Method under test:
   * {@link ExecutionListenerParser#addListenerToParent(ActivitiListener, BaseElement)}
   */
  @Test
  void testAddListenerToParent2() {
    // Arrange
    ExecutionListenerParser executionListenerParser = new ExecutionListenerParser();

    ActivitiListener listener = new ActivitiListener();
    listener.setEvent(null);
    SequenceFlow parentElement = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    executionListenerParser.addListenerToParent(listener, parentElement);

    // Assert
    assertEquals("take", listener.getEvent());
    List<ActivitiListener> executionListeners = parentElement.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    assertSame(listener, executionListeners.get(0));
  }

  /**
   * Method under test:
   * {@link ExecutionListenerParser#addListenerToParent(ActivitiListener, BaseElement)}
   */
  @Test
  void testAddListenerToParent3() {
    // Arrange
    ExecutionListenerParser executionListenerParser = new ExecutionListenerParser();

    ActivitiListener listener = new ActivitiListener();
    listener.setEvent("Listener");
    SequenceFlow parentElement = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    executionListenerParser.addListenerToParent(listener, parentElement);

    // Assert
    assertEquals("Listener", listener.getEvent());
    List<ActivitiListener> executionListeners = parentElement.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    assertSame(listener, executionListeners.get(0));
  }

  /**
   * Method under test:
   * {@link ExecutionListenerParser#addListenerToParent(ActivitiListener, BaseElement)}
   */
  @Test
  void testAddListenerToParent4() {
    // Arrange
    ExecutionListenerParser executionListenerParser = new ExecutionListenerParser();
    ActivitiListener listener = new ActivitiListener();
    AdhocSubProcess parentElement = new AdhocSubProcess();

    // Act
    executionListenerParser.addListenerToParent(listener, parentElement);

    // Assert
    assertNull(listener.getEvent());
    List<ActivitiListener> executionListeners = parentElement.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    assertSame(listener, executionListeners.get(0));
  }

  /**
   * Method under test:
   * {@link ExecutionListenerParser#addListenerToParent(ActivitiListener, BaseElement)}
   */
  @Test
  void testAddListenerToParent5() {
    // Arrange
    ExecutionListenerParser executionListenerParser = new ExecutionListenerParser();

    ActivitiListener listener = new ActivitiListener();
    listener.setEvent("");
    SequenceFlow parentElement = new SequenceFlow("Source Ref", "Target Ref");

    // Act
    executionListenerParser.addListenerToParent(listener, parentElement);

    // Assert
    assertEquals("take", listener.getEvent());
    List<ActivitiListener> executionListeners = parentElement.getExecutionListeners();
    assertEquals(1, executionListeners.size());
    assertSame(listener, executionListeners.get(0));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ExecutionListenerParser}
   *   <li>{@link ExecutionListenerParser#getElementName()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("executionListener", (new ExecutionListenerParser()).getElementName());
  }
}
