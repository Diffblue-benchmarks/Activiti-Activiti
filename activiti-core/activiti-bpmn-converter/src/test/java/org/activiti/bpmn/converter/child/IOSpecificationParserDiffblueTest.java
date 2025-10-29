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
import org.junit.jupiter.api.Test;

class IOSpecificationParserDiffblueTest {
  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link IOSpecificationParser}
   *   <li>{@link IOSpecificationParser#getElementName()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("ioSpecification", (new IOSpecificationParser()).getElementName());
  }

  /**
   * Method under test:
   * {@link IOSpecificationParser#parseItemSubjectRef(String, BpmnModel)}
   */
  @Test
  void testParseItemSubjectRef() {
    // Arrange
    IOSpecificationParser ioSpecificationParser = new IOSpecificationParser();

    // Act and Assert
    assertEquals("null:Hello from the Dreaming Spires",
        ioSpecificationParser.parseItemSubjectRef("Hello from the Dreaming Spires", new BpmnModel()));
  }

  /**
   * Method under test:
   * {@link IOSpecificationParser#parseItemSubjectRef(String, BpmnModel)}
   */
  @Test
  void testParseItemSubjectRef2() {
    // Arrange
    IOSpecificationParser ioSpecificationParser = new IOSpecificationParser();

    // Act and Assert
    assertNull(ioSpecificationParser.parseItemSubjectRef("", new BpmnModel()));
  }
}
