/*
 * Licensed to ElasticSearch and Shay Banon under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. ElasticSearch licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.elasticsearch.index.query;

import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

/**
 * Implements the wildcard search filter.
 * Note this filter can be a bit slow, as it needs to iterate over a number of terms.
 */
public class HashSplitterWildcardFilterBuilder extends BaseFilterBuilder {

    private final String name;

    private final String wildcard;

    private Boolean cache;
    private String cacheKey;

    private String filterName;

    public static HashSplitterWildcardFilterBuilder hashSplitterWildcardFilter(String name, String value) {
        return new HashSplitterWildcardFilterBuilder(name, value);
    }

    /**
     * Implements the wildcard search filter.
     * Note this filter can be a bit slow, as it needs to iterate over a number of terms.
     *
     * @param name     The field name
     * @param wildcard The wildcard filter string
     */
    public HashSplitterWildcardFilterBuilder(String name, String wildcard) {
        this.name = name;
        this.wildcard = wildcard;
    }

    /**
     * Sets the filter name for the filter that can be used when searching for matched_filters per hit.
     */
    public HashSplitterWildcardFilterBuilder filterName(String filterName) {
        this.filterName = filterName;
        return this;
    }

    /**
     * Should the filter be cached or not. Defaults to <tt>true</tt>.
     */
    public HashSplitterWildcardFilterBuilder cache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public HashSplitterWildcardFilterBuilder cacheKey(String cacheKey) {
        this.cacheKey = cacheKey;
        return this;
    }

    @Override
    public void doXContent(XContentBuilder builder, Params params) throws IOException {
        builder.startObject(HashSplitterWildcardFilterParser.NAME);
        builder.field(name, wildcard);
        if (filterName != null) {
            builder.field("_name", filterName);
        }
        if (cache != null) {
            builder.field("_cache", cache);
        }
        if (cacheKey != null) {
            builder.field("_cache_key", cacheKey);
        }
        builder.endObject();
    }

}
