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
package org.activiti.bpmn.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;
import org.junit.Test;

public class DataStoreReferenceDiffblueTest {
  /**
   * Test {@link DataStoreReference#clone()}.
   * <ul>
   *   <li>Given {@link DataStoreReference} (default constructor).</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataStoreReference#clone()}
   */
  @Test
  public void testClone_givenDataStoreReference_thenReturnIdIsNull() {
    // Arrange and Act
    DataStoreReference actualCloneResult = (new DataStoreReference()).clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDataState());
    assertNull(actualCloneResult.getDataStoreRef());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test {@link DataStoreReference#clone()}.
   * <ul>
   *   <li>Given {@link HashMap#HashMap()} computeIfPresent {@code foo} and
   * {@link BiFunction}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test: {@link DataStoreReference#clone()}
   */
  @Test
  public void testClone_givenHashMapComputeIfPresentFooAndBiFunction_thenReturnIdIsNull() {
    // Arrange
    HashMap<String, List<ExtensionAttribute>> attributes = new HashMap<>();
    attributes.computeIfPresent("foo", mock(BiFunction.class));

    DataStoreReference dataStoreReference = new DataStoreReference();
    dataStoreReference.setExtensionElements(null);
    dataStoreReference.setAttributes(attributes);

    // Act
    DataStoreReference actualCloneResult = dataStoreReference.clone();

    // Assert
    assertNull(actualCloneResult.getId());
    assertNull(actualCloneResult.getDataState());
    assertNull(actualCloneResult.getDataStoreRef());
    assertNull(actualCloneResult.getItemSubjectRef());
    assertNull(actualCloneResult.getDocumentation());
    assertNull(actualCloneResult.getName());
    assertNull(actualCloneResult.getParentContainer());
    assertNull(actualCloneResult.getSubProcess());
    assertEquals(0, actualCloneResult.getXmlColumnNumber());
    assertEquals(0, actualCloneResult.getXmlRowNumber());
    assertTrue(actualCloneResult.getExecutionListeners().isEmpty());
    assertTrue(actualCloneResult.getAttributes().isEmpty());
    assertTrue(actualCloneResult.getExtensionElements().isEmpty());
  }

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>default or parameterless constructor of {@link DataStoreReference}
   *   <li>{@link DataStoreReference#setDataState(String)}
   *   <li>{@link DataStoreReference#setDataStoreRef(String)}
   *   <li>{@link DataStoreReference#setItemSubjectRef(String)}
   *   <li>{@link DataStoreReference#getDataState()}
   *   <li>{@link DataStoreReference#getDataStoreRef()}
   *   <li>{@link DataStoreReference#getItemSubjectRef()}
   * </ul>
   */
  @Test
  public void testGettersAndSetters() {
    // Arrange and Act
    DataStoreReference actualDataStoreReference = new DataStoreReference();
    actualDataStoreReference.setDataState("Data State");
    actualDataStoreReference.setDataStoreRef("Data Store Ref");
    actualDataStoreReference.setItemSubjectRef("Hello from the Dreaming Spires");
    String actualDataState = actualDataStoreReference.getDataState();
    String actualDataStoreRef = actualDataStoreReference.getDataStoreRef();

    // Assert that nothing has changed
    assertEquals("Data State", actualDataState);
    assertEquals("Data Store Ref", actualDataStoreRef);
    assertEquals("Hello from the Dreaming Spires", actualDataStoreReference.getItemSubjectRef());
    assertEquals(0, actualDataStoreReference.getXmlColumnNumber());
    assertEquals(0, actualDataStoreReference.getXmlRowNumber());
    assertTrue(actualDataStoreReference.getExecutionListeners().isEmpty());
    assertTrue(actualDataStoreReference.getAttributes().isEmpty());
    assertTrue(actualDataStoreReference.getExtensionElements().isEmpty());
  }
}
