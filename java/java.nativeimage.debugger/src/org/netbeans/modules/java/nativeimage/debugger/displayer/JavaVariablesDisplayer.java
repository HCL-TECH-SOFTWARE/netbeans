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
package org.netbeans.modules.java.nativeimage.debugger.displayer;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.netbeans.modules.nativeimage.api.debug.EvaluateException;
import org.netbeans.modules.nativeimage.api.debug.NIDebugger;
import org.netbeans.modules.nativeimage.api.debug.NIFrame;
import org.netbeans.modules.nativeimage.api.debug.NIVariable;
import org.netbeans.modules.nativeimage.spi.debug.filters.VariableDisplayer;

/**
 *
 * @author martin
 */
public final class JavaVariablesDisplayer implements VariableDisplayer {

    private static final String HUB = "__hub__";
    private static final String ARRAY = "__array__";
    private static final String ARRAY_LENGTH = "__length__";
    private static final String COMPRESSED_REF_REFIX = "_z_.";
    private static final String PUBLIC = "public";
    private static final String STRING_VALUE = "value";
    private static final String STRING_CODER = "coder";
    private static final String HASH = "hash";
    private static final String NAME = "name";
    private static final String UNSET = "<optimized out>";

    private static final String[] STRING_TYPES = new String[] { String.class.getName(), StringBuilder.class.getName(), StringBuffer.class.getName() };

    // Variable names with this prefix contain space-separated variable name and expression path
    private static final String PREFIX_VAR_PATH = "{ ";

    private NIDebugger debugger;
    private final Map<NIVariable, String> variablePaths = Collections.synchronizedMap(new WeakHashMap<>());

    public JavaVariablesDisplayer() {
    }

    public void setDebugger(NIDebugger debugger) {
        this.debugger = debugger;
    }

    @Override
    public NIVariable[] displayed(NIVariable[] variables) {
        List<NIVariable> displayedVars = new ArrayList<>(variables.length);
        for (NIVariable var : variables) {
            String value = var.getValue();
            if (UNSET.equals(value)) {
                continue;
            }
            if (var instanceof AbstractVar) {
                // Translated already
                displayedVars.add(var);
                continue;
            }
            String name = var.getName();
            String path = null;
            if (name.startsWith(PREFIX_VAR_PATH)) {
                int i = name.indexOf(' ', PREFIX_VAR_PATH.length());
                if (i > 0) {
                    path = name.substring(i + 1);
                    name = name.substring(PREFIX_VAR_PATH.length(), i);
                }
            }
            if (path != null) {
                variablePaths.put(var, path);
            }
            int nch = var.getNumChildren();
            NIVariable displayedVar;
            if (nch == 0) {
                displayedVar = new Var(var, name, path);
            } else {
                NIVariable[] children = var.getChildren();
                NIVariable[] subChildren = children.length > 0 ? children[0].getChildren() : new NIVariable[]{};
                // Check for Array
                if (subChildren.length == 3 &&
                        //HUB.equals(subChildren[0].getName()) &&
                        ARRAY_LENGTH.equals(subChildren[1].getName()) &&
                        ARRAY.equals(subChildren[2].getName())) {
                    displayedVar = new ArrayVar(var, name, path, subChildren[1], subChildren[2]);
                } else {
                    // Check for String
                    String type = getSimpleType(var.getType());
                    boolean isString = STRING_TYPES[0].equals(type);
                    boolean likeString = isString;
                    if (!likeString) {
                        for (String strType : STRING_TYPES) {
                            if (strType.equals(type)) {
                                likeString = true;
                                break;
                            }
                        }
                    }
                    if (likeString) {
                        displayedVar = new StringVar(var, name, path, type, isString ? null : subChildren);
                    } else {
                        if (children.length == 1 && PUBLIC.equals(children[0].getName())) {
                            // Object children
                            displayedVar = new ObjectVar(var, name, path, subChildren);
                        } else {
                            displayedVar = new Var(var, name, path);
                        }
                    }
                }
            }
            if (displayedVar != var) {
                synchronized (variablePaths) {
                    variablePaths.put(displayedVar, variablePaths.get(var));
                }
            }
            displayedVars.add(displayedVar);
        }
        return displayedVars.toArray(new NIVariable[displayedVars.size()]);
    }

