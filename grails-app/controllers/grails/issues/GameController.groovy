package grails.issues

import grails.transaction.Transactional

@Transactional(readOnly = true)
class GameController {

    static scaffold = Game

    static allowedMethods = [saveGame: 'POST']

    @Transactional
    def saveGame() {

        def game = new Game(startDate: new Date(), totalTicketsAvailable: 20)

        game.validate()
        game.save()

        render "Hello World"

    }
}
