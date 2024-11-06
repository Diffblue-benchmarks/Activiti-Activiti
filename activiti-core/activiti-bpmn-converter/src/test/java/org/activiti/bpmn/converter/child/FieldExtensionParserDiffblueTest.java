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
import org.activiti.bpmn.model.MessageEventDefinition;
import org.activiti.bpmn.model.SendTask;
import org.activiti.bpmn.model.ServiceTask;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FieldExtensionParserDiffblueTest {
  /**
   * Test {@link FieldExtensionParser#accepts(BaseElement)}.
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FieldExtensionParser#accepts(BaseElement)}
   */
  @Test
  @DisplayName("Test accepts(BaseElement); when ActivitiListener (default constructor); then return 'true'")
  void testAccepts_whenActivitiListener_thenReturnTrue() {
    // Arrange
    FieldExtensionParser fieldExtensionParser = new FieldExtensionParser();

    // Act and Assert
    assertTrue(fieldExtensionParser.accepts(new ActivitiListener()));
  }

  /**
   * Test {@link FieldExtensionParser#accepts(BaseElement)}.
   * <ul>
   *   <li>When {@link MessageEventDefinition} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FieldExtensionParser#accepts(BaseElement)}
   */
  @Test
  @DisplayName("Test accepts(BaseElement); when MessageEventDefinition (default constructor); then return 'true'")
  void testAccepts_whenMessageEventDefinition_thenReturnTrue() {
    // Arrange
    FieldExtensionParser fieldExtensionParser = new FieldExtensionParser();

    // Act and Assert
    assertTrue(fieldExtensionParser.accepts(new MessageEventDefinition()));
  }

  /**
   * Test {@link FieldExtensionParser#accepts(BaseElement)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FieldExtensionParser#accepts(BaseElement)}
   */
  @Test
  @DisplayName("Test accepts(BaseElement); when 'null'; then return 'false'")
  void testAccepts_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new FieldExtensionParser()).accepts(null));
  }

  /**
   * Test {@link FieldExtensionParser#accepts(BaseElement)}.
   * <ul>
   *   <li>When {@link SendTask} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FieldExtensionParser#accepts(BaseElement)}
   */
  @Test
  @DisplayName("Test accepts(BaseElement); when SendTask (default constructor); then return 'true'")
  void testAccepts_whenSendTask_thenReturnTrue() {
    // Arrange
    FieldExtensionParser fieldExtensionParser = new FieldExtensionParser();

    // Act and Assert
    assertTrue(fieldExtensionParser.accepts(new SendTask()));
  }

  /**
   * Test {@link FieldExtensionParser#accepts(BaseElement)}.
   * <ul>
   *   <li>When {@link ServiceTask} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link FieldExtensionParser#accepts(BaseElement)}
   */
  @Test
  @DisplayName("Test accepts(BaseElement); when ServiceTask (default constructor); then return 'true'")
  void testAccepts_whenServiceTask_thenReturnTrue() {
    // Arrange
    FieldExtensionParser fieldExtensionParser = new FieldExtensionParser();

    // Act and Assert
    assertTrue(fieldExtensionParser.accepts(new ServiceTask()));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link FieldExtensionParser}
   *   <li>{@link FieldExtensionParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("field", (new FieldExtensionParser()).getElementName());
  }
}
