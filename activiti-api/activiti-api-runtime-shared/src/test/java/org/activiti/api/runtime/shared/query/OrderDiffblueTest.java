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
package org.activiti.api.runtime.shared.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.runtime.shared.query.Order.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class OrderDiffblueTest {
  /**
   * Test {@link Order#Order(String)}.
   * <p>
   * Method under test: {@link Order#Order(String)}
   */
  @Test
  @DisplayName("Test new Order(String)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"void Order.<init>(String)"})
  void testNewOrder() {
    // Arrange and Act
    Order actualOrder = new Order("Property");

    // Assert
    assertEquals("Property", actualOrder.getProperty());
    assertEquals(Direction.ASC, actualOrder.getDirection());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Order#getDirection()}
   *   <li>{@link Order#getProperty()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Direction Order.getDirection()", "String Order.getProperty()"})
  void testGettersAndSetters() {
    // Arrange
    Order byResult = Order.by("Property", Direction.ASC);

    // Act
    Direction actualDirection = byResult.getDirection();

    // Assert
    assertEquals("Property", byResult.getProperty());
    assertEquals(Direction.ASC, actualDirection);
  }

  /**
   * Test {@link Order#by(String, Direction)}.
   * <p>
   * Method under test: {@link Order#by(String, Direction)}
   */
  @Test
  @DisplayName("Test by(String, Direction)")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"Order Order.by(String, Direction)"})
  void testBy() {
    // Arrange and Act
    Order actualByResult = Order.by("Property", Direction.ASC);

    // Assert
    assertEquals("Property", actualByResult.getProperty());
    assertEquals(Direction.ASC, actualByResult.getDirection());
  }
}
