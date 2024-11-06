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
package org.activiti.spring.impl.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CleanTestExecutionListenerDiffblueTest {
  /**
   * Test new {@link CleanTestExecutionListener} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link CleanTestExecutionListener}
   */
  @Test
  public void testNewCleanTestExecutionListener() {
    // Arrange, Act and Assert
    assertEquals(Integer.MAX_VALUE, (new CleanTestExecutionListener()).getOrder());
  }
}
