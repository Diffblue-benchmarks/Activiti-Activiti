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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FormPropertyParserDiffblueTest {
  /**
   * Test {@link FormPropertyParser#accepts(BaseElement)}.
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormPropertyParser#accepts(BaseElement)}
   */
  @Test
  @DisplayName("Test accepts(BaseElement); when ActivitiListener (default constructor); then return 'false'")
  void testAccepts_whenActivitiListener_thenReturnFalse() {
    // Arrange
    FormPropertyParser formPropertyParser = new FormPropertyParser();

    // Act and Assert
    assertFalse(formPropertyParser.accepts(new ActivitiListener()));
  }

  /**
   * Test {@link FormPropertyParser#accepts(BaseElement)}.
   * <ul>
   *   <li>When {@link StartEvent} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormPropertyParser#accepts(BaseElement)}
   */
  @Test
  @DisplayName("Test accepts(BaseElement); when StartEvent (default constructor); then return 'true'")
  void testAccepts_whenStartEvent_thenReturnTrue() {
    // Arrange
    FormPropertyParser formPropertyParser = new FormPropertyParser();

    // Act and Assert
    assertTrue(formPropertyParser.accepts(new StartEvent()));
  }

  /**
   * Test {@link FormPropertyParser#accepts(BaseElement)}.
   * <ul>
   *   <li>When {@link UserTask} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FormPropertyParser#accepts(BaseElement)}
   */
  @Test
  @DisplayName("Test accepts(BaseElement); when UserTask (default constructor); then return 'true'")
  void testAccepts_whenUserTask_thenReturnTrue() {
    // Arrange
    FormPropertyParser formPropertyParser = new FormPropertyParser();

    // Act and Assert
    assertTrue(formPropertyParser.accepts(new UserTask()));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link FormPropertyParser}
   *   <li>{@link FormPropertyParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("formProperty", (new FormPropertyParser()).getElementName());
  }
}
