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
package org.activiti.engine.impl.persistence.entity.data;

import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisAttachmentDataManager;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AbstractDataManagerDiffblueTest {
  @Mock private ProcessEngineConfigurationImpl processEngineConfigurationImpl;

  /**
   * Test {@link AbstractDataManager#getManagedEntitySubClasses()}.
   *
   * <p>Method under test: {@link AbstractDataManager#getManagedEntitySubClasses()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List AbstractDataManager.getManagedEntitySubClasses()"})
  public void testGetManagedEntitySubClasses() {
    // Arrange, Act and Assert
    assertNull(
        new MybatisAttachmentDataManager(new JtaProcessEngineConfiguration())
            .getManagedEntitySubClasses());
  }

  /**
   * Test {@link AbstractDataManager#findById(String)}.
   *
   * <ul>
   *   <li>When {@code null}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>Method under test: {@link AbstractDataManager#findById(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.impl.persistence.entity.Entity AbstractDataManager.findById(String)"
  })
  public void testFindById_whenNull_thenReturnNull() {
    // Arrange, Act and Assert
    assertNull(new MybatisAttachmentDataManager(processEngineConfigurationImpl).findById(null));
  }
}
