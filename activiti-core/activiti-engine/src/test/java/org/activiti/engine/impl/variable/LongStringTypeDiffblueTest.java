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
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.util.json.JSONObject;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class LongStringTypeDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link LongStringType#LongStringType(int)}
   *   <li>{@link LongStringType#getTypeName()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void LongStringType.<init>(int)", "java.lang.String LongStringType.getTypeName()"})
  public void testGettersAndSetters() {
    // Arrange, Act and Assert
    assertEquals("longString", (new LongStringType(3)).getTypeName());
  }

  /**
   * Test {@link LongStringType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongStringType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LongStringType.isAbleToStore(Object)"})
  public void testIsAbleToStore_when42_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new LongStringType(3)).isAbleToStore("42"));
  }

  /**
   * Test {@link LongStringType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@link JSONObject#NULL}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongStringType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LongStringType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new LongStringType(3)).isAbleToStore(JSONObject.NULL));
  }

  /**
   * Test {@link LongStringType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongStringType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LongStringType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenNull_thenReturnFalse2() {
    // Arrange, Act and Assert
    assertFalse((new LongStringType(3)).isAbleToStore(null));
  }

  /**
   * Test {@link LongStringType#isAbleToStore(Object)}.
   * <ul>
   *   <li>When {@code Value}.</li>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link LongStringType#isAbleToStore(Object)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"boolean LongStringType.isAbleToStore(Object)"})
  public void testIsAbleToStore_whenValue_thenReturnTrue() {
    // Arrange, Act and Assert
    assertTrue((new LongStringType(3)).isAbleToStore("Value"));
  }
}
