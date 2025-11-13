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
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LogInterceptorDiffblueTest {
  /**
   * Test {@link LogInterceptor#execute(CommandConfig, Command)}.
   *
   * <ul>
   *   <li>Given {@link LogInterceptor} (default constructor) Next is {@link
   *       CommandContextInterceptor}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LogInterceptor#execute(CommandConfig, Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Object LogInterceptor.execute(CommandConfig, Command)"})
  public void testExecute_givenLogInterceptorNextIsCommandContextInterceptor_thenReturnNull() {
    // Arrange
    LogInterceptor logInterceptor = new LogInterceptor();
    logInterceptor.setNext(mock(CommandContextInterceptor.class));

    // Act and Assert
    assertNull(logInterceptor.execute(new CommandConfig(), mock(Command.class)));
  }

  /**
   * Test {@link LogInterceptor#execute(CommandConfig, Command)}.
   *
   * <ul>
   *   <li>Given {@link LogInterceptor} (default constructor) Next is {@link
   *       CommandContextInterceptor}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link LogInterceptor#execute(CommandConfig, Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.Object LogInterceptor.execute(CommandConfig, Command)"})
  public void testExecute_givenLogInterceptorNextIsCommandContextInterceptor_thenReturnNull2() {
    // Arrange
    LogInterceptor next = new LogInterceptor();
    next.setNext(mock(CommandContextInterceptor.class));

    LogInterceptor logInterceptor = new LogInterceptor();
    logInterceptor.setNext(next);

    // Act and Assert
    assertNull(logInterceptor.execute(new CommandConfig(), mock(Command.class)));
  }

  /**
   * Test new {@link LogInterceptor} (default constructor).
   *
   * <p>Method under test: default or parameterless constructor of {@link LogInterceptor}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void LogInterceptor.<init>()"})
  public void testNewLogInterceptor() {
    // Arrange, Act and Assert
    assertNull(new LogInterceptor().getNext());
  }
}
