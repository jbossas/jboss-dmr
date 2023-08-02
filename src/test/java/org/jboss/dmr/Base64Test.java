/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2023 Red Hat, Inc., and individual contributors
 * as indicated by the @author tags.
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

package org.jboss.dmr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class Base64Test {

    @Test
    public void testEncode() {
        try {
            assertEquals("QSB0", Base64.encodeBytes("A t".getBytes()));
            assertEquals("QQ==", Base64.encodeBytes("A".getBytes()));
            assertEquals("QUI=", Base64.encodeBytes("AB".getBytes()));
            assertEquals("QUJD", Base64.encodeBytes("ABC".getBytes()));
            assertEquals("VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZw==",
                    Base64.encodeBytes("The quick brown fox jumps over the lazy dog".getBytes()));
        } catch (final Exception e) {
            fail("IOException not expected.");
        }
    }

    @Test
    public void testDecode() {
        try {
            assertEquals("A t", new String(Base64.decode("QSB0")));
            assertEquals("A", new String(Base64.decode("QQ==")));
            assertEquals("AB", new String(Base64.decode("QUI=")));
            assertEquals("ABC", new String(Base64.decode("QUJD")));
            assertEquals("The quick brown fox jumps over the lazy dog",
                    new String(Base64.decode("VGhlIHF1aWNrIGJyb3duIGZveCBqdW1wcyBvdmVyIHRoZSBsYXp5IGRvZw==")));
        } catch (final Exception e) {
            fail("IOException not expected.");
        }
    }
}
