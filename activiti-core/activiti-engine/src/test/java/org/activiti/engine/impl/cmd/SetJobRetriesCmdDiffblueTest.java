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
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;
import org.activiti.engine.impl.persistence.entity.JobEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.JobDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class SetJobRetriesCmdDiffblueTest {
  /**
   * Test {@link SetJobRetriesCmd#SetJobRetriesCmd(String, int)}.
   *
   * <ul>
   *   <li>When empty string.
   * </ul>
   *
   * <p>Method under test: {@link SetJobRetriesCmd#SetJobRetriesCmd(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetJobRetriesCmd.<init>(String, int)"})
  public void testNewSetJobRetriesCmd_whenEmptyString() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new SetJobRetriesCmd("", 1));
  }

  /**
   * Test {@link SetJobRetriesCmd#SetJobRetriesCmd(String, int)}.
   *
   * <ul>
   *   <li>When minus one.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link SetJobRetriesCmd#SetJobRetriesCmd(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetJobRetriesCmd.<init>(String, int)"})
  public void testNewSetJobRetriesCmd_whenMinusOne_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new SetJobRetriesCmd("42", -1));
  }

  /**
   * Test {@link SetJobRetriesCmd#SetJobRetriesCmd(String, int)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link SetJobRetriesCmd#SetJobRetriesCmd(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetJobRetriesCmd.<init>(String, int)"})
  public void testNewSetJobRetriesCmd_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> new SetJobRetriesCmd(null, 0));
  }

  /**
   * Test {@link SetJobRetriesCmd#SetJobRetriesCmd(String, int)}.
   *
   * <ul>
   *   <li>When zero.
   *   <li>Then return execute {@link CommandContext} is {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link SetJobRetriesCmd#SetJobRetriesCmd(String, int)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void SetJobRetriesCmd.<init>(String, int)"})
  public void testNewSetJobRetriesCmd_whenZero_thenReturnExecuteCommandContextIsNull() {
    // Arrange and Act
    SetJobRetriesCmd actualSetJobRetriesCmd = new SetJobRetriesCmd("42", 0);
    JobDataManager jobDataManager = mock(JobDataManager.class);
    when(jobDataManager.findById(Mockito.<String>any())).thenReturn(new JobEntityImpl());
    JobEntityManagerImpl jobEntityManagerImpl =
        new JobEntityManagerImpl(new JtaProcessEngineConfiguration(), jobDataManager);
    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getEventDispatcher()).thenReturn(new ActivitiEventDispatcherImpl());
    when(commandContext.getJobEntityManager()).thenReturn(jobEntityManagerImpl);
    Void actualExecuteResult = actualSetJobRetriesCmd.execute(commandContext);

    // Assert
    verify(commandContext, atLeast(1)).getEventDispatcher();
    verify(commandContext).getJobEntityManager();
    verify(jobDataManager).findById("42");
    assertNull(actualExecuteResult);
  }
}
