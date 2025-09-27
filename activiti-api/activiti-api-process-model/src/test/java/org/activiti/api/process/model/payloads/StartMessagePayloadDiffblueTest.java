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
package org.activiti.api.process.model.payloads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StartMessagePayloadDiffblueTest {
  /**
   * Test {@link StartMessagePayload#StartMessagePayload()}.
   *
   * <p>Method under test: {@link StartMessagePayload#StartMessagePayload()}
   */
  @Test
  @DisplayName("Test new StartMessagePayload()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartMessagePayload.<init>()"})
  void testNewStartMessagePayload() {
    // Arrange and Act
    StartMessagePayload actualStartMessagePayload = new StartMessagePayload();

    // Assert
    assertNull(actualStartMessagePayload.getBusinessKey());
    assertNull(actualStartMessagePayload.getName());
    assertTrue(actualStartMessagePayload.getVariables().isEmpty());
  }

  /**
   * Test {@link StartMessagePayload#StartMessagePayload(String, String, Map)}.
   *
   * <p>Method under test: {@link StartMessagePayload#StartMessagePayload(String, String, Map)}
   */
  @Test
  @DisplayName("Test new StartMessagePayload(String, String, Map)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void StartMessagePayload.<init>(String, String, Map)"})
  void testNewStartMessagePayload2() {
    // Arrange and Act
    StartMessagePayload actualStartMessagePayload =
        new StartMessagePayload("Name", "Business Key", new HashMap<>());

    // Assert
    assertEquals("Business Key", actualStartMessagePayload.getBusinessKey());
    assertEquals("Name", actualStartMessagePayload.getName());
    assertTrue(actualStartMessagePayload.getVariables().isEmpty());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link StartMessagePayload#toString()}
   *   <li>{@link StartMessagePayload#getBusinessKey()}
   *   <li>{@link StartMessagePayload#getId()}
   *   <li>{@link StartMessagePayload#getName()}
   *   <li>{@link StartMessagePayload#getVariables()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "String StartMessagePayload.getBusinessKey()",
    "String StartMessagePayload.getId()",
    "String StartMessagePayload.getName()",
    "Map StartMessagePayload.getVariables()",
    "String StartMessagePayload.toString()"
  })
  void testGettersAndSetters() {
    // Arrange
    StartMessagePayload startMessagePayload = new StartMessagePayload();

    // Act
    startMessagePayload.toString();
    String actualBusinessKey = startMessagePayload.getBusinessKey();
    startMessagePayload.getId();
    String actualName = startMessagePayload.getName();

    // Assert
    assertNull(actualBusinessKey);
    assertNull(actualName);
    assertTrue(startMessagePayload.getVariables().isEmpty());
  }

  /**
   * Test {@link StartMessagePayload#equals(Object)}, and {@link StartMessagePayload#hashCode()}.
   *
   * <ul>
   *   <li>When other is same.
   *   <li>Then return equal.
   * </ul>
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link StartMessagePayload#equals(Object)}
   *   <li>{@link StartMessagePayload#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean StartMessagePayload.equals(Object)",
    "int StartMessagePayload.hashCode()"
  })
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    StartMessagePayload startMessagePayload = new StartMessagePayload();

    // Act and Assert
    assertEquals(startMessagePayload, startMessagePayload);
    int expectedHashCodeResult = startMessagePayload.hashCode();
    assertEquals(expectedHashCodeResult, startMessagePayload.hashCode());
  }

  /**
   * Test {@link StartMessagePayload#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link StartMessagePayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean StartMessagePayload.equals(Object)",
    "int StartMessagePayload.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    StartMessagePayload startMessagePayload = new StartMessagePayload();

    // Act and Assert
    assertNotEquals(startMessagePayload, new StartMessagePayload());
  }

  /**
   * Test {@link StartMessagePayload#equals(Object)}.
   *
   * <ul>
   *   <li>When other is different.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link StartMessagePayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean StartMessagePayload.equals(Object)",
    "int StartMessagePayload.hashCode()"
  })
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual2() {
    // Arrange
    StartMessagePayload startMessagePayload =
        new StartMessagePayload("Name", "Business Key", new HashMap<>());

    // Act and Assert
    assertNotEquals(startMessagePayload, new StartMessagePayload());
  }

  /**
   * Test {@link StartMessagePayload#equals(Object)}.
   *
   * <ul>
   *   <li>When other is {@code null}.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link StartMessagePayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean StartMessagePayload.equals(Object)",
    "int StartMessagePayload.hashCode()"
  })
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StartMessagePayload(), null);
  }

  /**
   * Test {@link StartMessagePayload#equals(Object)}.
   *
   * <ul>
   *   <li>When other is wrong type.
   *   <li>Then return not equal.
   * </ul>
   *
   * <p>Method under test: {@link StartMessagePayload#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "boolean StartMessagePayload.equals(Object)",
    "int StartMessagePayload.hashCode()"
  })
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange, Act and Assert
    assertNotEquals(new StartMessagePayload(), "Different type to StartMessagePayload");
  }
}
