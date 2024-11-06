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
package org.activiti.engine.impl.persistence.entity.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;
import org.junit.Test;

public class IntegrationContextEntityImplDiffblueTest {
  /**
   * Test {@link IntegrationContextEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given {@link IntegrationContextEntityImpl} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_givenIntegrationContextEntityImpl() {
    // Arrange and Act
    Object actualPersistentState = (new IntegrationContextEntityImpl()).getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertTrue(((Map<Object, Object>) actualPersistentState).isEmpty());
  }

  /**
   * Test {@link IntegrationContextEntityImpl#getPersistentState()}.
   * <ul>
   *   <li>Given {@link IntegrationContextEntityImpl} (default constructor)
   * CreatedDate is {@link Date}.</li>
   * </ul>
   * <p>
   * Method under test: {@link IntegrationContextEntityImpl#getPersistentState()}
   */
  @Test
  public void testGetPersistentState_givenIntegrationContextEntityImplCreatedDateIsDate() {
    // Arrange
    IntegrationContextEntityImpl integrationContextEntityImpl = new IntegrationContextEntityImpl();
    integrationContextEntityImpl.setCreatedDate(mock(java.sql.Date.class));

    // Act
    Object actualPersistentState = integrationContextEntityImpl.getPersistentState();

    // Assert
    assertTrue(actualPersistentState instanceof Map);
    assertTrue(((Map<Object, Object>) actualPersistentState).isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of
   * {@link IntegrationContextEntityImpl}
   *   <li>{@link IntegrationContextEntityImpl#setCreatedDate(Date)}
   *   <li>{@link IntegrationContextEntityImpl#setExecutionId(String)}
   *   <li>{@link IntegrationContextEntityImpl#setFlowNodeId(String)}
   *   <li>{@link IntegrationContextEntityImpl#setProcessDefinitionId(String)}
   *   <li>{@link IntegrationContextEntityImpl#setProcessInstanceId(String)}
   *   <li>{@link IntegrationContextEntityImpl#toString()}
   *   <li>{@link IntegrationContextEntityImpl#getCreatedDate()}
   *   <li>{@link IntegrationContextEntityImpl#getExecutionId()}
   *   <li>{@link IntegrationContextEntityImpl#getFlowNodeId()}
   *   <li>{@link IntegrationContextEntityImpl#getProcessDefinitionId()}
   *   <li>{@link IntegrationContextEntityImpl#getProcessInstanceId()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    IntegrationContextEntityImpl actualIntegrationContextEntityImpl = new IntegrationContextEntityImpl();
    Date createdDate = Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant());
    actualIntegrationContextEntityImpl.setCreatedDate(createdDate);
    actualIntegrationContextEntityImpl.setExecutionId("42");
    actualIntegrationContextEntityImpl.setFlowNodeId("42");
    actualIntegrationContextEntityImpl.setProcessDefinitionId("42");
    actualIntegrationContextEntityImpl.setProcessInstanceId("42");
    String actualToStringResult = actualIntegrationContextEntityImpl.toString();
    Date actualCreatedDate = actualIntegrationContextEntityImpl.getCreatedDate();
    String actualExecutionId = actualIntegrationContextEntityImpl.getExecutionId();
    String actualFlowNodeId = actualIntegrationContextEntityImpl.getFlowNodeId();
    String actualProcessDefinitionId = actualIntegrationContextEntityImpl.getProcessDefinitionId();

    // Assert that nothing has changed
    assertEquals("42", actualExecutionId);
    assertEquals("42", actualFlowNodeId);
    assertEquals("42", actualProcessDefinitionId);
    assertEquals("42", actualIntegrationContextEntityImpl.getProcessInstanceId());
    assertEquals("IntegrationContext[ executionId='42', processInstanceId='42', flowNodeId='42' ]",
        actualToStringResult);
    assertEquals(1, actualIntegrationContextEntityImpl.getRevision());
    assertFalse(actualIntegrationContextEntityImpl.isDeleted());
    assertFalse(actualIntegrationContextEntityImpl.isInserted());
    assertFalse(actualIntegrationContextEntityImpl.isUpdated());
    assertSame(createdDate, actualCreatedDate);
  }
}
