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
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.MutableDoubleList;
import org.eclipse.collections.api.partition.list.PartitionMutableList;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.utility.ArrayIterate;
import org.eclipse.collections.impl.utility.ListIterate;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.Assert;
import org.junit.Test;

/**
 * Below are links to APIs that may be helpful during these exercises.
 *
 * {@link ArrayIterate#collect(Object[], Function)}
 * {@link ArrayIterate#count(Object[], Predicate)}
 * {@link ArrayIterate#detect(Object[], Predicate)}
 * {@link ListIterate#collect(List, Function)}
 * {@link ListIterate#collectDouble(List, DoubleFunction)}
 * {@link ListIterate#select(List, Predicate)}
 *
 * @see
 * <a href="http://eclipse.github.io/eclipse-collections-kata/company-kata/#/12">Exercise
 * 4 Slides</a>
 */
public class Exercise4Test extends CompanyDomainForKata {

    /**
     * Solve this without changing the return type of
     * {@link Company#getSuppliers()}. Find the appropriate method on
     * {@link ArrayIterate}.
     */
    @Test
    public void findSupplierNames() {
        Function<Supplier, String> nameFunction = Supplier::getName;
        MutableList<String> supplierNames = getSuppliersList().collect(nameFunction).toList();

        MutableList<String> expectedSupplierNames = Lists.mutable.with(
                "Shedtastic",
                "Splendid Crocks",
                "Annoying Pets",
                "Gnomes 'R' Us",
                "Furniture Hamlet",
                "SFD",
                "Doxins");
        Assert.assertEquals(expectedSupplierNames, supplierNames);
    }

    /**
     * Create a {@link Predicate} for Suppliers that supply more than 2 items.
     * Find the number of suppliers that satisfy that Predicate.
     */
    @Test
    public void countSuppliersWithMoreThanTwoItems() {
        Predicate<Supplier> moreThanTwoItems = (supplier) -> {
            return supplier.getItemNames().length > 2;
        };
        ImmutableList<Supplier> suppliers = getSuppliersList().select(moreThanTwoItems);
        int suppliersWithMoreThanTwoItems = suppliers.size();
        Assert.assertEquals("suppliers with more than 2 items", 5, suppliersWithMoreThanTwoItems);
    }

    /**
     * Try to solve this without changing the return type of
     * {@link Supplier#getItemNames()}.
     */
    @Test
    public void whoSuppliesSandwichToaster() {
        // Create a Predicate that will check to see if a Supplier supplies a "sandwich toaster".
        
        Predicate<Supplier> suppliesToaster = (supplier) -> {
            ImmutableList<String> items = Lists.immutable.with(supplier.getItemNames());
            Predicate<String> namePredicate = (name)->{
                return name.equalsIgnoreCase("sandwich toaster");
            };
            return items.detect(namePredicate) != null;
           
        };

        // Find one supplier that supplies toasters.
        Supplier toasterSupplier = getSuppliersList().detect(suppliesToaster);
        Assert.assertNotNull("toaster supplier", toasterSupplier);
        Assert.assertEquals("Doxins", toasterSupplier.getName());
    }

    /**
     * Get the order values that are greater than 1.5.
     */
    @Test
    public void filterOrderValues() {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        Predicate<Order> highOrder = (order)->{
          return order.getValue()>1.5D;
        };
        Function<Order, Double> orderValueFunction = Order::getValue;
        MutableList<Double> orderValues = ListIterate.collectIf(orders, highOrder, orderValueFunction);
        
        Assert.assertEquals(Lists.mutable.with(372.5, 1.75), orderValues);
    }

    /**
     * Get the order values that are greater than 1.5 using double instead of
     * Double.
     */
    @Test
    public void filterOrderValuesUsingPrimitives() {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        MutableDoubleList orderValues = ListIterate.collectDouble(orders, Order::getValue);
        MutableDoubleList filtered = orderValues.select((dp)->dp>1.5D);
        Assert.assertEquals(DoubleLists.mutable.with(372.5, 1.75), filtered);
    }

    /**
     * Get the actual orders (not their double values) where those orders have a
     * value greater than 2.0.
     */
    @Test
    public void filterOrders() {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        Predicate<Order> highOrderPredicate = (order)->{
            return order.getValue() > 2.0D;
        };
        MutableList<Order> filtered = ListIterate.select(orders, highOrderPredicate);
        Assert.assertEquals(Lists.mutable.with(Iterate.getFirst(this.company.getMostRecentCustomer().getOrders())), filtered);
    }

    private ImmutableList<Supplier> getSuppliersList() {
        return Lists.immutable.with(this.company.getSuppliers());
    }

}
