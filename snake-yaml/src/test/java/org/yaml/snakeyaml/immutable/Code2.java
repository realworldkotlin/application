/**
 * Copyright (c) 2008, http://www.snakeyaml.org
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yaml.snakeyaml.immutable;

/**
 * Two constructors with 1 argument. These immutable objects are not supported.
 */
public class Code2 {
    private final Integer code;

    public Code2(Integer name) {
        this.code = name;
    }

    public Code2(String name) {
        this.code = new Integer(name);
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Code2) {
            Code2 code = (Code2) obj;
            return code.equals(code.code);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return "<Code2 code=" + code + ">";
    }
}
