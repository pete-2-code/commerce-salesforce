/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2019 Adobe Systems Incorporated and others
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

package com.adobe.cq.commerce.demandware.replication.content.attributemapping.converter;

import com.adobe.cq.commerce.demandware.replication.content.attributemapping.AbstractAttributeToJsonConverter;
import com.adobe.cq.commerce.demandware.replication.content.attributemapping.AttributeDescriptor;
import com.adobe.cq.commerce.demandware.replication.content.attributemapping.AttributeToJsonConverter;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.wcm.api.Page;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.commons.json.JSONArray;
import org.osgi.service.component.annotations.Component;

import java.util.Arrays;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

@Component(service = AttributeToJsonConverter.class, immediate = true,
        property = {
                "service.ranking:Integer=1000"
        })
public class StringArrayAttributeToJsonConverter extends AbstractAttributeToJsonConverter {

    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    @Override
    protected Object getValue(AttributeDescriptor attr, Page page, HierarchyNodeInheritanceValueMap properties) {
        String[] values = properties.get(attr.getSourceName(), String[].class);
        if(ArrayUtils.isEmpty(values)) {
            values = StringUtils.isEmpty(attr.getDefaultValue())
                    ? EMPTY_STRING_ARRAY
                    : new String[] { attr.getDefaultValue() };
        }
        return new JSONArray(Arrays.asList(values));
    }

    @Override
    public boolean canHandle(AttributeDescriptor attr, Page page, HierarchyNodeInheritanceValueMap properties) {
        return super.canHandle(attr, page, properties)
                && equalsIgnoreCase("[string]", attr.getConverterId());
    }
}
