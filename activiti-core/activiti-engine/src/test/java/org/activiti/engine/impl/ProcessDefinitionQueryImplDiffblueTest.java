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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class ProcessDefinitionQueryImplDiffblueTest {
  /**
   * Test {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionQueryImpl#ProcessDefinitionQueryImpl()}
   */
  @Test
  public void testNewProcessDefinitionQueryImpl() {
    // Arrange and Act
    ProcessDefinitionQueryImpl actualProcessDefinitionQueryImpl = new ProcessDefinitionQueryImpl();

    // Assert
    assertEquals("RES.ID_ asc", actualProcessDefinitionQueryImpl.getOrderBy());
    assertEquals("RES.ID_ asc", actualProcessDefinitionQueryImpl.getOrderByColumns());
    assertNull(actualProcessDefinitionQueryImpl.getVersion());
    assertNull(actualProcessDefinitionQueryImpl.getVersionGt());
    assertNull(actualProcessDefinitionQueryImpl.getVersionGte());
    assertNull(actualProcessDefinitionQueryImpl.getVersionLt());
    assertNull(actualProcessDefinitionQueryImpl.getVersionLte());
    assertNull(actualProcessDefinitionQueryImpl.getDatabaseType());
    assertNull(actualProcessDefinitionQueryImpl.getAuthorizationUserId());
    assertNull(actualProcessDefinitionQueryImpl.getCategory());
    assertNull(actualProcessDefinitionQueryImpl.getCategoryLike());
    assertNull(actualProcessDefinitionQueryImpl.getCategoryNotEquals());
    assertNull(actualProcessDefinitionQueryImpl.getDeploymentId());
    assertNull(actualProcessDefinitionQueryImpl.getEventSubscriptionName());
    assertNull(actualProcessDefinitionQueryImpl.getEventSubscriptionType());
    assertNull(actualProcessDefinitionQueryImpl.getId());
    assertNull(actualProcessDefinitionQueryImpl.getIdOrKey());
    assertNull(actualProcessDefinitionQueryImpl.getKey());
    assertNull(actualProcessDefinitionQueryImpl.getKeyLike());
    assertNull(actualProcessDefinitionQueryImpl.getName());
    assertNull(actualProcessDefinitionQueryImpl.getNameLike());
    assertNull(actualProcessDefinitionQueryImpl.getProcDefId());
    assertNull(actualProcessDefinitionQueryImpl.getResourceName());
    assertNull(actualProcessDefinitionQueryImpl.getResourceNameLike());
    assertNull(actualProcessDefinitionQueryImpl.getTenantId());
    assertNull(actualProcessDefinitionQueryImpl.getTenantIdLike());
    assertNull(actualProcessDefinitionQueryImpl.orderBy);
    assertNull(actualProcessDefinitionQueryImpl.getAuthorizationGroups());
    assertNull(actualProcessDefinitionQueryImpl.getDeploymentIds());
    assertNull(actualProcessDefinitionQueryImpl.getIds());
    assertNull(actualProcessDefinitionQueryImpl.getKeys());
    assertNull(actualProcessDefinitionQueryImpl.nullHandlingOnOrder);
    assertNull(actualProcessDefinitionQueryImpl.resultType);
    assertNull(actualProcessDefinitionQueryImpl.commandContext);
    assertNull(actualProcessDefinitionQueryImpl.commandExecutor);
    assertNull(actualProcessDefinitionQueryImpl.getSuspensionState());
    assertNull(actualProcessDefinitionQueryImpl.orderProperty);
    assertEquals(0, actualProcessDefinitionQueryImpl.getFirstResult());
    assertEquals(1, actualProcessDefinitionQueryImpl.getFirstRow());
    assertFalse(actualProcessDefinitionQueryImpl.isLatest());
    assertFalse(actualProcessDefinitionQueryImpl.isWithoutTenantId());
    assertEquals(Integer.MAX_VALUE, actualProcessDefinitionQueryImpl.getLastRow());
    assertEquals(Integer.MAX_VALUE, actualProcessDefinitionQueryImpl.getMaxResults());
    assertSame(actualProcessDefinitionQueryImpl, actualProcessDefinitionQueryImpl.getParameter());
  }
}
