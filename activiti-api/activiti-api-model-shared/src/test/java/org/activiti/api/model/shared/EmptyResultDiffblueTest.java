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
package org.activiti.api.model.shared;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmptyResultDiffblueTest {
  /**
   * Test {@link EmptyResult#EmptyResult()}.
   * <ul>
   *   <li>Then return Payload is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EmptyResult#EmptyResult()}
   */
  @Test
  @DisplayName("Test new EmptyResult(); then return Payload is 'null'")
  void testNewEmptyResult_thenReturnPayloadIsNull() {
    // Arrange and Act
    EmptyResult actualEmptyResult = new EmptyResult();

    // Assert
    assertNull(actualEmptyResult.getEntity());
    assertNull(actualEmptyResult.getPayload());
  }

  /**
   * Test {@link EmptyResult#EmptyResult(Payload)}.
   * <ul>
   *   <li>When {@link Payload}.</li>
   *   <li>Then return {@link Payload}.</li>
   * </ul>
   * <p>
   * Method under test: {@link EmptyResult#EmptyResult(Payload)}
   */
  @Test
  @DisplayName("Test new EmptyResult(Payload); when Payload; then return Payload")
  void testNewEmptyResult_whenPayload_thenReturnPayload() {
    // Arrange
    Payload payload = mock(Payload.class);

    // Act
    EmptyResult actualEmptyResult = new EmptyResult(payload);

    // Assert
    assertNull(actualEmptyResult.getEntity());
    assertSame(payload, actualEmptyResult.getPayload());
  }
}
