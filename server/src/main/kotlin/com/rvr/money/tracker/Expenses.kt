package com.rvr.money.tracker

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Expenses (val title:String, val date:String, val amount:Long, val type:String)
{
    @Id
    @GeneratedValue
    var id: Long? = null
}
