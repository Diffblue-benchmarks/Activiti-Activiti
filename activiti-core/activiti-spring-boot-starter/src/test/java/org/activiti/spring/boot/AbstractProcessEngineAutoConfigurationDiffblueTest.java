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
package org.activiti.spring.boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.activiti.core.common.spring.identity.ActivitiUserGroupManagerImpl;
import org.activiti.core.common.spring.identity.ExtendedInMemoryUserDetailsManager;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringCallerRunsRejectedJobsHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

class AbstractProcessEngineAutoConfigurationDiffblueTest {
  /**
   * Test
   * {@link AbstractProcessEngineAutoConfiguration#springAsyncExecutor(TaskExecutor)}.
   * <p>
   * Method under test:
   * {@link AbstractProcessEngineAutoConfiguration#springAsyncExecutor(TaskExecutor)}
   */
  @Test
  @DisplayName("Test springAsyncExecutor(TaskExecutor)")
  void testSpringAsyncExecutor() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    TaskExecutor applicationTaskExecutor = mock(TaskExecutor.class);

    // Act
    SpringAsyncExecutor actualSpringAsyncExecutorResult = (new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager())))
        .springAsyncExecutor(applicationTaskExecutor);

    // Assert
    assertTrue(actualSpringAsyncExecutorResult.getRejectedJobsHandler() instanceof SpringCallerRunsRejectedJobsHandler);
    assertNull(actualSpringAsyncExecutorResult.getAsyncJobAcquisitionThread());
    assertNull(actualSpringAsyncExecutorResult.getResetExpiredJobThread());
    assertNull(actualSpringAsyncExecutorResult.getTimerJobAcquisitionThread());
    assertNull(actualSpringAsyncExecutorResult.getThreadPoolQueue());
    assertNull(actualSpringAsyncExecutorResult.getExecutorService());
    assertNull(actualSpringAsyncExecutorResult.getExecuteAsyncRunnableFactory());
    assertNull(actualSpringAsyncExecutorResult.getProcessEngineConfiguration());
    assertEquals(0, actualSpringAsyncExecutorResult.getDefaultQueueSizeFullWaitTimeInMillis());
    assertEquals(1, actualSpringAsyncExecutorResult.getMaxAsyncJobsDuePerAcquisition());
    assertEquals(1, actualSpringAsyncExecutorResult.getMaxTimerJobsPerAcquisition());
    assertEquals(10, actualSpringAsyncExecutorResult.getMaxPoolSize());
    assertEquals(100, actualSpringAsyncExecutorResult.getQueueSize());
    assertEquals(10000, actualSpringAsyncExecutorResult.getDefaultAsyncJobAcquireWaitTimeInMillis());
    assertEquals(10000, actualSpringAsyncExecutorResult.getDefaultTimerJobAcquireWaitTimeInMillis());
    assertEquals(2, actualSpringAsyncExecutorResult.getCorePoolSize());
    assertEquals(3, actualSpringAsyncExecutorResult.getResetExpiredJobsPageSize());
    assertEquals(300000, actualSpringAsyncExecutorResult.getAsyncJobLockTimeInMillis());
    assertEquals(300000, actualSpringAsyncExecutorResult.getTimerLockTimeInMillis());
    assertEquals(500, actualSpringAsyncExecutorResult.getRetryWaitTimeInMillis());
    assertEquals(5000L, actualSpringAsyncExecutorResult.getKeepAliveTime());
    assertEquals(60000, actualSpringAsyncExecutorResult.getResetExpiredJobsInterval());
    assertEquals(60L, actualSpringAsyncExecutorResult.getSecondsToWaitOnShutdown());
    assertFalse(actualSpringAsyncExecutorResult.isActive());
    assertFalse(actualSpringAsyncExecutorResult.isAutoActivate());
    assertFalse(actualSpringAsyncExecutorResult.isMessageQueueMode());
    assertSame(applicationTaskExecutor, actualSpringAsyncExecutorResult.getTaskExecutor());
  }

  /**
   * Test
   * {@link AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}.
   * <p>
   * Method under test:
   * {@link AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}
   */
  @Test
  @DisplayName("Test getCustomMybatisMapperClasses(List)")
  void testGetCustomMybatisMapperClasses() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(mock(ExtendedInMemoryUserDetailsManager.class)));

    // Act and Assert
    assertTrue(processEngineAutoConfiguration.getCustomMybatisMapperClasses(new ArrayList<>()).isEmpty());
  }

  /**
   * Test
   * {@link AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}.
   * <ul>
   *   <li>Given {@code Custom My Batis Mappers}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}
   */
  @Test
  @DisplayName("Test getCustomMybatisMapperClasses(List); given 'Custom My Batis Mappers'")
  void testGetCustomMybatisMapperClasses_givenCustomMyBatisMappers() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    ArrayList<String> customMyBatisMappers = new ArrayList<>();
    customMyBatisMappers.add("Custom My Batis Mappers");

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> processEngineAutoConfiguration.getCustomMybatisMapperClasses(customMyBatisMappers));
  }

  /**
   * Test
   * {@link AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}.
   * <ul>
   *   <li>Given empty string.</li>
   *   <li>When {@link ArrayList#ArrayList()} add empty string.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}
   */
  @Test
  @DisplayName("Test getCustomMybatisMapperClasses(List); given empty string; when ArrayList() add empty string")
  void testGetCustomMybatisMapperClasses_givenEmptyString_whenArrayListAddEmptyString() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    ArrayList<String> customMyBatisMappers = new ArrayList<>();
    customMyBatisMappers.add("");
    customMyBatisMappers.add("foo");

    // Act and Assert
    assertThrows(IllegalArgumentException.class,
        () -> processEngineAutoConfiguration.getCustomMybatisMapperClasses(customMyBatisMappers));
  }

  /**
   * Test
   * {@link AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}.
   * <ul>
   *   <li>When {@link ArrayList#ArrayList()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractProcessEngineAutoConfiguration#getCustomMybatisMapperClasses(List)}
   */
  @Test
  @DisplayName("Test getCustomMybatisMapperClasses(List); when ArrayList(); then return Empty")
  void testGetCustomMybatisMapperClasses_whenArrayList_thenReturnEmpty() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange
    ProcessEngineAutoConfiguration processEngineAutoConfiguration = new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()));

    // Act and Assert
    assertTrue(processEngineAutoConfiguration.getCustomMybatisMapperClasses(new ArrayList<>()).isEmpty());
  }

  /**
   * Test {@link AbstractProcessEngineAutoConfiguration#taskExecutor()}.
   * <p>
   * Method under test:
   * {@link AbstractProcessEngineAutoConfiguration#taskExecutor()}
   */
  @Test
  @DisplayName("Test taskExecutor()")
  void testTaskExecutor() {
    //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

    // Arrange and Act
    TaskExecutor actualTaskExecutorResult = (new ProcessEngineAutoConfiguration(
        new ActivitiUserGroupManagerImpl(new ExtendedInMemoryUserDetailsManager()))).taskExecutor();
    actualTaskExecutorResult.execute(mock(Runnable.class));

    // Assert
    assertTrue(actualTaskExecutorResult instanceof SimpleAsyncTaskExecutor);
    assertEquals("SimpleAsyncTaskExecutor-",
        ((SimpleAsyncTaskExecutor) actualTaskExecutorResult).getThreadNamePrefix());
    assertNull(((SimpleAsyncTaskExecutor) actualTaskExecutorResult).getThreadGroup());
    assertNull(((SimpleAsyncTaskExecutor) actualTaskExecutorResult).getThreadFactory());
    assertEquals(-1, ((SimpleAsyncTaskExecutor) actualTaskExecutorResult).getConcurrencyLimit());
    assertEquals(5, ((SimpleAsyncTaskExecutor) actualTaskExecutorResult).getThreadPriority());
    assertFalse(((SimpleAsyncTaskExecutor) actualTaskExecutorResult).isThrottleActive());
    assertFalse(((SimpleAsyncTaskExecutor) actualTaskExecutorResult).isDaemon());
    assertTrue(((SimpleAsyncTaskExecutor) actualTaskExecutorResult).isActive());
  }
}
