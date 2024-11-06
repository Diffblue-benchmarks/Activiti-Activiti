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
package org.activiti.api.runtime.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ApplicationElementImplDiffblueTest {
  /**
   * Test {@link ApplicationElementImpl#equals(Object)}, and
   * {@link ApplicationElementImpl#hashCode()}.
   * <ul>
   *   <li>When other is equal.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ApplicationElementImpl#equals(Object)}
   *   <li>{@link ApplicationElementImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is equal; then return equal")
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    // Arrange
    ApplicationElementImpl applicationElementImpl = new ApplicationElementImpl();
    applicationElementImpl.setAppVersion("1.0.2");

    ApplicationElementImpl applicationElementImpl2 = new ApplicationElementImpl();
    applicationElementImpl2.setAppVersion("1.0.2");

    // Act and Assert
    assertEquals(applicationElementImpl, applicationElementImpl2);
    int expectedHashCodeResult = applicationElementImpl.hashCode();
    assertEquals(expectedHashCodeResult, applicationElementImpl2.hashCode());
  }

  /**
   * Test {@link ApplicationElementImpl#equals(Object)}, and
   * {@link ApplicationElementImpl#hashCode()}.
   * <ul>
   *   <li>When other is same.</li>
   *   <li>Then return equal.</li>
   * </ul>
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ApplicationElementImpl#equals(Object)}
   *   <li>{@link ApplicationElementImpl#hashCode()}
   * </ul>
   */
  @Test
  @DisplayName("Test equals(Object), and hashCode(); when other is same; then return equal")
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    // Arrange
    ApplicationElementImpl applicationElementImpl = new ApplicationElementImpl();
    applicationElementImpl.setAppVersion("1.0.2");

    // Act and Assert
    assertEquals(applicationElementImpl, applicationElementImpl);
    int expectedHashCodeResult = applicationElementImpl.hashCode();
    assertEquals(expectedHashCodeResult, applicationElementImpl.hashCode());
  }

  /**
   * Test {@link ApplicationElementImpl#equals(Object)}.
   * <ul>
   *   <li>When other is different.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ApplicationElementImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is different; then return not equal")
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    // Arrange
    ApplicationElementImpl applicationElementImpl = new ApplicationElementImpl();
    applicationElementImpl.setAppVersion("App Version");

    ApplicationElementImpl applicationElementImpl2 = new ApplicationElementImpl();
    applicationElementImpl2.setAppVersion("1.0.2");

    // Act and Assert
    assertNotEquals(applicationElementImpl, applicationElementImpl2);
  }

  /**
   * Test {@link ApplicationElementImpl#equals(Object)}.
   * <ul>
   *   <li>When other is {@code null}.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ApplicationElementImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is 'null'; then return not equal")
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    // Arrange
    ApplicationElementImpl applicationElementImpl = new ApplicationElementImpl();
    applicationElementImpl.setAppVersion("1.0.2");

    // Act and Assert
    assertNotEquals(applicationElementImpl, null);
  }

  /**
   * Test {@link ApplicationElementImpl#equals(Object)}.
   * <ul>
   *   <li>When other is wrong type.</li>
   *   <li>Then return not equal.</li>
   * </ul>
   * <p>
   * Method under test: {@link ApplicationElementImpl#equals(Object)}
   */
  @Test
  @DisplayName("Test equals(Object); when other is wrong type; then return not equal")
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    // Arrange
    ApplicationElementImpl applicationElementImpl = new ApplicationElementImpl();
    applicationElementImpl.setAppVersion("1.0.2");

    // Act and Assert
    assertNotEquals(applicationElementImpl, "Different type to ApplicationElementImpl");
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ApplicationElementImpl}
   *   <li>{@link ApplicationElementImpl#setAppVersion(String)}
   *   <li>{@link ApplicationElementImpl#toString()}
   *   <li>{@link ApplicationElementImpl#getAppVersion()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange and Act
    ApplicationElementImpl actualApplicationElementImpl = new ApplicationElementImpl();
    actualApplicationElementImpl.setAppVersion("1.0.2");
    String actualToStringResult = actualApplicationElementImpl.toString();

    // Assert that nothing has changed
    assertEquals("1.0.2", actualApplicationElementImpl.getAppVersion());
    assertEquals("ApplicationElementImpl{appVersion='1.0.2'}", actualToStringResult);
  }
}
