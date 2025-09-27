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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.event.impl.ActivitiEventDispatcherImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityImpl;
import org.activiti.engine.impl.persistence.entity.TimerJobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.TimerJobDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class SetTimerJobRetriesCmdDiffblueTest {
  /**
   * Test {@link SetTimerJobRetriesCmd#SetTimerJobRetriesCmd(String, int)}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link SetTimerJobRetriesCmd#SetTimerJobRetriesCmd(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetTimerJobRetriesCmd.<init>(String, int)"})
  public void testNewSetTimerJobRetriesCmd_whenEmptyString() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new SetTimerJobRetriesCmd("", 1));
  }

  /**
   * Test {@link SetTimerJobRetriesCmd#SetTimerJobRetriesCmd(String, int)}.
   *
   * <ul>
   *   <li>When minus one.
   * </ul>
   *
   * <p>Method under test: {@link SetTimerJobRetriesCmd#SetTimerJobRetriesCmd(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetTimerJobRetriesCmd.<init>(String, int)"})
  public void testNewSetTimerJobRetriesCmd_whenMinusOne() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new SetTimerJobRetriesCmd("42", -1));
  }

  /**
   * Test {@link SetTimerJobRetriesCmd#SetTimerJobRetriesCmd(String, int)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link SetTimerJobRetriesCmd#SetTimerJobRetriesCmd(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetTimerJobRetriesCmd.<init>(String, int)"})
  public void testNewSetTimerJobRetriesCmd_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new SetTimerJobRetriesCmd(null, 0));
  }

  /**
   * Test {@link SetTimerJobRetriesCmd#SetTimerJobRetriesCmd(String, int)}.
   *
   * <ul>
   *   <li>When zero.
   *   <li>Then return execute {@link CommandContext} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SetTimerJobRetriesCmd#SetTimerJobRetriesCmd(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetTimerJobRetriesCmd.<init>(String, int)"})
  public void testNewSetTimerJobRetriesCmd_whenZero_thenReturnExecuteCommandContextIsNull() {
    // Arrange and Act
    SetTimerJobRetriesCmd actualSetTimerJobRetriesCmd = new SetTimerJobRetriesCmd("42", 0);
    TimerJobDataManager jobDataManager = mock(TimerJobDataManager.class);
    when(jobDataManager.findById(Mockito.<String>any())).thenReturn(new TimerJobEntityImpl());
    TimerJobEntityManagerImpl timerJobEntityManagerImpl =
        new TimerJobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(commandContext.getTimerJobEntityManager()).thenReturn(timerJobEntityManagerImpl);
    Void actualExecuteResult = actualSetTimerJobRetriesCmd.execute(commandContext);

    // Assert
    verify(commandContext, atLeast(1)).getEventDispatcher();
    verify(commandContext).getTimerJobEntityManager();
    verify(jobDataManager).findById("42");
    assertNull(actualExecuteResult);
  }
}
