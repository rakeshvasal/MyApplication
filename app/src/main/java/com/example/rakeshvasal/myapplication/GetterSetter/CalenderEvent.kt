package com.example.rakeshvasal.myapplication.GetterSetter

import androidx.room.ColumnInfo

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity
class CalenderEvent : Serializable {
    @PrimaryKey
    var id: Long=0
    @ColumnInfo(name = "serverId")
    var serverId: String? = null
    @ColumnInfo(name = "type")
    var type = 0
    @ColumnInfo(name = "eventDescription")
    var eventDescription: String? = null
    @ColumnInfo(name = "eventStringTime")
    var eventStringTime: String? = null
    @ColumnInfo(name = "contactName")
    var contactName: String? = null
    /*@ColumnInfo(name = "relatedEntities")
    var relatedEntities: List<String>? = null*/
    @ColumnInfo(name = "createdOn")
    var createdOn: String? = null
    @ColumnInfo(name = "modifiedOn")
    var modifiedOn: String? = null
    @ColumnInfo(name = "createdBy")
    var createdBy: String? = null
    @ColumnInfo(name = "modifiedBy")
    var modifiedBy: String? = null
    @ColumnInfo(name = "eventTenureType")
    var eventTenureType = 0

    /*override fun toString(): String {
        return "CalenderEvent{" +
                "id=" + id +
                ", type=" + type +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventStringTime=" + eventStringTime +
                ", contactName='" + contactName + '\'' +
                ", relatedEntities=" + relatedEntities +
                ", createdOn=" + createdOn +
                ", modifiedOn=" + modifiedOn +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", eventTenureType=" + eventTenureType +
                '}'
    }*/
}