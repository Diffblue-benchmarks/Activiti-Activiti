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
import org.activiti.bpmn.model.BpmnModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IOSpecificationParserDiffblueTest {
  /**
   * Test {@link IOSpecificationParser#parseItemSubjectRef(String, BpmnModel)}.
   * <ul>
   *   <li>Then return {@code null:Hello from the Dreaming Spires}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IOSpecificationParser#parseItemSubjectRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseItemSubjectRef(String, BpmnModel); then return 'null:Hello from the Dreaming Spires'")
  void testParseItemSubjectRef_thenReturnNullHelloFromTheDreamingSpires() {
    // Arrange
    IOSpecificationParser ioSpecificationParser = new IOSpecificationParser();

    // Act and Assert
    assertEquals("null:Hello from the Dreaming Spires",
        ioSpecificationParser.parseItemSubjectRef("Hello from the Dreaming Spires", new BpmnModel()));
  }

  /**
   * Test {@link IOSpecificationParser#parseItemSubjectRef(String, BpmnModel)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link IOSpecificationParser#parseItemSubjectRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseItemSubjectRef(String, BpmnModel); when empty string; then return 'null'")
  void testParseItemSubjectRef_whenEmptyString_thenReturnNull() {
    // Arrange
    IOSpecificationParser ioSpecificationParser = new IOSpecificationParser();

    // Act and Assert
    assertNull(ioSpecificationParser.parseItemSubjectRef("", new BpmnModel()));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link IOSpecificationParser}
   *   <li>{@link IOSpecificationParser#getElementName()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("ioSpecification", (new IOSpecificationParser()).getElementName());
  }
}
