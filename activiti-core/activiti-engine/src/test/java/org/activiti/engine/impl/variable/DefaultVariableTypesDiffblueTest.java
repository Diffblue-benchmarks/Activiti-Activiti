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
   * Method under test: {@link DefaultVariableTypes#addType(VariableType)}
   */
  @Test
  public void testAddType() {
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
   * Method under test: {@link DefaultVariableTypes#addType(VariableType, int)}
   */
  @Test
  public void testAddType2() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();
    defaultVariableTypes.addType(new BigDecimalType());

    // Act and Assert
    assertSame(defaultVariableTypes, defaultVariableTypes.addType(new BigDecimalType(), 1));
  }

  /**
   * Method under test: {@link DefaultVariableTypes#setTypesList(List)}
   */
  @Test
  public void testSetTypesList() {
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
   * Method under test: {@link DefaultVariableTypes#getVariableType(String)}
   */
  @Test
  public void testGetVariableType() {
    // Arrange, Act and Assert
    assertNull(defaultVariableTypes.getVariableType("Type Name"));
  }

  /**
   * Method under test: {@link DefaultVariableTypes#findVariableType(Object)}
   */
  @Test
  public void testFindVariableType() {
    // Arrange, Act and Assert
    assertThrows(ActivitiException.class, () -> (new DefaultVariableTypes()).findVariableType(JSONObject.NULL));
  }

  /**
   * Method under test: {@link DefaultVariableTypes#findVariableType(Object)}
   */
  @Test
  public void testFindVariableType2() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();
    defaultVariableTypes.addType(new BigDecimalType());

    // Act and Assert
    assertThrows(ActivitiException.class, () -> defaultVariableTypes.findVariableType(JSONObject.NULL));
  }

  /**
   * Method under test: {@link DefaultVariableTypes#findVariableType(Object)}
   */
  @Test
  public void testFindVariableType3() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();
    Class<Object> theClass = Object.class;
    CustomObjectType type = new CustomObjectType("null", theClass);

    defaultVariableTypes.addType(type);

    // Act and Assert
    assertSame(type, defaultVariableTypes.findVariableType(JSONObject.NULL));
  }

  /**
   * Method under test: {@link DefaultVariableTypes#getTypeIndex(VariableType)}
   */
  @Test
  public void testGetTypeIndex() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();

    // Act and Assert
    assertEquals(-1, defaultVariableTypes.getTypeIndex(new BigDecimalType()));
  }

  /**
   * Method under test: {@link DefaultVariableTypes#getTypeIndex(String)}
   */
  @Test
  public void testGetTypeIndex2() {
    // Arrange, Act and Assert
    assertEquals(-1, defaultVariableTypes.getTypeIndex("Type Name"));
  }

  /**
   * Method under test: {@link DefaultVariableTypes#removeType(VariableType)}
   */
  @Test
  public void testRemoveType() {
    // Arrange
    DefaultVariableTypes defaultVariableTypes = new DefaultVariableTypes();

    // Act and Assert
    assertSame(defaultVariableTypes, defaultVariableTypes.removeType(new BigDecimalType()));
  }
}
