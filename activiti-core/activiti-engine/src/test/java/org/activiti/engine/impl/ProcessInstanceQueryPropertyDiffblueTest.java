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
package org.activiti.engine.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessInstanceQueryPropertyDiffblueTest {
  @InjectMocks
  private ProcessInstanceQueryProperty processInstanceQueryProperty;

  @InjectMocks
  private String string;

  /**
   * Test
   * {@link ProcessInstanceQueryProperty#ProcessInstanceQueryProperty(String)}.
   * <p>
   * Method under test:
   * {@link ProcessInstanceQueryProperty#ProcessInstanceQueryProperty(String)}
   */
  @Test
  public void testNewProcessInstanceQueryProperty() {
    // Arrange, Act and Assert
    assertEquals("Name", (new ProcessInstanceQueryProperty("Name")).getName());
  }

  /**
   * Test {@link ProcessInstanceQueryProperty#getName()}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryProperty#getName()}
   */
  @Test
  public void testGetName() {
    // Arrange, Act and Assert
    assertEquals("Name", (new ProcessInstanceQueryProperty("Name")).getName());
  }

  /**
   * Test {@link ProcessInstanceQueryProperty#findByName(String)}.
   * <p>
   * Method under test: {@link ProcessInstanceQueryProperty#findByName(String)}
   */
  @Test
  public void testFindByName() {
    // Arrange, Act and Assert
    assertNull(ProcessInstanceQueryProperty.findByName("Property Name"));
  }
}
