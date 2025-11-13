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
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class ResultDiffblueTest {
  /**
   * Test {@link Result#getPayload()}.
   *
   * <p>Method under test: {@link Result#getPayload()}
   */
  @Test
  @DisplayName("Test getPayload()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"org.activiti.api.model.shared.Payload Result.getPayload()"})
  void testGetPayload() {
    // Arrange, Act and Assert
    assertNull(new EmptyResult().getPayload());
  }

  /**
   * Test {@link Result#getEntity()}.
   *
   * <p>Method under test: {@link Result#getEntity()}
   */
  @Test
  @DisplayName("Test getEntity()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Object Result.getEntity()"})
  void testGetEntity() {
    // Arrange, Act and Assert
    assertNull(new EmptyResult().getEntity());
  }
}
