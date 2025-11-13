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
package org.activiti.application;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;

class ApplicationServiceDiffblueTest {
  /**
   * Test {@link ApplicationService#loadApplications()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationService#loadApplications()}
   */
  @Test
  @DisplayName("Test loadApplications(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List ApplicationService.loadApplications()"})
  void testLoadApplications_thenReturnEmpty() {
    // Arrange
    ApplicationDiscovery applicationDiscovery =
        new ApplicationDiscovery(
            new AnnotationConfigReactiveWebApplicationContext(), "Applications Location");
    ApplicationService applicationService =
        new ApplicationService(applicationDiscovery, new ApplicationReader(new ArrayList<>()));

    // Act and Assert
    assertTrue(applicationService.loadApplications().isEmpty());
  }
}
