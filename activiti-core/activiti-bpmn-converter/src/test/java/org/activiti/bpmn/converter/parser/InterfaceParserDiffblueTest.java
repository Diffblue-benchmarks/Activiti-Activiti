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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.bpmn.model.BpmnModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class InterfaceParserDiffblueTest {
  /**
   * Test {@link InterfaceParser#parseMessageRef(String, BpmnModel)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link InterfaceParser#parseMessageRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessageRef(String, BpmnModel); when empty string; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String InterfaceParser.parseMessageRef(String, BpmnModel)"})
  void testParseMessageRef_whenEmptyString_thenReturnNull() {
    // Arrange
    InterfaceParser interfaceParser = new InterfaceParser();

    // Act and Assert
    assertNull(interfaceParser.parseMessageRef("", new BpmnModel()));
  }

  /**
   * Test {@link InterfaceParser#parseMessageRef(String, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@code Message Ref}.
   *   <li>Then return {@code Message Ref}.
   * </ul>
   *
   * <p>Method under test: {@link InterfaceParser#parseMessageRef(String, BpmnModel)}
   */
  @Test
  @DisplayName(
      "Test parseMessageRef(String, BpmnModel); when 'Message Ref'; then return 'Message Ref'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String InterfaceParser.parseMessageRef(String, BpmnModel)"})
  void testParseMessageRef_whenMessageRef_thenReturnMessageRef() {
    // Arrange
    InterfaceParser interfaceParser = new InterfaceParser();

    // Act and Assert
    assertEquals("Message Ref", interfaceParser.parseMessageRef("Message Ref", new BpmnModel()));
  }

  /**
   * Test {@link InterfaceParser#parseMessageRef(String, BpmnModel)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link InterfaceParser#parseMessageRef(String, BpmnModel)}
   */
  @Test
  @DisplayName("Test parseMessageRef(String, BpmnModel); when 'null'; then return 'null'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"String InterfaceParser.parseMessageRef(String, BpmnModel)"})
  void testParseMessageRef_whenNull_thenReturnNull() {
    // Arrange
    InterfaceParser interfaceParser = new InterfaceParser();

    // Act and Assert
    assertNull(interfaceParser.parseMessageRef(null, new BpmnModel()));
  }
}
