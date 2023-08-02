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

/**
 * Public API tests of classes in the {@code org.jboss.dmr} package and subpackages.
 * The basic reason for this test package instead of just sticking the tests in the
 * main packages is to have some callers for public methods in
 * a different package so IDEs will stop suggesting we make the public methods
 * package protected.
 *
 * @author Brian Stansberry
 */
package org.jboss.dmr.test;
