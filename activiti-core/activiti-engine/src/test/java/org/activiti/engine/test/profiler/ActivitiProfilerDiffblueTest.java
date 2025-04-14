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
package org.activiti.engine.test.profiler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import com.diffblue.cover.annotations.MaintainedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ActivitiProfilerDiffblueTest {
  /**
   * Test {@link ActivitiProfiler#beforeInit(ProcessEngineConfigurationImpl)}.
   * <p>
   * Method under test: {@link ActivitiProfiler#beforeInit(ProcessEngineConfigurationImpl)}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ActivitiProfiler.beforeInit(ProcessEngineConfigurationImpl)"})
  public void testBeforeInit() {
    // Arrange
    ActivitiProfiler instance = ActivitiProfiler.getInstance();
    JtaProcessEngineConfiguration processEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    instance.beforeInit(processEngineConfiguration);

    // Assert
    assertTrue(processEngineConfiguration.getDbSqlSessionFactory() instanceof ProfilingDbSqlSessionFactory);
    assertEquals(1, processEngineConfiguration.getCustomPreCommandInterceptors().size());
  }

  /**
   * Test {@link ActivitiProfiler#reset()}.
   * <ul>
   *   <li>Given Instance.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiProfiler#reset()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ActivitiProfiler.reset()"})
  public void testReset_givenInstance() {
    // Arrange
    ActivitiProfiler instance = ActivitiProfiler.getInstance();

    // Act
    instance.reset();

    // Assert that nothing has changed
    assertTrue(instance.getProfileSessions().isEmpty());
  }

  /**
   * Test {@link ActivitiProfiler#reset()}.
   * <ul>
   *   <li>Then Instance CurrentProfileSession is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiProfiler#reset()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ActivitiProfiler.reset()"})
  public void testReset_thenInstanceCurrentProfileSessionIsNull() {
    // Arrange
    ActivitiProfiler instance = ActivitiProfiler.getInstance();
    instance.startProfileSession(null);

    // Act
    instance.reset();

    // Assert
    assertNull(instance.getCurrentProfileSession());
    assertTrue(instance.getProfileSessions().isEmpty());
  }

  /**
   * Test {@link ActivitiProfiler#stopCurrentProfileSession()}.
   * <ul>
   *   <li>Then Instance CurrentProfileSession is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ActivitiProfiler#stopCurrentProfileSession()}
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ActivitiProfiler.stopCurrentProfileSession()"})
  public void testStopCurrentProfileSession_thenInstanceCurrentProfileSessionIsNull() {
    // Arrange
    ActivitiProfiler instance = ActivitiProfiler.getInstance();
    instance.startProfileSession("Name");

    // Act
    instance.stopCurrentProfileSession();

    // Assert
    assertNull(instance.getCurrentProfileSession());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link ActivitiProfiler}
   *   <li>{@link ActivitiProfiler#setCurrentProfileSession(ProfileSession)}
   *   <li>{@link ActivitiProfiler#setProfileSessions(List)}
   *   <li>{@link ActivitiProfiler#configure(ProcessEngineConfigurationImpl)}
   *   <li>{@link ActivitiProfiler#getInstance()}
   *   <li>{@link ActivitiProfiler#getCurrentProfileSession()}
   *   <li>{@link ActivitiProfiler#getPriority()}
   *   <li>{@link ActivitiProfiler#getProfileSessions()}
   * </ul>
   */
  @Test
  @Category(MaintainedByDiffblue.class)
  @MethodsUnderTest({"void ActivitiProfiler.<init>()",
      "void ActivitiProfiler.configure(ProcessEngineConfigurationImpl)",
      "ProfileSession ActivitiProfiler.getCurrentProfileSession()", "ActivitiProfiler ActivitiProfiler.getInstance()",
      "int ActivitiProfiler.getPriority()", "List ActivitiProfiler.getProfileSessions()",
      "void ActivitiProfiler.setCurrentProfileSession(ProfileSession)",
      "void ActivitiProfiler.setProfileSessions(List)"})
  public void testGettersAndSetters() {
    // Arrange and Act
    ActivitiProfiler actualActivitiProfiler = new ActivitiProfiler();
    ProfileSession currentProfileSession = new ProfileSession("Name");
    actualActivitiProfiler.setCurrentProfileSession(currentProfileSession);
    ArrayList<ProfileSession> profileSessions = new ArrayList<>();
    actualActivitiProfiler.setProfileSessions(profileSessions);
    actualActivitiProfiler.configure(new JtaProcessEngineConfiguration());
    ActivitiProfiler actualInstance = actualActivitiProfiler.getInstance();
    ProfileSession actualCurrentProfileSession = actualActivitiProfiler.getCurrentProfileSession();
    int actualPriority = actualActivitiProfiler.getPriority();
    List<ProfileSession> actualProfileSessions = actualActivitiProfiler.getProfileSessions();

    // Assert
    assertEquals(0, actualPriority);
    assertTrue(actualProfileSessions.isEmpty());
    assertSame(profileSessions, actualProfileSessions);
    assertSame(currentProfileSession, actualCurrentProfileSession);
    assertSame(actualInstance.INSTANCE, actualInstance);
  }
}
