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
package org.activiti.engine.impl.variable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultVariableTypesDiffblueTest {
  @InjectMocks
  private DefaultVariableTypes defaultVariableTypes;

  /**
   * Test {@link DefaultVariableTypes#addType(VariableType, int)} with
   * {@code type}, {@code index}.
   * <ul>
   *   <li>Then return {@link DefaultVariableTypes} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultVariableTypes#addType(VariableType, int)}
   */
  @Test
  public void testAddTypeWithTypeIndex_thenReturnDefaultVariableTypes() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();
    defaultVariableTypes.addType(new BigDecimalType());

    // Act and Assert
    assertSame(defaultVariableTypes, defaultVariableTypes.addType(new BigDecimalType(), 1));
  }

  /**
   * Test {@link DefaultVariableTypes#addType(VariableType)} with {@code type}.
   * <ul>
   *   <li>Then {@link DefaultVariableTypes} (default constructor) findVariableType
   * {@code null} is {@link BigDecimalType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultVariableTypes#addType(VariableType)}
   */
  @Test
  public void testAddTypeWithType_thenDefaultVariableTypesFindVariableTypeNullIsBigDecimalType() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();
    BigDecimalType type = new BigDecimalType();

    // Act
    DefaultVariableTypes actualAddTypeResult = defaultVariableTypes.addType(type);

    // Assert
    assertSame(type, defaultVariableTypes.findVariableType(null));
    assertSame(defaultVariableTypes, actualAddTypeResult);
  }

  /**
   * Test {@link DefaultVariableTypes#setTypesList(List)}.
   * <ul>
   *   <li>Then {@link DefaultVariableTypes} (default constructor) findVariableType
   * {@code null} is {@link BigDecimalType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultVariableTypes#setTypesList(List)}
   */
  @Test
  public void testSetTypesList_thenDefaultVariableTypesFindVariableTypeNullIsBigDecimalType() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();

    ArrayList<VariableType> typesList = new ArrayList<>();
    BigDecimalType bigDecimalType = new BigDecimalType();
    typesList.add(bigDecimalType);

    // Act
    defaultVariableTypes.setTypesList(typesList);

    // Assert
    assertSame(bigDecimalType, defaultVariableTypes.findVariableType(null));
  }

  /**
   * Test {@link DefaultVariableTypes#getVariableType(String)}.
   * <p>
   * Method under test: {@link DefaultVariableTypes#getVariableType(String)}
   */
  @Test
  public void testGetVariableType() {
    // Arrange, Act and Assert
    assertNull(defaultVariableTypes.getVariableType("Type Name"));
  }

  /**
   * Test {@link DefaultVariableTypes#findVariableType(Object)}.
   * <p>
   * Method under test: {@link DefaultVariableTypes#findVariableType(Object)}
   */
  @Test
  public void testFindVariableType() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();
    Class<Object> theClass = Object.class;
    CustomObjectType type = new CustomObjectType("null", theClass);

    defaultVariableTypes.addType(type);

    // Act and Assert
    assertSame(type, defaultVariableTypes.findVariableType(JSONObject.NULL));
  }

  /**
   * Test {@link DefaultVariableTypes#findVariableType(Object)}.
   * <ul>
   *   <li>Given {@link DefaultVariableTypes} (default constructor) addType
   * {@link BigDecimalType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultVariableTypes#findVariableType(Object)}
   */
  @Test
  public void testFindVariableType_givenDefaultVariableTypesAddTypeBigDecimalType() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();
    defaultVariableTypes.addType(new BigDecimalType());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultVariableTypes.findVariableType(JSONObject.NULL));
  }

  /**
   * Test {@link DefaultVariableTypes#findVariableType(Object)}.
   * <ul>
   *   <li>Given {@link DefaultVariableTypes} (default constructor).</li>
   *   <li>Then throw {@link ActivitiException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultVariableTypes#findVariableType(Object)}
   */
  @Test
  public void testFindVariableType_givenDefaultVariableTypes_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new DefaultVariableTypes()).findVariableType(JSONObject.NULL));
  }

  /**
   * Test {@link DefaultVariableTypes#getTypeIndex(VariableType)} with
   * {@code type}.
   * <p>
   * Method under test: {@link DefaultVariableTypes#getTypeIndex(VariableType)}
   */
  @Test
  public void testGetTypeIndexWithType() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();

    // Act and Assert
    assertEquals(-1, defaultVariableTypes.getTypeIndex(new BigDecimalType()));
  }

  /**
   * Test {@link DefaultVariableTypes#getTypeIndex(String)} with {@code typeName}.
   * <p>
   * Method under test: {@link DefaultVariableTypes#getTypeIndex(String)}
   */
  @Test
  public void testGetTypeIndexWithTypeName() {
    // Arrange, Act and Assert
    assertEquals(-1, defaultVariableTypes.getTypeIndex("Type Name"));
  }

  /**
   * Test {@link DefaultVariableTypes#removeType(VariableType)}.
   * <ul>
   *   <li>When {@link BigDecimalType} (default constructor).</li>
   *   <li>Then return {@link DefaultVariableTypes} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultVariableTypes#removeType(VariableType)}
   */
  @Test
  public void testRemoveType_whenBigDecimalType_thenReturnDefaultVariableTypes() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();

    // Act and Assert
    assertSame(defaultVariableTypes, defaultVariableTypes.removeType(new BigDecimalType()));
  }
}
