/*
 * Copyright (c) 2002-2021 "Neo4j,"
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
package org.neo4j.ogm.drivers.embedded.response;

import java.util.Map;

import org.neo4j.graphdb.Result;
import org.neo4j.ogm.model.RowModel;
import org.neo4j.ogm.result.adapter.ResultAdapter;

/**
 * @author Vince Bickers
 */
public class RowModelResponse extends EmbeddedResponse<RowModel> {

    private final ResultAdapter<Map<String, Object>, RowModel> adapter = new EmbeddedRowModelAdapter();

    public RowModelResponse(Result result) {
        super(result);
        ((EmbeddedRowModelAdapter) adapter).setColumns(result.columns());
    }

    @Override
    public RowModel next() {
        if (result.hasNext()) {
            return adapter.adapt(result.next());
        }
        return null;
    }
}
