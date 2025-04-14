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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.List;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.ExecutionQuery;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class AbstractVariableQueryImplDiffblueTest {
  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEquals(String, Object)"})
  public void testVariableValueEqualsWithNameValue() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .variableValueEquals("Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEquals(String, Object, boolean)"})
  public void testVariableValueEqualsWithNameValueLocalScope() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueEquals(null, null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEquals(String, Object, boolean)"})
  public void testVariableValueEqualsWithNameValueLocalScope2() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueEqualsResult = executionQueryImpl.variableValueEquals("Name", null, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEquals(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEquals(String, Object, boolean)"})
  public void testVariableValueEqualsWithNameValueLocalScope_whenNull() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueEqualsResult = executionQueryImpl.variableValueEquals("Name", JSONObject.NULL,
        true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)} with {@code name}, {@code value}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEquals(String, Object)"})
  public void testVariableValueEqualsWithNameValue_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueEquals(null, null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)} with {@code name}, {@code value}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEquals(String, Object)"})
  public void testVariableValueEqualsWithNameValue_whenNull() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueEqualsResult = historicTaskInstanceQueryImpl
        .variableValueEquals("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueEqualsResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(Object)} with {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEquals(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEquals(Object)"})
  public void testVariableValueEqualsWithValue() {
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
   * Test {@link AbstractVariableQueryImpl#variableValueEquals(Object, boolean)} with {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEquals(Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEquals(Object, boolean)"})
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
   * Test {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEqualsIgnoreCase(String, String)"})
  public void testVariableValueEqualsIgnoreCaseWithNameValue() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .variableValueEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEqualsIgnoreCase(String, String)"})
  public void testVariableValueEqualsIgnoreCaseWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueEqualsIgnoreCase(null, null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEqualsIgnoreCase(String, String)"})
  public void testVariableValueEqualsIgnoreCaseWithNameValue3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueEqualsIgnoreCase(null, "42"));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEqualsIgnoreCase(String, String, boolean)"})
  public void testVariableValueEqualsIgnoreCaseWithNameValueLocalScope() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueEqualsIgnoreCaseResult = executionQueryImpl.variableValueEqualsIgnoreCase("Name",
        "42", true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEqualsIgnoreCase(String, String, boolean)"})
  public void testVariableValueEqualsIgnoreCaseWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueEqualsIgnoreCase(null, null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueEqualsIgnoreCase(String, String, boolean)"})
  public void testVariableValueEqualsIgnoreCaseWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueEqualsIgnoreCase(null, "42", true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueNotEqualsIgnoreCase(String, String)"})
  public void testVariableValueNotEqualsIgnoreCaseWithNameValue() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueNotEqualsIgnoreCaseResult = historicTaskInstanceQueryImpl
        .variableValueNotEqualsIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueNotEqualsIgnoreCase(String, String)"})
  public void testVariableValueNotEqualsIgnoreCaseWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueNotEqualsIgnoreCase(null, null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueNotEqualsIgnoreCase(String, String)"})
  public void testVariableValueNotEqualsIgnoreCaseWithNameValue3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueNotEqualsIgnoreCase(null, "42"));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueNotEqualsIgnoreCase(String, String, boolean)"})
  public void testVariableValueNotEqualsIgnoreCaseWithNameValueLocalScope() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueNotEqualsIgnoreCaseResult = executionQueryImpl
        .variableValueNotEqualsIgnoreCase("Name", "42", true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueNotEqualsIgnoreCaseResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueNotEqualsIgnoreCase(String, String, boolean)"})
  public void testVariableValueNotEqualsIgnoreCaseWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueNotEqualsIgnoreCase(null, null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueNotEqualsIgnoreCase(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueNotEqualsIgnoreCase(String, String, boolean)"})
  public void testVariableValueNotEqualsIgnoreCaseWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueNotEqualsIgnoreCase(null, "42", true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueNotEquals(String, Object)"})
  public void testVariableValueNotEqualsWithNameValue() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueNotEquals(null, null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueNotEquals(String, Object)"})
  public void testVariableValueNotEqualsWithNameValue2() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .variableValueNotEquals("Name", null);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueNotEquals(String, Object, boolean)"})
  public void testVariableValueNotEqualsWithNameValueLocalScope() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueNotEquals(null, null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueNotEquals(String, Object, boolean)"})
  public void testVariableValueNotEqualsWithNameValueLocalScope2() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueNotEqualsResult = executionQueryImpl.variableValueNotEquals("Name", null, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueNotEquals(String, Object, boolean)"})
  public void testVariableValueNotEqualsWithNameValueLocalScope_whenNull() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueNotEqualsResult = executionQueryImpl.variableValueNotEquals("Name",
        JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)} with {@code name}, {@code value}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueNotEquals(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueNotEquals(String, Object)"})
  public void testVariableValueNotEqualsWithNameValue_whenNull() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueNotEqualsResult = historicTaskInstanceQueryImpl
        .variableValueNotEquals("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueNotEqualsResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueGreaterThan(String, Object)"})
  public void testVariableValueGreaterThanWithNameValue() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueGreaterThanResult = historicTaskInstanceQueryImpl
        .variableValueGreaterThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueGreaterThanResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueGreaterThan(String, Object)"})
  public void testVariableValueGreaterThanWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueGreaterThan(null, null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueGreaterThan(String, Object)"})
  public void testVariableValueGreaterThanWithNameValue3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueGreaterThan("Name", null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueGreaterThan(String, Object, boolean)"})
  public void testVariableValueGreaterThanWithNameValueLocalScope() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueGreaterThanResult = executionQueryImpl.variableValueGreaterThan("Name",
        JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueGreaterThanResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueGreaterThan(String, Object, boolean)"})
  public void testVariableValueGreaterThanWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueGreaterThan(null, null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueGreaterThan(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueGreaterThan(String, Object, boolean)"})
  public void testVariableValueGreaterThanWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueGreaterThan("Name", null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueGreaterThanOrEqual(String, Object)"})
  public void testVariableValueGreaterThanOrEqualWithNameValue() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueGreaterThanOrEqualResult = historicTaskInstanceQueryImpl
        .variableValueGreaterThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueGreaterThanOrEqual(String, Object)"})
  public void testVariableValueGreaterThanOrEqualWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueGreaterThanOrEqual(null, null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueGreaterThanOrEqual(String, Object)"})
  public void testVariableValueGreaterThanOrEqualWithNameValue3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueGreaterThanOrEqual("Name", null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueGreaterThanOrEqual(String, Object, boolean)"})
  public void testVariableValueGreaterThanOrEqualWithNameValueLocalScope() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueGreaterThanOrEqualResult = executionQueryImpl
        .variableValueGreaterThanOrEqual("Name", JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueGreaterThanOrEqualResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueGreaterThanOrEqual(String, Object, boolean)"})
  public void testVariableValueGreaterThanOrEqualWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueGreaterThanOrEqual(null, null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueGreaterThanOrEqual(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueGreaterThanOrEqual(String, Object, boolean)"})
  public void testVariableValueGreaterThanOrEqualWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueGreaterThanOrEqual("Name", null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLessThan(String, Object)"})
  public void testVariableValueLessThanWithNameValue() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueLessThanResult = historicTaskInstanceQueryImpl
        .variableValueLessThan("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueLessThanResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLessThan(String, Object, boolean)"})
  public void testVariableValueLessThanWithNameValueLocalScope() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueLessThanResult = executionQueryImpl.variableValueLessThan("Name", JSONObject.NULL,
        true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueLessThanResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLessThan(String, Object, boolean)"})
  public void testVariableValueLessThanWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueLessThan(null, null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLessThan(String, Object, boolean)"})
  public void testVariableValueLessThanWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueLessThan("Name", null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)} with {@code name}, {@code value}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLessThan(String, Object)"})
  public void testVariableValueLessThanWithNameValue_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueLessThan(null, null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)} with {@code name}, {@code value}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLessThan(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLessThan(String, Object)"})
  public void testVariableValueLessThanWithNameValue_thenThrowActivitiIllegalArgumentException2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueLessThan("Name", null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLessThanOrEqual(String, Object)"})
  public void testVariableValueLessThanOrEqualWithNameValue() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueLessThanOrEqualResult = historicTaskInstanceQueryImpl
        .variableValueLessThanOrEqual("Name", JSONObject.NULL);

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueLessThanOrEqualResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLessThanOrEqual(String, Object)"})
  public void testVariableValueLessThanOrEqualWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueLessThanOrEqual(null, null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLessThanOrEqual(String, Object)"})
  public void testVariableValueLessThanOrEqualWithNameValue3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueLessThanOrEqual("Name", null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLessThanOrEqual(String, Object, boolean)"})
  public void testVariableValueLessThanOrEqualWithNameValueLocalScope() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueLessThanOrEqualResult = executionQueryImpl.variableValueLessThanOrEqual("Name",
        JSONObject.NULL, true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueLessThanOrEqualResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLessThanOrEqual(String, Object, boolean)"})
  public void testVariableValueLessThanOrEqualWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueLessThanOrEqual(null, null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLessThanOrEqual(String, Object, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLessThanOrEqual(String, Object, boolean)"})
  public void testVariableValueLessThanOrEqualWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueLessThanOrEqual("Name", null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLike(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLike(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLike(String, String)"})
  public void testVariableValueLikeWithNameValue() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueLikeResult = historicTaskInstanceQueryImpl.variableValueLike("Name",
        "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueLikeResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLike(String, String, boolean)"})
  public void testVariableValueLikeWithNameValueLocalScope() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueLikeResult = executionQueryImpl.variableValueLike("Name", "42", true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueLikeResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLike(String, String, boolean)"})
  public void testVariableValueLikeWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueLike(null, null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLike(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLike(String, String, boolean)"})
  public void testVariableValueLikeWithNameValueLocalScope3() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueLike("Name", null, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLike(String, String)} with {@code name}, {@code value}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLike(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLike(String, String)"})
  public void testVariableValueLikeWithNameValue_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueLike(null, null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLike(String, String)} with {@code name}, {@code value}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLike(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLike(String, String)"})
  public void testVariableValueLikeWithNameValue_thenThrowActivitiIllegalArgumentException2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueLike("Name", null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLikeIgnoreCase(String, String)"})
  public void testVariableValueLikeIgnoreCaseWithNameValue() {
    // Arrange
    HistoricTaskInstanceQueryImpl historicTaskInstanceQueryImpl = new HistoricTaskInstanceQueryImpl();

    // Act
    HistoricTaskInstanceQuery actualVariableValueLikeIgnoreCaseResult = historicTaskInstanceQueryImpl
        .variableValueLikeIgnoreCase("Name", "42");

    // Assert
    assertTrue(historicTaskInstanceQueryImpl.hasLocalQueryVariableValue());
    assertSame(historicTaskInstanceQueryImpl, actualVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String)} with {@code name}, {@code value}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLikeIgnoreCase(String, String)"})
  public void testVariableValueLikeIgnoreCaseWithNameValue2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new HistoricTaskInstanceQueryImpl()).variableValueLikeIgnoreCase(null, "42"));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLikeIgnoreCase(String, String, boolean)"})
  public void testVariableValueLikeIgnoreCaseWithNameValueLocalScope() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    ExecutionQuery actualVariableValueLikeIgnoreCaseResult = executionQueryImpl.variableValueLikeIgnoreCase("Name",
        "42", true);

    // Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
    assertSame(executionQueryImpl, actualVariableValueLikeIgnoreCaseResult);
  }

  /**
   * Test {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String, boolean)} with {@code name}, {@code value}, {@code localScope}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#variableValueLikeIgnoreCase(String, String, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({
      "org.activiti.engine.query.Query AbstractVariableQueryImpl.variableValueLikeIgnoreCase(String, String, boolean)"})
  public void testVariableValueLikeIgnoreCaseWithNameValueLocalScope2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).variableValueLikeIgnoreCase(null, "42", true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@link QueryOperator#EQUALS_IGNORE_CASE}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractVariableQueryImpl.addVariable(String, Object, QueryOperator, boolean)"})
  public void testAddVariable_whenEquals_ignore_case_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).addVariable("Name", null, QueryOperator.EQUALS_IGNORE_CASE, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code EQUALS}.</li>
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()} QueryVariableValues size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractVariableQueryImpl.addVariable(String, Object, QueryOperator, boolean)"})
  public void testAddVariable_whenEquals_thenExecutionQueryImplQueryVariableValuesSizeIsOne() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    executionQueryImpl.addVariable("Name", null, QueryOperator.EQUALS, true);

    // Assert
    List<QueryVariableValue> queryVariableValues = executionQueryImpl.getQueryVariableValues();
    assertEquals(1, queryVariableValues.size());
    QueryVariableValue getResult = queryVariableValues.get(0);
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
   * Test {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code GREATER_THAN_OR_EQUAL}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractVariableQueryImpl.addVariable(String, Object, QueryOperator, boolean)"})
  public void testAddVariable_whenGreaterThanOrEqual_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).addVariable("Name", null, QueryOperator.GREATER_THAN_OR_EQUAL, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code GREATER_THAN}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractVariableQueryImpl.addVariable(String, Object, QueryOperator, boolean)"})
  public void testAddVariable_whenGreaterThan_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).addVariable(null, null, QueryOperator.GREATER_THAN, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code GREATER_THAN}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractVariableQueryImpl.addVariable(String, Object, QueryOperator, boolean)"})
  public void testAddVariable_whenGreaterThan_thenThrowActivitiIllegalArgumentException2() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).addVariable("Name", null, QueryOperator.GREATER_THAN, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code LESS_THAN_OR_EQUAL}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractVariableQueryImpl.addVariable(String, Object, QueryOperator, boolean)"})
  public void testAddVariable_whenLessThanOrEqual_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).addVariable("Name", null, QueryOperator.LESS_THAN_OR_EQUAL, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@code LESS_THAN}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractVariableQueryImpl.addVariable(String, Object, QueryOperator, boolean)"})
  public void testAddVariable_whenLessThan_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).addVariable("Name", null, QueryOperator.LESS_THAN, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@link QueryOperator#LIKE_IGNORE_CASE}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractVariableQueryImpl.addVariable(String, Object, QueryOperator, boolean)"})
  public void testAddVariable_whenLike_ignore_case_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).addVariable("Name", null, QueryOperator.LIKE_IGNORE_CASE, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@link QueryOperator#LIKE}.</li>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractVariableQueryImpl.addVariable(String, Object, QueryOperator, boolean)"})
  public void testAddVariable_whenLike_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).addVariable("Name", null, QueryOperator.LIKE, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@link QueryOperator#NOT_EQUALS_IGNORE_CASE}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractVariableQueryImpl.addVariable(String, Object, QueryOperator, boolean)"})
  public void testAddVariable_whenNot_equals_ignore_case() {
    // Arrange, Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> (new ExecutionQueryImpl()).addVariable("Name", null, QueryOperator.NOT_EQUALS_IGNORE_CASE, true));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then {@link ExecutionQueryImpl#ExecutionQueryImpl()} QueryVariableValues size is one.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#addVariable(String, Object, QueryOperator, boolean)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void AbstractVariableQueryImpl.addVariable(String, Object, QueryOperator, boolean)"})
  public void testAddVariable_whenNull_thenExecutionQueryImplQueryVariableValuesSizeIsOne() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();

    // Act
    executionQueryImpl.addVariable("Name", JSONObject.NULL, QueryOperator.EQUALS, true);

    // Assert
    List<QueryVariableValue> queryVariableValues = executionQueryImpl.getQueryVariableValues();
    assertEquals(1, queryVariableValues.size());
    QueryVariableValue getResult = queryVariableValues.get(0);
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
   * Test {@link AbstractVariableQueryImpl#isBoolean(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#isBoolean(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractVariableQueryImpl.isBoolean(Object)"})
  public void testIsBoolean_whenNull() {
    // Arrange, Act and Assert
    assertFalse((new ExecutionQueryImpl()).isBoolean(JSONObject.NULL));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#isBoolean(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#isBoolean(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractVariableQueryImpl.isBoolean(Object)"})
  public void testIsBoolean_whenNull2() {
    // Arrange, Act and Assert
    assertFalse((new ExecutionQueryImpl()).isBoolean(null));
  }

  /**
   * Test {@link AbstractVariableQueryImpl#getQueryVariableValues()}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#getQueryVariableValues()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"List AbstractVariableQueryImpl.getQueryVariableValues()"})
  public void testGetQueryVariableValues() {
    // Arrange, Act and Assert
    assertTrue((new ExecutionQueryImpl()).getQueryVariableValues().isEmpty());
  }

  /**
   * Test {@link AbstractVariableQueryImpl#hasLocalQueryVariableValue()}.
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#hasLocalQueryVariableValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractVariableQueryImpl.hasLocalQueryVariableValue()"})
  public void testHasLocalQueryVariableValue() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    executionQueryImpl.addVariable("description", JSONObject.NULL, QueryOperator.NOT_EQUALS, false);
    executionQueryImpl.addVariable("Name", JSONObject.NULL, QueryOperator.EQUALS, true);

    // Act and Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
  }

  /**
   * Test {@link AbstractVariableQueryImpl#hasLocalQueryVariableValue()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#hasLocalQueryVariableValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractVariableQueryImpl.hasLocalQueryVariableValue()"})
  public void testHasLocalQueryVariableValue_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ExecutionQueryImpl()).hasLocalQueryVariableValue());
  }

  /**
   * Test {@link AbstractVariableQueryImpl#hasLocalQueryVariableValue()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#hasLocalQueryVariableValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractVariableQueryImpl.hasLocalQueryVariableValue()"})
  public void testHasLocalQueryVariableValue_thenReturnTrue() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    executionQueryImpl.addVariable("Name", JSONObject.NULL, QueryOperator.EQUALS, true);

    // Act and Assert
    assertTrue(executionQueryImpl.hasLocalQueryVariableValue());
  }

  /**
   * Test {@link AbstractVariableQueryImpl#hasNonLocalQueryVariableValue()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#hasNonLocalQueryVariableValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractVariableQueryImpl.hasNonLocalQueryVariableValue()"})
  public void testHasNonLocalQueryVariableValue_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new ExecutionQueryImpl()).hasNonLocalQueryVariableValue());
  }

  /**
   * Test {@link AbstractVariableQueryImpl#hasNonLocalQueryVariableValue()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#hasNonLocalQueryVariableValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractVariableQueryImpl.hasNonLocalQueryVariableValue()"})
  public void testHasNonLocalQueryVariableValue_thenReturnFalse2() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    executionQueryImpl.addVariable("Name", JSONObject.NULL, QueryOperator.EQUALS, true);

    // Act and Assert
    assertFalse(executionQueryImpl.hasNonLocalQueryVariableValue());
  }

  /**
   * Test {@link AbstractVariableQueryImpl#hasNonLocalQueryVariableValue()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link AbstractVariableQueryImpl#hasNonLocalQueryVariableValue()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean AbstractVariableQueryImpl.hasNonLocalQueryVariableValue()"})
  public void testHasNonLocalQueryVariableValue_thenReturnTrue() {
    // Arrange
    ExecutionQueryImpl executionQueryImpl = new ExecutionQueryImpl();
    executionQueryImpl.addVariable("description", JSONObject.NULL, QueryOperator.NOT_EQUALS, false);
    executionQueryImpl.addVariable("Name", JSONObject.NULL, QueryOperator.EQUALS, true);

    // Act and Assert
    assertTrue(executionQueryImpl.hasNonLocalQueryVariableValue());
  }
}
