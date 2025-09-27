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
package org.activiti.engine.impl.event.logger.handler;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivitySignaledEventHandlerDiffblueTest {
  /**
   * Test new {@link ActivitySignaledEventHandler} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link
   * ActivitySignaledEventHandler}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitySignaledEventHandler.<init>()"})
  public void testNewActivitySignaledEventHandler() {
    // Arrange and Act
    ActivitySignaledEventHandler actualActivitySignaledEventHandler =
        new ActivitySignaledEventHandler();

    // Assert
    assertNull(actualActivitySignaledEventHandler.objectMapper);
    assertNull(actualActivitySignaledEventHandler.timeStamp);
    assertNull(actualActivitySignaledEventHandler.event);
  }
}
