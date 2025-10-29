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
import static org.mockito.Mockito.mock;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;

public class NullTypeDiffblueTest {
  /**
   * Method under test: {@link NullType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue() {
    // Arrange
    NullType nullType = new NullType();

    // Act and Assert
    assertNull(nullType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Method under test: {@link NullType#getValue(ValueFields)}
   */
  @Test
  public void testGetValue2() {
    // Arrange, Act and Assert
    assertNull((new NullType()).getValue(mock(ValueFields.class)));
  }

  /**
   * Method under test: {@link NullType#isAbleToStore(Object)}
   */
  @Test
  public void testIsAbleToStore() {
    // Arrange, Act and Assert
    assertFalse((new NullType()).isAbleToStore(JSONObject.NULL));
    assertTrue((new NullType()).isAbleToStore(null));
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link NullType}
   *   <li>{@link NullType#setValue(Object, ValueFields)}
   *   <li>{@link NullType#getTypeName()}
   *   <li>{@link NullType#isCachable()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    NullType actualNullType = new NullType();
    actualNullType.setValue(JSONObject.NULL, new HistoricDetailVariableInstanceUpdateEntityImpl());
    String actualTypeName = actualNullType.getTypeName();

    // Assert that nothing has changed
    assertEquals("null", actualTypeName);
    assertTrue(actualNullType.isCachable());
  }
}
