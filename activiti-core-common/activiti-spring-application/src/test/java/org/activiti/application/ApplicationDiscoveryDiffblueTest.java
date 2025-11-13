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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.ResourceLoader;

class ApplicationDiscoveryDiffblueTest {
  /**
   * Test {@link ApplicationDiscovery#discoverApplications()}.
   *
   * <ul>
   *   <li>Then return Empty.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationDiscovery#discoverApplications()}
   */
  @Test
  @DisplayName("Test discoverApplications(); then return Empty")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List ApplicationDiscovery.discoverApplications()"})
  void testDiscoverApplications_thenReturnEmpty() {
    // Arrange, Act and Assert
    assertTrue(
        new ApplicationDiscovery(
                new AnnotationConfigReactiveWebApplicationContext(), "Applications Location")
            .discoverApplications()
            .isEmpty());
  }

  /**
   * Test {@link ApplicationDiscovery#discoverApplications()}.
   *
   * <ul>
   *   <li>Then throw {@link ApplicationLoadException}.
   * </ul>
   *
   * <p>Method under test: {@link ApplicationDiscovery#discoverApplications()}
   */
  @Test
  @DisplayName("Test discoverApplications(); then throw ApplicationLoadException")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"java.util.List ApplicationDiscovery.discoverApplications()"})
  void testDiscoverApplications_thenThrowApplicationLoadException()
      throws UnsupportedEncodingException {
    // Arrange
    ProtocolResolver resolver = mock(ProtocolResolver.class);
    when(resolver.resolve(Mockito.<String>any(), Mockito.<ResourceLoader>any()))
        .thenReturn(new ByteArrayResource("AXAXAXAX".getBytes("UTF-8")));

    AnnotationConfigReactiveWebApplicationContext resourceLoader =
        new AnnotationConfigReactiveWebApplicationContext();
    resourceLoader.addProtocolResolver(resolver);

    // Act and Assert
    assertThrows(
        ApplicationLoadException.class,
        () ->
            new ApplicationDiscovery(resourceLoader, "Applications Location")
                .discoverApplications());
    verify(resolver, atLeast(1)).resolve(Mockito.<String>any(), isA(ResourceLoader.class));
  }
}
