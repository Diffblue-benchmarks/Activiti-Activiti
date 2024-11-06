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
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricActivityInstanceQueryImplDiffblueTest {
  @InjectMocks
  private HistoricActivityInstanceQueryImpl historicActivityInstanceQueryImpl;

  /**
   * Test
   * {@link HistoricActivityInstanceQueryImpl#HistoricActivityInstanceQueryImpl()}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#HistoricActivityInstanceQueryImpl()}
   */
  @Test
  public void testNewHistoricActivityInstanceQueryImpl() {
    // Arrange and Act
    HistoricActivityInstanceQueryImpl actualHistoricActivityInstanceQueryImpl = new HistoricActivityInstanceQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualHistoricActivityInstanceQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualHistoricActivityInstanceQueryImpl.getOrderByColumns());
    assertNull(actualHistoricActivityInstanceQueryImpl.getDatabaseType());
    assertNull(actualHistoricActivityInstanceQueryImpl.getActivityId());
    assertNull(actualHistoricActivityInstanceQueryImpl.getActivityInstanceId());
    assertNull(actualHistoricActivityInstanceQueryImpl.getActivityName());
    assertNull(actualHistoricActivityInstanceQueryImpl.getActivityType());
    assertNull(actualHistoricActivityInstanceQueryImpl.getAssignee());
    assertNull(actualHistoricActivityInstanceQueryImpl.getDeleteReason());
    assertNull(actualHistoricActivityInstanceQueryImpl.getDeleteReasonLike());
    assertNull(actualHistoricActivityInstanceQueryImpl.getExecutionId());
    assertNull(actualHistoricActivityInstanceQueryImpl.getProcessDefinitionId());
    assertNull(actualHistoricActivityInstanceQueryImpl.getProcessInstanceId());
    assertNull(actualHistoricActivityInstanceQueryImpl.getTenantId());
    assertNull(actualHistoricActivityInstanceQueryImpl.getTenantIdLike());
    assertNull(actualHistoricActivityInstanceQueryImpl.orderBy);
    assertNull(actualHistoricActivityInstanceQueryImpl.nullHandlingOnOrder);
    assertNull(actualHistoricActivityInstanceQueryImpl.resultType);
    assertNull(actualHistoricActivityInstanceQueryImpl.commandContext);
    assertNull(actualHistoricActivityInstanceQueryImpl.commandExecutor);
    assertNull(actualHistoricActivityInstanceQueryImpl.orderProperty);
    assertEquals(0, actualHistoricActivityInstanceQueryImpl.getFirstResult());
    assertEquals(1, actualHistoricActivityInstanceQueryImpl.getFirstRow());
    assertFalse(actualHistoricActivityInstanceQueryImpl.isFinished());
    assertFalse(actualHistoricActivityInstanceQueryImpl.isUnfinished());
    assertFalse(actualHistoricActivityInstanceQueryImpl.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualHistoricActivityInstanceQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualHistoricActivityInstanceQueryImpl.getMaxResults());
    assertSame(actualHistoricActivityInstanceQueryImpl, actualHistoricActivityInstanceQueryImpl.getParameter());
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#processInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId() {
    // Arrange and Act
    HistoricActivityInstanceQueryImpl actualProcessInstanceIdResult = historicActivityInstanceQueryImpl
        .processInstanceId("42");

    // Assert
    assertEquals("42", historicActivityInstanceQueryImpl.getProcessInstanceId());
    assertSame(historicActivityInstanceQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#executionId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId() {
    // Arrange and Act
    HistoricActivityInstanceQueryImpl actualExecutionIdResult = historicActivityInstanceQueryImpl.executionId("42");

    // Assert
    assertEquals("42", historicActivityInstanceQueryImpl.getExecutionId());
    assertSame(historicActivityInstanceQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#processDefinitionId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#processDefinitionId(String)}
   */
  @Test
  public void testProcessDefinitionId() {
    // Arrange and Act
    HistoricActivityInstanceQueryImpl actualProcessDefinitionIdResult = historicActivityInstanceQueryImpl
        .processDefinitionId("42");

    // Assert
    assertEquals("42", historicActivityInstanceQueryImpl.getProcessDefinitionId());
    assertSame(historicActivityInstanceQueryImpl, actualProcessDefinitionIdResult);
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#activityId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#activityId(String)}
   */
  @Test
  public void testActivityId() {
    // Arrange and Act
    HistoricActivityInstanceQueryImpl actualActivityIdResult = historicActivityInstanceQueryImpl.activityId("42");

    // Assert
    assertEquals("42", historicActivityInstanceQueryImpl.getActivityId());
    assertSame(historicActivityInstanceQueryImpl, actualActivityIdResult);
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#activityName(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#activityName(String)}
   */
  @Test
  public void testActivityName() {
    // Arrange and Act
    HistoricActivityInstanceQueryImpl actualActivityNameResult = historicActivityInstanceQueryImpl
        .activityName("Activity Name");

    // Assert
    assertEquals("Activity Name", historicActivityInstanceQueryImpl.getActivityName());
    assertSame(historicActivityInstanceQueryImpl, actualActivityNameResult);
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#activityType(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#activityType(String)}
   */
  @Test
  public void testActivityType() {
    // Arrange and Act
    HistoricActivityInstanceQueryImpl actualActivityTypeResult = historicActivityInstanceQueryImpl
        .activityType("Activity Type");

    // Assert
    assertEquals("Activity Type", historicActivityInstanceQueryImpl.getActivityType());
    assertSame(historicActivityInstanceQueryImpl, actualActivityTypeResult);
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#taskAssignee(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#taskAssignee(String)}
   */
  @Test
  public void testTaskAssignee() {
    // Arrange and Act
    HistoricActivityInstanceQueryImpl actualTaskAssigneeResult = historicActivityInstanceQueryImpl
        .taskAssignee("Assignee");

    // Assert
    assertEquals("Assignee", historicActivityInstanceQueryImpl.getAssignee());
    assertSame(historicActivityInstanceQueryImpl, actualTaskAssigneeResult);
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#finished()}.
   * <p>
   * Method under test: {@link HistoricActivityInstanceQueryImpl#finished()}
   */
  @Test
  public void testFinished() {
    // Arrange
    HistoricActivityInstanceQueryImpl historicActivityInstanceQueryImpl = new HistoricActivityInstanceQueryImpl();

    // Act
    HistoricActivityInstanceQueryImpl actualFinishedResult = historicActivityInstanceQueryImpl.finished();

    // Assert
    assertTrue(historicActivityInstanceQueryImpl.isFinished());
    assertSame(historicActivityInstanceQueryImpl, actualFinishedResult);
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#unfinished()}.
   * <p>
   * Method under test: {@link HistoricActivityInstanceQueryImpl#unfinished()}
   */
  @Test
  public void testUnfinished() {
    // Arrange
    HistoricActivityInstanceQueryImpl historicActivityInstanceQueryImpl = new HistoricActivityInstanceQueryImpl();

    // Act
    HistoricActivityInstanceQueryImpl actualUnfinishedResult = historicActivityInstanceQueryImpl.unfinished();

    // Assert
    assertTrue(historicActivityInstanceQueryImpl.isUnfinished());
    assertSame(historicActivityInstanceQueryImpl, actualUnfinishedResult);
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#activityTenantId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link HistoricActivityInstanceQueryImpl} TenantId is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#activityTenantId(String)}
   */
  @Test
  public void testActivityTenantId_when42_thenHistoricActivityInstanceQueryImplTenantIdIs42() {
    // Arrange and Act
    HistoricActivityInstanceQueryImpl actualActivityTenantIdResult = historicActivityInstanceQueryImpl
        .activityTenantId("42");

    // Assert
    assertEquals("42", historicActivityInstanceQueryImpl.getTenantId());
    assertSame(historicActivityInstanceQueryImpl, actualActivityTenantIdResult);
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#activityTenantId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#activityTenantId(String)}
   */
  @Test
  public void testActivityTenantId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicActivityInstanceQueryImpl.activityTenantId(null));
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#activityTenantIdLike(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#activityTenantIdLike(String)}
   */
  @Test
  public void testActivityTenantIdLike() {
    // Arrange and Act
    HistoricActivityInstanceQueryImpl actualActivityTenantIdLikeResult = historicActivityInstanceQueryImpl
        .activityTenantIdLike("Tenant Id Like");

    // Assert
    assertEquals("Tenant Id Like", historicActivityInstanceQueryImpl.getTenantIdLike());
    assertSame(historicActivityInstanceQueryImpl, actualActivityTenantIdLikeResult);
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#activityTenantIdLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#activityTenantIdLike(String)}
   */
  @Test
  public void testActivityTenantIdLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicActivityInstanceQueryImpl.activityTenantIdLike(null));
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#activityWithoutTenantId()}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#activityWithoutTenantId()}
   */
  @Test
  public void testActivityWithoutTenantId() {
    // Arrange
    HistoricActivityInstanceQueryImpl historicActivityInstanceQueryImpl = new HistoricActivityInstanceQueryImpl();

    // Act
    HistoricActivityInstanceQueryImpl actualActivityWithoutTenantIdResult = historicActivityInstanceQueryImpl
        .activityWithoutTenantId();

    // Assert
    assertTrue(historicActivityInstanceQueryImpl.isWithoutTenantId());
    assertSame(historicActivityInstanceQueryImpl, actualActivityWithoutTenantIdResult);
  }

  /**
   * Test {@link HistoricActivityInstanceQueryImpl#activityInstanceId(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryImpl#activityInstanceId(String)}
   */
  @Test
  public void testActivityInstanceId() {
    // Arrange and Act
    HistoricActivityInstanceQueryImpl actualActivityInstanceIdResult = historicActivityInstanceQueryImpl
        .activityInstanceId("42");

    // Assert
    assertEquals("42", historicActivityInstanceQueryImpl.getActivityInstanceId());
    assertSame(historicActivityInstanceQueryImpl, actualActivityInstanceIdResult);
  }
}
