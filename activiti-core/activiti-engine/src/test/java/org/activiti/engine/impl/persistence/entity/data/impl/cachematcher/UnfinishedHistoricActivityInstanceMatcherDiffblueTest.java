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
package org.activiti.engine.impl.persistence.entity.data.impl.cachematcher;

import static org.junit.Assert.assertFalse;
import java.util.HashMap;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntity;
import org.activiti.engine.impl.persistence.entity.HistoricActivityInstanceEntityImpl;
import org.junit.Test;

public class UnfinishedHistoricActivityInstanceMatcherDiffblueTest {
  /**
   * Test
   * {@link UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   * with {@code HistoricActivityInstanceEntity}, {@code Object}.
   * <ul>
   *   <li>Given {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   */
  @Test
  public void testIsRetainedWithHistoricActivityInstanceEntityObject_given42() {
    // Arrange
    UnfinishedHistoricActivityInstanceMatcher unfinishedHistoricActivityInstanceMatcher = new UnfinishedHistoricActivityInstanceMatcher();

    HistoricActivityInstanceEntityImpl entity = new HistoricActivityInstanceEntityImpl();
    entity.setExecutionId("42");

    // Act and Assert
    assertFalse(unfinishedHistoricActivityInstanceMatcher.isRetained(entity, new HashMap<>()));
  }

  /**
   * Test
   * {@link UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   * with {@code HistoricActivityInstanceEntity}, {@code Object}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link UnfinishedHistoricActivityInstanceMatcher#isRetained(HistoricActivityInstanceEntity, Object)}
   */
  @Test
  public void testIsRetainedWithHistoricActivityInstanceEntityObject_thenReturnFalse() {
    // Arrange
    UnfinishedHistoricActivityInstanceMatcher unfinishedHistoricActivityInstanceMatcher = new UnfinishedHistoricActivityInstanceMatcher();
    HistoricActivityInstanceEntityImpl entity = new HistoricActivityInstanceEntityImpl();

    // Act and Assert
    assertFalse(unfinishedHistoricActivityInstanceMatcher.isRetained(entity, new HashMap<>()));
  }
}
