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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.Map;
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
   * Method under test:
   * {@link ExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}
   */
  @Test
  public void testGetCustomPropertiesMap() {
    // Arrange
    ExpressionCustomPropertiesResolver expressionCustomPropertiesResolver = new ExpressionCustomPropertiesResolver(
        new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> expressionCustomPropertiesResolver
        .getCustomPropertiesMap(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Method under test:
   * {@link ExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}
   */
  @Test
  public void testGetCustomPropertiesMap2() {
    // Arrange
    HashMap<Object, Object> objectObjectMap = new HashMap<>();
    ExpressionCustomPropertiesResolver expressionCustomPropertiesResolver = new ExpressionCustomPropertiesResolver(
        new FixedValue(objectObjectMap));

    // Act
    Map<String, Object> actualCustomPropertiesMap = expressionCustomPropertiesResolver
        .getCustomPropertiesMap(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertTrue(actualCustomPropertiesMap.isEmpty());
    assertSame(objectObjectMap, actualCustomPropertiesMap);
  }

  /**
   * Method under test:
   * {@link ExpressionCustomPropertiesResolver#getExpressionText()}
   */
  @Test
  public void testGetExpressionText() {
    // Arrange, Act and Assert
    assertEquals("null", (new ExpressionCustomPropertiesResolver(new FixedValue(JSONObject.NULL))).getExpressionText());
  }

  /**
   * Method under test:
   * {@link ExpressionCustomPropertiesResolver#getExpressionText()}
   */
  @Test
  public void testGetExpressionText2() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("null",
        (new ExpressionCustomPropertiesResolver(
            new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "null")))
            .getExpressionText());
  }

  /**
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
}
