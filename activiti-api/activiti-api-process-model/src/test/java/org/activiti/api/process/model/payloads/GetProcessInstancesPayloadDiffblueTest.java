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
package org.activiti.api.process.model.payloads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GetProcessInstancesPayloadDiffblueTest {
  /**
   * Test new {@link GetProcessInstancesPayload} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link GetProcessInstancesPayload}
   */
  @Test
  @DisplayName("Test new GetProcessInstancesPayload (default constructor)")
  void testNewGetProcessInstancesPayload() {
    // Arrange and Act
    GetProcessInstancesPayload actualGetProcessInstancesPayload = new GetProcessInstancesPayload();

    // Assert
    assertNull(actualGetProcessInstancesPayload.getBusinessKey());
    assertNull(actualGetProcessInstancesPayload.getParentProcessInstanceId());
    assertFalse(actualGetProcessInstancesPayload.isActiveOnly());
    assertFalse(actualGetProcessInstancesPayload.isSuspendedOnly());
    assertTrue(actualGetProcessInstancesPayload.getProcessDefinitionKeys().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link GetProcessInstancesPayload#setActiveOnly(boolean)}
   *   <li>{@link GetProcessInstancesPayload#setBusinessKey(String)}
   *   <li>{@link GetProcessInstancesPayload#setParentProcessInstanceId(String)}
   *   <li>{@link GetProcessInstancesPayload#setProcessDefinitionKeys(Set)}
   *   <li>{@link GetProcessInstancesPayload#setSuspendedOnly(boolean)}
   *   <li>{@link GetProcessInstancesPayload#getBusinessKey()}
   *   <li>{@link GetProcessInstancesPayload#getId()}
   *   <li>{@link GetProcessInstancesPayload#getParentProcessInstanceId()}
   *   <li>{@link GetProcessInstancesPayload#getProcessDefinitionKeys()}
   *   <li>{@link GetProcessInstancesPayload#isActiveOnly()}
   *   <li>{@link GetProcessInstancesPayload#isSuspendedOnly()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    GetProcessInstancesPayload getProcessInstancesPayload = new GetProcessInstancesPayload();

    // Act
    getProcessInstancesPayload.setActiveOnly(true);
    getProcessInstancesPayload.setBusinessKey("Business Key");
    getProcessInstancesPayload.setParentProcessInstanceId("42");
    HashSet<String> processDefinitionKeys = new HashSet<>();
    getProcessInstancesPayload.setProcessDefinitionKeys(processDefinitionKeys);
    getProcessInstancesPayload.setSuspendedOnly(true);
    String actualBusinessKey = getProcessInstancesPayload.getBusinessKey();
    getProcessInstancesPayload.getId();
    String actualParentProcessInstanceId = getProcessInstancesPayload.getParentProcessInstanceId();
    Set<String> actualProcessDefinitionKeys = getProcessInstancesPayload.getProcessDefinitionKeys();
    boolean actualIsActiveOnlyResult = getProcessInstancesPayload.isActiveOnly();
    boolean actualIsSuspendedOnlyResult = getProcessInstancesPayload.isSuspendedOnly();

    // Assert that nothing has changed
    assertEquals("42", actualParentProcessInstanceId);
    assertEquals("Business Key", actualBusinessKey);
    assertTrue(actualProcessDefinitionKeys.isEmpty());
    assertTrue(actualIsActiveOnlyResult);
    assertTrue(actualIsSuspendedOnlyResult);
    assertSame(processDefinitionKeys, actualProcessDefinitionKeys);
  }
}
