package com.health.vistacan.encounter.model

data class EncounterModel(val id:Int, val name:String, val phn:String, val gender:String, var isexpandable:Boolean=false)