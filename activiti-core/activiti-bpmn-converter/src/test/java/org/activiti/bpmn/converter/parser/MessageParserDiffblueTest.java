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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MessageParserDiffblueTest {
  /**
   * Test {@link MessageParser#parseItemRef(String, BpmnModel)}.
   * <ul>
   *   <li>When empty string.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageParser#parseItemRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseItemRef(String, BpmnModel); when empty string; then return 'null'")
  void testParseItemRef_whenEmptyString_thenReturnNull() {
    // Arrange
    MessageParser messageParser = new MessageParser();

    // Act and Assert
    assertNull(messageParser.parseItemRef("", new BpmnModel()));
  }

  /**
   * Test {@link MessageParser#parseItemRef(String, BpmnModel)}.
   * <ul>
   *   <li>When {@code Item Ref}.</li>
   *   <li>Then return {@code null:Item Ref}.</li>
   * </ul>
   * <p>
   * Method under test: {@link MessageParser#parseItemRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseItemRef(String, BpmnModel); when 'Item Ref'; then return 'null:Item Ref'")
  void testParseItemRef_whenItemRef_thenReturnNullItemRef() {
    // Arrange
    MessageParser messageParser = new MessageParser();

    // Act and Assert
    assertEquals("null:Item Ref", messageParser.parseItemRef("Item Ref", new BpmnModel()));
  }
}
