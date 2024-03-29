package reader.kart.service

import org.springframework.beans.factory.annotation.Autowired
import reader.kart.KartReaderApplicationTest
import reader.kart.domain.Racer
import reader.kart.factory.RacerFactory
import reader.kart.repository.RacerRepository

class RacerServiceTest extends KartReaderApplicationTest {

    @Autowired
    RacerService service

    def 'Should save a racer when it does not exists'(){

        given: 'A valid racer'
        Racer racer = RacerFactory.VALID

        when: 'It tries to save'
        def save = service.save(racer)

        then: 'it should save it'
        save
        save.code == '001'
        save.name =='S.MOCK'
    }

    def 'Should not add a racer if already exists'(){

        given: 'A saved racer'
        Racer racer = RacerFactory.VALID
        service.save(racer)

        when: 'It tries to save again'
        def save = service.save(RacerFactory.VALID2)

        then: 'it should not save, just retrieve with the old value'
        save
        save.code == '001'
        save.name =='S.MOCK'
    }

}