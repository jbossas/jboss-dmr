/*
Copyright 2017 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package org.jboss.dmr.test;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.stream.ModelReader;
import org.jboss.dmr.stream.ModelStreamFactory;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author <a href="mailto:ropalka@redhat.com">Richard Opalka</a>
 */
public final class RoundTripTest {

    private static final String[] DMR_MODELS = new String[] {
            "/JBoss-AS7-7.1.1.Final.dmr",
            "/JBoss-EAP-6.0.0.Final.dmr",
            "/JBoss-EAP-6.0.1.Final.dmr",
            "/JBoss-EAP-6.1.0.Final.dmr",
            "/JBoss-EAP-6.1.1.Final.dmr",
            "/JBoss-EAP-6.2.0.Final.dmr",
            "/JBoss-EAP-6.3.0.Final.dmr",
            "/JBoss-EAP-6.4.0.Final.dmr",
            "/Wildfly-10.0.0.Final.dmr",
            "/Wildfly-8.0.0.Final.dmr",
            "/Wildfly-8.1.0.Final.dmr",
            "/Wildfly-8.2.0.Final.dmr",
            "/Wildfly-9.0.0.Final.dmr",
    };

    private static byte[] getDmrModel(final String resourceName, final boolean jsonEncoding, final boolean compact) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ModelNode node = new ModelNode();
        node.readExternal(RoundTripTest.class.getResource(resourceName).openStream());
        if (jsonEncoding) {
            PrintWriter pw = new PrintWriter(baos);
            node.writeJSONString(pw, compact);
            pw.flush();
        } else {
            PrintWriter pw = new PrintWriter(baos);
            node.writeString(pw, compact);
            pw.flush();
        }
        return baos.toByteArray();
    }

    @Test
    public void test_writeDmrInCompactMode_readViaStreamAPI() throws Exception {
        byte[] data;
        System.out.println("Write DMR in compact mode - read via Stream API");
        for (final String dmrModel : DMR_MODELS) {
            System.out.println(" * processing model: " + dmrModel);
            data = getDmrModel(dmrModel, false, true);
            ModelReader reader = ModelStreamFactory.getInstance(false).newModelReader(new ByteArrayInputStream(data));
            while (reader.hasNext()) reader.next();
        }
    }

    @Test
    public void test_writeDmrInCompactMode_readViaModelAPI() throws Exception {
        byte[] data;
        System.out.println("Write DMR in compact mode - read via Model API");
        for (final String dmrModel : DMR_MODELS) {
            System.out.println(" * processing model: " + dmrModel);
            data = getDmrModel(dmrModel, false, true);
            ModelNode.fromStream(new ByteArrayInputStream(data));
        }
    }

    @Test
    public void test_writeDmrInPrettyMode_readViaStreamAPI() throws Exception {
        byte[] data;
        System.out.println("Write DMR in pretty mode - read via Stream API");
        for (final String dmrModel : DMR_MODELS) {
            System.out.println(" * processing model: " + dmrModel);
            data = getDmrModel(dmrModel, false, false);
            ModelReader reader = ModelStreamFactory.getInstance(false).newModelReader(new ByteArrayInputStream(data));
            while (reader.hasNext()) reader.next();
        }
    }

    @Test
    public void test_writeDmrInPrettyMode_readViaModelAPI() throws Exception {
        byte[] data;
        System.out.println("Write DMR in pretty mode - read via Model API");
        for (final String dmrModel : DMR_MODELS) {
            System.out.println(" * processing model: " + dmrModel);
            data = getDmrModel(dmrModel, false, false);
            ModelNode.fromStream(new ByteArrayInputStream(data));
        }
    }

    @Test
    public void test_writeJsonInCompactMode_readViaStreamAPI() throws Exception {
        byte[] data;
        System.out.println("Write JSON in compact mode - read via Stream API");
        for (final String dmrModel : DMR_MODELS) {
            System.out.println(" * processing model: " + dmrModel);
            data = getDmrModel(dmrModel, true, true);
            ModelReader reader = ModelStreamFactory.getInstance(true).newModelReader(new ByteArrayInputStream(data));
            while (reader.hasNext()) reader.next();
        }
    }

    @Test
    public void test_writeJsonInCompactMode_readViaModelAPI() throws Exception {
        byte[] data;
        System.out.println("Write JSON in compact mode - read via Model API");
        for (final String dmrModel : DMR_MODELS) {
            System.out.println(" * processing model: " + dmrModel);
            data = getDmrModel(dmrModel, true, true);
            ModelNode.fromJSONStream(new ByteArrayInputStream(data));
        }
    }

    @Test
    public void test_writeJSONInPrettyMode_readViaStreamAPI() throws Exception {
        byte[] data;
        System.out.println("Write JSON in pretty mode - read via Stream API");
        for (final String dmrModel : DMR_MODELS) {
            System.out.println(" * processing model: " + dmrModel);
            data = getDmrModel(dmrModel, true, false);
            ModelReader reader = ModelStreamFactory.getInstance(true).newModelReader(new ByteArrayInputStream(data));
            while (reader.hasNext()) reader.next();
        }
    }

    @Test
    public void test_writeJsonInPrettyMode_readViaModelAPI() throws Exception {
        byte[] data;
        System.out.println("Write JSON in pretty mode - read via Model API");
        for (final String dmrModel : DMR_MODELS) {
            System.out.println(" * processing model: " + dmrModel);
            data = getDmrModel(dmrModel, true, false);
            ModelNode.fromJSONStream(new ByteArrayInputStream(data));
        }
    }

}
