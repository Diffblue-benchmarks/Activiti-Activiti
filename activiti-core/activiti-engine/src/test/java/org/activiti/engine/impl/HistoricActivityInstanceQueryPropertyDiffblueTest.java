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
public class HistoricActivityInstanceQueryPropertyDiffblueTest {
  @InjectMocks
  private HistoricActivityInstanceQueryProperty historicActivityInstanceQueryProperty;

  @InjectMocks
  private String string;

  /**
   * Test
   * {@link HistoricActivityInstanceQueryProperty#HistoricActivityInstanceQueryProperty(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryProperty#HistoricActivityInstanceQueryProperty(String)}
   */
  @Test
  public void testNewHistoricActivityInstanceQueryProperty() {
    // Arrange, Act and Assert
    assertEquals("Name", (new HistoricActivityInstanceQueryProperty("Name")).getName());
  }

  /**
   * Test {@link HistoricActivityInstanceQueryProperty#getName()}.
   * <p>
   * Method under test: {@link HistoricActivityInstanceQueryProperty#getName()}
   */
  @Test
  public void testGetName() {
    // Arrange, Act and Assert
    assertEquals("Name", (new HistoricActivityInstanceQueryProperty("Name")).getName());
  }

  /**
   * Test {@link HistoricActivityInstanceQueryProperty#findByName(String)}.
   * <p>
   * Method under test:
   * {@link HistoricActivityInstanceQueryProperty#findByName(String)}
   */
  @Test
  public void testFindByName() {
    // Arrange, Act and Assert
    assertNull(HistoricActivityInstanceQueryProperty.findByName("Property Name"));
  }
}
