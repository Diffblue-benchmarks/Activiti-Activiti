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
package org.activiti.engine.delegate.event;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ActivitiIllegalArgumentException;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivitiEventTypeDiffblueTest {
  /**
   * Test {@link ActivitiEventType#getTypesFromString(String)}.
   *
   * <ul>
   *   <li>Then return array of {@link ActivitiEventType} with {@code ENTITY_CREATED}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventType#getTypesFromString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ActivitiEventType[] ActivitiEventType.getTypesFromString(String)"})
  public void testGetTypesFromString_thenReturnArrayOfActivitiEventTypeWithEntityCreated() {
    // Arrange, Act and Assert
    assertArrayEquals(
        new ActivitiEventType[] {ActivitiEventType.ENTITY_CREATED},
        ActivitiEventType.getTypesFromString("ENTITY_CREATED"));
  }

  /**
   * Test {@link ActivitiEventType#getTypesFromString(String)}.
   *
   * <ul>
   *   <li>When {@code ,}.
   *   <li>Then return array length is zero.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventType#getTypesFromString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ActivitiEventType[] ActivitiEventType.getTypesFromString(String)"})
  public void testGetTypesFromString_whenComma_thenReturnArrayLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, ActivitiEventType.getTypesFromString(",").length);
  }

  /**
   * Test {@link ActivitiEventType#getTypesFromString(String)}.
   *
   * <ul>
   *   <li>When empty string.
   *   <li>Then return array length is zero.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventType#getTypesFromString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ActivitiEventType[] ActivitiEventType.getTypesFromString(String)"})
  public void testGetTypesFromString_whenEmptyString_thenReturnArrayLengthIsZero() {
    // Arrange, Act and Assert
    assertEquals(0, ActivitiEventType.getTypesFromString("").length);
  }

  /**
   * Test {@link ActivitiEventType#getTypesFromString(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return array length is zero.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventType#getTypesFromString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ActivitiEventType[] ActivitiEventType.getTypesFromString(String)"})
  public void testGetTypesFromString_whenNull_thenReturnArrayLengthIsZero() {
    // Arrange and Act
    ActivitiEventType[] actualTypesFromString = ActivitiEventType.getTypesFromString(null);

    // Assert
    assertEquals(0, actualTypesFromString.length);
  }

  /**
   * Test {@link ActivitiEventType#getTypesFromString(String)}.
   *
   * <ul>
   *   <li>When {@code String}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventType#getTypesFromString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ActivitiEventType[] ActivitiEventType.getTypesFromString(String)"})
  public void testGetTypesFromString_whenString_thenThrowActivitiIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ActivitiEventType.getTypesFromString("String"));
  }

  /**
   * Test {@link ActivitiEventType#getTypesFromString(String)}.
   *
   * <ul>
   *   <li>When {@code String,}.
   *   <li>Then throw {@link ActivitiIllegalArgumentException}.
   * </ul>
   *
   * <p>Method under test: {@link ActivitiEventType#getTypesFromString(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"ActivitiEventType[] ActivitiEventType.getTypesFromString(String)"})
  public void testGetTypesFromString_whenString_thenThrowActivitiIllegalArgumentException2() {
    // Arrange, Act and Assert
    assertThrows(
        ActivitiIllegalArgumentException.class,
        () -> ActivitiEventType.getTypesFromString("String,"));
  }
}
