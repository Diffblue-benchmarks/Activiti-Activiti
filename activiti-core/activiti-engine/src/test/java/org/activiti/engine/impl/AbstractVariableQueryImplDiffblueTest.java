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
import static org.mockito.Mockito.mock;
import java.util.List;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.query.QueryProperty;
import org.activiti.engine.runtime.ExecutionQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractVariableQueryImplDiffblueTest {
  @InjectMocks
  private HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl;

  @InjectMocks
  private ExecutionQueryImpl executionQueryImpl;

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(Object, boolean)}
   */
  @Test
  public void testVariableValueEquals() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueEqualsResult = executionQueryImpl.variableValueEquals(JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)}
   */
  @Test
  public void testVariableValueEquals2() {
    // Arrange and Act
    ExecutionQuery actualVariableValueEqualsResult = executionQueryImpl.variableValueEquals("Name", JSONObject.NULL,
        true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)}
   */
  @Test
  public void testVariableValueEquals3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueEquals(null, JSONObject.NULL, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)}
   */
  @Test
  public void testVariableValueEquals4() {
    // Arrange and Act
    ExecutionQuery actualVariableValueEqualsResult = executionQueryImpl.variableValueEquals("Name", null, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(Object)}
   */
  @Test
  public void testVariableValueEquals5() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .variableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(Object)}
   */
  @Test
  public void testVariableValueEquals6() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();
    historicTaskInstanceQueryImpl.orderBy(mock(QueryProperty.class));

    // Act
    HistoricTaskInstanceQuery actualVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .variableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEquals7() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .variableValueEquals("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEquals8() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueEquals(null, JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEquals9() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .variableValueEquals("Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCase() {
    // Arrange and Act
    ExecutionQuery actualVariableValueEqualsIgnoreCaseResult = executionQueryImpl.variableValueEqualsIgnoreCase("Name",
        "42", true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCase2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueEqualsIgnoreCase(null, "42", true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCase3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueEqualsIgnoreCase("Name", null, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCase4() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .variableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCase5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueEqualsIgnoreCase(null, "42"));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCase6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueEqualsIgnoreCase("Name", null));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCase() {
    // Arrange and Act
    ExecutionQuery actualVariableValueNotEqualsIgnoreCaseResult = executionQueryImpl
        .variableValueNotEqualsIgnoreCase("Name", "42", true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCase2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueNotEqualsIgnoreCase(null, "42", true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCase3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueNotEqualsIgnoreCase("Name", null, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCase4() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueNotEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .variableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCase5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueNotEqualsIgnoreCase(null, "42"));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCase6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueNotEqualsIgnoreCase("Name", null));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)}
   */
  @Test
  public void testVariableValueNotEquals() {
    // Arrange and Act
    ExecutionQuery actualVariableValueNotEqualsResult = executionQueryImpl.variableValueNotEquals("Name",
        JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)}
   */
  @Test
  public void testVariableValueNotEquals2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueNotEquals(null, JSONObject.NULL, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)}
   */
  @Test
  public void testVariableValueNotEquals3() {
    // Arrange and Act
    ExecutionQuery actualVariableValueNotEqualsResult = executionQueryImpl.variableValueNotEquals("Name", null, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEquals4() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .variableValueNotEquals("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEquals5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueNotEquals(null, JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEquals6() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .variableValueNotEquals("Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)}
   */
  @Test
  public void testVariableValueGreaterThan() {
    // Arrange and Act
    ExecutionQuery actualVariableValueGreaterThanResult = executionQueryImpl.variableValueGreaterThan("Name",
        JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueGreaterThanResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)}
   */
  @Test
  public void testVariableValueGreaterThan2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueGreaterThan(null, JSONObject.NULL, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)}
   */
  @Test
  public void testVariableValueGreaterThan3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueGreaterThan("Name", null, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThan4() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueGreaterThanResult = historicTaskInstanceQueryImpl
        .variableValueGreaterThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueGreaterThanResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThan5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueGreaterThan(null, JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThan6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueGreaterThan("Name", null));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqual() {
    // Arrange and Act
    ExecutionQuery actualVariableValueGreaterThanOrEqualResult = executionQueryImpl
        .variableValueGreaterThanOrEqual("Name", JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqual2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueGreaterThanOrEqual(null, JSONObject.NULL, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqual3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueGreaterThanOrEqual("Name", null, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqual4() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueGreaterThanOrEqualResult = historicTaskInstanceQueryImpl
        .variableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqual5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueGreaterThanOrEqual(null, JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqual6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueGreaterThanOrEqual("Name", null));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)}
   */
  @Test
  public void testVariableValueLessThan() {
    // Arrange and Act
    ExecutionQuery actualVariableValueLessThanResult = executionQueryImpl.variableValueLessThan("Name", JSONObject.NULL,
        true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueLessThanResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)}
   */
  @Test
  public void testVariableValueLessThan2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueLessThan(null, JSONObject.NULL, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)}
   */
  @Test
  public void testVariableValueLessThan3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueLessThan("Name", null, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)}
   */
  @Test
  public void testVariableValueLessThan4() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueLessThanResult = historicTaskInstanceQueryImpl
        .variableValueLessThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueLessThanResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)}
   */
  @Test
  public void testVariableValueLessThan5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLessThan(null, JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)}
   */
  @Test
  public void testVariableValueLessThan6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLessThan("Name", null));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)}
   */
  @Test
  public void testVariableValueLessThanOrEqual() {
    // Arrange and Act
    ExecutionQuery actualVariableValueLessThanOrEqualResult = executionQueryImpl.variableValueLessThanOrEqual("Name",
        JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueLessThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)}
   */
  @Test
  public void testVariableValueLessThanOrEqual2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueLessThanOrEqual(null, JSONObject.NULL, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)}
   */
  @Test
  public void testVariableValueLessThanOrEqual3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueLessThanOrEqual("Name", null, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueLessThanOrEqual4() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueLessThanOrEqualResult = historicTaskInstanceQueryImpl
        .variableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueLessThanOrEqualResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueLessThanOrEqual5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLessThanOrEqual(null, JSONObject.NULL));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueLessThanOrEqual6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLessThanOrEqual("Name", null));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)}
   */
  @Test
  public void testVariableValueLike() {
    // Arrange and Act
    ExecutionQuery actualVariableValueLikeResult = executionQueryImpl.variableValueLike("Name", "42", true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueLikeResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)}
   */
  @Test
  public void testVariableValueLike2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.variableValueLike(null, "42", true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)}
   */
  @Test
  public void testVariableValueLike3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueLike("Name", null, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String)}
   */
  @Test
  public void testVariableValueLike4() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueLikeResult = historicTaskInstanceQueryImpl.variableValueLike("Name",
        "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueLikeResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String)}
   */
  @Test
  public void testVariableValueLike5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLike(null, "42"));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String)}
   */
  @Test
  public void testVariableValueLike6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLike("Name", null));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueLikeIgnoreCase() {
    // Arrange and Act
    ExecutionQuery actualVariableValueLikeIgnoreCaseResult = executionQueryImpl.variableValueLikeIgnoreCase("Name",
        "42", true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueLikeIgnoreCase2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueLikeIgnoreCase(null, "42", true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueLikeIgnoreCase3() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .variableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueLikeIgnoreCase4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLikeIgnoreCase(null, "42"));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable() {
    // Arrange and Act
    executionQueryImpl.addVariable("Name", JSONObject.NULL, QueryOperator.EQUALS, true);

    // Assert
    QueryVariableValue getResult = executionQueryImpl.getQueryVariableValues().get(0);
    assertEquals("EQUALS", getResult.getOperator());
    assertEquals("Name", getResult.getName());
    assertNull(getResult.getDoubleValue());
    assertNull(getResult.getLongValue());
    assertNull(getResult.getTextValue());
    assertNull(getResult.getTextValue2());
    assertNull(getResult.getType());
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertTrue(getResult.isLocal());
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable(null, JSONObject.NULL, QueryOperator.EQUALS, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable3() {
    // Arrange and Act
    executionQueryImpl.addVariable("Name", null, QueryOperator.EQUALS, true);

    // Assert
    QueryVariableValue getResult = executionQueryImpl.getQueryVariableValues().get(0);
    assertEquals("EQUALS", getResult.getOperator());
    assertEquals("Name", getResult.getName());
    assertNull(getResult.getDoubleValue());
    assertNull(getResult.getLongValue());
    assertNull(getResult.getTextValue());
    assertNull(getResult.getTextValue2());
    assertNull(getResult.getType());
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertTrue(getResult.isLocal());
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable4() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.GREATER_THAN, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable5() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.GREATER_THAN_OR_EQUAL, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable6() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.LESS_THAN, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable7() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.LESS_THAN_OR_EQUAL, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable8() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.LIKE, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable9() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.EQUALS_IGNORE_CASE, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable10() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.NOT_EQUALS_IGNORE_CASE, true));
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable11() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.LIKE_IGNORE_CASE, true));
  }

  /**
   * Method under test: {@link AbstractVariableQueryImpl#isBoolean(Object)}
   */
  @Test
  public void testIsBoolean() {
    // Arrange, Act and Assert
    assertFalse((new ExecutionQueryImpl()).isBoolean(JSONObject.NULL));
    assertFalse((new ExecutionQueryImpl()).isBoolean(null));
  }

  /**
   * Method under test: {@link AbstractVariableQueryImpl#getQueryVariableValues()}
   */
  @Test
  public void testGetQueryVariableValues() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    List<QueryVariableValue> actualQueryVariableValues = executionQueryImpl.getQueryVariableValues();

    // Assert
    assertTrue(actualQueryVariableValues.isEmpty());
    assertSame(executionQueryImpl.queryVariableValues, actualQueryVariableValues);
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#hasLocalQueryVariableValue()}
   */
  @Test
  public void testHasLocalQueryVariableValue() {
    // Arrange, Act and Assert
    assertFalse((new ExecutionQueryImpl()).hasLocalQueryVariableValue());
  }

  /**
   * Method under test:
   * {@link AbstractVariableQueryImpl#hasNonLocalQueryVariableValue()}
   */
  @Test
  public void testHasNonLocalQueryVariableValue() {
    // Arrange, Act and Assert
    assertFalse((new ExecutionQueryImpl()).hasNonLocalQueryVariableValue());
  }
}
