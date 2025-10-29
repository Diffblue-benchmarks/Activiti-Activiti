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
import org.junit.jupiter.api.Test;

class FormPropertyParserDiffblueTest {
  /**
   * Method under test: {@link FormPropertyParser#accepts(BaseElement)}
   */
  @Test
  void testAccepts() {
    // Arrange
    FormPropertyParser formPropertyParser = new FormPropertyParser();

    // Act and Assert
    assertFalse(formPropertyParser.accepts(new ActivitiListener()));
  }

  /**
   * Method under test: {@link FormPropertyParser#accepts(BaseElement)}
   */
  @Test
  void testAccepts2() {
    // Arrange
    FormPropertyParser formPropertyParser = new FormPropertyParser();

    // Act and Assert
    assertTrue(formPropertyParser.accepts(new UserTask()));
  }

  /**
   * Method under test: {@link FormPropertyParser#accepts(BaseElement)}
   */
  @Test
  void testAccepts3() {
    // Arrange
    FormPropertyParser formPropertyParser = new FormPropertyParser();

    // Act and Assert
    assertTrue(formPropertyParser.accepts(new StartEvent()));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link FormPropertyParser}
   *   <li>{@link FormPropertyParser#getElementName()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("formProperty", (new FormPropertyParser()).getElementName());
  }
}