    private static String displayType(String type) {
        if (type.startsWith(COMPRESSED_REF_REFIX)) {
            type = type.substring(COMPRESSED_REF_REFIX.length());
        }
        if (type.endsWith("*")) {
            type = type.substring(0, type.length() - 1).trim();
        }
        return type;
    }

    private static String getSimpleType(String type) {
        type = displayType(type);
        for (int i = 0; i < type.length(); i++) {
            char c = type.charAt(i);
            if (c != '.' && !Character.isJavaIdentifierPart(c)) {
                return type.substring(0, i);
            }
        }
        return type;
    }

    private static boolean isOfType(String varType, String type) {
        varType = displayType(varType);
        return varType.equals(type) || varType.startsWith(type) && !Character.isJavaIdentifierPart(varType.charAt(type.length()));
    }

    private static int getTypeSize(String type) {
        type = getSimpleType(type);
        switch (type) {
            case "boolean":
            case "byte":
                return 1;
            case "char":
            case "short":
                return 2;
            case "int":
            case "float":
                return 4;
            case "long":
            case "double":
                return 8;
            default:
                return 8;
        }
    }

    private static Map<String, NIVariable> getVarsByName(NIVariable[] vars) {
        switch (vars.length) {
            case 0:
                return Collections.emptyMap();
            case 1:
                return Collections.singletonMap(vars[0].getName(), vars[0]);
            default:
                Map<String, NIVariable> varsByName = new HashMap<>(vars.length);
                for (NIVariable var : vars) {
                    varsByName.put(var.getName(), var);
                }
                return varsByName;
        }
    }

    private static NIVariable[] restrictChildren(NIVariable[] children, int from, int to) {
        if (from > 0 || to < children.length) {
            to = Math.min(to, children.length);
            if (from < to) {
                children = Arrays.copyOfRange(children, from, to);
            } else {
                children = new NIVariable[]{};
            }
        }
        return children;
    }

    private static String getHash(NIVariable[] children) {
        for (NIVariable child : children) {
            if (HASH.equals(child.getName())) {
                String hash = child.getValue();
                try {
                    hash = Integer.toHexString(Integer.parseInt(hash));
                } catch (NumberFormatException ex) {}
                return hash;
            }
        }
        return null;
    }

    private static String getArrayExpression(NIVariable variable) {
        StringBuilder arrayExpression = new StringBuilder(variable.getName());
        while ((variable = variable.getParent()) != null) {
            if (!PUBLIC.equals(variable.getName())) {
                arrayExpression.insert(0, '.');
                arrayExpression.insert(0, variable.getName());
            }
        }
        return arrayExpression.toString();
    }

    private static String getNameOrIndex(String name) {
        if (name.endsWith("]")) {
            int i = name.lastIndexOf(ARRAY+"[");
            if (i > 0) {
                String index = name.substring(i + ARRAY.length() + 1, name.length() - 1);
                return index;
            }
        }
        return name;
    }

    private String readArray(NIVariable lengthVariable, int itemSize) {
        int length = Integer.parseInt(lengthVariable.getValue());
        String expressionPath = getExpressionPath(lengthVariable);
        if (expressionPath != null && !expressionPath.isEmpty()) {
            String addressExpr = "&" + expressionPath;
            return debugger.readMemory(addressExpr, 4, length * itemSize); // length has 4 bytes
        }
        return null;
    }

    private String readArray(NIVariable lengthVariable, int offset, int itemSize) {
        int length = Integer.parseInt(lengthVariable.getValue());
        String expressionPath = getExpressionPath(lengthVariable);
        if (expressionPath != null && !expressionPath.isEmpty()) {
            String addressExpr = "&" + expressionPath;
            return debugger.readMemory(addressExpr, 4 + offset, length * itemSize); // length has 4 bytes
        }
        return null;
    }

    private static NIVariable[] getObjectChildren(NIVariable[] children, int from, int to) {
        for (int i = 0; i < children.length; i++) {
            if (HUB.equals(children[i].getName())) {
                NIVariable[] ch1 = Arrays.copyOf(children, i);
                NIVariable[] ch2 = Arrays.copyOfRange(children, i + 1, children.length);
                if (ch1.length == 0) {
                    return ch2;
                }
                if (ch2.length == 0) {
                    return ch1;
                }
                NIVariable[] ch = new NIVariable[ch1.length + ch2.length];
                System.arraycopy(ch1, 0, ch, 0, ch1.length);
                System.arraycopy(ch2, 0, ch, ch1.length, ch2.length);
                return ch;
            }
        }
        return restrictChildren(children, from, to);
    }

