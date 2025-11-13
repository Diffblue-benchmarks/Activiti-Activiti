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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiOptimisticLockingException;
import org.activiti.engine.impl.DeadLetterJobQueryImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class RetryInterceptorDiffblueTest {
  /**
   * Test {@link RetryInterceptor#execute(CommandConfig, Command)}.
   *
   * <ul>
   *   <li>Given {@link RetryInterceptor} (default constructor) Next is {@link
   *       CommandContextInterceptor}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link RetryInterceptor#execute(CommandConfig, Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RetryInterceptor.execute(CommandConfig, Command)"})
  public void testExecute_givenRetryInterceptorNextIsCommandContextInterceptor_thenReturnNull() {
    // Arrange
    RetryInterceptor retryInterceptor = new RetryInterceptor();
    retryInterceptor.setNext(mock(CommandContextInterceptor.class));

    // Act and Assert
    assertNull(retryInterceptor.execute(new CommandConfig(), mock(Command.class)));
  }

  /**
   * Test {@link RetryInterceptor#execute(CommandConfig, Command)}.
   *
   * <ul>
   *   <li>Then throw {@link ActivitiException}.
   * </ul>
   *
   * <p>Method under test: {@link RetryInterceptor#execute(CommandConfig, Command)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object RetryInterceptor.execute(CommandConfig, Command)"})
  public void testExecute_thenThrowActivitiException() {
    // Arrange
    CommandContextInterceptor next = mock(CommandContextInterceptor.class);
    when(next.execute(Mockito.<CommandConfig>any(), Mockito.<Command<Object>>any()))
        .thenThrow(new ActivitiOptimisticLockingException("An error occurred"));

    RetryInterceptor retryInterceptor = new RetryInterceptor();
    retryInterceptor.setNext(next);
    CommandConfig config = new CommandConfig();

    // Act and Assert
    assertThrows(
        ActivitiException.class,
        () -> retryInterceptor.execute(config, new DeadLetterJobQueryImpl()));
    verify(next, atLeast(1)).execute(isA(CommandConfig.class), isA(Command.class));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link RetryInterceptor}
   *   <li>{@link RetryInterceptor#setNumOfRetries(int)}
   *   <li>{@link RetryInterceptor#setWaitIncreaseFactor(int)}
   *   <li>{@link RetryInterceptor#setWaitTimeInMs(int)}
   *   <li>{@link RetryInterceptor#getNumOfRetries()}
   *   <li>{@link RetryInterceptor#getWaitIncreaseFactor()}
   *   <li>{@link RetryInterceptor#getWaitTimeInMs()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void RetryInterceptor.<init>()",
    "int RetryInterceptor.getNumOfRetries()",
    "int RetryInterceptor.getWaitIncreaseFactor()",
    "int RetryInterceptor.getWaitTimeInMs()",
    "void RetryInterceptor.setNumOfRetries(int)",
    "void RetryInterceptor.setWaitIncreaseFactor(int)",
    "void RetryInterceptor.setWaitTimeInMs(int)"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    RetryInterceptor actualRetryInterceptor = new RetryInterceptor();
    actualRetryInterceptor.setNumOfRetries(10);
    actualRetryInterceptor.setWaitIncreaseFactor(3);
    actualRetryInterceptor.setWaitTimeInMs(1);
    int actualNumOfRetries = actualRetryInterceptor.getNumOfRetries();
    int actualWaitIncreaseFactor = actualRetryInterceptor.getWaitIncreaseFactor();
    int actualWaitTimeInMs = actualRetryInterceptor.getWaitTimeInMs();

    // Assert
    assertNull(actualRetryInterceptor.getNext());
    assertEquals(1, actualWaitTimeInMs);
    assertEquals(10, actualNumOfRetries);
    assertEquals(3, actualWaitIncreaseFactor);
  }
}
