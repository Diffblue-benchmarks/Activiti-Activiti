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
package org.activiti.engine.impl.transformer;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class StringToIntegerDiffblueTest {
  /**
   * Test {@link StringToInteger#primTransform(Object)}.
   *
   * <ul>
   *   <li>When {@code 42}.
   *   <li>Then return intValue is forty-two.
   * </ul>
   *
   * <p>Method under test: {@link StringToInteger#primTransform(Object)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Object StringToInteger.primTransform(Object)"})
  public void testPrimTransform_when42_thenReturnIntValueIsFortyTwo() throws Exception {
    // Arrange, Act and Assert
    assertEquals(42, ((Integer) new StringToInteger().primTransform("42")).intValue());
  }
}
