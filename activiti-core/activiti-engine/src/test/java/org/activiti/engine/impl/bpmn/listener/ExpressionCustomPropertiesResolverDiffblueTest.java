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
import java.util.HashMap;
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

public class ExpressionCustomPropertiesResolverDiffblueTest {
  /**
   * Test
   * {@link ExpressionCustomPropertiesResolver#ExpressionCustomPropertiesResolver(Expression)}.
   * <p>
   * Method under test:
   * {@link ExpressionCustomPropertiesResolver#ExpressionCustomPropertiesResolver(Expression)}
   */
  @Test
  public void testNewExpressionCustomPropertiesResolver() {
    // Arrange and Act
    ExpressionCustomPropertiesResolver actualExpressionCustomPropertiesResolver = new ExpressionCustomPropertiesResolver(
        new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualExpressionCustomPropertiesResolver.expression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertEquals("null", actualExpressionCustomPropertiesResolver.getExpressionText());
  }

  /**
   * Test
   * {@link ExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}.
   * <ul>
   *   <li>Given {@link FixedValue#FixedValue(Object)} with value is
   * {@link HashMap#HashMap()}.</li>
   *   <li>Then return Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}
   */
  @Test
  public void testGetCustomPropertiesMap_givenFixedValueWithValueIsHashMap_thenReturnEmpty() {
    // Arrange
    ExpressionCustomPropertiesResolver expressionCustomPropertiesResolver = new ExpressionCustomPropertiesResolver(
        new FixedValue(new HashMap<>()));

    // Act and Assert
    assertTrue(expressionCustomPropertiesResolver
        .getCustomPropertiesMap(ExecutionEntityImpl.createWithEmptyRelationshipCollections())
        .isEmpty());
  }

  /**
   * Test
   * {@link ExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}
   */
  @Test
  public void testGetCustomPropertiesMap_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    ExpressionCustomPropertiesResolver expressionCustomPropertiesResolver = new ExpressionCustomPropertiesResolver(
        new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> expressionCustomPropertiesResolver
        .getCustomPropertiesMap(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link ExpressionCustomPropertiesResolver#getExpressionText()}.
   * <ul>
   *   <li>Given {@link FixedValue#FixedValue(Object)} with value is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionCustomPropertiesResolver#getExpressionText()}
   */
  @Test
  public void testGetExpressionText_givenFixedValueWithValueIsNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals("null", (new ExpressionCustomPropertiesResolver(new FixedValue(JSONObject.NULL))).getExpressionText());
  }

  /**
   * Test {@link ExpressionCustomPropertiesResolver#getExpressionText()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ExpressionCustomPropertiesResolver#getExpressionText()}
   */
  @Test
  public void testGetExpressionText_givenJavaLangObject_thenReturnNull() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("null",
        (new ExpressionCustomPropertiesResolver(
            new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "null")))
            .getExpressionText());
  }
}
