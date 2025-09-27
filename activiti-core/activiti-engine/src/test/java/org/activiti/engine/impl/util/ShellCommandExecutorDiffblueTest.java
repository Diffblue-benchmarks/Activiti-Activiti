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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ShellCommandExecutorDiffblueTest {
  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link ShellCommandExecutor#ShellCommandExecutor(Boolean, Boolean, Boolean, String,
   *       String, String, List)}
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
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ShellCommandExecutor.<init>(Boolean, Boolean, Boolean, String, String, String, List)",
    "List ShellCommandExecutor.getArgList()",
    "Boolean ShellCommandExecutor.getCleanEnvBoolean()",
    "String ShellCommandExecutor.getDirectoryStr()",
    "String ShellCommandExecutor.getErrorCodeVariableStr()",
    "Boolean ShellCommandExecutor.getRedirectErrorFlag()",
    "String ShellCommandExecutor.getResultVariableStr()",
    "Boolean ShellCommandExecutor.getWaitFlag()",
    "void ShellCommandExecutor.setWaitFlag(Boolean)"
  })
  public void testGettersAndSetters() {
    // Arrange
    ArrayList<String> argList = new ArrayList<>();

    // Act
    ShellCommandExecutor actualShellCommandExecutor =
        new ShellCommandExecutor(
            true, true, true, "/directory", "Result Variable Str", "An error occurred", argList);
    actualShellCommandExecutor.setWaitFlag(true);
    List<String> actualArgList = actualShellCommandExecutor.getArgList();
    Boolean actualCleanEnvBoolean = actualShellCommandExecutor.getCleanEnvBoolean();
    String actualDirectoryStr = actualShellCommandExecutor.getDirectoryStr();
    String actualErrorCodeVariableStr = actualShellCommandExecutor.getErrorCodeVariableStr();
    Boolean actualRedirectErrorFlag = actualShellCommandExecutor.getRedirectErrorFlag();
    String actualResultVariableStr = actualShellCommandExecutor.getResultVariableStr();
    Boolean actualWaitFlag = actualShellCommandExecutor.getWaitFlag();

    // Assert
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
   *
   * <p>Method under test: {@link ShellCommandExecutor#ShellCommandExecutor(ShellExecutorContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ShellCommandExecutor.<init>(ShellExecutorContext)"})
  public void testNewShellCommandExecutor() {
    // Arrange
    ShellExecutorContext context =
        new ShellExecutorContext(
            true,
            true,
            true,
            "/directory",
            "Result Variable Str",
            "An error occurred",
            new ArrayList<>());

    // Act
    ShellCommandExecutor actualShellCommandExecutor = new ShellCommandExecutor(context);

    // Assert
    assertEquals("/directory", actualShellCommandExecutor.getDirectoryStr());
    assertEquals("An error occurred", actualShellCommandExecutor.getErrorCodeVariableStr());
    assertEquals("Result Variable Str", actualShellCommandExecutor.getResultVariableStr());
    assertTrue(actualShellCommandExecutor.getArgList().isEmpty());
    assertTrue(actualShellCommandExecutor.getCleanEnvBoolean());
    assertTrue(actualShellCommandExecutor.getRedirectErrorFlag());
    assertTrue(actualShellCommandExecutor.getWaitFlag());
  }
}
