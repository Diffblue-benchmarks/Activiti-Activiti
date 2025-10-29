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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.cfg.CommandExecutorImpl;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandContextInterceptor;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.HistoricTaskInstanceEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.query.QueryProperty;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricTaskInstanceQueryImplDiffblueTest {
  @InjectMocks
  private HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl;

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId() {
    // Arrange and Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceIdResult = historicTaskInstanceQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getProcessInstanceId());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  public void testProcessInstanceIdIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processInstanceIdIn(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  public void testProcessInstanceIdIn2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processInstanceIds = new ArrayList<>();
    processInstanceIds.add("Process instance id list is empty");

    // Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceIdInResult = historicTaskInstanceQueryImpl
        .processInstanceIdIn(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, historicTaskInstanceQueryImpl.getProcessInstanceIds());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceIdInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  public void testProcessInstanceIdIn3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processInstanceIds = new ArrayList<>();
    processInstanceIds.add("42");
    processInstanceIds.add("Process instance id list is empty");

    // Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceIdInResult = historicTaskInstanceQueryImpl
        .processInstanceIdIn(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, historicTaskInstanceQueryImpl.getProcessInstanceIds());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceIdInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  public void testProcessInstanceIdIn4() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processInstanceIds = new ArrayList<>();
    processInstanceIds.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processInstanceIdIn(processInstanceIds));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceIdIn(List)}
   */
  @Test
  public void testProcessInstanceIdIn5() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    ArrayList<String> processInstanceIds = new ArrayList<>();
    processInstanceIds.add("Process instance id list is empty");

    // Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceIdInResult = historicTaskInstanceQueryImpl
        .processInstanceIdIn(processInstanceIds);

    // Assert
    assertSame(processInstanceIds, historicTaskInstanceQueryImpl.getProcessInstanceIds());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceIdInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKey(String)}
   */
  @Test
  public void testProcessInstanceBusinessKey() {
    // Arrange and Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceBusinessKeyResult = historicTaskInstanceQueryImpl
        .processInstanceBusinessKey("Process Instance Business Key");

    // Assert
    assertEquals("Process Instance Business Key", historicTaskInstanceQueryImpl.getProcessInstanceBusinessKey());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceBusinessKeyResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKeyLike(String)}
   */
  @Test
  public void testProcessInstanceBusinessKeyLike() {
    // Arrange and Act
    HistoricTaskInstanceQueryImpl actualProcessInstanceBusinessKeyLikeResult = historicTaskInstanceQueryImpl
        .processInstanceBusinessKeyLike("Process Instance Business Key Like");

    // Assert
    assertEquals("Process Instance Business Key Like",
        historicTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLike());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceBusinessKeyLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processInstanceBusinessKeyLikeIgnoreCase(String)}
   */
  @Test
  public void testProcessInstanceBusinessKeyLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessInstanceBusinessKeyLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processInstanceBusinessKeyLikeIgnoreCase("Process Instance Business Key Like Ignore Case");

    // Assert
    assertEquals("process instance business key like ignore case",
        historicTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualProcessInstanceBusinessKeyLikeIgnoreCaseResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId() {
    // Arrange and Act
    HistoricTaskInstanceQueryImpl actualExecutionIdResult = historicTaskInstanceQueryImpl.executionId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getExecutionId());
    assertSame(historicTaskInstanceQueryImpl, actualExecutionIdResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId() {
    // Arrange and Act
    HistoricTaskInstanceQueryImpl actualProcessDefinitionIdResult = historicTaskInstanceQueryImpl
        .processDefinitionId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getProcessDefinitionId());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKey(String)}
   */
  @Test
  public void testProcessDefinitionKey() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyResult = historicTaskInstanceQueryImpl
        .processDefinitionKey("Process Definition Key");

    // Assert
    assertEquals("Process Definition Key", historicTaskInstanceQueryImpl.getProcessDefinitionKey());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyLike(String)}
   */
  @Test
  public void testProcessDefinitionKeyLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyLikeResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyLike("Process Definition Key Like");

    // Assert
    assertEquals("Process Definition Key Like", historicTaskInstanceQueryImpl.getProcessDefinitionKeyLike());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyLikeIgnoreCase(String)}
   */
  @Test
  public void testProcessDefinitionKeyLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyLikeIgnoreCase("Process Definition Key Like Ignore Case");

    // Assert
    assertEquals("process definition key like ignore case",
        historicTaskInstanceQueryImpl.getProcessDefinitionKeyLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyLikeIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  public void testProcessDefinitionKeyIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    ArrayList<String> processDefinitionKeys = new ArrayList<>();

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyInResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicTaskInstanceQueryImpl.getProcessDefinitionKeys());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  public void testProcessDefinitionKeyIn2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processDefinitionKeys = new ArrayList<>();
    processDefinitionKeys.add("foo");

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyInResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicTaskInstanceQueryImpl.getProcessDefinitionKeys());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  public void testProcessDefinitionKeyIn3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processDefinitionKeys = new ArrayList<>();
    processDefinitionKeys.add("42");
    processDefinitionKeys.add("foo");

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyInResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicTaskInstanceQueryImpl.getProcessDefinitionKeys());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionKeyIn(List)}
   */
  @Test
  public void testProcessDefinitionKeyIn4() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));
    ArrayList<String> processDefinitionKeys = new ArrayList<>();

    // Act
    HistoricTaskInstanceQuery actualProcessDefinitionKeyInResult = historicTaskInstanceQueryImpl
        .processDefinitionKeyIn(processDefinitionKeys);

    // Assert
    assertSame(processDefinitionKeys, historicTaskInstanceQueryImpl.getProcessDefinitionKeys());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionKeyInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionName(String)}
   */
  @Test
  public void testProcessDefinitionName() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessDefinitionNameResult = historicTaskInstanceQueryImpl
        .processDefinitionName("Process Definition Name");

    // Assert
    assertEquals("Process Definition Name", historicTaskInstanceQueryImpl.getProcessDefinitionName());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionNameResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processDefinitionNameLike(String)}
   */
  @Test
  public void testProcessDefinitionNameLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessDefinitionNameLikeResult = historicTaskInstanceQueryImpl
        .processDefinitionNameLike("Process Definition Name Like");

    // Assert
    assertEquals("Process Definition Name Like", historicTaskInstanceQueryImpl.getProcessDefinitionNameLike());
    assertSame(historicTaskInstanceQueryImpl, actualProcessDefinitionNameLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
  public void testProcessCategoryIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processCategoryIn(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
  public void testProcessCategoryIn2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryInList = new ArrayList<>();
    processCategoryInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryInResult = historicTaskInstanceQueryImpl
        .processCategoryIn(processCategoryInList);

    // Assert
    assertSame(processCategoryInList, historicTaskInstanceQueryImpl.getProcessCategoryInList());
    assertSame(historicTaskInstanceQueryImpl, actualProcessCategoryInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
  public void testProcessCategoryIn3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryInList = new ArrayList<>();
    processCategoryInList.add("42");
    processCategoryInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryInResult = historicTaskInstanceQueryImpl
        .processCategoryIn(processCategoryInList);

    // Assert
    assertSame(processCategoryInList, historicTaskInstanceQueryImpl.getProcessCategoryInList());
    assertSame(historicTaskInstanceQueryImpl, actualProcessCategoryInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
  public void testProcessCategoryIn4() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryInList = new ArrayList<>();
    processCategoryInList.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processCategoryIn(processCategoryInList));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryIn(List)}
   */
  @Test
  public void testProcessCategoryIn5() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    ArrayList<String> processCategoryInList = new ArrayList<>();
    processCategoryInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryInResult = historicTaskInstanceQueryImpl
        .processCategoryIn(processCategoryInList);

    // Assert
    assertSame(processCategoryInList, historicTaskInstanceQueryImpl.getProcessCategoryInList());
    assertSame(historicTaskInstanceQueryImpl, actualProcessCategoryInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  public void testProcessCategoryNotIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processCategoryNotIn(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  public void testProcessCategoryNotIn2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryNotInList = new ArrayList<>();
    processCategoryNotInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryNotInResult = historicTaskInstanceQueryImpl
        .processCategoryNotIn(processCategoryNotInList);

    // Assert
    assertSame(processCategoryNotInList, historicTaskInstanceQueryImpl.getProcessCategoryNotInList());
    assertSame(historicTaskInstanceQueryImpl, actualProcessCategoryNotInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  public void testProcessCategoryNotIn3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryNotInList = new ArrayList<>();
    processCategoryNotInList.add("42");
    processCategoryNotInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryNotInResult = historicTaskInstanceQueryImpl
        .processCategoryNotIn(processCategoryNotInList);

    // Assert
    assertSame(processCategoryNotInList, historicTaskInstanceQueryImpl.getProcessCategoryNotInList());
    assertSame(historicTaskInstanceQueryImpl, actualProcessCategoryNotInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  public void testProcessCategoryNotIn4() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> processCategoryNotInList = new ArrayList<>();
    processCategoryNotInList.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.processCategoryNotIn(processCategoryNotInList));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processCategoryNotIn(List)}
   */
  @Test
  public void testProcessCategoryNotIn5() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    ArrayList<String> processCategoryNotInList = new ArrayList<>();
    processCategoryNotInList.add("Process category list is empty");

    // Act
    HistoricTaskInstanceQuery actualProcessCategoryNotInResult = historicTaskInstanceQueryImpl
        .processCategoryNotIn(processCategoryNotInList);

    // Assert
    assertSame(processCategoryNotInList, historicTaskInstanceQueryImpl.getProcessCategoryNotInList());
    assertSame(historicTaskInstanceQueryImpl, actualProcessCategoryNotInResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#deploymentId(String)}
   */
  @Test
  public void testDeploymentId() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualDeploymentIdResult = historicTaskInstanceQueryImpl.deploymentId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getDeploymentId());
    assertSame(historicTaskInstanceQueryImpl, actualDeploymentIdResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  public void testDeploymentIdIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    ArrayList<String> deploymentIds = new ArrayList<>();

    // Act
    HistoricTaskInstanceQuery actualDeploymentIdInResult = historicTaskInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, historicTaskInstanceQueryImpl.getDeploymentIds());
    assertSame(historicTaskInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  public void testDeploymentIdIn2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("foo");

    // Act
    HistoricTaskInstanceQuery actualDeploymentIdInResult = historicTaskInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, historicTaskInstanceQueryImpl.getDeploymentIds());
    assertSame(historicTaskInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  public void testDeploymentIdIn3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> deploymentIds = new ArrayList<>();
    deploymentIds.add("42");
    deploymentIds.add("foo");

    // Act
    HistoricTaskInstanceQuery actualDeploymentIdInResult = historicTaskInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, historicTaskInstanceQueryImpl.getDeploymentIds());
    assertSame(historicTaskInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#deploymentIdIn(List)}
   */
  @Test
  public void testDeploymentIdIn4() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));
    ArrayList<String> deploymentIds = new ArrayList<>();

    // Act
    HistoricTaskInstanceQuery actualDeploymentIdInResult = historicTaskInstanceQueryImpl.deploymentIdIn(deploymentIds);

    // Assert
    assertSame(deploymentIds, historicTaskInstanceQueryImpl.getDeploymentIds());
    assertSame(historicTaskInstanceQueryImpl, actualDeploymentIdInResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskId(String)}
   */
  @Test
  public void testTaskId() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskIdResult = historicTaskInstanceQueryImpl.taskId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getTaskId());
    assertSame(historicTaskInstanceQueryImpl, actualTaskIdResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskName(String)}
   */
  @Test
  public void testTaskName() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskNameResult = historicTaskInstanceQueryImpl.taskName("Task Name");

    // Assert
    assertEquals("Task Name", historicTaskInstanceQueryImpl.getTaskName());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  public void testTaskNameIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameIn(new ArrayList<>()));
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  public void testTaskNameIn2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskNameInResult = historicTaskInstanceQueryImpl.taskNameIn(taskNameList);

    // Assert
    assertSame(taskNameList, historicTaskInstanceQueryImpl.getTaskNameList());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameInResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  public void testTaskNameIn3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("42");
    taskNameList.add("Task name list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskNameInResult = historicTaskInstanceQueryImpl.taskNameIn(taskNameList);

    // Assert
    assertSame(taskNameList, historicTaskInstanceQueryImpl.getTaskNameList());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameInResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  public void testTaskNameIn4() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskNameInResult = historicTaskInstanceQueryImpl.taskNameIn(taskNameList);

    // Assert
    assertSame(taskNameList, historicTaskInstanceQueryImpl.getTaskNameList());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameInResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  public void testTaskNameIn5() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskName("Task Name");

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskNameIn(taskNameList));
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  public void testTaskNameIn6() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskNameLike("Task Name Like");

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskNameIn(taskNameList));
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameIn(List)}
   */
  @Test
  public void testTaskNameIn7() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskNameLikeIgnoreCase("Task Name Like Ignore Case");

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskNameIn(taskNameList));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  public void testTaskNameInIgnoreCase() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameInIgnoreCase(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  public void testTaskNameInIgnoreCase2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  public void testTaskNameInIgnoreCase3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("42");
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  public void testTaskNameInIgnoreCase4() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  public void testTaskNameInIgnoreCase5() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  public void testTaskNameInIgnoreCase6() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskName("Task Name");

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  public void testTaskNameInIgnoreCase7() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskNameLike("Task Name Like");

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameInIgnoreCase(List)}
   */
  @Test
  public void testTaskNameInIgnoreCase8() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskNameLikeIgnoreCase("Task Name Like Ignore Case");

    ArrayList<String> taskNameList = new ArrayList<>();
    taskNameList.add("Task name list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskNameInIgnoreCase(taskNameList));
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskNameLike(String)}
   */
  @Test
  public void testTaskNameLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskNameLikeResult = historicTaskInstanceQueryImpl.taskNameLike("Task Name Like");

    // Assert
    assertEquals("Task Name Like", historicTaskInstanceQueryImpl.getTaskNameLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskNameLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskNameLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskNameLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskNameLikeIgnoreCase("Task Name Like Ignore Case");

    // Assert
    assertEquals("task name like ignore case", historicTaskInstanceQueryImpl.getTaskNameLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualTaskNameLikeIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskParentTaskId(String)}
   */
  @Test
  public void testTaskParentTaskId() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskParentTaskIdResult = historicTaskInstanceQueryImpl.taskParentTaskId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getTaskParentTaskId());
    assertSame(historicTaskInstanceQueryImpl, actualTaskParentTaskIdResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDescription(String)}
   */
  @Test
  public void testTaskDescription() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDescriptionResult = historicTaskInstanceQueryImpl
        .taskDescription("Task Description");

    // Assert
    assertEquals("Task Description", historicTaskInstanceQueryImpl.getTaskDescription());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDescriptionResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDescriptionLike(String)}
   */
  @Test
  public void testTaskDescriptionLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDescriptionLikeResult = historicTaskInstanceQueryImpl
        .taskDescriptionLike("Task Description Like");

    // Assert
    assertEquals("Task Description Like", historicTaskInstanceQueryImpl.getTaskDescriptionLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDescriptionLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDescriptionLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskDescriptionLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDescriptionLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskDescriptionLikeIgnoreCase("Task Description Like Ignore Case");

    // Assert
    assertEquals("task description like ignore case", historicTaskInstanceQueryImpl.getTaskDescriptionLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDescriptionLikeIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDeleteReason(String)}
   */
  @Test
  public void testTaskDeleteReason() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDeleteReasonResult = historicTaskInstanceQueryImpl
        .taskDeleteReason("Just cause");

    // Assert
    assertEquals("Just cause", historicTaskInstanceQueryImpl.getTaskDeleteReason());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDeleteReasonResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDeleteReasonLike(String)}
   */
  @Test
  public void testTaskDeleteReasonLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDeleteReasonLikeResult = historicTaskInstanceQueryImpl
        .taskDeleteReasonLike("Just cause");

    // Assert
    assertEquals("Just cause", historicTaskInstanceQueryImpl.getTaskDeleteReasonLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDeleteReasonLikeResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskAssignee(String)}
   */
  @Test
  public void testTaskAssignee() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskAssigneeResult = historicTaskInstanceQueryImpl.taskAssignee("Task Assignee");

    // Assert
    assertEquals("Task Assignee", historicTaskInstanceQueryImpl.getTaskAssignee());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeLike(String)}
   */
  @Test
  public void testTaskAssigneeLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskAssigneeLikeResult = historicTaskInstanceQueryImpl
        .taskAssigneeLike("Task Assignee Like");

    // Assert
    assertEquals("Task Assignee Like", historicTaskInstanceQueryImpl.getTaskAssigneeLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskAssigneeLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskAssigneeLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskAssigneeLikeIgnoreCase("Task Assignee Like Ignore Case");

    // Assert
    assertEquals("task assignee like ignore case", historicTaskInstanceQueryImpl.getTaskAssigneeLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeLikeIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  public void testTaskAssigneeIds() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskAssigneeIds(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  public void testTaskAssigneeIds2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskAssigneeIdsResult = historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds);

    // Assert
    assertSame(assigneeIds, historicTaskInstanceQueryImpl.getTaskAssigneeIds());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeIdsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  public void testTaskAssigneeIds3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("42");
    assigneeIds.add("Task assignee list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskAssigneeIdsResult = historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds);

    // Assert
    assertSame(assigneeIds, historicTaskInstanceQueryImpl.getTaskAssigneeIds());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeIdsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  public void testTaskAssigneeIds4() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add(null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  public void testTaskAssigneeIds5() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskAssigneeIdsResult = historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds);

    // Assert
    assertSame(assigneeIds, historicTaskInstanceQueryImpl.getTaskAssigneeIds());
    assertSame(historicTaskInstanceQueryImpl, actualTaskAssigneeIdsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  public void testTaskAssigneeIds6() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskAssignee("Task Assignee");

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  public void testTaskAssigneeIds7() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskAssigneeLike("Task Assignee Like");

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskAssigneeIds(List)}
   */
  @Test
  public void testTaskAssigneeIds8() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskAssigneeLikeIgnoreCase("Task Assignee Like Ignore Case");

    ArrayList<String> assigneeIds = new ArrayList<>();
    assigneeIds.add("Task assignee list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskAssigneeIds(assigneeIds));
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskOwner(String)}
   */
  @Test
  public void testTaskOwner() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskOwnerResult = historicTaskInstanceQueryImpl.taskOwner("Task Owner");

    // Assert
    assertEquals("Task Owner", historicTaskInstanceQueryImpl.getTaskOwner());
    assertSame(historicTaskInstanceQueryImpl, actualTaskOwnerResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskOwnerLike(String)}
   */
  @Test
  public void testTaskOwnerLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskOwnerLikeResult = historicTaskInstanceQueryImpl
        .taskOwnerLike("Task Owner Like");

    // Assert
    assertEquals("Task Owner Like", historicTaskInstanceQueryImpl.getTaskOwnerLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskOwnerLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskOwnerLikeIgnoreCase(String)}
   */
  @Test
  public void testTaskOwnerLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskOwnerLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskOwnerLikeIgnoreCase("Task Owner Like Ignore Case");

    // Assert
    assertEquals("task owner like ignore case", historicTaskInstanceQueryImpl.getTaskOwnerLikeIgnoreCase());
    assertSame(historicTaskInstanceQueryImpl, actualTaskOwnerLikeIgnoreCaseResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#finished()}
   */
  @Test
  public void testFinished() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualFinishedResult = historicTaskInstanceQueryImpl.finished();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isFinished());
    assertSame(historicTaskInstanceQueryImpl, actualFinishedResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#finished()}
   */
  @Test
  public void testFinished2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualFinishedResult = historicTaskInstanceQueryImpl.finished();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isFinished());
    assertSame(historicTaskInstanceQueryImpl, actualFinishedResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#unfinished()}
   */
  @Test
  public void testUnfinished() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualUnfinishedResult = historicTaskInstanceQueryImpl.unfinished();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isUnfinished());
    assertSame(historicTaskInstanceQueryImpl, actualUnfinishedResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#unfinished()}
   */
  @Test
  public void testUnfinished2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualUnfinishedResult = historicTaskInstanceQueryImpl.unfinished();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isUnfinished());
    assertSame(historicTaskInstanceQueryImpl, actualUnfinishedResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(Object)}
   */
  @Test
  public void testTaskVariableValueEquals() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(Object)}
   */
  @Test
  public void testTaskVariableValueEquals2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)}
   */
  @Test
  public void testTaskVariableValueEquals3() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)}
   */
  @Test
  public void testTaskVariableValueEquals4() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueEquals("Variable Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEquals(String, Object)}
   */
  @Test
  public void testTaskVariableValueEquals5() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueEquals("Variable Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testTaskVariableValueEqualsIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskVariableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testTaskVariableValueNotEqualsIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueNotEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskVariableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testTaskVariableValueNotEquals() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testTaskVariableValueNotEquals2() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueNotEquals("Variable Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testTaskVariableValueNotEquals3() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .taskVariableValueNotEquals("Variable Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueGreaterThan(String, Object)}
   */
  @Test
  public void testTaskVariableValueGreaterThan() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueGreaterThanResult = historicTaskInstanceQueryImpl
        .taskVariableValueGreaterThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueGreaterThanResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueGreaterThan(String, Object)}
   */
  @Test
  public void testTaskVariableValueGreaterThan2() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueGreaterThanResult = historicTaskInstanceQueryImpl
        .taskVariableValueGreaterThan("Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueGreaterThanResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testTaskVariableValueGreaterThanOrEqual() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueGreaterThanOrEqualResult = historicTaskInstanceQueryImpl
        .taskVariableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testTaskVariableValueGreaterThanOrEqual2() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueGreaterThanOrEqualResult = historicTaskInstanceQueryImpl
        .taskVariableValueGreaterThanOrEqual("Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueLessThan(String, Object)}
   */
  @Test
  public void testTaskVariableValueLessThan() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueLessThanResult = historicTaskInstanceQueryImpl
        .taskVariableValueLessThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueLessThanResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueLessThan(String, Object)}
   */
  @Test
  public void testTaskVariableValueLessThan2() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueLessThanResult = historicTaskInstanceQueryImpl
        .taskVariableValueLessThan("Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueLessThanResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testTaskVariableValueLessThanOrEqual() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueLessThanOrEqualResult = historicTaskInstanceQueryImpl
        .taskVariableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueLessThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testTaskVariableValueLessThanOrEqual2() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualTaskVariableValueLessThanOrEqualResult = historicTaskInstanceQueryImpl
        .taskVariableValueLessThanOrEqual("Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueLessThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueLike(String, String)}
   */
  @Test
  public void testTaskVariableValueLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueLikeResult = historicTaskInstanceQueryImpl
        .taskVariableValueLike("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskVariableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testTaskVariableValueLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskVariableValueLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .taskVariableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(Object)}
   */
  @Test
  public void testProcessVariableValueEquals() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(Object)}
   */
  @Test
  public void testProcessVariableValueEquals2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueEquals3() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueEquals4() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueEquals("Variable Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueEquals5() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueEquals("Variable Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueNotEquals() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueNotEquals2() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueNotEquals("Variable Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEquals(String, Object)}
   */
  @Test
  public void testProcessVariableValueNotEquals3() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .processVariableValueNotEquals("Variable Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testProcessVariableValueEqualsIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processVariableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testProcessVariableValueNotEqualsIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueNotEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processVariableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThan(String, Object)}
   */
  @Test
  public void testProcessVariableValueGreaterThan() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueGreaterThanResult = historicTaskInstanceQueryImpl
        .processVariableValueGreaterThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueGreaterThanResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThan(String, Object)}
   */
  @Test
  public void testProcessVariableValueGreaterThan2() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueGreaterThanResult = historicTaskInstanceQueryImpl
        .processVariableValueGreaterThan("Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueGreaterThanResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testProcessVariableValueGreaterThanOrEqual() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueGreaterThanOrEqualResult = historicTaskInstanceQueryImpl
        .processVariableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testProcessVariableValueGreaterThanOrEqual2() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueGreaterThanOrEqualResult = historicTaskInstanceQueryImpl
        .processVariableValueGreaterThanOrEqual("Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThan(String, Object)}
   */
  @Test
  public void testProcessVariableValueLessThan() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueLessThanResult = historicTaskInstanceQueryImpl
        .processVariableValueLessThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLessThanResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThan(String, Object)}
   */
  @Test
  public void testProcessVariableValueLessThan2() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueLessThanResult = historicTaskInstanceQueryImpl
        .processVariableValueLessThan("Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLessThanResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testProcessVariableValueLessThanOrEqual() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueLessThanOrEqualResult = historicTaskInstanceQueryImpl
        .processVariableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLessThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testProcessVariableValueLessThanOrEqual2() {
    // Arrange
    CommandConfig defaultConfig = mock(CommandConfig.class);

    // Act
    HistoricTaskInstanceQuery actualProcessVariableValueLessThanOrEqualResult = historicTaskInstanceQueryImpl
        .processVariableValueLessThanOrEqual("Name",
            new HistoricTaskInstanceQueryImpl(new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor())));

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLessThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLike(String, String)}
   */
  @Test
  public void testProcessVariableValueLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueLikeResult = historicTaskInstanceQueryImpl
        .processVariableValueLike("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#processVariableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testProcessVariableValueLikeIgnoreCase() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualProcessVariableValueLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .processVariableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualProcessVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDefinitionKey(String)}
   */
  @Test
  public void testTaskDefinitionKey() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDefinitionKeyResult = historicTaskInstanceQueryImpl
        .taskDefinitionKey("Task Definition Key");

    // Assert
    assertEquals("Task Definition Key", historicTaskInstanceQueryImpl.getTaskDefinitionKey());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDefinitionKeyResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDefinitionKeyLike(String)}
   */
  @Test
  public void testTaskDefinitionKeyLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskDefinitionKeyLikeResult = historicTaskInstanceQueryImpl
        .taskDefinitionKeyLike("Task Definition Key Like");

    // Assert
    assertEquals("Task Definition Key Like", historicTaskInstanceQueryImpl.getTaskDefinitionKeyLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskDefinitionKeyLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskPriority(Integer)}
   */
  @Test
  public void testTaskPriority() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskPriorityResult = historicTaskInstanceQueryImpl.taskPriority(1);

    // Assert
    assertEquals(1, historicTaskInstanceQueryImpl.getTaskPriority().intValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskPriorityResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskPriority(Integer)}
   */
  @Test
  public void testTaskPriority2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualTaskPriorityResult = historicTaskInstanceQueryImpl.taskPriority(1);

    // Assert
    assertEquals(1, historicTaskInstanceQueryImpl.getTaskPriority().intValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskPriorityResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskMinPriority(Integer)}
   */
  @Test
  public void testTaskMinPriority() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskMinPriorityResult = historicTaskInstanceQueryImpl.taskMinPriority(1);

    // Assert
    assertEquals(1, historicTaskInstanceQueryImpl.getTaskMinPriority().intValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskMinPriorityResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskMinPriority(Integer)}
   */
  @Test
  public void testTaskMinPriority2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualTaskMinPriorityResult = historicTaskInstanceQueryImpl.taskMinPriority(1);

    // Assert
    assertEquals(1, historicTaskInstanceQueryImpl.getTaskMinPriority().intValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskMinPriorityResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskMaxPriority(Integer)}
   */
  @Test
  public void testTaskMaxPriority() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskMaxPriorityResult = historicTaskInstanceQueryImpl.taskMaxPriority(1);

    // Assert
    assertEquals(1, historicTaskInstanceQueryImpl.getTaskMaxPriority().intValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskMaxPriorityResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskMaxPriority(Integer)}
   */
  @Test
  public void testTaskMaxPriority2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualTaskMaxPriorityResult = historicTaskInstanceQueryImpl.taskMaxPriority(1);

    // Assert
    assertEquals(1, historicTaskInstanceQueryImpl.getTaskMaxPriority().intValue());
    assertSame(historicTaskInstanceQueryImpl, actualTaskMaxPriorityResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processFinished()}
   */
  @Test
  public void testProcessFinished() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessFinishedResult = historicTaskInstanceQueryImpl.processFinished();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isProcessFinished());
    assertSame(historicTaskInstanceQueryImpl, actualProcessFinishedResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processFinished()}
   */
  @Test
  public void testProcessFinished2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualProcessFinishedResult = historicTaskInstanceQueryImpl.processFinished();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isProcessFinished());
    assertSame(historicTaskInstanceQueryImpl, actualProcessFinishedResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processUnfinished()}
   */
  @Test
  public void testProcessUnfinished() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualProcessUnfinishedResult = historicTaskInstanceQueryImpl.processUnfinished();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isProcessUnfinished());
    assertSame(historicTaskInstanceQueryImpl, actualProcessUnfinishedResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#processUnfinished()}
   */
  @Test
  public void testProcessUnfinished2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualProcessUnfinishedResult = historicTaskInstanceQueryImpl.processUnfinished();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isProcessUnfinished());
    assertSame(historicTaskInstanceQueryImpl, actualProcessUnfinishedResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDueDate(Date)}
   */
  @Test
  public void testTaskDueDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date dueDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskDueDate(dueDate));
    assertSame(dueDate, historicTaskInstanceQueryImpl.getDueDate());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDueDate(java.util.Date)}
   */
  @Test
  public void testTaskDueDate2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date dueDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskDueDate(dueDate));
    assertSame(dueDate, historicTaskInstanceQueryImpl.getDueDate());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDueAfter(Date)}
   */
  @Test
  public void testTaskDueAfter() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date dueAfter = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskDueAfter(dueAfter));
    assertSame(dueAfter, historicTaskInstanceQueryImpl.getDueAfter());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDueAfter(java.util.Date)}
   */
  @Test
  public void testTaskDueAfter2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date dueAfter = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskDueAfter(dueAfter));
    assertSame(dueAfter, historicTaskInstanceQueryImpl.getDueAfter());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskDueBefore(Date)}
   */
  @Test
  public void testTaskDueBefore() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date dueBefore = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskDueBefore(dueBefore));
    assertSame(dueBefore, historicTaskInstanceQueryImpl.getDueBefore());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskDueBefore(java.util.Date)}
   */
  @Test
  public void testTaskDueBefore2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date dueBefore = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskDueBefore(dueBefore));
    assertSame(dueBefore, historicTaskInstanceQueryImpl.getDueBefore());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCreatedOn(Date)}
   */
  @Test
  public void testTaskCreatedOn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date creationDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedOn(creationDate));
    assertSame(creationDate, historicTaskInstanceQueryImpl.getCreationDate());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCreatedOn(java.util.Date)}
   */
  @Test
  public void testTaskCreatedOn2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date creationDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedOn(creationDate));
    assertSame(creationDate, historicTaskInstanceQueryImpl.getCreationDate());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCreatedBefore(Date)}
   */
  @Test
  public void testTaskCreatedBefore() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date creationBeforeDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedBefore(creationBeforeDate));
    assertSame(creationBeforeDate, historicTaskInstanceQueryImpl.getCreationBeforeDate());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCreatedBefore(java.util.Date)}
   */
  @Test
  public void testTaskCreatedBefore2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date creationBeforeDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedBefore(creationBeforeDate));
    assertSame(creationBeforeDate, historicTaskInstanceQueryImpl.getCreationBeforeDate());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCreatedAfter(Date)}
   */
  @Test
  public void testTaskCreatedAfter() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date creationAfterDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedAfter(creationAfterDate));
    assertSame(creationAfterDate, historicTaskInstanceQueryImpl.getCreationAfterDate());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCreatedAfter(java.util.Date)}
   */
  @Test
  public void testTaskCreatedAfter2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date creationAfterDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCreatedAfter(creationAfterDate));
    assertSame(creationAfterDate, historicTaskInstanceQueryImpl.getCreationAfterDate());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCompletedOn(Date)}
   */
  @Test
  public void testTaskCompletedOn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date completedDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedOn(completedDate));
    assertSame(completedDate, historicTaskInstanceQueryImpl.getCompletedDate());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCompletedOn(java.util.Date)}
   */
  @Test
  public void testTaskCompletedOn2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date completedDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedOn(completedDate));
    assertSame(completedDate, historicTaskInstanceQueryImpl.getCompletedDate());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCompletedBefore(Date)}
   */
  @Test
  public void testTaskCompletedBefore() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date completedBeforeDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedBefore(completedBeforeDate));
    assertSame(completedBeforeDate, historicTaskInstanceQueryImpl.getCompletedBeforeDate());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCompletedBefore(java.util.Date)}
   */
  @Test
  public void testTaskCompletedBefore2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date completedBeforeDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedBefore(completedBeforeDate));
    assertSame(completedBeforeDate, historicTaskInstanceQueryImpl.getCompletedBeforeDate());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCompletedAfter(Date)}
   */
  @Test
  public void testTaskCompletedAfter() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    Date completedAfterDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedAfter(completedAfterDate));
    assertSame(completedAfterDate, historicTaskInstanceQueryImpl.getCompletedAfterDate());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCompletedAfter(java.util.Date)}
   */
  @Test
  public void testTaskCompletedAfter2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    java.sql.Date completedAfterDate = mock(java.sql.Date.class);

    // Act and Assert
    assertSame(historicTaskInstanceQueryImpl, historicTaskInstanceQueryImpl.taskCompletedAfter(completedAfterDate));
    assertSame(completedAfterDate, historicTaskInstanceQueryImpl.getCompletedAfterDate());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#withoutTaskDueDate()}
   */
  @Test
  public void testWithoutTaskDueDate() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualWithoutTaskDueDateResult = historicTaskInstanceQueryImpl.withoutTaskDueDate();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isWithoutDueDate());
    assertSame(historicTaskInstanceQueryImpl, actualWithoutTaskDueDateResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#withoutTaskDueDate()}
   */
  @Test
  public void testWithoutTaskDueDate2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualWithoutTaskDueDateResult = historicTaskInstanceQueryImpl.withoutTaskDueDate();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isWithoutDueDate());
    assertSame(historicTaskInstanceQueryImpl, actualWithoutTaskDueDateResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskCategory(String)}
   */
  @Test
  public void testTaskCategory() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskCategoryResult = historicTaskInstanceQueryImpl.taskCategory("Category");

    // Assert
    assertEquals("Category", historicTaskInstanceQueryImpl.getCategory());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCategoryResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String)}
   */
  @Test
  public void testTaskCandidateUser() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskCandidateUserResult = historicTaskInstanceQueryImpl
        .taskCandidateUser("2020-03-01");

    // Assert
    assertEquals("2020-03-01", historicTaskInstanceQueryImpl.getCandidateUser());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String)}
   */
  @Test
  public void testTaskCandidateUser2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskCandidateUser(null));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  public void testTaskCandidateUser3() {
    // Arrange
    ArrayList<String> usersGroups = new ArrayList<>();

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateUserResult = historicTaskInstanceQueryImpl
        .taskCandidateUser("2020-03-01", usersGroups);

    // Assert
    assertEquals("2020-03-01", historicTaskInstanceQueryImpl.getCandidateUser());
    assertSame(usersGroups, historicTaskInstanceQueryImpl.getCandidateGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  public void testTaskCandidateUser4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskCandidateUser(null, new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  public void testTaskCandidateUser5() {
    // Arrange
    ArrayList<String> usersGroups = new ArrayList<>();
    usersGroups.add("foo");

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateUserResult = historicTaskInstanceQueryImpl
        .taskCandidateUser("2020-03-01", usersGroups);

    // Assert
    assertEquals("2020-03-01", historicTaskInstanceQueryImpl.getCandidateUser());
    assertSame(usersGroups, historicTaskInstanceQueryImpl.getCandidateGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateUser(String, List)}
   */
  @Test
  public void testTaskCandidateUser6() {
    // Arrange
    ArrayList<String> usersGroups = new ArrayList<>();
    usersGroups.add("42");
    usersGroups.add("foo");

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateUserResult = historicTaskInstanceQueryImpl
        .taskCandidateUser("2020-03-01", usersGroups);

    // Assert
    assertEquals("2020-03-01", historicTaskInstanceQueryImpl.getCandidateUser());
    assertSame(usersGroups, historicTaskInstanceQueryImpl.getCandidateGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateUserResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroup(String)}
   */
  @Test
  public void testTaskCandidateGroup() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskCandidateGroupResult = historicTaskInstanceQueryImpl
        .taskCandidateGroup("2020-03-01");

    // Assert
    List<String> candidateGroups = historicTaskInstanceQueryImpl.getCandidateGroups();
    assertEquals(1, candidateGroups.size());
    assertEquals("2020-03-01", candidateGroups.get(0));
    assertEquals("2020-03-01", historicTaskInstanceQueryImpl.getCandidateGroup());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateGroupResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroup(String)}
   */
  @Test
  public void testTaskCandidateGroup2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskCandidateGroup(null));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  public void testTaskCandidateGroupIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskCandidateGroupIn(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  public void testTaskCandidateGroupIn2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("Candidate group list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateGroupInResult = historicTaskInstanceQueryImpl
        .taskCandidateGroupIn(candidateGroups);

    // Assert
    assertSame(candidateGroups, historicTaskInstanceQueryImpl.getCandidateGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateGroupInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  public void testTaskCandidateGroupIn3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("42");
    candidateGroups.add("Candidate group list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateGroupInResult = historicTaskInstanceQueryImpl
        .taskCandidateGroupIn(candidateGroups);

    // Assert
    assertSame(candidateGroups, historicTaskInstanceQueryImpl.getCandidateGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateGroupInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  public void testTaskCandidateGroupIn4() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("Candidate group list is empty");

    // Act
    HistoricTaskInstanceQuery actualTaskCandidateGroupInResult = historicTaskInstanceQueryImpl
        .taskCandidateGroupIn(candidateGroups);

    // Assert
    assertSame(candidateGroups, historicTaskInstanceQueryImpl.getCandidateGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskCandidateGroupInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskCandidateGroupIn(List)}
   */
  @Test
  public void testTaskCandidateGroupIn5() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskCandidateGroup("2020-03-01");

    ArrayList<String> candidateGroups = new ArrayList<>();
    candidateGroups.add("Candidate group list is empty");

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskCandidateGroupIn(candidateGroups));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskInvolvedUser(String)}
   */
  @Test
  public void testTaskInvolvedUser() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskInvolvedUserResult = historicTaskInstanceQueryImpl
        .taskInvolvedUser("Involved User");

    // Assert
    assertEquals("Involved User", historicTaskInstanceQueryImpl.getInvolvedUser());
    assertSame(historicTaskInstanceQueryImpl, actualTaskInvolvedUserResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  public void testTaskInvolvedGroupsIn() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.taskInvolvedGroupsIn(new ArrayList<>()));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  public void testTaskInvolvedGroupsIn2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    HistoricTaskInstanceQuery actualTaskInvolvedGroupsInResult = historicTaskInstanceQueryImpl
        .taskInvolvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, historicTaskInstanceQueryImpl.getInvolvedGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskInvolvedGroupsInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  public void testTaskInvolvedGroupsIn3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("42");
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    HistoricTaskInstanceQuery actualTaskInvolvedGroupsInResult = historicTaskInstanceQueryImpl
        .taskInvolvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, historicTaskInstanceQueryImpl.getInvolvedGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskInvolvedGroupsInResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskInvolvedGroupsIn(List)}
   */
  @Test
  public void testTaskInvolvedGroupsIn4() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    ArrayList<String> involvedGroups = new ArrayList<>();
    involvedGroups.add("Involved groups list is null or empty.");

    // Act
    HistoricTaskInstanceQuery actualTaskInvolvedGroupsInResult = historicTaskInstanceQueryImpl
        .taskInvolvedGroupsIn(involvedGroups);

    // Assert
    assertSame(involvedGroups, historicTaskInstanceQueryImpl.getInvolvedGroups());
    assertSame(historicTaskInstanceQueryImpl, actualTaskInvolvedGroupsInResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskTenantId(String)}
   */
  @Test
  public void testTaskTenantId() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskTenantIdResult = historicTaskInstanceQueryImpl.taskTenantId("42");

    // Assert
    assertEquals("42", historicTaskInstanceQueryImpl.getTenantId());
    assertSame(historicTaskInstanceQueryImpl, actualTaskTenantIdResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#taskTenantId(String)}
   */
  @Test
  public void testTaskTenantId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskTenantId(null));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskTenantIdLike(String)}
   */
  @Test
  public void testTaskTenantIdLike() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualTaskTenantIdLikeResult = historicTaskInstanceQueryImpl
        .taskTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", historicTaskInstanceQueryImpl.getTenantIdLike());
    assertSame(historicTaskInstanceQueryImpl, actualTaskTenantIdLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskTenantIdLike(String)}
   */
  @Test
  public void testTaskTenantIdLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.taskTenantIdLike(null));
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskWithoutTenantId()}
   */
  @Test
  public void testTaskWithoutTenantId() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualTaskWithoutTenantIdResult = historicTaskInstanceQueryImpl.taskWithoutTenantId();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isWithoutTenantId());
    assertSame(historicTaskInstanceQueryImpl, actualTaskWithoutTenantIdResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#taskWithoutTenantId()}
   */
  @Test
  public void testTaskWithoutTenantId2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualTaskWithoutTenantIdResult = historicTaskInstanceQueryImpl.taskWithoutTenantId();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isWithoutTenantId());
    assertSame(historicTaskInstanceQueryImpl, actualTaskWithoutTenantIdResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#locale(String)}
   */
  @Test
  public void testLocale() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualLocaleResult = historicTaskInstanceQueryImpl.locale("en");

    // Assert
    assertEquals("en", historicTaskInstanceQueryImpl.getLocale());
    assertSame(historicTaskInstanceQueryImpl, actualLocaleResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#includeTaskLocalVariables()}
   */
  @Test
  public void testIncludeTaskLocalVariables() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualIncludeTaskLocalVariablesResult = historicTaskInstanceQueryImpl
        .includeTaskLocalVariables();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isIncludeTaskLocalVariables());
    assertSame(historicTaskInstanceQueryImpl, actualIncludeTaskLocalVariablesResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#includeTaskLocalVariables()}
   */
  @Test
  public void testIncludeTaskLocalVariables2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualIncludeTaskLocalVariablesResult = historicTaskInstanceQueryImpl
        .includeTaskLocalVariables();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isIncludeTaskLocalVariables());
    assertSame(historicTaskInstanceQueryImpl, actualIncludeTaskLocalVariablesResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#includeProcessVariables()}
   */
  @Test
  public void testIncludeProcessVariables() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualIncludeProcessVariablesResult = historicTaskInstanceQueryImpl
        .includeProcessVariables();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isIncludeProcessVariables());
    assertSame(historicTaskInstanceQueryImpl, actualIncludeProcessVariablesResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#includeProcessVariables()}
   */
  @Test
  public void testIncludeProcessVariables2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualIncludeProcessVariablesResult = historicTaskInstanceQueryImpl
        .includeProcessVariables();

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.isIncludeProcessVariables());
    assertSame(historicTaskInstanceQueryImpl, actualIncludeProcessVariablesResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#limitTaskVariables(Integer)}
   */
  @Test
  public void testLimitTaskVariables() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualLimitTaskVariablesResult = historicTaskInstanceQueryImpl.limitTaskVariables(1);

    // Assert
    assertEquals(1, historicTaskInstanceQueryImpl.getTaskVariablesLimit().intValue());
    assertSame(historicTaskInstanceQueryImpl, actualLimitTaskVariablesResult);
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#limitTaskVariables(Integer)}
   */
  @Test
  public void testLimitTaskVariables2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualLimitTaskVariablesResult = historicTaskInstanceQueryImpl.limitTaskVariables(1);

    // Assert
    assertEquals(1, historicTaskInstanceQueryImpl.getTaskVariablesLimit().intValue());
    assertSame(historicTaskInstanceQueryImpl, actualLimitTaskVariablesResult);
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#or()}
   */
  @Test
  public void testOr() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualOrResult = historicTaskInstanceQueryImpl.or();

    // Assert
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl2 = historicTaskInstanceQueryImpl.currentOrQueryObject;
    assertEquals("RES.ID_ asc", historicTaskInstanceQueryImpl2.getOrderBy());
    assertEquals("RES.ID_ asc", historicTaskInstanceQueryImpl2.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", historicTaskInstanceQueryImpl2.getMssqlOrDB2OrderBy());
    assertNull(historicTaskInstanceQueryImpl2.getTaskMaxPriority());
    assertNull(historicTaskInstanceQueryImpl2.getTaskMinPriority());
    assertNull(historicTaskInstanceQueryImpl2.getTaskPriority());
    assertNull(historicTaskInstanceQueryImpl2.getTaskVariablesLimit());
    assertNull(historicTaskInstanceQueryImpl2.getDatabaseType());
    assertNull(historicTaskInstanceQueryImpl2.getCandidateGroup());
    assertNull(historicTaskInstanceQueryImpl2.getCandidateUser());
    assertNull(historicTaskInstanceQueryImpl2.getCategory());
    assertNull(historicTaskInstanceQueryImpl2.getDeploymentId());
    assertNull(historicTaskInstanceQueryImpl2.getExecutionId());
    assertNull(historicTaskInstanceQueryImpl2.getInvolvedUser());
    assertNull(historicTaskInstanceQueryImpl2.getLocale());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionId());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionKey());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionKeyLike());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionName());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionNameLike());
    assertNull(historicTaskInstanceQueryImpl2.getProcessInstanceBusinessKey());
    assertNull(historicTaskInstanceQueryImpl2.getProcessInstanceBusinessKeyLike());
    assertNull(historicTaskInstanceQueryImpl2.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.getProcessInstanceId());
    assertNull(historicTaskInstanceQueryImpl2.getTaskAssignee());
    assertNull(historicTaskInstanceQueryImpl2.getTaskAssigneeLike());
    assertNull(historicTaskInstanceQueryImpl2.getTaskAssigneeLikeIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDefinitionKey());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDefinitionKeyLike());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDeleteReason());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDeleteReasonLike());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDescription());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDescriptionLike());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDescriptionLikeIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.getTaskId());
    assertNull(historicTaskInstanceQueryImpl2.getTaskName());
    assertNull(historicTaskInstanceQueryImpl2.getTaskNameLike());
    assertNull(historicTaskInstanceQueryImpl2.getTaskNameLikeIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.getTaskOwner());
    assertNull(historicTaskInstanceQueryImpl2.getTaskOwnerLike());
    assertNull(historicTaskInstanceQueryImpl2.getTaskOwnerLikeIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.getTaskParentTaskId());
    assertNull(historicTaskInstanceQueryImpl2.getTenantId());
    assertNull(historicTaskInstanceQueryImpl2.getTenantIdLike());
    assertNull(historicTaskInstanceQueryImpl2.orderBy);
    assertNull(historicTaskInstanceQueryImpl2.getCompletedAfterDate());
    assertNull(historicTaskInstanceQueryImpl2.getCompletedBeforeDate());
    assertNull(historicTaskInstanceQueryImpl2.getCompletedDate());
    assertNull(historicTaskInstanceQueryImpl2.getCreationAfterDate());
    assertNull(historicTaskInstanceQueryImpl2.getCreationBeforeDate());
    assertNull(historicTaskInstanceQueryImpl2.getCreationDate());
    assertNull(historicTaskInstanceQueryImpl2.getDueAfter());
    assertNull(historicTaskInstanceQueryImpl2.getDueBefore());
    assertNull(historicTaskInstanceQueryImpl2.getDueDate());
    assertNull(historicTaskInstanceQueryImpl2.getCandidateGroups());
    assertNull(historicTaskInstanceQueryImpl2.getDeploymentIds());
    assertNull(historicTaskInstanceQueryImpl2.getInvolvedGroups());
    assertNull(historicTaskInstanceQueryImpl2.getProcessCategoryInList());
    assertNull(historicTaskInstanceQueryImpl2.getProcessCategoryNotInList());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionKeys());
    assertNull(historicTaskInstanceQueryImpl2.getProcessInstanceIds());
    assertNull(historicTaskInstanceQueryImpl2.getTaskAssigneeIds());
    assertNull(historicTaskInstanceQueryImpl2.getTaskNameList());
    assertNull(historicTaskInstanceQueryImpl2.getTaskNameListIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.nullHandlingOnOrder);
    assertNull(historicTaskInstanceQueryImpl2.resultType);
    assertNull(historicTaskInstanceQueryImpl2.currentOrQueryObject);
    assertNull(historicTaskInstanceQueryImpl2.commandContext);
    assertNull(historicTaskInstanceQueryImpl2.commandExecutor);
    assertNull(historicTaskInstanceQueryImpl2.orderProperty);
    assertEquals(0, historicTaskInstanceQueryImpl2.getFirstResult());
    assertEquals(1, historicTaskInstanceQueryImpl2.getFirstRow());
    assertFalse(historicTaskInstanceQueryImpl2.hasLocalQueryVariableValue());
    assertFalse(historicTaskInstanceQueryImpl2.hasNonLocalQueryVariableValue());
    assertFalse(historicTaskInstanceQueryImpl2.isFinished());
    assertFalse(historicTaskInstanceQueryImpl2.isInOrStatement());
    assertFalse(historicTaskInstanceQueryImpl2.isIncludeProcessVariables());
    assertFalse(historicTaskInstanceQueryImpl2.isIncludeTaskLocalVariables());
    assertFalse(historicTaskInstanceQueryImpl2.isProcessFinished());
    assertFalse(historicTaskInstanceQueryImpl2.isProcessUnfinished());
    assertFalse(historicTaskInstanceQueryImpl2.isUnfinished());
    assertFalse(historicTaskInstanceQueryImpl2.isWithoutDueDate());
    assertFalse(historicTaskInstanceQueryImpl2.isWithoutTenantId());
    assertFalse(historicTaskInstanceQueryImpl2.withLocalizationFallback);
    assertTrue(historicTaskInstanceQueryImpl.isInOrStatement());
    assertEquals(Integer.MAX_VALUE, historicTaskInstanceQueryImpl2.getLastRow());
    assertEquals(Integer.MAX_VALUE, historicTaskInstanceQueryImpl2.getMaxResults());
    assertSame(historicTaskInstanceQueryImpl, actualOrResult);
    HistoricTaskInstanceQueryImpl expectedParameter = ((HistoricTaskInstanceQueryImpl) actualOrResult).currentOrQueryObject;
    assertSame(expectedParameter, historicTaskInstanceQueryImpl2.getParameter());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#or()}
   */
  @Test
  public void testOr2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualOrResult = historicTaskInstanceQueryImpl.or();

    // Assert
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl2 = historicTaskInstanceQueryImpl.currentOrQueryObject;
    assertEquals("RES.ID_ asc", historicTaskInstanceQueryImpl2.getOrderBy());
    assertEquals("RES.ID_ asc", historicTaskInstanceQueryImpl2.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", historicTaskInstanceQueryImpl2.getMssqlOrDB2OrderBy());
    assertNull(historicTaskInstanceQueryImpl2.getTaskMaxPriority());
    assertNull(historicTaskInstanceQueryImpl2.getTaskMinPriority());
    assertNull(historicTaskInstanceQueryImpl2.getTaskPriority());
    assertNull(historicTaskInstanceQueryImpl2.getTaskVariablesLimit());
    assertNull(historicTaskInstanceQueryImpl2.getDatabaseType());
    assertNull(historicTaskInstanceQueryImpl2.getCandidateGroup());
    assertNull(historicTaskInstanceQueryImpl2.getCandidateUser());
    assertNull(historicTaskInstanceQueryImpl2.getCategory());
    assertNull(historicTaskInstanceQueryImpl2.getDeploymentId());
    assertNull(historicTaskInstanceQueryImpl2.getExecutionId());
    assertNull(historicTaskInstanceQueryImpl2.getInvolvedUser());
    assertNull(historicTaskInstanceQueryImpl2.getLocale());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionId());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionKey());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionKeyLike());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionName());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionNameLike());
    assertNull(historicTaskInstanceQueryImpl2.getProcessInstanceBusinessKey());
    assertNull(historicTaskInstanceQueryImpl2.getProcessInstanceBusinessKeyLike());
    assertNull(historicTaskInstanceQueryImpl2.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.getProcessInstanceId());
    assertNull(historicTaskInstanceQueryImpl2.getTaskAssignee());
    assertNull(historicTaskInstanceQueryImpl2.getTaskAssigneeLike());
    assertNull(historicTaskInstanceQueryImpl2.getTaskAssigneeLikeIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDefinitionKey());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDefinitionKeyLike());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDeleteReason());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDeleteReasonLike());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDescription());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDescriptionLike());
    assertNull(historicTaskInstanceQueryImpl2.getTaskDescriptionLikeIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.getTaskId());
    assertNull(historicTaskInstanceQueryImpl2.getTaskName());
    assertNull(historicTaskInstanceQueryImpl2.getTaskNameLike());
    assertNull(historicTaskInstanceQueryImpl2.getTaskNameLikeIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.getTaskOwner());
    assertNull(historicTaskInstanceQueryImpl2.getTaskOwnerLike());
    assertNull(historicTaskInstanceQueryImpl2.getTaskOwnerLikeIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.getTaskParentTaskId());
    assertNull(historicTaskInstanceQueryImpl2.getTenantId());
    assertNull(historicTaskInstanceQueryImpl2.getTenantIdLike());
    assertNull(historicTaskInstanceQueryImpl2.orderBy);
    assertNull(historicTaskInstanceQueryImpl2.getCompletedAfterDate());
    assertNull(historicTaskInstanceQueryImpl2.getCompletedBeforeDate());
    assertNull(historicTaskInstanceQueryImpl2.getCompletedDate());
    assertNull(historicTaskInstanceQueryImpl2.getCreationAfterDate());
    assertNull(historicTaskInstanceQueryImpl2.getCreationBeforeDate());
    assertNull(historicTaskInstanceQueryImpl2.getCreationDate());
    assertNull(historicTaskInstanceQueryImpl2.getDueAfter());
    assertNull(historicTaskInstanceQueryImpl2.getDueBefore());
    assertNull(historicTaskInstanceQueryImpl2.getDueDate());
    assertNull(historicTaskInstanceQueryImpl2.getCandidateGroups());
    assertNull(historicTaskInstanceQueryImpl2.getDeploymentIds());
    assertNull(historicTaskInstanceQueryImpl2.getInvolvedGroups());
    assertNull(historicTaskInstanceQueryImpl2.getProcessCategoryInList());
    assertNull(historicTaskInstanceQueryImpl2.getProcessCategoryNotInList());
    assertNull(historicTaskInstanceQueryImpl2.getProcessDefinitionKeys());
    assertNull(historicTaskInstanceQueryImpl2.getProcessInstanceIds());
    assertNull(historicTaskInstanceQueryImpl2.getTaskAssigneeIds());
    assertNull(historicTaskInstanceQueryImpl2.getTaskNameList());
    assertNull(historicTaskInstanceQueryImpl2.getTaskNameListIgnoreCase());
    assertNull(historicTaskInstanceQueryImpl2.nullHandlingOnOrder);
    assertNull(historicTaskInstanceQueryImpl2.resultType);
    assertNull(historicTaskInstanceQueryImpl2.currentOrQueryObject);
    assertNull(historicTaskInstanceQueryImpl2.commandContext);
    assertNull(historicTaskInstanceQueryImpl2.commandExecutor);
    assertNull(historicTaskInstanceQueryImpl2.orderProperty);
    assertEquals(0, historicTaskInstanceQueryImpl2.getFirstResult());
    assertEquals(1, historicTaskInstanceQueryImpl2.getFirstRow());
    assertFalse(historicTaskInstanceQueryImpl2.hasLocalQueryVariableValue());
    assertFalse(historicTaskInstanceQueryImpl2.hasNonLocalQueryVariableValue());
    assertFalse(historicTaskInstanceQueryImpl2.isFinished());
    assertFalse(historicTaskInstanceQueryImpl2.isInOrStatement());
    assertFalse(historicTaskInstanceQueryImpl2.isIncludeProcessVariables());
    assertFalse(historicTaskInstanceQueryImpl2.isIncludeTaskLocalVariables());
    assertFalse(historicTaskInstanceQueryImpl2.isProcessFinished());
    assertFalse(historicTaskInstanceQueryImpl2.isProcessUnfinished());
    assertFalse(historicTaskInstanceQueryImpl2.isUnfinished());
    assertFalse(historicTaskInstanceQueryImpl2.isWithoutDueDate());
    assertFalse(historicTaskInstanceQueryImpl2.isWithoutTenantId());
    assertFalse(historicTaskInstanceQueryImpl2.withLocalizationFallback);
    assertTrue(historicTaskInstanceQueryImpl.isInOrStatement());
    assertEquals(Integer.MAX_VALUE, historicTaskInstanceQueryImpl2.getLastRow());
    assertEquals(Integer.MAX_VALUE, historicTaskInstanceQueryImpl2.getMaxResults());
    assertSame(historicTaskInstanceQueryImpl, actualOrResult);
    HistoricTaskInstanceQueryImpl expectedParameter = ((HistoricTaskInstanceQueryImpl) actualOrResult).currentOrQueryObject;
    assertSame(expectedParameter, historicTaskInstanceQueryImpl2.getParameter());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#endOr()}
   */
  @Test
  public void testEndOr() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new HistoricTaskInstanceQueryImpl()).endOr());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#localize(HistoricTaskInstance)}
   */
  @Test
  public void testLocalize() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    HistoricTaskInstanceEntityImpl task = mock(HistoricTaskInstanceEntityImpl.class);
    doNothing().when(task).setLocalizedDescription(Mockito.<String>any());
    doNothing().when(task).setLocalizedName(Mockito.<String>any());

    // Act
    historicTaskInstanceQueryImpl.localize(task);

    // Assert that nothing has changed
    verify(task).setLocalizedDescription(isNull());
    verify(task).setLocalizedName(isNull());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#localize(HistoricTaskInstance)}
   */
  @Test
  public void testLocalize2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.locale("en");
    HistoricTaskInstanceEntityImpl task = mock(HistoricTaskInstanceEntityImpl.class);
    when(task.getTaskDefinitionKey()).thenThrow(new ActivitiIllegalArgumentException("An error occurred"));
    when(task.getProcessDefinitionId()).thenReturn("42");
    doNothing().when(task).setLocalizedDescription(Mockito.<String>any());
    doNothing().when(task).setLocalizedName(Mockito.<String>any());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicTaskInstanceQueryImpl.localize(task));
    verify(task).getProcessDefinitionId();
    verify(task).getTaskDefinitionKey();
    verify(task).setLocalizedDescription(isNull());
    verify(task).setLocalizedName(isNull());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#localize(HistoricTaskInstance)}
   */
  @Test
  public void testLocalize3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.locale("en");
    HistoricTaskInstanceEntityImpl task = mock(HistoricTaskInstanceEntityImpl.class);
    when(task.getProcessDefinitionId()).thenReturn(null);
    doNothing().when(task).setLocalizedDescription(Mockito.<String>any());
    doNothing().when(task).setLocalizedName(Mockito.<String>any());

    // Act
    historicTaskInstanceQueryImpl.localize(task);

    // Assert that nothing has changed
    verify(task).getProcessDefinitionId();
    verify(task).setLocalizedDescription(isNull());
    verify(task).setLocalizedName(isNull());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  public void testGetMssqlOrDB2OrderBy() {
    // Arrange, Act and Assert
    assertEquals("TEMPRES_ID_ asc", (new HistoricTaskInstanceQueryImpl()).getMssqlOrDB2OrderBy());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  public void testGetMssqlOrDB2OrderBy2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.addOrder("RES.ID_ asc", AbstractQuery.SORTORDER_ASC,
        AbstractQuery.NullHandlingOnOrder.NULLS_FIRST);

    // Act and Assert
    assertEquals("TEMPRES_ID_ asc asc", historicTaskInstanceQueryImpl.getMssqlOrDB2OrderBy());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#getMssqlOrDB2OrderBy()}
   */
  @Test
  public void testGetMssqlOrDB2OrderBy3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));
    historicTaskInstanceQueryImpl.addOrder("RES.ID_ asc", AbstractQuery.SORTORDER_ASC,
        AbstractQuery.NullHandlingOnOrder.NULLS_FIRST);

    // Act and Assert
    assertEquals("TEMPRES_ID_ asc asc", historicTaskInstanceQueryImpl.getMssqlOrDB2OrderBy());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#getCandidateGroups()}
   */
  @Test
  public void testGetCandidateGroups() {
    // Arrange, Act and Assert
    assertNull((new HistoricTaskInstanceQueryImpl()).getCandidateGroups());
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#getCandidateGroups()}
   */
  @Test
  public void testGetCandidateGroups2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.taskCandidateGroup("2020-03-01");

    // Act
    List<String> actualCandidateGroups = historicTaskInstanceQueryImpl.getCandidateGroups();

    // Assert
    assertEquals(1, actualCandidateGroups.size());
    assertEquals("2020-03-01", actualCandidateGroups.get(0));
  }

  /**
   * Method under test: {@link HistoricTaskInstanceQueryImpl#getCandidateGroups()}
   */
  @Test
  public void testGetCandidateGroups3() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act and Assert
    assertNull(historicTaskInstanceQueryImpl.getCandidateGroups());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl(CommandExecutor)}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCandidateGroup()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCandidateUser()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCategory()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCompletedAfterDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCompletedBeforeDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCompletedDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCreationAfterDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCreationBeforeDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCreationDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDeploymentId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDeploymentIds()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDueAfter()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDueBefore()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDueDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getExecutionId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getInvolvedGroups()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getInvolvedUser()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getLocale()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getOrQueryObjects()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessCategoryInList()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessCategoryNotInList()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeyLike()}
   *   <li>
   * {@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeyLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeys()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionName()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionNameLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKeyLike()}
   *   <li>
   * {@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKeyLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceIds()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssignee()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssigneeIds()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssigneeLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssigneeLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDefinitionKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDefinitionKeyLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDeleteReason()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDeleteReasonLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDescription()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDescriptionLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDescriptionLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskMaxPriority()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskMinPriority()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskName()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameList()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameListIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskOwner()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskOwnerLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskOwnerLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskParentTaskId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskPriority()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskVariablesLimit()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTenantId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTenantIdLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isFinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isInOrStatement()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isIncludeProcessVariables()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isIncludeTaskLocalVariables()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isProcessFinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isProcessUnfinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isUnfinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isWithoutDueDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isWithoutTenantId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();

    // Act
    HistoricTaskInstanceQueryImpl actualHistoricTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl(
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()));
    String actualCandidateGroup = actualHistoricTaskInstanceQueryImpl.getCandidateGroup();
    String actualCandidateUser = actualHistoricTaskInstanceQueryImpl.getCandidateUser();
    String actualCategory = actualHistoricTaskInstanceQueryImpl.getCategory();
    Date actualCompletedAfterDate = actualHistoricTaskInstanceQueryImpl.getCompletedAfterDate();
    Date actualCompletedBeforeDate = actualHistoricTaskInstanceQueryImpl.getCompletedBeforeDate();
    Date actualCompletedDate = actualHistoricTaskInstanceQueryImpl.getCompletedDate();
    Date actualCreationAfterDate = actualHistoricTaskInstanceQueryImpl.getCreationAfterDate();
    Date actualCreationBeforeDate = actualHistoricTaskInstanceQueryImpl.getCreationBeforeDate();
    Date actualCreationDate = actualHistoricTaskInstanceQueryImpl.getCreationDate();
    String actualDeploymentId = actualHistoricTaskInstanceQueryImpl.getDeploymentId();
    List<String> actualDeploymentIds = actualHistoricTaskInstanceQueryImpl.getDeploymentIds();
    Date actualDueAfter = actualHistoricTaskInstanceQueryImpl.getDueAfter();
    Date actualDueBefore = actualHistoricTaskInstanceQueryImpl.getDueBefore();
    Date actualDueDate = actualHistoricTaskInstanceQueryImpl.getDueDate();
    String actualExecutionId = actualHistoricTaskInstanceQueryImpl.getExecutionId();
    List<String> actualInvolvedGroups = actualHistoricTaskInstanceQueryImpl.getInvolvedGroups();
    String actualInvolvedUser = actualHistoricTaskInstanceQueryImpl.getInvolvedUser();
    String actualLocale = actualHistoricTaskInstanceQueryImpl.getLocale();
    List<HistoricTaskInstanceQueryImpl> actualOrQueryObjects = actualHistoricTaskInstanceQueryImpl.getOrQueryObjects();
    List<String> actualProcessCategoryInList = actualHistoricTaskInstanceQueryImpl.getProcessCategoryInList();
    List<String> actualProcessCategoryNotInList = actualHistoricTaskInstanceQueryImpl.getProcessCategoryNotInList();
    String actualProcessDefinitionId = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionId();
    String actualProcessDefinitionKey = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKey();
    String actualProcessDefinitionKeyLike = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeyLike();
    String actualProcessDefinitionKeyLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl
        .getProcessDefinitionKeyLikeIgnoreCase();
    List<String> actualProcessDefinitionKeys = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeys();
    String actualProcessDefinitionName = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionName();
    String actualProcessDefinitionNameLike = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionNameLike();
    String actualProcessInstanceBusinessKey = actualHistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKey();
    String actualProcessInstanceBusinessKeyLike = actualHistoricTaskInstanceQueryImpl
        .getProcessInstanceBusinessKeyLike();
    String actualProcessInstanceBusinessKeyLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl
        .getProcessInstanceBusinessKeyLikeIgnoreCase();
    String actualProcessInstanceId = actualHistoricTaskInstanceQueryImpl.getProcessInstanceId();
    List<String> actualProcessInstanceIds = actualHistoricTaskInstanceQueryImpl.getProcessInstanceIds();
    String actualTaskAssignee = actualHistoricTaskInstanceQueryImpl.getTaskAssignee();
    List<String> actualTaskAssigneeIds = actualHistoricTaskInstanceQueryImpl.getTaskAssigneeIds();
    String actualTaskAssigneeLike = actualHistoricTaskInstanceQueryImpl.getTaskAssigneeLike();
    String actualTaskAssigneeLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskAssigneeLikeIgnoreCase();
    String actualTaskDefinitionKey = actualHistoricTaskInstanceQueryImpl.getTaskDefinitionKey();
    String actualTaskDefinitionKeyLike = actualHistoricTaskInstanceQueryImpl.getTaskDefinitionKeyLike();
    String actualTaskDeleteReason = actualHistoricTaskInstanceQueryImpl.getTaskDeleteReason();
    String actualTaskDeleteReasonLike = actualHistoricTaskInstanceQueryImpl.getTaskDeleteReasonLike();
    String actualTaskDescription = actualHistoricTaskInstanceQueryImpl.getTaskDescription();
    String actualTaskDescriptionLike = actualHistoricTaskInstanceQueryImpl.getTaskDescriptionLike();
    String actualTaskDescriptionLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskDescriptionLikeIgnoreCase();
    String actualTaskId = actualHistoricTaskInstanceQueryImpl.getTaskId();
    Integer actualTaskMaxPriority = actualHistoricTaskInstanceQueryImpl.getTaskMaxPriority();
    Integer actualTaskMinPriority = actualHistoricTaskInstanceQueryImpl.getTaskMinPriority();
    String actualTaskName = actualHistoricTaskInstanceQueryImpl.getTaskName();
    String actualTaskNameLike = actualHistoricTaskInstanceQueryImpl.getTaskNameLike();
    String actualTaskNameLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskNameLikeIgnoreCase();
    List<String> actualTaskNameList = actualHistoricTaskInstanceQueryImpl.getTaskNameList();
    List<String> actualTaskNameListIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskNameListIgnoreCase();
    String actualTaskOwner = actualHistoricTaskInstanceQueryImpl.getTaskOwner();
    String actualTaskOwnerLike = actualHistoricTaskInstanceQueryImpl.getTaskOwnerLike();
    String actualTaskOwnerLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskOwnerLikeIgnoreCase();
    String actualTaskParentTaskId = actualHistoricTaskInstanceQueryImpl.getTaskParentTaskId();
    Integer actualTaskPriority = actualHistoricTaskInstanceQueryImpl.getTaskPriority();
    Integer actualTaskVariablesLimit = actualHistoricTaskInstanceQueryImpl.getTaskVariablesLimit();
    String actualTenantId = actualHistoricTaskInstanceQueryImpl.getTenantId();
    String actualTenantIdLike = actualHistoricTaskInstanceQueryImpl.getTenantIdLike();
    boolean actualIsFinishedResult = actualHistoricTaskInstanceQueryImpl.isFinished();
    boolean actualIsInOrStatementResult = actualHistoricTaskInstanceQueryImpl.isInOrStatement();
    boolean actualIsIncludeProcessVariablesResult = actualHistoricTaskInstanceQueryImpl.isIncludeProcessVariables();
    boolean actualIsIncludeTaskLocalVariablesResult = actualHistoricTaskInstanceQueryImpl.isIncludeTaskLocalVariables();
    boolean actualIsProcessFinishedResult = actualHistoricTaskInstanceQueryImpl.isProcessFinished();
    boolean actualIsProcessUnfinishedResult = actualHistoricTaskInstanceQueryImpl.isProcessUnfinished();
    boolean actualIsUnfinishedResult = actualHistoricTaskInstanceQueryImpl.isUnfinished();
    boolean actualIsWithoutDueDateResult = actualHistoricTaskInstanceQueryImpl.isWithoutDueDate();
    boolean actualIsWithoutTenantIdResult = actualHistoricTaskInstanceQueryImpl.isWithoutTenantId();

    // Assert
    assertNull(actualTaskMaxPriority);
    assertNull(actualTaskMinPriority);
    assertNull(actualTaskPriority);
    assertNull(actualTaskVariablesLimit);
    assertNull(actualHistoricTaskInstanceQueryImpl.getParameter());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDatabaseType());
    assertNull(actualCandidateGroup);
    assertNull(actualCandidateUser);
    assertNull(actualCategory);
    assertNull(actualDeploymentId);
    assertNull(actualExecutionId);
    assertNull(actualInvolvedUser);
    assertNull(actualLocale);
    assertNull(actualProcessDefinitionId);
    assertNull(actualProcessDefinitionKey);
    assertNull(actualProcessDefinitionKeyLike);
    assertNull(actualProcessDefinitionKeyLikeIgnoreCase);
    assertNull(actualProcessDefinitionName);
    assertNull(actualProcessDefinitionNameLike);
    assertNull(actualProcessInstanceBusinessKey);
    assertNull(actualProcessInstanceBusinessKeyLike);
    assertNull(actualProcessInstanceBusinessKeyLikeIgnoreCase);
    assertNull(actualProcessInstanceId);
    assertNull(actualTaskAssignee);
    assertNull(actualTaskAssigneeLike);
    assertNull(actualTaskAssigneeLikeIgnoreCase);
    assertNull(actualTaskDefinitionKey);
    assertNull(actualTaskDefinitionKeyLike);
    assertNull(actualTaskDeleteReason);
    assertNull(actualTaskDeleteReasonLike);
    assertNull(actualTaskDescription);
    assertNull(actualTaskDescriptionLike);
    assertNull(actualTaskDescriptionLikeIgnoreCase);
    assertNull(actualTaskId);
    assertNull(actualTaskName);
    assertNull(actualTaskNameLike);
    assertNull(actualTaskNameLikeIgnoreCase);
    assertNull(actualTaskOwner);
    assertNull(actualTaskOwnerLike);
    assertNull(actualTaskOwnerLikeIgnoreCase);
    assertNull(actualTaskParentTaskId);
    assertNull(actualTenantId);
    assertNull(actualTenantIdLike);
    assertNull(actualCompletedAfterDate);
    assertNull(actualCompletedBeforeDate);
    assertNull(actualCompletedDate);
    assertNull(actualCreationAfterDate);
    assertNull(actualCreationBeforeDate);
    assertNull(actualCreationDate);
    assertNull(actualDueAfter);
    assertNull(actualDueBefore);
    assertNull(actualDueDate);
    assertNull(actualDeploymentIds);
    assertNull(actualInvolvedGroups);
    assertNull(actualProcessCategoryInList);
    assertNull(actualProcessCategoryNotInList);
    assertNull(actualProcessDefinitionKeys);
    assertNull(actualProcessInstanceIds);
    assertNull(actualTaskAssigneeIds);
    assertNull(actualTaskNameList);
    assertNull(actualTaskNameListIgnoreCase);
    assertEquals(0, actualHistoricTaskInstanceQueryImpl.getFirstResult());
    assertFalse(actualIsFinishedResult);
    assertFalse(actualIsInOrStatementResult);
    assertFalse(actualIsIncludeProcessVariablesResult);
    assertFalse(actualIsIncludeTaskLocalVariablesResult);
    assertFalse(actualIsProcessFinishedResult);
    assertFalse(actualIsProcessUnfinishedResult);
    assertFalse(actualIsUnfinishedResult);
    assertFalse(actualIsWithoutDueDateResult);
    assertFalse(actualIsWithoutTenantIdResult);
    assertTrue(actualHistoricTaskInstanceQueryImpl.getQueryVariableValues().isEmpty());
    assertTrue(actualOrQueryObjects.isEmpty());
    assertEquals(Integer.MAX_VALUE, actualHistoricTaskInstanceQueryImpl.getMaxResults());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl(CommandExecutor, String)}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCandidateGroup()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCandidateUser()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCategory()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCompletedAfterDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCompletedBeforeDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCompletedDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCreationAfterDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCreationBeforeDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getCreationDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDeploymentId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDeploymentIds()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDueAfter()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDueBefore()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getDueDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getExecutionId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getInvolvedGroups()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getInvolvedUser()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getLocale()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getOrQueryObjects()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessCategoryInList()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessCategoryNotInList()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeyLike()}
   *   <li>
   * {@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeyLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionKeys()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionName()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessDefinitionNameLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKeyLike()}
   *   <li>
   * {@link HistoricTaskInstanceQueryImpl#getProcessInstanceBusinessKeyLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getProcessInstanceIds()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssignee()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssigneeIds()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssigneeLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskAssigneeLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDefinitionKey()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDefinitionKeyLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDeleteReason()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDeleteReasonLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDescription()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDescriptionLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskDescriptionLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskMaxPriority()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskMinPriority()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskName()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameList()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskNameListIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskOwner()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskOwnerLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskOwnerLikeIgnoreCase()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskParentTaskId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskPriority()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTaskVariablesLimit()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTenantId()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#getTenantIdLike()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isFinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isInOrStatement()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isIncludeProcessVariables()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isIncludeTaskLocalVariables()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isProcessFinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isProcessUnfinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isUnfinished()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isWithoutDueDate()}
   *   <li>{@link HistoricTaskInstanceQueryImpl#isWithoutTenantId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters2() {
    // Arrange
    CommandConfig defaultConfig = new CommandConfig();

    // Act
    HistoricTaskInstanceQueryImpl actualHistoricTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl(
        new CommandExecutorImpl(defaultConfig, new CommandContextInterceptor()), "Database Type");
    String actualCandidateGroup = actualHistoricTaskInstanceQueryImpl.getCandidateGroup();
    String actualCandidateUser = actualHistoricTaskInstanceQueryImpl.getCandidateUser();
    String actualCategory = actualHistoricTaskInstanceQueryImpl.getCategory();
    Date actualCompletedAfterDate = actualHistoricTaskInstanceQueryImpl.getCompletedAfterDate();
    Date actualCompletedBeforeDate = actualHistoricTaskInstanceQueryImpl.getCompletedBeforeDate();
    Date actualCompletedDate = actualHistoricTaskInstanceQueryImpl.getCompletedDate();
    Date actualCreationAfterDate = actualHistoricTaskInstanceQueryImpl.getCreationAfterDate();
    Date actualCreationBeforeDate = actualHistoricTaskInstanceQueryImpl.getCreationBeforeDate();
    Date actualCreationDate = actualHistoricTaskInstanceQueryImpl.getCreationDate();
    String actualDeploymentId = actualHistoricTaskInstanceQueryImpl.getDeploymentId();
    List<String> actualDeploymentIds = actualHistoricTaskInstanceQueryImpl.getDeploymentIds();
    Date actualDueAfter = actualHistoricTaskInstanceQueryImpl.getDueAfter();
    Date actualDueBefore = actualHistoricTaskInstanceQueryImpl.getDueBefore();
    Date actualDueDate = actualHistoricTaskInstanceQueryImpl.getDueDate();
    String actualExecutionId = actualHistoricTaskInstanceQueryImpl.getExecutionId();
    List<String> actualInvolvedGroups = actualHistoricTaskInstanceQueryImpl.getInvolvedGroups();
    String actualInvolvedUser = actualHistoricTaskInstanceQueryImpl.getInvolvedUser();
    String actualLocale = actualHistoricTaskInstanceQueryImpl.getLocale();
    List<HistoricTaskInstanceQueryImpl> actualOrQueryObjects = actualHistoricTaskInstanceQueryImpl.getOrQueryObjects();
    List<String> actualProcessCategoryInList = actualHistoricTaskInstanceQueryImpl.getProcessCategoryInList();
    List<String> actualProcessCategoryNotInList = actualHistoricTaskInstanceQueryImpl.getProcessCategoryNotInList();
    String actualProcessDefinitionId = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionId();
    String actualProcessDefinitionKey = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKey();
    String actualProcessDefinitionKeyLike = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeyLike();
    String actualProcessDefinitionKeyLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl
        .getProcessDefinitionKeyLikeIgnoreCase();
    List<String> actualProcessDefinitionKeys = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeys();
    String actualProcessDefinitionName = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionName();
    String actualProcessDefinitionNameLike = actualHistoricTaskInstanceQueryImpl.getProcessDefinitionNameLike();
    String actualProcessInstanceBusinessKey = actualHistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKey();
    String actualProcessInstanceBusinessKeyLike = actualHistoricTaskInstanceQueryImpl
        .getProcessInstanceBusinessKeyLike();
    String actualProcessInstanceBusinessKeyLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl
        .getProcessInstanceBusinessKeyLikeIgnoreCase();
    String actualProcessInstanceId = actualHistoricTaskInstanceQueryImpl.getProcessInstanceId();
    List<String> actualProcessInstanceIds = actualHistoricTaskInstanceQueryImpl.getProcessInstanceIds();
    String actualTaskAssignee = actualHistoricTaskInstanceQueryImpl.getTaskAssignee();
    List<String> actualTaskAssigneeIds = actualHistoricTaskInstanceQueryImpl.getTaskAssigneeIds();
    String actualTaskAssigneeLike = actualHistoricTaskInstanceQueryImpl.getTaskAssigneeLike();
    String actualTaskAssigneeLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskAssigneeLikeIgnoreCase();
    String actualTaskDefinitionKey = actualHistoricTaskInstanceQueryImpl.getTaskDefinitionKey();
    String actualTaskDefinitionKeyLike = actualHistoricTaskInstanceQueryImpl.getTaskDefinitionKeyLike();
    String actualTaskDeleteReason = actualHistoricTaskInstanceQueryImpl.getTaskDeleteReason();
    String actualTaskDeleteReasonLike = actualHistoricTaskInstanceQueryImpl.getTaskDeleteReasonLike();
    String actualTaskDescription = actualHistoricTaskInstanceQueryImpl.getTaskDescription();
    String actualTaskDescriptionLike = actualHistoricTaskInstanceQueryImpl.getTaskDescriptionLike();
    String actualTaskDescriptionLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskDescriptionLikeIgnoreCase();
    String actualTaskId = actualHistoricTaskInstanceQueryImpl.getTaskId();
    Integer actualTaskMaxPriority = actualHistoricTaskInstanceQueryImpl.getTaskMaxPriority();
    Integer actualTaskMinPriority = actualHistoricTaskInstanceQueryImpl.getTaskMinPriority();
    String actualTaskName = actualHistoricTaskInstanceQueryImpl.getTaskName();
    String actualTaskNameLike = actualHistoricTaskInstanceQueryImpl.getTaskNameLike();
    String actualTaskNameLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskNameLikeIgnoreCase();
    List<String> actualTaskNameList = actualHistoricTaskInstanceQueryImpl.getTaskNameList();
    List<String> actualTaskNameListIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskNameListIgnoreCase();
    String actualTaskOwner = actualHistoricTaskInstanceQueryImpl.getTaskOwner();
    String actualTaskOwnerLike = actualHistoricTaskInstanceQueryImpl.getTaskOwnerLike();
    String actualTaskOwnerLikeIgnoreCase = actualHistoricTaskInstanceQueryImpl.getTaskOwnerLikeIgnoreCase();
    String actualTaskParentTaskId = actualHistoricTaskInstanceQueryImpl.getTaskParentTaskId();
    Integer actualTaskPriority = actualHistoricTaskInstanceQueryImpl.getTaskPriority();
    Integer actualTaskVariablesLimit = actualHistoricTaskInstanceQueryImpl.getTaskVariablesLimit();
    String actualTenantId = actualHistoricTaskInstanceQueryImpl.getTenantId();
    String actualTenantIdLike = actualHistoricTaskInstanceQueryImpl.getTenantIdLike();
    boolean actualIsFinishedResult = actualHistoricTaskInstanceQueryImpl.isFinished();
    boolean actualIsInOrStatementResult = actualHistoricTaskInstanceQueryImpl.isInOrStatement();
    boolean actualIsIncludeProcessVariablesResult = actualHistoricTaskInstanceQueryImpl.isIncludeProcessVariables();
    boolean actualIsIncludeTaskLocalVariablesResult = actualHistoricTaskInstanceQueryImpl.isIncludeTaskLocalVariables();
    boolean actualIsProcessFinishedResult = actualHistoricTaskInstanceQueryImpl.isProcessFinished();
    boolean actualIsProcessUnfinishedResult = actualHistoricTaskInstanceQueryImpl.isProcessUnfinished();
    boolean actualIsUnfinishedResult = actualHistoricTaskInstanceQueryImpl.isUnfinished();
    boolean actualIsWithoutDueDateResult = actualHistoricTaskInstanceQueryImpl.isWithoutDueDate();
    boolean actualIsWithoutTenantIdResult = actualHistoricTaskInstanceQueryImpl.isWithoutTenantId();

    // Assert
    assertEquals("Database Type", actualHistoricTaskInstanceQueryImpl.getDatabaseType());
    assertNull(actualTaskMaxPriority);
    assertNull(actualTaskMinPriority);
    assertNull(actualTaskPriority);
    assertNull(actualTaskVariablesLimit);
    assertNull(actualHistoricTaskInstanceQueryImpl.getParameter());
    assertNull(actualCandidateGroup);
    assertNull(actualCandidateUser);
    assertNull(actualCategory);
    assertNull(actualDeploymentId);
    assertNull(actualExecutionId);
    assertNull(actualInvolvedUser);
    assertNull(actualLocale);
    assertNull(actualProcessDefinitionId);
    assertNull(actualProcessDefinitionKey);
    assertNull(actualProcessDefinitionKeyLike);
    assertNull(actualProcessDefinitionKeyLikeIgnoreCase);
    assertNull(actualProcessDefinitionName);
    assertNull(actualProcessDefinitionNameLike);
    assertNull(actualProcessInstanceBusinessKey);
    assertNull(actualProcessInstanceBusinessKeyLike);
    assertNull(actualProcessInstanceBusinessKeyLikeIgnoreCase);
    assertNull(actualProcessInstanceId);
    assertNull(actualTaskAssignee);
    assertNull(actualTaskAssigneeLike);
    assertNull(actualTaskAssigneeLikeIgnoreCase);
    assertNull(actualTaskDefinitionKey);
    assertNull(actualTaskDefinitionKeyLike);
    assertNull(actualTaskDeleteReason);
    assertNull(actualTaskDeleteReasonLike);
    assertNull(actualTaskDescription);
    assertNull(actualTaskDescriptionLike);
    assertNull(actualTaskDescriptionLikeIgnoreCase);
    assertNull(actualTaskId);
    assertNull(actualTaskName);
    assertNull(actualTaskNameLike);
    assertNull(actualTaskNameLikeIgnoreCase);
    assertNull(actualTaskOwner);
    assertNull(actualTaskOwnerLike);
    assertNull(actualTaskOwnerLikeIgnoreCase);
    assertNull(actualTaskParentTaskId);
    assertNull(actualTenantId);
    assertNull(actualTenantIdLike);
    assertNull(actualCompletedAfterDate);
    assertNull(actualCompletedBeforeDate);
    assertNull(actualCompletedDate);
    assertNull(actualCreationAfterDate);
    assertNull(actualCreationBeforeDate);
    assertNull(actualCreationDate);
    assertNull(actualDueAfter);
    assertNull(actualDueBefore);
    assertNull(actualDueDate);
    assertNull(actualDeploymentIds);
    assertNull(actualInvolvedGroups);
    assertNull(actualProcessCategoryInList);
    assertNull(actualProcessCategoryNotInList);
    assertNull(actualProcessDefinitionKeys);
    assertNull(actualProcessInstanceIds);
    assertNull(actualTaskAssigneeIds);
    assertNull(actualTaskNameList);
    assertNull(actualTaskNameListIgnoreCase);
    assertEquals(0, actualHistoricTaskInstanceQueryImpl.getFirstResult());
    assertFalse(actualIsFinishedResult);
    assertFalse(actualIsInOrStatementResult);
    assertFalse(actualIsIncludeProcessVariablesResult);
    assertFalse(actualIsIncludeTaskLocalVariablesResult);
    assertFalse(actualIsProcessFinishedResult);
    assertFalse(actualIsProcessUnfinishedResult);
    assertFalse(actualIsUnfinishedResult);
    assertFalse(actualIsWithoutDueDateResult);
    assertFalse(actualIsWithoutTenantIdResult);
    assertTrue(actualHistoricTaskInstanceQueryImpl.getQueryVariableValues().isEmpty());
    assertTrue(actualOrQueryObjects.isEmpty());
    assertEquals(Integer.MAX_VALUE, actualHistoricTaskInstanceQueryImpl.getMaxResults());
  }

  /**
   * Method under test:
   * {@link HistoricTaskInstanceQueryImpl#HistoricTaskInstanceQueryImpl()}
   */
  @Test
  public void testNewHistoricTaskInstanceQueryImpl() {
    // Arrange and Act
    HistoricTaskInstanceQueryImpl actualHistoricTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualHistoricTaskInstanceQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualHistoricTaskInstanceQueryImpl.getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc", actualHistoricTaskInstanceQueryImpl.getMssqlOrDB2OrderBy());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskMaxPriority());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskMinPriority());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskPriority());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskVariablesLimit());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDatabaseType());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCandidateGroup());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCandidateUser());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCategory());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDeploymentId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getExecutionId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getInvolvedUser());
    assertNull(actualHistoricTaskInstanceQueryImpl.getLocale());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKey());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeyLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeyLikeIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionName());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionNameLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKey());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessInstanceBusinessKeyLikeIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessInstanceId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskAssignee());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskAssigneeLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskAssigneeLikeIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDefinitionKey());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDefinitionKeyLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDeleteReason());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDeleteReasonLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDescription());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDescriptionLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskDescriptionLikeIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskName());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskNameLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskNameLikeIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskOwner());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskOwnerLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskOwnerLikeIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskParentTaskId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTenantId());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTenantIdLike());
    assertNull(actualHistoricTaskInstanceQueryImpl.orderBy);
    assertNull(actualHistoricTaskInstanceQueryImpl.getCompletedAfterDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCompletedBeforeDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCompletedDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCreationAfterDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCreationBeforeDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCreationDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDueAfter());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDueBefore());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDueDate());
    assertNull(actualHistoricTaskInstanceQueryImpl.getCandidateGroups());
    assertNull(actualHistoricTaskInstanceQueryImpl.getDeploymentIds());
    assertNull(actualHistoricTaskInstanceQueryImpl.getInvolvedGroups());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessCategoryInList());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessCategoryNotInList());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessDefinitionKeys());
    assertNull(actualHistoricTaskInstanceQueryImpl.getProcessInstanceIds());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskAssigneeIds());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskNameList());
    assertNull(actualHistoricTaskInstanceQueryImpl.getTaskNameListIgnoreCase());
    assertNull(actualHistoricTaskInstanceQueryImpl.nullHandlingOnOrder);
    assertNull(actualHistoricTaskInstanceQueryImpl.resultType);
    assertNull(actualHistoricTaskInstanceQueryImpl.currentOrQueryObject);
    assertNull(actualHistoricTaskInstanceQueryImpl.commandContext);
    assertNull(actualHistoricTaskInstanceQueryImpl.commandExecutor);
    assertNull(actualHistoricTaskInstanceQueryImpl.orderProperty);
    assertEquals(0, actualHistoricTaskInstanceQueryImpl.getFirstResult());
    assertEquals(1, actualHistoricTaskInstanceQueryImpl.getFirstRow());
    assertFalse(actualHistoricTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertFalse(actualHistoricTaskInstanceQueryImpl.hasNonLocalQueryVariableValue());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isFinished());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isInOrStatement());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isIncludeProcessVariables());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isIncludeTaskLocalVariables());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isProcessFinished());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isProcessUnfinished());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isUnfinished());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isWithoutDueDate());
    assertFalse(actualHistoricTaskInstanceQueryImpl.isWithoutTenantId());
    assertFalse(actualHistoricTaskInstanceQueryImpl.withLocalizationFallback);
    assertTrue(actualHistoricTaskInstanceQueryImpl.getQueryVariableValues().isEmpty());
    assertTrue(actualHistoricTaskInstanceQueryImpl.getOrQueryObjects().isEmpty());
    assertEquals(Integer.MAX_VALUE, actualHistoricTaskInstanceQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualHistoricTaskInstanceQueryImpl.getMaxResults());
    assertSame(actualHistoricTaskInstanceQueryImpl, actualHistoricTaskInstanceQueryImpl.getParameter());
  }
}
