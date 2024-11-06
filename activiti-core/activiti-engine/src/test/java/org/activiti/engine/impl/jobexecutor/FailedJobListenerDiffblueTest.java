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
package org.activiti.engine.impl.jobexecutor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import java.util.Map;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.DeadLetterJobEntityImpl;
import org.activiti.engine.runtime.Job;
import org.junit.Test;

public class FailedJobListenerDiffblueTest {
  /**
   * Test {@link FailedJobListener#FailedJobListener(CommandExecutor, Job)}.
   * <p>
   * Method under test:
   * {@link FailedJobListener#FailedJobListener(CommandExecutor, Job)}
   */
  @Test
  public void testNewFailedJobListener() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();
    CommandContextInterceptor first = new CommandContextInterceptor();
    CommandExecutorImpl commandExecutor = new CommandExecutorImpl(defaultConfig, first);

    // Act
    FailedJobListener actualFailedJobListener = new FailedJobListener(commandExecutor, new DeadLetterJobEntityImpl());

    // Assert
    Job job = actualFailedJobListener.job;
    Object persistentState = ((DeadLetterJobEntityImpl) job).getPersistentState();
    assertTrue(persistentState instanceof Map);
    CommandExecutor commandExecutor2 = actualFailedJobListener.commandExecutor;
    assertTrue(commandExecutor2 instanceof CommandExecutorImpl);
    assertTrue(job instanceof DeadLetterJobEntityImpl);
    assertEquals("", job.getTenantId());
    assertEquals(3, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("duedate"));
    assertNull(((Map<String, Integer>) persistentState).get("exceptionMessage"));
    assertNull(((DeadLetterJobEntityImpl) job).getExceptionStacktrace());
    assertNull(((DeadLetterJobEntityImpl) job).getRepeat());
    assertNull(job.getExceptionMessage());
    assertNull(job.getExecutionId());
    assertNull(job.getId());
    assertNull(job.getJobHandlerConfiguration());
    assertNull(job.getJobHandlerType());
    assertNull(job.getJobType());
    assertNull(job.getProcessDefinitionId());
    assertNull(job.getProcessInstanceId());
    assertNull(((DeadLetterJobEntityImpl) job).getEndDate());
    assertNull(job.getDuedate());
    assertNull(((DeadLetterJobEntityImpl) job).getExceptionByteArrayRef());
    assertEquals(0, ((Map<String, Integer>) persistentState).get("retries").intValue());
    assertEquals(0, ((DeadLetterJobEntityImpl) job).getMaxIterations());
    assertEquals(0, job.getRetries());
    assertEquals(1, ((DeadLetterJobEntityImpl) job).getRevision());
    assertEquals(2, ((DeadLetterJobEntityImpl) job).getRevisionNext());
    assertFalse(((DeadLetterJobEntityImpl) job).isDeleted());
    assertFalse(((DeadLetterJobEntityImpl) job).isInserted());
    assertFalse(((DeadLetterJobEntityImpl) job).isUpdated());
    assertTrue(job.isExclusive());
    assertSame(defaultConfig, commandExecutor2.getDefaultConfig());
    assertSame(first, ((CommandExecutorImpl) commandExecutor2).getFirst());
  }
}
