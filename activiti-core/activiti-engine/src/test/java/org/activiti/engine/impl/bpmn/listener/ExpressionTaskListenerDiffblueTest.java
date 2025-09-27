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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ExpressionTaskListenerDiffblueTest {
  /**
   * Test {@link ExpressionTaskListener#ExpressionTaskListener(Expression)}.
   *
   * <p>Method under test: {@link ExpressionTaskListener#ExpressionTaskListener(Expression)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ExpressionTaskListener.<init>(Expression)"})
  public void testNewExpressionTaskListener() {
    // Arrange and Act
    ExpressionTaskListener actualExpressionTaskListener =
        new ExpressionTaskListener(new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualExpressionTaskListener.expression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertEquals("null", actualExpressionTaskListener.getExpressionText());
  }

  /**
   * Test {@link ExpressionTaskListener#getExpressionText()}.
   *
   * <ul>
   *   <li>Given {@link FixedValue#FixedValue(Object)} with value is {@link JSONObject#NULL}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ExpressionTaskListener#getExpressionText()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.lang.String ExpressionTaskListener.getExpressionText()"})
  public void testGetExpressionText_givenFixedValueWithValueIsNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals(
        "null", new ExpressionTaskListener(new FixedValue(JSONObject.NULL)).getExpressionText());
  }
}
