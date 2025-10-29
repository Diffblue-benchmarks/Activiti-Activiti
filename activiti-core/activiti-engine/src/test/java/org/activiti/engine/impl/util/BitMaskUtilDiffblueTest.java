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
package org.activiti.engine.impl.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BitMaskUtilDiffblueTest {
  /**
   * Method under test: {@link BitMaskUtil#setBitOn(int, int)}
   */
  @Test
  public void testSetBitOn() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBitOn(42, 10));
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBitOn(42, 0));
    assertEquals(43, BitMaskUtil.setBitOn(42, 1));
    assertEquals(170, BitMaskUtil.setBitOn(42, 8));
  }

  /**
   * Method under test: {@link BitMaskUtil#setBitOff(int, int)}
   */
  @Test
  public void testSetBitOff() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBitOff(42, 10));
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBitOff(42, 0));
    assertEquals(42, BitMaskUtil.setBitOff(42, 1));
    assertEquals(42, BitMaskUtil.setBitOff(42, 8));
  }

  /**
   * Method under test: {@link BitMaskUtil#isBitOn(int, int)}
   */
  @Test
  public void testIsBitOn() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.isBitOn(42, 10));
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.isBitOn(42, 0));
    assertFalse(BitMaskUtil.isBitOn(42, 1));
    assertTrue(BitMaskUtil.isBitOn(42, 6));
  }

  /**
   * Method under test: {@link BitMaskUtil#setBit(int, int, boolean)}
   */
  @Test
  public void testSetBit() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBit(42, 10, true));
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBit(42, 0, false));
    assertEquals(42, BitMaskUtil.setBit(42, 1, false));
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBit(42, 9, false));
    assertEquals(43, BitMaskUtil.setBit(42, 1, true));
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBit(42, 0, true));
  }
}
