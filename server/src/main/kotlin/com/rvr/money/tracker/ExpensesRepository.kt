package com.rvr.money.tracker

import org.springframework.data.jpa.repository.JpaRepository

interface ExpensesRepository:JpaRepository<Expenses, Long>
