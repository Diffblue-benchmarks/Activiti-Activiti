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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SetProcessDefinitionCategoryCmdDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link SetProcessDefinitionCategoryCmd#SetProcessDefinitionCategoryCmd(String, String)}
   *   <li>{@link SetProcessDefinitionCategoryCmd#setCategory(String)}
   *   <li>{@link SetProcessDefinitionCategoryCmd#setProcessDefinitionId(String)}
   *   <li>{@link SetProcessDefinitionCategoryCmd#getCategory()}
   *   <li>{@link SetProcessDefinitionCategoryCmd#getProcessDefinitionId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    SetProcessDefinitionCategoryCmd actualSetProcessDefinitionCategoryCmd = new SetProcessDefinitionCategoryCmd("42",
        "Category");
    actualSetProcessDefinitionCategoryCmd.setCategory("Category");
    actualSetProcessDefinitionCategoryCmd.setProcessDefinitionId("42");
    String actualCategory = actualSetProcessDefinitionCategoryCmd.getCategory();

    // Assert that nothing has changed
    assertEquals("42", actualSetProcessDefinitionCategoryCmd.getProcessDefinitionId());
    assertEquals("Category", actualCategory);
  }
}
