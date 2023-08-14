package com.sezer.tipcalculator


data class Event(var title:String, var description:String?, var daypart: Daypart,
                 var duration : Int)


enum class Daypart
{
    Morning,Afternoon,Evening
}