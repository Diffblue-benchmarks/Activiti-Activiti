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
   * Test
   * {@link HistoricVariableInstanceQueryImpl#HistoricVariableInstanceQueryImpl()}.
   * <p>
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

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>Then {@link HistoricVariableInstanceQueryImpl} ProcessInstanceId is
   * {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId_thenHistoricVariableInstanceQueryImplProcessInstanceIdIs42() {
    // Arrange and Act
    HistoricVariableInstanceQueryImpl actualProcessInstanceIdResult = historicVariableInstanceQueryImpl
        .processInstanceId("42");

    // Assert
    assertEquals("42", historicVariableInstanceQueryImpl.getProcessInstanceId());
    assertSame(historicVariableInstanceQueryImpl, actualProcessInstanceIdResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#processInstanceId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#processInstanceId(String)}
   */
  @Test
  public void testProcessInstanceId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.processInstanceId(null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#executionId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#executionId(String)}
   */
  @Test
  public void testExecutionId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicVariableInstanceQueryImpl.executionId(null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}.
   * <ul>
   *   <li>When {@link HashSet#HashSet()}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}
   */
  @Test
  public void testExecutionIds_whenHashSet() {
    // Arrange
    HistoricVariableInstanceQueryImpl historicVariableInstanceQueryImpl = new HistoricVariableInstanceQueryImpl();

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.executionIds(new HashSet<>()));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#executionIds(Set)}
   */
  @Test
  public void testExecutionIds_whenNull() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricVariableInstanceQueryImpl()).executionIds(null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#taskId(String)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then {@link HistoricVariableInstanceQueryImpl} TaskId is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricVariableInstanceQueryImpl#taskId(String)}
   */
  @Test
  public void testTaskId_when42_thenHistoricVariableInstanceQueryImplTaskIdIs42() {
    // Arrange and Act
    HistoricVariableInstanceQuery actualTaskIdResult = historicVariableInstanceQueryImpl.taskId("42");

    // Assert
    assertEquals("42", historicVariableInstanceQueryImpl.getTaskId());
    assertSame(historicVariableInstanceQueryImpl, actualTaskIdResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#taskId(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link HistoricVariableInstanceQueryImpl#taskId(String)}
   */
  @Test
  public void testTaskId_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicVariableInstanceQueryImpl.taskId(null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#taskIds(Set)}.
   * <p>
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
   * Test {@link HistoricVariableInstanceQueryImpl#excludeTaskVariables()}.
   * <p>
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
   * Test {@link HistoricVariableInstanceQueryImpl#variableName(String)}.
   * <ul>
   *   <li>Then {@link HistoricVariableInstanceQueryImpl} VariableName is
   * {@code Variable Name}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableName(String)}
   */
  @Test
  public void testVariableName_thenHistoricVariableInstanceQueryImplVariableNameIsVariableName() {
    // Arrange and Act
    HistoricVariableInstanceQuery actualVariableNameResult = historicVariableInstanceQueryImpl
        .variableName("Variable Name");

    // Assert
    assertEquals("Variable Name", historicVariableInstanceQueryImpl.getVariableName());
    assertSame(historicVariableInstanceQueryImpl, actualVariableNameResult);
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableName(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableName(String)}
   */
  @Test
  public void testVariableName_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> historicVariableInstanceQueryImpl.variableName(null));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String, Object)}.
   * <p>
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
   * Test
   * {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String, Object)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEquals_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueEquals("Variable Name", null));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueEquals(null, JSONObject.NULL));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String, Object)}.
   * <p>
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
   * Test
   * {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String, Object)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEquals_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueNotEquals("Variable Name", null));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String, Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEquals_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueNotEquals(null, JSONObject.NULL));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceQueryImpl#variableValueLike(String, String)}.
   * <p>
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
   * Test
   * {@link HistoricVariableInstanceQueryImpl#variableValueLike(String, String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueLike(String, String)}
   */
  @Test
  public void testVariableValueLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueLike(null, "42"));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceQueryImpl#variableValueLike(String, String)}.
   * <ul>
   *   <li>When {@code Variable Name}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueLike(String, String)}
   */
  @Test
  public void testVariableValueLike_whenVariableName_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueLike("Variable Name", null));
  }

  /**
   * Test
   * {@link HistoricVariableInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}.
   * <p>
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
   * Test
   * {@link HistoricVariableInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueLikeIgnoreCase_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueLikeIgnoreCase(null, "42"));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableValueLikeIgnoreCase("Variable Name", null));
  }

  /**
   * Test {@link HistoricVariableInstanceQueryImpl#variableNameLike(String)}.
   * <p>
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
   * Test {@link HistoricVariableInstanceQueryImpl#variableNameLike(String)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link HistoricVariableInstanceQueryImpl#variableNameLike(String)}
   */
  @Test
  public void testVariableNameLike_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicVariableInstanceQueryImpl.variableNameLike(null));
  }
}