    private static NIVariable findChild(String name, NIVariable[] children) {
        for (NIVariable var : children) {
            if (name.equals(var.getName())) {
                return var;
            }
        }
        return null;
    }

    private static boolean isPrimitiveArray(String type) {
        int i = type.indexOf(' ');
        if (i < 0) {
            return false;
        }
        String name = type.substring(0, i);
        switch (name) {
            case "boolean":
            case "byte":
            case "char":
            case "short":
            case "int":
            case "long":
            case "float":
            case "double":
                return true;
            default:
                return false;
        }
    }

    private String getExpressionPath(NIVariable var) {
        String path = var.getExpressionPath();
        if (!path.isEmpty()) {
            return path;
        } else {
            return createExpressionPath(var);
        }
    }

    /** Used as a fallback when {@link NIVariable#getExpressionPath()} does not provide anything. */
    private String createExpressionPath(NIVariable var) {
        String path = variablePaths.get(var);
        if (path != null) {
            return path;
        }
        path = var.getName();
        NIVariable parent = var.getParent();
        if (parent == null) {
            return path;
        } else {
            String parentPath = createExpressionPath(parent);
            if (PUBLIC.equals(path)) {
                return parentPath;
            } else {
                return parentPath + '.' + path;
            }
        }
    }

    private class StringVar extends AbstractVar {

        private final String type;
        private final NIVariable[] children;

        StringVar(NIVariable var, String name, String path, String type, NIVariable[] children) {
            super(var, name, path);
            this.type = type;
            this.children = children;
        }

        @Override
        public NIFrame getFrame() {
            return var.getFrame();
        }

        @Override
        public String getType() {
            return type;
        }

        @Override
        public String getValue() {
            NIVariable pub = getVarsByName(var.getChildren()).get(PUBLIC);
            Map<String, NIVariable> varChildren = getVarsByName(pub.getChildren());
            Map<String, NIVariable> arrayInfo = getVarsByName(varChildren.get(STRING_VALUE).getChildren());
            arrayInfo = getVarsByName(arrayInfo.get(PUBLIC).getChildren());
            NIVariable arrayVariable = arrayInfo.get(ARRAY);
            NIVariable lengthVariable = arrayInfo.get(ARRAY_LENGTH);
            String lengthStr = lengthVariable.getValue();
            if (lengthStr.isEmpty()) {
                return "";
            }
            int length = Integer.parseInt(lengthStr);
            if (length <= 0) {
                return "";
            }
            NIVariable coderVar = varChildren.get(STRING_CODER);
            int coder = -1;
            if (coderVar != null) {
                String coderStr = coderVar.getValue();
                int space = coderStr.indexOf(' ');
                if (space > 0) {
                    coderStr = coderStr.substring(0, space);
                }
                try {
                    coder = Integer.parseInt(coderStr);
                } catch (NumberFormatException ex) {
                }
            }
            String hexArray = readArray(lengthVariable, 0, 2);
            if (hexArray != null) {
                switch (coder) {
                    case 0: // Compressed String on JDK 9+
                        return parseLatin1(hexArray, length);
                    case 1: // UTF-16 String on JDK 9+
                        return parseUTF16(hexArray, length/2);
                    default: // UTF-16 String on JDK 8
                        return parseUTF16(hexArray, length);
                }
            } else { // legacy code for older gdb version (less than 10.x)
                String arrayExpression = JavaVariablesDisplayer.this.getExpressionPath(arrayVariable); //getArrayExpression(arrayVariable);
                NIFrame frame = var.getFrame();
                try {
                    NIVariable charVar = debugger.evaluate(arrayExpression + "[0]", null, frame);
                    if ("byte".equals(charVar.getType())) {
                        // bytes to be parsed to String
                        switch (coder) {
                            case 0: // Compressed String on JDK 9+
                                return parseLatin1(arrayExpression, frame, length);
                            case 1: // UTF-16 String on JDK 9+
                                return parseUTF16(arrayExpression, frame, length/2);
                            default: // UTF-16 String on JDK 8
                                return parseUTF16(arrayExpression, frame, length);
                        }
                    } else {
                        char[] characters = new char[length];
                        for(int i = 0; ; ) {
                            String charStr = charVar.getValue();
                            characters[i] = parseCharacter(charStr);
                            if (++i >= length) {
                                break;
                            }
                            charVar = debugger.evaluate(arrayExpression + "[" + i + "]", null, frame);
                        }
                        return new String(characters);
                    }
                } catch (EvaluateException ex) {
                    return ex.getLocalizedMessage();
                }
            }
        }

