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
package org.activiti.engine.impl.bpmn.listener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.List;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class DelegateExpressionExecutionListenerDiffblueTest {
  /**
   * Test
   * {@link DelegateExpressionExecutionListener#DelegateExpressionExecutionListener(Expression, List)}.
   * <p>
   * Method under test:
   * {@link DelegateExpressionExecutionListener#DelegateExpressionExecutionListener(Expression, List)}
   */
  @Test
  public void testNewDelegateExpressionExecutionListener() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act
    DelegateExpressionExecutionListener actualDelegateExpressionExecutionListener = new DelegateExpressionExecutionListener(
        expression, new ArrayList<>());

    // Assert
    Expression expression2 = actualDelegateExpressionExecutionListener.expression;
    assertTrue(expression2 instanceof FixedValue);
    assertEquals("null", expression2.getExpressionText());
    assertEquals("null", actualDelegateExpressionExecutionListener.getExpressionText());
  }

  /**
   * Test {@link DelegateExpressionExecutionListener#notify(DelegateExecution)}
   * with {@code DelegateExecution}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateExpressionExecutionListener#notify(DelegateExecution)}
   */
  @Test
  public void testNotifyWithDelegateExecution_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);
    DelegateExpressionExecutionListener delegateExpressionExecutionListener = new DelegateExpressionExecutionListener(
        expression, new ArrayList<>());

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class,
        () -> delegateExpressionExecutionListener.notify(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link DelegateExpressionExecutionListener#getExpressionText()}.
   * <ul>
   *   <li>Given {@link FixedValue#FixedValue(Object)} with value is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateExpressionExecutionListener#getExpressionText()}
   */
  @Test
  public void testGetExpressionText_givenFixedValueWithValueIsNull_thenReturnNull() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act and Assert
    assertEquals("null", (new DelegateExpressionExecutionListener(expression, new ArrayList<>())).getExpressionText());
  }

  /**
   * Test {@link DelegateExpressionExecutionListener#getExpressionText()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateExpressionExecutionListener#getExpressionText()}
   */
  @Test
  public void testGetExpressionText_givenJavaLangObject_thenReturnNull() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression expression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "null");

    // Act and Assert
    assertEquals("null", (new DelegateExpressionExecutionListener(expression, new ArrayList<>())).getExpressionText());
  }
}
