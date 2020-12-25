package com.rvr.money.tracker

import org.springframework.web.bind.annotation.*
import java.util.*

/**
 * Created by romanrybak on 06.09.2020.
 */
@RestController
@RequestMapping("/api")
class ExpensesController(private val repository: ExpensesRepository) {

    @GetMapping("/expenses")
    fun getListOfExpenses() : List<Expenses>
    {
        return listOf(Expenses("Food", Date().toString(), 100, "test"))
//        return repository.findAll()
    }

    @PostMapping("/expenses")
    fun addExpensesItem(@RequestBody expensesItem: Expenses) : Expenses
    {
        return repository.save(expensesItem)
    }
}
