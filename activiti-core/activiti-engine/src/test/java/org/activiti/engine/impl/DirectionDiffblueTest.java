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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class DirectionDiffblueTest {
  /**
   * Test {@link Direction#Direction(String)}.
   *
   * <p>Method under test: {@link Direction#Direction(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void Direction.<init>(String)"})
  public void testNewDirection() {
    // Arrange, Act and Assert
    assertEquals("Name", new Direction("Name").getName());
  }

  /**
   * Test {@link Direction#getName()}.
   *
   * <p>Method under test: {@link Direction#getName()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String Direction.getName()"})
  public void testGetName() {
    // Arrange, Act and Assert
    assertEquals("Name", new Direction("Name").getName());
  }

  /**
   * Test {@link Direction#findByName(String)}.
   *
   * <p>Method under test: {@link Direction#findByName(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"Direction Direction.findByName(String)"})
  public void testFindByName() {
    // Arrange, Act and Assert
    assertNull(Direction.findByName("Direction Name"));
  }
}
