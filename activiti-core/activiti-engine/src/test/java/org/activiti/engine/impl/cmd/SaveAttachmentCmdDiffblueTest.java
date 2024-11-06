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
import org.activiti.engine.impl.persistence.entity.AttachmentEntityImpl;
import org.activiti.engine.task.Attachment;
import org.junit.Test;

public class SaveAttachmentCmdDiffblueTest {
  /**
   * Test {@link SaveAttachmentCmd#SaveAttachmentCmd(Attachment)}.
   * <p>
   * Method under test: {@link SaveAttachmentCmd#SaveAttachmentCmd(Attachment)}
   */
  @Test
  public void testNewSaveAttachmentCmd() {
    // Arrange, Act and Assert
    Attachment attachment = (new SaveAttachmentCmd(new AttachmentEntityImpl())).attachment;
    Object persistentState = ((AttachmentEntityImpl) attachment).getPersistentState();
    assertTrue(persistentState instanceof Map);
    assertTrue(attachment instanceof AttachmentEntityImpl);
    assertEquals(2, ((Map<String, Object>) persistentState).size());
    assertNull(((Map<String, Object>) persistentState).get("description"));
    assertNull(((Map<String, Object>) persistentState).get("name"));
    assertNull(attachment.getContentId());
    assertNull(attachment.getDescription());
    assertNull(attachment.getId());
    assertNull(attachment.getName());
    assertNull(attachment.getProcessInstanceId());
    assertNull(attachment.getTaskId());
    assertNull(attachment.getType());
    assertNull(attachment.getUrl());
    assertNull(attachment.getUserId());
    assertNull(attachment.getTime());
    assertNull(((AttachmentEntityImpl) attachment).getContent());
    assertEquals(1, ((AttachmentEntityImpl) attachment).getRevision());
    assertEquals(2, ((AttachmentEntityImpl) attachment).getRevisionNext());
    assertFalse(((AttachmentEntityImpl) attachment).isDeleted());
    assertFalse(((AttachmentEntityImpl) attachment).isInserted());
    assertFalse(((AttachmentEntityImpl) attachment).isUpdated());
  }
}
