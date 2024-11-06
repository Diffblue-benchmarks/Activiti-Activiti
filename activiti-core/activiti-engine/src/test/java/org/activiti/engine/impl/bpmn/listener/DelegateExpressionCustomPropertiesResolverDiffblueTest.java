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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
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
import org.activiti.examples.bpmn.executionlistener.MyCustomPropertiesResolver;
import org.junit.Test;

public class DelegateExpressionCustomPropertiesResolverDiffblueTest {
  /**
   * Test
   * {@link DelegateExpressionCustomPropertiesResolver#DelegateExpressionCustomPropertiesResolver(Expression)}.
   * <p>
   * Method under test:
   * {@link DelegateExpressionCustomPropertiesResolver#DelegateExpressionCustomPropertiesResolver(Expression)}
   */
  @Test
  public void testNewDelegateExpressionCustomPropertiesResolver() {
    // Arrange and Act
    DelegateExpressionCustomPropertiesResolver actualDelegateExpressionCustomPropertiesResolver = new DelegateExpressionCustomPropertiesResolver(
        new FixedValue(JSONObject.NULL));

    // Assert
    Expression expression = actualDelegateExpressionCustomPropertiesResolver.expression;
    assertTrue(expression instanceof FixedValue);
    assertEquals("null", expression.getExpressionText());
    assertEquals("null", actualDelegateExpressionCustomPropertiesResolver.getExpressionText());
  }

  /**
   * Test
   * {@link DelegateExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}
   */
  @Test
  public void testGetCustomPropertiesMap_thenReturnSizeIsOne() {
    // Arrange
    DelegateExpressionCustomPropertiesResolver delegateExpressionCustomPropertiesResolver = new DelegateExpressionCustomPropertiesResolver(
        new FixedValue(new MyCustomPropertiesResolver()));

    // Act
    Map<String, Object> actualCustomPropertiesMap = delegateExpressionCustomPropertiesResolver
        .getCustomPropertiesMap(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertEquals(1, actualCustomPropertiesMap.size());
    assertNull(actualCustomPropertiesMap.get("customProp1"));
  }

  /**
   * Test
   * {@link DelegateExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}.
   * <ul>
   *   <li>Then return size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}
   */
  @Test
  public void testGetCustomPropertiesMap_thenReturnSizeIsOne2() {
    // Arrange
    DelegateExpressionCustomPropertiesResolver delegateExpressionCustomPropertiesResolver = new DelegateExpressionCustomPropertiesResolver(
        new FixedValue(
            new DelegateExpressionCustomPropertiesResolver(new FixedValue(new MyCustomPropertiesResolver()))));

    // Act
    Map<String, Object> actualCustomPropertiesMap = delegateExpressionCustomPropertiesResolver
        .getCustomPropertiesMap(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert
    assertEquals(1, actualCustomPropertiesMap.size());
    assertNull(actualCustomPropertiesMap.get("customProp1"));
  }

  /**
   * Test
   * {@link DelegateExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}
   */
  @Test
  public void testGetCustomPropertiesMap_thenThrowActivitiIllegalArgumentException() {
    // Arrange
    DelegateExpressionCustomPropertiesResolver delegateExpressionCustomPropertiesResolver = new DelegateExpressionCustomPropertiesResolver(
        new FixedValue(JSONObject.NULL));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> delegateExpressionCustomPropertiesResolver
        .getCustomPropertiesMap(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test
   * {@link DelegateExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}.
   * <ul>
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateExpressionCustomPropertiesResolver#getCustomPropertiesMap(DelegateExecution)}
   */
  @Test
  public void testGetCustomPropertiesMap_thenThrowActivitiIllegalArgumentException2() {
    // Arrange
    DelegateExpressionCustomPropertiesResolver delegateExpressionCustomPropertiesResolver = new DelegateExpressionCustomPropertiesResolver(
        new FixedValue(new DelegateExpressionCustomPropertiesResolver(new FixedValue(JSONObject.NULL))));

    // Act and Assert
    assertThrows(ActivitiIllegalArgumentException.class, () -> delegateExpressionCustomPropertiesResolver
        .getCustomPropertiesMap(ExecutionEntityImpl.createWithEmptyRelationshipCollections()));
  }

  /**
   * Test {@link DelegateExpressionCustomPropertiesResolver#getExpressionText()}.
   * <ul>
   *   <li>Given {@link FixedValue#FixedValue(Object)} with value is
   * {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateExpressionCustomPropertiesResolver#getExpressionText()}
   */
  @Test
  public void testGetExpressionText_givenFixedValueWithValueIsNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertEquals("null",
        (new DelegateExpressionCustomPropertiesResolver(new FixedValue(JSONObject.NULL))).getExpressionText());
  }

  /**
   * Test {@link DelegateExpressionCustomPropertiesResolver#getExpressionText()}.
   * <ul>
   *   <li>Given {@code java.lang.Object}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link DelegateExpressionCustomPropertiesResolver#getExpressionText()}
   */
  @Test
  public void testGetExpressionText_givenJavaLangObject_thenReturnNull() {
    // Arrange
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;

    // Act and Assert
    assertEquals("null",
        (new DelegateExpressionCustomPropertiesResolver(
            new JuelExpression(new ObjectValueExpression(converter, JSONObject.NULL, type), "null")))
            .getExpressionText());
  }
}
