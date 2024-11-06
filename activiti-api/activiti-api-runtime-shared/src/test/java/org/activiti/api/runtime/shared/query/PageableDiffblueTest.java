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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PageableDiffblueTest {
  /**
   * Test {@link Pageable#of(int, int)} with {@code startIndex}, {@code maxItems}.
   * <p>
   * Method under test: {@link Pageable#of(int, int)}
   */
  @Test
  @DisplayName("Test of(int, int) with 'startIndex', 'maxItems'")
  void testOfWithStartIndexMaxItems() {
    // Arrange and Act
    Pageable actualOfResult = Pageable.of(1, 3);

    // Assert
    assertNull(actualOfResult.getOrder());
    assertEquals(1, actualOfResult.getStartIndex());
    assertEquals(3, actualOfResult.getMaxItems());
  }

  /**
   * Test {@link Pageable#of(int, int, Order)} with {@code startIndex},
   * {@code maxItems}, {@code order}.
   * <p>
   * Method under test: {@link Pageable#of(int, int, Order)}
   */
  @Test
  @DisplayName("Test of(int, int, Order) with 'startIndex', 'maxItems', 'order'")
  void testOfWithStartIndexMaxItemsOrder() {
    // Arrange
    Order order = Order.by("Property", Order.Direction.ASC);

    // Act
    Pageable actualOfResult = Pageable.of(1, 3, order);

    // Assert
    assertEquals(1, actualOfResult.getStartIndex());
    assertEquals(3, actualOfResult.getMaxItems());
    assertSame(order, actualOfResult.getOrder());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link Pageable#getMaxItems()}
   *   <li>{@link Pageable#getOrder()}
   *   <li>{@link Pageable#getStartIndex()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    Pageable ofResult = Pageable.of(1, 3);

    // Act
    int actualMaxItems = ofResult.getMaxItems();
    Order actualOrder = ofResult.getOrder();

    // Assert
    assertNull(actualOrder);
    assertEquals(1, ofResult.getStartIndex());
    assertEquals(3, actualMaxItems);
  }
}
