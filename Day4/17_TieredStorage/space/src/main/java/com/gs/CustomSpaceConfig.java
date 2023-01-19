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

import org.openspaces.core.cluster.ClusterInfo;
import org.openspaces.core.cluster.ClusterInfoContext;
import org.openspaces.core.config.annotation.EmbeddedSpaceBeansConfig;
import org.openspaces.core.space.EmbeddedSpaceFactoryBean;
import org.openspaces.core.space.TieredStorageCachePolicy;
//import org.openspaces.core.space.TieredStorageCachePolicy;

import java.time.Duration;
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
        //properties.setProperty("space-config.engine.cache_policy", "4"); //for 16.2+
		properties.setProperty("space-config.engine.query.result.size.limit", "400");
		properties.setProperty("space-config.engine.query.result.size.limit.memory.check.batch.size", "250");
		properties.setProperty("cluster-config.groups.group.repl-policy.sync-replication.target-consume-timeout", "500");
		properties.setProperty("space-config.leader-election.zookeeper.connection-timeout", "30000");
		properties.setProperty("space-config.leader-election.zookeeper.session-timeout", "90000");
		factoryBean.setProperties(properties);

		TieredStorageConfig tieredStorageConfig = new TieredStorageConfig();
//16.1.1
		//Criteria per table can be defined in space configuration or when registering the type
		Map<String, TieredStorageTableConfig> tables = new HashMap<>();
		tables.put("MyTestClass", new TieredStorageTableConfig().setName("MyTestClass").setCriteria("all"));
		tables.put("Doc1", new TieredStorageTableConfig().setName("Doc1").setCriteria("type='4'"));
		tables.put("Doc2", new TieredStorageTableConfig().setName("Doc2").setTransient(true));
		tables.put("Doc4", new TieredStorageTableConfig().setName("Doc4").setCriteria("all"));
		tables.put("Doc5", new TieredStorageTableConfig().setName("Doc5").setTimeColumn("expire").setPeriod( Duration.ofHours(24)));

		tieredStorageConfig.setTables(tables);
		factoryBean.setTieredStorageConfig(tieredStorageConfig);

//16.2
	//	tieredStorageConfig.addTable(new TieredStorageTableConfig().setName("Doc1").setCriteria("type='4'"));
	//	tieredStorageConfig.addTable(new TieredStorageTableConfig().setName("Doc2").setTransient(true));
	//	tieredStorageConfig.addTable(new TieredStorageTableConfig().setName("Doc4").setCriteria("all"));
	//	tieredStorageConfig.addTable(new TieredStorageTableConfig().setName("Doc5").setTimeColumn("expire").setPeriod( Duration.ofHours(1)));
	//	factoryBean.setCachePolicy(new TieredStorageCachePolicy(tieredStorageConfig));


	}


}