        private char parseCharacter(String charValue) {
            if (charValue.startsWith("'") && charValue.endsWith("'")) {
                charValue = charValue.substring(1, charValue.length() - 1);
            }
            byte b0, b1;
            if (charValue.startsWith("\\\\x")) {
                // hexadecimal
                b1 = parseByte(charValue, 3);
                b0 = parseByte(charValue, 5);
            } else {
                int[] pos = new int[] {0};
                try {
                    b1 = getByteFromChar(charValue, pos);
                    charValue = charValue.substring(pos[0]);
                    b0 = getByteFromChar(charValue, pos);
                } catch (NumberFormatException nfex) {
                    // octal does not fit into byte
                    try {
                        return (char) Short.parseShort(charValue.substring(2), 8);
                    } catch (NumberFormatException ex) {
                        return charValue.charAt(0);
                    }
                }
            }
            CharsetDecoder cd = Charset.forName("utf-16").newDecoder(); // NOI18N
            byte[] bytes = new byte[] {b1, b0};
            ByteBuffer buffer = ByteBuffer.allocate(2);
            buffer.rewind();
            buffer.put(b0);
            buffer.put(b1);
            buffer.rewind();
            try {
                char c = cd.decode(buffer).get();
                return c;
            } catch (CharacterCodingException ex) {
                return charValue.charAt(0);
            }
        }

        private byte getByteFromChar(String charValue, int[] pos) {
            if (charValue.startsWith("\\\\")) {
                pos[0] += 2;
                char c = charValue.charAt(2);
                if (Character.isDigit(c)) {
                    // octal
                    pos[0] += 3;
                    return Byte.parseByte(charValue.substring(2, 5), 8);
                }
                pos[0]++;
                switch (c) {
                    case 'a': return 7; // BEL
                    case 'b': return 8; // Backspace
                    case 't': return 9; // TAB
                    case 'n': return 10; // LF
                    case 'v': return 11; // VT
                    case 'f': return 12; // FF
                    case 'r': return 13; // CR
                    case '\\':
                        pos[0]++; // Two back slashes
                        return (byte) c;
                    default:
                        return (byte) c;
                }
            } else {
                pos[0] += 1;
                return (byte) charValue.charAt(0);
            }
        }

        private String parseUTF16(String hexArray, int length) {
            CharsetDecoder cd = Charset.forName("utf-16").newDecoder(); // NOI18N
            ByteBuffer buffer = ByteBuffer.allocate(2);
            char[] characters = new char[length];
            int ih = 0;
            for (int i = 0; i < length; i++) {
                byte b1 = parseByte(hexArray, ih);
                ih += 2;
                byte b0 = parseByte(hexArray, ih);
                ih += 2;
                buffer.rewind();
                buffer.put(b0);
                buffer.put(b1);
                buffer.rewind();
                try {
                    char c = cd.decode(buffer).get();
                    characters[i] = c;
                } catch (CharacterCodingException ex) {
                }
            }
            return new String(characters);
        }

        private String parseLatin1(String hexArray, int length) {
            CharsetDecoder cd = Charset.forName("latin1").newDecoder(); // NOI18N
            ByteBuffer buffer = ByteBuffer.allocate(1);
            char[] characters = new char[length];
            int ih = 0;
            for (int i = 0; i < length; i++) {
                byte b = parseByte(hexArray, ih);
                ih += 2;
                buffer.rewind();
                buffer.put(b);
                buffer.rewind();
                try {
                    char c = cd.decode(buffer).get();
                    characters[i] = c;
                } catch (CharacterCodingException ex) {
                }
            }
            return new String(characters);
        }

