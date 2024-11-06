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
import static org.junit.jupiter.api.Assertions.assertSame;
import java.util.List;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.UserTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TaskListenerParserDiffblueTest {
  /**
   * Test
   * {@link TaskListenerParser#addListenerToParent(ActivitiListener, BaseElement)}.
   * <ul>
   *   <li>When {@link UserTask} (default constructor).</li>
   *   <li>Then {@link UserTask} (default constructor) TaskListeners size is
   * one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskListenerParser#addListenerToParent(ActivitiListener, BaseElement)}
   */
  @Test
  @DisplayName("Test addListenerToParent(ActivitiListener, BaseElement); when UserTask (default constructor); then UserTask (default constructor) TaskListeners size is one")
  void testAddListenerToParent_whenUserTask_thenUserTaskTaskListenersSizeIsOne() {
    // Arrange
    TaskListenerParser taskListenerParser = new TaskListenerParser();
    ActivitiListener listener = new ActivitiListener();
    UserTask parentElement = new UserTask();

    // Act
    taskListenerParser.addListenerToParent(listener, parentElement);

    // Assert
    List<ActivitiListener> taskListeners = parentElement.getTaskListeners();
    assertEquals(1, taskListeners.size());
    assertSame(listener, taskListeners.get(0));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link TaskListenerParser}
   *   <li>{@link TaskListenerParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("taskListener", (new TaskListenerParser()).getElementName());
  }
}
