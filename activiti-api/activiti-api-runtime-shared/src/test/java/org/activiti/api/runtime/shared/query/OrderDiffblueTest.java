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
import org.junit.jupiter.api.Test;

class OrderDiffblueTest {
  /**
   * Method under test: {@link Order#by(String, Order.Direction)}
   */
  @Test
  void testBy() {
    // Arrange and Act
    Order actualByResult = Order.by("Property", Order.Direction.ASC);

    // Assert
    assertEquals("Property", actualByResult.getProperty());
    assertEquals(Order.Direction.ASC, actualByResult.getDirection());
  }

  /**
   * Methods under test:
   * <ul>
   *   <li>{@link Order#getDirection()}
   *   <li>{@link Order#getProperty()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange
    Order byResult = Order.by("Property", Order.Direction.ASC);

    // Act
    Order.Direction actualDirection = byResult.getDirection();

    // Assert
    assertEquals("Property", byResult.getProperty());
    assertEquals(Order.Direction.ASC, actualDirection);
  }

  /**
   * Method under test: {@link Order#Order(String)}
   */
  @Test
  void testNewOrder() {
    // Arrange and Act
    Order actualOrder = new Order("Property");

    // Assert
    assertEquals("Property", actualOrder.getProperty());
    assertEquals(Order.Direction.ASC, actualOrder.getDirection());
  }
}
