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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.impl.history.HistoryLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ActivitiPropertiesDiffblueTest {
  /**
   * Test getters and setters.
   * <p>
   * Methods under test:
   * <ul>
   *   <li>{@link ActivitiProperties#setAsyncExecutorActivate(boolean)}
   *   <li>{@link ActivitiProperties#setCheckProcessDefinitions(boolean)}
   *   <li>{@link ActivitiProperties#setCopyVariablesToLocalForTasks(boolean)}
   *   <li>{@link ActivitiProperties#setCustomMybatisMappers(List)}
   *   <li>{@link ActivitiProperties#setCustomMybatisXMLMappers(List)}
   *   <li>{@link ActivitiProperties#setDatabaseSchema(String)}
   *   <li>{@link ActivitiProperties#setDatabaseSchemaUpdate(String)}
   *   <li>{@link ActivitiProperties#setDbHistoryUsed(boolean)}
   *   <li>{@link ActivitiProperties#setDeploymentMode(String)}
   *   <li>{@link ActivitiProperties#setDeploymentName(String)}
   *   <li>{@link ActivitiProperties#setHistoryLevel(HistoryLevel)}
   *   <li>{@link ActivitiProperties#setJavaClassFieldForJackson(String)}
   *   <li>{@link ActivitiProperties#setMailServerDefaultFrom(String)}
   *   <li>{@link ActivitiProperties#setMailServerHost(String)}
   *   <li>{@link ActivitiProperties#setMailServerPassword(String)}
   *   <li>{@link ActivitiProperties#setMailServerPort(int)}
   *   <li>{@link ActivitiProperties#setMailServerUseSsl(boolean)}
   *   <li>{@link ActivitiProperties#setMailServerUseTls(boolean)}
   *   <li>{@link ActivitiProperties#setMailServerUserName(String)}
   *   <li>{@link ActivitiProperties#setProcessDefinitionLocationPrefix(String)}
   *   <li>{@link ActivitiProperties#setProcessDefinitionLocationSuffixes(List)}
   *   <li>{@link ActivitiProperties#setSerializePOJOsInVariablesToJson(boolean)}
   *   <li>{@link ActivitiProperties#setUseStrongUuids(boolean)}
   *   <li>{@link ActivitiProperties#getCustomMybatisMappers()}
   *   <li>{@link ActivitiProperties#getCustomMybatisXMLMappers()}
   *   <li>{@link ActivitiProperties#getDatabaseSchema()}
   *   <li>{@link ActivitiProperties#getDatabaseSchemaUpdate()}
   *   <li>{@link ActivitiProperties#getDeploymentMode()}
   *   <li>{@link ActivitiProperties#getDeploymentName()}
   *   <li>{@link ActivitiProperties#getHistoryLevel()}
   *   <li>{@link ActivitiProperties#getJavaClassFieldForJackson()}
   *   <li>{@link ActivitiProperties#getMailServerDefaultFrom()}
   *   <li>{@link ActivitiProperties#getMailServerHost()}
   *   <li>{@link ActivitiProperties#getMailServerPassword()}
   *   <li>{@link ActivitiProperties#getMailServerPort()}
   *   <li>{@link ActivitiProperties#getMailServerUserName()}
   *   <li>{@link ActivitiProperties#getProcessDefinitionLocationPrefix()}
   *   <li>{@link ActivitiProperties#getProcessDefinitionLocationSuffixes()}
   *   <li>{@link ActivitiProperties#isAsyncExecutorActivate()}
   *   <li>{@link ActivitiProperties#isCheckProcessDefinitions()}
   *   <li>{@link ActivitiProperties#isCopyVariablesToLocalForTasks()}
   *   <li>{@link ActivitiProperties#isDbHistoryUsed()}
   *   <li>{@link ActivitiProperties#isMailServerUseSsl()}
   *   <li>{@link ActivitiProperties#isMailServerUseTls()}
   *   <li>{@link ActivitiProperties#isSerializePOJOsInVariablesToJson()}
   *   <li>{@link ActivitiProperties#isUseStrongUuids()}
   * </ul>
   */
  @Test
  @DisplayName("Test getters and setters")
  void testGettersAndSetters() {
    // Arrange
    ActivitiProperties activitiProperties = new ActivitiProperties();

    // Act
    activitiProperties.setAsyncExecutorActivate(true);
    activitiProperties.setCheckProcessDefinitions(true);
    activitiProperties.setCopyVariablesToLocalForTasks(true);
    ArrayList<String> customMyBatisMappers = new ArrayList<>();
    activitiProperties.setCustomMybatisMappers(customMyBatisMappers);
    ArrayList<String> customMybatisXMLMappers = new ArrayList<>();
    activitiProperties.setCustomMybatisXMLMappers(customMybatisXMLMappers);
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
    List<String> actualCustomMybatisMappers = activitiProperties.getCustomMybatisMappers();
    List<String> actualCustomMybatisXMLMappers = activitiProperties.getCustomMybatisXMLMappers();
    String actualDatabaseSchema = activitiProperties.getDatabaseSchema();
    String actualDatabaseSchemaUpdate = activitiProperties.getDatabaseSchemaUpdate();
    String actualDeploymentMode = activitiProperties.getDeploymentMode();
    String actualDeploymentName = activitiProperties.getDeploymentName();
    HistoryLevel actualHistoryLevel = activitiProperties.getHistoryLevel();
    String actualJavaClassFieldForJackson = activitiProperties.getJavaClassFieldForJackson();
    String actualMailServerDefaultFrom = activitiProperties.getMailServerDefaultFrom();
    String actualMailServerHost = activitiProperties.getMailServerHost();
    String actualMailServerPassword = activitiProperties.getMailServerPassword();
    int actualMailServerPort = activitiProperties.getMailServerPort();
    String actualMailServerUserName = activitiProperties.getMailServerUserName();
    String actualProcessDefinitionLocationPrefix = activitiProperties.getProcessDefinitionLocationPrefix();
    List<String> actualProcessDefinitionLocationSuffixes = activitiProperties.getProcessDefinitionLocationSuffixes();
    boolean actualIsAsyncExecutorActivateResult = activitiProperties.isAsyncExecutorActivate();
    boolean actualIsCheckProcessDefinitionsResult = activitiProperties.isCheckProcessDefinitions();
    boolean actualIsCopyVariablesToLocalForTasksResult = activitiProperties.isCopyVariablesToLocalForTasks();
    boolean actualIsDbHistoryUsedResult = activitiProperties.isDbHistoryUsed();
    boolean actualIsMailServerUseSslResult = activitiProperties.isMailServerUseSsl();
    boolean actualIsMailServerUseTlsResult = activitiProperties.isMailServerUseTls();
    boolean actualIsSerializePOJOsInVariablesToJsonResult = activitiProperties.isSerializePOJOsInVariablesToJson();
    boolean actualIsUseStrongUuidsResult = activitiProperties.isUseStrongUuids();

    // Assert that nothing has changed
    assertEquals("2020-03-01", actualDatabaseSchemaUpdate);
    assertEquals("Database Schema", actualDatabaseSchema);
    assertEquals("Deployment Mode", actualDeploymentMode);
    assertEquals("Deployment Name", actualDeploymentName);
    assertEquals("Java Class Field For Jackson", actualJavaClassFieldForJackson);
    assertEquals("Process Definition Location Prefix", actualProcessDefinitionLocationPrefix);
    assertEquals("iloveyou", actualMailServerPassword);
    assertEquals("jane.doe@example.org", actualMailServerDefaultFrom);
    assertEquals("janedoe", actualMailServerUserName);
    assertEquals("localhost", actualMailServerHost);
    assertEquals(8080, actualMailServerPort);
    assertEquals(HistoryLevel.NONE, actualHistoryLevel);
    assertTrue(actualCustomMybatisMappers.isEmpty());
    assertTrue(actualCustomMybatisXMLMappers.isEmpty());
    assertTrue(actualProcessDefinitionLocationSuffixes.isEmpty());
    assertTrue(actualIsAsyncExecutorActivateResult);
    assertTrue(actualIsCheckProcessDefinitionsResult);
    assertTrue(actualIsCopyVariablesToLocalForTasksResult);
    assertTrue(actualIsDbHistoryUsedResult);
    assertTrue(actualIsMailServerUseSslResult);
    assertTrue(actualIsMailServerUseTlsResult);
    assertTrue(actualIsSerializePOJOsInVariablesToJsonResult);
    assertTrue(actualIsUseStrongUuidsResult);
    assertSame(customMyBatisMappers, actualCustomMybatisMappers);
    assertSame(customMybatisXMLMappers, actualCustomMybatisXMLMappers);
    assertSame(processDefinitionLocationSuffixes, actualProcessDefinitionLocationSuffixes);
  }

  /**
   * Test new {@link ActivitiProperties} (default constructor).
   * <p>
   * Method under test: default or parameterless constructor of
   * {@link ActivitiProperties}
   */
  @Test
  @DisplayName("Test new ActivitiProperties (default constructor)")
  void testNewActivitiProperties() {
    // Arrange and Act
    ActivitiProperties actualActivitiProperties = new ActivitiProperties();

    // Assert
    List<String> processDefinitionLocationSuffixes = actualActivitiProperties.getProcessDefinitionLocationSuffixes();
    assertEquals(2, processDefinitionLocationSuffixes.size());
    assertEquals("**.bpmn", processDefinitionLocationSuffixes.get(1));
    assertEquals("**.bpmn20.xml", processDefinitionLocationSuffixes.get(0));
    assertEquals("@class", actualActivitiProperties.getJavaClassFieldForJackson());
    assertEquals("SpringAutoDeployment", actualActivitiProperties.getDeploymentName());
    assertEquals("classpath*:**/processes/", actualActivitiProperties.getProcessDefinitionLocationPrefix());
    assertEquals("default", actualActivitiProperties.getDeploymentMode());
    assertEquals("localhost", actualActivitiProperties.getMailServerHost());
    assertNull(actualActivitiProperties.getDatabaseSchema());
    assertNull(actualActivitiProperties.getMailServerDefaultFrom());
    assertNull(actualActivitiProperties.getMailServerPassword());
    assertNull(actualActivitiProperties.getMailServerUserName());
    assertNull(actualActivitiProperties.getCustomMybatisMappers());
    assertNull(actualActivitiProperties.getCustomMybatisXMLMappers());
    assertEquals(1025, actualActivitiProperties.getMailServerPort());
    assertEquals(HistoryLevel.NONE, actualActivitiProperties.getHistoryLevel());
    assertFalse(actualActivitiProperties.isDbHistoryUsed());
    assertFalse(actualActivitiProperties.isMailServerUseSsl());
    assertFalse(actualActivitiProperties.isMailServerUseTls());
    assertTrue(actualActivitiProperties.isAsyncExecutorActivate());
    assertTrue(actualActivitiProperties.isCheckProcessDefinitions());
    assertTrue(actualActivitiProperties.isCopyVariablesToLocalForTasks());
    assertTrue(actualActivitiProperties.isSerializePOJOsInVariablesToJson());
    assertTrue(actualActivitiProperties.isUseStrongUuids());
    String expectedDatabaseSchemaUpdate = Boolean.TRUE.toString();
    assertEquals(expectedDatabaseSchemaUpdate, actualActivitiProperties.getDatabaseSchemaUpdate());
  }
}
