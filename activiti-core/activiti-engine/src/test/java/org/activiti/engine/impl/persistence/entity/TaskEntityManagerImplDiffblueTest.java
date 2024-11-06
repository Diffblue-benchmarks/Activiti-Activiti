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
package org.activiti.engine.impl.persistence.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.TaskQueryImpl;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.DataManager;
import org.activiti.engine.impl.persistence.entity.data.TaskDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisTaskDataManager;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskEntityManagerImplDiffblueTest {
  @Mock
  private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  @Mock
  private TaskDataManager taskDataManager;

  @InjectMocks
  private TaskEntityManagerImpl taskEntityManagerImpl;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link TaskEntityManagerImpl#TaskEntityManagerImpl(ProcessEngineConfigurationImpl, TaskDataManager)}
   *   <li>{@link TaskEntityManagerImpl#setTaskDataManager(TaskDataManager)}
   *   <li>{@link TaskEntityManagerImpl#getDataManager()}
   *   <li>{@link TaskEntityManagerImpl#getTaskDataManager()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    TaskEntityManagerImpl actualTaskEntityManagerImpl = new TaskEntityManagerImpl(processEngineConfiguration,
        new MybatisTaskDataManager(new JtaProcessEngineConfiguration()));
    MybatisTaskDataManager taskDataManager = new MybatisTaskDataManager(new JtaProcessEngineConfiguration());
    actualTaskEntityManagerImpl.setTaskDataManager(taskDataManager);
    DataManager<TaskEntity> actualDataManager = actualTaskEntityManagerImpl.getDataManager();

    // Assert that nothing has changed
    assertSame(taskDataManager, actualDataManager);
    assertSame(taskDataManager, actualTaskEntityManagerImpl.getTaskDataManager());
  }

  /**
   * Test
   * {@link TaskEntityManagerImpl#deleteTasksByProcessInstanceId(String, String, boolean)}.
   * <ul>
   *   <li>Then calls
   * {@link TaskDataManager#findTasksByProcessInstanceId(String)}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#deleteTasksByProcessInstanceId(String, String, boolean)}
   */
  @Test
  public void testDeleteTasksByProcessInstanceId_thenCallsFindTasksByProcessInstanceId() {
    // Arrange
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    taskEntityManagerImpl.deleteTasksByProcessInstanceId("42", "Just cause", true);

    // Assert
    verify(taskDataManager).findTasksByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link TaskEntityManagerImpl#findTasksByExecutionId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#findTasksByExecutionId(String)}
   */
  @Test
  public void testFindTasksByExecutionId_thenReturnEmpty() {
    // Arrange
    when(taskDataManager.findTasksByExecutionId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<TaskEntity> actualFindTasksByExecutionIdResult = taskEntityManagerImpl.findTasksByExecutionId("42");

    // Assert
    verify(taskDataManager).findTasksByExecutionId(eq("42"));
    assertTrue(actualFindTasksByExecutionIdResult.isEmpty());
  }

  /**
   * Test {@link TaskEntityManagerImpl#findTasksByExecutionId(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#findTasksByExecutionId(String)}
   */
  @Test
  public void testFindTasksByExecutionId_thenThrowActivitiException() {
    // Arrange
    when(taskDataManager.findTasksByExecutionId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskEntityManagerImpl.findTasksByExecutionId("42"));
    verify(taskDataManager).findTasksByExecutionId(eq("42"));
  }

  /**
   * Test {@link TaskEntityManagerImpl#findTasksByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#findTasksByProcessInstanceId(String)}
   */
  @Test
  public void testFindTasksByProcessInstanceId_thenReturnEmpty() {
    // Arrange
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<TaskEntity> actualFindTasksByProcessInstanceIdResult = taskEntityManagerImpl
        .findTasksByProcessInstanceId("42");

    // Assert
    verify(taskDataManager).findTasksByProcessInstanceId(eq("42"));
    assertTrue(actualFindTasksByProcessInstanceIdResult.isEmpty());
  }

  /**
   * Test {@link TaskEntityManagerImpl#findTasksByProcessInstanceId(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#findTasksByProcessInstanceId(String)}
   */
  @Test
  public void testFindTasksByProcessInstanceId_thenThrowActivitiException() {
    // Arrange
    when(taskDataManager.findTasksByProcessInstanceId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskEntityManagerImpl.findTasksByProcessInstanceId("42"));
    verify(taskDataManager).findTasksByProcessInstanceId(eq("42"));
  }

  /**
   * Test {@link TaskEntityManagerImpl#findTasksByQueryCriteria(TaskQueryImpl)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#findTasksByQueryCriteria(TaskQueryImpl)}
   */
  @Test
  public void testFindTasksByQueryCriteria_thenReturnEmpty() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByQueryCriteria(Mockito.<TaskQueryImpl>any())).thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    // Act
    List<Task> actualFindTasksByQueryCriteriaResult = taskEntityManagerImpl
        .findTasksByQueryCriteria(new TaskQueryImpl());

    // Assert
    verify(taskDataManager).findTasksByQueryCriteria(isA(TaskQueryImpl.class));
    assertTrue(actualFindTasksByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test
   * {@link TaskEntityManagerImpl#findTasksAndVariablesByQueryCriteria(TaskQueryImpl)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#findTasksAndVariablesByQueryCriteria(TaskQueryImpl)}
   */
  @Test
  public void testFindTasksAndVariablesByQueryCriteria_thenReturnEmpty() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksAndVariablesByQueryCriteria(Mockito.<TaskQueryImpl>any()))
        .thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    // Act
    List<Task> actualFindTasksAndVariablesByQueryCriteriaResult = taskEntityManagerImpl
        .findTasksAndVariablesByQueryCriteria(new TaskQueryImpl());

    // Assert
    verify(taskDataManager).findTasksAndVariablesByQueryCriteria(isA(TaskQueryImpl.class));
    assertTrue(actualFindTasksAndVariablesByQueryCriteriaResult.isEmpty());
  }

  /**
   * Test
   * {@link TaskEntityManagerImpl#findTaskCountByQueryCriteria(TaskQueryImpl)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#findTaskCountByQueryCriteria(TaskQueryImpl)}
   */
  @Test
  public void testFindTaskCountByQueryCriteria_thenReturnThree() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTaskCountByQueryCriteria(Mockito.<TaskQueryImpl>any())).thenReturn(3L);
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    // Act
    long actualFindTaskCountByQueryCriteriaResult = taskEntityManagerImpl
        .findTaskCountByQueryCriteria(new TaskQueryImpl());

    // Assert
    verify(taskDataManager).findTaskCountByQueryCriteria(isA(TaskQueryImpl.class));
    assertEquals(3L, actualFindTaskCountByQueryCriteriaResult);
  }

  /**
   * Test {@link TaskEntityManagerImpl#findTasksByNativeQuery(Map, int, int)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#findTasksByNativeQuery(Map, int, int)}
   */
  @Test
  public void testFindTasksByNativeQuery_thenReturnEmpty() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTasksByNativeQuery(Mockito.<Map<String, Object>>any(), anyInt(), anyInt()))
        .thenReturn(new ArrayList<>());
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    // Act
    List<Task> actualFindTasksByNativeQueryResult = taskEntityManagerImpl.findTasksByNativeQuery(new HashMap<>(), 1, 3);

    // Assert
    verify(taskDataManager).findTasksByNativeQuery(isA(Map.class), eq(1), eq(3));
    assertTrue(actualFindTasksByNativeQueryResult.isEmpty());
  }

  /**
   * Test {@link TaskEntityManagerImpl#findTaskCountByNativeQuery(Map)}.
   * <ul>
   *   <li>Then return three.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#findTaskCountByNativeQuery(Map)}
   */
  @Test
  public void testFindTaskCountByNativeQuery_thenReturnThree() {
    // Arrange
    TaskDataManager taskDataManager = mock(TaskDataManager.class);
    when(taskDataManager.findTaskCountByNativeQuery(Mockito.<Map<String, Object>>any())).thenReturn(3L);
    TaskEntityManagerImpl taskEntityManagerImpl = new TaskEntityManagerImpl(new JtaProcessEngineConfiguration(),
        taskDataManager);

    // Act
    long actualFindTaskCountByNativeQueryResult = taskEntityManagerImpl.findTaskCountByNativeQuery(new HashMap<>());

    // Assert
    verify(taskDataManager).findTaskCountByNativeQuery(isA(Map.class));
    assertEquals(3L, actualFindTaskCountByNativeQueryResult);
  }

  /**
   * Test {@link TaskEntityManagerImpl#findTasksByParentTaskId(String)}.
   * <ul>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#findTasksByParentTaskId(String)}
   */
  @Test
  public void testFindTasksByParentTaskId_thenReturnEmpty() {
    // Arrange
    when(taskDataManager.findTasksByParentTaskId(Mockito.<String>any())).thenReturn(new ArrayList<>());

    // Act
    List<Task> actualFindTasksByParentTaskIdResult = taskEntityManagerImpl.findTasksByParentTaskId("42");

    // Assert
    verify(taskDataManager).findTasksByParentTaskId(eq("42"));
    assertTrue(actualFindTasksByParentTaskIdResult.isEmpty());
  }

  /**
   * Test {@link TaskEntityManagerImpl#findTasksByParentTaskId(String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#findTasksByParentTaskId(String)}
   */
  @Test
  public void testFindTasksByParentTaskId_thenThrowActivitiException() {
    // Arrange
    when(taskDataManager.findTasksByParentTaskId(Mockito.<String>any()))
        .thenThrow(new ActivitiException("An error occurred"));

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskEntityManagerImpl.findTasksByParentTaskId("42"));
    verify(taskDataManager).findTasksByParentTaskId(eq("42"));
  }

  /**
   * Test
   * {@link TaskEntityManagerImpl#updateTaskTenantIdForDeployment(String, String)}.
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#updateTaskTenantIdForDeployment(String, String)}
   */
  @Test
  public void testUpdateTaskTenantIdForDeployment() {
    // Arrange
    doNothing().when(taskDataManager).updateTaskTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());

    // Act
    taskEntityManagerImpl.updateTaskTenantIdForDeployment("42", "42");

    // Assert
    verify(taskDataManager).updateTaskTenantIdForDeployment(eq("42"), eq("42"));
  }

  /**
   * Test
   * {@link TaskEntityManagerImpl#updateTaskTenantIdForDeployment(String, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link TaskEntityManagerImpl#updateTaskTenantIdForDeployment(String, String)}
   */
  @Test
  public void testUpdateTaskTenantIdForDeployment_thenThrowActivitiException() {
    // Arrange
    doThrow(new ActivitiException("An error occurred")).when(taskDataManager)
        .updateTaskTenantIdForDeployment(Mockito.<String>any(), Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> taskEntityManagerImpl.updateTaskTenantIdForDeployment("42", "42"));
    verify(taskDataManager).updateTaskTenantIdForDeployment(eq("42"), eq("42"));
  }
}
