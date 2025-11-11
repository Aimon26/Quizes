package com.aimon.app.quizes.fetures.leaderboard.repository

import android.util.Log
import com.aimon.app.quizes.core.constants.Constants.type
import com.aimon.app.quizes.fetures.leaderboard.data.LeaderBoardType
import com.aimon.app.quizes.fetures.score.repository.LeaderBoard
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LeaderBoardRepositoryImpl @Inject constructor(

) : LeaderBoardRepository {
    private val db = FirebaseFirestore.getInstance()

    override suspend fun showLeaderBoard(type: LeaderBoardType): Result<List<Board>> {
        val items = mutableListOf<Board>()

        try {
            val allUsers = db.collection("users").get().await().map {
                User(
                    username = it.data["username"] as String,
                    uuid = it.id
                )
            }

            db.collection("scores").get().await().map {
                items.add(
                    Board(
                        uuid = it.data["uuid"] as String,
                        score = it.data["score"] as Long,
                        timeStamp = it.data["timeStamp"] as Long,
                        username = allUsers.find{ user -> user.uuid == it.data["uuid"] }?.username ?: ""
                    )
                )
            }
            return Result.success(filterLeaderBoard(
                originalItems = items.sortedByDescending { it.score },
                type = type
            ))
        } catch (e: Exception) {
            Log.d("error", e.toString())
            return Result.failure<List<Board>>(e)
        }
    }

    private fun filterLeaderBoard(originalItems: List<Board>, type: LeaderBoardType): List<Board>{
        when(type) {
            LeaderBoardType.Daily -> {
                val today = getShortDate(System.currentTimeMillis())
                return originalItems.filter {
                    val itemDate = getShortDate(it.timeStamp)
                    itemDate == today
                }
            }

            LeaderBoardType.Monthly -> {
                val thisMonth = getShortDate(System.currentTimeMillis(), pattern = "yyyy-MM")
                return originalItems.filter {
                    val itemDate = getShortDate(it.timeStamp, pattern = "yyyy-MM")
                    itemDate == thisMonth
                }
            }

            LeaderBoardType.Weekly -> {
                val thisDay = getShortDate(System.currentTimeMillis(), pattern = "dd")
                return originalItems.filter {
                    val itemDate = getShortDate(it.timeStamp, pattern = "yyyy-MM-dd")
                    getStartAndEndDayByToday(stringToCalendar(itemDate))[0] <= thisDay &&  getStartAndEndDayByToday(stringToCalendar(itemDate))[1]>=thisDay
                }
            }
            LeaderBoardType.Overall -> return originalItems

        }
    }

    fun getShortDate(ts:Long?, pattern: String = "yyyy-MM-dd"):String{
        if(ts == null) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.getDefault())
        //get current date from ts
        calendar.timeInMillis = ts
        //return formatted date
        return android.text.format.DateFormat.format(pattern, calendar).toString()
    }

    fun stringToCalendar(dateString: String): Calendar {
        val formatPattern = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(formatPattern, Locale.getDefault())
        val date: Date? = dateFormat.parse(dateString)
        val calendar = Calendar.getInstance()
        if (date != null) {
           calendar.time= date
        }
        return calendar
    }


    fun getStartAndEndDayByToday(today: Calendar): ArrayList<String> {
        val startAndEndDayArray = ArrayList<String>()
        // Get the day of this week today
        val todayWeekDay = today.get(Calendar.DAY_OF_WEEK)
        // Below you can get the start day of week; it would be changeable according to local time zone
        today.add(Calendar.DAY_OF_MONTH, 1 - todayWeekDay)
        val calStartDayOfWeek = today.time
        // Below you can get the end day of week; it would be changeable according to local time zone
        today.add(Calendar.DAY_OF_MONTH, 6)
        val calEndDayOfWeek = today.time
        // Here you can set the date format by using SimpleDateFormat
        val sdfStartDayOfWeek = SimpleDateFormat("dd")
        val sdfEndDayOfWeek = SimpleDateFormat("dd")
        // Make the array with start date and end date
        startAndEndDayArray.add(sdfStartDayOfWeek.format(calStartDayOfWeek))
        startAndEndDayArray.add(sdfEndDayOfWeek.format(calEndDayOfWeek))
        return startAndEndDayArray
    }



}

data class User(
    val username: String,
    val uuid: String
)



