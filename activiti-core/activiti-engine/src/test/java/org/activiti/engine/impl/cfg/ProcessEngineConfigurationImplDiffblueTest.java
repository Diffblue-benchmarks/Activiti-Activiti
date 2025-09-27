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
package org.activiti.engine.impl.cfg;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import com.diffblue.cover.annotations.ContributionFromDiffblue;
import com.diffblue.cover.annotations.ManagedByDiffblue;
import com.diffblue.cover.annotations.MethodsUnderTest;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.xml.namespace.QName;
import org.activiti.core.el.CustomFunctionProvider;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.cfg.ProcessEngineConfigurator;
import org.activiti.engine.impl.cfg.multitenant.MultiSchemaMultiTenantProcessEngineConfiguration;
import org.activiti.engine.test.cfg.multitenant.DummyTenantInfoHolder;
import org.activiti.engine.test.impl.logger.ProcessExecutionLoggerConfigurator;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class ProcessEngineConfigurationImplDiffblueTest {
  /**
   * Test {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addConfigurator(ProcessEngineConfigurator)"
  })
  public void testAddConfigurator() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setConfigurators(new ArrayList<>());

    // Act
    ProcessEngineConfigurationImpl actualAddConfiguratorResult =
        multiSchemaMultiTenantProcessEngineConfiguration.addConfigurator(
            new ProcessExecutionLoggerConfigurator());

    // Assert
    assertSame(multiSchemaMultiTenantProcessEngineConfiguration, actualAddConfiguratorResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}.
   *
   * <ul>
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#addConfigurator(ProcessEngineConfigurator)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addConfigurator(ProcessEngineConfigurator)"
  })
  public void testAddConfigurator_thenReturnJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualAddConfiguratorResult =
        jtaProcessEngineConfiguration.addConfigurator(new ProcessExecutionLoggerConfigurator());

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualAddConfiguratorResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addWsEndpointAddress(QName, URL)}.
   *
   * <ul>
   *   <li>When valueOf {@code foo}.
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link ProcessEngineConfigurationImpl#addWsEndpointAddress(QName, URL)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfiguration ProcessEngineConfigurationImpl.addWsEndpointAddress(QName, URL)"
  })
  public void testAddWsEndpointAddress_whenValueOfFoo_thenReturnJtaProcessEngineConfiguration()
      throws MalformedURLException {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfiguration actualAddWsEndpointAddressResult =
        jtaProcessEngineConfiguration.addWsEndpointAddress(
            QName.valueOf("foo"),
            Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toURL());

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualAddWsEndpointAddressResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}.
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addCustomFunctionProvider(CustomFunctionProvider)"
  })
  public void testAddCustomFunctionProvider() {
    // Arrange
    MultiSchemaMultiTenantProcessEngineConfiguration
        multiSchemaMultiTenantProcessEngineConfiguration =
            new MultiSchemaMultiTenantProcessEngineConfiguration(new DummyTenantInfoHolder());
    multiSchemaMultiTenantProcessEngineConfiguration.setCustomFunctionProviders(new ArrayList<>());

    // Act
    ProcessEngineConfigurationImpl actualAddCustomFunctionProviderResult =
        multiSchemaMultiTenantProcessEngineConfiguration.addCustomFunctionProvider(
            mock(CustomFunctionProvider.class));

    // Assert
    assertSame(
        multiSchemaMultiTenantProcessEngineConfiguration, actualAddCustomFunctionProviderResult);
  }

  /**
   * Test {@link ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}.
   *
   * <ul>
   *   <li>Then return {@link JtaProcessEngineConfiguration} (default constructor).
   * </ul>
   *
   * <p>Method under test: {@link
   * ProcessEngineConfigurationImpl#addCustomFunctionProvider(CustomFunctionProvider)}
   */
  @Test
  @Category(ContributionFromDiffblue.class)
  @ManagedByDiffblue
  @MethodsUnderTest({
    "ProcessEngineConfigurationImpl ProcessEngineConfigurationImpl.addCustomFunctionProvider(CustomFunctionProvider)"
  })
  public void testAddCustomFunctionProvider_thenReturnJtaProcessEngineConfiguration() {
    // Arrange
    JtaProcessEngineConfiguration jtaProcessEngineConfiguration =
        new JtaProcessEngineConfiguration();

    // Act
    ProcessEngineConfigurationImpl actualAddCustomFunctionProviderResult =
        jtaProcessEngineConfiguration.addCustomFunctionProvider(mock(CustomFunctionProvider.class));

    // Assert
    assertSame(jtaProcessEngineConfiguration, actualAddCustomFunctionProviderResult);
  }
}
