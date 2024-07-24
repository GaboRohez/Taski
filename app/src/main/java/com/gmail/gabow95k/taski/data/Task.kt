package com.gmail.gabow95k.taski.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.gabow95k.taski.Constants
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = Constants.TASK_TABLE_NAME)
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val createDate: String = getCurrentDateTime(),
    val lastUpdateDate: String = getCurrentDateTime(),
    val isCompleted: Boolean = false
) : Parcelable {
    companion object {
        fun getCurrentDateTime(): String {
            val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault())
            return dateFormat.format(Date())
        }
    }
}