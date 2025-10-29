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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.activiti.bpmn.model.BpmnModel;
import org.junit.jupiter.api.Test;

class InterfaceParserDiffblueTest {
  /**
   * Method under test: {@link InterfaceParser#parseMessageRef(String, BpmnModel)}
   */
  @Test
  void testParseMessageRef() {
    // Arrange
    InterfaceParser interfaceParser = new InterfaceParser();

    // Act and Assert
    assertEquals("Message Ref", interfaceParser.parseMessageRef("Message Ref", new BpmnModel()));
  }

  /**
   * Method under test: {@link InterfaceParser#parseMessageRef(String, BpmnModel)}
   */
  @Test
  void testParseMessageRef2() {
    // Arrange
    InterfaceParser interfaceParser = new InterfaceParser();

    // Act and Assert
    assertNull(interfaceParser.parseMessageRef("", new BpmnModel()));
  }
}
