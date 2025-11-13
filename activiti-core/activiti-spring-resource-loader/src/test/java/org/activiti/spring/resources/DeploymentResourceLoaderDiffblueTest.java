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
package org.activiti.spring.resources;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeploymentResourceLoaderDiffblueTest {
  @InjectMocks private DeploymentResourceLoader<Object> deploymentResourceLoader;

  @Mock private Map<String, List<Object>> map;

  /**
   * Test {@link DeploymentResourceLoader#loadResourcesForDeployment(String, ResourceReader)}.
   *
   * <ul>
   *   <li>Given {@link Map} {@link Map#get(Object)} return {@link ArrayList#ArrayList()}.
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link DeploymentResourceLoader#loadResourcesForDeployment(String,
   * ResourceReader)}
   */
  @Test
  @DisplayName(
      "Test loadResourcesForDeployment(String, ResourceReader); given Map get(Object) return ArrayList(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "List DeploymentResourceLoader.loadResourcesForDeployment(String, ResourceReader)"
  })
  void testLoadResourcesForDeployment_givenMapGetReturnArrayList_thenReturnEmpty() {
    // Arrange
    when(map.get(Mockito.<Object>any())).thenReturn(new ArrayList<>());

    // Act
    List<Object> actualLoadResourcesForDeploymentResult =
        deploymentResourceLoader.loadResourcesForDeployment("42", mock(ResourceReader.class));

    // Assert
    verify(map).get(isA(Object.class));
    assertTrue(actualLoadResourcesForDeploymentResult.isEmpty());
  }
}
