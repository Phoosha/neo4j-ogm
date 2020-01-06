/*
 * Copyright (c) 2002-2020 "Neo4j,"
 * Neo4j Sweden AB [http://neo4j.com]
 *
 * This file is part of Neo4j.
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
package org.neo4j.ogm.config;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assume.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.ogm.driver.Driver;
import org.neo4j.ogm.drivers.bolt.driver.BoltDriver;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.testutil.MultiDriverTestClass;

/**
 * @author Frantisek Hartman
 */
public class BoltDriverServiceTest extends MultiDriverTestClass {

    @BeforeClass
    public static void setUp() throws Exception {
        assumeTrue(getBaseConfiguration().build().getDriverClassName().equals(BoltDriver.class.getName()));
    }

    @Test
    public void loadLoadBoltDriver() {
        String uri = getBaseConfiguration().build().getURI();
        Configuration driverConfiguration = new Configuration.Builder().uri(uri).build();
        SessionFactory sf = new SessionFactory(driverConfiguration, "org.neo4j.ogm.domain.social.User");
        Driver driver = sf.getDriver();
        assertThat(driver).isNotNull();
        sf.close();
    }

}
