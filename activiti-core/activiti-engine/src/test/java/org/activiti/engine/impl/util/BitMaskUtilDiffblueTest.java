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
   * Test {@link BitMaskUtil#setBitOn(int, int)}.
   * <ul>
   *   <li>When eight.</li>
   *   <li>Then return one hundred seventy.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBitOn(int, int)}
   */
  @Test
  public void testSetBitOn_whenEight_thenReturnOneHundredSeventy() {
    // Arrange, Act and Assert
    assertEquals(170, BitMaskUtil.setBitOn(42, 8));
  }

  /**
   * Test {@link BitMaskUtil#setBitOn(int, int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return forty-three.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBitOn(int, int)}
   */
  @Test
  public void testSetBitOn_whenOne_thenReturnFortyThree() {
    // Arrange, Act and Assert
    assertEquals(43, BitMaskUtil.setBitOn(42, 1));
  }

  /**
   * Test {@link BitMaskUtil#setBitOn(int, int)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBitOn(int, int)}
   */
  @Test
  public void testSetBitOn_whenTen_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBitOn(42, 10));
  }

  /**
   * Test {@link BitMaskUtil#setBitOn(int, int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBitOn(int, int)}
   */
  @Test
  public void testSetBitOn_whenZero_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBitOn(42, 0));
  }

  /**
   * Test {@link BitMaskUtil#setBitOff(int, int)}.
   * <ul>
   *   <li>When eight.</li>
   *   <li>Then return forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBitOff(int, int)}
   */
  @Test
  public void testSetBitOff_whenEight_thenReturnFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, BitMaskUtil.setBitOff(42, 8));
  }

  /**
   * Test {@link BitMaskUtil#setBitOff(int, int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBitOff(int, int)}
   */
  @Test
  public void testSetBitOff_whenOne_thenReturnFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, BitMaskUtil.setBitOff(42, 1));
  }

  /**
   * Test {@link BitMaskUtil#setBitOff(int, int)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBitOff(int, int)}
   */
  @Test
  public void testSetBitOff_whenTen_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBitOff(42, 10));
  }

  /**
   * Test {@link BitMaskUtil#setBitOff(int, int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBitOff(int, int)}
   */
  @Test
  public void testSetBitOff_whenZero_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBitOff(42, 0));
  }

  /**
   * Test {@link BitMaskUtil#isBitOn(int, int)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#isBitOn(int, int)}
   */
  @Test
  public void testIsBitOn_whenOne_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse(BitMaskUtil.isBitOn(42, 1));
  }

  /**
   * Test {@link BitMaskUtil#isBitOn(int, int)}.
   * <ul>
   *   <li>When six.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#isBitOn(int, int)}
   */
  @Test
  public void testIsBitOn_whenSix_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue(BitMaskUtil.isBitOn(42, 6));
  }

  /**
   * Test {@link BitMaskUtil#isBitOn(int, int)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#isBitOn(int, int)}
   */
  @Test
  public void testIsBitOn_whenTen_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.isBitOn(42, 10));
  }

  /**
   * Test {@link BitMaskUtil#isBitOn(int, int)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#isBitOn(int, int)}
   */
  @Test
  public void testIsBitOn_whenZero_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.isBitOn(42, 0));
  }

  /**
   * Test {@link BitMaskUtil#setBit(int, int, boolean)}.
   * <ul>
   *   <li>When nine.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBit(int, int, boolean)}
   */
  @Test
  public void testSetBit_whenNine_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBit(42, 9, false));
  }

  /**
   * Test {@link BitMaskUtil#setBit(int, int, boolean)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return forty-three.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBit(int, int, boolean)}
   */
  @Test
  public void testSetBit_whenOne_thenReturnFortyThree() {
    // Arrange, Act and Assert
    assertEquals(43, BitMaskUtil.setBit(42, 1, true));
  }

  /**
   * Test {@link BitMaskUtil#setBit(int, int, boolean)}.
   * <ul>
   *   <li>When one.</li>
   *   <li>Then return forty-two.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBit(int, int, boolean)}
   */
  @Test
  public void testSetBit_whenOne_thenReturnFortyTwo() {
    // Arrange, Act and Assert
    assertEquals(42, BitMaskUtil.setBit(42, 1, false));
  }

  /**
   * Test {@link BitMaskUtil#setBit(int, int, boolean)}.
   * <ul>
   *   <li>When ten.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBit(int, int, boolean)}
   */
  @Test
  public void testSetBit_whenTen_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBit(42, 10, true));
  }

  /**
   * Test {@link BitMaskUtil#setBit(int, int, boolean)}.
   * <ul>
   *   <li>When zero.</li>
   *   <li>Then throw {@link IllegalArgumentException}.</li>
   * </ul>
   * <p>
   * Method under test: {@link BitMaskUtil#setBit(int, int, boolean)}
   */
  @Test
  public void testSetBit_whenZero_thenThrowIllegalArgumentException() {
    // Arrange, Act and Assert
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBit(42, 0, false));
    assertThrows(IllegalArgumentException.class, () -> BitMaskUtil.setBit(42, 0, true));
  }
}
