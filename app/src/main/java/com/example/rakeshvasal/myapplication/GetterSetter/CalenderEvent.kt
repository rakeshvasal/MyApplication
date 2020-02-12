package com.example.rakeshvasal.myapplication.GetterSetter

import androidx.room.ColumnInfo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
class CalenderEvent : Serializable {
    @PrimaryKey
    var id: Long=0
    @ColumnInfo(name = "serverId")
    var serverId: String? = null
    @ColumnInfo(name = "first_name")
    var type = 0
    @ColumnInfo(name = "first_name")
    var eventDescription: String? = null
    @ColumnInfo(name = "first_name")
    var eventDateTime: Date? = null
    @ColumnInfo(name = "first_name")
    var contactName: String? = null
    @ColumnInfo(name = "first_name")
    var relatedEntities: List<String>? = null
    @ColumnInfo(name = "first_name")
    var createdOn: Date? = null
    @ColumnInfo(name = "first_name")
    var modifiedOn: Date? = null
    @ColumnInfo(name = "first_name")
    var createdBy: String? = null
    @ColumnInfo(name = "first_name")
    var modifiedBy: String? = null
    @ColumnInfo(name = "first_name")
    var eventTenureType = 0

    override fun toString(): String {
        return "CalenderEvent{" +
                "id=" + id +
                ", type=" + type +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventDateTime=" + eventDateTime +
                ", contactName='" + contactName + '\'' +
                ", relatedEntities=" + relatedEntities +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", eventTenureType=" + eventTenureType +
                '}'
    }
}