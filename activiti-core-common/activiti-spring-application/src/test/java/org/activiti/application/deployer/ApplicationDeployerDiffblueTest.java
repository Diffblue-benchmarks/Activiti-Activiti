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

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.IOException;
import java.util.ArrayList;
import org.activiti.application.ApplicationContent;
import org.activiti.application.ApplicationDiscovery;
import org.activiti.application.ApplicationReader;
import org.activiti.application.ApplicationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

class ApplicationDeployerDiffblueTest {
  /**
   * Test {@link ApplicationDeployer#deploy()}.
   *
   * <ul>
   *   <li>Given {@link ArrayList#ArrayList()} add {@link ApplicationContent} (default constructor).
   *   <li>Then calls {@link ApplicationEntryDeployer#deployEntries(ApplicationContent)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationDeployer#deploy()}
   */
  @Test
  @DisplayName(
      "Test deploy(); given ArrayList() add ApplicationContent (default constructor); then calls deployEntries(ApplicationContent)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ApplicationDeployer.deploy()"})
  void testDeploy_givenArrayListAddApplicationContent_thenCallsDeployEntries() {
    // Arrange
    ArrayList<ApplicationContent> applicationContentList = new ArrayList<>();
    applicationContentList.add(new ApplicationContent());

    ApplicationService applicationLoader = mock(ApplicationService.class);
    when(applicationLoader.loadApplications()).thenReturn(applicationContentList);

    ApplicationEntryDeployer applicationEntryDeployer = mock(ApplicationEntryDeployer.class);
    doNothing().when(applicationEntryDeployer).deployEntries(Mockito.<ApplicationContent>any());

    ArrayList<ApplicationEntryDeployer> deployers = new ArrayList<>();
    deployers.add(applicationEntryDeployer);

    ApplicationDeployer applicationDeployer = new ApplicationDeployer(applicationLoader, deployers);

    // Act
    applicationDeployer.deploy();

    // Assert
    verify(applicationLoader).loadApplications();
    verify(applicationEntryDeployer).deployEntries(isA(ApplicationContent.class));
  }

  /**
   * Test {@link ApplicationDeployer#deploy()}.
   *
   * <ul>
   *   <li>Then calls {@link ApplicationDiscovery#discoverApplications()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationDeployer#deploy()}
   */
  @Test
  @DisplayName("Test deploy(); then calls discoverApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ApplicationDeployer.deploy()"})
  void testDeploy_thenCallsDiscoverApplications() {
    // Arrange
    ApplicationDiscovery applicationDiscovery = mock(ApplicationDiscovery.class);
    when(applicationDiscovery.discoverApplications()).thenReturn(new ArrayList<>());
    ApplicationService applicationLoader =
        new ApplicationService(applicationDiscovery, new ApplicationReader(new ArrayList<>()));
    ApplicationDeployer applicationDeployer =
        new ApplicationDeployer(applicationLoader, new ArrayList<>());

    // Act
    applicationDeployer.deploy();

    // Assert
    verify(applicationDiscovery).discoverApplications();
  }

  /**
   * Test {@link ApplicationDeployer#deploy()}.
   *
   * <ul>
   *   <li>Then calls {@link PathMatchingResourcePatternResolver#getResource(String)}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationDeployer#deploy()}
   */
  @Test
  @DisplayName("Test deploy(); then calls getResource(String)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ApplicationDeployer.deploy()"})
  void testDeploy_thenCallsGetResource() throws IOException {
    // Arrange
    PathMatchingResourcePatternResolver resourceLoader =
        mock(PathMatchingResourcePatternResolver.class);
    when(resourceLoader.getResources(Mockito.<String>any()))
        .thenReturn(new Resource[] {new ByteArrayResource("AXAXAXAX".getBytes("UTF-8"))});
    when(resourceLoader.getResource(Mockito.<String>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));
    ApplicationDiscovery applicationDiscovery =
        new ApplicationDiscovery(resourceLoader, "Applications Location");
    ApplicationService applicationLoader =
        new ApplicationService(applicationDiscovery, new ApplicationReader(new ArrayList<>()));
    ApplicationDeployer applicationDeployer =
        new ApplicationDeployer(applicationLoader, new ArrayList<>());

    // Act
    applicationDeployer.deploy();

    // Assert
    verify(resourceLoader).getResource("Applications Location");
    verify(resourceLoader).getResources("Applications Location**.zip");
  }

  /**
   * Test {@link ApplicationDeployer#deploy()}.
   *
   * <ul>
   *   <li>Then calls {@link ApplicationService#loadApplications()}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationDeployer#deploy()}
   */
  @Test
  @DisplayName("Test deploy(); then calls loadApplications()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void ApplicationDeployer.deploy()"})
  void testDeploy_thenCallsLoadApplications() {
    // Arrange
    ApplicationService applicationLoader = mock(ApplicationService.class);
    when(applicationLoader.loadApplications()).thenReturn(new ArrayList<>());
    ApplicationDeployer applicationDeployer =
        new ApplicationDeployer(applicationLoader, new ArrayList<>());

    // Act
    applicationDeployer.deploy();

    // Assert
    verify(applicationLoader).loadApplications();
  }
}
