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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManagerImpl;
import org.activiti.engine.impl.persistence.entity.data.ExecutionDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

public class JobRetryCmdDiffblueTest {
  /**
   * Test {@link JobRetryCmd#JobRetryCmd(String, Throwable)}.
   *
   * <p>Method under test: {@link JobRetryCmd#JobRetryCmd(String, Throwable)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void JobRetryCmd.<init>(String, Throwable)"})
  public void testNewJobRetryCmd() {
    // Arrange and Act
    JobRetryCmd actualJobRetryCmd = new JobRetryCmd("42", new Throwable());

    // Assert
    Throwable throwable = actualJobRetryCmd.exception;
    assertNull(throwable.getLocalizedMessage());
    assertNull(throwable.getMessage());
    assertNull(throwable.getCause());
    assertEquals(0, throwable.getSuppressed().length);
  }

  /**
   * Test {@link JobRetryCmd#fetchExecutionEntity(CommandContext, String)}.
   *
   * <ul>
   *   <li>Then return createWithEmptyRelationshipCollections.
   * </ul>
   *
   * <p>Method under test: {@link JobRetryCmd#fetchExecutionEntity(CommandContext, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntity JobRetryCmd.fetchExecutionEntity(CommandContext, String)"})
  public void testFetchExecutionEntity_thenReturnCreateWithEmptyRelationshipCollections() {
    // Arrange
    JobRetryCmd jobRetryCmd = new JobRetryCmd("42", new Throwable());

    ExecutionDataManager executionDataManager = mock(ExecutionDataManager.class);
    ExecutionEntityImpl createWithEmptyRelationshipCollectionsResult =
        ExecutionEntityImpl.createWithEmptyRelationshipCollections();
    when(executionDataManager.findById(Mockito.<String>any()))
        .thenReturn(createWithEmptyRelationshipCollectionsResult);
    ExecutionEntityManagerImpl executionEntityManagerImpl =
        new ExecutionEntityManagerImpl(new JtaProcessEngineConfiguration(), executionDataManager);

    CommandContext commandContext = mock(CommandContext.class);
    when(commandContext.getExecutionEntityManager()).thenReturn(executionEntityManagerImpl);

    // Act
    ExecutionEntity actualFetchExecutionEntityResult =
        jobRetryCmd.fetchExecutionEntity(commandContext, "0123456789ABCDEF");

    // Assert
    verify(commandContext).getExecutionEntityManager();
    verify(executionDataManager).findById("0123456789ABCDEF");
    assertSame(createWithEmptyRelationshipCollectionsResult, actualFetchExecutionEntityResult);
  }

  /**
   * Test {@link JobRetryCmd#fetchExecutionEntity(CommandContext, String)}.
   *
   * <ul>
   *   <li>When {@link CommandContext}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link JobRetryCmd#fetchExecutionEntity(CommandContext, String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ExecutionEntity JobRetryCmd.fetchExecutionEntity(CommandContext, String)"})
  public void testFetchExecutionEntity_whenCommandContext_thenReturnNull() {
    // Arrange
    JobRetryCmd jobRetryCmd = new JobRetryCmd("42", new Throwable());

    // Act and Assert
    assertNull(jobRetryCmd.fetchExecutionEntity(mock(CommandContext.class), null));
  }
}
