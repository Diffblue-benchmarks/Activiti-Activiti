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
package org.activiti.core.el;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import jakarta.el.ValueExpression;
import java.util.HashMap;
import java.util.Map;
import org.activiti.core.el.juel.ObjectValueExpression;
import org.activiti.core.el.juel.misc.TypeConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ActivitiVariablesMapperDiffblueTest {
  /**
   * Test {@link ActivitiVariablesMapper#ActivitiVariablesMapper()}.
   * <p>
   * Method under test: {@link ActivitiVariablesMapper#ActivitiVariablesMapper()}
   */
  @Test
  @DisplayName("Test new ActivitiVariablesMapper()")
  void testNewActivitiVariablesMapper() {
    // Arrange, Act and Assert
    assertTrue((new ActivitiVariablesMapper()).map.isEmpty());
  }

  /**
   * Test {@link ActivitiVariablesMapper#ActivitiVariablesMapper(Map)}.
   * <ul>
   *   <li>Given {@code foo}.</li>
   *   <li>Then return {@link ActivitiVariablesMapper#map} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiVariablesMapper#ActivitiVariablesMapper(Map)}
   */
  @Test
  @DisplayName("Test new ActivitiVariablesMapper(Map); given 'foo'; then return map size is one")
  void testNewActivitiVariablesMapper_givenFoo_thenReturnMapSizeIsOne() {
    // Arrange
    HashMap<String, ValueExpression> map = new HashMap<>();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression objectValueExpression = new ObjectValueExpression(converter, "Object", type);

    map.put("foo", objectValueExpression);

    // Act and Assert
    Map<String, ValueExpression> stringValueExpressionMap = (new ActivitiVariablesMapper(map)).map;
    assertEquals(1, stringValueExpressionMap.size());
    assertSame(objectValueExpression, stringValueExpressionMap.get("foo"));
  }

  /**
   * Test {@link ActivitiVariablesMapper#ActivitiVariablesMapper(Map)}.
   * <ul>
   *   <li>When {@link HashMap#HashMap()}.</li>
   *   <li>Then return {@link ActivitiVariablesMapper#map} Empty.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiVariablesMapper#ActivitiVariablesMapper(Map)}
   */
  @Test
  @DisplayName("Test new ActivitiVariablesMapper(Map); when HashMap(); then return map Empty")
  void testNewActivitiVariablesMapper_whenHashMap_thenReturnMapEmpty() {
    // Arrange, Act and Assert
    assertTrue((new ActivitiVariablesMapper(new HashMap<>())).map.isEmpty());
  }

  /**
   * Test {@link ActivitiVariablesMapper#resolveVariable(String)}.
   * <p>
   * Method under test: {@link ActivitiVariablesMapper#resolveVariable(String)}
   */
  @Test
  @DisplayName("Test resolveVariable(String)")
  void testResolveVariable() {
    // Arrange
    ActivitiVariablesMapper activitiVariablesMapper = new ActivitiVariablesMapper();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression expression = new ObjectValueExpression(converter, "Object", type);

    activitiVariablesMapper.setVariable("Variable", expression);

    // Act and Assert
    assertSame(expression, activitiVariablesMapper.resolveVariable("Variable"));
  }

  /**
   * Test {@link ActivitiVariablesMapper#resolveVariable(String)}.
   * <ul>
   *   <li>Given {@link ActivitiVariablesMapper#ActivitiVariablesMapper()}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiVariablesMapper#resolveVariable(String)}
   */
  @Test
  @DisplayName("Test resolveVariable(String); given ActivitiVariablesMapper(); then return 'null'")
  void testResolveVariable_givenActivitiVariablesMapper_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull((new ActivitiVariablesMapper()).resolveVariable("Variable"));
  }

  /**
   * Test {@link ActivitiVariablesMapper#setVariable(String, ValueExpression)}.
   * <ul>
   *   <li>Then {@link ActivitiVariablesMapper#ActivitiVariablesMapper()}
   * {@link ActivitiVariablesMapper#map} size is one.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiVariablesMapper#setVariable(String, ValueExpression)}
   */
  @Test
  @DisplayName("Test setVariable(String, ValueExpression); then ActivitiVariablesMapper() map size is one")
  void testSetVariable_thenActivitiVariablesMapperMapSizeIsOne() {
    // Arrange
    ActivitiVariablesMapper activitiVariablesMapper = new ActivitiVariablesMapper();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    ObjectValueExpression expression = new ObjectValueExpression(converter, "Object", type);

    // Act and Assert
    assertNull(activitiVariablesMapper.setVariable("Variable", expression));
    Map<String, ValueExpression> stringValueExpressionMap = activitiVariablesMapper.map;
    assertEquals(1, stringValueExpressionMap.size());
    assertSame(expression, stringValueExpressionMap.get("Variable"));
  }

  /**
   * Test {@link ActivitiVariablesMapper#setVariable(String, ValueExpression)}.
   * <ul>
   *   <li>Then {@link ActivitiVariablesMapper#ActivitiVariablesMapper(Map)} with
   * map is {@link HashMap#HashMap()} {@link ActivitiVariablesMapper#map} size is
   * two.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ActivitiVariablesMapper#setVariable(String, ValueExpression)}
   */
  @Test
  @DisplayName("Test setVariable(String, ValueExpression); then ActivitiVariablesMapper(Map) with map is HashMap() map size is two")
  void testSetVariable_thenActivitiVariablesMapperWithMapIsHashMapMapSizeIsTwo() {
    // Arrange
    HashMap<String, ValueExpression> map = new HashMap<>();
    TypeConverter converter = mock(TypeConverter.class);
    Class<Object> type = Object.class;
    map.put("foo", new ObjectValueExpression(converter, "Object", type));
    ActivitiVariablesMapper activitiVariablesMapper = new ActivitiVariablesMapper(map);
    TypeConverter converter2 = mock(TypeConverter.class);
    Class<Object> type2 = Object.class;
    ObjectValueExpression expression = new ObjectValueExpression(converter2, "Object", type2);

    // Act and Assert
    assertNull(activitiVariablesMapper.setVariable("Variable", expression));
    Map<String, ValueExpression> stringValueExpressionMap = activitiVariablesMapper.map;
    assertEquals(2, stringValueExpressionMap.size());
    assertTrue(stringValueExpressionMap.containsKey("foo"));
    assertSame(expression, stringValueExpressionMap.get("Variable"));
  }
}
