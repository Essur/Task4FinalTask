package ua.mk.essur.task4finaltask.weblogic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.mk.essur.task4finaltask.weblogic.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}