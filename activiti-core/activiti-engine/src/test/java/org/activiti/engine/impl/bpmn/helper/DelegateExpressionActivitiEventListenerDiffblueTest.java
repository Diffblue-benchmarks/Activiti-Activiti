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
package org.activiti.engine.impl.bpmn.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.BaseEntityEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiActivityCancelledEventImpl;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class DelegateExpressionActivitiEventListenerDiffblueTest {
  /**
   * Test
   * {@link DelegateExpressionActivitiEventListener#DelegateExpressionActivitiEventListener(Expression, Class)}.
   * <p>
   * Method under test:
   * {@link DelegateExpressionActivitiEventListener#DelegateExpressionActivitiEventListener(Expression, Class)}
   */
  @Test
  public void testNewDelegateExpressionActivitiEventListener() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression expression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");

    Class<Object> entityClass = Object.class;

    // Act
    DelegateExpressionActivitiEventListener actualDelegateExpressionActivitiEventListener = new DelegateExpressionActivitiEventListener(
        expression, entityClass);

    // Assert
    Expression expression2 = actualDelegateExpressionActivitiEventListener.expression;
    assertTrue(expression2 instanceof JuelExpression);
    assertEquals("Expression Text", expression2.getExpressionText());
    assertFalse(actualDelegateExpressionActivitiEventListener.isFailOnException());
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, actualDelegateExpressionActivitiEventListener.entityClass);
  }

  /**
   * Test
   * {@link DelegateExpressionActivitiEventListener#DelegateExpressionActivitiEventListener(Expression, Class)}.
   * <ul>
   *   <li>Then {@link DelegateExpressionActivitiEventListener#expression} return
   * {@link FixedValue}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateExpressionActivitiEventListener#DelegateExpressionActivitiEventListener(Expression, Class)}
   */
  @Test
  public void testNewDelegateExpressionActivitiEventListener_thenExpressionReturnFixedValue() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    Class<Object> entityClass = Object.class;

    // Act
    DelegateExpressionActivitiEventListener actualDelegateExpressionActivitiEventListener = new DelegateExpressionActivitiEventListener(
        expression, entityClass);

    // Assert
    Expression expression2 = actualDelegateExpressionActivitiEventListener.expression;
    assertTrue(expression2 instanceof FixedValue);
    assertEquals("null", expression2.getExpressionText());
    assertFalse(actualDelegateExpressionActivitiEventListener.isFailOnException());
    Class<Object> expectedResultClass = Object.class;
    assertEquals(expectedResultClass, actualDelegateExpressionActivitiEventListener.entityClass);
  }

  /**
   * Test {@link DelegateExpressionActivitiEventListener#onEvent(ActivitiEvent)}.
   * <p>
   * Method under test:
   * {@link DelegateExpressionActivitiEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    Class<Object> entityClass = Object.class;
    DelegateExpressionActivitiEventListener delegateExpressionActivitiEventListener = new DelegateExpressionActivitiEventListener(
        expression, entityClass);

    // Act
    delegateExpressionActivitiEventListener.onEvent(new ActivitiActivityCancelledEventImpl());

    // Assert that nothing has changed
    Expression expression2 = delegateExpressionActivitiEventListener.expression;
    assertTrue(expression2 instanceof FixedValue);
    assertEquals("null", expression2.getExpressionText());
    assertFalse(delegateExpressionActivitiEventListener.isFailOnException());
  }

  /**
   * Test {@link DelegateExpressionActivitiEventListener#onEvent(ActivitiEvent)}.
   * <p>
   * Method under test:
   * {@link DelegateExpressionActivitiEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression expression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type),
        "Expression Text");

    Class<Object> entityClass = Object.class;
    DelegateExpressionActivitiEventListener delegateExpressionActivitiEventListener = new DelegateExpressionActivitiEventListener(
        expression, entityClass);

    // Act
    delegateExpressionActivitiEventListener.onEvent(new ActivitiActivityCancelledEventImpl());

    // Assert that nothing has changed
    assertFalse(delegateExpressionActivitiEventListener.isFailOnException());
  }

  /**
   * Test {@link DelegateExpressionActivitiEventListener#onEvent(ActivitiEvent)}.
   * <p>
   * Method under test:
   * {@link DelegateExpressionActivitiEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent3() {
    // Arrange
    DelegateExpressionActivitiEventListener delegateExpressionActivitiEventListener = new DelegateExpressionActivitiEventListener(
        new FixedValue(new BaseEntityEventListener(true)), null);

    // Act
    delegateExpressionActivitiEventListener.onEvent(new ActivitiActivityCancelledEventImpl());

    // Assert
    assertTrue(delegateExpressionActivitiEventListener.isFailOnException());
  }

  /**
   * Test {@link DelegateExpressionActivitiEventListener#onEvent(ActivitiEvent)}.
   * <p>
   * Method under test:
   * {@link DelegateExpressionActivitiEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent4() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    Class<Object> entityClass = Object.class;
    DelegateExpressionActivitiEventListener delegateExpressionActivitiEventListener = new DelegateExpressionActivitiEventListener(
        new FixedValue(new DelegateExpressionActivitiEventListener(expression, entityClass)), null);

    // Act
    delegateExpressionActivitiEventListener.onEvent(new ActivitiActivityCancelledEventImpl());

    // Assert
    assertFalse(delegateExpressionActivitiEventListener.isFailOnException());
  }

  /**
   * Test {@link DelegateExpressionActivitiEventListener#onEvent(ActivitiEvent)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateExpressionActivitiEventListener#onEvent(ActivitiEvent)}
   */
  @Test
  public void testOnEvent_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DelegateExpressionActivitiEventListener delegateExpressionActivitiEventListener = new DelegateExpressionActivitiEventListener(
        new FixedValue(JSONObject.NULL), null);

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> delegateExpressionActivitiEventListener.onEvent(new ActivitiActivityCancelledEventImpl()));
  }

  /**
   * Test {@link DelegateExpressionActivitiEventListener#isFailOnException()}.
   * <p>
   * Method under test:
   * {@link DelegateExpressionActivitiEventListener#isFailOnException()}
   */
  @Test
  public void testIsFailOnException() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    Class<Object> entityClass = Object.class;

    // Act and Assert
    assertFalse((new DelegateExpressionActivitiEventListener(expression, entityClass)).isFailOnException());
  }
}
