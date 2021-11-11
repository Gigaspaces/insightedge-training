/*
 * Copyright (c) 2008-2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gs;

import com.gigaspaces.internal.server.space.tiered_storage.TieredStorageConfig;
import com.gigaspaces.internal.server.space.tiered_storage.TieredStorageTableConfig;
import com.gs.Data1;
import com.gs.Data2;
import com.gs.Data4;
import org.hibernate.SessionFactory;
import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoContext;
import org.openspaces.core.config.annotation.EmbeddedSpaceBeansConfig;
import org.openspaces.core.space.EmbeddedSpaceFactoryBean;
import org.openspaces.persistency.hibernate.DefaultHibernateSpaceDataSourceConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CustomSpaceConfig extends EmbeddedSpaceBeansConfig {

	@ClusterInfoContext
	private ClusterInfo clusterInfo;


    @Override
    protected void configure(EmbeddedSpaceFactoryBean factoryBean) {
		super.configure(factoryBean);
		Properties properties = new Properties();
        properties.setProperty("space-config.engine.cache_policy", "1");
        factoryBean.setProperties(properties);

		TieredStorageConfig tieredStorageConfig = new TieredStorageConfig();
		Map<String, TieredStorageTableConfig> tables = new HashMap<>();
		tables.put(Data1.class.getName(), new TieredStorageTableConfig().setName(Data1.class.getName()).setCriteria("type='4'"));
		tables.put(Data2.class.getName(), new TieredStorageTableConfig().setName(Data2.class.getName()).setTransient(true));
		tables.put(Data4.class.getName(), new TieredStorageTableConfig().setName(Data4.class.getName()).setCriteria("all"));
		tables.put("MyTestClass", new TieredStorageTableConfig().setName("MyTestClass").setCriteria("all"));
		tieredStorageConfig.setTables(tables);
		factoryBean.setTieredStorageConfig(tieredStorageConfig);

	}


}
