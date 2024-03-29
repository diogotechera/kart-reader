package reader.kart.domain

import reader.kart.exception.DuplicatedLapException
import reader.kart.exception.InconsistentFinishTimeException
import reader.kart.factory.LapFactory
import reader.kart.factory.RacerFactory
import spock.lang.Specification

import java.time.LocalTime

class RacerTest extends Specification {


    def 'Should be able to add a lap'(){

        given: 'A valid racer'
        Racer racer = RacerFactory.VALID

        when: 'It tries to add a lap'
        racer.addLap(LapFactory.VALID)

        then: 'a lao is added'
        racer.laps.size() == 1
    }

    def 'Should not not be able to add a duplicated lap'(){
        given: 'a racer with a lap'
        Racer racer = RacerFactory.VALID2
        racer.addLap(LapFactory.VALID)

        when: 'It tries to add the same lap number'
        racer.addLap(LapFactory.VALID)

        then: 'it thrown an DuplicatedLapException'
        thrown(DuplicatedLapException)
    }

    def 'Should be able to retrieve the greater lap number'(){

        given: 'A racer with laps'
        Racer racer = RacerFactory.VALID_WITH_LAPS

        when: 'It tries to retrieve the greater lap number'
        def number = racer.lastLapNumber()

        then: 'the correct number is given'
        number == 2
    }

    def 'Should be able to retrieve the final lap time'(){

        given: 'A racer with laps'
        Racer racer = RacerFactory.VALID_WITH_LAPS

        when: 'It tries to retrieve the final lap time'
        def time = racer.finalLapTime()

        then: 'the correct time is given'
        time.hour == 12
        time.minute == 20
        time.second == 12
    }

    def 'Should be able to retrieve the best lap'(){

        given: 'A racer with laps'
        Racer racer = RacerFactory.VALID_WITH_LAPS

        when: 'It tries to retrieve the final lap time'
        def lap = racer.bestLap()

        then: 'the best lap is retrieved'
        lap.number == 1
    }


    def 'Should be able to calculate the racer total time'(){

        given: 'A racer with laps'
        Racer racer = RacerFactory.VALID_WITH_LAPS

        when: 'It tries to calculate the total time'
        def time = racer.totalTime()

        then: 'the correct time is given'
        time.second == 33
        time.minute == 2
    }

    def 'Should be able to calculate the racer average speed'(){

        given: 'A racer with laps'
        Racer racer = RacerFactory.VALID_WITH_LAPS

        when: 'It tries to calculate the average speed'
        def averageSpeed = racer.averageSpeed()

        then: 'the correct time is given'
        averageSpeed == 40D
    }

    def 'Should be able to calculate the difference between two time'(){

        given: 'A racer with laps and time before the given time'
        Racer racer = RacerFactory.VALID_WITH_LAPS
        def time = LocalTime.of(12, 10, 10)

        when: 'It tries to calculate the average speed'
        def diff = racer.differenceBetween(time)

        then: 'the correct time is given'
        diff.hour == 0
        diff.minute == 10
        diff.second == 02
    }

    def 'Should not be able to calculate the difference between two times if the informed is after the last racer time'(){

        given: 'A racer with laps and time after the given time'
        Racer racer = RacerFactory.VALID_WITH_LAPS
        def time = LocalTime.of(12, 30, 00)

        when: 'It tries to calculate the average speed'
        racer.differenceBetween(time)

        then: 'the correct time is given'
        thrown(InconsistentFinishTimeException)
    }

}