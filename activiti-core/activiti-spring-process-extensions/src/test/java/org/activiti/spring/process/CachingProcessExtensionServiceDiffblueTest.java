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
package org.activiti.spring.process;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.activiti.spring.process.model.Extension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CachingProcessExtensionService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class CachingProcessExtensionServiceDiffblueTest {
  @Autowired
  private CachingProcessExtensionService cachingProcessExtensionService;

  @MockBean
  private ProcessExtensionService processExtensionService;

  /**
   * Test {@link CachingProcessExtensionService#getExtensionsForId(String)}.
   * <p>
   * Method under test:
   * {@link CachingProcessExtensionService#getExtensionsForId(String)}
   */
  @Test
  @DisplayName("Test getExtensionsForId(String)")
  void testGetExtensionsForId() {
    // Arrange
    Extension extension = new Extension();
    when(processExtensionService.getExtensionsForId(Mockito.<String>any())).thenReturn(extension);

    // Act
    Extension actualExtensionsForId = cachingProcessExtensionService.getExtensionsForId("42");

    // Assert
    verify(processExtensionService).getExtensionsForId(eq("42"));
    assertSame(extension, actualExtensionsForId);
  }
}
