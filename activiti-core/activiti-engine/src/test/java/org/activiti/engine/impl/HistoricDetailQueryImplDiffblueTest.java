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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class HistoricDetailQueryImplDiffblueTest {
  /**
   * Test {@link HistoricDetailQueryImpl#HistoricDetailQueryImpl()}.
   * <p>
   * Method under test: {@link HistoricDetailQueryImpl#HistoricDetailQueryImpl()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void HistoricDetailQueryImpl.<init>()"})
  public void testNewHistoricDetailQueryImpl() {
    // Arrange and Act
    HistoricDetailQueryImpl actualHistoricDetailQueryImpl = new HistoricDetailQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualHistoricDetailQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualHistoricDetailQueryImpl.getOrderByColumns());
    assertNull(actualHistoricDetailQueryImpl.getDatabaseType());
    assertNull(actualHistoricDetailQueryImpl.getActivityId());
    assertNull(actualHistoricDetailQueryImpl.getActivityInstanceId());
    assertNull(actualHistoricDetailQueryImpl.getExecutionId());
    assertNull(actualHistoricDetailQueryImpl.getId());
    assertNull(actualHistoricDetailQueryImpl.getProcessInstanceId());
    assertNull(actualHistoricDetailQueryImpl.getTaskId());
    assertNull(actualHistoricDetailQueryImpl.getType());
    assertNull(actualHistoricDetailQueryImpl.orderBy);
    assertNull(actualHistoricDetailQueryImpl.nullHandlingOnOrder);
    assertNull(actualHistoricDetailQueryImpl.resultType);
    assertNull(actualHistoricDetailQueryImpl.commandContext);
    assertNull(actualHistoricDetailQueryImpl.commandExecutor);
    assertNull(actualHistoricDetailQueryImpl.orderProperty);
    assertEquals(0, actualHistoricDetailQueryImpl.getFirstResult());
    assertEquals(1, actualHistoricDetailQueryImpl.getFirstRow());
    assertFalse(actualHistoricDetailQueryImpl.getExcludeTaskRelated());
    assertEquals(Integer.MAX_VALUE, actualHistoricDetailQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualHistoricDetailQueryImpl.getMaxResults());
    assertSame(actualHistoricDetailQueryImpl, actualHistoricDetailQueryImpl.getParameter());
  }

  /**
   * Test {@link HistoricDetailQueryImpl#id(String)}.
   * <p>
   * Method under test: {@link HistoricDetailQueryImpl#id(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricDetailQueryImpl HistoricDetailQueryImpl.id(String)"})
  public void testId() {
    // Arrange
    HistoricDetailQueryImpl historicDetailQueryImpl = new HistoricDetailQueryImpl();

    // Act
    HistoricDetailQueryImpl actualIdResult = historicDetailQueryImpl.id("42");

    // Assert
    assertEquals("42", historicDetailQueryImpl.getId());
    assertSame(historicDetailQueryImpl, actualIdResult);
  }

  /**
   * Test {@link HistoricDetailQueryImpl#processInstanceId(String)}.
   * <p>
   * Method under test: {@link HistoricDetailQueryImpl#processInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricDetailQueryImpl HistoricDetailQueryImpl.processInstanceId(String)"})
  public void testProcessInstanceId() {
    // Arrange
    HistoricDetailQueryImpl historicDetailQueryImpl = new HistoricDetailQueryImpl();

    // Act
    HistoricDetailQueryImpl actualProcessInstanceIdResult = historicDetailQueryImpl.processInstanceId("42");

    // Assert
    assertEquals("42", historicDetailQueryImpl.getProcessInstanceId());
    assertSame(historicDetailQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link HistoricDetailQueryImpl#executionId(String)}.
   * <p>
   * Method under test: {@link HistoricDetailQueryImpl#executionId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricDetailQueryImpl HistoricDetailQueryImpl.executionId(String)"})
  public void testExecutionId() {
    // Arrange
    HistoricDetailQueryImpl historicDetailQueryImpl = new HistoricDetailQueryImpl();

    // Act
    HistoricDetailQueryImpl actualExecutionIdResult = historicDetailQueryImpl.executionId("42");

    // Assert
    assertEquals("42", historicDetailQueryImpl.getExecutionId());
    assertSame(historicDetailQueryImpl, actualExecutionIdResult);
  }

  /**
   * Test {@link HistoricDetailQueryImpl#activityInstanceId(String)}.
   * <p>
   * Method under test: {@link HistoricDetailQueryImpl#activityInstanceId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricDetailQueryImpl HistoricDetailQueryImpl.activityInstanceId(String)"})
  public void testActivityInstanceId() {
    // Arrange
    HistoricDetailQueryImpl historicDetailQueryImpl = new HistoricDetailQueryImpl();

    // Act
    HistoricDetailQueryImpl actualActivityInstanceIdResult = historicDetailQueryImpl.activityInstanceId("42");

    // Assert
    assertEquals("42", historicDetailQueryImpl.getActivityInstanceId());
    assertSame(historicDetailQueryImpl, actualActivityInstanceIdResult);
  }

  /**
   * Test {@link HistoricDetailQueryImpl#taskId(String)}.
   * <p>
   * Method under test: {@link HistoricDetailQueryImpl#taskId(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricDetailQueryImpl HistoricDetailQueryImpl.taskId(String)"})
  public void testTaskId() {
    // Arrange
    HistoricDetailQueryImpl historicDetailQueryImpl = new HistoricDetailQueryImpl();

    // Act
    HistoricDetailQueryImpl actualTaskIdResult = historicDetailQueryImpl.taskId("42");

    // Assert
    assertEquals("42", historicDetailQueryImpl.getTaskId());
    assertSame(historicDetailQueryImpl, actualTaskIdResult);
  }

  /**
   * Test {@link HistoricDetailQueryImpl#variableUpdates()}.
   * <p>
   * Method under test: {@link HistoricDetailQueryImpl#variableUpdates()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricDetailQueryImpl HistoricDetailQueryImpl.variableUpdates()"})
  public void testVariableUpdates() {
    // Arrange
    HistoricDetailQueryImpl historicDetailQueryImpl = new HistoricDetailQueryImpl();

    // Act
    HistoricDetailQueryImpl actualVariableUpdatesResult = historicDetailQueryImpl.variableUpdates();

    // Assert
    assertEquals("VariableUpdate", historicDetailQueryImpl.getType());
    assertSame(historicDetailQueryImpl, actualVariableUpdatesResult);
  }

  /**
   * Test {@link HistoricDetailQueryImpl#excludeTaskDetails()}.
   * <p>
   * Method under test: {@link HistoricDetailQueryImpl#excludeTaskDetails()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"HistoricDetailQueryImpl HistoricDetailQueryImpl.excludeTaskDetails()"})
  public void testExcludeTaskDetails() {
    // Arrange
    HistoricDetailQueryImpl historicDetailQueryImpl = new HistoricDetailQueryImpl();

    // Act
    HistoricDetailQueryImpl actualExcludeTaskDetailsResult = historicDetailQueryImpl.excludeTaskDetails();

    // Assert
    assertTrue(historicDetailQueryImpl.getExcludeTaskRelated());
    assertSame(historicDetailQueryImpl, actualExcludeTaskDetailsResult);
  }
}
