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
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DefaultVariableTypesDiffblueTest {
  /**
   * Test {@link DefaultVariableTypes#addType(VariableType, int)} with {@code type}, {@code index}.
   * <ul>
   *   <li>Then return {@link DefaultVariableTypes} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultVariableTypes#addType(VariableType, int)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DefaultVariableTypes DefaultVariableTypes.addType(VariableType, int)"})
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
   *   <li>Then {@link DefaultVariableTypes} (default constructor) findVariableType {@code null} is {@link BigDecimalType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultVariableTypes#addType(VariableType)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"DefaultVariableTypes DefaultVariableTypes.addType(VariableType)"})
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
   *   <li>Then {@link DefaultVariableTypes} (default constructor) findVariableType {@code null} is {@link BigDecimalType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultVariableTypes#setTypesList(List)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void DefaultVariableTypes.setTypesList(List)"})
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableType DefaultVariableTypes.getVariableType(String)"})
  public void testGetVariableType() {
    // Arrange, Act and Assert
    assertNull((new DefaultVariableTypes()).getVariableType("Type Name"));
  }

  /**
   * Test {@link DefaultVariableTypes#findVariableType(Object)}.
   * <p>
   * Method under test: {@link DefaultVariableTypes#findVariableType(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableType DefaultVariableTypes.findVariableType(Object)"})
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
   *   <li>Given {@link DefaultVariableTypes} (default constructor) addType {@link BigDecimalType} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultVariableTypes#findVariableType(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableType DefaultVariableTypes.findVariableType(Object)"})
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableType DefaultVariableTypes.findVariableType(Object)"})
  public void testFindVariableType_givenDefaultVariableTypes_thenThrowActivitiException() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new DefaultVariableTypes()).findVariableType(JSONObject.NULL));
  }

  /**
   * Test {@link DefaultVariableTypes#getTypeIndex(VariableType)} with {@code type}.
   * <p>
   * Method under test: {@link DefaultVariableTypes#getTypeIndex(VariableType)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int DefaultVariableTypes.getTypeIndex(VariableType)"})
  public void testGetTypeIndexWithType() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();

    // Act and Assert
    assertEquals(-1, defaultVariableTypes.getTypeIndex(new BigDecimalType()));
  }

  /**
   * Test {@link DefaultVariableTypes#getTypeIndex(String)} with {@code typeName}.
   * <ul>
   *   <li>Given {@link DefaultVariableTypes} (default constructor).</li>
   *   <li>Then return minus one.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultVariableTypes#getTypeIndex(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int DefaultVariableTypes.getTypeIndex(String)"})
  public void testGetTypeIndexWithTypeName_givenDefaultVariableTypes_thenReturnMinusOne() {
    // Arrange, Act and Assert
    assertEquals(-1, (new DefaultVariableTypes()).getTypeIndex("Type Name"));
  }

  /**
   * Test {@link DefaultVariableTypes#getTypeIndex(String)} with {@code typeName}.
   * <ul>
   *   <li>Given {@code Object}.</li>
   *   <li>Then return zero.</li>
   * </ul>
   * <p>
   * Method under test: {@link DefaultVariableTypes#getTypeIndex(String)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"int DefaultVariableTypes.getTypeIndex(String)"})
  public void testGetTypeIndexWithTypeName_givenJavaLangObject_thenReturnZero() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();
    Class<Object> theClass = Object.class;
    defaultVariableTypes.addType(new CustomObjectType("Type Name", theClass));

    // Act and Assert
    assertEquals(0, defaultVariableTypes.getTypeIndex("Type Name"));
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
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"VariableTypes DefaultVariableTypes.removeType(VariableType)"})
  public void testRemoveType_whenBigDecimalType_thenReturnDefaultVariableTypes() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();

    // Act and Assert
    assertSame(defaultVariableTypes, defaultVariableTypes.removeType(new BigDecimalType()));
  }
}
