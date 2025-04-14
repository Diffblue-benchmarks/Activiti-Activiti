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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class BigDecimalTypeDiffblueTest {
  /**
   * Test {@link BigDecimalType#getValue(ValueFields)}.
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BigDecimalType#getValue(ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"Object BigDecimalType.getValue(ValueFields)"})
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    BigDecimalType bigDecimalType = new BigDecimalType();

    // Act and Assert
    assertNull(bigDecimalType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link BigDecimalType#setValue(Object, ValueFields)}.
   * <ul>
   *   <li>Then {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor) TextValue is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BigDecimalType#setValue(Object, ValueFields)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BigDecimalType.setValue(Object, ValueFields)"})
  public void testSetValue_thenHistoricDetailVariableInstanceUpdateEntityImplTextValueIsNull() {
    // Arrange
    BigDecimalType bigDecimalType = new BigDecimalType();
    HistoricDetailVariableInstanceUpdateEntityImpl valueFields = new HistoricDetailVariableInstanceUpdateEntityImpl();

    // Act
    bigDecimalType.setValue(JSONObject.NULL, valueFields);

    // Assert
    assertEquals("null", valueFields.getTextValue());
  }

  /**
   * Test {@link BigDecimalType#isAbleToStore(Object)}.
   * <p>
   * Method under test: {@link BigDecimalType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean BigDecimalType.isAbleToStore(Object)"})
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new BigDecimalType()).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link BigDecimalType}
   *   <li>{@link BigDecimalType#getTypeName()}
   *   <li>{@link BigDecimalType#isCachable()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void BigDecimalType.<init>()", "String BigDecimalType.getTypeName()",
      "boolean BigDecimalType.isCachable()"})
  public void testGettersAndSetters() {
    // Arrange and Act
    BigDecimalType actualBigDecimalType = new BigDecimalType();
    String actualTypeName = actualBigDecimalType.getTypeName();

    // Assert
    assertEquals("bigdecimal", actualTypeName);
    assertTrue(actualBigDecimalType.isCachable());
  }
}
