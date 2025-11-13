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
package org.activiti.engine.impl.db;

import static org.junit.Assert.assertEquals;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class IdBlockDiffblueTest {
  /**
   * Test {@link IdBlock#IdBlock(long, long)}.
   *
   * <p>Method under test: {@link IdBlock#IdBlock(long, long)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void IdBlock.<init>(long, long)"})
  public void testNewIdBlock() {
    // Arrange and Act
    IdBlock actualIdBlock = new IdBlock(1L, 1L);

    // Assert
    assertEquals(1L, actualIdBlock.getLastId());
    assertEquals(1L, actualIdBlock.getNextId());
  }

  /**
   * Test getters and setters.
   *
   * <p>Methods under test:
   *
   * <ul>
   *   <li>{@link IdBlock#getLastId()}
   *   <li>{@link IdBlock#getNextId()}
   * </ul>
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"long IdBlock.getLastId()", "long IdBlock.getNextId()"})
  public void testGettersAndSetters() {
    // Arrange
    IdBlock idBlock = new IdBlock(1L, 1L);

    // Act
    long actualLastId = idBlock.getLastId();

    // Assert
    assertEquals(1L, actualLastId);
    assertEquals(1L, idBlock.getNextId());
  }
}
