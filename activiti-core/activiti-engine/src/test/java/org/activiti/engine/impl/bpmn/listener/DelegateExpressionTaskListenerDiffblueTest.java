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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DelegateExpressionTaskListenerDiffblueTest {
  /**
   * Test {@link DelegateExpressionTaskListener#DelegateExpressionTaskListener(Expression, List)}.
   * <p>
   * Method under test: {@link DelegateExpressionTaskListener#DelegateExpressionTaskListener(Expression, List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DelegateExpressionTaskListener.<init>(Expression, List)"})
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

  /**
   * Test {@link DelegateExpressionTaskListener#getExpressionText()}.
   * <ul>
   *   <li>Given {@link FixedValue#FixedValue(Object)} with value is {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DelegateExpressionTaskListener#getExpressionText()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"java.lang.String DelegateExpressionTaskListener.getExpressionText()"})
  public void testGetExpressionText_givenFixedValueWithValueIsNull_thenReturnNull() {
    // Arrange
    FixedValue expression = new FixedValue(JSONObject.NULL);

    // Act and Assert
    assertEquals("null", (new DelegateExpressionTaskListener(expression, new ArrayList<>())).getExpressionText());
  }
}
