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
package org.activiti.spring.boot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.history.HistoryLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProcessDefinitionResourceFinderDescriptor.class, ActivitiProperties.class})
@ExtendWith(SpringExtension.class)
class ProcessDefinitionResourceFinderDescriptorDiffblueTest {
  @Autowired
  private ActivitiProperties activitiProperties;

  @Autowired
  private ProcessDefinitionResourceFinderDescriptor processDefinitionResourceFinderDescriptor;

  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>
   * {@link ProcessDefinitionResourceFinderDescriptor#ProcessDefinitionResourceFinderDescriptor(ActivitiProperties)}
   *   <li>{@link ProcessDefinitionResourceFinderDescriptor#validate(List)}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ActivitiProperties activitiProperties = new ActivitiProperties();
    activitiProperties.setAsyncExecutorActivate(true);
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    activitiProperties.setCustomMybatisMappers(new ArrayList<>());
    activitiProperties.setCustomMybatisXMLMappers(new ArrayList<>());
    activitiProperties.setDatabaseSchema("Database Schema");
    activitiProperties.setDatabaseSchemaUpdate("2020-03-01");
    activitiProperties.setDbHistoryUsed(true);
    activitiProperties.setDeploymentMode("Deployment Mode");
    activitiProperties.setDeploymentName("Deployment Name");
    activitiProperties.setHistoryLevel(HistoryLevel.NONE);
    activitiProperties.setJavaClassFieldForJackson("Java Class Field For Jackson");
    activitiProperties.setMailServerDefaultFrom("jane.doe@example.org");
    activitiProperties.setMailServerHost("localhost");
    activitiProperties.setMailServerPassword("iloveyou");
    activitiProperties.setMailServerPort(8080);
    activitiProperties.setMailServerUseSsl(true);
    activitiProperties.setMailServerUseTls(true);
    activitiProperties.setMailServerUserName("janedoe");
    activitiProperties.setProcessDefinitionLocationPrefix("Process Definition Location Prefix");
    ArrayList<String> processDefinitionLocationSuffixes = new ArrayList<>();
    activitiProperties.setProcessDefinitionLocationSuffixes(processDefinitionLocationSuffixes);
    activitiProperties.setSerializePOJOsInVariablesToJson(true);
    activitiProperties.setUseStrongUuids(true);

    // Act
    ProcessDefinitionResourceFinderDescriptor actualProcessDefinitionResourceFinderDescriptor = new ProcessDefinitionResourceFinderDescriptor(
        activitiProperties);
    actualProcessDefinitionResourceFinderDescriptor.validate(new ArrayList<>());

    // Assert that nothing has changed
    assertEquals("No process definitions were found for auto-deployment in the location `Process Definition Location"
        + " Prefix`", actualProcessDefinitionResourceFinderDescriptor.getMsgForEmptyResources());
    assertEquals("Process Definition Location Prefix",
        actualProcessDefinitionResourceFinderDescriptor.getLocationPrefix());
    List<String> locationSuffixes = actualProcessDefinitionResourceFinderDescriptor.getLocationSuffixes();
    assertTrue(locationSuffixes.isEmpty());
    assertSame(processDefinitionLocationSuffixes, locationSuffixes);
  }

  /**
   * Test {@link ProcessDefinitionResourceFinderDescriptor#getLocationSuffixes()}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionResourceFinderDescriptor#getLocationSuffixes()}
   */
  @Test
  @DisplayName("Test getLocationSuffixes()")
  void testGetLocationSuffixes() {
    // Arrange and Act
    List<String> actualLocationSuffixes = processDefinitionResourceFinderDescriptor.getLocationSuffixes();

    // Assert
    assertEquals(2, actualLocationSuffixes.size());
    assertEquals("**.bpmn", actualLocationSuffixes.get(1));
    assertEquals("**.bpmn20.xml", actualLocationSuffixes.get(0));
  }

  /**
   * Test {@link ProcessDefinitionResourceFinderDescriptor#getLocationPrefix()}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionResourceFinderDescriptor#getLocationPrefix()}
   */
  @Test
  @DisplayName("Test getLocationPrefix()")
  void testGetLocationPrefix() {
    // Arrange, Act and Assert
    assertEquals("classpath*:**/processes/", processDefinitionResourceFinderDescriptor.getLocationPrefix());
  }

  /**
   * Test
   * {@link ProcessDefinitionResourceFinderDescriptor#shouldLookUpResources()}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionResourceFinderDescriptor#shouldLookUpResources()}
   */
  @Test
  @DisplayName("Test shouldLookUpResources()")
  void testShouldLookUpResources() {
    // Arrange, Act and Assert
    assertTrue(processDefinitionResourceFinderDescriptor.shouldLookUpResources());
  }

  /**
   * Test
   * {@link ProcessDefinitionResourceFinderDescriptor#getMsgForEmptyResources()}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionResourceFinderDescriptor#getMsgForEmptyResources()}
   */
  @Test
  @DisplayName("Test getMsgForEmptyResources()")
  void testGetMsgForEmptyResources() {
    // Arrange, Act and Assert
    assertEquals("No process definitions were found for auto-deployment in the location `classpath*:**/processes/`",
        processDefinitionResourceFinderDescriptor.getMsgForEmptyResources());
  }

  /**
   * Test
   * {@link ProcessDefinitionResourceFinderDescriptor#getMsgForResourcesFound(List)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionResourceFinderDescriptor#getMsgForResourcesFound(List)}
   */
  @Test
  @DisplayName("Test getMsgForResourcesFound(List)")
  void testGetMsgForResourcesFound() {
    // Arrange, Act and Assert
    assertEquals("The following process definition files will be deployed: []",
        processDefinitionResourceFinderDescriptor.getMsgForResourcesFound(new ArrayList<>()));
  }

  /**
   * Test
   * {@link ProcessDefinitionResourceFinderDescriptor#getMsgForResourcesFound(List)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionResourceFinderDescriptor#getMsgForResourcesFound(List)}
   */
  @Test
  @DisplayName("Test getMsgForResourcesFound(List)")
  void testGetMsgForResourcesFound2() {
    // Arrange
    ArrayList<String> foundProcessResources = new ArrayList<>();
    foundProcessResources.add("foo");

    // Act and Assert
    assertEquals("The following process definition files will be deployed: [foo]",
        processDefinitionResourceFinderDescriptor.getMsgForResourcesFound(foundProcessResources));
  }

  /**
   * Test
   * {@link ProcessDefinitionResourceFinderDescriptor#getMsgForResourcesFound(List)}.
   * <p>
   * Method under test:
   * {@link ProcessDefinitionResourceFinderDescriptor#getMsgForResourcesFound(List)}
   */
  @Test
  @DisplayName("Test getMsgForResourcesFound(List)")
  void testGetMsgForResourcesFound3() {
    // Arrange
    ArrayList<String> foundProcessResources = new ArrayList<>();
    foundProcessResources.add("42");
    foundProcessResources.add("foo");

    // Act and Assert
    assertEquals("The following process definition files will be deployed: [42, foo]",
        processDefinitionResourceFinderDescriptor.getMsgForResourcesFound(foundProcessResources));
  }
}
