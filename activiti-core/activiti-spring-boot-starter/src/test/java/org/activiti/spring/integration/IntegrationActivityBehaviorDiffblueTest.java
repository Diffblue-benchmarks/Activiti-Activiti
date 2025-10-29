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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {IntegrationActivityBehavior.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class IntegrationActivityBehaviorDiffblueTest {
  @MockBean
  private ActivitiInboundGateway activitiInboundGateway;

  @Autowired
  private IntegrationActivityBehavior integrationActivityBehavior;

  /**
   * Method under test:
   * {@link IntegrationActivityBehavior#execute(DelegateExecution)}
   */
  @Test
  void testExecute() {
    // Arrange
    ActivitiInboundGateway gateway = mock(ActivitiInboundGateway.class);
    doNothing().when(gateway).execute(Mockito.<IntegrationActivityBehavior>any(), Mockito.<DelegateExecution>any());
    IntegrationActivityBehavior inboundGatewayActivityBehaviorResult = Activiti.inboundGatewayActivityBehavior(gateway);

    // Act
    inboundGatewayActivityBehaviorResult.execute(ExecutionEntityImpl.createWithEmptyRelationshipCollections());

    // Assert that nothing has changed
    verify(gateway).execute(isA(IntegrationActivityBehavior.class), isA(DelegateExecution.class));
  }

  /**
   * Method under test:
   * {@link IntegrationActivityBehavior#IntegrationActivityBehavior(ActivitiInboundGateway)}
   */
  @Test
  void testNewIntegrationActivityBehavior() {
    // Arrange, Act and Assert
    assertNull((new IntegrationActivityBehavior(activitiInboundGateway)).getMultiInstanceActivityBehavior());
  }

  /**
   * Method under test:
   * {@link IntegrationActivityBehavior#trigger(DelegateExecution, String, Object)}
   */
  @Test
  void testTrigger() {
    // Arrange
    ActivitiInboundGateway gateway = mock(ActivitiInboundGateway.class);
    doNothing().when(gateway)
        .signal(Mockito.<IntegrationActivityBehavior>any(), Mockito.<DelegateExecution>any(), Mockito.<String>any(),
            Mockito.<Object>any());
    IntegrationActivityBehavior inboundGatewayActivityBehaviorResult = Activiti.inboundGatewayActivityBehavior(gateway);

    // Act
    inboundGatewayActivityBehaviorResult.trigger(ExecutionEntityImpl.createWithEmptyRelationshipCollections(),
        "Signal Name", "Data");

    // Assert that nothing has changed
    verify(gateway).signal(isA(IntegrationActivityBehavior.class), isA(DelegateExecution.class), eq("Signal Name"),
        isA(Object.class));
  }
}
