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
package org.activiti.spring.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.engine.ProcessEngine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.integration.IntegrationPatternType;

class ActivitiDiffblueTest {
  /**
   * Test {@link Activiti#inboundGateway(ProcessEngine, String[])}.
   * <ul>
   *   <li>When {@code null}.</li>
   *   <li>Then return ComponentType is {@code gateway}.</li>
   * </ul>
   * <p>
   * Method under test: {@link Activiti#inboundGateway(ProcessEngine, String[])}
   */
  @Test
  @DisplayName("Test inboundGateway(ProcessEngine, String[]); when 'null'; then return ComponentType is 'gateway'")
  @Tag("MaintainedByDiffblue")
  @MethodsUnderTest({"ActivitiInboundGateway Activiti.inboundGateway(ProcessEngine, String[])"})
  void testInboundGateway_whenNull_thenReturnComponentTypeIsGateway() {
    // Arrange and Act
    ActivitiInboundGateway actualInboundGatewayResult = Activiti.inboundGateway(null, "Vars To Preserve");

    // Assert
    assertEquals("gateway", actualInboundGatewayResult.getComponentType());
    assertNull(actualInboundGatewayResult.getApplicationContextId());
    assertNull(actualInboundGatewayResult.getBeanName());
    assertNull(actualInboundGatewayResult.getComponentName());
    assertNull(actualInboundGatewayResult.getRole());
    assertNull(actualInboundGatewayResult.getManagedName());
    assertNull(actualInboundGatewayResult.getManagedType());
    assertNull(actualInboundGatewayResult.getConversionService());
    assertNull(actualInboundGatewayResult.getExpression());
    assertNull(actualInboundGatewayResult.getErrorChannel());
    assertNull(actualInboundGatewayResult.getReplyChannel());
    assertNull(actualInboundGatewayResult.getRequestChannel());
    assertEquals(0, actualInboundGatewayResult.getPhase());
    assertEquals(IntegrationPatternType.inbound_gateway, actualInboundGatewayResult.getIntegrationPatternType());
    assertFalse(actualInboundGatewayResult.isActive());
    assertFalse(actualInboundGatewayResult.isRunning());
    assertFalse(actualInboundGatewayResult.isObserved());
    assertTrue(actualInboundGatewayResult.isAutoStartup());
    assertTrue(actualInboundGatewayResult.isLoggingEnabled());
  }
}
