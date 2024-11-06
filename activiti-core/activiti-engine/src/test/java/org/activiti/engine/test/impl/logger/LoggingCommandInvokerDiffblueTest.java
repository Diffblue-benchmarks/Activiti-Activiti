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
package org.activiti.engine.test.impl.logger;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Test;

public class LoggingCommandInvokerDiffblueTest {
  /**
   * Test
   * {@link LoggingCommandInvoker#LoggingCommandInvoker(ProcessExecutionLogger)}.
   * <p>
   * Method under test:
   * {@link LoggingCommandInvoker#LoggingCommandInvoker(ProcessExecutionLogger)}
   */
  @Test
  public void testNewLoggingCommandInvoker() {
    // Arrange and Act
    LoggingCommandInvoker actualLoggingCommandInvoker = new LoggingCommandInvoker(new ProcessExecutionLogger());

    // Assert
    assertNull(actualLoggingCommandInvoker.getNext());
    ProcessExecutionLogger processExecutionLogger = actualLoggingCommandInvoker.processExecutionLogger;
    assertTrue(processExecutionLogger.createdExecutions.isEmpty());
    assertTrue(processExecutionLogger.debugInfoMap.isEmpty());
    assertTrue(processExecutionLogger.deletedExecutions.isEmpty());
  }

  /**
   * Test {@link LoggingCommandInvoker#executeOperation(Runnable)}.
   * <p>
   * Method under test: {@link LoggingCommandInvoker#executeOperation(Runnable)}
   */
  @Test
  public void testExecuteOperation() {
    // Arrange
    LoggingCommandInvoker loggingCommandInvoker = new LoggingCommandInvoker(new ProcessExecutionLogger());
    Runnable runnable = mock(Runnable.class);
    doNothing().when(runnable).run();

    // Act
    loggingCommandInvoker.executeOperation(runnable);

    // Assert that nothing has changed
    verify(runnable).run();
  }
}
