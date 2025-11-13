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
package org.activiti.engine.impl.interceptor;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DebugCommandInvokerDiffblueTest {
  /**
   * Test {@link DebugCommandInvoker#executeOperation(Runnable)}.
   *
   * <p>Method under test: {@link DebugCommandInvoker#executeOperation(Runnable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DebugCommandInvoker.executeOperation(Runnable)"})
  public void testExecuteOperation() {
    // Arrange
    DebugCommandInvoker debugCommandInvoker = new DebugCommandInvoker();

    Runnable runnable = mock(Runnable.class);
    doNothing().when(runnable).run();

    // Act
    debugCommandInvoker.executeOperation(runnable);

    // Assert
    verify(runnable).run();
  }

  /**
   * Test new {@link DebugCommandInvoker} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link DebugCommandInvoker}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void DebugCommandInvoker.<init>()"})
  public void testNewDebugCommandInvoker() {
    // Arrange and Act
    DebugCommandInvoker actualDebugCommandInvoker = new DebugCommandInvoker();

    // Assert
    assertNull(actualDebugCommandInvoker.getNext());
    assertNull(actualDebugCommandInvoker.next);
  }
}
