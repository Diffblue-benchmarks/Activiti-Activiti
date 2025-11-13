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
package org.activiti.application.conf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.application.ApplicationEntryDiscovery;
import org.activiti.application.deployer.ProcessEntryDeployer;
import org.activiti.application.discovery.ProcessEntryDiscovery;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ApplicationProcessAutoConfiguration.class})
@DisabledInAotMode
@ExtendWith(SpringExtension.class)
class ApplicationProcessAutoConfigurationDiffblueTest {
  @Autowired private ApplicationProcessAutoConfiguration applicationProcessAutoConfiguration;

  @MockBean private RepositoryService repositoryService;

  /**
   * Test {@link ApplicationProcessAutoConfiguration#processEntryDiscovery()}.
   *
   * <p>Method under test: {@link ApplicationProcessAutoConfiguration#processEntryDiscovery()}
   */
  @Test
  @DisplayName("Test processEntryDiscovery()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ApplicationEntryDiscovery ApplicationProcessAutoConfiguration.processEntryDiscovery()"
  })
  void testProcessEntryDiscovery() {
    // Arrange and Act
    ApplicationEntryDiscovery actualProcessEntryDiscoveryResult =
        new ApplicationProcessAutoConfiguration().processEntryDiscovery();

    // Assert
    assertTrue(actualProcessEntryDiscoveryResult instanceof ProcessEntryDiscovery);
    assertEquals("processes", actualProcessEntryDiscoveryResult.getEntryType());
  }

  /**
   * Test {@link ApplicationProcessAutoConfiguration#processEntryDeployer(RepositoryService)}.
   *
   * <ul>
   *   <li>Then return {@link ProcessEntryDeployer}.
   * </ul>
   *
   * <p>Method under test: {@link
   * ApplicationProcessAutoConfiguration#processEntryDeployer(RepositoryService)}
   */
  @Test
  @DisplayName("Test processEntryDeployer(RepositoryService); then return ProcessEntryDeployer")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.application.deployer.ApplicationEntryDeployer ApplicationProcessAutoConfiguration.processEntryDeployer(RepositoryService)"
  })
  void testProcessEntryDeployer_thenReturnProcessEntryDeployer() {
    // Arrange, Act and Assert
    assertTrue(
        applicationProcessAutoConfiguration.processEntryDeployer(new RepositoryServiceImpl())
            instanceof ProcessEntryDeployer);
  }
}
