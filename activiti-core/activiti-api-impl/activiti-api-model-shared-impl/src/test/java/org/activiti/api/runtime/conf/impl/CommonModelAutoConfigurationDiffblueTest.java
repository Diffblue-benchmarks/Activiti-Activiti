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
package org.activiti.api.runtime.conf.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonModelAutoConfigurationDiffblueTest {
  /**
   * Test {@link CommonModelAutoConfiguration#customizeCommonModelObjectMapper()}.
   * <p>
   * Method under test:
   * {@link CommonModelAutoConfiguration#customizeCommonModelObjectMapper()}
   */
  @Test
  @DisplayName("Test customizeCommonModelObjectMapper()")
  void testCustomizeCommonModelObjectMapper() {
    // Arrange and Act
    Module actualCustomizeCommonModelObjectMapperResult = (new CommonModelAutoConfiguration())
        .customizeCommonModelObjectMapper();

    // Assert
    assertTrue(actualCustomizeCommonModelObjectMapperResult instanceof SimpleModule);
    Iterable<? extends Module> dependencies = actualCustomizeCommonModelObjectMapperResult.getDependencies();
    assertTrue(dependencies instanceof List);
    Version versionResult = actualCustomizeCommonModelObjectMapperResult.version();
    assertEquals("", versionResult.getArtifactId());
    assertEquals("", versionResult.getGroupId());
    assertEquals("//0.0.0", versionResult.toFullString());
    assertEquals("mapCommonModelInterfaces", actualCustomizeCommonModelObjectMapperResult.getModuleName());
    assertEquals("mapCommonModelInterfaces", actualCustomizeCommonModelObjectMapperResult.getTypeId());
    assertEquals(0, versionResult.getMajorVersion());
    assertEquals(0, versionResult.getMinorVersion());
    assertEquals(0, versionResult.getPatchLevel());
    assertFalse(versionResult.isSnapshot());
    assertTrue(versionResult.isUknownVersion());
    assertTrue(versionResult.isUnknownVersion());
    assertTrue(((List<? extends Module>) dependencies).isEmpty());
  }
}
