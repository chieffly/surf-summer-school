package ru.chieffly.memoscope.db


import androidx.room.*
import io.reactivex.Flowable
import ru.chieffly.memoscope.model.MemDto


@Dao
interface MemDao {



    @Query("SELECT * FROM memdto")
    fun getall() : Flowable<List<MemDto>>

    @Query("SELECT * FROM memdto WHERE id = :id")
    fun getById(id: Long): Flowable<MemDto>

    @Query("SELECT * FROM memdto WHERE creatorId = :creator")
    fun getByCreatorId(creator: Int): Flowable<List<MemDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(employee: MemDto)

    @Update
    fun update(employee: MemDto)

    @Delete
    fun delete(employee: MemDto)

}