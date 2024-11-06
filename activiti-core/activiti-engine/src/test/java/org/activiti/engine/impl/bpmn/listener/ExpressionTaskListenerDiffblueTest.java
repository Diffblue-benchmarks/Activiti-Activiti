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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class ExpressionTaskListenerDiffblueTest {
  /**
   * Test {@link ExpressionTaskListener#ExpressionTaskListener(Expression)}.
   * <p>
   * Method under test:
   * {@link ExpressionTaskListener#ExpressionTaskListener(Expression)}
   */
  @Test
  public void testNewExpressionTaskListener() {
    // Arrange and Act
    ExpressionTaskListener actualExpressionTaskListener = new ExpressionTaskListener(new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualExpressionTaskListener.expression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertEquals("null", actualExpressionTaskListener.getExpressionText());
  }

  /**
   * Test {@link ExpressionTaskListener#getExpressionText()}.
   * <ul>
   *   <li>Given {@link FixedValue#FixedValue(Object)} with value is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionTaskListener#getExpressionText()}
   */
  @Test
  public void testGetExpressionText_givenFixedValueWithValueIsNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals("null", (new ExpressionTaskListener(new FixedValue(JSONObject.NULL))).getExpressionText());
  }

  /**
   * Test {@link ExpressionTaskListener#getExpressionText()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ExpressionTaskListener#getExpressionText()}
   */
  @Test
  public void testGetExpressionText_givenJavaLangObject_thenReturnNull() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("null",
        (new ExpressionTaskListener(
            new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "null")))
            .getExpressionText());
  }
}
