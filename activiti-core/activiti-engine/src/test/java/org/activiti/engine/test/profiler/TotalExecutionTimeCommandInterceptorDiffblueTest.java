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
package org.activiti.engine.test.profiler;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class TotalExecutionTimeCommandInterceptorDiffblueTest {
  /**
   * Test {@link TotalExecutionTimeCommandInterceptor#execute(CommandConfig, Command)}.
   *
   * <p>Method under test: {@link TotalExecutionTimeCommandInterceptor#execute(CommandConfig,
   * Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Object TotalExecutionTimeCommandInterceptor.execute(CommandConfig, Command)"
  })
  public void testExecute() {
    // Arrange
    TotalExecutionTimeCommandInterceptor next = new TotalExecutionTimeCommandInterceptor();
    next.setNext(mock(CommandContextInterceptor.class));

    TotalExecutionTimeCommandInterceptor totalExecutionTimeCommandInterceptor =
        new TotalExecutionTimeCommandInterceptor();
    totalExecutionTimeCommandInterceptor.setNext(next);

    // Act and Assert
    assertNull(totalExecutionTimeCommandInterceptor.execute(null, mock(Command.class)));
  }

  /**
   * Test {@link TotalExecutionTimeCommandInterceptor#execute(CommandConfig, Command)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TotalExecutionTimeCommandInterceptor#execute(CommandConfig,
   * Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Object TotalExecutionTimeCommandInterceptor.execute(CommandConfig, Command)"
  })
  public void testExecute_thenReturnNull() {
    // Arrange
    TotalExecutionTimeCommandInterceptor totalExecutionTimeCommandInterceptor =
        new TotalExecutionTimeCommandInterceptor();
    totalExecutionTimeCommandInterceptor.setNext(mock(CommandContextInterceptor.class));

    // Act and Assert
    assertNull(
        totalExecutionTimeCommandInterceptor.execute(new CommandConfig(), mock(Command.class)));
  }

  /**
   * Test {@link TotalExecutionTimeCommandInterceptor#execute(CommandConfig, Command)}.
   *
   * <ul>
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link TotalExecutionTimeCommandInterceptor#execute(CommandConfig,
   * Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "java.lang.Object TotalExecutionTimeCommandInterceptor.execute(CommandConfig, Command)"
  })
  public void testExecute_thenReturnNull2() {
    // Arrange
    TotalExecutionTimeCommandInterceptor totalExecutionTimeCommandInterceptor =
        new TotalExecutionTimeCommandInterceptor();
    totalExecutionTimeCommandInterceptor.setNext(mock(CommandContextInterceptor.class));

    // Act and Assert
    assertNull(totalExecutionTimeCommandInterceptor.execute(null, mock(Command.class)));
  }
}
