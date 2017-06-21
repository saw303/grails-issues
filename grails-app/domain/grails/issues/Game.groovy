package grails.issues

class Game {

    transient gameService

    Date startDate
    Date endDate
    Integer totalTicketsAvailable = -1
    Short hoursBeforeBookingCloses = -1
    Boolean visible = Boolean.FALSE

    static constraints = {
        startDate nullable: false, attributes: [precision: 'minute']
        endDate nullable: false, display: false, validator: { val, obj -> val >= obj.startDate }
        totalTicketsAvailable nullable: false, validator: { val, obj ->
            return obj.gameService.verifyAvailableTickets(obj, val)
        }
        hoursBeforeBookingCloses nullable: false
        visible nullable: false
    }

    def beforeValidate() {
        if ((!endDate && startDate) || (endDate && endDate < startDate)) {

            def calendar = new GregorianCalendar()
            calendar.time = startDate

            calendar.add(Calendar.HOUR_OF_DAY, 3)
            endDate = calendar.time

            log.debug("Automatically set end date to ${endDate.format('dd.MM.yyyy HH:mm:SS')}")
        }
    }

    static mapping = {
        sort startDate: 'asc'
        autowire true
    }
}
