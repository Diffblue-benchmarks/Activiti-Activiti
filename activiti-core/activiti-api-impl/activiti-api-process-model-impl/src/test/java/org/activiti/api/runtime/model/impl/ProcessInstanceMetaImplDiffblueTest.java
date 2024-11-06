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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProcessInstanceMetaImplDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessInstanceMetaImpl#ProcessInstanceMetaImpl()}
   *   <li>{@link ProcessInstanceMetaImpl#setActiveActivitiesIds(List)}
   *   <li>{@link ProcessInstanceMetaImpl#setProcessInstanceId(String)}
   *   <li>{@link ProcessInstanceMetaImpl#getActiveActivitiesIds()}
   *   <li>{@link ProcessInstanceMetaImpl#getProcessInstanceId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ProcessInstanceMetaImpl actualProcessInstanceMetaImpl = new ProcessInstanceMetaImpl();
    ArrayList<String> activeActivitiesIds = new ArrayList<>();
    actualProcessInstanceMetaImpl.setActiveActivitiesIds(activeActivitiesIds);
    actualProcessInstanceMetaImpl.setProcessInstanceId("42");
    List<String> actualActiveActivitiesIds = actualProcessInstanceMetaImpl.getActiveActivitiesIds();

    // Assert that nothing has changed
    assertEquals("42", actualProcessInstanceMetaImpl.getProcessInstanceId());
    assertTrue(actualActiveActivitiesIds.isEmpty());
    assertSame(activeActivitiesIds, actualActiveActivitiesIds);
  }

  /**
   * Test getters and setters.
   * <ul>
   *   <li>When {@code 42}.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ProcessInstanceMetaImpl#ProcessInstanceMetaImpl(String)}
   *   <li>{@link ProcessInstanceMetaImpl#setActiveActivitiesIds(List)}
   *   <li>{@link ProcessInstanceMetaImpl#setProcessInstanceId(String)}
   *   <li>{@link ProcessInstanceMetaImpl#getActiveActivitiesIds()}
   *   <li>{@link ProcessInstanceMetaImpl#getProcessInstanceId()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters; when '42'")
  void testGettersAndSetters_when42() {
    // Arrange and Act
    ProcessInstanceMetaImpl actualProcessInstanceMetaImpl = new ProcessInstanceMetaImpl("42");
    ArrayList<String> activeActivitiesIds = new ArrayList<>();
    actualProcessInstanceMetaImpl.setActiveActivitiesIds(activeActivitiesIds);
    actualProcessInstanceMetaImpl.setProcessInstanceId("42");
    List<String> actualActiveActivitiesIds = actualProcessInstanceMetaImpl.getActiveActivitiesIds();

    // Assert that nothing has changed
    assertEquals("42", actualProcessInstanceMetaImpl.getProcessInstanceId());
    assertTrue(actualActiveActivitiesIds.isEmpty());
    assertSame(activeActivitiesIds, actualActiveActivitiesIds);
  }
}
