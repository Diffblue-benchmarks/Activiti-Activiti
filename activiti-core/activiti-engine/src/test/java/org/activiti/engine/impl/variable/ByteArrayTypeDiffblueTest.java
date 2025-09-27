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
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.persistence.entity.HistoricDetailVariableInstanceUpdateEntityImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ByteArrayTypeDiffblueTest {
  /**
   * Test {@link ByteArrayType#getValue(ValueFields)}.
   *
   * <ul>
   *   <li>When {@link HistoricDetailVariableInstanceUpdateEntityImpl} (default constructor).
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link ByteArrayType#getValue(ValueFields)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object ByteArrayType.getValue(ValueFields)"})
  public void testGetValue_whenHistoricDetailVariableInstanceUpdateEntityImpl_thenReturnNull() {
    // Arrange
    ByteArrayType byteArrayType = new ByteArrayType();

    // Act and Assert
    assertNull(byteArrayType.getValue(new HistoricDetailVariableInstanceUpdateEntityImpl()));
  }

  /**
   * Test {@link ByteArrayType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@link JSONObject#NULL}.
   *   <li>Then return {@code false}.
   * </ul>
   *
   * <p>Method under test: {@link ByteArrayType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ByteArrayType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(new ByteArrayType().isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link ByteArrayType#isAbleToStore(Object)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code true}.
   * </ul>
   *
   * <p>Method under test: {@link ByteArrayType#isAbleToStore(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean ByteArrayType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(new ByteArrayType().isAbleToStore(null));
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>default or parameterless constructor of {@link ByteArrayType}
   *   <li>{@link ByteArrayType#getTypeName()}
   *   <li>{@link ByteArrayType#isCachable()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "void ByteArrayType.<init>()",
    "String ByteArrayType.getTypeName()",
    "boolean ByteArrayType.isCachable()"
  })
  public void testGettersAndSetters() {
    // Arrange and Act
    ByteArrayType actualByteArrayType = new ByteArrayType();
    String actualTypeName = actualByteArrayType.getTypeName();

    // Assert
    assertEquals("bytes", actualTypeName);
    assertTrue(actualByteArrayType.isCachable());
  }
}
