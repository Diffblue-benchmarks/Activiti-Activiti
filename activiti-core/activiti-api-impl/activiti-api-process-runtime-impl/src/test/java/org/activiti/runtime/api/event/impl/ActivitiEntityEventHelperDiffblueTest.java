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
package org.activiti.runtime.api.event.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ActivitiEntityEventHelperDiffblueTest {
  /**
   * Test {@link ActivitiEntityEventHelper#isProcessInstanceEntity(Object)}.
   *
   * <ul>
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEntityEventHelper#isProcessInstanceEntity(Object)}
   */
  @Test
  @DisplayName("Test isProcessInstanceEntity(Object); then return 'true'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ActivitiEntityEventHelper.isProcessInstanceEntity(Object)"})
  void testIsProcessInstanceEntity_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(
        ActivitiEntityEventHelper.isProcessInstanceEntity(
            ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ActivitiEntityEventHelper#isProcessInstanceEntity(Object)}.
   *
   * <ul>
   *   <li>When {@code Entity}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEntityEventHelper#isProcessInstanceEntity(Object)}
   */
  @Test
  @DisplayName("Test isProcessInstanceEntity(Object); when 'Entity'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ActivitiEntityEventHelper.isProcessInstanceEntity(Object)"})
  void testIsProcessInstanceEntity_whenEntity_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ActivitiEntityEventHelper.isProcessInstanceEntity("Entity"));
  }

  /**
   * Test {@link ActivitiEntityEventHelper#isProcessInstanceEntity(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEntityEventHelper#isProcessInstanceEntity(Object)}
   */
  @Test
  @DisplayName("Test isProcessInstanceEntity(Object); when 'null'; then return 'false'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ActivitiEntityEventHelper.isProcessInstanceEntity(Object)"})
  void testIsProcessInstanceEntity_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(ActivitiEntityEventHelper.isProcessInstanceEntity(null));
  }
}
