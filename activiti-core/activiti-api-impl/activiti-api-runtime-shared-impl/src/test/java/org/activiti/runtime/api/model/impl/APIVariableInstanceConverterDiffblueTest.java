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
package org.activiti.runtime.api.model.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.runtime.model.impl.VariableInstanceImpl;
import org.activiti.engine.impl.persistence.entity.VariableInstanceEntityImpl;
import org.activiti.engine.impl.variable.BigDecimalType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {APIVariableInstanceConverter.class})
@ExtendWith(SpringExtension.class)
class APIVariableInstanceConverterDiffblueTest {
  @Autowired private APIVariableInstanceConverter aPIVariableInstanceConverter;

  /**
   * Test {@link APIVariableInstanceConverter#from(VariableInstance)} with {@code VariableInstance}.
   *
   * <ul>
   *   <li>Given {@link BigDecimalType} (default constructor).
   *   <li>Then return {@link VariableInstanceImpl}.
   * </ul>
   *
   * <p>Method under test: {@link
   * APIVariableInstanceConverter#from(org.activiti.engine.impl.persistence.entity.VariableInstance)}
   */
  @Test
  @DisplayName(
      "Test from(VariableInstance) with 'VariableInstance'; given BigDecimalType (default constructor); then return VariableInstanceImpl")
  @Tag("ContributionFromDiffblue")
  @ManagedByDiffblue
  @MethodsUnderTest({
    "VariableInstance APIVariableInstanceConverter.from(org.activiti.engine.impl.persistence.entity.VariableInstance)"
  })
  void testFromWithVariableInstance_givenBigDecimalType_thenReturnVariableInstanceImpl() {
    // Arrange
    VariableInstanceEntityImpl internalVariableInstance = new VariableInstanceEntityImpl();
    internalVariableInstance.setType(new BigDecimalType());

    // Act
    VariableInstance actualFromResult = aPIVariableInstanceConverter.from(internalVariableInstance);

    // Assert
    assertTrue(actualFromResult instanceof VariableInstanceImpl);
    assertEquals("bigdecimal", actualFromResult.getType());
    assertNull(actualFromResult.getValue());
    assertNull(actualFromResult.getName());
    assertNull(actualFromResult.getProcessInstanceId());
    assertNull(actualFromResult.getTaskId());
    assertFalse(actualFromResult.isTaskVariable());
  }
}
