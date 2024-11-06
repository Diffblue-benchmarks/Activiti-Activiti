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
package org.activiti.engine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import groovy.lang.GroovyClassLoader;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.impl.asyncexecutor.AsyncExecutor;
import org.activiti.engine.impl.asyncexecutor.DefaultAsyncJobExecutor;
import org.activiti.engine.impl.cfg.JtaProcessEngineConfiguration;
import org.activiti.engine.impl.history.HistoryLevel;
import org.activiti.engine.impl.util.DefaultClockImpl;
import org.activiti.engine.impl.util.json.JSONObject;
import org.activiti.engine.runtime.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessEngineConfigurationDiffblueTest {
  @InjectMocks
  private JtaProcessEngineConfiguration jtaProcessEngineConfiguration;

  /**
   * Test {@link ProcessEngineConfiguration#getProcessEngineName()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getProcessEngineName()}
   */
  @Test
  public void testGetProcessEngineName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(ProcessEngines.NAME_DEFAULT, jtaProcessEngineConfiguration.getProcessEngineName());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getProcessEngineName()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getProcessEngineName()}
   */
  @Test
  public void testGetProcessEngineName_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngines.NAME_DEFAULT, (new JtaProcessEngineConfiguration()).getProcessEngineName());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setProcessEngineName(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setProcessEngineName(String)}
   */
  @Test
  public void testSetProcessEngineName() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetProcessEngineNameResult = jtaProcessEngineConfiguration
        .setProcessEngineName("Process Engine Name");

    // Assert
    assertEquals("Process Engine Name", jtaProcessEngineConfiguration.getProcessEngineName());
    assertSame(jtaProcessEngineConfiguration, actualSetProcessEngineNameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getIdBlockSize()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getIdBlockSize()}
   */
  @Test
  public void testGetIdBlockSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(2500, jtaProcessEngineConfiguration.getIdBlockSize());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getIdBlockSize()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getIdBlockSize()}
   */
  @Test
  public void testGetIdBlockSize_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(2500, (new JtaProcessEngineConfiguration()).getIdBlockSize());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setIdBlockSize(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setIdBlockSize(int)}
   */
  @Test
  public void testSetIdBlockSize() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetIdBlockSizeResult = jtaProcessEngineConfiguration.setIdBlockSize(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getIdBlockSize());
    assertSame(jtaProcessEngineConfiguration, actualSetIdBlockSizeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setIdBlockSize(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setIdBlockSize(int)}
   */
  @Test
  public void testSetIdBlockSize_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetIdBlockSizeResult = jtaProcessEngineConfiguration.setIdBlockSize(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getIdBlockSize());
    assertSame(jtaProcessEngineConfiguration, actualSetIdBlockSizeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getHistory()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getHistory()}
   */
  @Test
  public void testGetHistory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("audit", jtaProcessEngineConfiguration.getHistory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getHistory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getHistory()}
   */
  @Test
  public void testGetHistory_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals("audit", (new JtaProcessEngineConfiguration()).getHistory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setHistory(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setHistory(String)}
   */
  @Test
  public void testSetHistory() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetHistoryResult = jtaProcessEngineConfiguration.setHistory("History");

    // Assert
    assertEquals("History", jtaProcessEngineConfiguration.getHistory());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoryResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerHost()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerHost()}
   */
  @Test
  public void testGetMailServerHost() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("localhost", jtaProcessEngineConfiguration.getMailServerHost());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerHost()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerHost()}
   */
  @Test
  public void testGetMailServerHost_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals("localhost", (new JtaProcessEngineConfiguration()).getMailServerHost());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerHost(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerHost(String)}
   */
  @Test
  public void testSetMailServerHost() {
    // Arrange, Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailServerHost("localhost"));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUsername()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUsername()}
   */
  @Test
  public void testGetMailServerUsername() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getMailServerUsername());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUsername()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUsername()}
   */
  @Test
  public void testGetMailServerUsername_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getMailServerUsername());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerUsername(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerUsername(String)}
   */
  @Test
  public void testSetMailServerUsername() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetMailServerUsernameResult = jtaProcessEngineConfiguration
        .setMailServerUsername("janedoe");

    // Assert
    assertEquals("janedoe", jtaProcessEngineConfiguration.getMailServerUsername());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUsernameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerPassword()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerPassword()}
   */
  @Test
  public void testGetMailServerPassword() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getMailServerPassword());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerPassword()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerPassword()}
   */
  @Test
  public void testGetMailServerPassword_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getMailServerPassword());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerPassword(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerPassword(String)}
   */
  @Test
  public void testSetMailServerPassword() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetMailServerPasswordResult = jtaProcessEngineConfiguration
        .setMailServerPassword("iloveyou");

    // Assert
    assertEquals("iloveyou", jtaProcessEngineConfiguration.getMailServerPassword());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerPasswordResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailSessionJndi()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailSessionJndi()}
   */
  @Test
  public void testGetMailSessionJndi() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getMailSessionJndi());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailSessionJndi(String)} with
   * {@code String}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getMailSessionJndi(String)}
   */
  @Test
  public void testGetMailSessionJndiWithString() {
    // Arrange, Act and Assert
    assertNull(jtaProcessEngineConfiguration.getMailSessionJndi("42"));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailSessionJndi()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailSessionJndi()}
   */
  @Test
  public void testGetMailSessionJndi_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getMailSessionJndi());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailSessionJndi(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailSessionJndi(String)}
   */
  @Test
  public void testSetMailSessionJndi() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetMailSessionJndiResult = jtaProcessEngineConfiguration
        .setMailSessionJndi("Mail Session Jndi");

    // Assert
    assertEquals("Mail Session Jndi", jtaProcessEngineConfiguration.getMailSessionJndi());
    assertSame(jtaProcessEngineConfiguration, actualSetMailSessionJndiResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailSessionsJndi()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailSessionsJndi()}
   */
  @Test
  public void testGetMailSessionsJndi() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.getMailSessionsJndi().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailSessionsJndi()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailSessionsJndi()}
   */
  @Test
  public void testGetMailSessionsJndi_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).getMailSessionsJndi().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailSessionsJndi(Map)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailSessionsJndi(Map)}
   */
  @Test
  public void testSetMailSessionsJndi() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailSessionsJndi(new HashMap<>()));
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailSessionsJndi(Map)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailSessionsJndi(Map)}
   */
  @Test
  public void testSetMailSessionsJndi_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailSessionsJndi(new HashMap<>()));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerPort()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerPort()}
   */
  @Test
  public void testGetMailServerPort() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(25, jtaProcessEngineConfiguration.getMailServerPort());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerPort()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerPort()}
   */
  @Test
  public void testGetMailServerPort_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(25, (new JtaProcessEngineConfiguration()).getMailServerPort());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerPort(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailServerPort(int)}
   */
  @Test
  public void testSetMailServerPort() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetMailServerPortResult = jtaProcessEngineConfiguration.setMailServerPort(8080);

    // Assert
    assertEquals(8080, jtaProcessEngineConfiguration.getMailServerPort());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerPortResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerPort(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailServerPort(int)}
   */
  @Test
  public void testSetMailServerPort_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerPortResult = jtaProcessEngineConfiguration.setMailServerPort(8080);

    // Assert
    assertEquals(8080, jtaProcessEngineConfiguration.getMailServerPort());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerPortResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseSSL()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseSSL()}
   */
  @Test
  public void testGetMailServerUseSSL_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).getMailServerUseSSL());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseSSL()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseSSL()}
   */
  @Test
  public void testGetMailServerUseSSL_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.getMailServerUseSSL());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseSSL()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseSSL()}
   */
  @Test
  public void testGetMailServerUseSSL_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMailServerUseSSL(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseSSL());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerUseSSL(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerUseSSL(boolean)}
   */
  @Test
  public void testSetMailServerUseSSL() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetMailServerUseSSLResult = jtaProcessEngineConfiguration
        .setMailServerUseSSL(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseSSL());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUseSSLResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerUseSSL(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerUseSSL(boolean)}
   */
  @Test
  public void testSetMailServerUseSSL_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerUseSSLResult = jtaProcessEngineConfiguration
        .setMailServerUseSSL(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseSSL());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUseSSLResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseTLS()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseTLS()}
   */
  @Test
  public void testGetMailServerUseTLS_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).getMailServerUseTLS());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseTLS()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseTLS()}
   */
  @Test
  public void testGetMailServerUseTLS_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.getMailServerUseTLS());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerUseTLS()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServerUseTLS()}
   */
  @Test
  public void testGetMailServerUseTLS_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setMailServerUseTLS(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseTLS());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerUseTLS(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerUseTLS(boolean)}
   */
  @Test
  public void testSetMailServerUseTLS() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetMailServerUseTLSResult = jtaProcessEngineConfiguration
        .setMailServerUseTLS(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseTLS());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUseTLSResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerUseTLS(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerUseTLS(boolean)}
   */
  @Test
  public void testSetMailServerUseTLS_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetMailServerUseTLSResult = jtaProcessEngineConfiguration
        .setMailServerUseTLS(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServerUseTLS());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerUseTLSResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerDefaultFrom()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getMailServerDefaultFrom()}
   */
  @Test
  public void testGetMailServerDefaultFrom() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("activiti@localhost", jtaProcessEngineConfiguration.getMailServerDefaultFrom());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServerDefaultFrom()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getMailServerDefaultFrom()}
   */
  @Test
  public void testGetMailServerDefaultFrom_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals("activiti@localhost", (new JtaProcessEngineConfiguration()).getMailServerDefaultFrom());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServerDefaultFrom(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setMailServerDefaultFrom(String)}
   */
  @Test
  public void testSetMailServerDefaultFrom() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetMailServerDefaultFromResult = jtaProcessEngineConfiguration
        .setMailServerDefaultFrom("jane.doe@example.org");

    // Assert
    assertEquals("jane.doe@example.org", jtaProcessEngineConfiguration.getMailServerDefaultFrom());
    assertSame(jtaProcessEngineConfiguration, actualSetMailServerDefaultFromResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServer(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServer(String)}
   */
  @Test
  public void testGetMailServer() {
    // Arrange, Act and Assert
    assertNull(jtaProcessEngineConfiguration.getMailServer("42"));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServers()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServers()}
   */
  @Test
  public void testGetMailServers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.getMailServers().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getMailServers()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getMailServers()}
   */
  @Test
  public void testGetMailServers_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).getMailServers().isEmpty());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServers(Map)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailServers(Map)}
   */
  @Test
  public void testSetMailServers() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailServers(new HashMap<>()));
  }

  /**
   * Test {@link ProcessEngineConfiguration#setMailServers(Map)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setMailServers(Map)}
   */
  @Test
  public void testSetMailServers_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setMailServers(new HashMap<>()));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseType()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseType()}
   */
  @Test
  public void testGetDatabaseType() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDatabaseType());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseType()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseType()}
   */
  @Test
  public void testGetDatabaseType_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDatabaseType());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseType(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setDatabaseType(String)}
   */
  @Test
  public void testSetDatabaseType() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDatabaseTypeResult = jtaProcessEngineConfiguration
        .setDatabaseType("Database Type");

    // Assert
    assertEquals("Database Type", jtaProcessEngineConfiguration.getDatabaseType());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseTypeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseSchemaUpdate()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getDatabaseSchemaUpdate()}
   */
  @Test
  public void testGetDatabaseSchemaUpdate() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    String actualDatabaseSchemaUpdate = jtaProcessEngineConfiguration.getDatabaseSchemaUpdate();

    // Assert
    assertEquals(Boolean.FALSE.toString(), actualDatabaseSchemaUpdate);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseSchemaUpdate()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getDatabaseSchemaUpdate()}
   */
  @Test
  public void testGetDatabaseSchemaUpdate_givenJtaProcessEngineConfiguration() {
    // Arrange and Act
    String actualDatabaseSchemaUpdate = (new JtaProcessEngineConfiguration()).getDatabaseSchemaUpdate();

    // Assert
    assertEquals(Boolean.FALSE.toString(), actualDatabaseSchemaUpdate);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseSchemaUpdate(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setDatabaseSchemaUpdate(String)}
   */
  @Test
  public void testSetDatabaseSchemaUpdate() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDatabaseSchemaUpdateResult = jtaProcessEngineConfiguration
        .setDatabaseSchemaUpdate("2020-03-01");

    // Assert
    assertEquals("2020-03-01", jtaProcessEngineConfiguration.getDatabaseSchemaUpdate());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseSchemaUpdateResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDataSource()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDataSource()}
   */
  @Test
  public void testGetDataSource() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDataSource());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDataSource()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDataSource()}
   */
  @Test
  public void testGetDataSource_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDataSource());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDataSource(DataSource)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setDataSource(DataSource)}
   */
  @Test
  public void testSetDataSource() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DataSource dataSource = mock(DataSource.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setDataSource(dataSource));
    assertSame(dataSource, jtaProcessEngineConfiguration.getDataSource());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcDriver()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcDriver()}
   */
  @Test
  public void testGetJdbcDriver() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("org.h2.Driver", jtaProcessEngineConfiguration.getJdbcDriver());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcDriver()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcDriver()}
   */
  @Test
  public void testGetJdbcDriver_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals("org.h2.Driver", (new JtaProcessEngineConfiguration()).getJdbcDriver());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcDriver(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcDriver(String)}
   */
  @Test
  public void testSetJdbcDriver() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetJdbcDriverResult = jtaProcessEngineConfiguration.setJdbcDriver("Jdbc Driver");

    // Assert
    assertEquals("Jdbc Driver", jtaProcessEngineConfiguration.getJdbcDriver());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcDriverResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcUrl()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcUrl()}
   */
  @Test
  public void testGetJdbcUrl() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", jtaProcessEngineConfiguration.getJdbcUrl());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcUrl()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcUrl()}
   */
  @Test
  public void testGetJdbcUrl_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals("jdbc:h2:tcp://localhost/~/activiti", (new JtaProcessEngineConfiguration()).getJdbcUrl());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcUrl(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcUrl(String)}
   */
  @Test
  public void testSetJdbcUrl() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetJdbcUrlResult = jtaProcessEngineConfiguration
        .setJdbcUrl("https://example.org/example");

    // Assert
    assertEquals("https://example.org/example", jtaProcessEngineConfiguration.getJdbcUrl());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcUrlResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcUsername()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcUsername()}
   */
  @Test
  public void testGetJdbcUsername() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("sa", jtaProcessEngineConfiguration.getJdbcUsername());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcUsername()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcUsername()}
   */
  @Test
  public void testGetJdbcUsername_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals("sa", (new JtaProcessEngineConfiguration()).getJdbcUsername());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcUsername(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcUsername(String)}
   */
  @Test
  public void testSetJdbcUsername() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetJdbcUsernameResult = jtaProcessEngineConfiguration.setJdbcUsername("janedoe");

    // Assert
    assertEquals("janedoe", jtaProcessEngineConfiguration.getJdbcUsername());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcUsernameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcPassword()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcPassword()}
   */
  @Test
  public void testGetJdbcPassword() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID, jtaProcessEngineConfiguration.getJdbcPassword());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcPassword()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcPassword()}
   */
  @Test
  public void testGetJdbcPassword_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID, (new JtaProcessEngineConfiguration()).getJdbcPassword());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPassword(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcPassword(String)}
   */
  @Test
  public void testSetJdbcPassword() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetJdbcPasswordResult = jtaProcessEngineConfiguration.setJdbcPassword("iloveyou");

    // Assert
    assertEquals("iloveyou", jtaProcessEngineConfiguration.getJdbcPassword());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPasswordResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isTransactionsExternallyManaged()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isTransactionsExternallyManaged()}
   */
  @Test
  public void testIsTransactionsExternallyManaged() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isTransactionsExternallyManaged());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isTransactionsExternallyManaged()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isTransactionsExternallyManaged()}
   */
  @Test
  public void testIsTransactionsExternallyManaged_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isTransactionsExternallyManaged());
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setTransactionsExternallyManaged(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setTransactionsExternallyManaged(boolean)}
   */
  @Test
  public void testSetTransactionsExternallyManaged() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTransactionsExternallyManaged(true));
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setTransactionsExternallyManaged(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setTransactionsExternallyManaged(boolean)}
   */
  @Test
  public void testSetTransactionsExternallyManaged_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setTransactionsExternallyManaged(true));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getHistoryLevel()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getHistoryLevel()}
   */
  @Test
  public void testGetHistoryLevel() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getHistoryLevel());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getHistoryLevel()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getHistoryLevel()}
   */
  @Test
  public void testGetHistoryLevel_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getHistoryLevel());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setHistoryLevel(HistoryLevel)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setHistoryLevel(HistoryLevel)}
   */
  @Test
  public void testSetHistoryLevel() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetHistoryLevelResult = jtaProcessEngineConfiguration
        .setHistoryLevel(HistoryLevel.NONE);

    // Assert
    assertEquals(HistoryLevel.NONE, jtaProcessEngineConfiguration.getHistoryLevel());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoryLevelResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setHistoryLevel(HistoryLevel)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setHistoryLevel(HistoryLevel)}
   */
  @Test
  public void testSetHistoryLevel_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetHistoryLevelResult = jtaProcessEngineConfiguration
        .setHistoryLevel(HistoryLevel.NONE);

    // Assert
    assertEquals(HistoryLevel.NONE, jtaProcessEngineConfiguration.getHistoryLevel());
    assertSame(jtaProcessEngineConfiguration, actualSetHistoryLevelResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isDbHistoryUsed()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isDbHistoryUsed()}
   */
  @Test
  public void testIsDbHistoryUsed() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isDbHistoryUsed());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isDbHistoryUsed()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isDbHistoryUsed()}
   */
  @Test
  public void testIsDbHistoryUsed_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isDbHistoryUsed());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDbHistoryUsed(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setDbHistoryUsed(boolean)}
   */
  @Test
  public void testSetDbHistoryUsed() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setDbHistoryUsed(true));
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDbHistoryUsed(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setDbHistoryUsed(boolean)}
   */
  @Test
  public void testSetDbHistoryUsed_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setDbHistoryUsed(true));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxActiveConnections()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcMaxActiveConnections()}
   */
  @Test
  public void testGetJdbcMaxActiveConnections() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getJdbcMaxActiveConnections());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxActiveConnections()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcMaxActiveConnections()}
   */
  @Test
  public void testGetJdbcMaxActiveConnections_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcMaxActiveConnections());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxActiveConnections(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcMaxActiveConnections(int)}
   */
  @Test
  public void testSetJdbcMaxActiveConnections() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxActiveConnectionsResult = jtaProcessEngineConfiguration
        .setJdbcMaxActiveConnections(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxActiveConnectionsResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxActiveConnections(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcMaxActiveConnections(int)}
   */
  @Test
  public void testSetJdbcMaxActiveConnections_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxActiveConnectionsResult = jtaProcessEngineConfiguration
        .setJdbcMaxActiveConnections(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxActiveConnections());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxActiveConnectionsResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxIdleConnections()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcMaxIdleConnections()}
   */
  @Test
  public void testGetJdbcMaxIdleConnections() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getJdbcMaxIdleConnections());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxIdleConnections()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcMaxIdleConnections()}
   */
  @Test
  public void testGetJdbcMaxIdleConnections_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcMaxIdleConnections());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxIdleConnections(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcMaxIdleConnections(int)}
   */
  @Test
  public void testSetJdbcMaxIdleConnections() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxIdleConnectionsResult = jtaProcessEngineConfiguration
        .setJdbcMaxIdleConnections(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxIdleConnectionsResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxIdleConnections(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcMaxIdleConnections(int)}
   */
  @Test
  public void testSetJdbcMaxIdleConnections_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxIdleConnectionsResult = jtaProcessEngineConfiguration
        .setJdbcMaxIdleConnections(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxIdleConnections());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxIdleConnectionsResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxCheckoutTime()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcMaxCheckoutTime()}
   */
  @Test
  public void testGetJdbcMaxCheckoutTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getJdbcMaxCheckoutTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxCheckoutTime()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcMaxCheckoutTime()}
   */
  @Test
  public void testGetJdbcMaxCheckoutTime_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcMaxCheckoutTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxCheckoutTime(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcMaxCheckoutTime(int)}
   */
  @Test
  public void testSetJdbcMaxCheckoutTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxCheckoutTimeResult = jtaProcessEngineConfiguration
        .setJdbcMaxCheckoutTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxCheckoutTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxCheckoutTime(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcMaxCheckoutTime(int)}
   */
  @Test
  public void testSetJdbcMaxCheckoutTime_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxCheckoutTimeResult = jtaProcessEngineConfiguration
        .setJdbcMaxCheckoutTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxCheckoutTime());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxCheckoutTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxWaitTime()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcMaxWaitTime()}
   */
  @Test
  public void testGetJdbcMaxWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getJdbcMaxWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcMaxWaitTime()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcMaxWaitTime()}
   */
  @Test
  public void testGetJdbcMaxWaitTime_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcMaxWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxWaitTime(int)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcMaxWaitTime(int)}
   */
  @Test
  public void testSetJdbcMaxWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxWaitTimeResult = jtaProcessEngineConfiguration.setJdbcMaxWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcMaxWaitTime(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setJdbcMaxWaitTime(int)}
   */
  @Test
  public void testSetJdbcMaxWaitTime_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcMaxWaitTimeResult = jtaProcessEngineConfiguration.setJdbcMaxWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcMaxWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcMaxWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJdbcPingEnabled()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isJdbcPingEnabled()}
   */
  @Test
  public void testIsJdbcPingEnabled_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isJdbcPingEnabled());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJdbcPingEnabled()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isJdbcPingEnabled()}
   */
  @Test
  public void testIsJdbcPingEnabled_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isJdbcPingEnabled());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJdbcPingEnabled()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isJdbcPingEnabled()}
   */
  @Test
  public void testIsJdbcPingEnabled_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setJdbcPingEnabled(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isJdbcPingEnabled());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPingEnabled(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcPingEnabled(boolean)}
   */
  @Test
  public void testSetJdbcPingEnabled() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcPingEnabledResult = jtaProcessEngineConfiguration.setJdbcPingEnabled(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJdbcPingEnabled());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingEnabledResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPingEnabled(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcPingEnabled(boolean)}
   */
  @Test
  public void testSetJdbcPingEnabled_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcPingEnabledResult = jtaProcessEngineConfiguration.setJdbcPingEnabled(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJdbcPingEnabled());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingEnabledResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcPingQuery()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcPingQuery()}
   */
  @Test
  public void testGetJdbcPingQuery() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getJdbcPingQuery());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcPingQuery()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getJdbcPingQuery()}
   */
  @Test
  public void testGetJdbcPingQuery_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJdbcPingQuery());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPingQuery(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcPingQuery(String)}
   */
  @Test
  public void testSetJdbcPingQuery() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetJdbcPingQueryResult = jtaProcessEngineConfiguration
        .setJdbcPingQuery("Jdbc Ping Query");

    // Assert
    assertEquals("Jdbc Ping Query", jtaProcessEngineConfiguration.getJdbcPingQuery());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingQueryResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcPingConnectionNotUsedFor()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcPingConnectionNotUsedFor()}
   */
  @Test
  public void testGetJdbcPingConnectionNotUsedFor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJdbcPingConnectionNotUsedFor()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcPingConnectionNotUsedFor()}
   */
  @Test
  public void testGetJdbcPingConnectionNotUsedFor_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcPingConnectionNotUsedFor());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPingConnectionNotUsedFor(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcPingConnectionNotUsedFor(int)}
   */
  @Test
  public void testSetJdbcPingConnectionNotUsedFor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcPingConnectionNotUsedForResult = jtaProcessEngineConfiguration
        .setJdbcPingConnectionNotUsedFor(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingConnectionNotUsedForResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJdbcPingConnectionNotUsedFor(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcPingConnectionNotUsedFor(int)}
   */
  @Test
  public void testSetJdbcPingConnectionNotUsedFor_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcPingConnectionNotUsedForResult = jtaProcessEngineConfiguration
        .setJdbcPingConnectionNotUsedFor(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcPingConnectionNotUsedFor());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcPingConnectionNotUsedForResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#getJdbcDefaultTransactionIsolationLevel()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcDefaultTransactionIsolationLevel()}
   */
  @Test
  public void testGetJdbcDefaultTransactionIsolationLevel() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(0, jtaProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#getJdbcDefaultTransactionIsolationLevel()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJdbcDefaultTransactionIsolationLevel()}
   */
  @Test
  public void testGetJdbcDefaultTransactionIsolationLevel_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(0, (new JtaProcessEngineConfiguration()).getJdbcDefaultTransactionIsolationLevel());
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setJdbcDefaultTransactionIsolationLevel(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcDefaultTransactionIsolationLevel(int)}
   */
  @Test
  public void testSetJdbcDefaultTransactionIsolationLevel() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJdbcDefaultTransactionIsolationLevelResult = jtaProcessEngineConfiguration
        .setJdbcDefaultTransactionIsolationLevel(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcDefaultTransactionIsolationLevelResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setJdbcDefaultTransactionIsolationLevel(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJdbcDefaultTransactionIsolationLevel(int)}
   */
  @Test
  public void testSetJdbcDefaultTransactionIsolationLevel_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJdbcDefaultTransactionIsolationLevelResult = jtaProcessEngineConfiguration
        .setJdbcDefaultTransactionIsolationLevel(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getJdbcDefaultTransactionIsolationLevel());
    assertSame(jtaProcessEngineConfiguration, actualSetJdbcDefaultTransactionIsolationLevelResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}
   */
  @Test
  public void testIsAsyncExecutorActivate_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isAsyncExecutorActivate());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}
   */
  @Test
  public void testIsAsyncExecutorActivate_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isAsyncExecutorActivate());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isAsyncExecutorActivate()}
   */
  @Test
  public void testIsAsyncExecutorActivate_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setAsyncExecutorActivate(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorActivate());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setAsyncExecutorActivate(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setAsyncExecutorActivate(boolean)}
   */
  @Test
  public void testSetAsyncExecutorActivate() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetAsyncExecutorActivateResult = jtaProcessEngineConfiguration
        .setAsyncExecutorActivate(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorActivate());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorActivateResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setAsyncExecutorActivate(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setAsyncExecutorActivate(boolean)}
   */
  @Test
  public void testSetAsyncExecutorActivate_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetAsyncExecutorActivateResult = jtaProcessEngineConfiguration
        .setAsyncExecutorActivate(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isAsyncExecutorActivate());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorActivateResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getClassLoader()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getClassLoader()}
   */
  @Test
  public void testGetClassLoader() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getClassLoader());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getClassLoader()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getClassLoader()}
   */
  @Test
  public void testGetClassLoader_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getClassLoader());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setClassLoader(ClassLoader)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setClassLoader(ClassLoader)}
   */
  @Test
  public void testSetClassLoader() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    GroovyClassLoader classLoader = new GroovyClassLoader();

    // Act
    ProcessEngineConfiguration actualSetClassLoaderResult = jtaProcessEngineConfiguration.setClassLoader(classLoader);

    // Assert
    assertSame(classLoader, jtaProcessEngineConfiguration.getClassLoader());
    assertSame(jtaProcessEngineConfiguration, actualSetClassLoaderResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setClassLoader(ClassLoader)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setClassLoader(ClassLoader)}
   */
  @Test
  public void testSetClassLoader_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    GroovyClassLoader classLoader = new GroovyClassLoader();

    // Act
    ProcessEngineConfiguration actualSetClassLoaderResult = jtaProcessEngineConfiguration.setClassLoader(classLoader);

    // Assert
    assertSame(classLoader, jtaProcessEngineConfiguration.getClassLoader());
    assertSame(jtaProcessEngineConfiguration, actualSetClassLoaderResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isUseClassForNameClassLoading()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isUseClassForNameClassLoading()}
   */
  @Test
  public void testIsUseClassForNameClassLoading() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isUseClassForNameClassLoading());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isUseClassForNameClassLoading()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isUseClassForNameClassLoading()}
   */
  @Test
  public void testIsUseClassForNameClassLoading_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertTrue((new JtaProcessEngineConfiguration()).isUseClassForNameClassLoading());
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setUseClassForNameClassLoading(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setUseClassForNameClassLoading(boolean)}
   */
  @Test
  public void testSetUseClassForNameClassLoading() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setUseClassForNameClassLoading(true));
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setUseClassForNameClassLoading(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setUseClassForNameClassLoading(boolean)}
   */
  @Test
  public void testSetUseClassForNameClassLoading_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setUseClassForNameClassLoading(true));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJpaEntityManagerFactory()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJpaEntityManagerFactory()}
   */
  @Test
  public void testGetJpaEntityManagerFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getJpaEntityManagerFactory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJpaEntityManagerFactory()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJpaEntityManagerFactory()}
   */
  @Test
  public void testGetJpaEntityManagerFactory_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJpaEntityManagerFactory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaEntityManagerFactory(Object)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaEntityManagerFactory(Object)}
   */
  @Test
  public void testSetJpaEntityManagerFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    Object object = JSONObject.NULL;

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setJpaEntityManagerFactory(object));
    assertSame(object, jtaProcessEngineConfiguration.getJpaEntityManagerFactory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaEntityManagerFactory(Object)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaEntityManagerFactory(Object)}
   */
  @Test
  public void testSetJpaEntityManagerFactory_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    Object object = JSONObject.NULL;

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setJpaEntityManagerFactory(object));
    assertSame(object, jtaProcessEngineConfiguration.getJpaEntityManagerFactory());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaHandleTransaction()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isJpaHandleTransaction()}
   */
  @Test
  public void testIsJpaHandleTransaction_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isJpaHandleTransaction());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaHandleTransaction()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isJpaHandleTransaction()}
   */
  @Test
  public void testIsJpaHandleTransaction_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isJpaHandleTransaction());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaHandleTransaction()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isJpaHandleTransaction()}
   */
  @Test
  public void testIsJpaHandleTransaction_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setJpaHandleTransaction(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaHandleTransaction());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaHandleTransaction(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaHandleTransaction(boolean)}
   */
  @Test
  public void testSetJpaHandleTransaction() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJpaHandleTransactionResult = jtaProcessEngineConfiguration
        .setJpaHandleTransaction(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaHandleTransaction());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaHandleTransactionResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaHandleTransaction(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaHandleTransaction(boolean)}
   */
  @Test
  public void testSetJpaHandleTransaction_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJpaHandleTransactionResult = jtaProcessEngineConfiguration
        .setJpaHandleTransaction(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaHandleTransaction());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaHandleTransactionResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}
   */
  @Test
  public void testIsJpaCloseEntityManager_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isJpaCloseEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}
   */
  @Test
  public void testIsJpaCloseEntityManager_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isJpaCloseEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isJpaCloseEntityManager()}
   */
  @Test
  public void testIsJpaCloseEntityManager_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setJpaCloseEntityManager(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaCloseEntityManager());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaCloseEntityManager(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaCloseEntityManager(boolean)}
   */
  @Test
  public void testSetJpaCloseEntityManager() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetJpaCloseEntityManagerResult = jtaProcessEngineConfiguration
        .setJpaCloseEntityManager(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaCloseEntityManager());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaCloseEntityManagerResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaCloseEntityManager(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaCloseEntityManager(boolean)}
   */
  @Test
  public void testSetJpaCloseEntityManager_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetJpaCloseEntityManagerResult = jtaProcessEngineConfiguration
        .setJpaCloseEntityManager(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isJpaCloseEntityManager());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaCloseEntityManagerResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJpaPersistenceUnitName()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJpaPersistenceUnitName()}
   */
  @Test
  public void testGetJpaPersistenceUnitName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getJpaPersistenceUnitName());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getJpaPersistenceUnitName()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getJpaPersistenceUnitName()}
   */
  @Test
  public void testGetJpaPersistenceUnitName_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getJpaPersistenceUnitName());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setJpaPersistenceUnitName(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setJpaPersistenceUnitName(String)}
   */
  @Test
  public void testSetJpaPersistenceUnitName() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetJpaPersistenceUnitNameResult = jtaProcessEngineConfiguration
        .setJpaPersistenceUnitName("Jpa Persistence Unit Name");

    // Assert
    assertEquals("Jpa Persistence Unit Name", jtaProcessEngineConfiguration.getJpaPersistenceUnitName());
    assertSame(jtaProcessEngineConfiguration, actualSetJpaPersistenceUnitNameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDataSourceJndiName()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDataSourceJndiName()}
   */
  @Test
  public void testGetDataSourceJndiName() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDataSourceJndiName());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDataSourceJndiName()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDataSourceJndiName()}
   */
  @Test
  public void testGetDataSourceJndiName_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDataSourceJndiName());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDataSourceJndiName(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setDataSourceJndiName(String)}
   */
  @Test
  public void testSetDataSourceJndiName() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDataSourceJndiNameResult = jtaProcessEngineConfiguration
        .setDataSourceJndiName("Data Source Jndi Name");

    // Assert
    assertEquals("Data Source Jndi Name", jtaProcessEngineConfiguration.getDataSourceJndiName());
    assertSame(jtaProcessEngineConfiguration, actualSetDataSourceJndiNameResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDefaultCamelContext()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getDefaultCamelContext()}
   */
  @Test
  public void testGetDefaultCamelContext() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("camelContext", jtaProcessEngineConfiguration.getDefaultCamelContext());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDefaultCamelContext()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getDefaultCamelContext()}
   */
  @Test
  public void testGetDefaultCamelContext_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals("camelContext", (new JtaProcessEngineConfiguration()).getDefaultCamelContext());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDefaultCamelContext(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setDefaultCamelContext(String)}
   */
  @Test
  public void testSetDefaultCamelContext() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDefaultCamelContextResult = jtaProcessEngineConfiguration
        .setDefaultCamelContext("Default Camel Context");

    // Assert
    assertEquals("Default Camel Context", jtaProcessEngineConfiguration.getDefaultCamelContext());
    assertSame(jtaProcessEngineConfiguration, actualSetDefaultCamelContextResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setProcessEngineLifecycleListener(ProcessEngineLifecycleListener)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setProcessEngineLifecycleListener(ProcessEngineLifecycleListener)}
   */
  @Test
  public void testSetProcessEngineLifecycleListener() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ProcessEngineLifecycleListener processEngineLifecycleListener = mock(ProcessEngineLifecycleListener.class);

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration,
        jtaProcessEngineConfiguration.setProcessEngineLifecycleListener(processEngineLifecycleListener));
    assertSame(processEngineLifecycleListener, jtaProcessEngineConfiguration.getProcessEngineLifecycleListener());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getProcessEngineLifecycleListener()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getProcessEngineLifecycleListener()}
   */
  @Test
  public void testGetProcessEngineLifecycleListener() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getProcessEngineLifecycleListener());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getProcessEngineLifecycleListener()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getProcessEngineLifecycleListener()}
   */
  @Test
  public void testGetProcessEngineLifecycleListener_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getProcessEngineLifecycleListener());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseTablePrefix()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getDatabaseTablePrefix()}
   */
  @Test
  public void testGetDatabaseTablePrefix() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID, jtaProcessEngineConfiguration.getDatabaseTablePrefix());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseTablePrefix()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getDatabaseTablePrefix()}
   */
  @Test
  public void testGetDatabaseTablePrefix_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID,
        (new JtaProcessEngineConfiguration()).getDatabaseTablePrefix());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseTablePrefix(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setDatabaseTablePrefix(String)}
   */
  @Test
  public void testSetDatabaseTablePrefix() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDatabaseTablePrefixResult = jtaProcessEngineConfiguration
        .setDatabaseTablePrefix("Database Table Prefix");

    // Assert
    assertEquals("Database Table Prefix", jtaProcessEngineConfiguration.getDatabaseTablePrefix());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseTablePrefixResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setTablePrefixIsSchema(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setTablePrefixIsSchema(boolean)}
   */
  @Test
  public void testSetTablePrefixIsSchema() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetTablePrefixIsSchemaResult = jtaProcessEngineConfiguration
        .setTablePrefixIsSchema(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isTablePrefixIsSchema());
    assertSame(jtaProcessEngineConfiguration, actualSetTablePrefixIsSchemaResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setTablePrefixIsSchema(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setTablePrefixIsSchema(boolean)}
   */
  @Test
  public void testSetTablePrefixIsSchema_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetTablePrefixIsSchemaResult = jtaProcessEngineConfiguration
        .setTablePrefixIsSchema(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isTablePrefixIsSchema());
    assertSame(jtaProcessEngineConfiguration, actualSetTablePrefixIsSchemaResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}
   */
  @Test
  public void testIsTablePrefixIsSchema_givenJtaProcessEngineConfiguration_thenReturnFalse() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isTablePrefixIsSchema());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}
   */
  @Test
  public void testIsTablePrefixIsSchema_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isTablePrefixIsSchema());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#isTablePrefixIsSchema()}
   */
  @Test
  public void testIsTablePrefixIsSchema_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setTablePrefixIsSchema(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isTablePrefixIsSchema());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseWildcardEscapeCharacter()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getDatabaseWildcardEscapeCharacter()}
   */
  @Test
  public void testGetDatabaseWildcardEscapeCharacter() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseWildcardEscapeCharacter()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getDatabaseWildcardEscapeCharacter()}
   */
  @Test
  public void testGetDatabaseWildcardEscapeCharacter_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDatabaseWildcardEscapeCharacter());
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setDatabaseWildcardEscapeCharacter(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setDatabaseWildcardEscapeCharacter(String)}
   */
  @Test
  public void testSetDatabaseWildcardEscapeCharacter() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDatabaseWildcardEscapeCharacterResult = jtaProcessEngineConfiguration
        .setDatabaseWildcardEscapeCharacter("Database Wildcard Escape Character");

    // Assert
    assertEquals("Database Wildcard Escape Character",
        jtaProcessEngineConfiguration.getDatabaseWildcardEscapeCharacter());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseWildcardEscapeCharacterResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseCatalog()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseCatalog()}
   */
  @Test
  public void testGetDatabaseCatalog() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID, jtaProcessEngineConfiguration.getDatabaseCatalog());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseCatalog()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseCatalog()}
   */
  @Test
  public void testGetDatabaseCatalog_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(ProcessEngineConfiguration.NO_TENANT_ID, (new JtaProcessEngineConfiguration()).getDatabaseCatalog());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseCatalog(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setDatabaseCatalog(String)}
   */
  @Test
  public void testSetDatabaseCatalog() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDatabaseCatalogResult = jtaProcessEngineConfiguration
        .setDatabaseCatalog("Database Catalog");

    // Assert
    assertEquals("Database Catalog", jtaProcessEngineConfiguration.getDatabaseCatalog());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseCatalogResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseSchema()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseSchema()}
   */
  @Test
  public void testGetDatabaseSchema() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getDatabaseSchema());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDatabaseSchema()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getDatabaseSchema()}
   */
  @Test
  public void testGetDatabaseSchema_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getDatabaseSchema());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDatabaseSchema(String)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setDatabaseSchema(String)}
   */
  @Test
  public void testSetDatabaseSchema() {
    // Arrange and Act
    ProcessEngineConfiguration actualSetDatabaseSchemaResult = jtaProcessEngineConfiguration
        .setDatabaseSchema("Database Schema");

    // Assert
    assertEquals("Database Schema", jtaProcessEngineConfiguration.getDatabaseSchema());
    assertSame(jtaProcessEngineConfiguration, actualSetDatabaseSchemaResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getXmlEncoding()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getXmlEncoding()}
   */
  @Test
  public void testGetXmlEncoding() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals("UTF-8", jtaProcessEngineConfiguration.getXmlEncoding());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getXmlEncoding()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getXmlEncoding()}
   */
  @Test
  public void testGetXmlEncoding_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals("UTF-8", (new JtaProcessEngineConfiguration()).getXmlEncoding());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setXmlEncoding(String)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setXmlEncoding(String)}
   */
  @Test
  public void testSetXmlEncoding() {
    // Arrange, Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setXmlEncoding("UTF-8"));
  }

  /**
   * Test {@link ProcessEngineConfiguration#getClock()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getClock()}
   */
  @Test
  public void testGetClock() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getClock());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getClock()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getClock()}
   */
  @Test
  public void testGetClock_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getClock());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setClock(Clock)}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setClock(Clock)}
   */
  @Test
  public void testSetClock() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultClockImpl clock = new DefaultClockImpl();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setClock(clock));
    assertSame(clock, jtaProcessEngineConfiguration.getClock());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setClock(Clock)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#setClock(Clock)}
   */
  @Test
  public void testSetClock_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultClockImpl clock = new DefaultClockImpl();

    // Act and Assert
    assertSame(jtaProcessEngineConfiguration, jtaProcessEngineConfiguration.setClock(clock));
    assertSame(clock, jtaProcessEngineConfiguration.getClock());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getAsyncExecutor()}.
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getAsyncExecutor()}
   */
  @Test
  public void testGetAsyncExecutor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertNull(jtaProcessEngineConfiguration.getAsyncExecutor());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getAsyncExecutor()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test: {@link ProcessEngineConfiguration#getAsyncExecutor()}
   */
  @Test
  public void testGetAsyncExecutor_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertNull((new JtaProcessEngineConfiguration()).getAsyncExecutor());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setAsyncExecutor(AsyncExecutor)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setAsyncExecutor(AsyncExecutor)}
   */
  @Test
  public void testSetAsyncExecutor() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();

    // Act
    ProcessEngineConfiguration actualSetAsyncExecutorResult = jtaProcessEngineConfiguration
        .setAsyncExecutor(asyncExecutor);

    // Assert
    assertSame(asyncExecutor, jtaProcessEngineConfiguration.getAsyncExecutor());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setAsyncExecutor(AsyncExecutor)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setAsyncExecutor(AsyncExecutor)}
   */
  @Test
  public void testSetAsyncExecutor_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    DefaultAsyncJobExecutor asyncExecutor = new DefaultAsyncJobExecutor();

    // Act
    ProcessEngineConfiguration actualSetAsyncExecutorResult = jtaProcessEngineConfiguration
        .setAsyncExecutor(asyncExecutor);

    // Assert
    assertSame(asyncExecutor, jtaProcessEngineConfiguration.getAsyncExecutor());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncExecutorResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getLockTimeAsyncJobWaitTime()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getLockTimeAsyncJobWaitTime()}
   */
  @Test
  public void testGetLockTimeAsyncJobWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(60, jtaProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getLockTimeAsyncJobWaitTime()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getLockTimeAsyncJobWaitTime()}
   */
  @Test
  public void testGetLockTimeAsyncJobWaitTime_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(60, (new JtaProcessEngineConfiguration()).getLockTimeAsyncJobWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setLockTimeAsyncJobWaitTime(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setLockTimeAsyncJobWaitTime(int)}
   */
  @Test
  public void testSetLockTimeAsyncJobWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetLockTimeAsyncJobWaitTimeResult = jtaProcessEngineConfiguration
        .setLockTimeAsyncJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetLockTimeAsyncJobWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setLockTimeAsyncJobWaitTime(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setLockTimeAsyncJobWaitTime(int)}
   */
  @Test
  public void testSetLockTimeAsyncJobWaitTime_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetLockTimeAsyncJobWaitTimeResult = jtaProcessEngineConfiguration
        .setLockTimeAsyncJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getLockTimeAsyncJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetLockTimeAsyncJobWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDefaultFailedJobWaitTime()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getDefaultFailedJobWaitTime()}
   */
  @Test
  public void testGetDefaultFailedJobWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(10, jtaProcessEngineConfiguration.getDefaultFailedJobWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getDefaultFailedJobWaitTime()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getDefaultFailedJobWaitTime()}
   */
  @Test
  public void testGetDefaultFailedJobWaitTime_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(10, (new JtaProcessEngineConfiguration()).getDefaultFailedJobWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDefaultFailedJobWaitTime(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setDefaultFailedJobWaitTime(int)}
   */
  @Test
  public void testSetDefaultFailedJobWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetDefaultFailedJobWaitTimeResult = jtaProcessEngineConfiguration
        .setDefaultFailedJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetDefaultFailedJobWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setDefaultFailedJobWaitTime(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setDefaultFailedJobWaitTime(int)}
   */
  @Test
  public void testSetDefaultFailedJobWaitTime_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetDefaultFailedJobWaitTimeResult = jtaProcessEngineConfiguration
        .setDefaultFailedJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getDefaultFailedJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetDefaultFailedJobWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#getAsyncFailedJobWaitTime()}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getAsyncFailedJobWaitTime()}
   */
  @Test
  public void testGetAsyncFailedJobWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertEquals(10, jtaProcessEngineConfiguration.getAsyncFailedJobWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#getAsyncFailedJobWaitTime()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#getAsyncFailedJobWaitTime()}
   */
  @Test
  public void testGetAsyncFailedJobWaitTime_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertEquals(10, (new JtaProcessEngineConfiguration()).getAsyncFailedJobWaitTime());
  }

  /**
   * Test {@link ProcessEngineConfiguration#setAsyncFailedJobWaitTime(int)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setAsyncFailedJobWaitTime(int)}
   */
  @Test
  public void testSetAsyncFailedJobWaitTime() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetAsyncFailedJobWaitTimeResult = jtaProcessEngineConfiguration
        .setAsyncFailedJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncFailedJobWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#setAsyncFailedJobWaitTime(int)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setAsyncFailedJobWaitTime(int)}
   */
  @Test
  public void testSetAsyncFailedJobWaitTime_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetAsyncFailedJobWaitTimeResult = jtaProcessEngineConfiguration
        .setAsyncFailedJobWaitTime(1);

    // Assert
    assertEquals(1, jtaProcessEngineConfiguration.getAsyncFailedJobWaitTime());
    assertSame(jtaProcessEngineConfiguration, actualSetAsyncFailedJobWaitTimeResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}
   */
  @Test
  public void testIsEnableProcessDefinitionInfoCache_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isEnableProcessDefinitionInfoCache());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}
   */
  @Test
  public void testIsEnableProcessDefinitionInfoCache_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isEnableProcessDefinitionInfoCache()}
   */
  @Test
  public void testIsEnableProcessDefinitionInfoCache_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setEnableProcessDefinitionInfoCache(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setEnableProcessDefinitionInfoCache(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setEnableProcessDefinitionInfoCache(boolean)}
   */
  @Test
  public void testSetEnableProcessDefinitionInfoCache() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetEnableProcessDefinitionInfoCacheResult = jtaProcessEngineConfiguration
        .setEnableProcessDefinitionInfoCache(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertSame(jtaProcessEngineConfiguration, actualSetEnableProcessDefinitionInfoCacheResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setEnableProcessDefinitionInfoCache(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setEnableProcessDefinitionInfoCache(boolean)}
   */
  @Test
  public void testSetEnableProcessDefinitionInfoCache_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetEnableProcessDefinitionInfoCacheResult = jtaProcessEngineConfiguration
        .setEnableProcessDefinitionInfoCache(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isEnableProcessDefinitionInfoCache());
    assertSame(jtaProcessEngineConfiguration, actualSetEnableProcessDefinitionInfoCacheResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setCopyVariablesToLocalForTasks(boolean)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setCopyVariablesToLocalForTasks(boolean)}
   */
  @Test
  public void testSetCopyVariablesToLocalForTasks() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act
    ProcessEngineConfiguration actualSetCopyVariablesToLocalForTasksResult = jtaProcessEngineConfiguration
        .setCopyVariablesToLocalForTasks(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertSame(jtaProcessEngineConfiguration, actualSetCopyVariablesToLocalForTasksResult);
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setCopyVariablesToLocalForTasks(boolean)}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setCopyVariablesToLocalForTasks(boolean)}
   */
  @Test
  public void testSetCopyVariablesToLocalForTasks_givenJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualSetCopyVariablesToLocalForTasksResult = jtaProcessEngineConfiguration
        .setCopyVariablesToLocalForTasks(true);

    // Assert
    assertTrue(jtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
    assertSame(jtaProcessEngineConfiguration, actualSetCopyVariablesToLocalForTasksResult);
  }

  /**
   * Test {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}.
   * <ul>
   *   <li>Given {@link JtaProcessEngineConfiguration} (default constructor).</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}
   */
  @Test
  public void testIsCopyVariablesToLocalForTasks_givenJtaProcessEngineConfiguration() {
    // Arrange, Act and Assert
    assertFalse((new JtaProcessEngineConfiguration()).isCopyVariablesToLocalForTasks());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}.
   * <ul>
   *   <li>Then return {@code false}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}
   */
  @Test
  public void testIsCopyVariablesToLocalForTasks_thenReturnFalse() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertFalse(jtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
  }

  /**
   * Test {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}.
   * <ul>
   *   <li>Then return {@code true}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#isCopyVariablesToLocalForTasks()}
   */
  @Test
  public void testIsCopyVariablesToLocalForTasks_thenReturnTrue() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    jtaProcessEngineConfiguration.setCopyVariablesToLocalForTasks(true);
    jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Act and Assert
    assertTrue(jtaProcessEngineConfiguration.isCopyVariablesToLocalForTasks());
  }

  /**
   * Test
   * {@link ProcessEngineConfiguration#setEngineAgendaFactory(ActivitiEngineAgendaFactory)}.
   * <p>
   * Method under test:
   * {@link ProcessEngineConfiguration#setEngineAgendaFactory(ActivitiEngineAgendaFactory)}
   */
  @Test
  public void testSetEngineAgendaFactory() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration = new JtaProcessEngineConfiguration();
    ActivitiEngineAgendaFactory engineAgendaFactory = mock(ActivitiEngineAgendaFactory.class);

    // Act
    jtaProcessEngineConfiguration.setEngineAgendaFactory(engineAgendaFactory);

    // Assert
    assertSame(engineAgendaFactory, jtaProcessEngineConfiguration.getEngineAgendaFactory());
  }
}
