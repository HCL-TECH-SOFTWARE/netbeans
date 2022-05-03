/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.j2ee.persistenceapi;

import org.netbeans.api.project.FileOwnerQuery;
import org.netbeans.api.project.Project;
import org.netbeans.modules.j2ee.persistence.api.PersistenceScope;
import org.netbeans.modules.j2ee.persistence.spi.PersistenceScopeProvider;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Andrei Badea
 */
@org.openide.util.lookup.ServiceProvider(service=org.netbeans.modules.j2ee.persistence.spi.PersistenceScopeProvider.class)
public class ProjectPersistenceScopeProvider implements PersistenceScopeProvider {

    public PersistenceScope findPersistenceScope(FileObject fo) {
        Project project = FileOwnerQuery.getOwner(fo);
        if (project != null) {
            PersistenceScopeProvider provider = project.getLookup().lookup(PersistenceScopeProvider.class);
            if (provider != null) {
                return provider.findPersistenceScope(fo);
            }
        }
        return null;
    }
}
