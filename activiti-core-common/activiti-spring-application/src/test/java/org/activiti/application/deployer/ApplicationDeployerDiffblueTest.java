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
package org.activiti.application.deployer;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.ArrayList;
import org.activiti.application.ApplicationDiscovery;
import org.activiti.application.ApplicationReader;
import org.activiti.application.ApplicationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

class ApplicationDeployerDiffblueTest {
  /**
   * Test {@link ApplicationDeployer#deploy()}.
   * <ul>
   *   <li>Then calls
   * {@link PathMatchingResourcePatternResolver#getResource(String)}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ApplicationDeployer#deploy()}
   */
  @Test
  @DisplayName("Test deploy(); then calls getResource(String)")
  void testDeploy_thenCallsGetResource() throws IOException {
    // Arrange
    PathMatchingResourcePatternResolver resourceLoader = mock(PathMatchingResourcePatternResolver.class);
    when(resourceLoader.getResources(Mockito.<String>any()))
        .thenReturn(new Resource[]{new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"))});
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));
    ApplicationDiscovery applicationDiscovery = new ApplicationDiscovery(resourceLoader, "Applications Location");

    ApplicationService applicationLoader = new ApplicationService(applicationDiscovery,
        new ApplicationReader(new ArrayList<>()));

    // Act
    (new ApplicationDeployer(applicationLoader, new ArrayList<>())).deploy();

    // Assert
    verify(resourceLoader).getResource(eq("Applications Location"));
    verify(resourceLoader).getResources(eq("Applications Location**.zip"));
  }
}
