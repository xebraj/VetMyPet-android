package com.vetmypet.vetmypet.model

data class Hourinterval(val start: String, val end: String) {
    override fun toString(): String {
        return "$start - $end"
    }
}