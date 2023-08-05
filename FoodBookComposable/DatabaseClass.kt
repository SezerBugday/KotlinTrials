import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Employee.Parcelize
@Entity(tableName = "employees")
data class Employee(

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "employeeId")
    var employeeId: Long,

    @ColumnInfo(name = "employeeName")
    var employeeName: String,

    @ColumnInfo(name = "designation")
    var employeeDesignation: String,

    @ColumnInfo(name = "experience")
    var empExperience: Float,

    @ColumnInfo(name = "email")
    var empEmail: String,

    @ColumnInfo(name = "phoneNo")
    var empPhoneNo: Long
) : Parcelable {
    annotation class Parcelize
}