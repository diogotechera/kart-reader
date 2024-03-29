package reader.kart.domain

import reader.kart.exception.DuplicatedLapException
import reader.kart.exception.InconsistentFinishTimeException
import reader.kart.helper.LapHelper
import reader.kart.helper.LapHelper.LAST_LAP
import java.time.LocalTime

data class Racer(val code : String, val name : String, private val laps : ArrayList<Lap> = arrayListOf()){


    fun addLap(lap: Lap){
        if(alreadyHasLap(lap))
            throw DuplicatedLapException("The lap is duplicated for the given racer: $lap")
        laps.add(lap)
    }

    private fun alreadyHasLap(lap: Lap) = laps.find { it.number == lap.number } != null

    fun lastLapNumber(): Int {
        return laps.maxBy { it.number }?.number!!
    }

    fun finalLapTime(): LocalTime {
        return laps.maxBy { it.number }?.currentTime!!
    }

    fun bestLap() : Lap {
       return laps.minBy { it.lapTime }!!
    }

    fun totalTime(): LocalTime {
        var totalTime = LocalTime.of(0,0,0,0)
        laps.forEach{
            totalTime = totalTime.plusMinutes(it.lapTime.minute.toLong())
            totalTime = totalTime.plusSeconds(it.lapTime.second.toLong())
            totalTime = totalTime.plusNanos(it.lapTime.nano.toLong())
        }
        return totalTime
    }

    fun averageSpeed(): Double {
        return laps.map { it.averageSpeed }.average()
    }

    fun differenceBetween(firstPlaceTime: LocalTime): LocalTime {
        if(firstPlaceTime.isAfter(finalLapTime()))
            throw InconsistentFinishTimeException("The given time is after of the final lap time")

        return finalLapTime().minusHours(firstPlaceTime.hour.toLong())
                .minusMinutes(firstPlaceTime.minute.toLong())
                .minusSeconds(firstPlaceTime.second.toLong())
                .minusNanos(firstPlaceTime.nano.toLong())
    }

    fun finishedRace(): Boolean {
        return lastLapNumber() == LAST_LAP
    }


}
