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
package org.activiti.engine.impl.cmd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Map;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.impl.persistence.entity.ModelEntityImpl;
import org.junit.Test;

public class SaveModelCmdDiffblueTest {
  /**
   * Test {@link SaveModelCmd#SaveModelCmd(ModelEntity)}.
   * <p>
   * Method under test: {@link SaveModelCmd#SaveModelCmd(ModelEntity)}
   */
  @Test
  public void testNewSaveModelCmd() {
    // Arrange, Act and Assert
    ModelEntity modelEntity = (new SaveModelCmd(new ModelEntityImpl())).model;
    Object persistentState = modelEntity.getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(modelEntity instanceof ModelEntityImpl);
    assertEquals("", modelEntity.getTenantId());
    assertEquals(10, ((Map<String, Integer>) persistentState).size());
    assertNull(((Map<String, Integer>) persistentState).get("createTime"));
    assertNull(((Map<String, Integer>) persistentState).get("deploymentId"));
    assertNull(((Map<String, Integer>) persistentState).get("editorSourceValueId"));
    assertNull(((Map<String, Integer>) persistentState).get("metaInfo"));
    assertNull(modelEntity.getId());
    assertNull(modelEntity.getEditorSourceExtraValueId());
    assertNull(modelEntity.getEditorSourceValueId());
    assertNull(modelEntity.getCategory());
    assertNull(modelEntity.getDeploymentId());
    assertNull(modelEntity.getKey());
    assertNull(modelEntity.getMetaInfo());
    assertNull(modelEntity.getName());
    assertNull(modelEntity.getCreateTime());
    assertNull(modelEntity.getLastUpdateTime());
    assertEquals(1, modelEntity.getVersion().intValue());
    assertEquals(1, modelEntity.getRevision());
    assertEquals(2, modelEntity.getRevisionNext());
    assertFalse(modelEntity.isDeleted());
    assertFalse(modelEntity.isInserted());
    assertFalse(modelEntity.isUpdated());
    assertFalse(modelEntity.hasEditorSource());
    assertFalse(modelEntity.hasEditorSourceExtra());
  }
}
