package grails.issues

class Address {

    static belongsTo = [member: Member]

    String street
    String housenumber
    String postCode
    String city
    String additionalLine

    Date validFrom
    Date validTo

    static constraints = {
        street nullable: false, maxSize: 150, blank: false
        housenumber nullable: true, maxSize: 6
        postCode nullable: false, maxSize: 10, blank: false
        city nullable: false, maxSize: 60, blank: false
        validFrom nullable: false, attributes: [precision:"day"]
        validTo nullable: false, attributes: [precision:"day"]
        member nullable: false
        additionalLine nullable: true, maxSize: 200
    }

    @Override
    String toString() {
        return "${toStringShortFormat()} (${validFrom.format(Consts.DATETIME_FORMAT)} - ${validTo?.format(Consts.DATETIME_FORMAT)})"
    }

    String toStringShortFormat() {
        "${street} ${housenumber}, ${postCode} ${city}"
    }
}
