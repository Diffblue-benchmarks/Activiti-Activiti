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
package org.activiti.engine.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.standalone.testing.ActivitiTestCaseTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ActivitiTestCaseDiffblueTest {
  /**
   * Test {@link ActivitiTestCase#getConfigurationResource()}.
   *
   * <p>Method under test: {@link ActivitiTestCase#getConfigurationResource()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"String ActivitiTestCase.getConfigurationResource()"})
  public void testGetConfigurationResource() {
    // Arrange, Act and Assert
    assertEquals("activiti.cfg.xml", new ActivitiTestCaseTest().getConfigurationResource());
  }

  /**
   * Test {@link ActivitiTestCase#setConfigurationResource(String)}.
   *
   * <p>Method under test: {@link ActivitiTestCase#setConfigurationResource(String)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({"void ActivitiTestCase.setConfigurationResource(String)"})
  public void testSetConfigurationResource() {
    // Arrange
    ActivitiTestCaseTest activitiTestCaseTest = new ActivitiTestCaseTest();

    // Act
    activitiTestCaseTest.setConfigurationResource("Configuration Resource");

    // Assert
    assertEquals("Configuration Resource", activitiTestCaseTest.getConfigurationResource());
  }

  /**
   * Test {@link ActivitiTestCase#getMockSupport()}.
   *
   * <p>Method under test: {@link ActivitiTestCase#getMockSupport()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.test.mock.ActivitiMockSupport ActivitiTestCase.getMockSupport()"
  })
  public void testGetMockSupport() {
    // Arrange, Act and Assert
    assertNull(new ActivitiTestCaseTest().getMockSupport());
  }

  /**
   * Test {@link ActivitiTestCase#mockSupport()}.
   *
   * <p>Method under test: {@link ActivitiTestCase#mockSupport()}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "org.activiti.engine.test.mock.ActivitiMockSupport ActivitiTestCase.mockSupport()"
  })
  public void testMockSupport() {
    // Arrange, Act and Assert
    assertNull(new ActivitiTestCaseTest().mockSupport());
  }
}
