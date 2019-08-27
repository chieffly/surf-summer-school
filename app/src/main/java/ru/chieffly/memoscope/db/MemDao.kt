package ru.chieffly.memoscope.db


import androidx.room.*
import ru.chieffly.memoscope.model.MemDto


@Dao
interface MemDao {
    @get:Query("SELECT * FROM memdto")
    val all: List<MemDto>

    @Query("SELECT * FROM memdto WHERE id = :id")
    fun getById(id: Long): MemDto

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(employee: MemDto)

    @Update
    fun update(employee: MemDto)

    @Delete
    fun delete(employee: MemDto)

}