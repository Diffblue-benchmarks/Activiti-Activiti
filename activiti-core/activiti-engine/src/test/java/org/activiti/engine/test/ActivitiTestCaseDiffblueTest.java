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
import org.activiti.standalone.testing.ActivitiTestCaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ActivitiTestCaseDiffblueTest {
  @InjectMocks
  private ActivitiTestCaseTest activitiTestCaseTest;

  /**
   * Test {@link ActivitiTestCase#getConfigurationResource()}.
   * <p>
   * Method under test: {@link ActivitiTestCase#getConfigurationResource()}
   */
  @Test
  public void testGetConfigurationResource() {
    // Arrange, Act and Assert
    assertEquals("activiti.cfg.xml", (new ActivitiTestCaseTest()).getConfigurationResource());
  }

  /**
   * Test {@link ActivitiTestCase#setConfigurationResource(String)}.
   * <p>
   * Method under test: {@link ActivitiTestCase#setConfigurationResource(String)}
   */
  @Test
  public void testSetConfigurationResource() {
    // Arrange and Act
    activitiTestCaseTest.setConfigurationResource("Configuration Resource");

    // Assert
    assertEquals("Configuration Resource", activitiTestCaseTest.getConfigurationResource());
  }

  /**
   * Test {@link ActivitiTestCase#getMockSupport()}.
   * <p>
   * Method under test: {@link ActivitiTestCase#getMockSupport()}
   */
  @Test
  public void testGetMockSupport() {
    // Arrange, Act and Assert
    assertNull((new ActivitiTestCaseTest()).getMockSupport());
  }

  /**
   * Test {@link ActivitiTestCase#mockSupport()}.
   * <p>
   * Method under test: {@link ActivitiTestCase#mockSupport()}
   */
  @Test
  public void testMockSupport() {
    // Arrange, Act and Assert
    assertNull((new ActivitiTestCaseTest()).mockSupport());
  }
}
