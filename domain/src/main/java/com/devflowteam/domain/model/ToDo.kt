package com.devflowteam.domain.model

data class ToDo(
    val id: Int,
    val title: String,
    val text: String,
    val status: Status,
    val date: String
) {
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is ToDo) return false

        return id == other.id &&
               title == other.title &&
               text == other.text &&
               status == other.status &&
               date == other.date
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + text.hashCode()
        result = 31 * result + status.hashCode()
        result = 31 * result + date.hashCode()
        return result
    }
}