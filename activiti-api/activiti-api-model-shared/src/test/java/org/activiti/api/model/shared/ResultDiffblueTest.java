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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResultDiffblueTest {
  /**
   * Test {@link Result#getPayload()}.
   * <ul>
   *   <li>Given {@link Payload} {@link Payload#getId()} return {@code 42}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Result#getPayload()}
   */
  @Test
  @DisplayName("Test getPayload(); given Payload getId() return '42'; then return Id is '42'")
  void testGetPayload_givenPayloadGetIdReturn42_thenReturnIdIs42() {
    // Arrange
    Payload payload = mock(Payload.class);
    when(payload.getId()).thenReturn("42");

    // Act
    String actualId = (new EmptyResult(payload)).getPayload().getId();

    // Assert
    verify(payload).getId();
    assertEquals("42", actualId);
  }

  /**
   * Test {@link Result#getEntity()}.
   * <ul>
   *   <li>Given {@link EmptyResult#EmptyResult()}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Result#getEntity()}
   */
  @Test
  @DisplayName("Test getEntity(); given EmptyResult()")
  void testGetEntity_givenEmptyResult() {
    // Arrange, Act and Assert
    assertNull((new EmptyResult()).getEntity());
  }

  /**
   * Test {@link Result#getEntity()}.
   * <ul>
   *   <li>Given {@link EmptyResult#EmptyResult(Payload)} with {@link Payload}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Result#getEntity()}
   */
  @Test
  @DisplayName("Test getEntity(); given EmptyResult(Payload) with Payload")
  void testGetEntity_givenEmptyResultWithPayload() {
    // Arrange, Act and Assert
    assertNull((new EmptyResult(mock(Payload.class))).getEntity());
  }
}