        private byte parseByte(String hexArray, int offset) {
            String hex = new String(new char[] {hexArray.charAt(offset), hexArray.charAt(offset + 1)});
            return (byte) (Integer.parseInt(hex, 16) & 0xFF);
        }

        private String parseUTF16(String arrayExpression, NIFrame frame, int length) throws EvaluateException {
            CharsetDecoder cd = Charset.forName("utf-16").newDecoder(); // NOI18N
            ByteBuffer buffer = ByteBuffer.allocate(2);
            char[] characters = new char[length];
            for (int i = 0; i < length; i++) {
                NIVariable byteVar = debugger.evaluate(arrayExpression + "[" + (2*i) + "]", null, frame);
                byte b1 = Byte.parseByte(byteVar.getValue());
                byteVar = debugger.evaluate(arrayExpression + "[" + (2*i+1) + "]", null, frame);
                byte b0 = Byte.parseByte(byteVar.getValue());
                buffer.rewind();
                buffer.put(b0);
                buffer.put(b1);
                buffer.rewind();
                try {
                    char c = cd.decode(buffer).get();
                    characters[i] = c;
                } catch (CharacterCodingException ex) {
                }
            }
            return new String(characters);
        }

        private String parseLatin1(String arrayExpression, NIFrame frame, int length) throws EvaluateException {
            CharsetDecoder cd = Charset.forName("latin1").newDecoder(); // NOI18N
            ByteBuffer buffer = ByteBuffer.allocate(1);
            char[] characters = new char[length];
            for (int i = 0; i < length; i++) {
                NIVariable byteVar = debugger.evaluate(arrayExpression + "[" + i + "]", null, frame);
                byte b = Byte.parseByte(byteVar.getValue());
                buffer.rewind();
                buffer.put(b);
                buffer.rewind();
                try {
                    char c = cd.decode(buffer).get();
                    characters[i] = c;
                } catch (CharacterCodingException ex) {
                }
            }
            return new String(characters);
        }

        @Override
        public int getNumChildren() {
            return children != null ? children.length : 0;
        }

        @Override
        public NIVariable[] getChildren(int from, int to) {
            return children != null ? getObjectChildren(children, from, to) : new NIVariable[]{};
        }

        @Override
        public NIVariable getParent() {
            return var.getParent();
        }

    }

    private class ArrayVar extends AbstractVar {

        private final NIVariable lengthVariable;
        private final int length;
        private final NIVariable array;

        ArrayVar(NIVariable var, String name, String path, NIVariable lengthVariable, NIVariable array) {
            super(var, name, path);
            this.lengthVariable = lengthVariable;
            int arrayLength;
            try {
                arrayLength = Integer.parseInt(lengthVariable.getValue());
            } catch (NumberFormatException ex) {
                arrayLength = 0;
            }
            this.length = arrayLength;
            this.array = array;
        }

        @Override
        public NIFrame getFrame() {
            return var.getFrame();
        }

        @Override
        public NIVariable getParent() {
            return var.getParent();
        }

        @Override
        public String getType() {
            return displayType(var.getType());
        }

        @Override
        public String getValue() {
            String value = var.getValue();
            if (value.startsWith("@")) {
                value = getType() + value;
            }
            return value + "(length="+length+")";
        }

        @Override
        public int getNumChildren() {
            return length;
        }

