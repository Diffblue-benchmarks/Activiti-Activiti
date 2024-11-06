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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BaseChildElementParserDiffblueTest {
  /**
   * Test {@link BaseChildElementParser#accepts(BaseElement)}.
   * <ul>
   *   <li>When {@link ActivitiListener} (default constructor).</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseChildElementParser#accepts(BaseElement)}
   */
  @Test
  @DisplayName("Test accepts(BaseElement); when ActivitiListener (default constructor); then return 'true'")
  void testAccepts_whenActivitiListener_thenReturnTrue() {
    // Arrange
    ActivitiEventListenerParser activitiEventListenerParser = new ActivitiEventListenerParser();

    // Act and Assert
    assertTrue(activitiEventListenerParser.accepts(new ActivitiListener()));
  }

  /**
   * Test {@link BaseChildElementParser#accepts(BaseElement)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BaseChildElementParser#accepts(BaseElement)}
   */
  @Test
  @DisplayName("Test accepts(BaseElement); when 'null'; then return 'false'")
  void testAccepts_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ActivitiEventListenerParser()).accepts(null));
  }
}
