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
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.util.json.JSONObject;
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
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEqualsWithNameValue() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .variableValueEquals("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEqualsWithNameValue2() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .variableValueEquals("Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)}
   */
  @Test
  public void testVariableValueEqualsWithNameValueLocalScope() {
    // Arrange and Act
    ExecutionQuery actualVariableValueEqualsResult = executionQueryImpl.variableValueEquals("Name", JSONObject.NULL,
        true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)}
   */
  @Test
  public void testVariableValueEqualsWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueEquals(null, JSONObject.NULL, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)}
   */
  @Test
  public void testVariableValueEqualsWithNameValueLocalScope3() {
    // Arrange and Act
    ExecutionQuery actualVariableValueEqualsResult = executionQueryImpl.variableValueEquals("Name", null, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)}
   * with {@code name}, {@code value}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  public void testVariableValueEqualsWithNameValue_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueEquals(null, JSONObject.NULL));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(Object, boolean)}
   * with {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(Object, boolean)}
   */
  @Test
  public void testVariableValueEqualsWithValueLocalScope() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueEqualsResult = executionQueryImpl.variableValueEquals(JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(Object)} with
   * {@code value}.
   * <ul>
   *   <li>Then return OrderBy is {@code RES.ID_ asc}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEquals(Object)}
   */
  @Test
  public void testVariableValueEqualsWithValue_thenReturnOrderByIsResIdAsc() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueEqualsResult = (new HistoricTaskInstanceQueryImpl())
        .variableValueEquals(JSONObject.NULL);

    // Assert
    assertTrue(actualVariableValueEqualsResult instanceof HistoricTaskInstanceQueryImpl);
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualVariableValueEqualsResult).getOrderBy());
    assertEquals("RES.ID_ asc", ((HistoricTaskInstanceQueryImpl) actualVariableValueEqualsResult).getOrderByColumns());
    assertEquals("TEMPRES_ID_ asc",
        ((HistoricTaskInstanceQueryImpl) actualVariableValueEqualsResult).getMssqlOrDB2OrderBy());
    assertNull(((HistoricTaskInstanceQueryImpl) actualVariableValueEqualsResult).orderBy);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCaseWithNameValue() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .variableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCaseWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueEqualsIgnoreCase(null, "42"));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCaseWithNameValue3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueEqualsIgnoreCase("Name", null));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCaseWithNameValueLocalScope() {
    // Arrange and Act
    ExecutionQuery actualVariableValueEqualsIgnoreCaseResult = executionQueryImpl.variableValueEqualsIgnoreCase("Name",
        "42", true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCaseWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueEqualsIgnoreCase(null, "42", true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueEqualsIgnoreCaseWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueEqualsIgnoreCase("Name", null, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCaseWithNameValue() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueNotEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .variableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCaseWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueNotEqualsIgnoreCase(null, "42"));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCaseWithNameValue3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueNotEqualsIgnoreCase("Name", null));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCaseWithNameValueLocalScope() {
    // Arrange and Act
    ExecutionQuery actualVariableValueNotEqualsIgnoreCaseResult = executionQueryImpl
        .variableValueNotEqualsIgnoreCase("Name", "42", true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCaseWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueNotEqualsIgnoreCase(null, "42", true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueNotEqualsIgnoreCaseWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueNotEqualsIgnoreCase("Name", null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEqualsWithNameValue() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .variableValueNotEquals("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEqualsWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueNotEquals(null, JSONObject.NULL));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  public void testVariableValueNotEqualsWithNameValue3() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .variableValueNotEquals("Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)}
   */
  @Test
  public void testVariableValueNotEqualsWithNameValueLocalScope() {
    // Arrange and Act
    ExecutionQuery actualVariableValueNotEqualsResult = executionQueryImpl.variableValueNotEquals("Name",
        JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)}
   */
  @Test
  public void testVariableValueNotEqualsWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueNotEquals(null, JSONObject.NULL, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)}
   */
  @Test
  public void testVariableValueNotEqualsWithNameValueLocalScope3() {
    // Arrange and Act
    ExecutionQuery actualVariableValueNotEqualsResult = executionQueryImpl.variableValueNotEquals("Name", null, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThanWithNameValue() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueGreaterThanResult = historicTaskInstanceQueryImpl
        .variableValueGreaterThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueGreaterThanResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThanWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueGreaterThan(null, JSONObject.NULL));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThanWithNameValue3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueGreaterThan("Name", null));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)}
   */
  @Test
  public void testVariableValueGreaterThanWithNameValueLocalScope() {
    // Arrange and Act
    ExecutionQuery actualVariableValueGreaterThanResult = executionQueryImpl.variableValueGreaterThan("Name",
        JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueGreaterThanResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)}
   */
  @Test
  public void testVariableValueGreaterThanWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueGreaterThan(null, JSONObject.NULL, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)}
   */
  @Test
  public void testVariableValueGreaterThanWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueGreaterThan("Name", null, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqualWithNameValue() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueGreaterThanOrEqualResult = historicTaskInstanceQueryImpl
        .variableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqualWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueGreaterThanOrEqual(null, JSONObject.NULL));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqualWithNameValue3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueGreaterThanOrEqual("Name", null));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqualWithNameValueLocalScope() {
    // Arrange and Act
    ExecutionQuery actualVariableValueGreaterThanOrEqualResult = executionQueryImpl
        .variableValueGreaterThanOrEqual("Name", JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqualWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueGreaterThanOrEqual(null, JSONObject.NULL, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)}
   */
  @Test
  public void testVariableValueGreaterThanOrEqualWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueGreaterThanOrEqual("Name", null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)}
   */
  @Test
  public void testVariableValueLessThanWithNameValue() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueLessThanResult = historicTaskInstanceQueryImpl
        .variableValueLessThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueLessThanResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)}
   */
  @Test
  public void testVariableValueLessThanWithNameValueLocalScope() {
    // Arrange and Act
    ExecutionQuery actualVariableValueLessThanResult = executionQueryImpl.variableValueLessThan("Name", JSONObject.NULL,
        true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueLessThanResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)}
   */
  @Test
  public void testVariableValueLessThanWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueLessThan(null, JSONObject.NULL, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)}
   */
  @Test
  public void testVariableValueLessThanWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueLessThan("Name", null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)}
   * with {@code name}, {@code value}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)}
   */
  @Test
  public void testVariableValueLessThanWithNameValue_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLessThan(null, JSONObject.NULL));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLessThan("Name", null));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueLessThanOrEqualWithNameValue() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueLessThanOrEqualResult = historicTaskInstanceQueryImpl
        .variableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueLessThanOrEqualResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueLessThanOrEqualWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLessThanOrEqual(null, JSONObject.NULL));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  public void testVariableValueLessThanOrEqualWithNameValue3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLessThanOrEqual("Name", null));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)}
   */
  @Test
  public void testVariableValueLessThanOrEqualWithNameValueLocalScope() {
    // Arrange and Act
    ExecutionQuery actualVariableValueLessThanOrEqualResult = executionQueryImpl.variableValueLessThanOrEqual("Name",
        JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueLessThanOrEqualResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)}
   */
  @Test
  public void testVariableValueLessThanOrEqualWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueLessThanOrEqual(null, JSONObject.NULL, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)}
   */
  @Test
  public void testVariableValueLessThanOrEqualWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueLessThanOrEqual("Name", null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLike(String, String)} with
   * {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String)}
   */
  @Test
  public void testVariableValueLikeWithNameValue() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueLikeResult = historicTaskInstanceQueryImpl.variableValueLike("Name",
        "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueLikeResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)}
   */
  @Test
  public void testVariableValueLikeWithNameValueLocalScope() {
    // Arrange and Act
    ExecutionQuery actualVariableValueLikeResult = executionQueryImpl.variableValueLike("Name", "42", true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueLikeResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)}
   */
  @Test
  public void testVariableValueLikeWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> executionQueryImpl.variableValueLike(null, "42", true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)}
   */
  @Test
  public void testVariableValueLikeWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueLike("Name", null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLike(String, String)} with
   * {@code name}, {@code value}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLike(String, String)}
   */
  @Test
  public void testVariableValueLikeWithNameValue_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLike(null, "42"));
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLike("Name", null));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueLikeIgnoreCaseWithNameValue() {
    // Arrange and Act
    HistoricTaskInstanceQuery actualVariableValueLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .variableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String)}
   * with {@code name}, {@code value}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  public void testVariableValueLikeIgnoreCaseWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> historicTaskInstanceQueryImpl.variableValueLikeIgnoreCase(null, "42"));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueLikeIgnoreCaseWithNameValueLocalScope() {
    // Arrange and Act
    ExecutionQuery actualVariableValueLikeIgnoreCaseResult = executionQueryImpl.variableValueLikeIgnoreCase("Name",
        "42", true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String, boolean)}
   * with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String, boolean)}
   */
  @Test
  public void testVariableValueLikeIgnoreCaseWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.variableValueLikeIgnoreCase(null, "42", true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>Then {@link ExecutionQueryImpl} QueryVariableValues first Operator is
   * {@code EQUALS}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable_thenExecutionQueryImplQueryVariableValuesFirstOperatorIsEquals() {
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
   * Test
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>Then {@link ExecutionQueryImpl} QueryVariableValues first Operator is
   * {@code EQUALS}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable_thenExecutionQueryImplQueryVariableValuesFirstOperatorIsEquals2() {
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
   * Test
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code EQUALS_IGNORE_CASE}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable_whenEqualsIgnoreCase_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.EQUALS_IGNORE_CASE, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code GREATER_THAN_OR_EQUAL}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable_whenGreaterThanOrEqual_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.GREATER_THAN_OR_EQUAL, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code GREATER_THAN}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable_whenGreaterThan_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.GREATER_THAN, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code LESS_THAN_OR_EQUAL}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable_whenLessThanOrEqual_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.LESS_THAN_OR_EQUAL, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code LESS_THAN}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable_whenLessThan_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.LESS_THAN, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code LIKE_IGNORE_CASE}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable_whenLikeIgnoreCase_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.LIKE_IGNORE_CASE, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code LIKE}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable_whenLike_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.LIKE, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code NOT_EQUALS_IGNORE_CASE}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable_whenNotEqualsIgnoreCase() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable("Name", null, QueryOperator.NOT_EQUALS_IGNORE_CASE, true));
  }

  /**
   * Test
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  public void testAddVariable_whenNull_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> executionQueryImpl.addVariable(null, JSONObject.NULL, QueryOperator.EQUALS, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#isBoolean(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#isBoolean(Object)}
   */
  @Test
  public void testIsBoolean_whenNull() {
    // Arrange, Act and Assert
    assertFalse((new ExecutionQueryImpl()).isBoolean(JSONObject.NULL));
    assertFalse((new ExecutionQueryImpl()).isBoolean(null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#getQueryVariableValues()}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#getQueryVariableValues()}
   */
  @Test
  public void testGetQueryVariableValues() {
    // Arrange, Act and Assert
    assertTrue((new ExecutionQueryImpl()).getQueryVariableValues().isEmpty());
  }

  /**
   * Test {@link AbstractVariableQueryImpl#hasLocalQueryVariableValue()}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#hasLocalQueryVariableValue()}
   */
  @Test
  public void testHasLocalQueryVariableValue() {
    // Arrange, Act and Assert
    assertFalse((new ExecutionQueryImpl()).hasLocalQueryVariableValue());
  }

  /**
   * Test {@link AbstractVariableQueryImpl#hasNonLocalQueryVariableValue()}.
   * <p>
   * Method under test:
   * {@link AbstractVariableQueryImpl#hasNonLocalQueryVariableValue()}
   */
  @Test
  public void testHasNonLocalQueryVariableValue() {
    // Arrange, Act and Assert
    assertFalse((new ExecutionQueryImpl()).hasNonLocalQueryVariableValue());
  }
}
