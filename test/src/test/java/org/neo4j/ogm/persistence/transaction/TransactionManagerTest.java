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
package org.neo4j.ogm.persistence.transaction;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.neo4j.ogm.exception.core.TransactionManagerException;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.session.transaction.DefaultTransactionManager;
import org.neo4j.ogm.testutil.MultiDriverTestClass;
import org.neo4j.ogm.transaction.Transaction;

/**
 * Transactions in the OGM
 * A managed transaction is one whose lifecycle is managed via a TransactionManager instance
 * Transactions managed by a Transaction Manager will automatically:
 * 1)  maintain single per-thread transaction instances
 * 2)  manage the calls to the appropriate driver to implement the relevant transaction semantics
 * 3)  manage transaction lifecycle and state correctly
 *
 * @author Michal Bachman
 * @author Vince Bickers
 */
public class TransactionManagerTest extends MultiDriverTestClass {

    private Session session;

    @BeforeClass
    public static void setUpClass() {
        sessionFactory = new SessionFactory(driver, "org.neo4j.ogm.domain.social");
    }

    @Before
    public void init() throws IOException {
        session = sessionFactory.openSession();
    }

    @After
    public void destroy() {
        session.purgeDatabase();
    }

    @Test
    public void shouldBeAbleToCreateManagedTransaction() {
        DefaultTransactionManager transactionManager = new DefaultTransactionManager(session,
            sessionFactory.getDriver().getTransactionFactorySupplier());
        assertThat(session.getLastBookmark()).isNull();
        try (Transaction tx = transactionManager.openTransaction()) {
            assertThat(tx.status()).isEqualTo(Transaction.Status.OPEN);
        }
    }

    @Test(expected = TransactionManagerException.class)
    @Ignore("What's the rationale of this test ? Actually leaves tx in an inconsistent state")
    public void shouldFailCommitFreeTransactionInManagedContext() {
        DefaultTransactionManager transactionManager = new DefaultTransactionManager(null, sessionFactory.getDriver().getTransactionFactorySupplier());
        try (Transaction tx = sessionFactory.getDriver().newTransaction(Transaction.Type.READ_WRITE, null)) {
            transactionManager.commit(tx);
        }
    }

    @Test(expected = TransactionManagerException.class)
    @Ignore("What's the rationale of this test ? Actually leaves tx in an inconsistent state")
    public void shouldFailRollbackFreeTransactionInManagedContext() {
        DefaultTransactionManager transactionManager = new DefaultTransactionManager(null, sessionFactory.getDriver().getTransactionFactorySupplier());
        try (Transaction tx = sessionFactory.getDriver().newTransaction(Transaction.Type.READ_WRITE, null)) {
            transactionManager.rollback(tx);
        }
    }

    @Test
    public void shouldRollbackManagedTransaction() {
        DefaultTransactionManager transactionManager = new DefaultTransactionManager(session,
            sessionFactory.getDriver().getTransactionFactorySupplier());
        assertThat(session.getLastBookmark()).isNull();

        try (Transaction tx = transactionManager.openTransaction()) {
            assertThat(tx.status()).isEqualTo(Transaction.Status.OPEN);
            tx.rollback();
            assertThat(tx.status()).isEqualTo(Transaction.Status.ROLLEDBACK);
        }
    }
}
