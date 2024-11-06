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
package org.activiti.bpmn.converter.parser;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BpmnEdgeParserDiffblueTest {
  /**
   * Test {@link BpmnEdgeParser#parseElement()}.
   * <p>
   * Method under test: {@link BpmnEdgeParser#parseElement()}
   */
  @Test
  @DisplayName("Test parseElement()")
  void testParseElement() {
    // Arrange, Act and Assert
    assertNull((new BpmnEdgeParser()).parseElement());
  }
}
