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
import java.util.HashSet;
import java.util.Set;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HistoricVariableInstanceQueryImplDiffblueTest {
  @InjectMocks
  private HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl;

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId() {
    // Arrange and Act
    HistoricVariableInstanceQueryImpl actualProcessInstanceIdResult = historicVariableInstanceQueryImpl
        .processInstanceId("42");

    // Assert
    assertEquals("42", historicVariableInstanceQueryImpl.getProcessInstanceId());
    assertSame(historicVariableInstanceQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.processInstanceId(null));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicVariableInstanceQueryImpl.executionId(null));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}
   */
  @Test
  public void testExecutionIds() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl = new HistoricVariableInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.executionIds(new HashSet<>()));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}
   */
  @Test
  public void testExecutionIds2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricVariableInstanceQueryImpl()).executionIds(null));
  }

  /**
   * Method under test: {@link HistoricVariableInstanceQueryImpl#taskId(String)}
   */
  @Test
  public void testTaskId() {
    // Arrange and Act
    HistoricVariableInstanceQuery actualTaskIdResult = historicVariableInstanceQueryImpl.taskId("42");

    // Assert
    assertEquals("42", historicVariableInstanceQueryImpl.getTaskId());
    assertSame(historicVariableInstanceQueryImpl, actualTaskIdResult);
  }

  /**
   * Method under test: {@link HistoricVariableInstanceQueryImpl#taskId(String)}
   */
  @Test
  public void testTaskId2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicVariableInstanceQueryImpl.taskId(null));
  }

  /**
   * Method under test: {@link HistoricVariableInstanceQueryImpl#taskIds(Set)}
   */
  @Test
  public void testTaskIds() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl = new HistoricVariableInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.taskIds(new HashSet<>()));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#excludeTaskVariables()}
   */
  @Test
  public void testExcludeTaskVariables() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl = new HistoricVariableInstanceQueryImpl();

    // Act
    HistoricVariableInstanceQuery actualExcludeTaskVariablesResult = historicVariableInstanceQueryImpl
        .excludeTaskVariables();

    // Assert
    assertTrue(historicVariableInstanceQueryImpl.getExcludeTaskRelated());
    assertSame(historicVariableInstanceQueryImpl, actualExcludeTaskVariablesResult);
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableName(String)}
   */
  @Test
  public void testVariableName() {
    // Arrange and Act
    HistoricVariableInstanceQuery actualVariableNameResult = historicVariableInstanceQueryImpl
        .variableName("Variable Name");

    // Assert
    assertEquals("Variable Name", historicVariableInstanceQueryImpl.getVariableName());
    assertSame(historicVariableInstanceQueryImpl, actualVariableNameResult);
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableName(String)}
   */
  @Test
  public void testVariableName2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicVariableInstanceQueryImpl.variableName(null));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEquals() {
    // Arrange and Act
    HistoricVariableInstanceQuery actualVariableValueEqualsResult = historicVariableInstanceQueryImpl
        .variableValueEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertEquals("Variable Name", historicVariableInstanceQueryImpl.getVariableName());
    assertSame(historicVariableInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueEquals(null, JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEquals3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueEquals("Variable Name", null));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEquals() {
    // Arrange and Act
    HistoricVariableInstanceQuery actualVariableValueNotEqualsResult = historicVariableInstanceQueryImpl
        .variableValueNotEquals("Variable Name", JSONObject.NULL);

    // Assert
    assertEquals("Variable Name", historicVariableInstanceQueryImpl.getVariableName());
    assertSame(historicVariableInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueNotEquals(null, JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEquals3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueNotEquals("Variable Name", null));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueLike(String, String)}
   */
  @Test
  public void testVariableValueLike() {
    // Arrange and Act
    HistoricVariableInstanceQuery actualVariableValueLikeResult = historicVariableInstanceQueryImpl
        .variableValueLike("Variable Name", "42");

    // Assert
    assertEquals("Variable Name", historicVariableInstanceQueryImpl.getVariableName());
    assertSame(historicVariableInstanceQueryImpl, actualVariableValueLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueLike(String, String)}
   */
  @Test
  public void testVariableValueLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueLike(null, "42"));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueLike(String, String)}
   */
  @Test
  public void testVariableValueLike3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueLike("Variable Name", null));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueLikeIgnoreCase() {
    // Arrange and Act
    HistoricVariableInstanceQuery actualVariableValueLikeIgnoreCaseResult = historicVariableInstanceQueryImpl
        .variableValueLikeIgnoreCase("Variable Name", "42");

    // Assert
    assertEquals("Variable Name", historicVariableInstanceQueryImpl.getVariableName());
    assertSame(historicVariableInstanceQueryImpl, actualVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueLikeIgnoreCase2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueLikeIgnoreCase(null, "42"));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueLikeIgnoreCase3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueLikeIgnoreCase("Variable Name", null));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableNameLike(String)}
   */
  @Test
  public void testVariableNameLike() {
    // Arrange and Act
    HistoricVariableInstanceQuery actualVariableNameLikeResult = historicVariableInstanceQueryImpl
        .variableNameLike("Variable Name Like");

    // Assert
    assertEquals("Variable Name Like", historicVariableInstanceQueryImpl.getVariableNameLike());
    assertSame(historicVariableInstanceQueryImpl, actualVariableNameLikeResult);
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableNameLike(String)}
   */
  @Test
  public void testVariableNameLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableNameLike(null));
  }

  /**
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()}
   */
  @Test
  public void testNewHistoricVariableInstanceQueryImpl() {
    // Arrange and Act
    HistoricVariableInstanceQueryImpl actualHistoricVariableInstanceQueryImpl = new HistoricVariableInstanceQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualHistoricVariableInstanceQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualHistoricVariableInstanceQueryImpl.getOrderByColumns());
    assertNull(actualHistoricVariableInstanceQueryImpl.getDatabaseType());
    assertNull(actualHistoricVariableInstanceQueryImpl.getActivityInstanceId());
    assertNull(actualHistoricVariableInstanceQueryImpl.getProcessInstanceId());
    assertNull(actualHistoricVariableInstanceQueryImpl.getTaskId());
    assertNull(actualHistoricVariableInstanceQueryImpl.getVariableName());
    assertNull(actualHistoricVariableInstanceQueryImpl.getVariableNameLike());
    assertNull(actualHistoricVariableInstanceQueryImpl.orderBy);
    assertNull(actualHistoricVariableInstanceQueryImpl.executionId);
    assertNull(actualHistoricVariableInstanceQueryImpl.id);
    assertNull(actualHistoricVariableInstanceQueryImpl.executionIds);
    assertNull(actualHistoricVariableInstanceQueryImpl.taskIds);
    assertNull(actualHistoricVariableInstanceQueryImpl.nullHandlingOnOrder);
    assertNull(actualHistoricVariableInstanceQueryImpl.resultType);
    assertNull(actualHistoricVariableInstanceQueryImpl.getQueryVariableValue());
    assertNull(actualHistoricVariableInstanceQueryImpl.commandContext);
    assertNull(actualHistoricVariableInstanceQueryImpl.commandExecutor);
    assertNull(actualHistoricVariableInstanceQueryImpl.orderProperty);
    assertEquals(0, actualHistoricVariableInstanceQueryImpl.getFirstResult());
    assertEquals(1, actualHistoricVariableInstanceQueryImpl.getFirstRow());
    assertFalse(actualHistoricVariableInstanceQueryImpl.getExcludeTaskRelated());
    assertFalse(actualHistoricVariableInstanceQueryImpl.excludeVariableInitialization);
    assertEquals(Integer.MAX_VALUE, actualHistoricVariableInstanceQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualHistoricVariableInstanceQueryImpl.getMaxResults());
    assertSame(actualHistoricVariableInstanceQueryImpl, actualHistoricVariableInstanceQueryImpl.getParameter());
  }
}