        @Override
        public NIVariable[] getChildren(int from, int to) {
            if (from >= 0) {
                to = Math.min(to, length);
            } else {
                from = 0;
                to = length;
            }
            if (from >= to) {
                return new NIVariable[]{};
            }

            String arrayAddress = null;
            if (isPrimitiveArray(array.getType())) {
                String expressionPath = JavaVariablesDisplayer.this.getExpressionPath(lengthVariable);
                if (expressionPath != null && !expressionPath.isEmpty()) {
                    String addressExpr = "&" + expressionPath;
                    NIVariable addressVariable;
                    try {
                        addressVariable = debugger.evaluate(addressExpr, null, lengthVariable.getFrame());
                    } catch (EvaluateException ex) {
                        addressVariable = null;
                    }
                    if (addressVariable != null) {
                        String address = addressVariable.getValue();
                        address = address.toLowerCase();
                        if (address.startsWith("0x")) {
                            arrayAddress = address;
                        }
                    }
                }
            }
            NIVariable[] elements = new NIVariable[to - from];
            try {
                if (arrayAddress != null) {
                    int offset = (getTypeSize(getType()) == 8) ? 8 : 4;
                    String itemExpression = "*(((" + getSimpleType(getType()) + "*)(" + arrayAddress + "+"+offset+"))+";
                    int size = getTypeSize(getType());
                    for (int i = from; i < to; i++) {
                        String expr = itemExpression + i + ")";
                        NIVariable element = debugger.evaluate(expr, Integer.toString(i), var.getFrame());
                        // When gdb could retrieve variable address, it did resolve the expression path.
                        // Thus there is no need to remember it in variablePaths.
                        elements[i - from] = element;
                    }
                } else {
                    String arrayExpression = JavaVariablesDisplayer.this.getExpressionPath(array);
                    for (int i = from; i < to; i++) {
                        String expr = arrayExpression + "[" + i + "]";
                        String namePath = PREFIX_VAR_PATH + Integer.toString(i) + ' ' + expr;
                        NIVariable element = debugger.evaluate(expr, namePath, var.getFrame());
                        variablePaths.put(element, expr);
                        elements[i - from] = element;
                    }
                }
            } catch (EvaluateException ex) {
                return new NIVariable[]{};
            }
            return elements;
        }

    }

    private class ObjectVar extends AbstractVar {

        private final NIVariable[] children;

        ObjectVar(NIVariable var, String name, String path, NIVariable[] children) {
            super(var, name, path);
            this.children = children;
        }

        @Override
        public NIFrame getFrame() {
            return var.getFrame();
        }

        @Override
        public NIVariable getParent() {
            return var.getParent();
        }

        @Override
        public String getType() {
            return displayType(findRuntimeType());
        }

        @Override
        public String getValue() {
            String value = var.getValue();
            if (value.startsWith("@") || value.startsWith("0x")) {
                String hash = getHash(children);
                if (hash == null) {
                    if (value.startsWith("@")) {
                        hash = value.substring(1);
                    } else {
                        hash = value.substring(2);
                    }
                }
                value = getType() + '@' + hash;
            }
            return value;
        }

        @Override
        public int getNumChildren() {
            return children.length;
        }

        @Override
        public NIVariable[] getChildren(int from, int to) {
            return getObjectChildren(children, from, to);
        }

        private String findRuntimeType() {
            NIVariable hub = findChild(HUB, children);
            if (hub != null) {
                NIVariable pub = findChild(PUBLIC, hub.getChildren());
                NIVariable nameVar = findChild(NAME, pub.getChildren());
                if (nameVar != null) {
                    String name = new StringVar(nameVar, varName, varPath, null, null).getValue();
                    if (!name.isEmpty()) {
                        return name;
                    }
                }
            }
            return var.getType();
        }
    }

    private class Var extends AbstractVar {

        Var(NIVariable var, String name, String path) {
            super(var, name, path);
        }

        @Override
        public NIFrame getFrame() {
            return var.getFrame();
        }

        @Override
        public NIVariable getParent() {
            return var.getParent();
        }

        @Override
        public String getType() {
            return var.getType();
        }

        @Override
        public String getValue() {
            return var.getValue();
        }

        @Override
        public int getNumChildren() {
            return var.getNumChildren();
        }

        @Override
        public NIVariable[] getChildren(int from, int to) {
            return var.getChildren(from, to);
        }

        @Override
        public NIVariable[] getChildren() {
            return var.getChildren();
        }

    }

    private abstract class AbstractVar implements NIVariable {

        protected final NIVariable var;
        protected final String varName;
        protected final String varPath;

        AbstractVar(NIVariable var, String varName, String varPath) {
            this.var = var;
            this.varName = varName;
            this.varPath = varPath;
        }

        @Override
        public final String getName() {
            if (varName != null) {
                return varName;
            }
            return getNameOrIndex(var.getName());
        }

        @Override
        public final String getExpressionPath() {
            if (varPath != null) {
                return varPath;
            } else {
                String path = var.getExpressionPath();
                if (!path.isEmpty()) {
                    return path;
                } else {
                    path = variablePaths.get(var);
                    return path != null ? path : getName();
                }
            }
        }
    }
}
