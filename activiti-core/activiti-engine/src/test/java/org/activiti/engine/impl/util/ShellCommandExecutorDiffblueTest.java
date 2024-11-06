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
package org.activiti.engine.impl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.Test;

public class ShellCommandExecutorDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ShellCommandExecutor#ShellCommandExecutor(Boolean, Boolean, Boolean, String, String, String, List)}
   *   <li>{@link ShellCommandExecutor#setWaitFlag(Boolean)}
   *   <li>{@link ShellCommandExecutor#getArgList()}
   *   <li>{@link ShellCommandExecutor#getCleanEnvBoolean()}
   *   <li>{@link ShellCommandExecutor#getDirectoryStr()}
   *   <li>{@link ShellCommandExecutor#getErrorCodeVariableStr()}
   *   <li>{@link ShellCommandExecutor#getRedirectErrorFlag()}
   *   <li>{@link ShellCommandExecutor#getResultVariableStr()}
   *   <li>{@link ShellCommandExecutor#getWaitFlag()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    ArrayList<String> argList = new ArrayList<>();

    // Act
    ShellCommandExecutor actualShellCommandExecutor = new ShellCommandExecutor(true, true, true, "/directory",
        "Result Variable Str", "An error occurred", argList);
    actualShellCommandExecutor.setWaitFlag(true);
    List<String> actualArgList = actualShellCommandExecutor.getArgList();
    Boolean actualCleanEnvBoolean = actualShellCommandExecutor.getCleanEnvBoolean();
    String actualDirectoryStr = actualShellCommandExecutor.getDirectoryStr();
    String actualErrorCodeVariableStr = actualShellCommandExecutor.getErrorCodeVariableStr();
    Boolean actualRedirectErrorFlag = actualShellCommandExecutor.getRedirectErrorFlag();
    String actualResultVariableStr = actualShellCommandExecutor.getResultVariableStr();
    Boolean actualWaitFlag = actualShellCommandExecutor.getWaitFlag();

    // Assert that nothing has changed
    assertEquals("/directory", actualDirectoryStr);
    assertEquals("An error occurred", actualErrorCodeVariableStr);
    assertEquals("Result Variable Str", actualResultVariableStr);
    assertTrue(actualArgList.isEmpty());
    assertTrue(actualCleanEnvBoolean);
    assertTrue(actualRedirectErrorFlag);
    assertTrue(actualWaitFlag);
    assertSame(argList, actualArgList);
  }

  /**
   * Test {@link ShellCommandExecutor#ShellCommandExecutor(ShellExecutorContext)}.
   * <p>
   * Method under test:
   * {@link ShellCommandExecutor#ShellCommandExecutor(ShellExecutorContext)}
   */
  @Test
  public void testNewShellCommandExecutor() {
    // Arrange and Act
    ShellCommandExecutor actualShellCommandExecutor = new ShellCommandExecutor(new ShellExecutorContext(true, true,
        true, "/directory", "Result Variable Str", "An error occurred", new ArrayList<>()));

    // Assert
    assertEquals("/directory", actualShellCommandExecutor.getDirectoryStr());
    assertEquals("An error occurred", actualShellCommandExecutor.getErrorCodeVariableStr());
    assertEquals("Result Variable Str", actualShellCommandExecutor.getResultVariableStr());
    assertTrue(actualShellCommandExecutor.getArgList().isEmpty());
    assertTrue(actualShellCommandExecutor.getCleanEnvBoolean());
    assertTrue(actualShellCommandExecutor.getRedirectErrorFlag());
    assertTrue(actualShellCommandExecutor.getWaitFlag());
  }

  /**
   * Test {@link ShellCommandExecutor#executeCommand(DelegateExecution)}.
   * <ul>
   *   <li>Then calls {@link ShellExecutorContext#getArgList()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ShellCommandExecutor#executeCommand(DelegateExecution)}
   */
  @Test
  public void testExecuteCommand_thenCallsGetArgList() throws Exception {
    // Arrange
    ShellExecutorContext context = mock(ShellExecutorContext.class);
    when(context.getCleanEnvBoolan()).thenReturn(true);
    when(context.getRedirectErrorFlag()).thenReturn(true);
    when(context.getWaitFlag()).thenReturn(true);
    when(context.getDirectoryStr()).thenReturn("/directory");
    when(context.getErrorCodeVariableStr()).thenReturn("An error occurred");
    when(context.getResultVariableStr()).thenReturn("Result Variable Str");
    when(context.getArgList()).thenReturn(new ArrayList<>());
    ShellCommandExecutor shellCommandExecutor = new ShellCommandExecutor(context);

    // Act
    shellCommandExecutor.executeCommand(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert that nothing has changed
    verify(context).getArgList();
    verify(context).getCleanEnvBoolan();
    verify(context).getDirectoryStr();
    verify(context).getErrorCodeVariableStr();
    verify(context).getRedirectErrorFlag();
    verify(context).getResultVariableStr();
    verify(context).getWaitFlag();
  }
}
