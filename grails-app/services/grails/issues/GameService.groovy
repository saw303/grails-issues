package grails.issues

import grails.transaction.Transactional

@Transactional
class GameService {

    @Transactional(readOnly = true)
    def verifyAvailableTickets(a, b) {
        true
    }
}
