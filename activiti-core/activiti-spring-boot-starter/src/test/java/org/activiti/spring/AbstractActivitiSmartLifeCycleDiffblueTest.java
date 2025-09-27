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
package org.activiti.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import org.activiti.engine.RepositoryService;
import org.activiti.runtime.api.model.impl.APIDeploymentConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ExtendWith(MockitoExtension.class)
class AbstractActivitiSmartLifeCycleDiffblueTest {
  @Mock private APIDeploymentConverter aPIDeploymentConverter;

  @Mock private RepositoryService repositoryService;

  /**
   * Test {@link AbstractActivitiSmartLifeCycle#isAutoStartup()}.
   *
   * <p>Method under test: {@link AbstractActivitiSmartLifeCycle#isAutoStartup()}
   */
  @Test
  @DisplayName("Test isAutoStartup()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractActivitiSmartLifeCycle.isAutoStartup()"})
  void testIsAutoStartup() {
    // Arrange
    ApplicationDeployedEventProducer applicationDeployedEventProducer =
        new ApplicationDeployedEventProducer(
            repositoryService,
            aPIDeploymentConverter,
            new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Act and Assert
    assertTrue(applicationDeployedEventProducer.isAutoStartup());
  }

  /**
   * Test {@link AbstractActivitiSmartLifeCycle#setPhase(int)}.
   *
   * <p>Method under test: {@link AbstractActivitiSmartLifeCycle#setPhase(int)}
   */
  @Test
  @DisplayName("Test setPhase(int)")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractActivitiSmartLifeCycle.setPhase(int)"})
  void testSetPhase() {
    // Arrange
    ApplicationDeployedEventProducer applicationDeployedEventProducer =
        new ApplicationDeployedEventProducer(
            repositoryService,
            aPIDeploymentConverter,
            new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Act
    applicationDeployedEventProducer.setPhase(1);

    // Assert
    assertEquals(1, applicationDeployedEventProducer.getPhase());
  }

  /**
   * Test {@link AbstractActivitiSmartLifeCycle#getPhase()}.
   *
   * <p>Method under test: {@link AbstractActivitiSmartLifeCycle#getPhase()}
   */
  @Test
  @DisplayName("Test getPhase()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"int AbstractActivitiSmartLifeCycle.getPhase()"})
  void testGetPhase() {
    // Arrange
    ApplicationDeployedEventProducer applicationDeployedEventProducer =
        new ApplicationDeployedEventProducer(
            repositoryService,
            aPIDeploymentConverter,
            new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Act and Assert
    assertEquals(Integer.MAX_VALUE, applicationDeployedEventProducer.getPhase());
  }

  /**
   * Test {@link AbstractActivitiSmartLifeCycle#stop(Runnable)} with {@code Runnable}.
   *
   * <p>Method under test: {@link AbstractActivitiSmartLifeCycle#stop(Runnable)}
   */
  @Test
  @DisplayName("Test stop(Runnable) with 'Runnable'")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"void AbstractActivitiSmartLifeCycle.stop(Runnable)"})
  void testStopWithRunnable() {
    // Arrange
    ApplicationDeployedEventProducer applicationDeployedEventProducer =
        new ApplicationDeployedEventProducer(
            repositoryService,
            aPIDeploymentConverter,
            new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    Runnable callback = mock(Runnable.class);
    doNothing().when(callback).run();

    // Act
    applicationDeployedEventProducer.stop(callback);

    // Assert
    verify(callback).run();
  }

  /**
   * Test {@link AbstractActivitiSmartLifeCycle#isRunning()}.
   *
   * <p>Method under test: {@link AbstractActivitiSmartLifeCycle#isRunning()}
   */
  @Test
  @DisplayName("Test isRunning()")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({"boolean AbstractActivitiSmartLifeCycle.isRunning()"})
  void testIsRunning() {
    // Arrange
    ApplicationDeployedEventProducer applicationDeployedEventProducer =
        new ApplicationDeployedEventProducer(
            repositoryService,
            aPIDeploymentConverter,
            new ArrayList<>(),
            mock(ApplicationEventPublisher.class));

    // Act and Assert
    assertFalse(applicationDeployedEventProducer.isRunning());
  }
}
