/*
 * Copyright (c) 2018 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */
package org.eclipse.collections.companykata;

import java.util.List;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.Assert;
import org.junit.Test;

/**
 * Below are links to APIs that may be helpful during these exercises.
 *
 * {@link MutableList#flatCollect(Function)}<br>
 * {@link MutableList#collect(Function)}<br>
 *
 * @see
 * <a href="http://eclipse.github.io/eclipse-collections-kata/company-kata/#/8">Exercise
 * 3 Slides</a>
 */
public class Exercise3Test extends CompanyDomainForKata {

    /**
     * Improve {@link Company#getOrders()} without breaking this test.
     */
    @Test
    public void improveGetOrders() {
        Verify.assertSize(5, this.company.getOrders());
    }

    /**
     * Get all items that have been ordered by anybody.
     */
    @Test
    public void findItemNames() {
        Function<Order,List<LineItem>> lineItemFunction = Order::getLineItems;
        
        MutableList<LineItem> allOrderedLineItems = company.getOrders().flatCollect(lineItemFunction);
        Function<LineItem, String> nameFunction = LineItem::getName;
        MutableSet<String> actualItemNames = allOrderedLineItems.collect(nameFunction).toSet();

        Verify.assertInstanceOf(MutableSet.class, actualItemNames);
        Verify.assertInstanceOf(String.class, actualItemNames.getFirst());

        MutableSet<String> expectedItemNames = Sets.mutable.with(
                "shed", "big shed", "bowl", "cat", "cup", "chair", "dog",
                "goldfish", "gnome", "saucer", "sofa", "table");
        Assert.assertEquals(expectedItemNames, actualItemNames);
    }

    @Test
    public void findCustomerNames() {
        Function<Customer, String> nameFunction = Customer::getName;
        MutableList<String> names = company.getCustomers().collect(nameFunction);

        MutableList<String> expectedNames = Lists.mutable.with("Fred", "Mary", "Bill");
        Assert.assertEquals(expectedNames, names);
    }
}
