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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class StartCreatedProcessInstanceCmdDiffblueTest {
  /**
   * Test {@link StartCreatedProcessInstanceCmd#execute(CommandContext)}.
   *
   * <p>Method under test: {@link StartCreatedProcessInstanceCmd#execute(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstance StartCreatedProcessInstanceCmd.execute(CommandContext)"})
  public void testExecute() {
    // Arrange
    ProcessInstance internalProcessInstance = mock(ProcessInstance.class);
    when(internalProcessInstance.getProcessInstanceId())
        .thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(internalProcessInstance.getStartTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    StartCreatedProcessInstanceCmd<Object> startCreatedProcessInstanceCmd =
        new StartCreatedProcessInstanceCmd<>(internalProcessInstance, new HashMap<>());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> startCreatedProcessInstanceCmd.execute(null));
    verify(internalProcessInstance).getProcessInstanceId();
    verify(internalProcessInstance).getStartTime();
  }

  /**
   * Test {@link StartCreatedProcessInstanceCmd#execute(CommandContext)}.
   *
   * <ul>
   *   <li>Given {@link ProcessInstance} {@link ProcessInstance#getProcessInstanceId()} return
   *       {@code 42}.
   * </ul>
   *
   * <p>Method under test: {@link StartCreatedProcessInstanceCmd#execute(CommandContext)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ProcessInstance StartCreatedProcessInstanceCmd.execute(CommandContext)"})
  public void testExecute_givenProcessInstanceGetProcessInstanceIdReturn42() {
    // Arrange
    ProcessInstance internalProcessInstance = mock(ProcessInstance.class);
    when(internalProcessInstance.getProcessInstanceId()).thenReturn("42");
    when(internalProcessInstance.getStartTime())
        .thenReturn(
            Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
    StartCreatedProcessInstanceCmd<Object> startCreatedProcessInstanceCmd =
        new StartCreatedProcessInstanceCmd<>(internalProcessInstance, new HashMap<>());

    // Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class, () -> startCreatedProcessInstanceCmd.execute(null));
    verify(internalProcessInstance).getProcessInstanceId();
    verify(internalProcessInstance).getStartTime();
  }
}
