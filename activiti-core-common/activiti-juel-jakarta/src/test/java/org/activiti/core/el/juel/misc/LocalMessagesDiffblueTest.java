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
package org.activiti.core.el.juel.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocalMessagesDiffblueTest {
  /**
   * Test {@link LocalMessages#get(String, Object[])}.
   * <ul>
   *   <li>When {@code Args} and {@code Args}.</li>
   *   <li>Then return {@code Unknown message: Key(Args, Args)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalMessages#get(String, Object[])}
   */
  @Test
  @DisplayName("Test get(String, Object[]); when 'Args' and 'Args'; then return 'Unknown message: Key(Args, Args)'")
  void testGet_whenArgsAndArgs_thenReturnUnknownMessageKeyArgsArgs() {
    // Arrange, Act and Assert
    assertEquals("Unknown message: Key(Args, Args)", LocalMessages.get("Key", "Args", "Args"));
  }

  /**
   * Test {@link LocalMessages#get(String, Object[])}.
   * <ul>
   *   <li>When {@code Args}.</li>
   *   <li>Then return {@code Unknown message: Key(Args)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalMessages#get(String, Object[])}
   */
  @Test
  @DisplayName("Test get(String, Object[]); when 'Args'; then return 'Unknown message: Key(Args)'")
  void testGet_whenArgs_thenReturnUnknownMessageKeyArgs() {
    // Arrange, Act and Assert
    assertEquals("Unknown message: Key(Args)", LocalMessages.get("Key", "Args"));
  }

  /**
   * Test {@link LocalMessages#get(String, Object[])}.
   * <ul>
   *   <li>When {@code Key}.</li>
   *   <li>Then return {@code Unknown message: Key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalMessages#get(String, Object[])}
   */
  @Test
  @DisplayName("Test get(String, Object[]); when 'Key'; then return 'Unknown message: Key'")
  void testGet_whenKey_thenReturnUnknownMessageKey() {
    // Arrange, Act and Assert
    assertEquals("Unknown message: Key", LocalMessages.get("Key"));
  }

  /**
   * Test {@link LocalMessages#get(String, Object[])}.
   * <ul>
   *   <li>When {@code message.unknown}.</li>
   *   <li>Then return {@code Unknown message}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalMessages#get(String, Object[])}
   */
  @Test
  @DisplayName("Test get(String, Object[]); when 'message.unknown'; then return 'Unknown message'")
  void testGet_whenMessageUnknown_thenReturnUnknownMessage() {
    // Arrange, Act and Assert
    assertEquals("Unknown message", LocalMessages.get("message.unknown", "Args"));
  }

  /**
   * Test {@link LocalMessages#get(String, Object[])}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code Unknown message: Key}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LocalMessages#get(String, Object[])}
   */
  @Test
  @DisplayName("Test get(String, Object[]); when 'null'; then return 'Unknown message: Key'")
  void testGet_whenNull_thenReturnUnknownMessageKey() {
    // Arrange, Act and Assert
    assertEquals("Unknown message: Key", LocalMessages.get("Key", null));
  }
}
