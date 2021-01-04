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
package org.neo4j.ogm.drivers.bolt.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.types.Entity;
import org.neo4j.driver.v1.types.Node;
import org.neo4j.driver.v1.types.Path;
import org.neo4j.driver.v1.types.Relationship;

/**
 * Helper methods for Bolt entities
 *
 * @author Luanne Misquitta
 */
class BoltEntityAdapter {

    boolean isPath(Object value) {
        return value instanceof Path;
    }

    boolean isNode(Object value) {
        return value instanceof Node;
    }

    boolean isRelationship(Object value) {
        return value instanceof Relationship;
    }

    public long nodeId(Object node) {
        return ((Node) node).id();
    }

    public List<String> labels(Object value) {
        Node node = (Node) value;
        List<String> labels = new ArrayList<>();
        for (String label : node.labels()) {
            labels.add(label);
        }
        return labels;
    }

    public long relationshipId(Object relationship) {
        return ((Relationship) relationship).id();
    }

    public String relationshipType(Object relationship) {
        return ((Relationship) relationship).type();
    }

    public Long startNodeId(Object relationship) {
        return ((Relationship) relationship).startNodeId();
    }

    public Long endNodeId(Object relationship) {
        return ((Relationship) relationship).endNodeId();
    }

    public Map<String, Object> properties(Object container) {
        return ((Entity) container).asMap();
    }

    List<Object> nodesInPath(Object pathValue) {
        Path path = (Path) pathValue;
        List<Object> nodes = new ArrayList<>(path.length());
        for (Node node : path.nodes()) {
            nodes.add(node);
        }
        return nodes;
    }

    List<Object> relsInPath(Object pathValue) {
        Path path = (Path) pathValue;
        List<Object> rels = new ArrayList<>(path.length());
        for (Relationship rel : path.relationships()) {
            rels.add(rel);
        }
        return rels;
    }
}
