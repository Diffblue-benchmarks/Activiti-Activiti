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
import java.util.ArrayList;
import java.util.List;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.el.JuelExpression;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class DelegateExpressionTaskListenerDiffblueTest {
  /**
   * Method under test: {@link DelegateExpressionTaskListener#getExpressionText()}
   */
  @Test
  public void testGetExpressionText() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act and Assert
    assertEquals("null", (new DelegateExpressionTaskListener(expression, new ArrayList<>())).getExpressionText());
  }

  /**
   * Method under test: {@link DelegateExpressionTaskListener#getExpressionText()}
   */
  @Test
  public void testGetExpressionText2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    JuelExpression expression = new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "null");

    // Act and Assert
    assertEquals("null", (new DelegateExpressionTaskListener(expression, new ArrayList<>())).getExpressionText());
  }

  /**
   * Method under test:
   * {@link DelegateExpressionTaskListener#DelegateExpressionTaskListener(Expression, List)}
   */
  @Test
  public void testNewDelegateExpressionTaskListener() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act
    DelegateExpressionTaskListener actualDelegateExpressionTaskListener = new DelegateExpressionTaskListener(expression,
        new ArrayList<>());

    // Assert
    Expression expression2 = actualDelegateExpressionTaskListener.expression;
    assertTrue(expression2 instanceof FixedValue);
    assertEquals("null", expression2.getExpressionText());
    assertEquals("null", actualDelegateExpressionTaskListener.getExpressionText());
  }
}
