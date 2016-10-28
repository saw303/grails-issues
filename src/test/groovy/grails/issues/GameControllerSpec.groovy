package grails.issues

import grails.test.hibernate.HibernateSpec
import grails.test.mixin.TestFor

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(GameController)
class GameControllerSpec extends HibernateSpec {

    static doWithSpring = {
        gameService(GameService)
    }

    void setup() {
        controller.transactionManager = transactionManager
        def gameService = getApplicationContext().getBean('gameService')
        gameService.transactionManager = transactionManager

    }

    void "Create a game using the wizard"() {

        given: 'a POST request'
        request.method = 'POST'

        when:
        controller.saveGame()

        then:
        Game.count() == 1
    }
}
