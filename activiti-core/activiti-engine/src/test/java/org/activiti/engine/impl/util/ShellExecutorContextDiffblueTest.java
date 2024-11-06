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
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class ShellExecutorContextDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ShellExecutorContext#ShellExecutorContext(Boolean, Boolean, Boolean, String, String, String, List)}
   *   <li>{@link ShellExecutorContext#setArgList(List)}
   *   <li>{@link ShellExecutorContext#setWaitFlag(Boolean)}
   *   <li>{@link ShellExecutorContext#getArgList()}
   *   <li>{@link ShellExecutorContext#getCleanEnvBoolan()}
   *   <li>{@link ShellExecutorContext#getDirectoryStr()}
   *   <li>{@link ShellExecutorContext#getErrorCodeVariableStr()}
   *   <li>{@link ShellExecutorContext#getRedirectErrorFlag()}
   *   <li>{@link ShellExecutorContext#getResultVariableStr()}
   *   <li>{@link ShellExecutorContext#getWaitFlag()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    ShellExecutorContext actualShellExecutorContext = new ShellExecutorContext(true, true, true, "/directory",
        "Result Variable Str", "An error occurred", new ArrayList<>());
    ArrayList<String> argList = new ArrayList<>();
    actualShellExecutorContext.setArgList(argList);
    actualShellExecutorContext.setWaitFlag(true);
    List<String> actualArgList = actualShellExecutorContext.getArgList();
    Boolean actualCleanEnvBoolan = actualShellExecutorContext.getCleanEnvBoolan();
    String actualDirectoryStr = actualShellExecutorContext.getDirectoryStr();
    String actualErrorCodeVariableStr = actualShellExecutorContext.getErrorCodeVariableStr();
    Boolean actualRedirectErrorFlag = actualShellExecutorContext.getRedirectErrorFlag();
    String actualResultVariableStr = actualShellExecutorContext.getResultVariableStr();
    Boolean actualWaitFlag = actualShellExecutorContext.getWaitFlag();

    // Assert that nothing has changed
    assertEquals("/directory", actualDirectoryStr);
    assertEquals("An error occurred", actualErrorCodeVariableStr);
    assertEquals("Result Variable Str", actualResultVariableStr);
    assertTrue(actualArgList.isEmpty());
    assertTrue(actualCleanEnvBoolan);
    assertTrue(actualRedirectErrorFlag);
    assertTrue(actualWaitFlag);
    assertSame(argList, actualArgList);
  }
}
