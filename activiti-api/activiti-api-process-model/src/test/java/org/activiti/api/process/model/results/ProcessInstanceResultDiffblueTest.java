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
package org.activiti.api.process.model.results;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import org.activiti.api.model.shared.Payload;
import org.activiti.api.process.model.ProcessInstance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessInstanceResultDiffblueTest {
  /**
   * Test {@link ProcessInstanceResult#ProcessInstanceResult()}.
   * <ul>
   *   <li>Then return Payload is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessInstanceResult#ProcessInstanceResult()}
   */
  @Test
  @DisplayName("Test new ProcessInstanceResult(); then return Payload is 'null'")
  void testNewProcessInstanceResult_thenReturnPayloadIsNull() {
    // Arrange and Act
    ProcessInstanceResult actualProcessInstanceResult = new ProcessInstanceResult();

    // Assert
    assertNull(actualProcessInstanceResult.getPayload());
    assertNull(actualProcessInstanceResult.getEntity());
  }

  /**
   * Test
   * {@link ProcessInstanceResult#ProcessInstanceResult(Payload, ProcessInstance)}.
   * <ul>
   *   <li>When {@link Payload}.</li>
   *   <li>Then return {@link Payload}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessInstanceResult#ProcessInstanceResult(Payload, ProcessInstance)}
   */
  @Test
  @DisplayName("Test new ProcessInstanceResult(Payload, ProcessInstance); when Payload; then return Payload")
  void testNewProcessInstanceResult_whenPayload_thenReturnPayload() {
    // Arrange
    Payload payload = mock(Payload.class);
    ProcessInstance entity = mock(ProcessInstance.class);

    // Act
    ProcessInstanceResult actualProcessInstanceResult = new ProcessInstanceResult(payload, entity);

    // Assert
    assertSame(payload, actualProcessInstanceResult.getPayload());
    assertSame(entity, actualProcessInstanceResult.getEntity());
  }
}
